package com.dhf.dh.mapper;

import com.dhf.dh.entity.Address;

import java.util.List;
public interface AddressMapper {
    //查询所有取餐地点
    List<Address> selall(Address address);
    //添加取餐点
    Integer insadd(Address address);
    //更新取餐地点 更新禁用状态
    Integer upadd(Address address);
}
