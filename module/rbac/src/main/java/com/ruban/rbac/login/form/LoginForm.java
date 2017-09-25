package com.ruban.rbac.login.form;

import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotBlank;

public class LoginForm {

    /**
     * 用户名
     * 
     */
    @NotBlank
    private String username;

    /**
     * 密码
     * 
     */
    @NotBlank
    private String password;

    /**
     * 验证码
     * 
     */
    @Min(value=4)
    private String validation;



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getValidation() {
        return validation;
    }

    public void setValidation(String validation) {
        this.validation = validation;
    }

}
