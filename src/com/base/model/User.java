package com.base.model;

import java.sql.Timestamp;
import java.util.Date;

public class User {
	private int id;
	private String loginName;// 登录名
	private String passWord;// 密码
	private String displayName;// 显示名称
	private int state;// 状态，0=冻结，1=正常
	private String loginIp;// 登陆IP
	private String bindIp;// 绑定IP
	private int ipbandFlag;// 是否绑定
	private int tryTimes;// 登陆失败的尝试次数，超过次数后session有效期内禁止登陆
	private Timestamp lastLogintime;// 最后登陆时间
	private String email;// 邮箱
	private String contactPhone;// 电话
	private Date insertTime;// 插入时间

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}


	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public String getBindIp() {
		return bindIp;
	}

	public void setBindIp(String bindIp) {
		this.bindIp = bindIp;
	}

	public int getIpbandFlag() {
		return ipbandFlag;
	}

	public void setIpbandFlag(int ipbandFlag) {
		this.ipbandFlag = ipbandFlag;
	}

	public int getTryTimes() {
		return tryTimes;
	}

	public void setTryTimes(int tryTimes) {
		this.tryTimes = tryTimes;
	}

	public Timestamp getLastLogintime() {
		return lastLogintime;
	}

	public void setLastLogintime(Timestamp lastLogintime) {
		this.lastLogintime = lastLogintime;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

}
