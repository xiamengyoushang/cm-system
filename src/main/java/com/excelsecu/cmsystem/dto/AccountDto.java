package com.excelsecu.cmsystem.dto;

import com.excelsecu.cmsystem.common.enums.State;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {

    @JsonProperty("account_id")
    private String accountId;

    @JsonProperty("account")
    private String account;

    @JsonProperty("nick_name")
    private String nickName;

    @JsonProperty("role_name")
    private String roleName;

    private State state;

    @JsonProperty("head_img_url")
    private String headImgUrl;

    private String desc;

}
