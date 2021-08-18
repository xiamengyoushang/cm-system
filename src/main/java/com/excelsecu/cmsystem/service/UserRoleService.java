package com.excelsecu.cmsystem.service;

import com.excelsecu.cmsystem.entity.UserRole;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户角色 服务类
 * </p>
 *
 * @author PENG.LEI
 * @since 2021-08-17
 */
public interface UserRoleService extends IService<UserRole> {

    public UserRole query(String userId);

}
