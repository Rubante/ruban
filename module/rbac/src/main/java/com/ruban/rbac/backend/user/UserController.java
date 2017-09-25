package com.ruban.rbac.backend.user;

import java.util.HashMap;
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

import com.ruban.common.dict.Sex;
import com.ruban.common.dict.UserState;
import com.ruban.common.domain.Dictionary;
import com.ruban.framework.core.spring.SpringContext;
import com.ruban.framework.core.utils.commons.DateUtil;
import com.ruban.framework.core.utils.commons.RandomUtil;
import com.ruban.framework.dao.helper.ResultInfo;
import com.ruban.framework.web.page.JsonResult;
import com.ruban.rbac.backend.BackendController;
import com.ruban.rbac.backend.role.form.RoleCondition;
import com.ruban.rbac.backend.user.form.UserCondition;
import com.ruban.rbac.backend.user.form.UserForm;
import com.ruban.rbac.backend.user.form.UserMapping;
import com.ruban.rbac.backend.user.form.UserRoleCondition;
import com.ruban.rbac.base.Pagination;
import com.ruban.rbac.domain.authz.Role;
import com.ruban.rbac.domain.authz.User;
import com.ruban.rbac.domain.authz.UserRole;
import com.ruban.rbac.service.IRoleService;
import com.ruban.rbac.service.IUserRoleService;
import com.ruban.rbac.service.IUserService;
import com.ruban.rbac.vo.user.UserRoleVo;
import com.ruban.rbac.vo.user.UserVo;

/**
 * 用户帐号管理
 * 
 * @author yjwang
 *
 */
@Controller
public class UserController extends BackendController {

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IUserRoleService userRoleService;

    @RequestMapping(UserMapping.URI_MAIN)
    public String main(Model model) {

        UserCondition searchForm = new UserCondition();
        search(model, searchForm);

        return UserMapping.PAGE_MAIN;
    }

    @RequestMapping(value = UserMapping.URI_SEARCH, method = RequestMethod.POST)
    public String search(Model model, @ModelAttribute("searchForm") UserCondition searchForm) {

        // 预置数据
        prepareData(model);

        ResultInfo<User> resultInfo = userService.selectByPage(searchForm);

        Pagination<User> pagination = new Pagination<>();
        pagination.gereate(resultInfo);

        model.addAttribute("pagination", pagination);
        model.addAttribute("resultInfo", resultInfo);

        return UserMapping.PAGE_LIST;
    }

    /**
     * 跳转到添加页面
     * 
     * @param model
     * @return
     */
    @RequestMapping(value = UserMapping.URI_ADD_PAGE, method = RequestMethod.POST)
    public String addPage(Model model) {

        // 预置数据
        prepareData(model);

        return UserMapping.PAGE_ADD;

    }

    /**
     * 添加用户
     * 
     * @param model
     * @param userForm
     * @param bindingResult
     * @param response
     * @return
     */
    @RequestMapping(value = UserMapping.URI_ADD_SAVE, method = RequestMethod.POST)
    public JsonResult add(Model model, @Valid @ModelAttribute("userForm") UserForm userForm,
            BindingResult bindingResult, HttpServletResponse response) {

        JsonResult result = new JsonResult();

        // 校验表单是否错误
        if (!checkForm(result, bindingResult)) {
            return result;
        }

        userService.insert(userForm);

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
    @RequestMapping(value = UserMapping.URI_UPDATE_PAGE, method = RequestMethod.POST)
    public String updatePage(Model model, Long id) {

        // 预置数据
        prepareData(model);

        User user = userService.findById(id);
        model.addAttribute("result", user);

        return UserMapping.PAGE_UPDATE;
    }

    /**
     * 更新用户信息
     * 
     * @param model
     * @param id
     * @param userForm
     * @param bindingResult
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = UserMapping.URI_UPDATE_SAVE, method = RequestMethod.POST)
    public JsonResult update(Model model, String id, @Valid @ModelAttribute("userForm") UserForm userForm,
            BindingResult bindingResult, HttpServletResponse response) {

        JsonResult result = new JsonResult();

        // 校验表单是否错误
        if (!checkForm(result, bindingResult)) {
            return result;
        }

        // 乐观锁
        if (userForm.getHoldLock() == null) {
            result.getResult().put("error", SpringContext.getText("holdLockNo"));

            return result;
        }

        int count = userService.update(userForm);

        if (count > 0) {
            wrapSuccess(result, SpringContext.getText("updateOk"));
        } else {
            wrapError(result, SpringContext.getText("updateNoLock"));
        }

        return result;
    }

    /**
     * 根据ID获取人员
     * 
     * @param id
     * @return
     */
    @RequestMapping(value = UserMapping.URI_DETAIL, method = RequestMethod.POST)
    public String detail(Model model, String id, HttpServletResponse response) {

        JsonResult result = new JsonResult();

        // 校验Id是否正确
        if (!checkId(id)) {
            wrapError(result, SpringContext.getText("paramError", "id"));
            printJson(response, result);

            return null;
        }

        User user = userService.findById(Long.parseLong(id));

        if (user != null) {
            // 预置数据
            prepareData(model);

            model.addAttribute("result", user);

            // 获取当前用户所
            UserRoleCondition userRoleCondition = new UserRoleCondition();
            List<UserRole> userRoles = userRoleService.selectByCondition(userRoleCondition);
            model.addAttribute("userRoles", userRoles);

        } else {
            wrapError(result, SpringContext.getText("noResult"));
            printJson(response, result);

            return null;
        }

        return UserMapping.PAGE_DETAIL;
    }

    /**
     * 根据ID删除用户
     * 
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = UserMapping.URI_DELETE, method = RequestMethod.POST)
    public JsonResult delete(String id) {

        JsonResult result = new JsonResult();

        // 校验Id是否正确
        if (!checkId(id)) {
            wrapError(result, SpringContext.getText("paramError", "id"));

            return result;
        }

        int count = userService.deleteById(Long.parseLong(id));

        // 校验删除结果
        checkDelete(result, count);

        return result;
    }

    /**
     * 根据ID批量删除用户
     * 
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = UserMapping.URI_BATCH_DELETE, method = RequestMethod.POST)
    public JsonResult deleteByIds(String ids) {

        JsonResult result = new JsonResult();
        if (!checkId(ids)) {
            wrapError(result, SpringContext.getText("paramError", "ids"));

            return result;
        }

        String[] idArr = ids.split(",");

        int count = userService.deleteByIds(idArr);

        checkDelete(result, count);

        return result;
    }

    /**
     * 根据ID改变用户状态
     * 
     * @param id
     * @return
     */
    private JsonResult changeState(String id, int state, String status) {

        JsonResult result = new JsonResult();

        // 校验id是否正确
        if (!checkId(id)) {
            wrapError(result, SpringContext.getText("paramError", "id"));

            return result;
        }

        UserVo userVo = new UserVo();
        userVo.setId(Long.parseLong(id));
        userVo.setModTime(DateUtil.getNowTime());
        userVo.setUpdateLock(RandomUtil.getUpdateLock());
        userVo.setModUserId(1L);
        userVo.setState(state);

        int count = 0;
        if (UserState.Stop.getValue() == state) {
            count = userService.stop(userVo);
        } else if (UserState.Normal.getValue() == state) {
            count = userService.start(userVo);
        } else if (UserState.Disable.getValue() == state) {
            count = userService.disable(userVo);
        }

        if (count > 0) {
            wrapSuccess(result, "成功" + status);
        } else {
            wrapError(result, status + "失败");
        }

        return result;
    }

    /**
     * 根据ID停用用户
     * 
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = UserMapping.URI_STOP, method = RequestMethod.POST)
    public JsonResult stop(String id) {
        return changeState(id, UserState.Stop.getValue(), "停用");
    }

    /**
     * 根据ID启用用户
     * 
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = UserMapping.URI_START, method = RequestMethod.POST)
    public JsonResult start(String id) {
        return changeState(id, UserState.Normal.getValue(), "启用");
    }

    /**
     * 根据ID禁用用户
     * 
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = UserMapping.URI_DISABLE, method = RequestMethod.POST)
    public JsonResult disable(String id) {
        return changeState(id, UserState.Stop.getValue(), "禁用");
    }

    /**
     * 根据ID停用用户
     * 
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = UserMapping.URI_UNLOCK, method = RequestMethod.POST)
    public JsonResult unlock(String id) {
        return changeState(id, UserState.Normal.getValue(), "解锁");
    }

    /**
     * 用户授权
     * 
     * @param id
     * @return
     */
    @RequestMapping(value = UserMapping.URI_GRANT, method = RequestMethod.POST)
    public String grant(Model model, String id) {

        JsonResult result = new JsonResult();

        // 校验id是否正确
        if (!checkId(id)) {
            wrapError(result, SpringContext.getText("paramError", "id"));

            return null;
        }

        User user = userService.findById(Long.parseLong(id));
        model.addAttribute("result", user);

        // 依条件查询角色
        RoleCondition condition = new RoleCondition();
        List<Role> roles = roleService.selectByCondition(condition);
        model.addAttribute("roles", roles);

        // 获取当前用户所
        Map<Long, UserRole> resultMap = new HashMap<>();
        UserRoleCondition userRoleCondition = new UserRoleCondition();
        List<UserRole> userRoles = userRoleService.selectByCondition(userRoleCondition);
        if (userRoles != null) {
            for (UserRole userRole : userRoles) {
                resultMap.put(userRole.getRoleId(), userRole);
            }
        }
        model.addAttribute("userRoles", resultMap);

        // 查询所有的角色，
        return UserMapping.PAGE_GRANT;
    }

    /**
     * 用户授权保存
     * 
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = UserMapping.URI_GRANT_SAVE, method = RequestMethod.POST)
    public JsonResult grantSave(Model model, String id, @Valid @ModelAttribute("userForm") UserForm userForm,
            BindingResult bindingResult, HttpServletResponse response) {

        JsonResult result = new JsonResult();

        // 校验表单是否错误
        if (!checkForm(result, bindingResult)) {
            return result;
        }

        // 校验userId是否正确
        if (!checkId(userForm.getUserId())) {
            wrapError(result, SpringContext.getText("paramError", "userId"));

            return null;
        }

        UserRoleVo userRoleVo = new UserRoleVo();
        userRoleVo.setRoleIds(userForm.getRoles());
        userRoleVo.setUserId(Long.parseLong(userForm.getUserId()));
        userRoleVo.setModTime(DateUtil.getNowTime());
        userRoleVo.setModUserId(1L);

        int count = userRoleService.grant(userRoleVo);

        if (count > 0) {
            wrapSuccess(result, SpringContext.getText("updateOk"));
        } else {
            wrapError(result, SpringContext.getText("updateNoLock"));
        }

        return result;
    }

    @Override
    protected void prepareData(Model model) {
        // 数据字典：性别
        List<Dictionary> sexs = getDictionarys(Sex.KEY);
        model.addAttribute("sexs", sexs);

        Map<String, Dictionary> states = getDictionaryMap(UserState.KEY);
        model.addAttribute("stateMap", states);
    }

}
