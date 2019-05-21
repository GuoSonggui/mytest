package com.tensquare.search.dao;

import com.tensquare.search.pojo.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * 文章dao接口
 * ElasticsearchRepository，拥有CRUD方法
 */
public interface ArticleDao extends ElasticsearchRepository<Article,String>{

    /**
     * 搜索
     */
    public Page<Article> findByTitleOrContentLike(String title, String content, Pageable pageable);
}
