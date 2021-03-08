package com.crm.web.action;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.crm.domain.Customer;
import com.crm.domain.LinkMan;
import com.crm.domain.PageBean;
import com.crm.service.CustomerService;
import com.crm.utils.UploadUtils;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
/**
 * 用户管理的Action类
 * @author thinkpad
 *
 */
public class CustomerAction extends ActionSupport implements ModelDriven<Customer> {

	//提供模型驱动使用的对象
	private Customer customer=new Customer();
	@Override
	public Customer getModel() {
		return customer;
	}
	
	//注入Service
	private CustomerService customerService;
	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}
	
	//使用set方法的方式接受数据
	private Integer currPage=1;
	
	public void setCurrPage(Integer currPage) {
		if(currPage==null) {
			currPage=1;
		}
		this.currPage = currPage;
	}
	//使用set方法接收每页显示记录数
	private Integer pageSize=3;
	
	public void setPageSize(Integer pageSize) {
		if(pageSize==null) {
			pageSize=3;
		}
		this.pageSize = pageSize;
	}
	/**
	 * 文件上传提供的三个属性
	 * 文件名称与客户资质中的name对应，name为upload
	 */
	private String uploadFileName;  //文件名称
	private File upload;  //上传文件
	private String uploadContentType;  //文件类型
	
	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}
	public void setUpload(File upload) {
		this.upload = upload;
	}
	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}
	/**
	 * 客户管理：跳转到添加页面的方法：saveUI
	 */
	public String saveUI() {
		
		return "saveUI";
	}
	/**
	 * 保存客户的方法：save
	 * @throws IOException 
	 */
	public String save() throws IOException {
		//上传图片:不为空进行文件的上传，为空直接保存
		if(upload!=null) {
			//文件上传
			//设置文件上传路径
			String path="E:\\upload";
			//一个目录下存放相同文件名：随机文件名
			String uuidFileName=UploadUtils.getUuidFileName(uploadFileName);
			//一个目录下存放的文件过多：目录分离
			String realPath=UploadUtils.getPath(uuidFileName);
			//创建目录
			String url=path+realPath;
			File file=new File(url);
			if(!file.exists()) {
				file.mkdirs();
			}
			//文件上传
			File dictFile=new File(url+"/"+uuidFileName);
			FileUtils.copyFile(upload, dictFile);
			//设置image属性的值
			customer.setCust_image(url+"/"+uuidFileName);
		}
		//保存客户
		customerService.save(customer);
		return "saveSuccess";
	}
	/**
	 * 分页查询客户的方法：findAll
	 * @return
	 */
	public String findAll() {
		//接受参数：分页参数
		//最好使用DetachedCriteria对象(条件查询---带分页，使用这个对象非常方便,即查询的是Customer对象)
		DetachedCriteria detachedCriteria=DetachedCriteria.forClass(Customer.class);
		
		//设置条件：（在web层设置条件）
		if(customer.getCust_name()!=null) {
			//输入名称
			detachedCriteria.add(Restrictions.like("cust_name", "%"+customer.getCust_name()+"%"));
		}
		if(customer.getBaseDictSource()!=null) {
			if(customer.getBaseDictSource().getDict_id()!=null && !"".equals(customer.getBaseDictSource().getDict_id())) {
				//输入来源
				detachedCriteria.add(Restrictions.eq("baseDictSource.dict_id", customer.getBaseDictSource().getDict_id()));
			}
		}
		if(customer.getBaseDictLevel()!=null) {
			if(customer.getBaseDictLevel().getDict_id()!=null && !"".equals(customer.getBaseDictLevel().getDict_id())) {
				//输入级别
				detachedCriteria.add(Restrictions.eq("baseDictLevel.dict_id", customer.getBaseDictLevel().getDict_id()));
			}
		}
		if(customer.getBaseDictIndustry()!=null) {
			if(customer.getBaseDictIndustry().getDict_id()!=null && !"".equals(customer.getBaseDictIndustry().getDict_id())) {
				//输入行业
				detachedCriteria.add(Restrictions.eq("baseDictIndustry.dict_id", customer.getBaseDictIndustry().getDict_id()));
			}
		}
		
		//调用业务层查询
		PageBean<Customer> pageBean=customerService.findByPage(detachedCriteria,currPage,pageSize);
		//把pageBean放入值栈里
		ActionContext.getContext().getValueStack().push(pageBean);
		System.out.println(pageBean.toString());
		return "findAll";
	}
	/**
	 * 综合查询里的分页查询客户：totalFindAll
	 */
	public String totalFindAll() {
		//接受参数：分页参数
		//最好使用DetachedCriteria对象(条件查询---带分页，使用这个对象非常方便,即查询的是Customer对象)
		DetachedCriteria detachedCriteria=DetachedCriteria.forClass(Customer.class);
		
		//设置条件：（在web层设置条件）
		if(customer.getCust_name()!=null) {
			//输入名称
			detachedCriteria.add(Restrictions.like("cust_name", "%"+customer.getCust_name()+"%"));
		}
		if(customer.getCust_mobile()!=null) {
			//输入手机
			detachedCriteria.add(Restrictions.like("cust_mobile", "%"+customer.getCust_mobile()+"%"));
		}
		if(customer.getBaseDictSource()!=null) {
			if(customer.getBaseDictSource().getDict_id()!=null && !"".equals(customer.getBaseDictSource().getDict_id())) {
				//输入来源
				detachedCriteria.add(Restrictions.eq("baseDictSource.dict_id", customer.getBaseDictSource().getDict_id()));
			}
		}
		if(customer.getBaseDictLevel()!=null) {
			if(customer.getBaseDictLevel().getDict_id()!=null && !"".equals(customer.getBaseDictLevel().getDict_id())) {
				//输入级别
				detachedCriteria.add(Restrictions.eq("baseDictLevel.dict_id", customer.getBaseDictLevel().getDict_id()));
			}
		}
		if(customer.getBaseDictIndustry()!=null) {
			if(customer.getBaseDictIndustry().getDict_id()!=null && !"".equals(customer.getBaseDictIndustry().getDict_id())) {
				//输入行业
				detachedCriteria.add(Restrictions.eq("baseDictIndustry.dict_id", customer.getBaseDictIndustry().getDict_id()));
			}
		}
		
		//调用业务层查询
		PageBean<Customer> pageBean=customerService.findByPage(detachedCriteria,currPage,pageSize);
		//把pageBean放入值栈里
		ActionContext.getContext().getValueStack().push(pageBean);
		System.out.println(pageBean.toString());
		return "totalFindAll";
	}
	/**
	 * 删除客户的方法：delete
	 */
	public String delete() {
		//先查询，再删除。到时候可以做级联删除
		customer=customerService.findById(customer.getCust_id());
		//删除图片
		if(customer.getCust_image()!=null) {
			File file=new File(customer.getCust_image());
			if(file.exists()) {
				file.delete();
			}
		}
		//删除客户
		customerService.delete(customer);
		return "deleteSuccess";
	}
	/**
	 * 编辑客户的方法：edit
	 */
	public String edit() {
		//根据ID查询，跳转页面，回显数据
		customer=customerService.findById(customer.getCust_id());
		//将customer传递到页面：
		//两种方式：第一种，手动压栈；第二种，因为模型驱动对象默认在栈顶
		//如果使用第一种方式：回显数据      使用struts2的方式 <s:textfield name="cust_name">
		//如果使用第二种方式：回显数据  <s:textfield value="%{model.cust_name}">
		//这是第一种方式   ActionContext.getContext().getValueStack().push(customer);
		//这里使用第二种方式，什么都不用写，但struts2标签的value要用到%
		//跳转页面
		return "editSuccess";
	}
	/**
	 * 修改客户的方法：update
	 * @throws IOException 
	 */
	public String update() throws IOException {
		//文件项是否被选，如果选了，就删除原有文件，上传新文件；如果没选，使用原有文件即可
		if(upload!=null) {
			//文件项被选
			//删除原有文件
			String cust_image=customer.getCust_image();
			if(cust_image!=null || "".equals(cust_image)) {
				File file=new File(cust_image);
				file.delete();
			}
			//上传新文件
			//设置文件上传路径
			String path="E:\\upload";
			//一个目录下存放相同文件名：随机文件名
			String uuidFileName=UploadUtils.getUuidFileName(uploadFileName);
			//一个目录下存放的文件过多：目录分离
			String realPath=UploadUtils.getPath(uuidFileName);
			//创建目录
			String url=path+realPath;
			File file=new File(url);
			if(!file.exists()) {
				file.mkdirs();
			}
			//文件上传
			File dictFile=new File(url+"/"+uuidFileName);
			FileUtils.copyFile(upload, dictFile);
			//设置文件上传的新路径
			customer.setCust_image(url+"/"+uuidFileName);
		}
		customerService.update(customer);
		return "updateSuccess";
	}
	/**
	 * 用js异步加载，显示客户
	 * @throws IOException 
	 */
	public String findAllCustomer() throws IOException {
		List<Customer> list = customerService.findAll();
		//将list转成JSON
		JsonConfig jsonConfig=new JsonConfig();
		jsonConfig.setExcludes(new String[] {"linkMans","baseDictSource","baseDictLevel","baseDictIndustry"});
		//转成JSON
		JSONArray jsonArray=JSONArray.fromObject(list,jsonConfig);
		System.out.println(jsonArray.toString());
		ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
		ServletActionContext.getResponse().getWriter().print(jsonArray.toString());
		return NONE;
	}
	/**
	 * 分页查询客户行业的方法:findIndustry
	 */
	public String findIndustry() {
		//创建离线条件查询
		DetachedCriteria detachedCriteria=DetachedCriteria.forClass(Customer.class);
		//设置条件
		if(customer.getBaseDictIndustry()!=null) {
			if(customer.getBaseDictIndustry().getDict_id()!=null && !"".equals(customer.getBaseDictIndustry().getDict_id())) {
				//输入行业
				detachedCriteria.add(Restrictions.eq("baseDictIndustry.dict_id", customer.getBaseDictIndustry().getDict_id()));
			}
		}
		//调用业务层
		PageBean<Customer> pageBean=customerService.findIndustry(detachedCriteria,currPage,pageSize);
		//把pageBean放入值栈里
		ActionContext.getContext().getValueStack().push(pageBean);
		return "findIndustry";
	}
	/**
	 * 分页查询客户来源的方法:findSource
	 */
	public String findSource() {
		//创建离线条件查询
				DetachedCriteria detachedCriteria=DetachedCriteria.forClass(Customer.class);
				//设置条件
				if(customer.getBaseDictSource()!=null) {
					if(customer.getBaseDictSource().getDict_id()!=null && !"".equals(customer.getBaseDictSource().getDict_id())) {
						//输入来源
						detachedCriteria.add(Restrictions.eq("baseDictSource.dict_id", customer.getBaseDictSource().getDict_id()));
					}
				}
				//调用业务层
				PageBean<Customer> pageBean=customerService.findSource(detachedCriteria,currPage,pageSize);
				//把pageBean放入值栈里
				ActionContext.getContext().getValueStack().push(pageBean);
				return "findSource";
	}
}
