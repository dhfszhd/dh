package com.dhf.dh.service.imp;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.dhf.dh.entity.*;
import com.dhf.dh.mapper.FoodsMapper;
import com.dhf.dh.mapper.OrderMapper;
import com.dhf.dh.mapper.UsersMapper;
import com.dhf.dh.service.IOrderService;
import org.apache.catalina.User;
import org.apache.ibatis.ognl.ListPropertyAccessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements IOrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);
    //自动注入 应该使用构造方法注入避免报错
    @Autowired
    private OrderMapper orderMapper ;
    @Autowired
    private UsersMapper usersMapper ;
    @Autowired
    private FoodsMapper foodsMapper ;


    //按照账号查询当天的订单
    @Override
    public List<Order> selorbyusername(Users users ) {
        if (users.getUsername()==null|| "".equals(users.getUsername())){
            List<Order> list = new ArrayList<>();
            return list ;
        }
        List<Order> selorbyusername = orderMapper.selorbyusername(users);
        return selorbyusername;


    }

    //按照条件查询素有订单
    @Override
    public List<Order> selorderall(SelQuest selQuest) {
        if (ObjectUtils.isEmpty(selQuest)){
            return null;
        }
        List<Order> selorderall = orderMapper.selorderall(selQuest);
        return selorderall;
    }


    //添加订单 下单
    //添加事务
    @Transactional
    @Override
    public Integer addorder(List<Order> orderList, Users users) {
        //设置订单的总金额
        boolean foodtotal =true ;
        Integer total = 0;
        //按照账号密码重新查询 当前用户的信息
        Users byusps = usersMapper.selByusps(users);
        log.debug("结算购物车---通过密码账号查询用户--->{}",byusps.toString());
        //按照订单的ID(食物ID)查询食物库存 如果食物的库存<=0.或者库存 数量小于订购数量,直接返回300 订购失败

        for (int x = 0; x <orderList.size(); x ++){
            log.debug("查看变量x=={}",x);
            //通过食物ID查询食物 状态是true
            Foods foods = new Foods() ;
            foods.setId(orderList.get(x).getId());
            foods.setFooddisable("true");
            log.debug("查看foods的属性 ID={} fooddisable={}",foods.getId(),foods.getFooddisable());
            if (ObjectUtils.isEmpty(foodsMapper.selfoodsall(foods))){
                return 400;
            }
            Foods selfoodsall = foodsMapper.selfoodsall(foods).get(0);
            log.debug("查看食物查询结果--={}",selfoodsall);
            log.debug("结算购物车---通过ID和状态true查询食物库存--->{}",selfoodsall.getFoodstock());
            log.debug("查看库存={}是不是比订单订购数量={}多={}",selfoodsall.getFoodstock(),orderList.get(x).getFoodtotal(),selfoodsall.getFoodstock()<orderList.get(x).getFoodtotal());
            if (selfoodsall.getFoodstock()<=0 || selfoodsall.getFoodstock()<orderList.get(x).getFoodtotal()){
                log.debug("结算购物车---foodtotal--->{}",orderList.get(x).getFoodtotal());

                foodtotal = false ;
            }
            //库存正常的话,每次循环都会把食物的单价和数量相乘 保存早变量中,用来判断用户的余额是不是够用
            total+=selfoodsall.getFoodprice() *orderList.get(x).getFoodtotal();
        }
        log.debug("结算购物车--看一下总订单金额是多少->{}",total);
        //
        log.debug("foodtotal=={}",foodtotal);
        if (foodtotal!=true){
            //库存肯定有不足的,直接返回300
            return 300;
        }
        //如果订单的总额>新更新的用户的余额, 直接返回301
        if (total>byusps.getBalance()){
            return 301;
        }
        for (Order o :orderList){
            Order order = new Order();
            Foods food = new Foods() ;
            food.setId(o.getId());
            Foods selfoodsall =  foodsMapper.selfoodsall(food).get(0);
            //更新食物的库存 总库存-订购库存
            selfoodsall.setFoodstock(selfoodsall.getFoodstock()-o.getFoodtotal());
            //更新到数据库
            foodsMapper.upfoodall(selfoodsall);
            order.setUsername(byusps.getUsername());
            order.setName(byusps.getName());
            order.setPhone(byusps.getPhone());
            order.setFoodname(selfoodsall.getFoodname());
            order.setFoodprice(selfoodsall.getFoodprice());
            order.setFoodtotal(o.getFoodtotal());
            order.setFoodamount(order.getFoodprice()*order.getFoodtotal());
            Integer addorder = orderMapper.addorder(order);
        }
        //把用户金额更新到数据库中(余额-订单总额)
        byusps.setBalance(byusps.getBalance()-total);
        usersMapper.upUserAll(byusps);
        return 200;
    }

    //更新订单信息
    @Override
    public Integer uporder(Order order) {
        return null;
    }
    //按照条件更新信息
    @Override
    public Integer uporderall(Order order) {
        return null;
    }
    //查询购物车
    @Override
    public List<Order> selcart(List<Order> orderList) {
        List<Order> order = new ArrayList<>();
        for (Order o :orderList){
            Foods foods = new Foods();
            foods.setId(o.getId());
            List<Foods> foodsList = foodsMapper.selfoodsall(foods);
            Order ord = new Order();
            ord.setFoodname(foodsList.get(0).getFoodname());
            ord.setFoodprice(foodsList.get(0).getFoodprice());
            ord.setFoodtotal(o.getFoodtotal());
            ord.setFoodamount(foodsList.get(0).getFoodprice() * o.getFoodtotal());
            order.add(ord);
        }
        return order;
    }
    //实时查看销售食物数量
    @Override
    public List<Order> ondayorder() {
        List<Order> list = orderMapper.ondayorder();

        return list;
    }
    //生成今日订单 的excel
    @Override
    public void dayexcel(HttpServletResponse response) throws IOException {
        List<DayOrderExcel> list = orderMapper.dayexcel();

        ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream()).build();
        //自定义表头
        List<List<String>> headList = new ArrayList<List<String>>();
        //底N列表头
        List<String> headTitle0 = new ArrayList();
        List<String> headTitle1 = new ArrayList();
        List<String> headTitle2 = new ArrayList();
        List<String> headTitle3 = new ArrayList();
        List<String> headTitle4 = new ArrayList();
        List<String> headTitle5 = new ArrayList();
        List<String> headTitle6 = new ArrayList();
        List<String> headTitle7 = new ArrayList();

        //N列的表头名称
        headTitle0.add("ID");
        headTitle1.add("姓名");
        headTitle2.add("电话");
        headTitle3.add("食物名称");
        headTitle4.add("数量");
        headTitle5.add("订单时间");
        headTitle6.add("订单手生效");
        headTitle7.add("送餐地点");
        //添加表头名称到自定义list集合
        headList.add(headTitle0);
        headList.add(headTitle1);
        headList.add(headTitle2);
        headList.add(headTitle3);
        headList.add(headTitle4);
        headList.add(headTitle5);
        headList.add(headTitle6);
        headList.add(headTitle7);
        //这是表头
        WriteSheet sheet = EasyExcel.writerSheet(0,"今日订单").head(headList).build();
        excelWriter.write(list,sheet);
        excelWriter.finish();

    }


}
