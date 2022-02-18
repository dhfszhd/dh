package com.dhf.dh.service.imp;

import com.dhf.dh.entity.BalanceLog;
import com.dhf.dh.entity.SelQuest;
import com.dhf.dh.mapper.BalanceLogMapper;
import com.dhf.dh.service.IBalanceLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class BalanceLogServiceImpl implements IBalanceLogService {
    @Autowired
    private BalanceLogMapper balanceLogMapper ;
    //添加新的充值记录
    @Override
    public Integer addBalanceLog(BalanceLog balanceLog) {
        return balanceLogMapper.addBalanceLog(balanceLog);
    }
    //查询充值记录
    @Override
    public List<BalanceLog> selBalanceLog() {
        return null;
    }
    //选择性查询充值记录
    @Override
    public List<BalanceLog> selBalanceLogAll(SelQuest selquest) {
        List<BalanceLog> balanceLogs = balanceLogMapper.selBalanceLogAll(selquest);
        return balanceLogs;
    }
}
