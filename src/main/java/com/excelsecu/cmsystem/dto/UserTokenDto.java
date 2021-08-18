package com.excelsecu.cmsystem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserTokenDto {

    @JsonProperty("account_id")
    private String accountId;

    private String account;

    @JsonProperty("role_name")
    private String roleName;

    private String token;

}
