package com.crm.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.transaction.annotation.Transactional;

import com.crm.dao.SaleVisitDao;
import com.crm.domain.PageBean;
import com.crm.domain.SaleVisit;
import com.crm.service.SaleVisitService;
/**
 * 客户拜访记录的业务层实现类
 * @author thinkpad
 *
 */

@Transactional
public class SaleVisitServiceImpl implements SaleVisitService {
	
	//注入客户拜访记录的Dao
	@Resource(name="saleVisitDao")
	private SaleVisitDao saleVisitDao;

	//分页显示拜访记录的方法
	@Override
	public PageBean<SaleVisit> findByPage(DetachedCriteria detachedCriteria, Integer currPage, Integer pageSize) {
		PageBean<SaleVisit> pageBean=new PageBean<SaleVisit>();
		//设置当前页数
		pageBean.setCurrPage(currPage);
		//设置每页显示记录数
		pageBean.setPageSize(pageSize);
		//设置总记录数
		Integer totalCount=saleVisitDao.findCount(detachedCriteria);
		pageBean.setTotalCount(totalCount);
		//设置总页数
		double tc=totalCount;
		Double num=Math.ceil(tc/pageSize);
		pageBean.setTotalPage(num.intValue());
		//设置每页显示的数据的集合
		Integer begin=(currPage-1)*pageSize;
		List<SaleVisit> list = saleVisitDao.findByPage(detachedCriteria, begin, pageSize);
		pageBean.setList(list);
		return pageBean;
	}

	//保存客户拜访记录的方法
	@Override
	public void save(SaleVisit saleVisit) {
		saleVisitDao.save(saleVisit);
	}
}
