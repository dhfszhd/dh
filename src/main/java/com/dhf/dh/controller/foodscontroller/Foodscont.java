package com.dhf.dh.controller.foodscontroller;

import com.dhf.dh.entity.Foods;
import com.dhf.dh.service.IFoodsSrvice;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class Foodscont {
    private static final Logger log = LoggerFactory.getLogger(Foodscont.class);
    //注入
    @Autowired
    private IFoodsSrvice foodsSrvice ;

    //添加食物

    @RequiresRoles(value = {"admin","keeper","finance"},logical= Logical.OR)//admin管理员keeper食堂管理.finance财务管理
    @RequestMapping(value = "/addfood")
    @ResponseBody
    public Map<String, String> addfood(Foods foods){

        log.debug("添加食物----传参>{}",foods);
        Map<String, String> map =new HashMap<>();
        log.debug("添加新的食物----㔻是mull下面>{}",foods);
        Integer insfood = foodsSrvice.insfood(foods);
        if (insfood==301){
            map.put("code","301");
            map.put("msg","添加失败,食物的名字不能为空");
            return map;
        }

        if (insfood==302){
            map.put("code","302");
            map.put("msg","添加失败,食物的单价错误");
            return map;
        }
        if (insfood==303){
            map.put("code","303");
            map.put("msg","添加失败,添加的食物已存在");
            return map;
        }
        if (insfood==200){
            map.put("code","200");
            map.put("msg","添加成功");
            return map;
        }
        map.put("code","400");
        map.put("msg","未知错误");
        return map;
    }
    //设置食物库存 单价 状态
    @RequiresRoles(value = {"admin","keeper","finance"},logical=Logical.OR)//admin管理员keeper食堂管理.finance财务管理
    @RequestMapping(value = "/upfoodpsd")
    @ResponseBody
    public Map<String, String> upfoodtk(Foods foods){
        Map<String, String> map = new HashMap<>();
        Integer upfoodall = foodsSrvice.upfoodall(foods);
        if (upfoodall==200){
            map.put("code","200");
            map.put("msg","修改成功,请返回刷新页面");
            return map;
        }
        if (upfoodall==300){
            map.put("code","300");
            map.put("msg","修改食物失败,ID不能为空");
            return map;
        }
        if (upfoodall==301){
            map.put("code","301");
            map.put("msg","修改食物失败,属性不能为空");
            return map;
        }

        map.put("code","400");
        map.put("msg","修改食物失败,未知错误");
        return map;
    }
    //查询食物 默认查询 true ,查询全部需要fooddisable属性带默认参数

    @RequestMapping(value = "/selfoods" ,method = RequestMethod.POST)
    @ResponseBody
    public List<Foods> selfoods (Foods foods){
        List<Foods> selfoods = foodsSrvice.selfoods(foods);
        return selfoods ;

    }

}
