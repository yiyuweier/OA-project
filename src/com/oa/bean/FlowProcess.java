package com.oa.bean;

import org.springframework.stereotype.Component;

/**
 * ���̶���
 * @author Administrator
 *
 */
@Component
public class FlowProcess {
	
	private Long id;//����id
	private String F_id;//�����������ɵ�Id
	private String F_name;//��������
	private String F_desc;//��������
	private FlowCategory flowcategory;//���̵����
	private String F_url;//��д�����̵ı�url
	private String F_key;//���̵�key
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getF_id() {
		return F_id;
	}
	public void setF_id(String f_id) {
		F_id = f_id;
	}
	public String getF_name() {
		return F_name;
	}
	public void setF_name(String f_name) {
		F_name = f_name;
	}
	public String getF_desc() {
		return F_desc;
	}
	public void setF_desc(String f_desc) {
		F_desc = f_desc;
	}
	public FlowCategory getFlowcategory() {
		return flowcategory;
	}
	public void setFlowcategory(FlowCategory flowcategory) {
		this.flowcategory = flowcategory;
	}
	public String getF_url() {
		return F_url;
	}
	public void setF_url(String f_url) {
		F_url = f_url;
	}
	public String getF_key() {
		return F_key;
	}
	public void setF_key(String f_key) {
		F_key = f_key;
	}
	
	
	
	
	

}
