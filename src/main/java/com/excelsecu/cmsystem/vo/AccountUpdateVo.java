package com.excelsecu.cmsystem.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountUpdateVo {

    @NotBlank(message = "用户昵称不能为空")
    @JsonProperty("nick_name")
    @Length(max=32, message = "用户昵称不得多于32个字")
    String nickName;

    @JsonProperty("head_img_url")
    String headImgUrl;

    @JsonProperty("desc")
    @Length(max=255, message = "简介最多255字")
    String desc;

}
