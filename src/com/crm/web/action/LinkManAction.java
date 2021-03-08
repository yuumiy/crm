package com.crm.web.action;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.crm.domain.Customer;
import com.crm.domain.LinkMan;
import com.crm.domain.PageBean;
import com.crm.domain.User;
import com.crm.service.CustomerService;
import com.crm.service.LinkManService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
/**
 * 联系人的Action类
 * @author thinkpad
 *
 */
public class LinkManAction extends ActionSupport implements ModelDriven<LinkMan> {
	//加载模型驱动的对象
	private LinkMan linkMan=new LinkMan();
	
	@Override
	public LinkMan getModel() {
		return linkMan;
	}

	//注入Service，并且提供一个set方法
	private LinkManService linkManService;

	public void setLinkManService(LinkManService linkManService) {
		this.linkManService = linkManService;
	}
	//注入客户管理的Service
	private CustomerService customerService;
	
	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
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
	 * 查询联系人的列表的Action:findAll
	 */
	public String findAll() {
		//创建离线条件查询
		DetachedCriteria detachedCriteria=DetachedCriteria.forClass(LinkMan.class);
		//设置条件
		if(linkMan.getLkm_name()!=null) {
			//设置按名称查询的条件
			detachedCriteria.add(Restrictions.like("lkm_name", "%"+linkMan.getLkm_name()+"%"));
		}
		if(linkMan.getLkm_gender()!=null && !"".equals(linkMan.getLkm_gender())) {
			//设置按性别查询的条件,下拉框限制条件多一些，否则会有问题
			detachedCriteria.add(Restrictions.eq("lkm_gender",linkMan.getLkm_gender()));
		}
		//调用业务层
		PageBean<LinkMan> pageBean=linkManService.findAll(detachedCriteria,currPage,pageSize);
		//把pageBean放入值栈里
		ActionContext.getContext().getValueStack().push(pageBean);
		return "findAll";
	}
	/**
	 * 综合查询里的分页查询联系人：totalFindAll
	 */
	public String totalFindAll() {
		//创建离线条件查询
		DetachedCriteria detachedCriteria=DetachedCriteria.forClass(LinkMan.class);
		//设置条件
		if(linkMan.getLkm_name()!=null) {
			//设置名称查询
			detachedCriteria.add(Restrictions.like("lkm_name", "%"+linkMan.getLkm_name()+"%"));
		}
		if(linkMan.getLkm_mobile()!=null) {
			//设置手机查询
			detachedCriteria.add(Restrictions.like("lkm_mobile", "%"+linkMan.getLkm_mobile()+"%"));
		}
		if(linkMan.getLkm_email()!=null) {
			//设置邮箱查询
			detachedCriteria.add(Restrictions.like("lkm_email", "%"+linkMan.getLkm_email()+"%"));
		}
		if(linkMan.getLkm_qq()!=null) {
			//设置QQ查询
			detachedCriteria.add(Restrictions.like("lkm_qq", "%"+linkMan.getLkm_qq()+"%"));
		}
		if(linkMan.getLkm_position()!=null) {
			//设置职位查询
			detachedCriteria.add(Restrictions.like("lkm_position", "%"+linkMan.getLkm_position()+"%"));
		}
		if(linkMan.getLkm_gender()!=null && !"".equals(linkMan.getLkm_gender())) {
			//设置按性别查询的条件,下拉框限制条件多一些，否则会有问题
			detachedCriteria.add(Restrictions.eq("lkm_gender",linkMan.getLkm_gender()));
		}
		//调用业务层
		PageBean<LinkMan> pageBean=linkManService.findAll(detachedCriteria,currPage,pageSize);
		//把pageBean放入值栈里
		ActionContext.getContext().getValueStack().push(pageBean);
		return "totalFindAll";
	}
	/**
	 * 分页查询联系人角色的方法:find
	 */
	public String find() {
		//创建离线条件查询
		DetachedCriteria detachedCriteria=DetachedCriteria.forClass(LinkMan.class);
		//设置条件
		if(linkMan.getLkm_position()!=null) {
			//输入名称
			detachedCriteria.add(Restrictions.like("lkm_position", "%"+linkMan.getLkm_position()+"%"));
		}
		//调用业务层
		PageBean<LinkMan> pageBean=linkManService.find(detachedCriteria,currPage,pageSize);
		//把pageBean放入值栈里
		ActionContext.getContext().getValueStack().push(pageBean);
		return "find";
	}
	/**
	 * 跳转到添加联系人的页面:saveUI
	 */
	public String saveUI() {
		//查询所有客户
		List<Customer> list=customerService.findAll();
		//将List集合保存到值栈中,List集合用set方便获取，对象用push方便获取
		ActionContext.getContext().getValueStack().set("list", list);
		return "saveUI";
	}
	/**
	 * 保存联系人的方法
	 * @return
	 */
	public String save() {
		//调用业务层保存联系人
		linkManService.save(linkMan);
		return "saveSuccess";
	}
	/**
	 * 跳转到编辑页面的方法，有数据回显
	 */
	public String edit() {
		//查询某个联系人，查询所有客户
		//查询所有客户
		List<Customer> list=customerService.findAll();
		//查询某个联系人
		linkMan=linkManService.findByID(linkMan.getLkm_id());
		//将list和linkMan对象带到页面上。linkMan默认是在模型驱动里，在页面上取值直接用model.可以回显，也可以放到值栈里回显
		//用值栈数据回显，只要s标签name写对就可以回显；用模型驱动，加一个value="%{model.lkm_name}"
		//用值栈数据回显比较方便！！！要用struts2的标签才能值栈数据回显！！！用的是textfield,textarea
		ActionContext.getContext().getValueStack().set("list", list);
		ActionContext.getContext().getValueStack().push(linkMan);
		return "editSuccess";
	}
	/**
	 * 修改联系人的方法
	 */
	public String update() {
		linkManService.update(linkMan);
		return "updateSuccess";
	}
	/**
	 * 删除联系人的方法
	 */
	public String delete() {
		//堆栈中放着联系人ID，可以先查询，再删除
		linkMan = linkManService.findByID(linkMan.getLkm_id());
		linkManService.delete(linkMan);
		return "deleteSuccess";
	}
}
