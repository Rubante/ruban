package com.ruban.rbac.backend.account;

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
import com.ruban.rbac.backend.account.form.AccountForm;
import com.ruban.rbac.backend.account.form.SearchForm;
import com.ruban.rbac.base.Pagination;
import com.ruban.rbac.domain.authz.Account;
import com.ruban.rbac.service.ServiceLocator;

/**
 * 帐号管理
 * 
 * @author yjwang
 *
 */
@Controller
public class AccountController extends BackendController {

    Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private ServiceLocator serviceLocator;

    @RequestMapping("/account/main")
    public String main(Model model) {

        SearchForm searchForm = new SearchForm();
        search(model, searchForm);

        return "backend/account/main";
    }

    @RequestMapping(value = "/account/search", method = RequestMethod.POST)
    public String search(Model model, @ModelAttribute("searchForm") SearchForm searchForm) {

        ResultInfo<Account> resultInfo = serviceLocator.getAccountService().selectByPage(searchForm);

        Pagination<Account> pagination = new Pagination<>();
        pagination.gereate(resultInfo);

        Map<String,Dictionary> dicts = getDictionaryMap(DictionaryGroupKey.ACCOUNT_STATE);
        
        model.addAttribute("stateMap",dicts);
        model.addAttribute("pagination", pagination);
        model.addAttribute("resultInfo", resultInfo);

        return "backend/account/list";
    }

    /**
     * 添加账户
     * 
     * @param model
     * @param accountForm
     * @param bindingResult
     * @param response
     * @return
     */
    @RequestMapping(value = "/account/add", method = RequestMethod.POST)
    public String add(Model model, @Valid @ModelAttribute("accountForm") AccountForm accountForm,
            BindingResult bindingResult, HttpServletResponse response) {

        // 展示页面
        if (accountForm.getIsForm() == 0) {
            // 数据字典：性别
            List<Dictionary> dicts = getDictionarys(DictionaryGroupKey.PERSON_SEX);
            model.addAttribute("dicts", dicts);

            return "backend/account/add";
        }

        JsonResult result = new JsonResult();

        if (bindingResult.hasErrors()) {
            result.getResult().put("error", bindingResult.getAllErrors());

            printJson(response, result);

            return null;
        }

        for(int i = 0 ; i <15;i++)
        serviceLocator.getAccountService().insert(accountForm);

        result.setFlag(1);
        result.setMsg("添加成功！");

        printJson(response, result);

        return null;
    }

    /**
     * 更新账号
     * 
     * @param model
     * @param id
     * @param accountForm
     * @param bindingResult
     * @param response
     * @return
     */
    @RequestMapping(value = "/account/update", method = RequestMethod.POST)
    public String update(Model model, String id, @Valid @ModelAttribute("accountForm") AccountForm accountForm,
            BindingResult bindingResult, HttpServletResponse response) {

        // 展示页面
        if (accountForm.getIsForm() == 0) {
            // 数据字典：性别
            List<Dictionary> dicts = getDictionarys(DictionaryGroupKey.PERSON_SEX);
            model.addAttribute("dicts", dicts);

            Account person = serviceLocator.getAccountService().findById(accountForm.getId());
            model.addAttribute("result", person);

            return "backend/account/update";
        }

        JsonResult result = new JsonResult();

        if (bindingResult.hasErrors()) {
            result.getResult().put("error", bindingResult.getAllErrors());

            printJson(response, result);

            return null;
        }

        // 乐观锁
        if (accountForm.getHoldLock() == null) {
            result.getResult().put("error", "未持有锁，无法更新！");

            printJson(response, result);

            return null;
        }

        int count = serviceLocator.getAccountService().update(accountForm);

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
    @RequestMapping(value = "/account/detail", method = RequestMethod.POST)
    public String detail(Model model, String id, HttpServletResponse response) {

        JsonResult result = new JsonResult();
        if (!StringUtil.isDigit(id)) {
            result.setFlag(0);
            result.setMsg("id 参数不正确！");

            printJson(response, result);

            return null;
        }

        Account person = serviceLocator.getAccountService().findById(Long.parseLong(id));

        if (person != null) {
            model.addAttribute("result", person);
        } else {
            result.setFlag(0);
            result.setMsg("未找到相应的记录！");

            printJson(response, result);

            return null;
        }

        return "backend/account/detail";
    }

    /**
     * 根据ID删除人员
     * 
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/account/delete", method = RequestMethod.POST)
    public JsonResult delete(String id) {

        JsonResult result = new JsonResult();
        if (!StringUtil.isDigit(id)) {
            result.setFlag(0);
            result.setMsg("id 参数不正确！");

            return result;
        }

        int count = serviceLocator.getAccountService().deleteById(Long.parseLong(id));

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
    @RequestMapping(value = "/account/batchDelete", method = RequestMethod.POST)
    public JsonResult deleteByIds(String ids) {

        JsonResult result = new JsonResult();
        if (StringUtil.isNullOrEmpty(ids)) {
            result.setFlag(0);
            result.setMsg("ids 参数不正确！");

            return result;
        }

        String[] idArr = ids.split(",");

        int count = serviceLocator.getAccountService().deleteByIds(idArr);

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
    @RequestMapping(value = "/account/sortList", method = RequestMethod.POST)
    public String sortList(Model model, @ModelAttribute("searchForm") SearchForm searchForm) {

        List<Account> list = serviceLocator.getAccountService().selectByCondition(searchForm);
        model.addAttribute("list", list);

        return "backend/account/sortList";
    }

    /**
     * 结果排序
     * 
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/account/sort", method = RequestMethod.POST)
    public JsonResult sort(String ids) {

        JsonResult result = new JsonResult();
        if (StringUtil.isNullOrEmpty(ids)) {
            result.setFlag(0);
            result.setMsg("ids 参数不正确！");

            return result;
        }

        String[] idArr = ids.split(",");

        int count = serviceLocator.getAccountService().sortByIds(idArr);

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
