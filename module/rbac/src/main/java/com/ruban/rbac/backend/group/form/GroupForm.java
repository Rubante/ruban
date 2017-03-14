package com.ruban.rbac.backend.group.form;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import com.ruban.rbac.backend.BackendForm;

@Validated
public class GroupForm extends BackendForm implements Serializable {

    private static final long serialVersionUID = -8068180251093563415L;

    private Long id;

    @Length(max = 4, message = "{group.code.length}")
    private String code;

    @NotNull(message = "{group.name.notnull}")
    private String name;

    private String simpleName;

    private int personId;

    private String setupTime;

    private int state;

    private String memo;

    private int hasChildren;

    private Long companyId;

    private Long groupId;

    private String holdLock;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSimpleName() {
        return simpleName;
    }

    public void setSimpleName(String simpleName) {
        this.simpleName = simpleName;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public String getSetupTime() {
        return setupTime;
    }

    public void setSetupTime(String setupTime) {
        this.setupTime = setupTime;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public int getHasChildren() {
        return hasChildren;
    }

    public void setHasChildren(int hasChildren) {
        this.hasChildren = hasChildren;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getHoldLock() {
        return holdLock;
    }

    public void setHoldLock(String holdLock) {
        this.holdLock = holdLock;
    }

}
