package com.crm.service;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.crm.domain.LinkMan;
import com.crm.domain.PageBean;
import com.crm.domain.User;

/**
 * 用户管理的Service接口
 * @author thinkpad
 *
 */
public interface UserService {

	void regist(User user);

	User login(User user);

	List<User> findAll();

	User isRegist(User user);

	void update(User user);

	PageBean<User> find(DetachedCriteria detachedCriteria, Integer currPage, Integer pageSize);


}
