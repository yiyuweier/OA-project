package com.oa.action.flow;

import java.util.List;

import javax.annotation.Resource;

import com.oa.bean.Document;
import com.oa.bean.Message;
import com.oa.bean.Vacate;
import com.oa.dao.DocumentDao;
import com.oa.dao.MessageDao;
import com.oa.dao.VacateDao;
import com.oa.service.impl.UserService;
import com.opensymphony.xwork2.ActionContext;
/**
 * 
 * @author xg_liu
 * ��ȡ�ҵ����̵���ϸ��Ϣ�����ڻ�ǩ����ǩ���Ƿ�ͨ��������ǩ����ִ�е���һ����ͼ
 * 				        ������٣�����������Ƿ�ͨ�������������ִ�е�ͼ
 * 				        ����֪ͨ���棬����Щ�˿���֪ͨ���棬��Щ��û�п�
 */
public class workplaceGetMyFlowDetail {
	
	private String D_name;//��������
	private String username;//�����̵ķ�����
	private String flowtype;//���̵����ͣ���������̣������������̣�֪ͨ��������
	private String date;
	
	public String getD_name() {
		return D_name;
	}
	public void setD_name(String d_name) {
		D_name = d_name;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getFlowtype() {
		return flowtype;
	}
	public void setFlowtype(String flowtype) {
		this.flowtype = flowtype;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}




	@Resource
	DocumentDao documentdao;
	@Resource
	VacateDao vacatedao;
	@Resource
	MessageDao messagedao;
	@Resource
	UserService userService;

	public String execute(){
		
		
		if(flowtype.equals("Document")){//֪ͨ��������
			boolean isHq = false;//�Ƿ��ڻ�ǩ��һ��
			boolean iscx = false;//��ǩ�Ƿ�Ϊ����
			boolean isNotpass = false;//�Ƿ�������ûͨ����һ��
			boolean isbgs = true;//�Ƿ��ڰ칫����һ��
			boolean iswjxf = false;//�Ƿ����ļ��·���һ��
			
			int allpeople = 0;//����Ǵ��еĻ�ǩ��������봮�л�ǩ��������
			
			List<Document> mydocumentlist = documentdao.getDocumentByInitiatorAndDocumentName(username, D_name);
			for(Document document : mydocumentlist){
//				document.setSendto(userService.findById(Long.parseLong(document.getSendto())).getU_name());
				if(mydocumentlist.size()==1){
					if(document.getSendto()==null){
						iscx = true;
						isHq = true;
					}
				}else
				if(document.getType()==1){
					
					if(document.getSendto()==null){
						allpeople = document.getD_content().split(",").length;
					}
					
					iscx = true;
					if(document.isIspass()==0){
						isNotpass = true;
						isbgs = false;
					}else if(document.isIspass()==2&&!isNotpass){
						isHq = true;
						isbgs = false;
					}
				}else if(document.getType()==2){
					if(document.isIspass()==0){
						isNotpass = true;
						isbgs = false;
					}else if(document.isIspass()==2&&!isNotpass){
						isHq = true;
						isbgs = false;
					}
				}else{
					
					iswjxf = true;
					isbgs = false;
				}
				if(!isHq&&allpeople>mydocumentlist.size()-1){
					isHq = true;
				}
			}
			System.out.println(iscx+"-"+isHq+"-"+isNotpass+"-"+isbgs+"-"+iswjxf);
			System.out.println(mydocumentlist.size());
			ActionContext.getContext().getSession().put("flowtype", flowtype);
			ActionContext.getContext().getSession().put("mydocumentlist", mydocumentlist);
			ActionContext.getContext().getSession().put("isHq", isHq);
			ActionContext.getContext().getSession().put("isNotpass", isNotpass);
			ActionContext.getContext().getSession().put("isbgs", isbgs);
			ActionContext.getContext().getSession().put("iswjxf", iswjxf);
			ActionContext.getContext().getSession().put("iscx", iscx);
			return "success";
		}else if(flowtype.equals("AskForLeave")){
			boolean issp = true;
			boolean askforleaveispass = false;
			String askforleavetype = null;
			if(D_name.equals("���")){
				askforleavetype = "1";
			}else if(D_name.equals("����")){
				askforleavetype = "2";
			}else if(D_name.equals("����")){
				askforleavetype = "3";
			}else{
				askforleavetype = "4";
			}
			List<Vacate> myvacatelist = vacatedao.getVacateByInitiatorAndVacateName(username, askforleavetype, date);
//			List<Vacate> myvacatelistClone = 
			for(Vacate vacate : myvacatelist){
//				vacate.setSendto(userService.findById(Long.parseLong(vacate.getSendto())).getU_name());
				if(vacate.isIspass()==1){
					issp = false;
					askforleaveispass = true;
				}else if(vacate.isIspass()==0){
					askforleaveispass = false;
					issp = false;
				}
			}
			ActionContext.getContext().getSession().put("flowtype", flowtype);
			ActionContext.getContext().getSession().put("issp", issp);
			ActionContext.getContext().getSession().put("askforleaveispass", askforleaveispass);
			ActionContext.getContext().getSession().put("mydocumentlist", myvacatelist);
			return "success";
			
		}else if(flowtype.equals("Message")){
			List<Message> messagelist = messagedao.getMessageByInitiatorAndMessageName(username, D_name);
//			for(Message message : messagelist){
//				message.setSendto(userService.findById(Long.parseLong(message.getSendto())).getU_name());
//			}
			ActionContext.getContext().getSession().put("flowtype", flowtype);
			ActionContext.getContext().getSession().put("mydocumentlist", messagelist);
			return "success";
			
		}
		return "success";
	}

}
