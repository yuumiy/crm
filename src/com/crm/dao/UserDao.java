package com.crm.dao;

import com.crm.domain.User;

/**
 * 用户管理的Dao接口
 * @author thinkpad
 *
 */
public interface UserDao extends BaseDao<User>{

	User login(User user);

	User isRegist(User user);

}
