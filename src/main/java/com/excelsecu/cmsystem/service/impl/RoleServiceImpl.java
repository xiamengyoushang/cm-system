package com.excelsecu.cmsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.excelsecu.cmsystem.entity.Role;
import com.excelsecu.cmsystem.mapper.RoleMapper;
import com.excelsecu.cmsystem.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色 服务实现类
 * </p>
 *
 * @author PENG.LEI
 * @since 2021-08-17
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Override
    public Role query(String code) {
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        wrapper.eq("code",code);
        wrapper.last("LIMIT 1");
        return getOne(wrapper);
    }

}
