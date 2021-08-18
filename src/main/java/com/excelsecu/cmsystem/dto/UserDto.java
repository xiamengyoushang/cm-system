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
public class UserDto {

    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("user_name")
    private String userName;

    private State state;

    @JsonProperty("nick_name")
    private String nickName;

    @JsonProperty("role_name")
    private String roleName;

    @JsonProperty("head_img_url")
    private String headImgUrl;

    private String desc;

}
