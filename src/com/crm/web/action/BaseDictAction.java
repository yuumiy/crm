package com.crm.web.action;

import java.io.IOException;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.crm.domain.BaseDict;
import com.crm.domain.PageBean;
import com.crm.service.BaseDictService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
/**
 * 字典的Action类
 * @author thinkpad
 *
 */
public class BaseDictAction extends ActionSupport implements ModelDriven<BaseDict> {
	//模型驱动使用的对象
	private BaseDict baseDict=new BaseDict();
	@Override
	public BaseDict getModel() {
		return baseDict;
	}

	//注入Service
	private BaseDictService baseDictService;
	public void setBaseDictService(BaseDictService baseDictService) {
		this.baseDictService = baseDictService;
	}
	/**
	 * 根据类型名称查询字典的方法：findByTypeCode
	 * @throws IOException 
	 */
	public String findByTypeCode() throws IOException {
		System.out.println("typecode方法执行");
		//调用业务层查询
		List<BaseDict> list=baseDictService.findByTypeCode(baseDict.getDict_type_code());
		//将List转成JSON---------jsonlib(当前方式)     fastjson
		/**
		 * JSONConfig  转JSON的配置对象
		 * JSONArray  将数组和list集合转成JSON
		 * JSONObject  将对象和Map集合转成JSON
		 */
		JsonConfig jsonConfig=new JsonConfig();
		jsonConfig.setExcludes(new String[] {"dict_sort","dict_enable","dict_memo","dict_item_code"});
		JSONArray jsonArray = JSONArray.fromObject(list, jsonConfig);
		System.out.println(jsonArray.toString());
		//将json打印到页面
		ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
		ServletActionContext.getResponse().getWriter().print(jsonArray.toString());
		//已经转移到这个页面了，就不用再跳转了
		return NONE;
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
	 * 分页查询字典数据的方法:find
	 */
	public String find() {
		//创建离线条件查询
		DetachedCriteria detachedCriteria=DetachedCriteria.forClass(BaseDict.class);
		//设置条件
		if(baseDict.getDict_type_name()!=null) {
			//输入名称
			detachedCriteria.add(Restrictions.like("dict_type_name", "%"+baseDict.getDict_type_name()+"%"));
		}
		//调用业务层
		PageBean<BaseDict> pageBean=baseDictService.find(detachedCriteria,currPage,pageSize);
		//把pageBean放入值栈里
		ActionContext.getContext().getValueStack().push(pageBean);
		return "find";
	}
}
