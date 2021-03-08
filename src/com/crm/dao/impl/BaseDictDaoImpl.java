package com.crm.dao.impl;

import java.util.List;

import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import com.crm.dao.BaseDictDao;
import com.crm.domain.BaseDict;
/**
 * 字典Dao的实现类
 * @author thinkpad
 *
 */
public class BaseDictDaoImpl extends BaseDaoImpl<BaseDict> implements BaseDictDao {

	public BaseDictDaoImpl() {
		super(BaseDict.class);
	}

	@Override
	//根据类型编码查询字典数据，得到一个集合List
	public List<BaseDict> findByTypeCode(String dict_type_code) {	
		return (List<BaseDict>) this.getHibernateTemplate().find("from BaseDict where dict_type_code=?",dict_type_code);
	}
}
