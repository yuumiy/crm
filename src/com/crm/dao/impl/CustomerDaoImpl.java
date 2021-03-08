package com.crm.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import com.crm.dao.CustomerDao;
import com.crm.domain.Customer;
/**
 * 客户管理Dao的实现类
 * @author thinkpad
 *
 */
public class CustomerDaoImpl extends BaseDaoImpl<Customer> implements CustomerDao {
	
	//父类提供了有参构造，子类继承父类，子类必须定义自己的无参构造
	public CustomerDaoImpl() {
		super(Customer.class);
	}


}
