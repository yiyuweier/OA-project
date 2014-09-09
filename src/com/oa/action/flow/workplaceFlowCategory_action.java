package com.oa.action.flow;

import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.oa.action.andirod.servlet.OaServlet;
import com.oa.bean.FlowCategory;
import com.oa.bean.FlowProcess;
import com.oa.service.impl.FlowCategoryService;
import com.opensymphony.xwork2.ActionContext;


@Controller
@Scope("prototype")
public class workplaceFlowCategory_action extends OaServlet{
	
	@Resource
	FlowCategoryService flowcategoryService;
	
	public String execute(){
		List<FlowCategory> result = flowcategoryService.findAll();
		JSONArray flowcategoryJsonArray = new JSONArray();
		JSONObject flwocategoryJsonobject = new JSONObject();
		for(FlowCategory flowcategory : result){
			for(FlowProcess flowprocess : flowcategory.getFlowProcess()){
				flwocategoryJsonobject.put("flowname", flowprocess.getF_name());
				flwocategoryJsonobject.put("flowbelong", flowcategory.getF_name());
				flwocategoryJsonobject.put("flowdesc", flowprocess.getF_desc());
				flwocategoryJsonobject.put("newflow", "workplace_"+flowprocess.getF_key()+"?flowProcessId="+flowprocess.getF_id());
				flwocategoryJsonobject.put("viewimage", "jsp/jbpm/AllImage.jsp?flowname="+flowprocess.getF_name());
				flowcategoryJsonArray.add(flwocategoryJsonobject);
			}
			
		}
		System.out.println("{\"totel\":"+result.size()+",\"rows\":"+flowcategoryJsonArray.toString()+"}");
		outprint("{\"totel\":"+result.size()+",\"rows\":"+flowcategoryJsonArray.toString()+"}");
		return null;
	}

}
