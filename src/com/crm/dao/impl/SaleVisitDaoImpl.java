package com.crm.dao.impl;

import com.crm.dao.SaleVisitDao;
import com.crm.domain.SaleVisit;
/**
 * 客户拜访记录的Dao实现类
 * @author thinkpad
 *
 */
public class SaleVisitDaoImpl extends BaseDaoImpl<SaleVisit> implements SaleVisitDao{

	public SaleVisitDaoImpl() {
		super(SaleVisit.class);
	}
	
}
