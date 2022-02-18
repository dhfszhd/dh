package com.dhf.dh.service.imp;

import com.dhf.dh.entity.Foods;
import com.dhf.dh.mapper.FoodsMapper;
import com.dhf.dh.service.IFoodsSrvice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class FoodsSrviceImpl implements IFoodsSrvice {
    private static final Logger log = LoggerFactory.getLogger(FoodsSrviceImpl.class);
    //注入
    @Autowired
    private FoodsMapper foodsMapper ;
    //查找食物
    @Override
    public List<Foods> selfoods(Foods foods) {
        List<Foods> selfoods = foodsMapper.selfoods(foods);
        return selfoods;
    }
    //按照条件查找
    @Override
    public List<Foods> selfoodsall(Foods foods) {
        List<Foods> selfoodsall = foodsMapper.selfoodsall(foods);
        log.debug("查询食物----食物列表>{}",selfoodsall);

        return selfoodsall;
    }

    //添加新的食物
    @Override
    public Integer insfood(Foods foods) {
        if (foods==null){
            return 300;
        }
        if (foods.getFoodname()==null || "".equals(foods.getFoodname())){
            return 301;
        }
        if (foods.getFoodprice()==null ||foods.getFoodprice()<0){
            return 302;
        }

        Foods newfood = new Foods();
        newfood.setFoodname(foods.getFoodname());
        List<Foods> selfoodsall = foodsMapper.selfoodsall(newfood);
        if (selfoodsall.size()>=1){
            return 303;
        }
        Integer insfood = foodsMapper.insfoodall(foods);
        if (insfood==1){
            return 200;
        }
        return 400;
    }

    //按条件添加新的食物
    @Override
    public Integer insfoodall(Foods foods) {
        return null;
    }

    //更新食物 单价 库存 是否上架
    @Override
    public Integer upfood(Foods foods) {
        return null;
    }

    //按照条件 更新食物 单价 库存 是否上架
    @Override
    public Integer upfoodall(Foods foods) {
        if (foods.getId()==null){
            return 300 ;
        }
        if ( foods.getFoodprice()==null &&foods.getFoodstock()==null
            && foods.getFooddisable()==null){
            return 301;
        }
        Integer upfoodall = foodsMapper.upfoodall(foods);
        if (upfoodall==1){
            return 200;
        }
        return 400;
    }
}
