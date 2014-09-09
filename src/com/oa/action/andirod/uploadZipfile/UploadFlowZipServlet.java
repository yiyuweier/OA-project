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
 * �ϴ����̵�zip�ļ����������̣���ӱ�Ҫ����Ϣ��������Ϣoa_flowprocess����
 * @author xg_liu
 *
 */

public class UploadFlowZipServlet extends OaServlet{
	
	private File image; //�ϴ����ļ�
    private String imageFileName; //�ļ�����
    private String imageContentType; //�ļ�����
    
    private String FlowProcess_name;//��������
    private Long categoryId;//���̶�Ӧ�ķ���id
    private String FlowProcess_desc;//��������
    
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
        String realpath = ServletActionContext.getServletContext().getRealPath("/file");//��ȡ��ʵ��·��
        //D:\apache-tomcat-6.0.18\webapps\struts2_upload\images
        System.out.println("realpath: "+realpath);
        System.out.println("imagefilename"+imageFileName);
        if (image != null) {
            File savefile = new File(new File(realpath), imageFileName);
            if (!savefile.getParentFile().exists())
                savefile.getParentFile().mkdirs();
            FileUtils.copyFile(image, savefile);
            ActionContext.getContext().put("message", "�ļ��ϴ��ɹ�");
            System.out.println("�ļ��ϴ��ɹ�");
        }//�ϴ��ļ�zip
        int index = imageFileName.indexOf(".");
        String imageName = imageFileName.substring(0,index);//��ȡ�ļ����̵�����
        FileInputStream in = new FileInputStream(realpath+"\\"+imageFileName);
        ZipInputStream zin = new ZipInputStream(in);
        String deployid = basejbpmoperation.deploy(zin);//ͨ��zip��������
        System.out.println("�����ɹ����汾��Ϊ��"+deployid);
        System.out.println("����IdΪ��"+categoryId);
        FlowProcess flowprocess = new FlowProcess();
        flowprocess.setF_id(deployid);
        flowprocess.setF_name(FlowProcess_name);
        flowprocess.setF_desc(FlowProcess_desc);
        flowprocess.setF_key(imageName);//
        flowprocess.setF_url(imageName);//���ø���������Ӧ��ҳ���url��ַ
        flowprocess.setFlowcategory(flowcategorydao.findById(categoryId));
        flowprocessdao.save(flowprocess);//�²�������̴���flowprocess��
        outprint("{\"success\":true}");
        return null;
    }

}
