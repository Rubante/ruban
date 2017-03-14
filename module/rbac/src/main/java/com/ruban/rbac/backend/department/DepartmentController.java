package com.ruban.rbac.backend.department;

import java.util.List;

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
import com.ruban.rbac.backend.department.form.DepartmentForm;
import com.ruban.rbac.backend.department.form.SearchForm;
import com.ruban.rbac.domain.organization.Department;
import com.ruban.rbac.service.ServiceLocator;

/**
 * 部门管理
 * 
 * @author yjwang
 *
 */
@Controller
public class DepartmentController extends BackendController {

    @Autowired
    private ServiceLocator serviceLocator;

    @RequestMapping("/department/main")
    public String main(Model model) {

        model.addAttribute("companyTree", getCompanyTree());
        model.addAttribute("ztree", getDepartmentTree());

        return "backend/department/main";
    }

    /**
     * 获取部门层次树
     * 
     * @return
     */
    @RequestMapping(value = "/department/getDptTree", method = RequestMethod.POST)
    @ResponseBody
    @Override
    public String getDepartmentTree() {
        return super.getDepartmentTree();
    }

    /**
     * 添加前数据准备
     * 
     * @return
     */
    @RequestMapping(value = "/department/list", method = RequestMethod.POST)
    public String list(Model model, @ModelAttribute("searchForm") SearchForm searchForm) {

        List<Department> list = serviceLocator.getDepartmentService().selectByCondition(searchForm);

        model.addAttribute("list", list);

        return "backend/department/list";
    }

    /**
     * 添加组织机构
     * 
     * @param model
     * @param departmentForm
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/department/add", method = RequestMethod.POST)
    public String add(Model model, @Valid @ModelAttribute("departmentForm") DepartmentForm departmentForm,
            BindingResult bindingResult, HttpServletResponse response) {

        JsonResult result = new JsonResult();

        // 展示页面
        if (departmentForm.getIsForm() == 0) {
            // 数据字典：机构类型
            List<Dictionary> dicts = getDictionarys(DictionaryGroupKey.COMPANY_TYPE);
            model.addAttribute("items", dicts);

            return "backend/department/add";
        }

        // 数据校验错误
        if (bindingResult.hasErrors()) {
            result.getResult().put("error", bindingResult.getAllErrors());

            printJson(response, result);

            return null;
        }

        if (bindingResult.hasErrors()) {
            result.getResult().put("error", bindingResult.getAllErrors());

        } else {

            serviceLocator.getDepartmentService().insert(departmentForm);

            result.setFlag(1);
            result.setMsg("添加成功！");
        }

        printJson(response, result);

        return null;
    }

    /**
     * 更新组织机构
     * 
     * @param model
     * @param departmentForm
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/department/update", method = RequestMethod.POST)
    public String update(Model model, String id, @Valid @ModelAttribute("departmentForm") DepartmentForm departmentForm,
            BindingResult bindingResult, HttpServletResponse response) {

        JsonResult result = new JsonResult();

        // 展示页面
        if (departmentForm.getIsForm() == 0) {

            if (StringUtil.isNullOrEmpty(id) || !StringUtil.isDigit(id)) {
                result.setFlag(0);
                result.setMsg("id 参数不正确！");

                printJson(response, result);

                return null;
            }

            Department department = serviceLocator.getDepartmentService().findById(Long.parseLong(id));

            if (department != null) {
                model.addAttribute("result", department);

                return "backend/department/update";
            } else {
                result.setFlag(0);
                result.setMsg("未查到数据！");

                printJson(response, result);

                return null;
            }

        }

        // 数据校验
        if (bindingResult.hasErrors()) {
            result.getResult().put("error", bindingResult.getAllErrors());

            printJson(response, result);

            return null;
        }

        // 乐观锁
        if (departmentForm.getHoldLock() == null) {
            result.getResult().put("error", "未持有锁，无法更新！");

            printJson(response, result);

            return null;
        }

        int count = serviceLocator.getDepartmentService().update(departmentForm);

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
    @RequestMapping(value = "/department/detail", method = RequestMethod.POST)
    public String detail(String id, Model model, HttpServletResponse response) {

        JsonResult result = new JsonResult();
        if (StringUtil.isNullOrEmpty(id) || !StringUtil.isDigit(id)) {
            result.setFlag(0);
            result.setMsg("id 参数不正确！");

            printJson(response, result);

            return null;
        }

        Department department = serviceLocator.getDepartmentService().findById(Long.parseLong(id));

        if (department != null) {
            model.addAttribute("result", department);

            return "backend/department/detail";
        } else {
            result.setFlag(0);
            result.setMsg("未找到相应的记录！");
        }

        return null;
    }

    /**
     * 根据ID删除组织机构
     * 
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/department/delete", method = RequestMethod.POST)
    public JsonResult delete(String id) {

        JsonResult result = new JsonResult();
        if (!StringUtil.isDigit(id)) {
            result.setFlag(0);
            result.setMsg("id 参数不正确！");

            return result;
        }

        int count = serviceLocator.getDepartmentService().deleteById(Long.parseLong(id));

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
    @RequestMapping(value = "/department/batchDelete", method = RequestMethod.POST)
    public JsonResult deleteByIds(String ids) {

        JsonResult result = new JsonResult();
        if (StringUtil.isNullOrEmpty(ids)) {
            result.setFlag(0);
            result.setMsg("ids 参数不正确！");

            return result;
        }

        String[] idArr = ids.split(",");

        int count = serviceLocator.getDepartmentService().deleteByIds(idArr);

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
    @RequestMapping(value = "/department/sortList", method = RequestMethod.POST)
    public String sortList(Model model, @ModelAttribute("searchForm") SearchForm searchForm) {

        List<Department> list = serviceLocator.getDepartmentService().selectByCondition(searchForm);
        model.addAttribute("list", list);

        return "backend/department/sortList";
    }

    /**
     * 结果排序
     * 
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/department/sort", method = RequestMethod.POST)
    public JsonResult sort(String ids) {

        JsonResult result = new JsonResult();
        if (StringUtil.isNullOrEmpty(ids)) {
            result.setFlag(0);
            result.setMsg("ids 参数不正确！");

            return result;
        }

        String[] idArr = ids.split(",");

        int count = serviceLocator.getDepartmentService().sortByIds(idArr);

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
