package com.excelsecu.cmsystem.service.impl;

import com.excelsecu.cmsystem.entity.User;
import com.excelsecu.cmsystem.entity.UserRole;
import com.excelsecu.cmsystem.mapper.UserMapper;
import com.excelsecu.cmsystem.pagination.PageWrapper;
import com.excelsecu.cmsystem.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户 服务实现类
 * </p>
 *
 * @author PENG.LEI
 * @since 2021-08-17
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public PageWrapper<List<User>> queryList(Integer type, String name, Long pageIndex, Long pageSize) {
        // 获取总数据量
        Long size = this.getBaseMapper().queryListSize(type,name);
        // 查询用户列表
        List<UserRole> list = this.getBaseMapper().quereyList(type,name,(pageIndex-1)*pageSize,pageSize);
        List<User> userList = list.stream().map(userRole -> {
            User user = this.getBaseMapper().selectById(userRole.getUserId());
            return user;
        }).filter(Objects::nonNull).collect(Collectors.toList());
        // 获取总页数
        long pageCount = size/pageSize+(size%pageSize>0?1:0);
        return PageWrapper.wrap(userList,pageIndex,pageSize,pageCount,size);
    }

}
