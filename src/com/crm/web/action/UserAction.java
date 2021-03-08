package com.crm.web.action;

import java.io.IOException;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.crm.domain.Customer;
import com.crm.domain.LinkMan;
import com.crm.domain.PageBean;
import com.crm.domain.User;
import com.crm.service.UserService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONArray;
/**
 * 用户管理的Action的类
 * @author thinkpad
 *
 */
public class UserAction extends ActionSupport implements ModelDriven<User> {
	//模型驱动使用的对象,页面数据的收集对象
	private User user=new User();
	@Override
	public User getModel() {
		
		return user;
	}
	//注入Service
	private UserService userService;
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/**
	 * 用户注册的方法：regist
	 */
	public String regist() {
		User existCode=userService.isRegist(user);
		String user_yzm=ServletActionContext.getRequest().getParameter("user_yzm");
		System.out.println(user);
		if("".equals(user.getUser_name()) || "".equals(user.getUser_password()) || "".equals(user.getUser_code())) {
			this.addActionError("注册信息不能为空");
			return "regist";
		}else if(existCode!=null){
			this.addActionError(existCode.getUser_code()+"用户账号已存在");
			return "regist";
		}else if(!"8713".equals(user_yzm)){
			this.addActionError("验证码错误");
			return "regist";
		}else{
			userService.regist(user);
			return "login";
		}
	}
	/**
	 * 用户登录的方法：login
	 */
	public String login() {
		System.out.println(user);
		String user_yzm=ServletActionContext.getRequest().getParameter("user_yzm");
		System.out.println(user_yzm);
		//调用业务层查询用户
		User existUser=userService.login(user);
		if(existUser==null) {
			//登录失败,添加错误信息
			this.addActionError("用户名或密码错误");
			return "login";
		}else if(!"8713".equals(user_yzm)){
			//判断验证码是否正确
			this.addActionError("验证码错误");
			return "login";
		}else {
			//登录成功
			ServletActionContext.getRequest().getSession().setAttribute("existUser", existUser);
			return "success";
		}
	}
	/**
	 * 用js异步加载，显示业务员
	 * @throws IOException 
	 */
	public String findAllUser() throws IOException {
		List<User> list = userService.findAll();
		//转成JSON,要去掉某些没用的东西才需要jsonConfig对象，这里没有List不会造成死循环，所以不用去掉
		JSONArray jsonArray=JSONArray.fromObject(list);
		ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
		ServletActionContext.getResponse().getWriter().print(jsonArray.toString());
		return NONE;
	}
	/**
	 * 用户退出的方法
	 */
	public String exit() {
		ServletActionContext.getRequest().getSession().removeAttribute("existUser");
		return "login";
	}
	/**
	 * 跳转到用户修改密码的UI界面
	 * @return
	 */
	public String alterPasswordUI() {
		User alterPass=(User) ServletActionContext.getRequest().getSession().getAttribute("existUser");
		ServletActionContext.getRequest().getSession().setAttribute("alterPass", alterPass);
		return "alterUI";
	}
	/**
	 * 修改密码并跳转到登录页面
	 */
	public String alterPassword() {
		String user_password=ServletActionContext.getRequest().getParameter("user_password");
		System.out.println(user_password);
		System.out.println("--------------");
		User user=(User) ServletActionContext.getRequest().getSession().getAttribute("alterPass");
		System.out.println(user);
		if("".equals(user_password)) {
			this.addActionError("密码不能为空");
			return "alterUI";
		}else {
			user.setUser_password(user_password);
			System.out.println(user);
			userService.update(user);
		}
		return "login";
	}
	
	//分页参数
		private Integer currPage=1;
		private Integer pageSize=3;
		
		public void setCurrPage(Integer currPage) {
			if(currPage==null) {
				currPage=1;
			}
			this.currPage = currPage;
		}

		public void setPageSize(Integer pageSize) {
			if(pageSize==null) {
				pageSize=3;
			}
			this.pageSize = pageSize;
		}

		/**
		 * 分页查询用户的方法:find
		 */
		public String find() {
			//创建离线条件查询
			DetachedCriteria detachedCriteria=DetachedCriteria.forClass(User.class);
			//设置条件
			if(user.getUser_name()!=null) {
				//输入名称
				detachedCriteria.add(Restrictions.like("user_name", "%"+user.getUser_name()+"%"));
			}
			//调用业务层
			PageBean<User> pageBean=userService.find(detachedCriteria,currPage,pageSize);
			//把pageBean放入值栈里
			ActionContext.getContext().getValueStack().push(pageBean);
			return "find";
		}
	
}
