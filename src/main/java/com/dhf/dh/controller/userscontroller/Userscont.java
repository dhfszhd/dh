package com.dhf.dh.controller.userscontroller;

import com.dhf.dh.entity.BalanceLog;
import com.dhf.dh.entity.Users;
import com.dhf.dh.service.IBalanceLogService;
import com.dhf.dh.service.IUsersService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import java.util.HashMap;

import java.util.List;
import java.util.Map;

@Controller
public class Userscont {
    private static final Logger log = LoggerFactory.getLogger(Userscont.class);
    //注入
    @Autowired
    private IUsersService usersService;
    @Autowired
    private IBalanceLogService balanceLogService;


    //登录 通过账号密码验证
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> login(HttpServletRequest request, Users users){
        Map<String, String> map = new HashMap<>();
        Users selByusps = usersService.selByusps(users);
        if (selByusps!=null){
            log.debug("用户的禁用状态--->{}",selByusps.getUserdisable());
            if (selByusps.getUserdisable().equals("false")){
                map.put("code","300");
                map.put("msg","账号被禁用");
                return map;
            }
            log.debug("用户的取消订单次数--->{}",selByusps.getChargeback());
            if (selByusps.getChargeback()>=5){
                map.put("code","301");
                map.put("msg","当月取消订单次数过多,请在下月使用订餐");
                return map;
            }
            log.debug("登录前的session---->{}",request.getSession(false));
            request.getSession().setAttribute("user",selByusps);
            //shiro 认证
            Subject subject = SecurityUtils.getSubject();
            subject.login(new UsernamePasswordToken(users.getUsername(),users.getPassword()));
            log.debug("登录后的session---->{}",request.getSession(false));
            log.debug("登录后的session的中用户---->{}",request.getSession(false).getAttribute("user"));
            map.put("code","200");
            map.put("msg","登录成功");
            return map;
        }

        map.put("code","400");
        map.put("msg","登录失败");
        return map;
    }
    //退出登录
    @RequestMapping(value = "/logout")
//    @ResponseBody
//    public Map<String, String> logout(HttpSession session){
//        Map<String, String> map =new HashMap<>();
    public String logout(HttpSession session){

        session.invalidate();

        return "login";
    }

//添加新的用户
    @RequestMapping(value = "/insuser" ,method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> insuser(Users users){
        log.debug("注册新用户控制器--->用户名:{}----用户电话---{}", users.getUsername(),users.getPhone());

        HashMap<String, String> map = new HashMap<>();

        Integer insUser = usersService.insUser(users);
        if (insUser==300){
            map.put("code","300");
            map.put("msg","账号重复");
            return map;
        }
        if (insUser==301){
            map.put("code","301");
            map.put("msg","手机号码重复");
            return map;
        }
        if (insUser==302){
            map.put("code","302");
            map.put("msg","不许为空");
            return map;
        }
        if (insUser==200){
            map.put("code","200");
            map.put("msg","注册成功");
            return map;
        }
        map.put("code","400");
        map.put("msg","注册失败,未知错误");
        return map;
    }
    //选择性更新用户资料
    //更新密码
    @RequestMapping(value = "/uppw")
    @ResponseBody
    public Map<String, String> uppw(HttpServletRequest request, Users users){
        log.debug("修改密码用户ID---->{}",users.getId());
        HashMap<String, String> map = new HashMap<>();
        if(users.getId()==null || "".equals(users.getId())){
            map.put("code","300");
            map.put("msg","用户ID不能为空");
            return map;
            }
        log.debug("修改密码--->新密码 {}",users.getPassword());
        if (users.getPassword()!=null && !"".equals(users.getPassword()) ){
            log.debug("修改密码用户session---->{}",request.getSession(false));
            Users olduser = (Users) request.getSession(false).getAttribute("user");
            log.debug("修改密码----->旧密码{}",olduser.getPassword());
            if (!users.getPassword().equals(olduser.getPassword())){
                Integer upUserAll = usersService.upUserAll(users);
                if (upUserAll==200){
                    map.put("code","200");
                    map.put("msg","修改成功,请重新登录");
                    request.getSession().invalidate();//清除session,退出登录
                    log.debug("修改成功后session失效---->{}",request.getSession(false));
                    return map;
                }
                map.put("code","303");
                map.put("msg","未知错误");
                return map;

            }
            map.put("code","302");
            map.put("msg","新密码和旧密码不能重复");
            return map;
        }
        map.put("code","301");
        map.put("msg","重置密码不能为空");
        return map ;
    }
    //修改取餐地点

    //按照手机号码查找用户 以便充值
    @RequiresRoles(value = {"admin","finance"},logical=Logical.OR)//admin管理员finance财务管理
    @RequestMapping(value = "/selbyphone")
    @ResponseBody
    public Map<String, String> selbyphone(HttpSession  session ,Users users){
        Map<String, String> map = new HashMap<>();
        if (users.getPhone()==null || "".equals(users.getPhone())){
            map.put("code","400");
            map.put("msg","未找到");
            return map ;
        }
        Users selByAll = usersService.selByAll(users);
        if (ObjectUtils.isEmpty(selByAll)){
            map.put("code","400");
            map.put("msg","未找到");
            return map ;
        }
        map.put("code","200");
        map.put("id",selByAll.getId().toString());
        map.put("name",selByAll.getName());
        map.put("phone",selByAll.getPhone());
        map.put("balance",selByAll.getBalance().toString());
        return map ;
    }

    //充值
    @RequiresRoles(value = {"admin","finance"},logical= Logical.OR)//admin管理员.finance财务管理
    @RequestMapping (value = "/upuserbalance")
    @ResponseBody
    public Map<String, String> upuserbalance(Users users){
        Map<String, String> map = new HashMap<>();
        if (users.getId()==null|| "".equals(users.getId())){
            map.put("code","400");
            map.put("msg","用户的ID不能为空");
            return map ;
        }if (users.getBalance()==null|| "".equals(users.getBalance())){
            map.put("code","401");
            map.put("msg","充值金额不能为空");
            return map ;
        }

        Integer upUserBalance = usersService.upUserBalance(users);
        if (upUserBalance==200){
            map.put("code","200");
            map.put("msg","充值成功");
            Users us = usersService.selByAll(users);
            map.put("balance",us.getBalance().toString());
            return map;
        }
        map.put("code","402");
        map.put("msg","充值失败,位置错误");
        return map;

    }
    //修改角色

    //修改取消订单次数

    //修改账号禁用


}
