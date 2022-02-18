package com.dhf.dh.mapper;

import com.dhf.dh.entity.BalanceLog;
import com.dhf.dh.entity.SelQuest;

import java.util.List;

public interface BalanceLogMapper {
    //添加新的充值记录
    Integer addBalanceLog (BalanceLog balanceLog);
    //查询充值记录
    List<BalanceLog> selBalanceLog();
    //选择性查询充值记录
    List<BalanceLog> selBalanceLogAll(SelQuest selquest);
}
