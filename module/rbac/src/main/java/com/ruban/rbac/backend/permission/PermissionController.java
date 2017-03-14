package com.ruban.rbac.backend.permission;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
import com.ruban.rbac.backend.permission.form.PermissionForm;
import com.ruban.rbac.backend.permission.form.SearchForm;
import com.ruban.rbac.base.Pagination;
import com.ruban.rbac.domain.authz.Permission;
import com.ruban.rbac.service.ServiceLocator;

/**
 * 权限管理
 * 
 * @author yjwang
 *
 */
@Controller
public class PermissionController extends BackendController {

    Logger logger = LoggerFactory.getLogger(PermissionController.class);

    @Autowired
    private ServiceLocator serviceLocator;

    @RequestMapping("/permission/main")
    public String main(Model model) {

        SearchForm searchForm = new SearchForm();

        search(model, searchForm);

        return "backend/permission/main";
    }

    @RequestMapping(value = "/permission/search", method = RequestMethod.POST)
    public String search(Model model, @ModelAttribute("searchForm") SearchForm searchForm) {

        String companyTree = getCompanyTree();
        model.addAttribute("companyTree", companyTree);

        ResultInfo<Permission> resultInfo = serviceLocator.getPermissionService().selectByPage(searchForm);

        Pagination<Permission> pagination = new Pagination<>();
        pagination.gereate(resultInfo);

        model.addAttribute("pagination", pagination);
        model.addAttribute("resultInfo", resultInfo);

        return "backend/permission/list";
    }

    /**
     * 跳转到添加页面
     * 
     * @param model
     * @return
     */
    @RequestMapping(value = "/permission/addPage", method = RequestMethod.POST)
    public String addPage(Model model) {

        // 预置数据
        prepareData(model);

        return "backend/permission/add";
    }

    /**
     * 添加权限
     * 
     * @param permissionForm
     * @param bindingResult
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/permission/addSave", method = RequestMethod.POST)
    public JsonResult add(@Valid @ModelAttribute("permissionForm") PermissionForm permissionForm,
            BindingResult bindingResult) {

        JsonResult result = new JsonResult();

        if (bindingResult.hasErrors()) {
            result.getResult().put("error", bindingResult.getAllErrors());

            return result;
        }

        // 登录者所属companyId
        permissionForm.setCompanyId(1L);

        serviceLocator.getPermissionService().insert(permissionForm);

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
    @RequestMapping(value = "/permission/updatePage", method = RequestMethod.POST)
    public String updatePage(Model model, Long id) {

        // 预置数据
        prepareData(model);

        Permission permission = serviceLocator.getPermissionService().findById(id);
        model.addAttribute("result", permission);

        return "backend/permission/update";
    }

    /**
     * 更新权限
     * 
     * @param permissionForm
     * @param bindingResult
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/permission/updateSave", method = RequestMethod.POST)
    public JsonResult update(@Valid @ModelAttribute("permissionForm") PermissionForm permissionForm,
            BindingResult bindingResult) {

        JsonResult result = new JsonResult();

        if (bindingResult.hasErrors()) {
            result.getResult().put("error", bindingResult.getAllErrors());

            return result;
        }

        // 乐观锁
        if (permissionForm.getHoldLock() == null) {
            result.getResult().put("error", "未持有锁，无法更新！");

            return result;
        }

        // 登录者所属companyId
        permissionForm.setCompanyId(1L);

        int count = serviceLocator.getPermissionService().update(permissionForm);

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
     * 根据ID获取权限
     * 
     * @param id
     * @return
     */
    @RequestMapping(value = "/permission/detail", method = RequestMethod.POST)
    public String detail(Model model, String id, HttpServletResponse response) {

        JsonResult result = new JsonResult();
        if (!StringUtil.isDigit(id)) {
            result.setFlag(0);
            result.setMsg("id 参数不正确！");

            printJson(response, result);

            return null;
        }

        Permission permission = serviceLocator.getPermissionService().findById(Long.parseLong(id));

        if (permission != null) {
            model.addAttribute("result", permission);
        } else {
            result.setFlag(0);
            result.setMsg("未找到相应的记录！");

            printJson(response, result);

            return null;
        }

        return "backend/permission/detail";
    }

    /**
     * 根据ID删除权限
     * 
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/permission/delete", method = RequestMethod.POST)
    public JsonResult delete(String id) {

        JsonResult result = new JsonResult();
        if (!StringUtil.isDigit(id)) {
            result.setFlag(0);
            result.setMsg("id 参数不正确！");

            return result;
        }

        int count = serviceLocator.getPermissionService().deleteById(Long.parseLong(id));

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
     * 根据ID批量删除权限
     * 
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/permission/batchDelete", method = RequestMethod.POST)
    public JsonResult deleteByIds(String ids) {

        JsonResult result = new JsonResult();
        if (StringUtil.isNullOrEmpty(ids)) {
            result.setFlag(0);
            result.setMsg("ids 参数不正确！");

            return result;
        }

        String[] idArr = ids.split(",");

        int count = serviceLocator.getPermissionService().deleteByIds(idArr);

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
    @RequestMapping(value = "/permission/sortList", method = RequestMethod.POST)
    public String sortList(Model model, @ModelAttribute("searchForm") SearchForm searchForm) {

        List<Permission> list = serviceLocator.getPermissionService().selectByCondition(searchForm);
        model.addAttribute("list", list);

        return "backend/permission/sortList";
    }

    /**
     * 结果排序
     * 
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/permission/sort", method = RequestMethod.POST)
    public JsonResult sort(String ids) {

        JsonResult result = new JsonResult();
        if (StringUtil.isNullOrEmpty(ids)) {
            result.setFlag(0);
            result.setMsg("ids 参数不正确！");

            return result;
        }

        String[] idArr = ids.split(",");

        int count = serviceLocator.getPermissionService().sortByIds(idArr);

        if (count == idArr.length) {
            result.setFlag(1);
            result.setMsg("排序成功！");
        } else {
            result.setFlag(0);
            result.setMsg("排序失败！");
        }

        return result;
    }

    /**
     * 权限树
     * 
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/permission/tree", method = RequestMethod.POST)
    public String tree() {

        List<String> ztree = new ArrayList<>();

        SearchForm searchForm = new SearchForm();
        List<Permission> list = serviceLocator.getPermissionService().selectByCondition(searchForm);

        // 数据字典：机构类型
        Map<String, Dictionary> dictMap = getDictionaryMap(DictionaryGroupKey.RESOURCE_TYPE);

        if (dictMap != null) {

            Iterator<String> iterator = dictMap.keySet().iterator();

            while (iterator.hasNext()) {
                String key = iterator.next();
                Dictionary dictionary = dictMap.get(key);

                ztree.add("{id:'T" + dictionary.getCode() + "',name:'" + dictionary.getValue() + "',open: true}");
            }
        }

        // 构建组织机构树
        for (int i = 0; list != null && i < list.size(); i++) {
            Permission permission = list.get(i);

            // 节点
            String node = "{id:'" + permission.getId() + "',pId:'T" + permission.getType() + "',name:'"
                    + permission.getName() + "',open: true}";

            ztree.add(node);
        }

        String[] strArr = ztree.toArray(new String[0]);

        return Arrays.toString(strArr);
    }
}
