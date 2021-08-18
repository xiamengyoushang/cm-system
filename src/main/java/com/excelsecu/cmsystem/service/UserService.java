package com.excelsecu.cmsystem.service;

import com.excelsecu.cmsystem.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.excelsecu.cmsystem.pagination.PageWrapper;

import java.util.List;

/**
 * <p>
 * 用户 服务类
 * </p>
 *
 * @author PENG.LEI
 * @since 2021-08-17
 */
public interface UserService extends IService<User> {

    /**
     *
     * 分页查询 - 支持跨表
     * @param type [0, 1]
     * @param name 0:name是username; 1:name是orgname
     * @param pageIndex 查询目标页码,从1开始
     * @param pageSize 页大小
     * @return
     */
    PageWrapper<List<User>> queryList(Integer type, String name, Long pageIndex, Long pageSize);

}
