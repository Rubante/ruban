package com.ruban.fenxiang.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ruban.fenxiang.domain.Article;
import com.ruban.fenxiang.domain.Post;
import com.ruban.fenxiang.service.IArticleService;
import com.ruban.fenxiang.service.IPostService;
import com.ruban.framework.core.utils.commons.DateUtil;

@Controller
public class ArticleController {

    @Autowired
    private IArticleService articleService;

    @Autowired
    private IPostService postService;

    @RequestMapping("/article/add")
    @ResponseBody
    public String addArticle(@RequestBody Article article, String accessToken) {

        article.setCreateTime(DateUtil.getToday());

        articleService.add(article);

        return "{\"id\":\"" + article.getId() + "\"}";
    }

    @RequestMapping(value = "/article/{id}", method = RequestMethod.GET)
    public String getArticle(@PathVariable String id, Model model) {

        List<Article> result = articleService.findList("");

        model.addAttribute("list", result);

        return "index";
    }

    @RequestMapping(value = "/article/{articleId}", method = RequestMethod.POST)
    public String post(@ModelAttribute Post post, @PathVariable String articleId) {

        post.setCreateDate(DateUtil.getToday());

        postService.add(post);

        return "index";
    }
}
