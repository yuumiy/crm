package com.crm.service;

import org.hibernate.criterion.DetachedCriteria;

import com.crm.domain.LinkMan;
import com.crm.domain.PageBean;

/**
 * 联系人的Service接口
 * @author thinkpad
 *
 */
public interface LinkManService {

	PageBean<LinkMan> findAll(DetachedCriteria detachedCriteria, Integer currPage, Integer pageSize);

	void save(LinkMan linkMan);

	LinkMan findByID(Long lkm_id);

	void update(LinkMan linkMan);

	void delete(LinkMan linkMan);

	PageBean<LinkMan> find(DetachedCriteria detachedCriteria, Integer currPage, Integer pageSize);

}
