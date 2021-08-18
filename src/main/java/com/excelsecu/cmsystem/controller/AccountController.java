package com.excelsecu.cmsystem.controller;

import com.auth0.jwt.interfaces.Claim;
import com.excelsecu.cmsystem.common.enums.AuthType;
import com.excelsecu.cmsystem.common.enums.ErrorType;
import com.excelsecu.cmsystem.common.enums.State;
import com.excelsecu.cmsystem.common.result.Result;
import com.excelsecu.cmsystem.common.utils.Constants;
import com.excelsecu.cmsystem.common.utils.PasswordHelper;
import com.excelsecu.cmsystem.dto.AccountDto;
import com.excelsecu.cmsystem.dto.UserTokenDto;
import com.excelsecu.cmsystem.entity.Account;
import com.excelsecu.cmsystem.entity.Role;
import com.excelsecu.cmsystem.entity.User;
import com.excelsecu.cmsystem.entity.UserRole;
import com.excelsecu.cmsystem.mapstruct.AccountMapper;
import com.excelsecu.cmsystem.mapstruct.UserAuthMapper;
import com.excelsecu.cmsystem.redis.RedisUtil;
import com.excelsecu.cmsystem.service.AccountService;
import com.excelsecu.cmsystem.service.RoleService;
import com.excelsecu.cmsystem.service.UserRoleService;
import com.excelsecu.cmsystem.service.UserService;
import com.excelsecu.cmsystem.shiro.JwtUtil;
import com.excelsecu.cmsystem.vo.AccountUpdateVo;
import com.excelsecu.cmsystem.vo.AccountVo;
import com.excelsecu.cmsystem.vo.LoginVo;
import com.excelsecu.cmsystem.vo.PasswordVo;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/key_service/v1/account")
public class AccountController {

    @Autowired
    AccountService accountService;

    @Autowired
    UserService userService;

    @Autowired
    UserRoleService userRoleService;

    @Autowired
    RoleService roleService;

    @Autowired
    PasswordHelper passwordHelper;

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    UserAuthMapper userAuthMapper;

    @Autowired
    AccountMapper accountMapper;

    @ApiOperation("创建用户")
    @PostMapping("/create")
    @RequiresRoles("admin")
    public Result<?> create(@RequestBody @Validated AccountVo accountVo) {
        Account account = accountService.query(accountVo.getAccount());
        if (null != account) {
            return Result.failure(ErrorType.ACCOUNT_EXISTED);
        }
        User user = User.builder()
                .name("Anonymous")
                .state(State.NORMAL)
                .intro(accountVo.getDesc())
                .build();
        user.setPassword(accountVo.getPassword());
        passwordHelper.encryptPassword(user);
        userService.save(user);

        account = Account.builder()
                .category(AuthType.PASSWORD)
                .openCode(accountVo.getAccount())
                .userId(Long.valueOf(user.getId()))
                .build();
        accountService.save(account);

        Role role = roleService.query("operator");
        UserRole userRole = UserRole.builder()
                .roleId(role.getId())
                .userId(Long.valueOf(user.getId()))
                .build();
        userRoleService.save(userRole);

        return Result.success();
    }

    @ApiOperation("用户登录")
    @PostMapping("/login")
    public Result<?> login(@RequestBody @Validated LoginVo loginVo){
        // 超级管理员 account:admin pwd:admin
        Account account = accountService.query(loginVo.getAccount());
        if (null == account) {
            return Result.failure(ErrorType.ACCOUNT_MISSING);
        }
        User user = userService.getById(account.getUserId());
        if (user.getState() == State.BANNED) {
            return Result.failure(ErrorType.ACCOUNT_INVALID);
        }
        if (!passwordHelper.verifyPassword(user,loginVo.getPassword())) {
            return Result.failure(ErrorType.LOGIN_FAILED);
        }
        String token = JwtUtil.sign(account.getOpenCode(),user.getId(),String.valueOf(System.currentTimeMillis()));
        redisUtil.set(Constants.PREFIX_USER_TOKEN+token,token);
        redisUtil.expire(Constants.PREFIX_USER_TOKEN+token,JwtUtil.REFRESH_TOKEN_EXPIRE_TIME/1000);

        String roleName = queryRoleNameByUserId(user.getId());
        UserTokenDto userTokenDto = userAuthMapper.do2Dto(account,token,roleName);
        return Result.success(userTokenDto);
    }

    @ApiOperation("用户登出")
    @PostMapping("/logout")
    public Result<?> logout(@RequestHeader(value = "Authorization") String token){
        Account account = accountFromToken(token);
        if (null == account) {
            return Result.failure(ErrorType.TOKEN_INVALID);
        }
        User user = userService.getById(account.getUserId());
        if (null != user) {
            redisUtil.del(Constants.PREFIX_USER_TOKEN+token);
        }
        Subject subject = SecurityUtils.getSubject();
        if (null != subject) {
            subject.logout();
        }
        return Result.success();
    }

    @ApiOperation("获取用户信息")
    @PostMapping("/query")
    @RequiresRoles(value={"admin","operator"},logical = Logical.OR)
    public Result<?> query(@RequestHeader(value = "Authorization") String token){
        Account account = accountFromToken(token);
        if (null == account) {
            return Result.failure(ErrorType.ACCOUNT_MISSING);
        }
        User user = userService.getById(account.getUserId());
        if (null == user) {
            return Result.failure(ErrorType.USER_ID_INVALID);
        }
        String roleName = queryRoleNameByUserId(user.getId());
        AccountDto accountDto = accountMapper.do2Dto(account,user,roleName);
        return Result.success(accountDto);
    }

    @ApiOperation("修改用户密码")
    @PostMapping("/modify_password")
    @RequiresRoles(value={"admin","operator"},logical = Logical.OR)
    public Result<?> modify_pwd(@RequestHeader(value = "Authorization") String token,
                                @RequestBody @Validated PasswordVo passwordVo){
        if (passwordVo.getPassword().equals(passwordVo.getNewPassword())) {
            return Result.failure(ErrorType.SAME_PASSWORD);
        }
        Account account = accountFromToken(token);
        if (null == account) {
            return Result.failure(ErrorType.ACCOUNT_MISSING);
        }
        User user = userService.getById(account.getUserId());
        if (null == user) {
            return Result.failure(ErrorType.USER_ID_INVALID);
        }
        if (!passwordHelper.verifyPassword(user,passwordVo.getPassword())){
            return Result.failure(ErrorType.WRONG_PASSWORD);
        }
        user.setPassword(passwordVo.getNewPassword());
        passwordHelper.encryptPassword(user);
        userService.updateById(user);
        return Result.success();
    }

    @ApiOperation("修改用户信息")
    @PostMapping("/update_profile")
    @RequiresRoles(value={"admin","operator"},logical = Logical.OR)
    public Result<?> update_info(@RequestHeader(value = "Authorization") String token,
                                 @RequestBody @Validated AccountUpdateVo accountUpdateVo){
        Account account = accountFromToken(token);
        if (null == account) {
            return Result.failure(ErrorType.ACCOUNT_MISSING);
        }
        User user = userService.getById(account.getUserId());
        if (null == user) {
            return Result.failure(ErrorType.USER_ID_INVALID);
        }

        String username = accountUpdateVo.getNickName();
        if (username != null && !username.isEmpty()) {
            user.setName(accountUpdateVo.getNickName());
        }
        String imgUrl = accountUpdateVo.getHeadImgUrl();
        if (imgUrl != null) {
            user.setHeadImgUrl(accountUpdateVo.getHeadImgUrl());
        }
        String desc = accountUpdateVo.getDesc();
        if (desc != null) {
            user.setIntro(accountUpdateVo.getDesc());
        }

        userService.updateById(user);
        String roleName = queryRoleNameByUserId(user.getId());
        AccountDto accountDto = accountMapper.do2Dto(account,user,roleName);
        return Result.success(accountDto);
    }

    private String queryRoleNameByUserId(String userId){
        UserRole userRole = userRoleService.query(userId);
        Role role = roleService.getById(userRole.getRoleId());
        return role.getName();
    }
    
    private Account accountFromToken(String token){
        if (null == token || token.isEmpty()) {
            return null;
        }
        Claim claim = JwtUtil.getClaim(token,JwtUtil.ACCOUNT);
        if (null == claim) {
            return null;
        }
        String username = claim.asString();
        return accountService.query(username);
    }

}
