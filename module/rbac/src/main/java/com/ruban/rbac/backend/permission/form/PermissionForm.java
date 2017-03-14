package com.ruban.rbac.backend.permission.form;

import java.io.Serializable;

import org.springframework.validation.annotation.Validated;

import com.ruban.rbac.vo.permission.PermissionVo;

@Validated
public class PermissionForm extends PermissionVo implements Serializable {

    private static final long serialVersionUID = -8068180251093563415L;

}
