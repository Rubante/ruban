package com.ruban.rbac.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruban.common.service.IDictionaryService;
import com.ruban.common.service.IResourceFieldService;
import com.ruban.common.service.IResourceService;

@Service
public class ServiceLocator {

    @Autowired
    private IDictionaryService dictionaryService;

    @Autowired
    private ICompanyService companyService;

    @Autowired
    private IDepartmentService departmentService;

    @Autowired
    private IPersonService personService;

    @Autowired
    private IAccountService accountService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IPermissionService permissionService;

    @Autowired
    private IResourceService resourceService;

    @Autowired
    private IResourceFieldService resourceFieldService;

    public IDictionaryService getDictionaryService() {
        return dictionaryService;
    }

    public ICompanyService getCompanyService() {
        return companyService;
    }

    public IDepartmentService getDepartmentService() {
        return departmentService;
    }

    public IPersonService getPersonService() {
        return personService;
    }

    public IAccountService getAccountService() {
        return accountService;
    }

    public IRoleService getRoleService() {
        return roleService;
    }

    public IPermissionService getPermissionService() {
        return permissionService;
    }

    public IResourceService getResourceService() {
        return resourceService;
    }

    public IResourceFieldService getResourceFieldService() {
        return resourceFieldService;
    }

}
