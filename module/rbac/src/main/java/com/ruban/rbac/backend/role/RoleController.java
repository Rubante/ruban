package com.ruban.rbac.backend.role;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
 * 角色管理
 * 
 * @author yjwang
 *
 */
@Controller
public class RoleController extends BackendController {

    Logger logger = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private ServiceLocator serviceLocator;

    @RequestMapping("/role/main")
    public String main(Model model) {

        SearchForm searchForm = new SearchForm();

        search(model, searchForm);

        return "backend/role/main";
    }

    @RequestMapping(value = "/role/search", method = RequestMethod.POST)
    public String search(Model model, @ModelAttribute("searchForm") SearchForm searchForm) {

        String companyTree = getCompanyTree();
        model.addAttribute("companyTree", companyTree);

        ResultInfo<Role> resultInfo = serviceLocator.getRoleService().selectByPage(searchForm);

        Pagination<Role> pagination = new Pagination<>();
        pagination.gereate(resultInfo);

        model.addAttribute("pagination", pagination);
        model.addAttribute("resultInfo", resultInfo);

        return "backend/role/list";
    }

    /**
     * 跳转到添加页面
     * 
     * @param model
     * @return
     */
    @RequestMapping(value = "/role/addPage", method = RequestMethod.POST)
    public String addPage(Model model) {

        // 预置数据
        prepareData(model);

        return "backend/role/add";
    }

    /**
     * 添加角色
     * 
     * @param model
     * @param roleForm
     * @param bindingResult
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/role/addSave", method = RequestMethod.POST)
    public JsonResult add(@Valid @ModelAttribute("roleForm") RoleForm roleForm, BindingResult bindingResult) {

        JsonResult result = new JsonResult();

        if (bindingResult.hasErrors()) {
            result.getResult().put("error", bindingResult.getAllErrors());

            return result;
        }

        serviceLocator.getRoleService().insert(roleForm);

        result.setFlag(1);
        result.setMsg("添加成功！");

        return result;
    }

    /**
     * 跳转到更新页面
     * 
     * @param model
     * @param id
     * @return
     */
    @RequestMapping(value = "/role/updatePage", method = RequestMethod.POST)
    public String updatePage(Model model, Long id) {

        // 预置数据
        prepareData(model);

        Role role = serviceLocator.getRoleService().findById(id);
        model.addAttribute("result", role);

        return "backend/role/update";
    }

    /**
     * 更新角色
     * 
     * @param roleForm
     * @param bindingResult
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/role/updateSave", method = RequestMethod.POST)
    public JsonResult update(@Valid @ModelAttribute("roleForm") RoleForm roleForm, BindingResult bindingResult) {

        JsonResult result = new JsonResult();

        if (bindingResult.hasErrors()) {
            result.getResult().put("error", bindingResult.getAllErrors());

            return result;
        }

        // 乐观锁
        if (roleForm.getHoldLock() == null) {
            result.getResult().put("error", "未持有锁，无法更新！");

            return result;
        }

        int count = serviceLocator.getRoleService().update(roleForm);

        if (count > 0) {
            result.setFlag(1);
            result.setMsg("修改成功！");
        } else {
            result.setFlag(0);
            result.setMsg("修改失败，数据已发生变化，无法保存！");
        }

        return result;
    }

    /**
     * 根据ID获取角色
     * 
     * @param id
     * @return
     */
    @RequestMapping(value = "/role/detail", method = RequestMethod.POST)
    public String detail(Model model, String id, HttpServletResponse response) {

        JsonResult result = new JsonResult();
        if (!StringUtil.isDigit(id)) {
            result.setFlag(0);
            result.setMsg("id 参数不正确！");

            printJson(response, result);

            return null;
        }

        Role role = serviceLocator.getRoleService().findById(Long.parseLong(id));

        if (role != null) {
            model.addAttribute("result", role);
        } else {
            result.setFlag(0);
            result.setMsg("未找到相应的记录！");

            printJson(response, result);

            return null;
        }

        return "backend/role/detail";
    }

    /**
     * 根据ID删除角色
     * 
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/role/delete", method = RequestMethod.POST)
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
     * 跳转到授权页面
     * 
     * @param model
     * @param id
     * @return
     */
    @RequestMapping(value = "/role/grantPage", method = RequestMethod.POST)
    public String grantPage(Model model, Long id) {

        // 预置数据
        prepareData(model);

        Role role = serviceLocator.getRoleService().findById(id);
        RoleForm form = new RoleForm();
        BeanUtils.copyProperties(role, form);

        model.addAttribute("result", form);

        return "backend/role/grant";
    }

    /**
     * 角色授权
     * 
     * @param roleForm
     * @param bindingResult
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/role/grantSave", method = RequestMethod.POST)
    public JsonResult grant(@Valid @ModelAttribute("roleForm") RoleForm roleForm, BindingResult bindingResult) {

        JsonResult result = new JsonResult();

        if (bindingResult.hasErrors()) {
            result.getResult().put("error", bindingResult.getAllErrors());

            return result;
        }

        // 乐观锁
        if (roleForm.getHoldLock() == null) {
            result.getResult().put("error", "未持有锁，无法授权！");

            return result;
        }

        int count = serviceLocator.getRoleService().update(roleForm);

        if (count > 0) {
            result.setFlag(1);
            result.setMsg("授权成功！");
        } else {
            result.setFlag(0);
            result.setMsg("授权失败，数据已发生变化，无法保存！");
        }

        return result;
    }

    /**
     * 根据ID批量删除角色
     * 
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/role/batchDelete", method = RequestMethod.POST)
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
    @RequestMapping(value = "/role/sortList", method = RequestMethod.POST)
    public String sortList(Model model, @ModelAttribute("searchForm") SearchForm searchForm) {

        List<Role> list = serviceLocator.getRoleService().selectByCondition(searchForm);
        model.addAttribute("list", list);

        return "backend/role/sortList";
    }

    /**
     * 结果排序
     * 
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/role/sort", method = RequestMethod.POST)
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

    @Override
    protected void prepareData(Model model) {

    }
}
