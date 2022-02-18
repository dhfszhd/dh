package com.dhf.dh.entity;

public class Users {
    private Integer id;//用户ID
    private String username;//用户账号
    private String password;//用户密码
    private String name;//用户真实姓名
    private String phone;//用户手机号
    private Integer balance;//用户余额
    private String address;//用户取餐点
    private Integer role; //用户角色id
    private Integer chargeback;//取消订餐次数
    private String userdisable;//账号禁用
    private String register;//注册时间

    public Users() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public Integer getChargeback() {
        return chargeback;
    }

    public void setChargeback(Integer chargeback) {
        this.chargeback = chargeback;
    }

    public String getUserdisable() {
        return userdisable;
    }

    public void setUserdisable(String userdisable) {
        this.userdisable = userdisable;
    }

    public String getRegister() {
        return register;
    }

    public void setRegister(String register) {
        this.register = register;
    }
}
