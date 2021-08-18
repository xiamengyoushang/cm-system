package com.excelsecu.cmsystem.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginVo {

    @NotBlank(message = "用户名不能为空")
    private String account;

    // 需要允许admin密码通过
    @NotBlank(message = "密码不能为空")
    @Length(min=5,max=18,message="密码长度在6-18位")
    @Pattern(regexp = "^[0-9A-Za-z]{5,18}$", message = "密码必须为数字和字母的组合")
    private String password;

}
