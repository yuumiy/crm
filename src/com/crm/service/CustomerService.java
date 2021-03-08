package com.crm.service;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.crm.domain.Customer;
import com.crm.domain.PageBean;

/**
 * 客户管理的Service接口
 * @author thinkpad
 *
 */
public interface CustomerService {

	void save(Customer customer);

	PageBean<Customer> findByPage(DetachedCriteria detachedCriteria, Integer currPage,Integer pageSize);

	Customer findById(Long cust_id);

	void delete(Customer customer);

	void update(Customer customer);

	List<Customer> findAll();

	PageBean<Customer> findIndustry(DetachedCriteria detachedCriteria, Integer currPage, Integer pageSize);

	PageBean<Customer> findSource(DetachedCriteria detachedCriteria, Integer currPage, Integer pageSize);

}
