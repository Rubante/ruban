package com.ruban.rbac.backend.person;

import java.util.List;
import java.util.Map;

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

import com.ruban.common.dict.Sex;
import com.ruban.common.domain.Dictionary;
import com.ruban.framework.core.spring.SpringContext;
import com.ruban.framework.dao.helper.ResultInfo;
import com.ruban.framework.web.page.JsonResult;
import com.ruban.rbac.backend.BackendController;
import com.ruban.rbac.backend.person.form.PersonForm;
import com.ruban.rbac.backend.person.form.PersonMapping;
import com.ruban.rbac.backend.person.form.SearchForm;
import com.ruban.rbac.base.Pagination;
import com.ruban.rbac.domain.organization.Person;
import com.ruban.rbac.service.IPersonService;

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
    private IPersonService personService;

    @RequestMapping(PersonMapping.URI_MAIN)
    public String main(Model model) {

        SearchForm searchForm = new SearchForm();
        search(model, searchForm);

        return PersonMapping.PAGE_MAIN;
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

    @RequestMapping(value = PersonMapping.URI_SEARCH, method = RequestMethod.POST)
    public String search(Model model, @ModelAttribute("searchForm") SearchForm searchForm) {

        // 预置数据
        prepareData(model);

        ResultInfo<Person> resultInfo = personService.selectByPage(searchForm);

        Pagination<Person> pagination = new Pagination<>();
        pagination.gereate(resultInfo);

        model.addAttribute("pagination", pagination);
        model.addAttribute("resultInfo", resultInfo);

        return PersonMapping.PAGE_LIST;
    }

    /**
     * 跳转到添加人员
     * 
     * @param model
     * @return
     */
    @RequestMapping(value = PersonMapping.URI_ADD_PAGE, method = RequestMethod.POST)
    public String addPage(Model model) {

        prepareData(model);

        return PersonMapping.PAGE_ADD;

    }

    /**
     * 添加人员
     * 
     * @param model
     * @param personForm
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = PersonMapping.URI_ADD_SAVE, method = RequestMethod.POST)
    public JsonResult add(@RequestPart(value = "photo", required = false) byte[] photo,
            @Valid @ModelAttribute("personForm") PersonForm personForm, BindingResult bindingResult, Model model,
            HttpServletResponse response) {

        JsonResult result = new JsonResult();

        // 校验表单是否错误
        if (!checkForm(result, bindingResult)) {
            return result;
        }

        // 检查照片是否上传
        if (photo != null) {
            String iconBase64 = Base64Utils.encodeToString(photo);
            personForm.setPhoto(iconBase64);
        }

        personService.insert(personForm);

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
    @RequestMapping(value = PersonMapping.URI_UPDATE_PAGE, method = RequestMethod.POST)
    public String updatePage(Model model, Long id) {

        // 预置数据
        prepareData(model);

        Person person = personService.findById(id);
        model.addAttribute("result", person);

        return PersonMapping.PAGE_UPDATE;
    }

    /**
     * 更新人员
     * 
     * @param model
     * @param id
     * @param photo
     * @param personForm
     * @param bindingResult
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = PersonMapping.URI_UPDATE_SAVE, method = RequestMethod.POST)
    public JsonResult update(Model model, String id, @RequestPart(value = "photo", required = false) byte[] photo,
            @Valid @ModelAttribute("personForm") PersonForm personForm, BindingResult bindingResult,
            HttpServletResponse response) {

        JsonResult result = new JsonResult();

        // 校验表单是否错误
        if (!checkForm(result, bindingResult)) {
            return result;
        }

        // 乐观锁
        if (personForm.getHoldLock() == null) {
            result.getResult().put("error", SpringContext.getText("holdLockNo"));

            return result;
        }

        // 检查照片是否上传
        if (photo != null) {
            String iconBase64 = Base64Utils.encodeToString(photo);
            personForm.setPhoto(iconBase64);
        }

        int count = personService.update(personForm);

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
     * @param model
     * @param id
     * @param response
     * @return
     */
    @RequestMapping(value = PersonMapping.URI_DETAIL, method = RequestMethod.POST)
    public String detail(Model model, String id, HttpServletResponse response) {

        JsonResult result = new JsonResult();

        // 校验Id是否正确
        if (!checkId(id)) {
            wrapError(result, SpringContext.getText("paramError", "id"));
            printJson(response, result);

            return null;
        }

        Person person = personService.findById(Long.parseLong(id));

        if (person != null) {
            // 预置数据
            prepareData(model);

            model.addAttribute("result", person);
        } else {
            wrapError(result, SpringContext.getText("noResult"));
            printJson(response, result);

            return null;
        }

        return PersonMapping.PAGE_DETAIL;
    }

    /**
     * 根据ID删除人员
     * 
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = PersonMapping.URI_DELETE, method = RequestMethod.POST)
    public JsonResult delete(String id) {

        JsonResult result = new JsonResult();

        // 校验Id是否正确
        if (!checkId(id)) {
            wrapError(result, SpringContext.getText("paramError", "id"));

            return result;
        }

        int count = personService.deleteById(Long.parseLong(id));

        // 校验删除结果
        checkDelete(result, count);

        return result;
    }

    /**
     * 根据ID批量删除人员
     * 
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = PersonMapping.URI_BATCH_DELETE, method = RequestMethod.POST)
    public JsonResult deleteByIds(String ids) {

        JsonResult result = new JsonResult();

        // 校验ids是否正确
        if (!checkId(ids)) {
            wrapError(result, SpringContext.getText("paramError", "id"));

            return result;
        }

        String[] idArr = ids.split(",");

        int count = personService.deleteByIds(idArr);

        if (count > 0) {
            wrapSuccess(result, "成功删除");
        } else {
            wrapError(result, "删除失败");
        }

        return result;
    }

    @Override
    protected void prepareData(Model model) {
        String companyTree = getCompanyTree();
        model.addAttribute("companyTree", companyTree);
        model.addAttribute("dptTree", getDepartmentTree());

        List<Dictionary> sexs = getDictionarys(Sex.KEY);
        model.addAttribute("sexs", sexs);

        Map<String, Dictionary> sexMap = getDictionaryMap(Sex.KEY);
        model.addAttribute("sexMap", sexMap);
    }

}
