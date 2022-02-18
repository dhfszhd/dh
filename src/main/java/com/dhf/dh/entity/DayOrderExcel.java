package com.dhf.dh.entity;

public class DayOrderExcel {
    private Integer id ;
    private String name;
    private String phone ;
    private String foodname ;
    private Integer foodtotal ;
    private String ordertime ;
    private String orderdisable ;
    private String mealaddress ;

    public DayOrderExcel() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getFoodtotal() {
        return foodtotal;
    }

    public void setFoodtotal(Integer foodtotal) {
        this.foodtotal = foodtotal;
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
        if ("true".equals(orderdisable)){
            this.orderdisable="订单生效";
        }else {
            this.orderdisable="订单已失效";
        }
    }

    public String getMealaddress() {
        return mealaddress;
    }

    public void setMealaddress(String mealaddress) {
        this.mealaddress = mealaddress;
    }
}
