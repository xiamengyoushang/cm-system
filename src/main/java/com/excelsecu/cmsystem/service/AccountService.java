package com.excelsecu.cmsystem.service;

import com.excelsecu.cmsystem.entity.Account;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 账号 服务类
 * </p>
 *
 * @author PENG.LEI
 * @since 2021-08-17
 */
public interface AccountService extends IService<Account> {

    public Account query(String account);

    public Account queryByUserId(String userId);

    public boolean removeByUserId(String userId);

}
