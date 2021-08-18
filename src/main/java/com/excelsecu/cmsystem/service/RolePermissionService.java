package com.excelsecu.cmsystem.service;

import com.excelsecu.cmsystem.entity.RolePermission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 角色权限 服务类
 * </p>
 *
 * @author PENG.LEI
 * @since 2021-08-17
 */
public interface RolePermissionService extends IService<RolePermission> {

    public List<RolePermission> query(Long roleId);

}
