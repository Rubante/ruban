package com.ruban.rbac.backend.company;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ruban.common.dict.DictionaryGroupKey;
import com.ruban.common.domain.Dictionary;
import com.ruban.framework.core.utils.commons.StringUtil;
import com.ruban.framework.web.page.JsonResult;
import com.ruban.rbac.backend.BackendController;
import com.ruban.rbac.backend.company.form.CompanyForm;
import com.ruban.rbac.backend.company.form.SearchForm;
import com.ruban.rbac.domain.organization.Company;
import com.ruban.rbac.service.ServiceLocator;

/**
 * 组织机构管理
 * 
 * @author yjwang
 *
 */
@Controller
public class CompanyController extends BackendController {

    @Autowired
    private ServiceLocator serviceLocator;

    @RequestMapping("/company/main")
    public String main(Model model) {

        String companyTree = getCompanyTree();
        model.addAttribute("companyTree", companyTree);
        model.addAttribute("ztree", companyTree);

        return "backend/company/main";
    }

    @RequestMapping("/company/getTree")
    @ResponseBody
    public String getTree() {
        return getCompanyTree();
    }

    /**
     * 查询数据
     * 
     * @return
     */
    @RequestMapping(value = "/company/list", method = RequestMethod.POST)
    public String list(Model model, @ModelAttribute("searchForm") SearchForm searchForm) {

        // 数据字典：机构类型
        List<Dictionary> dicts = getDictionarys(DictionaryGroupKey.COMPANY_TYPE);
        model.addAttribute("dicts", dicts);
        Map<String, Dictionary> dictMap = getDictionaryMap(dicts);

        List<Company> list = serviceLocator.getCompanyService().selectByCondition(searchForm);

        model.addAttribute("list", list);
        model.addAttribute("dictMap", dictMap);

        return "backend/company/list";
    }

    /**
     * 排序查询数据
     * 
     * @return
     */
    @RequestMapping(value = "/company/sortList", method = RequestMethod.POST)
    public String sortList(Model model, @ModelAttribute("searchForm") SearchForm searchForm) {

        // 数据字典：机构类型
        List<Dictionary> dicts = getDictionarys(DictionaryGroupKey.COMPANY_TYPE);
        model.addAttribute("dicts", dicts);
        Map<String, Dictionary> dictMap = getDictionaryMap(dicts);

        List<Company> list = serviceLocator.getCompanyService().selectByCondition(searchForm);

        model.addAttribute("list", list);
        model.addAttribute("dictMap", dictMap);

        return "backend/company/sortList";
    }

    /**
     * 添加组织机构
     * 
     * @param model
     * @param companyForm
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/company/add", method = RequestMethod.POST)
    public String add(Model model, @Valid @ModelAttribute("companyForm") CompanyForm companyForm,
            BindingResult bindingResult, HttpServletResponse response) {

        JsonResult result = new JsonResult();

        // 展示页面
        if (companyForm.getIsForm() == 0) {
            // 数据字典：机构类型
            List<Dictionary> dicts = getDictionarys(DictionaryGroupKey.COMPANY_TYPE);
            model.addAttribute("items", dicts);

            return "backend/company/add";
        }

        // 数据校验错误
        if (bindingResult.hasErrors()) {
            return "backend/company/add";
        }

        // 登录信息
        companyForm.setAddUserId(0L);
        companyForm.setModUserId(0L);

        serviceLocator.getCompanyService().insert(companyForm);

        result.setFlag(1);
        result.setMsg("修改成功！");

        printJson(response, result);

        return null;
    }

    /**
     * 更新组织机构
     * 
     * @param id
     * @param model
     * @param companyForm
     * @param bindingResult
     * @param response
     * @return
     */
    @RequestMapping(value = "/company/update", method = RequestMethod.POST)
    public String update(String id, Model model, @Valid @ModelAttribute("companyForm") CompanyForm companyForm,
            BindingResult bindingResult, HttpServletResponse response) {

        // 展示页面
        if (companyForm.getIsForm() == 0) {
            Company company = serviceLocator.getCompanyService().findById(Long.parseLong(id));

            if (company != null) {
                model.addAttribute("result", company);
            }

            // 数据字典：机构类型
            List<Dictionary> dicts = getDictionarys(DictionaryGroupKey.COMPANY_TYPE);
            model.addAttribute("dicts", dicts);

            return "backend/company/update";
        }

        if (bindingResult.hasErrors()) {
            return "backend/company/update";
        }

        JsonResult result = new JsonResult();
        // 乐观锁
        if (companyForm.getHoldLock() == null) {
            result.setMsg("未持有锁，无法更新！");
            return "backend/company/update";
        }

        // 登录信息
        companyForm.setModUserId(0L);

        int count = serviceLocator.getCompanyService().update(companyForm);

        if (count > 0) {
            result.setFlag(1);
            result.setMsg("修改成功！");
        } else {
            result.setFlag(0);
            result.setMsg("修改失败，数据已发生变化，无法保存！");
        }

        printJson(response, result);

        return null;
    }

    /**
     * 根据ID获取组织机构
     * 
     * @param id
     * @return
     */
    @RequestMapping(value = "/company/detail", method = RequestMethod.POST)
    public String detail(String id, Model model, HttpServletResponse response) {

        JsonResult result = new JsonResult();
        if (!StringUtil.isDigit(id)) {
            result.setFlag(0);
            result.setMsg("id 参数不正确！");

            printJson(response, result);

            return null;
        }

        // 数据字典：机构类型
        Map<String, Dictionary> dictMap = getDictionaryMap(DictionaryGroupKey.COMPANY_TYPE);

        Company company = serviceLocator.getCompanyService().findById(Long.parseLong(id));

        if (company != null) {

            model.addAttribute("company", company);
            model.addAttribute("dictMap", dictMap);

        } else {
            result.setFlag(0);
            result.setMsg("未找到相应的记录！");
            printJson(response, result);
        }

        return "backend/company/detail";
    }

    /**
     * 根据ID删除组织机构
     * 
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/company/delete", method = RequestMethod.POST)
    public JsonResult delete(String id) {

        JsonResult result = new JsonResult();
        if (!StringUtil.isDigit(id)) {
            result.setFlag(0);
            result.setMsg("id 参数不正确！");

            return result;
        }

        int count = serviceLocator.getCompanyService().deleteById(Long.parseLong(id));

        if (count > 0) {
            result.setFlag(1);
            result.setMsg("删除成功！");
        } else {
            result.setFlag(0);
            result.setMsg("删除失败！");
        }

        return result;
    }

    /**
     * 根据ID批量删除组织机构
     * 
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/company/batchDelete", method = RequestMethod.POST)
    public JsonResult deleteByIds(String ids) {

        JsonResult result = new JsonResult();
        if (StringUtil.isNullOrEmpty(ids)) {
            result.setFlag(0);
            result.setMsg("ids 参数不正确！");

            return result;
        }

        String[] idArr = ids.split(",");

        int count = serviceLocator.getCompanyService().deleteByIds(idArr);

        if (count > 0) {
            result.setFlag(1);
            result.setMsg("删除成功！");
        } else {
            result.setFlag(0);
            result.setMsg("删除失败！");
        }

        return result;
    }

    /**
     * 结果排序
     * 
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/company/sort", method = RequestMethod.POST)
    public JsonResult sort(String ids) {

        JsonResult result = new JsonResult();
        if (StringUtil.isNullOrEmpty(ids)) {
            result.setFlag(0);
            result.setMsg("ids 参数不正确！");

            return result;
        }

        String[] idArr = ids.split(",");

        int count = serviceLocator.getCompanyService().sortByIds(idArr);

        if (count == idArr.length) {
            result.setFlag(1);
            result.setMsg("排序成功！");
        } else {
            result.setFlag(0);
            result.setMsg("排序失败！");
        }

        return result;
    }
}
