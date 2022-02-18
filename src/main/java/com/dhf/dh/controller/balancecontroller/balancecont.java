package com.dhf.dh.controller.balancecontroller;

import com.dhf.dh.entity.BalanceLog;
import com.dhf.dh.entity.SelQuest;
import com.dhf.dh.service.IBalanceLogService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class balancecont {
    @Autowired
    private IBalanceLogService balanceLogService ;

    @RequiresRoles(value = {"admin","finance"},logical= Logical.OR)//admin管理员finance财务管理
    @RequestMapping(value = "/selbalan" ,method = RequestMethod.POST)
    @ResponseBody
    //按照账号条件查询充值记录
    public Map<String, Object> selbalan(SelQuest quest){
        if (quest.getKeyword()==null && quest.getOnlydate()==null){
            String dateNow = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            quest.setOnlydate(dateNow);

        }
        //开启分页
        Map<String, Object> map =new HashMap<>();
        Page<Object> pagehelper =PageHelper.startPage(quest.getPage(),quest.getRows());
        List<BalanceLog> balanceLogs = balanceLogService.selBalanceLogAll(quest);
        long total = pagehelper.getTotal();
        map.put("rows",balanceLogs);
        map.put("total",total);
        return map;

    }
}
