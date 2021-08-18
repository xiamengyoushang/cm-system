package com.excelsecu.cmsystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.excelsecu.cmsystem.common.enums.ErrorType;
import com.excelsecu.cmsystem.common.result.Result;
import com.excelsecu.cmsystem.dto.UserDto;
import com.excelsecu.cmsystem.entity.Account;
import com.excelsecu.cmsystem.entity.Role;
import com.excelsecu.cmsystem.entity.User;
import com.excelsecu.cmsystem.entity.UserRole;
import com.excelsecu.cmsystem.mapstruct.UserQueryMapper;
import com.excelsecu.cmsystem.pagination.PageWrapper;
import com.excelsecu.cmsystem.service.AccountService;
import com.excelsecu.cmsystem.service.RoleService;
import com.excelsecu.cmsystem.service.UserRoleService;
import com.excelsecu.cmsystem.service.UserService;
import com.excelsecu.cmsystem.vo.PageQueryVo;
import com.excelsecu.cmsystem.vo.UserDeleteVo;
import com.excelsecu.cmsystem.vo.UserStateVo;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/key_service/v1/users")
public class UserMngController {

    @Autowired
    AccountService accountService;

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    UserRoleService userRoleService;

    @Autowired
    UserQueryMapper userQueryMapper;

    @ApiOperation("查询用户列表")
    @PostMapping("/query_list")
    @RequiresRoles("admin")
    public Result<?> queryList(@RequestBody PageQueryVo pageQueryVo){
        PageWrapper wrapper = null;
        if (pageQueryVo.getQueryType() == 1) {
            wrapper = queryNotSameTableUserList(pageQueryVo);
        } else  {
            wrapper = querySameTableUserList(pageQueryVo);
        }
        if (null == wrapper) {
            return Result.failure(ErrorType.FAILED);
        }
        List<User> userList = wrapper.getData();
        List<UserDto> userDtoList = userList.stream().map(user -> {
            Account account = accountService.queryByUserId(user.getId());
            UserRole userRole = userRoleService.query(user.getId());
            Role role = roleService.getById(userRole.getRoleId());
            return userQueryMapper.do2Dto(user,account,role);
        }).filter(Objects::nonNull).collect(Collectors.toList());
        return Result.success(PageWrapper.wrap(userDtoList,
                wrapper.getCurrentPage(),wrapper.getPageSize(),wrapper.getTotalPage(),wrapper.getTotalSize()));
    }

    // 跨表模糊查询+分页
    public PageWrapper<List<User>> queryNotSameTableUserList(PageQueryVo pageQueryVo){
        PageWrapper<List<User>> pageWrapper = userService.queryList(
                pageQueryVo.getQueryType(),
                pageQueryVo.getQueryContent(),
                pageQueryVo.getPageIndex().longValue(),
                pageQueryVo.getPageSize().longValue());
        return pageWrapper;
    }

    // 同表模糊查询+分页
    public PageWrapper<List<User>> querySameTableUserList(PageQueryVo pageQueryVo){
        Page page = new Page(pageQueryVo.getPageIndex(),pageQueryVo.getPageSize());
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        if (!pageQueryVo.getQueryContent().isEmpty()) {
            wrapper
                    .like("name",pageQueryVo.getQueryContent())
                    .or()
                    .like("mobile",pageQueryVo.getQueryContent());
        }
        wrapper.orderByDesc("created");
        IPage pageData = userService.page(page,wrapper);
        PageWrapper<List<User>> pageWrapper = PageWrapper.wrap(pageData.getRecords(), pageData);
        return pageWrapper;
    }

    @ApiOperation("修改用户状态")
    @PostMapping("/update_state")
    @RequiresRoles("admin")
    public Result<?> updateState(@RequestBody UserStateVo userStateVo){
        User user = userService.getById(userStateVo.getUserId());
        if (null == user) {
            return Result.failure(ErrorType.USER_ID_INVALID);
        }
        UserRole userRole = userRoleService.query(user.getId());
        Role role = roleService.getById(userRole.getRoleId());
        if (role.getCode().equals("admin")){
            return Result.failure(ErrorType.IS_SUPER_ADMIN);
        }
        user.setState(userStateVo.getState());
        userService.updateById(user);
        return Result.success();
    }

    @ApiOperation("删除用户")
    @PostMapping("/delete")
    @RequiresRoles("admin")
    public Result<?> deleteUser(@RequestBody UserDeleteVo userDeleteVo){
        UserRole userRole = userRoleService.query(userDeleteVo.getUserId());
        if (null == userRole) {
            return Result.failure(ErrorType.USER_ID_INVALID);
        }
        Role role = roleService.getById(userRole.getRoleId());
        if (role.getCode().equals("admin")){
            return Result.failure(ErrorType.IS_SUPER_ADMIN);
        }
        userService.removeById(userDeleteVo.getUserId());
        accountService.removeByUserId(userDeleteVo.getUserId());
        return Result.success();
    }

}
