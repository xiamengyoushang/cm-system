package com.excelsecu.cmsystem.mapstruct;

import com.excelsecu.cmsystem.dto.UserDto;
import com.excelsecu.cmsystem.entity.Account;
import com.excelsecu.cmsystem.entity.Role;
import com.excelsecu.cmsystem.entity.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserQueryMapper {

    @Mappings({
            @Mapping(target = "userId",source = "user.id"),
            @Mapping(target = "userName",source = "account.openCode"),
            @Mapping(target = "nickName",source = "user.name"),
            @Mapping(target = "roleName",source = "role.name"),
            @Mapping(target = "desc",source = "user.intro")
    })
    UserDto do2Dto(User user, Account account, Role role);

}
