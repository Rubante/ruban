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

import com.ruban.common.dict.CompanyType;
import com.ruban.common.domain.Dictionary;
import com.ruban.framework.core.spring.SpringContext;
import com.ruban.framework.core.utils.commons.StringUtil;
import com.ruban.framework.web.page.JsonResult;
import com.ruban.rbac.backend.BackendController;
import com.ruban.rbac.backend.company.form.CompanyForm;
import com.ruban.rbac.backend.company.form.CompanyMapping;
import com.ruban.rbac.backend.company.form.SearchForm;
import com.ruban.rbac.domain.organization.Company;
import com.ruban.rbac.service.ICompanyService;

/**
 * 组织机构管理
 * 
 * @author yjwang
 *
 */
@Controller
public class CompanyController extends BackendController {

    @Autowired
    private ICompanyService companyService;

    @RequestMapping(CompanyMapping.URI_MAIN)
    public String main(Model model) {

        SearchForm searchForm = new SearchForm();

        search(model, searchForm);

        return CompanyMapping.PAGE_MAIN;
    }

    @RequestMapping(CompanyMapping.URI_GET_TREE)
    @ResponseBody
    public String getTree(String type) {
        return getCompanyTree(type);
    }

    /**
     * 查询数据
     * 
     * @return
     */
    @RequestMapping(value = CompanyMapping.URI_SEARCH, method = RequestMethod.POST)
    public String search(Model model, @ModelAttribute("searchForm") SearchForm searchForm) {

        // 初始化数据
        prepareData(model);

        // 设定只查询父节点分页
        searchForm.setCompanyId(0L);

        List<Company> list = companyService.selectByCondition(searchForm);

        model.addAttribute("list", list);

        // 下级节点是否显示
        model.addAttribute("childDisplay", searchForm.getChildDisplay());

        return CompanyMapping.PAGE_LIST;
    }

    /**
     * 跳转到添加页面
     * 
     * @param model
     * @return
     */
    @RequestMapping(value = CompanyMapping.URI_ADD_PAGE, method = RequestMethod.POST)
    public String addPage(Model model) {

        // 预置数据
        prepareData(model);

        return CompanyMapping.PAGE_ADD;

    }

    /**
     * 添加组织机构
     * 
     * @param model
     * @param companyForm
     * @param bindingResult
     * @return
     */
    @ResponseBody
    @RequestMapping(value = CompanyMapping.URI_ADD_SAVE, method = RequestMethod.POST)
    public JsonResult add(Model model, @Valid @ModelAttribute("companyForm") CompanyForm companyVo,
            BindingResult bindingResult, HttpServletResponse response) {

        JsonResult result = new JsonResult();

        // 校验表单是否错误
        if (!checkForm(result, bindingResult)) {
            return result;
        }

        // 登录信息
        companyVo.setAddUserId(0L);
        companyVo.setModUserId(0L);

        companyService.insert(companyVo);

        wrapSuccess(result, "添加成功");

        return result;
    }

    /**
     * 跳转到更新页面
     * 
     * @param model
     * @param id
     * @return
     */
    @RequestMapping(value = CompanyMapping.URI_UPDATE_PAGE, method = RequestMethod.POST)
    public String updatePage(Model model, Long id) {

        // 预置数据
        prepareData(model);

        Company company = companyService.findById(id);
        model.addAttribute("result", company);

        return CompanyMapping.PAGE_UPDATE;
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
    @ResponseBody
    @RequestMapping(value = CompanyMapping.URI_UPDATE_SAVE, method = RequestMethod.POST)
    public JsonResult update(String id, Model model, @Valid @ModelAttribute("companyForm") CompanyForm companyForm,
            BindingResult bindingResult, HttpServletResponse response) {

        JsonResult result = new JsonResult();

        // 校验表单是否错误
        if (!checkForm(result, bindingResult)) {
            return result;
        }

        // 乐观锁
        if (companyForm.getHoldLock() == null) {
            result.getResult().put("error", SpringContext.getText("holdLockNo"));
            return result;
        }

        // 登录信息
        companyForm.setModUserId(0L);

        int count = companyService.update(companyForm);

        if (count > 0) {
            wrapSuccess(result, SpringContext.getText("updateOk"));
        } else {
            wrapError(result, SpringContext.getText("updateNoLock"));
        }

        return result;
    }

    /**
     * 根据ID获取组织机构
     * 
     * @param id
     * @param model
     * @param response
     * @return
     */
    @RequestMapping(value = CompanyMapping.URI_DETAIL, method = RequestMethod.POST)
    public String detail(String id, Model model, HttpServletResponse response) {

        JsonResult result = new JsonResult();

        if (!checkId(id)) {

            wrapError(result, SpringContext.getText("paramError", "id"));

            printJson(response, result);

            return null;
        }

        Company company = companyService.findById(Long.parseLong(id));

        if (company != null) {

            // 预置数据
            prepareData(model);

            model.addAttribute("company", company);

            return CompanyMapping.PAGE_DETAIL;
        } else {
            wrapError(result, SpringContext.getText("noResult"));
            printJson(response, result);

            return null;
        }
    }

    /**
     * 根据ID删除组织机构
     * 
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = CompanyMapping.URI_DELETE, method = RequestMethod.POST)
    public JsonResult delete(String id) {

        JsonResult result = new JsonResult();

        if (!checkId(id)) {
            wrapError(result, SpringContext.getText("paramError", "id"));

            return result;
        }

        int count = companyService.deleteById(Long.parseLong(id));

        // 校验删除结果
        checkDelete(result, count);

        return result;
    }

    /**
     * 根据ID批量删除组织机构
     * 
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = CompanyMapping.URI_BATCH_DELETE, method = RequestMethod.POST)
    public JsonResult deleteByIds(String ids) {

        JsonResult result = new JsonResult();

        if (!checkId(ids)) {
            wrapError(result, SpringContext.getText("paramError", "id"));

            return result;
        }

        String[] idArr = ids.split(",");

        int count = companyService.deleteByIds(idArr);

        // 校验删除结果
        checkDelete(result, count);

        return result;
    }

    /**
     * 排序查询数据
     * 
     * @return
     */
    @RequestMapping(value = CompanyMapping.URI_SORT_LIST, method = RequestMethod.POST)
    public String sortList(Model model, @ModelAttribute("searchForm") SearchForm searchForm) {

        // 初始化数据
        prepareData(model);

        List<Company> list = companyService.selectByCondition(searchForm);

        model.addAttribute("list", list);

        return CompanyMapping.PAGE_SORT_LIST;
    }

    /**
     * 结果排序
     * 
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = CompanyMapping.URI_SORT, method = RequestMethod.POST)
    public JsonResult sort(String ids) {

        JsonResult result = new JsonResult();

        // 排序ids为空
        if (StringUtil.isNullOrEmpty(ids)) {
            wrapError(result, SpringContext.getText("paramError", "ids"));

            return result;
        }

        String[] idArr = ids.split(",");

        int count = companyService.sortByIds(idArr);

        if (count == idArr.length) {
            wrapSuccess(result, SpringContext.getText("sortOk"));
        } else {
            wrapError(result, SpringContext.getText("sortError"));
        }

        return result;
    }

    @Override
    protected void prepareData(Model model) {

        // 数据字典：机构类型
        List<Dictionary> types = getDictionarys(CompanyType.KEY);
        model.addAttribute("types", types);

        // 数据字典:机构类型
        Map<String, Dictionary> typeMap = getDictionaryMap(CompanyType.KEY);
        model.addAttribute("typeMap", typeMap);
    }

}
