package com.ruban.fenxiang.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ruban.fenxiang.domain.Article;
import com.ruban.fenxiang.service.IArticleService;
import com.ruban.framework.core.utils.commons.DateUtil;

@Controller
public class ArticleController {

    @Autowired
    private IArticleService articleService;

    @RequestMapping("/article/add")
    @ResponseBody
    public String addArticle(@RequestBody Article article, String accessToken) {

        article.setCreateTime(DateUtil.getToday());
        
        articleService.add(article);
        
        return "{\"id\":\""+article.getId()+"\"}";
    }

    @RequestMapping("/article/{id}")
    public String getArticle(@PathVariable String id, Model model) {

        List<Article> result = articleService.findList("");

        model.addAttribute("list", result);

        return "index";
    }
}
