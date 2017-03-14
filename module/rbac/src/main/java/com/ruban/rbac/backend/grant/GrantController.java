package com.ruban.rbac.backend.grant;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.ruban.framework.dao.helper.ResultInfo;
import com.ruban.framework.web.page.JsonResult;
import com.ruban.rbac.backend.BackendController;
import com.ruban.rbac.backend.role.form.RoleForm;
import com.ruban.rbac.backend.role.form.SearchForm;
import com.ruban.rbac.base.Pagination;
import com.ruban.rbac.domain.authz.Role;
import com.ruban.rbac.service.ServiceLocator;

/**
 * 授权管理
 * 
 * @author yjwang
 *
 */
@Controller
public class GrantController extends BackendController {

    Logger logger = LoggerFactory.getLogger(GrantController.class);

    @Autowired
    private ServiceLocator serviceLocator;

    @RequestMapping("/grant/main")
    public String main(Model model) {

        SearchForm searchForm = new SearchForm();
        
        search(model, searchForm);
        
        return "backend/grant/main";
    }

    @RequestMapping(value = "/grant/search", method = RequestMethod.POST)
    public String search(Model model, @ModelAttribute("searchForm") SearchForm searchForm) {
        
        String companyTree = getCompanyTree();
        model.addAttribute("companyTree", companyTree);
        
        ResultInfo<Role> resultInfo = serviceLocator.getRoleService().selectByPage(searchForm);

        Pagination<Role> pagination = new Pagination<>();
        pagination.gereate(resultInfo);

        model.addAttribute("pagination", pagination);
        model.addAttribute("resultInfo", resultInfo);

        return "backend/grant/list";
    }

    /**
     * 添加人员
     * 
     * @param model
     * @param roleForm
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/grant/add", method = RequestMethod.POST)
    public String add(Model model, @Valid @ModelAttribute("roleForm") RoleForm roleForm, BindingResult bindingResult,
            HttpServletResponse response) {

        JsonResult result = new JsonResult();

        if (bindingResult.hasErrors()) {
            result.getResult().put("error", bindingResult.getAllErrors());

            printJson(response, result);

            return null;
        }

        serviceLocator.getRoleService().insert(roleForm);

        result.setFlag(1);
        result.setMsg("添加成功！");

        printJson(response, result);

        return null;
    }

    /**
     * 更新人员
     * 
     * @param photo
     * @param roleForm
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/grant/update", method = RequestMethod.POST)
    public String update(Model model, String id, @Valid @ModelAttribute("roleForm") RoleForm roleForm,
            BindingResult bindingResult, HttpServletResponse response) {


        JsonResult result = new JsonResult();

        if (bindingResult.hasErrors()) {
            result.getResult().put("error", bindingResult.getAllErrors());

            printJson(response, result);

            return null;
        }

        // 乐观锁
        if (roleForm.getHoldLock() == null) {
            result.getResult().put("error", "未持有锁，无法更新！");

            printJson(response, result);

            return null;
        }

        int count = serviceLocator.getRoleService().update(roleForm);

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
     * 根据ID获取人员
     * 
     * @param id
     * @return
     */
    @RequestMapping(value = "/grant/detail", method = RequestMethod.POST)
    public String detail(Model model, String id, HttpServletResponse response) {

        JsonResult result = new JsonResult();
        if (!StringUtil.isDigit(id)) {
            result.setFlag(0);
            result.setMsg("id 参数不正确！");

            printJson(response, result);

            return null;
        }

        Role person = serviceLocator.getRoleService().findById(Long.parseLong(id));

        if (person != null) {
            model.addAttribute("result", person);
        } else {
            result.setFlag(0);
            result.setMsg("未找到相应的记录！");

            printJson(response, result);

            return null;
        }

        return "backend/grant/detail";
    }

    /**
     * 根据ID删除人员
     * 
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/grant/delete", method = RequestMethod.POST)
    public JsonResult delete(String id) {

        JsonResult result = new JsonResult();
        if (!StringUtil.isDigit(id)) {
            result.setFlag(0);
            result.setMsg("id 参数不正确！");

            return result;
        }

        int count = serviceLocator.getRoleService().deleteById(Long.parseLong(id));

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
     * 根据ID批量删除人员
     * 
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/grant/batchDelete", method = RequestMethod.POST)
    public JsonResult deleteByIds(String ids) {

        JsonResult result = new JsonResult();
        if (StringUtil.isNullOrEmpty(ids)) {
            result.setFlag(0);
            result.setMsg("ids 参数不正确！");

            return result;
        }

        String[] idArr = ids.split(",");

        int count = serviceLocator.getRoleService().deleteByIds(idArr);

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
     * 排序查询数据
     * 
     * @return
     */
    @RequestMapping(value = "/grant/sortList", method = RequestMethod.POST)
    public String sortList(Model model, @ModelAttribute("searchForm") SearchForm searchForm) {

        List<Role> list = serviceLocator.getRoleService().selectByCondition(searchForm);
        model.addAttribute("list", list);

        return "backend/grant/sortList";
    }

    /**
     * 结果排序
     * 
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/grant/sort", method = RequestMethod.POST)
    public JsonResult sort(String ids) {

        JsonResult result = new JsonResult();
        if (StringUtil.isNullOrEmpty(ids)) {
            result.setFlag(0);
            result.setMsg("ids 参数不正确！");

            return result;
        }

        String[] idArr = ids.split(",");

        int count = serviceLocator.getRoleService().sortByIds(idArr);

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
