package com.ruban.rbac.organization.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ruban.framework.dao.helper.ResultInfo;
import com.ruban.rbac.domain.organization.Company;
import com.ruban.rbac.organization.form.CompanyForm;
import com.ruban.rbac.organization.form.SearchForm;
import com.ruban.rbac.service.ServiceLocator;

@Controller
@RequestMapping(value = "/company")
public class CompanyController {

    @Autowired
    private ServiceLocator serviceLocator;

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public String search(Model model, @ModelAttribute("searchForm") SearchForm searchForm) {

        ResultInfo<Company> result = serviceLocator.getCompanyService().selectByPage(searchForm);

        model.addAttribute("list", result.getList());
        model.addAttribute("startIndex", result.getStartRow());
        model.addAttribute("firstPage", result.isFirstPage());
        model.addAttribute("lastPage", result.isLastPage());
        model.addAttribute("pages", result.getPages());

        return "company/list";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add() {
        return "company/add";
    }

    @RequestMapping(value = "/addForm", method = RequestMethod.POST)
    public String addForm(Model model, @Valid @ModelAttribute("companyForm") CompanyForm companyForm,
            BindingResult result) {

        if (result.hasErrors()) {
            return "company/add";
        }

        Company company = new Company();
        company.setCode(companyForm.getCode());
        company.setName(companyForm.getName());
        company.setAddress(companyForm.getAddress());
        company.setEmail(companyForm.getEmail());

        serviceLocator.getCompanyService().insert(company);

        return "company/add";
    }
}
