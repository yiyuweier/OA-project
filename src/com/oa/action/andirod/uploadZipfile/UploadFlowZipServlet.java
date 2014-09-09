package com.oa.action.andirod.uploadZipfile;

import java.io.File;
import java.io.FileInputStream;
import java.util.zip.ZipInputStream;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Controller;

import com.oa.action.andirod.servlet.OaServlet;
import com.oa.bean.FlowCategory;
import com.oa.bean.FlowProcess;
import com.oa.dao.FlowCategoryDao;
import com.oa.dao.FlowProcessDao;
import com.oa.jbpm.operation.BaseJbpmOperation;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;


/**
 * 上传流程的zip文件并部署流程，添加必要的信息到流程信息oa_flowprocess表中
 * @author xg_liu
 *
 */

public class UploadFlowZipServlet extends OaServlet{
	
	private File image; //上传的文件
    private String imageFileName; //文件名称
    private String imageContentType; //文件类型
    
    private String FlowProcess_name;//流程名称
    private Long categoryId;//流程对应的分类id
    private String FlowProcess_desc;//流程描述
    
    @Resource
    BaseJbpmOperation basejbpmoperation;
    @Resource
    FlowCategoryDao flowcategorydao;
    @Resource
    FlowProcessDao flowprocessdao;

    public File getImage() {
		return image;
	}



	public void setImage(File image) {
		this.image = image;
	}



	public String getImageFileName() {
		return imageFileName;
	}



	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}



	public String getImageContentType() {
		return imageContentType;
	}



	public void setImageContentType(String imageContentType) {
		this.imageContentType = imageContentType;
	}
	
	



	public String getFlowProcess_name() {
		return FlowProcess_name;
	}



	public void setFlowProcess_name(String flowProcess_name) {
		FlowProcess_name = flowProcess_name;
	}



	public Long getCategoryId() {
		return categoryId;
	}



	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	
	



	public String getFlowProcess_desc() {
		return FlowProcess_desc;
	}



	public void setFlowProcess_desc(String flowProcess_desc) {
		FlowProcess_desc = flowProcess_desc;
	}



	public String execute() throws Exception {
        String realpath = ServletActionContext.getServletContext().getRealPath("/file");//获取真实的路径
        //D:\apache-tomcat-6.0.18\webapps\struts2_upload\images
        System.out.println("realpath: "+realpath);
        System.out.println("imagefilename"+imageFileName);
        if (image != null) {
            File savefile = new File(new File(realpath), imageFileName);
            if (!savefile.getParentFile().exists())
                savefile.getParentFile().mkdirs();
            FileUtils.copyFile(image, savefile);
            ActionContext.getContext().put("message", "文件上传成功");
            System.out.println("文件上传成功");
        }//上传文件zip
        int index = imageFileName.indexOf(".");
        String imageName = imageFileName.substring(0,index);//获取文件流程的名称
        FileInputStream in = new FileInputStream(realpath+"\\"+imageFileName);
        ZipInputStream zin = new ZipInputStream(in);
        String deployid = basejbpmoperation.deploy(zin);//通过zip部署流程
        System.out.println("发布成功，版本号为："+deployid);
        System.out.println("种类Id为："+categoryId);
        FlowProcess flowprocess = new FlowProcess();
        flowprocess.setF_id(deployid);
        flowprocess.setF_name(FlowProcess_name);
        flowprocess.setF_desc(FlowProcess_desc);
        flowprocess.setF_key(imageName);//
        flowprocess.setF_url(imageName);//设置该流程所对应的页面的url地址
        flowprocess.setFlowcategory(flowcategorydao.findById(categoryId));
        flowprocessdao.save(flowprocess);//新部署的流程存入flowprocess中
        outprint("{\"success\":true}");
        return null;
    }

}
