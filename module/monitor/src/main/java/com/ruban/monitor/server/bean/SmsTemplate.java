package com.ruban.monitor.server.bean;

import java.io.Serializable;

/**
 * 短信模板
 * 
 * @author yjwang
 *
 */
public class SmsTemplate implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 主键 **/
    private Long id;

    /** 短信应用场景 **/
    private String scene;

    /** 短信签名 **/
    private String signature;

    /** 模板ID **/
    private String templateId;

    /** 模板内容 **/
    private String content;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getScene() {
        return scene;
    }

    public void setScene(String scene) {
        this.scene = scene;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
