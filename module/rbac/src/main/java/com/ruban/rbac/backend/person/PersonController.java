package com.ruban.rbac.backend.person;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Base64Utils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ruban.common.dict.DictionaryGroupKey;
import com.ruban.common.domain.Dictionary;
import com.ruban.framework.core.utils.commons.StringUtil;
import com.ruban.framework.dao.helper.ResultInfo;
import com.ruban.framework.web.page.JsonResult;
import com.ruban.rbac.backend.BackendController;
import com.ruban.rbac.backend.person.form.PersonForm;
import com.ruban.rbac.backend.person.form.SearchForm;
import com.ruban.rbac.base.Pagination;
import com.ruban.rbac.domain.organization.Person;
import com.ruban.rbac.service.ServiceLocator;

/**
 * 人员管理
 * 
 * @author yjwang
 *
 */
@Controller
public class PersonController extends BackendController {

    Logger logger = LoggerFactory.getLogger(PersonController.class);

    @Autowired
    private ServiceLocator serviceLocator;

    @RequestMapping("/person/main")
    public String main(Model model) {

        String companyTree = getCompanyTree();
        model.addAttribute("companyTree", companyTree);
        model.addAttribute("dptTree", getDepartmentTree());

        return "backend/person/main";
    }

    /**
     * 获取部门层次树
     * 
     * @return
     */
    @RequestMapping(value = "/person/getDptTree", method = RequestMethod.POST)
    @ResponseBody
    @Override
    public String getDepartmentTree() {
        return super.getDepartmentTree();
    }

    @RequestMapping(value = "/person/search", method = RequestMethod.POST)
    public String search(Model model, @ModelAttribute("searchForm") SearchForm searchForm) {

        ResultInfo<Person> resultInfo = serviceLocator.getPersonService().selectByPage(searchForm);

        Pagination<Person> pagination = new Pagination<>();
        pagination.gereate(resultInfo);

        model.addAttribute("pagination", pagination);
        model.addAttribute("resultInfo", resultInfo);

        return "backend/person/list";
    }

    /**
     * 添加人员
     * 
     * @param model
     * @param personForm
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/person/add", method = RequestMethod.POST)
    public String add(@RequestPart(value = "photo", required = false) byte[] photo,
            @Valid @ModelAttribute("personForm") PersonForm personForm, BindingResult bindingResult, Model model,
            HttpServletResponse response) {

        // 展示页面
        if (personForm.getIsForm() == 0) {
            // 数据字典：性别
            List<Dictionary> dicts = getDictionarys(DictionaryGroupKey.PERSON_SEX);
            model.addAttribute("dicts", dicts);

            return "backend/person/add";
        }

        JsonResult result = new JsonResult();

        if (bindingResult.hasErrors()) {
            result.getResult().put("error", bindingResult.getAllErrors());

            printJson(response, result);

            return null;
        }

        serviceLocator.getPersonService().insert(personForm);

        result.setFlag(1);
        result.setMsg("添加成功！");

        printJson(response, result);

        return null;
    }

    /**
     * 更新人员
     * 
     * @param photo
     * @param personForm
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/person/update", method = RequestMethod.POST)
    public String update(Model model, String id, @RequestPart(value = "photo", required = false) byte[] photo,
            @Valid @ModelAttribute("personForm") PersonForm personForm, BindingResult bindingResult,
            HttpServletResponse response) {

        // 展示页面
        if (personForm.getIsForm() == 0) {
            // 数据字典：性别
            List<Dictionary> dicts = getDictionarys(DictionaryGroupKey.PERSON_SEX);
            model.addAttribute("dicts", dicts);

            Person person = serviceLocator.getPersonService().findById(personForm.getId());
            model.addAttribute("result", person);

            return "backend/person/update";
        }

        JsonResult result = new JsonResult();

        if (bindingResult.hasErrors()) {
            result.getResult().put("error", bindingResult.getAllErrors());

            printJson(response, result);

            return null;
        }

        // 乐观锁
        if (personForm.getHoldLock() == null) {
            result.getResult().put("error", "未持有锁，无法更新！");

            printJson(response, result);

            return null;
        }

        personForm.setPhoto(Base64Utils.encodeToString(photo));

        int count = serviceLocator.getPersonService().update(personForm);

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
    @RequestMapping(value = "/person/detail", method = RequestMethod.POST)
    public String detail(Model model, String id, HttpServletResponse response) {

        JsonResult result = new JsonResult();
        if (!StringUtil.isDigit(id)) {
            result.setFlag(0);
            result.setMsg("id 参数不正确！");

            printJson(response, result);

            return null;
        }

        Person person = serviceLocator.getPersonService().findById(Long.parseLong(id));

        if (person != null) {
            model.addAttribute("result", person);
        } else {
            result.setFlag(0);
            result.setMsg("未找到相应的记录！");

            printJson(response, result);

            return null;
        }

        return "backend/person/detail";
    }

    /**
     * 根据ID删除人员
     * 
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/person/delete", method = RequestMethod.POST)
    public JsonResult delete(String id) {

        JsonResult result = new JsonResult();
        if (!StringUtil.isDigit(id)) {
            result.setFlag(0);
            result.setMsg("id 参数不正确！");

            return result;
        }

        int count = serviceLocator.getPersonService().deleteById(Long.parseLong(id));

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
    @RequestMapping(value = "/person/batchDelete", method = RequestMethod.POST)
    public JsonResult deleteByIds(String ids) {

        JsonResult result = new JsonResult();
        if (StringUtil.isNullOrEmpty(ids)) {
            result.setFlag(0);
            result.setMsg("ids 参数不正确！");

            return result;
        }

        String[] idArr = ids.split(",");

        int count = serviceLocator.getPersonService().deleteByIds(idArr);

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
    @RequestMapping(value = "/person/sortList", method = RequestMethod.POST)
    public String sortList(Model model, @ModelAttribute("searchForm") SearchForm searchForm) {

        List<Person> list = serviceLocator.getPersonService().selectByCondition(searchForm);
        model.addAttribute("list", list);

        return "backend/person/sortList";
    }

    /**
     * 结果排序
     * 
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/person/sort", method = RequestMethod.POST)
    public JsonResult sort(String ids) {

        JsonResult result = new JsonResult();
        if (StringUtil.isNullOrEmpty(ids)) {
            result.setFlag(0);
            result.setMsg("ids 参数不正确！");

            return result;
        }

        String[] idArr = ids.split(",");

        int count = serviceLocator.getPersonService().sortByIds(idArr);

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
