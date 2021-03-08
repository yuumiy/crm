package com.crm.domain;

import java.util.Date;

/**
 * 客户拜访记录管理的实体
 * @author thinkpad
 *
 */
public class SaleVisit {
	private String visit_id;
	private Date visit_time;
	private String visit_addr;
	private String visit_detail;
	private Date visit_nexttime;
	//拜访记录所关联的客户的对象，用户是1，拜访记录是多；客户是1，拜访记录是多
	//在多的一方放置一方的基本对象，在一的一方放置多的一方的集合
	//这里拜访记录对于客户方没用，因此客户那边不用设置集合
	//SaleVisit是多的一方，因此放置一个Customer对象即可
	private Customer customer;
	private User user;
	public String getVisit_id() {
		return visit_id;
	}
	public void setVisit_id(String visit_id) {
		this.visit_id = visit_id;
	}
	public Date getVisit_time() {
		return visit_time;
	}
	public void setVisit_time(Date visit_time) {
		this.visit_time = visit_time;
	}
	public String getVisit_addr() {
		return visit_addr;
	}
	public void setVisit_addr(String visit_addr) {
		this.visit_addr = visit_addr;
	}
	public String getVisit_detail() {
		return visit_detail;
	}
	public void setVisit_detail(String visit_detail) {
		this.visit_detail = visit_detail;
	}
	public Date getVisit_nexttime() {
		return visit_nexttime;
	}
	public void setVisit_nexttime(Date visit_nexttime) {
		this.visit_nexttime = visit_nexttime;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
}
