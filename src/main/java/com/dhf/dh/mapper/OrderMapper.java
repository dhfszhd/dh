package com.dhf.dh.mapper;

import com.dhf.dh.entity.DayOrderExcel;
import com.dhf.dh.entity.Order;
import com.dhf.dh.entity.SelQuest;
import com.dhf.dh.entity.Users;
import org.apache.catalina.User;

import java.util.List;
import java.util.Map;

public interface OrderMapper {
    //查询订单通过账号查询当天的订单
    List<Order> selorbyusername(Users users);
    //查询订单 按照条件可以查询所有
    List<Order> selorderall(SelQuest selQuest);
    //添加新的订单
    Integer addorder(Order order);
    //更新新的订单,
    Integer uporder(Order order);
    //更新订单 按照条件
    Integer uporderall(Order order);

    //实时查询当天食宿销售数量
    List<Order> ondayorder();
    //打印今日订单
    List<DayOrderExcel> dayexcel();
}
