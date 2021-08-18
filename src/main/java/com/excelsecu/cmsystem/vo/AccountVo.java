package com.excelsecu.cmsystem.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountVo {

    @NotBlank(message = "用户名不能为空")
    @Email(message = "请使用合法的邮箱作为账号")
    private String account;

    @NotBlank(message = "密码不能为空")
    private String password;

    @Length(max=255, message = "简介最多255字")
    private String desc;

}
