package com.excelsecu.cmsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.excelsecu.cmsystem.entity.Account;
import com.excelsecu.cmsystem.mapper.AccountMapper;
import com.excelsecu.cmsystem.service.AccountService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 账号 服务实现类
 * </p>
 *
 * @author PENG.LEI
 * @since 2021-08-17
 */
@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements AccountService {

    @Override
    public Account query(String account) {
        QueryWrapper<Account> wrapper = new QueryWrapper<>();
        /**
         * select * from table limit i,n
         * i : 为查询结果的索引值（默认从0开始）；
         * n : 为查询结果返回的数量
         * limit n 等同于 limit 0,n
         */
        // 查询第一条数据
        wrapper.last("LIMIT 1");
        wrapper.setEntity(Account.builder()
                .openCode(account)
                .build());
        return getOne(wrapper);
    }

    @Override
    public Account queryByUserId(String userId) {
        QueryWrapper<Account> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        wrapper.last("LIMIT 1");
        return getOne(wrapper);
    }

    @Override
    public boolean removeByUserId(String userId) {
        QueryWrapper<Account> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        return remove(wrapper);
    }

}
