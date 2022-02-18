package com.dhf.dh.entity;

public class Foods {
    private Integer id ;//食物id
    private String foodname;//食物名称
    private Integer foodprice;//食物单价
    private Integer foodstock;//食物库存
    private String fooddisable;//食物上架状态,数据库默认为"true"

    public Foods() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getFoodstock() {
        return foodstock;
    }

    public void setFoodstock(Integer foodstock) {
        this.foodstock = foodstock;
    }

    public String getFooddisable() {
        return fooddisable;
    }

    public void setFooddisable(String fooddisable) {
        this.fooddisable = fooddisable;
    }
}
