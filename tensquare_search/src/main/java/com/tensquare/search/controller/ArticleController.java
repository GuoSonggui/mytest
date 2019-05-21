package com.tensquare.search.controller;

import com.tensquare.search.pojo.Article;
import com.tensquare.search.service.ArticleService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * 文章Controller
 */
@RestController
@RequestMapping("/article")
@CrossOrigin
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     * 搜索
     */
    @RequestMapping(value = "/search/{keyword}/{page}/{size}",method = RequestMethod.GET)
    public Result search(@PathVariable String keyword,@PathVariable int page, @PathVariable int size){
        Page<Article> pageData = articleService.search(keyword,page,size);
        return new Result(true, StatusCode.OK,"搜索成功",new PageResult<>(pageData.getTotalElements(),pageData.getContent()));
    }

}
