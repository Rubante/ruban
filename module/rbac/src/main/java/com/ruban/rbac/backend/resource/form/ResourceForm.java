package com.ruban.rbac.backend.resource.form;

import org.springframework.validation.annotation.Validated;

import com.ruban.common.vo.ResourceVo;

@Validated
public class ResourceForm extends ResourceVo {

    /** 缩进距离 **/
    private String indent;

    /** 显示文件夹 **/
    private String folder;

    public String getIndent() {
        return indent;
    }

    public void setIndent(String indent) {
        this.indent = indent;
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

}
