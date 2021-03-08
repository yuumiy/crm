package com.crm.service;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;

import com.crm.domain.BaseDict;
import com.crm.domain.PageBean;

/**
 * 字典的业务层的接口
 * @author thinkpad
 *
 */
public interface BaseDictService {

	List<BaseDict> findByTypeCode(String dict_type_code);

	PageBean<BaseDict> find(DetachedCriteria detachedCriteria, Integer currPage, Integer pageSize);

}
