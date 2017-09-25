package com.ruban.rbac.backend.role;

import java.util.List;
import java.util.Map;

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

import com.ruban.common.dict.CommonState;
import com.ruban.common.dict.YesNo;
import com.ruban.common.domain.Dictionary;
import com.ruban.framework.core.spring.SpringContext;
import com.ruban.framework.core.utils.commons.DateUtil;
import com.ruban.framework.dao.helper.ResultInfo;
import com.ruban.framework.web.page.JsonResult;
import com.ruban.rbac.backend.BackendController;
import com.ruban.rbac.backend.role.form.RoleForm;
import com.ruban.rbac.backend.role.form.RoleMapping;
import com.ruban.rbac.backend.role.form.RoleCondition;
import com.ruban.rbac.base.Pagination;
import com.ruban.rbac.domain.authz.Role;
import com.ruban.rbac.domain.authz.RolePermission;
import com.ruban.rbac.service.IRolePermissionService;
import com.ruban.rbac.service.IRoleService;
import com.ruban.rbac.vo.permission.PermissionCondition;

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
    private IRoleService roleService;

    @Autowired
    private IRolePermissionService permissionService;

    @RequestMapping(RoleMapping.URI_MAIN)
    public String main(Model model) {

        RoleCondition searchForm = new RoleCondition();

        search(model, searchForm);

        return RoleMapping.PAGE_MAIN;
    }

    @RequestMapping(value = RoleMapping.URI_SEARCH, method = RequestMethod.POST)
    public String search(Model model, @ModelAttribute("searchForm") RoleCondition searchForm) {

        prepareData(model);

        String companyTree = getCompanyTree();
        model.addAttribute("companyTree", companyTree);

        ResultInfo<Role> resultInfo = roleService.selectByPage(searchForm);

        Pagination<Role> pagination = new Pagination<>();
        pagination.gereate(resultInfo);

        model.addAttribute("pagination", pagination);
        model.addAttribute("resultInfo", resultInfo);

        return RoleMapping.PAGE_LIST;
    }

    /**
     * 跳转到添加页面
     * 
     * @param model
     * @return
     */
    @RequestMapping(value = RoleMapping.URI_ADD_PAGE, method = RequestMethod.POST)
    public String addPage(Model model) {

        // 预置数据
        prepareData(model);

        return RoleMapping.PAGE_ADD;
    }

    /**
     * 添加角色
     * 
     * @param roleForm
     * @param bindingResult
     * @param model
     * @return
     */
    @ResponseBody
    @RequestMapping(value = RoleMapping.URI_ADD_SAVE, method = RequestMethod.POST)
    public JsonResult add(@Valid @ModelAttribute("roleForm") RoleForm roleForm, BindingResult bindingResult,
            Model model) {

        JsonResult result = new JsonResult();

        // 校验表单是否错误
        if (!checkForm(result, bindingResult)) {
            return result;
        }

        roleService.insert(roleForm);

        wrapSuccess(result, "添加成功");

        return result;
    }

    /**
     * 跳转到修改页面
     * 
     * @param model
     * @return
     */
    @RequestMapping(value = RoleMapping.URI_UPDATE_PAGE, method = RequestMethod.POST)
    public String updatePage(Model model, Long id) {

        // 预置数据
        prepareData(model);

        Role role = roleService.findById(id);
        model.addAttribute("result", role);

        return RoleMapping.PAGE_UPDATE;
    }

    /**
     * 更新角色
     * 
     * @param model
     * @param id
     * @param roleForm
     * @param bindingResult
     * @return
     */
    @ResponseBody
    @RequestMapping(value = RoleMapping.URI_UPDATE_SAVE, method = RequestMethod.POST)
    public JsonResult update(Model model, String id, @Valid @ModelAttribute("roleForm") RoleForm roleForm,
            BindingResult bindingResult) {

        JsonResult result = new JsonResult();

        // 校验表单是否错误
        if (!checkForm(result, bindingResult)) {
            return result;
        }

        // 乐观锁
        if (roleForm.getHoldLock() == null) {
            result.getResult().put("error", SpringContext.getText("holdLockNo"));
            return result;
        }

        // 设置修改人
        roleForm.setModUserId(1L);
        roleForm.setModTime(DateUtil.getNowTime());

        int count = roleService.update(roleForm);

        if (count > 0) {
            wrapSuccess(result, SpringContext.getText("updateOk"));
        } else {
            wrapSuccess(result, SpringContext.getText("updateNoLock"));
        }

        return result;
    }

    /**
     * 根据ID获取角色
     * 
     * @param id
     * @return
     */
    @RequestMapping(value = RoleMapping.URI_DETAIL, method = RequestMethod.POST)
    public String detail(Model model, String id, HttpServletResponse response) {

        JsonResult result = new JsonResult();
        if (!checkId(id)) {
            wrapError(result, SpringContext.getText("paramError", "id"));

            printJson(response, result);

            return null;
        }

        Role role = roleService.findById(Long.parseLong(id));

        if (role != null) {
            model.addAttribute("result", role);
            return RoleMapping.PAGE_DETAIL;

        } else {
            wrapError(result, SpringContext.getText("noResult"));
            printJson(response, result);

            return null;
        }
    }

    /**
     * 根据ID删除角色
     * 
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = RoleMapping.URI_DELETE, method = RequestMethod.POST)
    public JsonResult delete(String id) {

        JsonResult result = new JsonResult();
        if (!checkId(id)) {
            wrapError(result, SpringContext.getText("paramError", "id"));

            return result;
        }

        int count = roleService.deleteById(Long.parseLong(id));

        // 校验删除结果
        checkDelete(result, count);

        return result;
    }

    /**
     * 启用角色
     * 
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = RoleMapping.URI_ENABLE, method = RequestMethod.POST)
    public JsonResult enable(String id) {

        JsonResult result = new JsonResult();
        if (!checkId(id)) {
            wrapError(result, SpringContext.getText("paramError", "id"));

            return result;
        }

        roleService.enable(Long.parseLong(id));

        wrapSuccess(result, "启用成功");

        return result;
    }

    /**
     * 启用角色
     * 
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = RoleMapping.URI_UNABLE, method = RequestMethod.POST)
    public JsonResult unable(String id) {

        JsonResult result = new JsonResult();
        if (!checkId(id)) {
            wrapError(result, SpringContext.getText("paramError", "id"));

            return result;
        }

        roleService.unable(Long.parseLong(id));

        wrapSuccess(result, "禁用成功");

        return result;
    }

    /**
     * 跳转到授权页面
     * 
     * @param model
     * @param id
     * @return
     */
    @RequestMapping(value = RoleMapping.URI_GRANT_PAGE, method = RequestMethod.POST)
    public String grantPage(Model model, Long id) {

        // 预置数据
        prepareData(model);

        // 角色信息
        Role role = roleService.findById(id);
        model.addAttribute("result", role);

        return RoleMapping.PAGE_GRANT;
    }

    /**
     * 授权列表数据
     * 
     * @param model
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = RoleMapping.URI_GRANT_LIST, method = RequestMethod.POST)
    public List<RolePermission> grantList(Model model, Long roleId) {

        PermissionCondition condition = new PermissionCondition();
        condition.setRoleId(roleId);

        List<RolePermission> list = permissionService.selectByCondition(condition);

        return list;
    }

    /**
     * 角色授权
     * 
     * @param roleForm
     * @param bindingResult
     * @return
     */
    @ResponseBody
    @RequestMapping(value = RoleMapping.URI_GRANT_SAVE, method = RequestMethod.POST)
    public JsonResult grant(@Valid @ModelAttribute("role_grant_form") RoleForm roleForm, BindingResult bindingResult) {

        JsonResult result = new JsonResult();

        if (bindingResult.hasErrors()) {
            result.getResult().put("error", bindingResult.getAllErrors());

            return result;
        }

        roleForm.setModTime(DateUtil.getNowTime());
        roleForm.setModUserId(1L);

        int count = roleService.grant(roleForm);

        if (count > 0) {
            wrapSuccess(result, "授权成功");
        } else {
            wrapError(result, "授权失败");
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
    @RequestMapping(value = RoleMapping.URI_BATCH_DELETE, method = RequestMethod.POST)
    public JsonResult deleteByIds(String ids) {

        JsonResult result = new JsonResult();
        if (!checkId(ids)) {
            wrapError(result, SpringContext.getText("paramError", "ids"));

            return result;
        }

        String[] idArr = ids.split(",");

        int count = roleService.deleteByIds(idArr);

        checkDelete(result, count);

        return result;
    }

    @Override
    protected void prepareData(Model model) {

        // 是否map对象
        Map<String, Dictionary> yesnoMap = getDictionaryMap(YesNo.KEY);
        model.addAttribute("yesnoMap", yesnoMap);

        // 是否可委托：list
        List<Dictionary> yesnos = getDictionarys(YesNo.KEY);
        model.addAttribute("yesnos", yesnos);

        // 状态：map
        Map<String, Dictionary> stateMap = getDictionaryMap(CommonState.KEY);
        model.addAttribute("stateMap", stateMap);

        // 状态：map
        List<Dictionary> states = getDictionarys(CommonState.KEY);
        model.addAttribute("states", states);
    }
}
