package com.tensquare.spit.service;

import com.tensquare.spit.dao.SpitDao;
import com.tensquare.spit.pojo.Spit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import util.IdWorker;

import java.util.List;

/**
 * 吐槽的service
 */
@Service
public class SpitService {
    @Autowired
    private SpitDao spitDao;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private IdWorker idWorker;

    /**
     * 查询所有
     */
    public List<Spit> findAll() {
        return spitDao.findAll();
    }

    /**
     * 根据id查询
     */
    public Spit findById(String id) {
        return spitDao.findById(id).get();
    }

    /**
     * 发布新的吐槽
     */
    public void add(Spit spit) {
        //设置id
        spit.setId(idWorker.nextId() + "");
        spitDao.save(spit);

        //当用户添加评论的时候，评论对应的吐槽的回复说+1
        if (spit.getParentid() != null && !"".equals(spit.getParentid())) {
            //评论
            Query query = new Query();
            query.addCriteria(Criteria.where("_id").is(spit.getParentid()));
            Update update = new Update();
            update.inc("comment", 1);

            mongoTemplate.updateFirst(query, update, "spit");
        }
    }

    /**
     * 修改
     */
    public void update(Spit spit) {
        spitDao.save(spit);
    }


    /**
     * 根据id删除
     */
    public void deleteById(String id) {
        spitDao.deleteById(id);
    }


    /**
     * 根据上级 ID 查询吐槽列表
     *
     * @param parentid
     * @param page
     * @param size
     * @return
     */
    public Page<Spit> findByParentid(String parentid, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return spitDao.findByParentid(parentid, pageRequest);
    }

    /**
     * 点赞
     *
     * @param id $inc
     */
    public void updateThumbup(String id) {
        // db.spit.update({_id:"1114481124920283136" },{$inc:{"thumbup":NumberInt(1)  }  } )
        //_id:"1114481124920283136"
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        Update update = new Update();
        //$inc:{"thumbup":NumberInt(1)  }
        Update thumbup = update.inc("thumbup", 1);
        //db.spit.update
        mongoTemplate.updateFirst(query, update, "spit");
    }
}
