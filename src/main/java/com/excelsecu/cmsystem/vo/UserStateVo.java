package com.excelsecu.cmsystem.vo;

import com.excelsecu.cmsystem.common.enums.State;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserStateVo {

    @JsonProperty("user_id")
    String userId;

    State state;

}
