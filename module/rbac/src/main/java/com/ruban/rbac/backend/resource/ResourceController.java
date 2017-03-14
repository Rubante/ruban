package com.ruban.rbac.backend.resource;

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
import com.ruban.common.domain.Resource;
import com.ruban.framework.core.utils.commons.StringUtil;
import com.ruban.framework.dao.helper.ResultInfo;
import com.ruban.framework.web.page.JsonResult;
import com.ruban.rbac.backend.BackendController;
import com.ruban.rbac.backend.resource.form.ResourceForm;
import com.ruban.rbac.backend.resource.form.SearchForm;
import com.ruban.rbac.base.Pagination;
import com.ruban.rbac.service.ServiceLocator;

/**
 * 资源管理
 * 
 * @author yjwang
 *
 */
@Controller
public class ResourceController extends BackendController {

    Logger logger = LoggerFactory.getLogger(ResourceController.class);

    @Autowired
    private ServiceLocator serviceLocator;

    /**
     * 主界面
     * 
     * @param model
     * @return
     */
    @RequestMapping("/resource/main")
    public String main(Model model) {

        SearchForm searchForm = new SearchForm();
        search(model, searchForm);

        return "backend/resource/main";
    }

    /**
     * 查询
     * 
     * @param model
     * @param searchForm
     * @return
     */
    @RequestMapping(value = "/resource/search", method = RequestMethod.POST)
    public String search(Model model, @ModelAttribute("searchForm") SearchForm searchForm) {

        // 预置数据
        prepareData(model);

        ResultInfo<Resource> resultInfo = serviceLocator.getResourceService().selectByPage(searchForm);

        Pagination<Resource> pagination = new Pagination<>();
        pagination.gereate(resultInfo);

        model.addAttribute("pagination", pagination);
        model.addAttribute("resultInfo", resultInfo);

        return "backend/resource/list";
    }

    /**
     * 跳转到添加页面
     * 
     * @param model
     * @return
     */
    @RequestMapping(value = "/resource/addPage", method = RequestMethod.POST)
    public String addPage(Model model) {

        // 预置数据
        prepareData(model);

        return "backend/resource/add";

    }

    /**
     * 添加资源
     * 
     * @param resourceForm
     * @param bindingResult
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/resource/addSave", method = RequestMethod.POST)
    public JsonResult add(@Valid @ModelAttribute("resourceForm") ResourceForm resourceForm,
            BindingResult bindingResult) {

        JsonResult result = new JsonResult();

        if (bindingResult.hasErrors()) {
            result.getResult().put("error", bindingResult.getAllErrors());

            return result;
        }

        serviceLocator.getResourceService().insert(resourceForm);

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
    @RequestMapping(value = "/resource/updatePage", method = RequestMethod.POST)
    public String updatePage(Model model, Long id) {

        // 预置数据
        prepareData(model);

        Resource resource = serviceLocator.getResourceService().findById(id);
        model.addAttribute("result", resource);

        return "backend/resource/update";
    }

    /**
     * 更新数据
     * 
     * @param resourceForm
     * @param bindingResult
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/resource/updateSave", method = RequestMethod.POST)
    public JsonResult update(@Valid @ModelAttribute("resourceForm") ResourceForm resourceForm,
            BindingResult bindingResult) {

        JsonResult result = new JsonResult();

        if (bindingResult.hasErrors()) {
            result.getResult().put("error", bindingResult.getAllErrors());

            return result;
        }

        // 乐观锁
        if (resourceForm.getHoldLock() == null) {
            result.getResult().put("error", "未持有锁，无法更新！");

            return result;
        }

        int count = serviceLocator.getResourceService().update(resourceForm);

        if (count > 0) {
            result.setFlag(1);
            result.setMsg("修改成功！");
        } else {
            result.setFlag(1);
            result.setMsg("修改失败，数据已发生变化，无法保存！");
        }

        return result;
    }

    /**
     * 根据ID获取数据
     * 
     * @param id
     * @return
     */
    @RequestMapping(value = "/resource/detail", method = RequestMethod.POST)
    public String detail(Model model, String id, HttpServletResponse response) {

        JsonResult result = new JsonResult();

        if (!StringUtil.isDigit(id)) {
            result.setFlag(0);
            result.setMsg("id 参数不正确！");

            printJson(response, result);

            return null;
        }

        Resource resource = serviceLocator.getResourceService().findById(Long.parseLong(id));

        if (resource != null) {

            // 预置数据
            prepareData(model);

            model.addAttribute("result", resource);

            return "backend/resource/detail";

        } else {
            result.setFlag(0);
            result.setMsg("未找到相应的记录！");

            printJson(response, result);

            return null;
        }
    }

    /**
     * 根据ID删除数据
     * 
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/resource/delete", method = RequestMethod.POST)
    public JsonResult delete(String id) {

        JsonResult result = new JsonResult();
        if (!StringUtil.isDigit(id)) {
            result.setFlag(0);
            result.setMsg("id 参数不正确！");

            return result;
        }

        int count = serviceLocator.getResourceService().deleteById(Long.parseLong(id));

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
    @RequestMapping(value = "/resource/batchDelete", method = RequestMethod.POST)
    public JsonResult deleteByIds(String ids) {

        JsonResult result = new JsonResult();
        if (StringUtil.isNullOrEmpty(ids)) {
            result.setFlag(0);
            result.setMsg("ids 参数不正确！");

            return result;
        }

        String[] idArr = ids.split(",");

        int count = serviceLocator.getResourceService().deleteByIds(idArr);

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
    @RequestMapping(value = "/resource/sortList", method = RequestMethod.POST)
    public String sortList(Model model, @ModelAttribute("searchForm") SearchForm searchForm) {

        // 预置数据
        prepareData(model);

        return "backend/resource/sortList";
    }

    /**
     * 结果排序
     * 
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/resource/sort", method = RequestMethod.POST)
    public JsonResult sort(String ids) {

        JsonResult result = new JsonResult();
        if (StringUtil.isNullOrEmpty(ids)) {
            result.setFlag(0);
            result.setMsg("ids 参数不正确！");

            return result;
        }

        String[] idArr = ids.split(",");

        int count = serviceLocator.getResourceService().sortByIds(idArr);

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
        // 资源类型：map
        Map<String, Dictionary> typeMap = getDictionaryMap(DictionaryGroupKey.RESOURCE_TYPE);
        model.addAttribute("typeMap", typeMap);

        // 是否授权：map
        Map<String, Dictionary> yesnoMap = getDictionaryMap(DictionaryGroupKey.COMMON_YESNO);
        model.addAttribute("yesnoMap", yesnoMap);

        // 资源类型：list
        List<Dictionary> types = getDictionarys(DictionaryGroupKey.RESOURCE_TYPE);
        model.addAttribute("types", types);

        // 是否授权：list
        List<Dictionary> yesnos = getDictionarys(DictionaryGroupKey.COMMON_YESNO);
        model.addAttribute("yesnos", yesnos);
    }
}
