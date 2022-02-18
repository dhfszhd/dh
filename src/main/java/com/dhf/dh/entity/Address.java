package com.dhf.dh.entity;

public class Address {
    private Integer id;//取餐地点ID
    private String mealaddress;//取餐地点
    private String addressdisable;//禁用状态

    public Address() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMealaddress() {
        return mealaddress;
    }

    public void setMealaddress(String mealaddress) {
        this.mealaddress = mealaddress;
    }

    public String getAddressdisable() {
        return addressdisable;
    }

    public void setAddressdisable(String addressdisable) {
        this.addressdisable = addressdisable;
    }
}

