package com.excelsecu.cmsystem.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

// 用户登录认证方式
public enum AuthType {
    PASSWORD(0,"PWD"),
    FIDO(1,"FIDO"),
    KEY(2,"KEY");

    @EnumValue  //标记数据库存的值是code
    @JsonValue
    private final Integer value;

    private final String desc;

    AuthType(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }
}
