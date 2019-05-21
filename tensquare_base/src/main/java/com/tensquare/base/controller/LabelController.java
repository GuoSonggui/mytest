package com.tensquare.base.controller;

import com.tensquare.base.pojo.Label;
import com.tensquare.base.service.LabelService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
/**
 * 标签Controller
 */
@RestController    // @RestController = @Controller + @ResponseBody
@RequestMapping("/label")
@CrossOrigin // 解决跨域问题
public class LabelController {

    @Autowired
    private LabelService labelService;


    /**
     * 查询所有
     */
    @RequestMapping(method = RequestMethod.GET)
    //@GetMapping
    public Result findAll(){
        return new Result(true, StatusCode.OK,"查询成功",labelService.findAll());
    }

    /**
     * 参数传递方法
     *     1）普通查询参数       /label?id=10
     *
     *           findById(@RequestParam("id") String id)   @RequestParam可以省略
     *
     *     2) 路径参数          /label/10
     *           findById(@PathVariable String id)  @PathVariable不能省略
     */
    /**
     * 查询一个
     */
    @RequestMapping(value = "/{id}" ,method = RequestMethod.GET)
    public Result findById(@PathVariable String id){
        System.out.println("调用了基础微服务的方法....findById...");
        return new Result(true,StatusCode.OK,"查询成功33333",labelService.findById(id));
    }

    /**
     * 添加
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Label label){  //@RequestBody: 把请求的json正文格式转换为Java对象
        labelService.add(label);
        return new Result(true,StatusCode.OK,"添加成功");
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public Result update(@PathVariable String id,@RequestBody Label label){
        label.setId(id);
        labelService.update(label);
        return new Result(true,StatusCode.OK,"修改成功");
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public Result delete(@PathVariable String id){
        labelService.deleteById(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }

    /**
     * 动态条件查询
     */
    @RequestMapping(value = "/search",method = RequestMethod.POST)
    public Result findSearch(@RequestBody Map searchMap){
        List<Label> list  =labelService.findSearch(searchMap);
        return new Result(true,StatusCode.OK,"查询成功",list);
    }

    /**
     * 带条件分页查询
     */
    @RequestMapping(value = "/search/{page}/{size}" ,method = RequestMethod.POST)
    public Result findSearch(@PathVariable int page,@PathVariable int size,@RequestBody Map searchMap){
        //Page： 封装分页查询后的结果。 例如 总记录数：totalElements , 当前页数据列表： content
        Page<Label> pageData = labelService.findSearch(searchMap,page,size);
        return new Result(true,StatusCode.OK,"查询成功",new PageResult<>(pageData.getTotalElements(),pageData.getContent()));
    }
}
