package com.tensquare.search.service;

import com.tensquare.search.dao.ArticleDao;
import com.tensquare.search.pojo.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 * 文章service
 */
@Service
public class ArticleService {

    @Autowired
    private ArticleDao articleDao;

    /**
     * 搜索
     */
    public Page<Article> search(String keyword, int page, int size){
        /**
         * 使用命名查询+Pageable分页
         */
       //return articleDao.findByTitleLikeOrContentLike(keyword,keyword, PageRequest.of(page-1,size));

        //简化
        /**
         * 1）无论是一个条件还是多个条件，只有最后一个关键词是Equals的时候才可以省略，除此之外，最后一个关键词不能省
         * 2）如果在多个条件的时候，除了最后一个条件的关键词以外的条件，如果和最后一个条件的关键词一样，也可以省略，否则也不能省略
         */
        return articleDao.findByTitleOrContentLike(keyword,keyword, PageRequest.of(page-1,size));
    }
}
