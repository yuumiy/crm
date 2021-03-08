package com.crm.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import com.crm.dao.LinkManDao;
import com.crm.domain.LinkMan;
/**
 * 联系人Dao的实现类,需要继承HibernateDaoSupport
 * @author thinkpad
 *
 */
public class LinkManDaoImpl extends BaseDaoImpl<LinkMan> implements LinkManDao {
	//父类提供了有参数的构造方法，子类继承父类，在子类的构造方法中，调用父类有参数的构造方法
	public LinkManDaoImpl() {
		super(LinkMan.class);
	}


}
