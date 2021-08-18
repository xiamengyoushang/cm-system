package com.excelsecu.cmsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.excelsecu.cmsystem.entity.UserRole;
import com.excelsecu.cmsystem.mapper.UserRoleMapper;
import com.excelsecu.cmsystem.service.UserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户角色 服务实现类
 * </p>
 *
 * @author PENG.LEI
 * @since 2021-08-17
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

    @Override
    public UserRole query(String userId) {
        QueryWrapper<UserRole> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        wrapper.last("LIMIT 1");
        return getOne(wrapper);
    }

}
