package com.crm.service.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.transaction.annotation.Transactional;

import com.crm.dao.LinkManDao;
import com.crm.domain.LinkMan;
import com.crm.domain.PageBean;
import com.crm.domain.User;
import com.crm.service.LinkManService;
/**
 * 联系人的业务层的实现类
 * @author thinkpad
 *
 */
@Transactional      //要在联系人的业务层加上事务，否则保存联系人就会报错
public class LinkManServiceImpl implements LinkManService {
	//注入Dao,并且设置一个set方法
	private LinkManDao linkManDao;

	public void setLinkManDao(LinkManDao linkManDao) {
		this.linkManDao = linkManDao;
	}
	/**
	 * 这是业务层分类查询联系人的方法
	 */
	@Override
	public PageBean<LinkMan> findAll(DetachedCriteria detachedCriteria, Integer currPage, Integer pageSize) {
		PageBean<LinkMan> pageBean=new PageBean<LinkMan>();
		//设置当前页数
		pageBean.setCurrPage(currPage);
		//设置每页显示记录数
		pageBean.setPageSize(pageSize);
		//设置总记录数
		Integer totalCount=linkManDao.findCount(detachedCriteria);
		pageBean.setTotalCount(totalCount);
		//设置总页数,10/3 就是4
		double tc=totalCount;
		Double num=Math.ceil(tc/pageSize);
		pageBean.setTotalPage(num.intValue());
		//每页显示数据的集合
		Integer begin=(currPage-1)*pageSize;
		List<LinkMan> list=linkManDao.findByPage(detachedCriteria,begin,pageSize);
		pageBean.setList(list);
		return pageBean;
	}
	/**
	 * 业务层保存联系人的方法
	 */
	@Override
	public void save(LinkMan linkMan) {
		linkManDao.save(linkMan);
	}
	/**
	 * 业务层根据ID查询联系人的方法
	 */
	@Override
	public LinkMan findByID(Long lkm_id) {
		return linkManDao.findById(lkm_id);
	}
	/**
	 * 业务层修改联系人的方法
	 */
	@Override
	public void update(LinkMan linkMan) {
		linkManDao.update(linkMan);
	}
	/**
	 * 业务层删除联系人的方法
	 */
	@Override
	public void delete(LinkMan linkMan) {
		linkManDao.delete(linkMan);
	}
	/**
	 * 分页查询联系人角色的方法
	 */
	@Override
	public PageBean<LinkMan> find(DetachedCriteria detachedCriteria, Integer currPage, Integer pageSize) {
		PageBean<LinkMan> pageBean=new PageBean<LinkMan>();
		//设置当前页数
		pageBean.setCurrPage(currPage);
		//设置每页显示记录数
		pageBean.setPageSize(pageSize);
		//设置总记录数
		Integer totalCount=linkManDao.findCount(detachedCriteria);
		pageBean.setTotalCount(totalCount);
		//设置总页数,10/3 就是4
		double tc=totalCount;
		Double num=Math.ceil(tc/pageSize);
		pageBean.setTotalPage(num.intValue());
		//每页显示数据的集合
		Integer begin=(currPage-1)*pageSize;
		List<LinkMan> list=linkManDao.findByPage(detachedCriteria,begin,pageSize);
		pageBean.setList(list);
		return pageBean;
	}
	
}
