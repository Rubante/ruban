package com.ruban.rbac.domain.organization;

import java.util.List;

import com.ruban.framework.dao.domain.PersistentObject;

/**
 * 该表代表各种形式的组织：集团，公司，办事处，客户，供应商等。<br />
 * 可能存在多条数据，在规模庞大的集团中，<br />
 * 代表集团，分公司，各办事处等 在单一组织中，该领域只需存储一条数据即可<br/>
 * 在具体的业务场景下，可代表供应商，客户等
 * 
 * @author ruban
 *
 */
public class Company extends PersistentObject {

    /** 编码 **/
    private String code;

    /** 路径code **/
    private String pathCode;

    /** 排序code **/
    private int orderCode;

    /** 子节点数量 **/
    private int childrenNum;

    /** 名称 **/
    private String name;

    /** 简称 **/
    private String simpleName;

    /** 地址 **/
    private String address;

    /** 邮编 **/
    private String postCode;

    /** 抬头 **/
    private String title;

    /** 电话 **/
    private String tel;

    /** 邮箱 **/
    private String email;

    /** 父机构 **/
    private Long companyId;

    /** 父机构名称 **/
    private String companyName;

    /** 机构类型 **/
    private int type;

    /** 机构类型名称 **/
    private String typeName;

    /** 备注 **/
    private String memo;

    /** 状态 **/
    private int state;

    private List<Company> children;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPathCode() {
        return pathCode;
    }

    public void setPathCode(String pathCode) {
        this.pathCode = pathCode;
    }

    public int getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(int orderCode) {
        this.orderCode = orderCode;
    }

    public int getChildrenNum() {
        return childrenNum;
    }

    public void setChildrenNum(int childrenNum) {
        this.childrenNum = childrenNum;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public List<Company> getChildren() {
        return children;
    }

    public void setChildren(List<Company> children) {
        this.children = children;
    }

}
