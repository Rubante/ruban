package com.ruban.rbac.backend;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ruban.common.domain.Dictionary;
import com.ruban.framework.core.utils.commons.JsonUtils;
import com.ruban.framework.web.page.JsonResult;
import com.ruban.rbac.service.ServiceLocator;

@RequestMapping("/rbac/backend")
public class BackendController {

    @Autowired
    private ServiceLocator serviceLocator;

    /**
     * 获取组织机构树
     * 
     * @return
     */
    public String getCompanyTree() {
        return serviceLocator.getCompanyService().getJsonTree(null);
    }

    /**
     * 获取部门树
     * 
     * @return
     */
    public String getDepartmentTree() {
        return serviceLocator.getDepartmentService().getJsonTree(null);
    }

    /**
     * 获取数据字典
     * 
     * @return
     */
    public List<Dictionary> getDictionarys(String groupKey) {
        // 数据字典：机构类型
        List<Dictionary> dicts = serviceLocator.getDictionaryService().selectByGroup(groupKey);

        return dicts;
    }

    /**
     * 获取数据字典
     * 
     * @return
     */
    public Map<String, Dictionary> getDictionaryMap(String groupKey) {
        // 数据字典：机构类型
        List<Dictionary> dicts = serviceLocator.getDictionaryService().selectByGroup(groupKey);

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

}
