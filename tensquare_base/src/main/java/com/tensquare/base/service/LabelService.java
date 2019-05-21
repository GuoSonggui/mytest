package com.tensquare.base.service;

import com.tensquare.base.dao.LabelDao;
import com.tensquare.base.pojo.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import util.IdWorker;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/**
 * 标签service
 */
@Service
public class LabelService {

    @Autowired
    private LabelDao labelDao;

    @Autowired
    private IdWorker idWorker;

    /**
     * 查询所有
     */
    public List<Label> findAll(){
        return labelDao.findAll();
    }

    /**
     * 查询一个
     */
    public Label findById(String id){
        return labelDao.findById(id).get();
    }

    /**
     * 添加
     */
    public void add(Label label){
        //设置id
        label.setId(idWorker.nextId()+"");
        labelDao.save(label);
    }

    /**
     * 修改
     */
    public void update(Label label){    // label必须带数据库存在的id
        labelDao.save(label);
    }

    /**
     * 删除
     */
    public void deleteById(String id){
        labelDao.deleteById(id);
    }

    /**
     * 创建Specification对象
     */
    private Specification<Label> createSpecification(Map searchMap){
        //提供Specification接口的匿名内部类实现
        return new Specification<Label>() {
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

                // 需求：sql语句： where labelname like '%xxx%' and state = 'xxx' and count = xxx

                //1.设计一个List集合，用于Predicate条件对象
                List<Predicate> list = new ArrayList<Predicate>();

                //2.根据用户输入条件，构造Predicate条件对象，且放入List集合中*****
                if( searchMap.get("labelname")!=null && !"".equals(searchMap.get("labelname")) ){
                    // labelname like '%xxx%'
                    list.add( cb.like( root.get("labelname").as(String.class) , "%"+searchMap.get("labelname")+"%" ) );
                }
                if( searchMap.get("state")!=null && !"".equals(searchMap.get("state")) ){
                    // state = '1'
                    list.add( cb.equal( root.get("state").as(String.class) , searchMap.get("state") ) );
                }
                if( searchMap.get("count")!=null ){
                    // count = 20
                    list.add( cb.equal( root.get("count").as(Long.class) , searchMap.get("count") ) );
                }

                //3.使用连接条件进行Predicate条件对象连接
                /**
                 * list.toArray():  把list集合的每个元素取出，创建一个新的Object数组 ，把元素放入Object数组里面，返回Object数组
                 * list.toArray(preArray)： 把list集合的每个元素取出，把元素直接放入preArray数组里面，返回preArray数组
                 */
                //where labelname like '%xxx%' and state = 'xxx' and count = xxx
                Predicate[] preArray = new Predicate[list.size()];
                return cb.and(list.toArray(preArray));
            }
        };
    }

    /**
     * 动态条件查询
     */
    public List<Label> findSearch(Map searchMap){
        //Specification: 封装动态查询条件对象
        Specification<Label> spec = createSpecification(searchMap);
        return labelDao.findAll(spec);
    }

    /**
     * 带条件分页查询
     */
    public Page<Label> findSearch(Map searchMap, int page, int size){
        Specification<Label> spec = createSpecification(searchMap);
        //Pageable接口：封装分页查询所需参数  例如 当前页码，页面大小       注意：在Pageable接口的page参数，数值从0开始的
        return labelDao.findAll(spec,PageRequest.of(page-1,size));
    }
}
