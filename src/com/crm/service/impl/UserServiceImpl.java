package com.crm.service.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.transaction.annotation.Transactional;

import com.crm.dao.UserDao;
import com.crm.domain.LinkMan;
import com.crm.domain.PageBean;
import com.crm.domain.User;
import com.crm.service.UserService;
/**
 * UserService的实现类
 * @author thinkpad
 *
 */
@Transactional
public class UserServiceImpl implements UserService {
	
	//注入Dao
	private UserDao userDao;
	
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	//业务层用户注册的方法
	public void regist(User user) {
		//对密码进行加密处理
		//user.setUser_password(MD5Utils.md5(user.getUser_password()));
		user.setUser_password(user.getUser_password());
		user.setUser_state("1");
		//调用Dao
		userDao.save(user);
	}

	@Override
	//业务层用户登录的方法
	public User login(User user) {
		//user.setUser_password(MD5Utils.md5(user.getUser_password()));
		user.setUser_password(user.getUser_password());
		//调用Dao
		return userDao.login(user);
	}

	@Override
	public List<User> findAll() {
		return userDao.findAll();
	}
	/**
	 * 查找用户账号是否存在
	 */
	@Override
	public User isRegist(User user) {
		return userDao.isRegist(user);
	}

	//更改密码时的操作
	@Override
	public void update(User user) {
		userDao.update(user);
		
	}
	
	/**
	 * 分页查询用户的方法
	 */
	@Override
	public PageBean<User> find(DetachedCriteria detachedCriteria, Integer currPage, Integer pageSize) {
		PageBean<User> pageBean=new PageBean<User>();
		//设置当前页数
		pageBean.setCurrPage(currPage);
		//设置每页显示记录数
		pageBean.setPageSize(pageSize);
		//设置总记录数
		Integer totalCount=userDao.findCount(detachedCriteria);
		pageBean.setTotalCount(totalCount);
		//设置总页数,10/3 就是4
		double tc=totalCount;
		Double num=Math.ceil(tc/pageSize);
		pageBean.setTotalPage(num.intValue());
		//每页显示数据的集合
		Integer begin=(currPage-1)*pageSize;
		List<User> list=userDao.findByPage(detachedCriteria,begin,pageSize);
		pageBean.setList(list);
		return pageBean;
	}

}
