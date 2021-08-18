package com.excelsecu.cmsystem.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

// 操作状态类型
public enum State {
    BANNED(0, "禁用"),
    NORMAL(1, "正常");

    @EnumValue  //标记数据库存的值是code
    @JsonValue
    private final Integer value;

    private final String desc;

    State(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }
}
