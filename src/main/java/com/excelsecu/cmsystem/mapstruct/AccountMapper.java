package com.excelsecu.cmsystem.mapstruct;

import com.excelsecu.cmsystem.dto.AccountDto;
import com.excelsecu.cmsystem.entity.Account;
import com.excelsecu.cmsystem.entity.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AccountMapper {

    @Mappings({
            @Mapping(target="accountId", source = "account.id"),
            @Mapping(target="nickName", source = "user.name"),
            @Mapping(target="desc", source = "user.intro"),
            @Mapping(target="account", source = "account.openCode")
    })
    AccountDto do2Dto(Account account, User user, String roleName);

}
