package com.ruban.rbac.domain.authz;

import java.util.Date;

/**
 * 系统可登录的账号，每个账号可以依据状态是否允许登录。<br />
 * 也可能是系统管理员，也可能不对应某个具体的人，只是一个用户而已
 * 
 * @author ruban
 *
 */
public class User {

    /** 主键 **/
    private int id;

    /** 用户名 **/
    private String userNo;

    /** 密码 **/
    private String password;

    /** 显示名 **/
    private String name;

    /** 状态 **/
    private int status;

    /** 所属员工 **/
    private int employeeId;

    /** 更新时间 **/
    private Date updatetime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

}
