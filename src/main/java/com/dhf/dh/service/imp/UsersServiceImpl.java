package com.dhf.dh.service.imp;

import com.dhf.dh.entity.BalanceLog;
import com.dhf.dh.entity.Users;
import com.dhf.dh.mapper.BalanceLogMapper;
import com.dhf.dh.mapper.UsersMapper;
import com.dhf.dh.service.IUsersService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import java.util.List;

@Service
@Transactional
public class UsersServiceImpl implements IUsersService {
    //注入DAO
    @Autowired
    private UsersMapper usersMapper ;
    @Autowired
    private BalanceLogMapper balanceLogMapper ;
    @Autowired
    private HttpSession session ;
    //用过账号密码查询用户
    @Override
    public Users selByusps(Users users) {
        if (users.getUsername()==null||users.getPassword()==null||"".equals(users.getUsername())||"".equals(users.getPassword())){
            return null;
        }
        Users byusps = usersMapper.selByusps(users);

        return byusps;
    }
    //选择性查找用户
    @Override
    public Users selByAll(Users users) {
        if (users.getId()==null && users.getUsername()==null &&
                users.getName()==null&& users.getPhone()==null &&
                users.getChargeback()==null && users.getUserdisable()==null){
            return null;
        }
        List<Users> selByAll = usersMapper.selByAll(users);
        if (ObjectUtils.isEmpty(selByAll)){
            return null;
        }
        Users user = selByAll.get(0);
        return user;
    }
    //添加新的用户
    @Override
    public Integer insUser(Users users) {
        if (users.getUsername()==null|| "".equals(users.getUsername()) ||users.getPhone()==null||"".equals(users.getPhone())){

            return 302;
        }
        Users useru0 = new Users();
        useru0.setUsername(users.getUsername());
        List<Users> usersList0 = usersMapper.selByAll(useru0);
        if (usersList0.size()>=1){
            return 300;
        }
        Users users1 = new Users();
        users1.setPhone(users.getPhone());
        List<Users> usersList1 = usersMapper.selByAll(users1);
        if (usersList1.size()>=1){
            return 301;
        }
        Integer insUser = usersMapper.insUser(users);
        if (insUser==1){
            return 200;
        }
        return 400;
    }
    //通过ID修改用户
    @Override
    public Integer upUser(Users users) {
        return 0;
    }
    //通过ID 选择性修改用户
    @Override
    public Integer upUserAll( Users users) {
        if (users.getId()==null ||"".equals(users.getId())){
            return 400;
        }

            Integer upUserAll = usersMapper.upUserAll(users);
            if (upUserAll==1){
                return 200 ;
            }
        return 400;
    }
//充值 需要ID 和充值的具体金额
    @Override
    public synchronized Integer upUserBalance(Users users) {
        List<Users> selByAll = usersMapper.selByAll(users);//按照ID查询账号信息
        Users usersession = (Users) session.getAttribute("user");//取出session中的user信息
        BalanceLog balanceLog = new BalanceLog() ; //实例化充值表对象
        balanceLog.setUpuser(selByAll.get(0).getUsername());//充值表填充为谁充值
        balanceLog.setOldbalance(selByAll.get(0).getBalance());//充值表填充旧的余额
        balanceLog.setCharge(users.getBalance());//充值表填充充值金额是多少
        users.setBalance(selByAll.get(0).getBalance()+users.getBalance());//设置修改后的余额是多少,
        Integer upUserBalance = usersMapper.upUserBalance(users);//固化新的金额到数据库
        List<Users> newselByAll =  usersMapper.selByAll(users);//查询新的余额
        balanceLog.setNewbalance(newselByAll.get(0).getBalance());
        balanceLog.setCurrentuser(usersession.getUsername());//哪个账号给充值
        Integer log = balanceLogMapper.addBalanceLog(balanceLog);
        if (log==1 && upUserBalance==1){
            return 200 ;
        }
        return 400;
    }

}
