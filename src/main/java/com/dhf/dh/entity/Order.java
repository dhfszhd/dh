package com.dhf.dh.entity;

public class Order {

    private Integer id;//订单id
    private String username;//用户账号
    private String name;//用户姓名
    private String phone;//用户电话
    private String foodname;//订购食物名称
    private Integer foodprice;//食物的单价
    private Integer foodtotal;//食物的数量
    private Integer foodamount;//食物总价
    private String ordertime;//订单下单的时间
    private String orderdisable;//是否被禁用.也就是说取消订单

    public Order() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFoodname() {
        return foodname;
    }

    public void setFoodname(String foodname) {
        this.foodname = foodname;
    }

    public Integer getFoodprice() {
        return foodprice;
    }

    public void setFoodprice(Integer foodprice) {
        this.foodprice = foodprice;
    }

    public Integer getFoodtotal() {
        return foodtotal;
    }

    public void setFoodtotal(Integer foodtotal) {
        this.foodtotal = foodtotal;
    }

    public Integer getFoodamount() {
        return foodamount;
    }

    public void setFoodamount(Integer foodamount) {
        this.foodamount = foodamount;
    }

    public String getOrdertime() {
        return ordertime;
    }

    public void setOrdertime(String ordertime) {
        this.ordertime = ordertime;
    }

    public String getOrderdisable() {
        return orderdisable;
    }

    public void setOrderdisable(String orderdisable) {
        this.orderdisable = orderdisable;
    }
}
