package com.dhf.dh.entity;

public class BalanceLog {
    private Integer id ;
    private String upuser;//为谁充值
    private Integer oldbalance ; //账户余额
    private Integer charge ; //充值金额
    private Integer newbalance ;//充值完剩余金额
    private String currentuser ;//当前使用账号
    private String time ;//充值事件

    public BalanceLog() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUpuser() {
        return upuser;
    }

    public void setUpuser(String upuser) {
        this.upuser = upuser;
    }

    public Integer getOldbalance() {
        return oldbalance;
    }

    public void setOldbalance(Integer oldbalance) {
        this.oldbalance = oldbalance;
    }

    public Integer getCharge() {
        return charge;
    }

    public void setCharge(Integer charge) {
        this.charge = charge;
    }

    public Integer getNewbalance() {
        return newbalance;
    }

    public void setNewbalance(Integer newbalance) {
        this.newbalance = newbalance;
    }

    public String getCurrentuser() {
        return currentuser;
    }

    public void setCurrentuser(String currentuser) {
        this.currentuser = currentuser;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
