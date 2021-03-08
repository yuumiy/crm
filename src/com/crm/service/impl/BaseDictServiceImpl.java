package com.crm.service.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.transaction.annotation.Transactional;

import com.crm.dao.BaseDictDao;
import com.crm.domain.BaseDict;
import com.crm.domain.PageBean;
import com.crm.domain.User;
import com.crm.service.BaseDictService;
/**
 * 字典的业务层的实现类
 * @author thinkpad
 *
 */
@Transactional
public class BaseDictServiceImpl implements BaseDictService {
	//注入Dao
	private BaseDictDao baseDictDao;

	public void setBaseDictDao(BaseDictDao baseDictDao) {
		this.baseDictDao = baseDictDao;
	}

	@Override
	public List<BaseDict> findByTypeCode(String dict_type_code) {
		return baseDictDao.findByTypeCode(dict_type_code);
	}
	
	/**
	 * 业务层分页查询数据字典数据的方法
	 */
	@Override
	public PageBean<BaseDict> find(DetachedCriteria detachedCriteria, Integer currPage, Integer pageSize) {
		PageBean<BaseDict> pageBean=new PageBean<BaseDict>();
		//设置当前页数
		pageBean.setCurrPage(currPage);
		//设置每页显示记录数
		pageBean.setPageSize(pageSize);
		//设置总记录数
		Integer totalCount=baseDictDao.findCount(detachedCriteria);
		pageBean.setTotalCount(totalCount);
		//设置总页数,10/3 就是4
		double tc=totalCount;
		Double num=Math.ceil(tc/pageSize);
		pageBean.setTotalPage(num.intValue());
		//每页显示数据的集合
		Integer begin=(currPage-1)*pageSize;
		List<BaseDict> list=baseDictDao.findByPage(detachedCriteria,begin,pageSize);
		pageBean.setList(list);
		return pageBean;
	}
	
}
