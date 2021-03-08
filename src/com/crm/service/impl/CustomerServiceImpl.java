package com.crm.service.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.transaction.annotation.Transactional;

import com.crm.dao.CustomerDao;
import com.crm.domain.Customer;
import com.crm.domain.PageBean;
import com.crm.service.CustomerService;
/**
 * 客户管理的Service的实现类
 * @author thinkpad
 *
 */
@Transactional
public class CustomerServiceImpl implements CustomerService {
	//注入客户的Dao
	private CustomerDao customerDao;

	public void setCustomerDao(CustomerDao customerDao) {
		this.customerDao = customerDao;
	}
	
	//业务层保存客户的方法
	@Override
	public void save(Customer customer) {
		customerDao.save(customer);
	}
	
	//业务层分页查询客户的方法
	@Override
	public PageBean<Customer> findByPage(DetachedCriteria detachedCriteria, Integer currPage,Integer pageSize) {
		PageBean<Customer> pageBean=new PageBean<Customer>();
		//封装当前页数
		pageBean.setCurrPage(currPage);
		//封装每页显示记录数
		pageBean.setPageSize(pageSize);
		//封装总记录数，调用Dao
		Integer totalCount=customerDao.findCount(detachedCriteria);
		pageBean.setTotalCount(totalCount);
		//封装总页数
		Double tc =totalCount.doubleValue();
		//向上取整，如10/3，就是4
		Double num = Math.ceil(tc/pageSize);
		pageBean.setTotalPage(num.intValue());
		//封装每页显示的数据的集合
		Integer begin=(currPage-1)*pageSize;
		List<Customer> list=customerDao.findByPage(detachedCriteria,begin,pageSize);
		pageBean.setList(list);
		return pageBean;
	}
	
	/**
	 * 根据ID查询客户的方法
	 */
	@Override
	public Customer findById(Long cust_id) {
		return customerDao.findById(cust_id);
	}
	
	/**
	 * 业务层删除客户的方法
	 */
	@Override
	public void delete(Customer customer) {
		customerDao.delete(customer);
	}

	/**
	 * 业务层修改客户的方法
	 */
	@Override
	public void update(Customer customer) {
		customerDao.update(customer);
	}
	
	/**
	 * 业务层查询所有客户的方法
	 */
	@Override
	public List<Customer> findAll() {
		
		return customerDao.findAll();
	}
	/**
	 * 分页查询客户行业的方法
	 */
	@Override
	public PageBean<Customer> findIndustry(DetachedCriteria detachedCriteria, Integer currPage, Integer pageSize) {
		PageBean<Customer> pageBean=new PageBean<Customer>();
		//封装当前页数
		pageBean.setCurrPage(currPage);
		//封装每页显示记录数
		pageBean.setPageSize(pageSize);
		//封装总记录数，调用Dao
		Integer totalCount=customerDao.findCount(detachedCriteria);
		pageBean.setTotalCount(totalCount);
		//封装总页数
		Double tc =totalCount.doubleValue();
		//向上取整，如10/3，就是4
		Double num = Math.ceil(tc/pageSize);
		pageBean.setTotalPage(num.intValue());
		//封装每页显示的数据的集合
		Integer begin=(currPage-1)*pageSize;
		List<Customer> list=customerDao.findByPage(detachedCriteria,begin,pageSize);
		pageBean.setList(list);
		return pageBean;
	}
	/**
	 * 分页查询客户来源的方法
	 */
	@Override
	public PageBean<Customer> findSource(DetachedCriteria detachedCriteria, Integer currPage, Integer pageSize) {
		PageBean<Customer> pageBean=new PageBean<Customer>();
		//封装当前页数
		pageBean.setCurrPage(currPage);
		//封装每页显示记录数
		pageBean.setPageSize(pageSize);
		//封装总记录数，调用Dao
		Integer totalCount=customerDao.findCount(detachedCriteria);
		pageBean.setTotalCount(totalCount);
		//封装总页数
		Double tc =totalCount.doubleValue();
		//向上取整，如10/3，就是4
		Double num = Math.ceil(tc/pageSize);
		pageBean.setTotalPage(num.intValue());
		//封装每页显示的数据的集合
		Integer begin=(currPage-1)*pageSize;
		List<Customer> list=customerDao.findByPage(detachedCriteria,begin,pageSize);
		pageBean.setList(list);
		return pageBean;
	}
	
}
