package com.crm.web.action;

import java.util.Date;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.crm.domain.PageBean;
import com.crm.domain.SaleVisit;
import com.crm.service.SaleVisitService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class SaleVisitAction extends ActionSupport implements ModelDriven<SaleVisit> {
	
	//加载模型驱动的对象
	private SaleVisit saleVisit=new SaleVisit();
	@Override
	public SaleVisit getModel() {
		return saleVisit;
	}
	
	//在Action中注入Service
	@Resource(name="saleVisitService")
	private SaleVisitService saleVisitService;
	
	//接收分页数据
	private Integer currPage=1;
	private Integer pageSize=3;
	
	public void setCurrPage(Integer currPage) {
		if(currPage==null) {
			currPage=1;
		}
		this.currPage = currPage;
	}


	public void setPageSize(Integer pageSize) {
		if(pageSize==null) {
			pageSize=3;
		}
		this.pageSize = pageSize;
	}
	
	//接收数据
	private Date visit_end_time;
	
	public void setVisit_end_time(Date visit_end_time) {
		this.visit_end_time = visit_end_time;
	}
	public Date getVisit_end_time() {
		return visit_end_time;
	}


	/**
	 * 查询拜访记录列表的方法：findAll
	 */
	public String findAll() {
		//创建离线条件查询对象
		DetachedCriteria detachedCriteria=DetachedCriteria.forClass(SaleVisit.class);
		//设置条件
		//大于等于visit_time
		if(saleVisit.getVisit_time()!=null) {
			detachedCriteria.add(Restrictions.ge("visit_time", saleVisit.getVisit_time()));
		}
		//小于等于visit_end_time
		if(visit_end_time!=null) {
			detachedCriteria.add(Restrictions.le("visit_time", visit_end_time));
		}
		//调用业务层
		PageBean<SaleVisit> pageBean=saleVisitService.findByPage(detachedCriteria,currPage,pageSize);
		//存入到值栈
		ActionContext.getContext().getValueStack().push(pageBean);
		return "findAll";
	}
	/**
	 * 综合查询里的分页查询客户拜访记录：totalFindAll
	 */
	public String totalFindAll() {
		//创建离线条件查询对象
		DetachedCriteria detachedCriteria=DetachedCriteria.forClass(SaleVisit.class);
		//设置条件
		//大于等于visit_time
		if(saleVisit.getVisit_time()!=null) {
			detachedCriteria.add(Restrictions.ge("visit_time", saleVisit.getVisit_time()));
		}
		//小于等于visit_end_time
		if(visit_end_time!=null) {
			detachedCriteria.add(Restrictions.le("visit_time", visit_end_time));
		}
		if(saleVisit.getVisit_addr()!=null) {
			//设置拜访地点
			detachedCriteria.add(Restrictions.like("visit_addr", "%"+saleVisit.getVisit_addr()+"%"));
		}
		if(saleVisit.getVisit_detail()!=null) {
			//设置拜访详情
			detachedCriteria.add(Restrictions.like("visit_detail", "%"+saleVisit.getVisit_detail()+"%"));
		}
		//调用业务层
		PageBean<SaleVisit> pageBean=saleVisitService.findByPage(detachedCriteria,currPage,pageSize);
		//存入到值栈
		ActionContext.getContext().getValueStack().push(pageBean);
		return "totalFindAll";
	}
	/**
	 * 跳转到新增客户拜访的页面：saveUI
	 */
	public String saveUI() {
		
		return "saveUI";
	}
	/**
	 * 保存客户拜访记录的方法：save
	 */
	public String save() {
		saleVisitService.save(saleVisit);
		return "saveSuccess";
	}
}
