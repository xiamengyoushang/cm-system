package com.excelsecu.cmsystem.mapstruct;

import com.excelsecu.cmsystem.dto.UserTokenDto;
import com.excelsecu.cmsystem.entity.Account;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserAuthMapper {

    @Mappings({
            @Mapping(target = "accountId",source = "account.id"),
            @Mapping(target = "account",source = "account.openCode")
    })
    UserTokenDto do2Dto(Account account,String token,String roleName);

}
