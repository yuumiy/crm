package com.crm.dao.impl;

import java.util.List;

import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import com.crm.dao.UserDao;
import com.crm.domain.User;
/**
 * 用户管理的Dao实现类
 * @author thinkpad
 *
 */
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {

	public UserDaoImpl() {
		super(User.class);
	}

	@Override
	//Dao中根据用户名和密码查询的方法
	public User login(User user) {
		List<User> list = (List<User>) this.getHibernateTemplate().find("from User where user_code=? and user_password=?", user.getUser_code(),user.getUser_password());
		//判断是否查到该用户
		if(list.size()>0) {
			return list.get(0);
		}
		return null;		
	}

	//注册时查找用户账号是否存在
	@Override
	public User isRegist(User user) {
		List<User> list = (List<User>) this.getHibernateTemplate().find("from User where user_code=?", user.getUser_code());
		//判断是否查到该用户
		if(list.size()>0) {
			return list.get(0);
		}
		return null;
	}
	
}
