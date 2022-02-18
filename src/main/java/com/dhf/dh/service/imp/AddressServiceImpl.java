package com.dhf.dh.service.imp;

import com.dhf.dh.entity.Address;
import com.dhf.dh.mapper.AddressMapper;
import com.dhf.dh.service.IAddressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
//注释service

@Service
public class AddressServiceImpl implements IAddressService {

    private static final Logger log = LoggerFactory.getLogger(AddressServiceImpl.class);
    @Autowired
    private AddressMapper addressMapper;
    //查询取餐地点,
    @Override
    public List<Address> selall(Address address) {
        List<Address> selall = addressMapper.selall(address);
        log.debug("全部查询列表:{}",selall);
        return selall;
    }
    //添加新的取餐地点
    @Override
    public Integer insadd(Address address) {
        //查询已存在了地点 返回300
        List<Address> selall = addressMapper.selall(address);
        if (selall.size()>=1){

            return 300;
        }
        Integer insadd = addressMapper.insadd(address);
        if (insadd>=1){
            return 200;
        }

        return 400;
    }

    //更新取餐地点禁用状态
    @Override
    public Integer upadd(Address address) {
        List<Address> selall = addressMapper.selall(address);

        if (selall.get(0).getAddressdisable().equals(address.getAddressdisable())){
            return 400;
        }
        Integer upadd = addressMapper.upadd(address);

        return 200;

    }
}
