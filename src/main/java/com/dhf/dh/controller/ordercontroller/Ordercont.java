package com.dhf.dh.controller.ordercontroller;


import com.dhf.dh.entity.Foods;
import com.dhf.dh.entity.Order;
import com.dhf.dh.entity.SelQuest;
import com.dhf.dh.entity.Users;
import com.dhf.dh.service.IOrderService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import net.sf.ehcache.search.expression.Or;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class Ordercont {

    private static final Logger log = LoggerFactory.getLogger(Ordercont.class);
    //自动注入 应该用构造方法注入 避免报错
    @Autowired
    private IOrderService orderService ;
    // 查询订单 按照名字 查询当天的订单
    @RequestMapping (value = "/selordbyusna" )
    @ResponseBody
    public List<Order> selordbyusna(HttpSession session){
        Users user = (Users) session.getAttribute("user");
        List<Order> selorbyusername = orderService.selorbyusername(user);
        if(selorbyusername==null||selorbyusername.size()==0){
            List<Order> list = new ArrayList<>();
            return list;
        }
        return selorbyusername;
    }

    //按条件查询所有订单信息
    @RequiresRoles(value = {"admin","keeper","finance"},logical= Logical.OR)//admin管理员keeper食堂管理.finance财务管理
    @RequestMapping(value = "/selordall" ,method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> selordall(SelQuest selQuest ){

        if (ObjectUtils.isEmpty(selQuest)){

            return null ;
        }
        Map<String, Object> map = new HashMap<>();
        Page<Object> pagehelp = PageHelper.startPage(selQuest.getPage(), selQuest.getRows());

        List<Order> selorderall = orderService.selorderall(selQuest);
        long total =  pagehelp.getTotal();
        map.put("rows",selorderall);
        map.put("total",total);
        return map ;
    }

    //添加购物车
    @RequiresRoles(value = {"ord","admin"},logical=Logical.OR)//普通用户和admin管理员
    @RequestMapping (value = "/addcart" ,method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String>addcart(Order order,HttpSession session){

        log.debug("购物车session---->添加购物车>{}",session.getAttribute("order"));
        Map<String, String> map = new HashMap<>();
        if(ObjectUtils.isEmpty(order)){
            map.put("code","400");
            map.put("msg","添加购物车失败,食物实例不能为空");
            return map;

        }
        if(order.getId()==null ||order.getId()<=0){
            map.put("code","401");
            map.put("msg","添加购物车失败,食物ID不能为空或者ID不正确");
            return map;

        }
        if(order.getFoodtotal()==null|| order.getFoodtotal()<=0){
            map.put("code","402");
            map.put("msg","添加购物车失败,食物数量不能为空或者数量正确");
            return map;

        }
        if (session.getAttribute("order")==null) {
            List<Order> ordlist = new ArrayList<>();
            ordlist.add(order) ;
            session.setAttribute("order",ordlist);
            map.put("code","200");
            map.put("msg","添加购物车成功");
            log.debug("购物车session---查看list>{}",ordlist);
            return map;
        }
        List<Order> orderlist= (List<Order>) session.getAttribute("order");
        for (int x = 0 ;x <orderlist.size(); x ++) {
            if (orderlist.get(x).getId() == order.getId()){
                orderlist.get(x).setFoodtotal(orderlist.get(x).getFoodtotal()+order.getFoodtotal());
                session.removeAttribute("order");
                session.setAttribute("order",orderlist);
                map.put("code","200");
                map.put("msg","添加购物车成功");
                return map;
            }
        }
        session.removeAttribute("order");
        orderlist.add(order);
        session.setAttribute("order",orderlist);
        log.debug("购物车session---查看list>{}",orderlist.get(0).getFoodtotal());

        map.put("code","200");
        map.put("msg","添加购物车成功");
        return map;

    }

    //清空购物车
    @RequiresRoles(value = {"ord","admin"},logical=Logical.OR)//普通用户和admin管理员
    @RequestMapping (value = "/clecart" ,method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> clecart(HttpSession session){
        Map<String, String> map = new HashMap<>();
        if (ObjectUtils.isEmpty(session.getAttribute("order"))){
            map.put("code","200");
            map.put("msg","购物车空空如也");
            return map;
        }
        session.removeAttribute("order");
        map.put("code","200");
        map.put("msg","已经清空购物车");
        return map;
    }

    //结算购物车
    @RequiresRoles(value = {"ord","admin"},logical=Logical.OR)//普通用户和admin管理员
    @RequestMapping(value = "/check" ,method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> check(HttpSession session){
        Map<String, String> map =new HashMap<>();
        Users user = (Users) session.getAttribute("user");
        if (user.getUserdisable().equals("false")){
            map.put("code","500");
            map.put("msg","账号被禁用");
            return map;
        }
        if (user.getChargeback()>=5){
            map.put("code","401");
            map.put("msg","取消订单次数过多,请下月在使用");
            return map;
        }
        if (ObjectUtils.isEmpty(session.getAttribute("order"))){
            map.put("code","400");
            map.put("msg","购物车空空如也");
            return map;
        }
        List<Order> orderList = (List<Order>) session.getAttribute("order");

        Integer addorder = orderService.addorder(orderList, user);

        if (addorder==300){
            map.put("code","300");
            map.put("msg","食物库存不足,结算失败");
            return map;
        }
        if (addorder==301){
            map.put("code","301");
            map.put("msg","余额不足,结算失败");
            return map;
        }if (addorder==400){
            map.put("code","402");
            map.put("msg","结算失败,未知错误");
            return map;
        }if (addorder==200){
            map.put("code","200");
            map.put("msg","结算成功");
            session.removeAttribute("order");
            return map;
        }

            map.put("code","403");
            map.put("msg","结算失败,未知错误");
            return map;

    }

    //查看购物车
    @RequiresRoles(value = {"ord","admin"},logical=Logical.OR)//普通用户和admin管理员
    @RequestMapping (value = "/selcart" ,method = RequestMethod.POST)
    @ResponseBody
    public List<Order> selcart(HttpSession session){
        if (ObjectUtils.isEmpty(session.getAttribute("order"))){
            List<Order> objectList = new ArrayList<>();
            return objectList;
        }
        List<Order> orderList = (List<Order>) session.getAttribute("order");
        List<Order> selcart = orderService.selcart(orderList);
        return selcart;
    }
    //实时食物销售数量
    @RequiresRoles(value = {"admin","keeper","finance"},logical=Logical.OR)//admin管理员keeper食堂管理.finance财务管理
    @RequestMapping(value = "/ondayorder" ,method = RequestMethod.POST)
    @ResponseBody
    public List<Order> ondayorder(){
        List<Order> orderList=orderService.ondayorder();
        return orderList;
    }
    //下载Excel今日订单
    @RequiresRoles(value = {"admin","keeper","finance"},logical= Logical.OR)//admin管理员keeper食堂管理.finance财务管理
    @RequestMapping(value = "/dayorderexcel")
    @ResponseBody
    public void dayorderexcel(HttpServletResponse response) throws IOException {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String daydate = simpleDateFormat.format(date);
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition","attachment; filename="+daydate+".xlsx");
        orderService.dayexcel(response);
    }
}
