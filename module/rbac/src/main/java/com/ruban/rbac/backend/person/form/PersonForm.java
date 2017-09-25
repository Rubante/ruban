package com.ruban.rbac.backend.person.form;

import org.springframework.validation.annotation.Validated;

import com.ruban.rbac.vo.person.PersonVo;

@Validated
public class PersonForm extends PersonVo {

    /** 是否删除照片 **/
    private String delPhoto;

    public String getDelPhoto() {
        return delPhoto;
    }

    public void setDelPhoto(String delPhoto) {
        this.delPhoto = delPhoto;
    }

}
