package com.ruban.rbac.backend.resource;

import java.util.ArrayList;
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
import org.springframework.util.Base64Utils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ruban.common.dict.CommonState;
import com.ruban.common.dict.ResourceType;
import com.ruban.common.dict.YesNo;
import com.ruban.common.domain.Dictionary;
import com.ruban.common.domain.Resource;
import com.ruban.common.service.IResourceService;
import com.ruban.common.vo.ResourceVo;
import com.ruban.framework.core.spring.SpringContext;
import com.ruban.framework.core.utils.commons.DateUtil;
import com.ruban.framework.core.utils.commons.StringUtil;
import com.ruban.framework.web.page.JsonResult;
import com.ruban.rbac.backend.BackendController;
import com.ruban.rbac.backend.ZTreeNode;
import com.ruban.rbac.backend.resource.form.ResourceMapping;
import com.ruban.rbac.backend.resource.form.ResourceForm;
import com.ruban.rbac.backend.resource.form.SearchForm;

/**
 * 资源管理
 * 
 * @author yjwang
 *
 */
@Controller
public class ResourceController extends BackendController {

    private static final Logger logger = LoggerFactory.getLogger(ResourceController.class);

    @Autowired
    private IResourceService resourceService;

    /**
     * 资源管理主界面
     * 
     * @param model
     * @return
     */
    @RequestMapping(ResourceMapping.URI_MAIN)
    public String main(Model model) {

        SearchForm searchForm = new SearchForm();
        search(model, searchForm);

        return ResourceMapping.PAGE_MAIN;
    }

    /**
     * 列表查询资源
     * 
     * @param model
     * @param searchForm
     * @return
     */
    @RequestMapping(value = ResourceMapping.URI_SEARCH, method = RequestMethod.POST)
    public String search(Model model, @ModelAttribute("searchForm") SearchForm searchForm) {

        // 预置数据
        prepareData(model);

        List<Resource> list = resourceService.selectByCondition(searchForm);

        List<Resource> items = new ArrayList<>();

        int maxWidth = 0;
        // 处理数据,用于前端显示
        for (Resource res : list) {
            ResourceForm resForm = new ResourceForm();
            BeanUtils.copyProperties(res, resForm);
            resForm.setIndent(String.valueOf(res.getLevel() * 20));
            items.add(resForm);
            maxWidth += resForm.getName().length();
        }

        model.addAttribute("result", items);
        model.addAttribute("width", maxWidth * 3.5);

        return ResourceMapping.PAGE_LIST;
    }

    /**
     * 获取资源树列表
     * 
     * @param model
     * @return
     */
    @RequestMapping(value = ResourceMapping.URI_GET_TREE, method = RequestMethod.POST)
    public String getTree(Model model, @ModelAttribute("searchForm") SearchForm searchForm) {

        // 预置数据
        prepareData(model);

        // 授权标识为可授权
        searchForm.setDisplayFlag(1);
        List<Resource> list = resourceService.selectByCondition(searchForm);

        List<Resource> items = new ArrayList<>();

        int maxWidth = 0;
        // 处理数据,用于前端显示
        for (Resource res : list) {
            ResourceForm resForm = new ResourceForm();
            BeanUtils.copyProperties(res, resForm);
            resForm.setIndent(String.valueOf(res.getLevel() * 20));
            items.add(resForm);
            maxWidth += resForm.getName().length();
        }

        model.addAttribute("result", items);
        model.addAttribute("width", maxWidth * 3.5);

        return ResourceMapping.PAGE_TREE;
    }

    /**
     * 跳转到添加页面
     * 
     * @param model
     * @return
     */
    @RequestMapping(value = ResourceMapping.URI_ADD_PAGE, method = RequestMethod.POST)
    public String addPage(Model model) {

        // 预置数据
        prepareData(model);

        return ResourceMapping.PAGE_ADD;

    }

    /**
     * 添加资源
     * 
     * @param resourceForm
     * @param bindingResult
     * @return
     */
    @ResponseBody
    @RequestMapping(value = ResourceMapping.URI_ADD_SAVE, method = RequestMethod.POST)
    public JsonResult add(@RequestPart(value = "icon", required = false) byte[] icon,
            @Valid @ModelAttribute("resourceForm") ResourceForm resourceForm, BindingResult bindingResult) {

        JsonResult result = new JsonResult();

        // 校验表单是否错误
        if (!checkForm(result, bindingResult)) {
            return result;
        }

        // 检查图标是否上传
        if (icon != null) {
            String iconBase64 = Base64Utils.encodeToString(icon);
            resourceForm.setIcon(iconBase64);
        }

        // 设置添加人
        resourceForm.setAddUserId(1L);

        // 设置修改人
        resourceForm.setModUserId(1L);

        resourceService.insert(resourceForm);

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
    @RequestMapping(value = ResourceMapping.URI_UPDATE_PAGE, method = RequestMethod.POST)
    public String updatePage(Model model, Long id) {

        // 预置数据
        prepareData(model);

        Resource resource = resourceService.findById(id);
        model.addAttribute("result", resource);

        return ResourceMapping.PAGE_UPDATE;
    }

    /**
     * 更新操作
     * 
     * @param model
     * @param resourceForm
     * @param bindingResult
     * @return
     */
    @ResponseBody
    @RequestMapping(value = ResourceMapping.URI_UPDATE_SAVE, method = RequestMethod.POST)
    public JsonResult update(@RequestPart(value = "icon", required = false) byte[] icon, Model model,
            @Valid @ModelAttribute("resourceForm") ResourceForm resourceForm, BindingResult bindingResult) {

        JsonResult result = new JsonResult();

        // 校验表单是否错误
        if (!checkForm(result, bindingResult)) {
            return result;
        }

        // 乐观锁
        if (resourceForm.getHoldLock() == null) {
            result.getResult().put("error", SpringContext.getText("holdLockNo"));
            return result;
        }

        // 图标
        if (icon != null) {
            String base64 = Base64Utils.encodeToString(icon);
            resourceForm.setIcon("data:image/png;base64," + base64);
        }

        // 设置修改人
        resourceForm.setModUserId(1L);
        resourceForm.setModTime(DateUtil.getNowTime());

        int count = resourceService.update(resourceForm);

        if (count > 0) {
            wrapSuccess(result, SpringContext.getText("updateOk"));
        } else {
            wrapError(result, SpringContext.getText("updateNoLock"));
        }

        return result;
    }

    /**
     * 根据ID获取数据
     * 
     * @param id
     * @return
     */
    @RequestMapping(value = ResourceMapping.URI_DETAIL, method = RequestMethod.POST)
    public String detail(Model model, String id, HttpServletResponse response) {

        JsonResult result = new JsonResult();

        if (!checkId(id)) {

            wrapError(result, SpringContext.getText("paramError", "id"));

            printJson(response, result);

            return null;
        }

        Resource resource = resourceService.findById(Long.parseLong(id));

        if (resource != null) {

            // 预置数据
            prepareData(model);

            model.addAttribute("result", resource);

            return ResourceMapping.PAGE_DETAIL;

        } else {
            wrapError(result, SpringContext.getText("noResult"));
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
    @RequestMapping(value = ResourceMapping.URI_DELETE, method = RequestMethod.POST)
    public JsonResult delete(String id) {

        JsonResult result = new JsonResult();
        
        if (!checkId(id)) {
            wrapError(result, SpringContext.getText("paramError", "id"));

            return result;
        }

        int count = resourceService.deleteById(Long.parseLong(id));

        // 校验删除结果
        checkDelete(result, count);

        return result;
    }

    /**
     * 根据ID启用资源
     * 
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = ResourceMapping.URI_ENABLE, method = RequestMethod.POST)
    public JsonResult enable(String id) {

        JsonResult result = new JsonResult();
        
        if (!checkId(id)) {
            wrapError(result, SpringContext.getText("paramError", "id"));

            return result;
        }

        resourceService.enable(Long.parseLong(id));

        wrapSuccess(result, "启用成功");

        return result;
    }

    /**
     * 根据ID禁用资源
     * 
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = ResourceMapping.URI_UNABLE, method = RequestMethod.POST)
    public JsonResult unable(String id) {

        JsonResult result = new JsonResult();
        if (!checkId(id)) {
            wrapError(result, SpringContext.getText("paramError", "id"));

            return result;
        }

        resourceService.unable(Long.parseLong(id));

        wrapSuccess(result, "禁用成功");

        return result;
    }

    /**
     * 根据ID批量删除数据
     * 
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = ResourceMapping.URI_BATCH_DELETE, method = RequestMethod.POST)
    public JsonResult deleteByIds(String ids) {

        JsonResult result = new JsonResult();
        if (!checkId(ids)) {
            wrapError(result, SpringContext.getText("paramError", "ids"));

            return result;
        }

        String[] idArr = ids.split(",");

        int count = resourceService.deleteByIds(idArr);

        checkDelete(result, count);

        return result;
    }

    /**
     * 排序查询数据
     * 
     * @return
     */
    @RequestMapping(value = ResourceMapping.URI_SORT_LIST, method = RequestMethod.POST)
    public String sortList(Model model, @ModelAttribute("searchForm") SearchForm searchForm) {

        // 预置数据
        prepareData(model);

        List<Resource> list = resourceService.selectByCondition(searchForm);

        List<Resource> items = new ArrayList<>();

        int maxWidth = 0;
        // 处理数据,用于前端显示
        for (Resource res : list) {
            ResourceForm resForm = new ResourceForm();
            BeanUtils.copyProperties(res, resForm);
            resForm.setIndent(String.valueOf(res.getLevel() * 20));
            items.add(resForm);
            maxWidth += resForm.getName().length();
        }

        model.addAttribute("result", items);
        model.addAttribute("width", maxWidth * 3.5);

        return ResourceMapping.PAGE_SORT_LIST;
    }

    /**
     * 资源树结构
     * 
     * @param parentId
     * @return
     */
    @RequestMapping(value = ResourceMapping.URI_GET_JSON_TREE, method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public List<ZTreeNode> getJsonTree(Long parentId) {
        SearchForm searchForm = new SearchForm();

        List<Resource> list = resourceService.selectByCondition(searchForm);

        List<ZTreeNode> ztree = new ArrayList<>();

        // 构建组织机构树
        for (int i = 0; list != null && i < list.size(); i++) {
            Resource resource = list.get(i);

            ZTreeNode node = new ZTreeNode();
            node.setId(String.valueOf(resource.getId()));
            node.setpId(String.valueOf(resource.getParentId()));
            node.setName(resource.getName());

            if (resource.getParentId() != 0) {
                node.setpId(String.valueOf(resource.getParentId()));
            }

            if (resource.getChildrenNum() > 0) {
                node.setOpen(true);
            }
            ztree.add(node);
        }

        return ztree;
    }

    /**
     * 结果排序
     * 
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = ResourceMapping.URI_SORT, method = RequestMethod.POST)
    public JsonResult sort(String ids, String levels, String pathIds, String parentIds) {

        JsonResult result = new JsonResult();
        // 排序ids为空
        if (StringUtil.isNullOrEmpty(ids)) {
            wrapError(result, SpringContext.getText("paramError", "ids"));

            return result;
        }

        // 排序levels为空
        if (StringUtil.isNullOrEmpty(levels)) {
            wrapError(result, SpringContext.getText("paramError", "levels"));

            return result;
        }

        String[] idArr = ids.split(",");
        String[] levelArr = levels.split(",");
        String[] pathIdArr = pathIds.split(",");
        String[] parentArr = parentIds.split(",");

        // 更新排序相关字段
        int count = resourceService.sortByIds(idArr, levelArr, pathIdArr, parentArr);

        // 更新子节点数量
        for (String id : idArr) {
            ResourceVo vo = new ResourceVo();
            vo.setId(Long.parseLong(id));
            resourceService.updateChildrenNum(vo);
        }

        if (count == idArr.length) {
            wrapSuccess(result, SpringContext.getText("sortOk"));
        } else {
            wrapError(result, SpringContext.getText("sortError"));
        }

        return result;
    }

    /**
     * 预置数据(保存数据字典等)
     * 
     * @param model
     */
    protected void prepareData(Model model) {

        // 资源类型：map
        Map<String, Dictionary> typeMap = getDictionaryMap(ResourceType.KEY);
        model.addAttribute("typeMap", typeMap);
        
        // 资源类型：list
        List<Dictionary> types = getDictionarys(ResourceType.KEY);
        model.addAttribute("types", types);

        // 是否授权：map
        Map<String, Dictionary> yesnoMap = getDictionaryMap(YesNo.KEY);
        model.addAttribute("yesnoMap", yesnoMap);
        
        // 是否授权：list
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
