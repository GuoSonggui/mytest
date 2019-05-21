package com.tensquare.qa.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tensquare.qa.pojo.Problem;
import org.springframework.data.jpa.repository.Query;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface ProblemDao extends JpaRepository<Problem,String>,JpaSpecificationExecutor<Problem>{

    /**
     * 最新问答
     * 复杂的JPQL写法：1）先在SQL客户端工具写出复杂的SQL语句 2）把SQL语句翻译成JPQL语句
     *
     *     SQL语句：
     *
     *       -- 使用上面的结果，作为下面查询的条件（子查询）
                 SELECT * FROM tb_problem p WHERE p.id IN
                 (SELECT pl.problemid FROM tb_pl pl WHERE pl.labelid = '1113613223069798400')
                 ORDER BY p.replytime DESC;

           JPQL语句：把表名改成映射的类名，把表的字段名称改成类的属性名称
               select p FROM Problem p WHERE p.id IN
                    (SELECT pl.problemid FROM Pl pl WHERE pl.labelid = ?1)
                         ORDER BY p.replytime DESC
     *
     */
    @Query("select p FROM Problem p WHERE p.id IN" +
            "                    (SELECT pl.problemid FROM Pl pl WHERE pl.labelid = ?1)" +
            "                         ORDER BY p.replytime DESC")
    public Page<Problem> findByNewList(String labelid, Pageable pageable);

    /**
     * 热门问答
     * @param labelid
     * @param pageable
     * @return
     */
    @Query("select p FROM Problem p WHERE p.id IN" +
            "                    (SELECT pl.problemid FROM Pl pl WHERE pl.labelid = ?1)" +
            "                         ORDER BY p.reply DESC")
    public Page<Problem> findByHotList(String labelid, Pageable pageable);


    /**
     * 等待问答
     * @param labelid
     * @param pageable
     * @return
     */
    @Query("select p FROM Problem p WHERE p.id IN" +
            "                    (SELECT pl.problemid FROM Pl pl WHERE pl.labelid = ?1)" +
            "                         AND p.reply = 0 ORDER BY p.createtime DESC")
    public Page<Problem> findByWaitList(String labelid, Pageable pageable);
}
