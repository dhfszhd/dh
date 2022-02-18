package com.dhf.dh.service;

import com.dhf.dh.entity.Order;
import com.dhf.dh.entity.SelQuest;
import com.dhf.dh.entity.Users;


import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface IOrderService {

    //查询订单通过账号查询当天的订单
    List<Order> selorbyusername(Users users);
    //查询订单 按照条件可以查询所有
    List<Order> selorderall(SelQuest selQuest);
    //添加新的订单
    Integer addorder(List<Order> orderList, Users users);
    //更新新的订单,
    Integer uporder(Order order);
    //更新订单 按照条件
    Integer uporderall(Order order);
    //查询购物车
    List<Order> selcart(List<Order> orderList);
    //实时查询食物销售数量
    List<Order> ondayorder();
    //生成今日订单excel
    void dayexcel(HttpServletResponse response) throws IOException;
}
