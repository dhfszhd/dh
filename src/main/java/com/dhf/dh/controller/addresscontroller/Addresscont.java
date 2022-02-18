package com.dhf.dh.controller.addresscontroller;

import com.dhf.dh.entity.Address;
import com.dhf.dh.service.IAddressService;
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

//注释 控制器
@Controller
public class Addresscont {
    private static final Logger log = LoggerFactory.getLogger(Addresscont.class);
    @Autowired
    private IAddressService addressService;
    //查询取餐地点
    @RequestMapping(value = "/selall", method = RequestMethod.POST)
    @ResponseBody
    public List<Address> seleall(Address address){
        log.debug("取餐地点查询控制层",address);
        List<Address> selall = addressService.selall(address);
        return selall;
    }
    //添加新的取餐地点
    @RequiresRoles(value = {"admin","keeper"},logical=Logical.OR)//admin管理员keeper食堂管理
    @RequestMapping(value = "/insadd")
    @ResponseBody
    public Map<String,String> insadd(Address address){
        Map<String, String> map = new HashMap<String, String>();
        address.setAddressdisable("true");
        log.debug("添加地点控制层{}{}{}",address.getId(),address.getMealaddress(),address.getAddressdisable());

        Integer insadd = addressService.insadd(address);
        if (insadd==300){
            map.put("code","400");
            map.put("msg","已存在相同地点,添加失败");
            return map;
        }
        if (insadd==200){
            map.put("codo","200");
            map.put("msg","添加成功");
            return map ;
        }
        map.put("codo","400");
        map.put("msg","添加失败");
        return map;
    }
    //更新取餐地点的状态
    @RequiresRoles(value = {"admin","keeper"},logical= Logical.OR)//admin管理员keeper食堂管理
    @RequestMapping(value = "/upadd" ,method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> upadd(Address address){
        Map<String, String> map = new HashMap<String, String>();
        Integer upadd = addressService.upadd(address);
        if (upadd==200){
            map.put("code","200");
            map.put("msg","修改成功");
            return map;
        };
        map.put("code","400");
        map.put("msg","修改失败");
        return map;
    }

    }
