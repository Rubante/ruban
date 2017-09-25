package com.ruban.rbac.backend;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ruban.common.domain.Dictionary;
import com.ruban.common.service.IDictionaryService;
import com.ruban.framework.core.spring.SpringContext;
import com.ruban.framework.core.utils.commons.JsonUtils;
import com.ruban.framework.core.utils.commons.StringUtil;
import com.ruban.framework.web.page.JsonResult;
import com.ruban.rbac.service.ICompanyService;
import com.ruban.rbac.service.IDepartmentService;

@RequestMapping("/rbac/backend")
public class BackendController {

    @Autowired
    private ICompanyService companyService;

    @Autowired
    private IDepartmentService departmentService;

    @Autowired
    private IDictionaryService dictionaryService;

    /**
     * 获取组织机构树
     * 
     * @return
     */
    public String getCompanyTree() {
        return companyService.getJsonTree(null, null);
    }

    /**
     * 获取组织机构树
     * 
     * @param type
     *            :组织机构类型
     * @return
     */
    public String getCompanyTree(String type) {
        return companyService.getJsonTree(null, type);
    }

    /**
     * 获取部门树
     * 
     * @return
     */
    public String getDepartmentTree() {
        return departmentService.getJsonTree(null);
    }

    /**
     * 获取数据字典
     * 
     * @return
     */
    public List<Dictionary> getDictionarys(String groupKey) {
        // 数据字典：机构类型
        List<Dictionary> dicts = dictionaryService.selectByGroup(groupKey);

        return dicts;
    }

    /**
     * 获取数据字典
     * 
     * @return
     */
    public Map<String, Dictionary> getDictionaryMap(String groupKey) {
        // 数据字典：机构类型
        List<Dictionary> dicts = dictionaryService.selectByGroup(groupKey);

        return getDictionaryMap(dicts);
    }

    /**
     * 数据字典map结构
     * 
     * @param dicts
     * @return
     */
    public Map<String, Dictionary> getDictionaryMap(List<Dictionary> dicts) {
        Map<String, Dictionary> dictMap = new HashMap<>();
        for (int i = 0; dicts != null && i < dicts.size(); i++) {
            Dictionary dictionary = dicts.get(i);
            dictMap.put(dictionary.getCode(), dictionary);
        }

        return dictMap;
    }

    /**
     * 输出json串
     * 
     * @param response
     * @param result
     */
    public void printJson(HttpServletResponse response, JsonResult result) {
        try {
            response.setHeader("Content-Type", "application/json");
            String json = JsonUtils.toJson(result);
            response.getWriter().print(json);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 预备数据
     * 
     * @param model
     */
    protected void prepareData(Model model) {
    }

    /**
     * 成功结果包装
     * 
     * @param result
     * @param msg
     */
    protected void wrapSuccess(JsonResult result, String msg) {

        result.setFlag(0);
        result.setMsg(msg);
    }

    /**
     * 失败结果包装
     * 
     * @param result
     * @param msg
     */
    protected void wrapError(JsonResult result, String msg) {

        result.setFlag(1);
        result.setMsg(msg);

    }

    /**
     * 校验数据id是否为数字<br />
     * 校验数据ids是否是,切割的数字<br />
     * 
     * @param id
     * @return
     */
    public boolean checkId(String id) {
        if (StringUtil.isNullOrEmpty(id)) {
            return false;
        }

        if (id.contains(",")) {
            String[] idArr = id.split(",");
            for (String idStr : idArr) {
                if (!StringUtil.isDigit(idStr)) {
                    return false;
                }
            }
        } else {
            if (!StringUtil.isDigit(id)) {
                return false;
            }
        }

        return true;
    }

    /**
     * 检查删除结果是否正常
     * 
     * @param result
     * @param count
     * @return
     */
    public void checkDelete(JsonResult result, int count) {

        if (count > 0) {
            wrapSuccess(result, SpringContext.getText("deleteOk"));
        } else {
            wrapError(result, SpringContext.getText("deleteError"));
        }
    }

    /**
     * 校验form绑定数据是否正确
     * 
     * @param result
     * @param bindingResult
     * @return
     */
    protected boolean checkForm(JsonResult result, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            result.getResult().put("error", bindingResult.getAllErrors());

            return false;
        }

        return true;
    }
}
