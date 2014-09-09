package com.oa.action.andirod.flow;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.oa.bean.FlowCategory;
import com.oa.service.impl.FlowCategoryService;
import com.opensymphony.xwork2.ActionContext;


@Controller
@Scope("prototype")
public class workplaceFlowCategoryServlet {
	
	@Resource
	FlowCategoryService flowcategoryService;
	
	public String execute(){
		List<FlowCategory> result = flowcategoryService.findAll();
		for(FlowCategory flowcategory : result){
			System.out.println(flowcategory.getF_name());
		}
		ActionContext.getContext().getSession().put("FlowCategories", result);
		return "success";
	}

}
