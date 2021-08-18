package com.excelsecu.cmsystem.service;

import com.excelsecu.cmsystem.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 角色 服务类
 * </p>
 *
 * @author PENG.LEI
 * @since 2021-08-17
 */
public interface RoleService extends IService<Role> {

    public Role query(String code);

}
