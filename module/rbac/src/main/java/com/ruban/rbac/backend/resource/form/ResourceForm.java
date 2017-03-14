package com.ruban.rbac.backend.resource.form;

import java.io.Serializable;

import org.springframework.validation.annotation.Validated;

import com.ruban.common.vo.ResourceVo;

@Validated
public class ResourceForm extends ResourceVo implements Serializable {

    private static final long serialVersionUID = -8068180251093563415L;

}
