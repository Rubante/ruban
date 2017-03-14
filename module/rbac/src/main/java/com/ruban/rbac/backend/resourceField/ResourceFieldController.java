package com.ruban.rbac.backend.resourceField;

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
import com.ruban.common.domain.ResourceField;
import com.ruban.framework.core.utils.commons.StringUtil;
import com.ruban.framework.web.page.JsonResult;
import com.ruban.rbac.backend.BackendController;
import com.ruban.rbac.backend.resourceField.form.ResourceFieldForm;
import com.ruban.rbac.backend.resourceField.form.SearchFieldForm;
import com.ruban.rbac.service.ServiceLocator;

/**
 * 资源属性管理
 * 
 * @author yjwang
 *
 */
@Controller
public class ResourceFieldController extends BackendController {

    Logger logger = LoggerFactory.getLogger(ResourceFieldController.class);

    @Autowired
    private ServiceLocator serviceLocator;

    /**
     * 主界面
     * 
     * @param model
     * @return
     */
    @RequestMapping("/resourceField/main")
    public String main(Model model) {

        SearchFieldForm searchForm = new SearchFieldForm();
        search(model, searchForm);

        return "backend/resourceField/list";
    }

    /**
     * 查询
     * 
     * @param model
     * @param searchFieldForm
     * @return
     */
    @RequestMapping(value = "/resourceField/search", method = RequestMethod.POST)
    public String search(Model model, @ModelAttribute("searchFieldForm") SearchFieldForm searchFieldForm) {

        // 预置数据
        prepareData(model);

        List<ResourceField> list = serviceLocator.getResourceFieldService().selectByCondition(searchFieldForm);

        model.addAttribute("list", list);

        model.addAttribute("resourceId", searchFieldForm.getResourceId());

        return "backend/resourceField/list";
    }

    /**
     * 跳转到添加页面
     * 
     * @param model
     * @return
     */
    @RequestMapping(value = "/resourceField/addPage", method = RequestMethod.POST)
    public String addPage(Model model) {

        // 预置数据
        prepareData(model);

        return "backend/resourceField/add";
    }

    /**
     * 添加人员
     * 
     * @param resourceForm
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/resourceField/addSave", method = RequestMethod.POST)
    public String add(@Valid @ModelAttribute("resourceForm") ResourceFieldForm resourceFieldForm,
            BindingResult bindingResult, HttpServletResponse response) {

        JsonResult result = new JsonResult();

        if (bindingResult.hasErrors()) {
            result.getResult().put("error", bindingResult.getAllErrors());

            printJson(response, result);

            return null;
        }

        serviceLocator.getResourceFieldService().insert(resourceFieldForm);

        result.setFlag(1);
        result.setMsg("添加成功！");

        printJson(response, result);

        return null;
    }

    /**
     * 跳转到更新页面
     * 
     * @param model
     * @param id
     * @return
     */
    @RequestMapping(value = "/resourceField/updatePage", method = RequestMethod.POST)
    public String updatePage(Model model, Long id) {

        // 预置数据
        prepareData(model);

        ResourceField resourceField = serviceLocator.getResourceFieldService().findById(id);
        model.addAttribute("result", resourceField);

        return "backend/resourceField/update";

    }

    /**
     * 更新数据
     * 
     * @param resourceFieldForm
     * @param bindingResult
     * @param response
     * @return
     */
    @RequestMapping(value = "/resourceField/updateSave", method = RequestMethod.POST)
    public String update(@Valid @ModelAttribute("resourceForm") ResourceFieldForm resourceFieldForm,
            BindingResult bindingResult, HttpServletResponse response) {

        JsonResult result = new JsonResult();

        if (bindingResult.hasErrors()) {
            result.getResult().put("error", bindingResult.getAllErrors());

            printJson(response, result);

            return null;
        }

        // 乐观锁
        if (resourceFieldForm.getHoldLock() == null) {
            result.getResult().put("error", "未持有锁，无法更新！");

            printJson(response, result);

            return null;
        }

        int count = serviceLocator.getResourceFieldService().update(resourceFieldForm);

        if (count > 0) {
            result.setFlag(1);
            result.setMsg("修改成功！");
        } else {
            result.setFlag(1);
            result.setMsg("修改失败，数据已发生变化，无法保存！");
        }
        printJson(response, result);

        return null;
    }

    /**
     * 根据ID获取数据
     * 
     * @param id
     * @return
     */
    @RequestMapping(value = "/resourceField/detail", method = RequestMethod.POST)
    public String detail(Model model, String id, HttpServletResponse response) {
        SearchFieldForm searchFieldForm = new SearchFieldForm();

        search(model, searchFieldForm);

        return "backend/resourceField/detail";
    }

    /**
     * 根据ID删除数据
     * 
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/resourceField/delete", method = RequestMethod.POST)
    public JsonResult delete(String id) {

        JsonResult result = new JsonResult();
        if (!StringUtil.isDigit(id)) {
            result.setFlag(0);
            result.setMsg("id 参数不正确！");

            return result;
        }

        int count = serviceLocator.getResourceFieldService().deleteById(Long.parseLong(id));

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
     * 根据ID批量删除数据
     * 
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/resourceField/batchDelete", method = RequestMethod.POST)
    public JsonResult deleteByIds(String ids) {

        JsonResult result = new JsonResult();
        if (StringUtil.isNullOrEmpty(ids)) {
            result.setFlag(0);
            result.setMsg("ids 参数不正确！");

            return result;
        }

        String[] idArr = ids.split(",");

        int count = serviceLocator.getResourceFieldService().deleteByIds(idArr);

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
    @RequestMapping(value = "/resourceField/sortList", method = RequestMethod.POST)
    public String sortList(Model model, @ModelAttribute("searchFieldForm") SearchFieldForm searchFieldForm) {

        return "backend/resourceField/sortList";
    }

    /**
     * 结果排序
     * 
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/resourceField/sort", method = RequestMethod.POST)
    public JsonResult sort(String ids) {

        JsonResult result = new JsonResult();
        if (StringUtil.isNullOrEmpty(ids)) {
            result.setFlag(0);
            result.setMsg("ids 参数不正确！");

            return result;
        }

        String[] idArr = ids.split(",");

        int count = serviceLocator.getResourceFieldService().sortByIds(idArr);

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
     * 预备数据
     * 
     * @param model
     */
    protected void prepareData(Model model) {
        // 是否授权：list
        List<Dictionary> yesnos = getDictionarys(DictionaryGroupKey.COMMON_YESNO);
        model.addAttribute("yesnos", yesnos);
    }
}
