package com.dhf.dh.service;

import com.dhf.dh.entity.BalanceLog;
import com.dhf.dh.entity.SelQuest;

import java.util.List;

public interface IBalanceLogService {

    //添加新的充值记录
    Integer addBalanceLog (BalanceLog balanceLog);
    //查询充值记录
    List<BalanceLog> selBalanceLog();
    //选择性查询充值记录
    List<BalanceLog> selBalanceLogAll(SelQuest selquest);
}
