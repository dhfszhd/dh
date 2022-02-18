package com.dhf.dh.service;

import com.dhf.dh.entity.Foods;

import java.util.List;

public interface IFoodsSrvice {
    //查找食物
    List<Foods> selfoods(Foods foods);
    //按照条件查找
    List<Foods> selfoodsall(Foods foods);
    //添加新的食物
    Integer insfood (Foods foods);
    //按条件添加新的食物
    Integer insfoodall(Foods foods);
    //更新食物 单价 库存 是否上架
    Integer upfood(Foods foods);
    //按照条件 更新食物 单价 库存 是否上架
    Integer upfoodall(Foods foods);
}
