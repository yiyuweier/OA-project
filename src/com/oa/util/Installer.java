package com.oa.util;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.oa.bean.Privilege;
import com.oa.bean.User;


@Component
@Transactional
public class Installer {
	
	@Resource
	private SessionFactory seesionfactory;
	
	@Transactional
	public void install(){
		Session session = seesionfactory.getCurrentSession();
		
		//���泬������Ա
		User user = new User();
		user.setLoginname("admin");
		user.setU_name("��������Ա");
		user.setPassword(DigestUtils.md5Hex("admin"));
		session.save(user);
		
		//����Ȩ������
		Privilege priv1,priv2,priv3,priv4,priv5,priv6,priv7,priv11,priv12,priv13,priv14;
		priv1 = new Privilege("ϵͳ����",null,null,"icon-cog");	
		session.save(priv1);
		
		priv11 = new Privilege("��λ����","RoleServlet_roleManageUI",priv1,"");	
		session.save(priv11);
		session.save(new Privilege("��λ���","showNewPostionDlg()",priv11,"icon-add"));
		session.save(new Privilege("��λɾ��","deleterole()",priv11,"icon-remove"));
		session.save(new Privilege("��λ�޸�","showEditPostionDlg()",priv11,"icon-edit"));
		
		priv12 = new Privilege("���Ź���","DepartmentServlet_DepartmentManageUI",priv1,"");
		session.save(priv12);
		session.save(new Privilege("�������","showNewDepartment()",priv12,"icon-add"));
		session.save(new Privilege("����ɾ��","submitDeleteDepartment()",priv12,"icon-remove"));
		session.save(new Privilege("�����޸�","showEditDepartment()",priv12,"icon-edit"));
		
		priv13 = new Privilege("�û�����","UserServlet_userManageUI",priv1,"");
		session.save(priv13);
		session.save(new Privilege("�û����","showNewUserDlg()",priv13,"icon-add"));
		session.save(new Privilege("�û�ɾ��","submitDeleteUser()",priv13,"icon-remove"));
		session.save(new Privilege("�û��޸�","showEditUserDlg()",priv13,"icon-edit"));
		session.save(new Privilege("��ʼ������","submitResetPassword()",priv13,"icon-cog"));
		
//		priv14 = new Privilege("���̹���","FlowDeployServlet_FlowManageUI",priv1);
//		session.save(priv14);
		
		priv14 = new Privilege("���̹���","FlowDeployServlet_FlowManageUI",priv1,"");
		session.save(priv14);
		session.save(new Privilege("�½����̷���","showNewFlowCategoryDlg()",priv14,"icon-add"));
		session.save(new Privilege("��������","showDeployFlowDlg()",priv14,"icon-add"));
		session.save(new Privilege("�༭����","showEditFlowDlg()",priv14,"icon-edit"));
		session.save(new Privilege("ɾ������","submitDeleteFlow()",priv14,"icon-remove"));
		
//		priv2 = new Privilege("�ҵ�����",null,null,"icon-arrow");
//		session.save(priv2);
//		session.save(new Privilege("�����µ�����","./jsp/flow/NewFlow.jsp",priv2,""));
//		session.save(new Privilege("���е�����","./jsp/flow/RunMyFlow.jsp",priv2,""));
//		
//		priv3 = new Privilege("�ҵ�����",null,null,"");
//		session.save(priv3);
//		session.save(new Privilege("��������","./jsp/jbpm/AllMyTasks.jsp",priv3,""));
		
		priv2 = new Privilege("Эͬ�칫",null,null,"icon-arrow");
		session.save(priv2);
		session.save(new Privilege("�½�����","./jsp/flow/NewFlow.jsp",priv2,""));
		session.save(new Privilege("��������","./jsp/flow/RunMyFlow.jsp",priv2,""));
		session.save(new Privilege("��������","./jsp/jbpm/AllMyTasks.jsp",priv2,""));
		
		
//		priv9 = new Privilege("վ����Ϣ",null,null);
//		session.save(priv9);
//		session.save(new Privilege("�ռ���","msg_info_listReceive",priv9));
//		session.save(new Privilege("������","",priv9));
//		session.save(new Privilege("�½���Ϣ","",priv9));
		
		priv4 = new Privilege("�ļ�����",null,null,"icon-foldermanage");
		session.save(priv4);
		
//		Privilege priv41 = new Privilege("�ҵ��ļ�","FileServlet_FileUI",priv4,"");
		Privilege priv42 = new Privilege("������Դ","FileServlet_PublicFileUI",priv4,"");
		Privilege priv43 = new Privilege("������Դ","FileServlet_PrivateFileUI",priv4,"");
		Privilege priv44 = new Privilege("���ϴ���Դ","FileServlet_FileUI",priv4,"");
//		session.save(priv41);
		session.save(priv42);
		session.save(priv43);
		session.save(priv44);
//		session.save(new Privilege("�ϴ������ļ�","showUploadFile()",priv41,"icon-folderadd"));
//		session.save(new Privilege("�����ļ�","download1()",priv41,"icon-foldergo"));
//		session.save(new Privilege("ɾ���ļ�","submitDeleteFile()",priv41,"icon-foldergo"));
		session.save(new Privilege("�ϴ������ļ�","showUploadFileForPublic()",priv42,"icon-folderadd"));
		session.save(new Privilege("���ع����ļ�","downloadForPublic()",priv42,"icon-foldergo"));
		session.save(new Privilege("ɾ�������ļ�","submitDeleteFileForPublic()",priv42,"icon-foldergo"));
		
		session.save(new Privilege("�ϴ������ļ�","showUploadPrivateFile()",priv43,"icon-folderadd"));
		session.save(new Privilege("���ظ����ļ�","downloadForPrivate()",priv43,"icon-foldergo"));
		session.save(new Privilege("ɾ�������ļ�","submitDeleteFileForPrivate()",priv43,"icon-foldergo"));
		
		session.save(new Privilege("�������ϴ������ļ�","download1()",priv44,"icon-foldergo"));
		session.save(new Privilege("ɾ�����ϴ������ļ�","submitDeleteFile()",priv44,"icon-foldergo"));
		
//		session.save(new Privilege("����ļ�","",priv10));
		
		priv5 = new Privilege("ͨѶ¼",null,null,"icon-recordbook");
		session.save(priv5);
		session.save(new Privilege("ͨѶ¼","./jsp/Contacts/Contacts.jsp",priv5,""));
		
		priv6 = new Privilege("��������",null,null,"icon-car");
		session.save(priv6);
		Privilege priv61 = new Privilege("������Ϣ","CarServlet_CarInfoUI",priv6,"");
		session.save(priv61);
		session.save(new Privilege("����������ز���","",priv61,""));
		session.save(new Privilege("������Ϣ","",priv61,""));
//		session.save(new Privilege("ɾ������","submitDeleteCar()",priv61,"icon-remove"));
//		session.save(new Privilege("������ʷ��¼","showHistoryCar()",priv61,"icon-book"));
//		session.save(new Privilege("����Ԥ����¼","showBookCarHistory()",priv61,"icon-book"));
		
		
		Privilege priv62 = new Privilege("����Ԥ��","CarbookServlet_CarBookUI",priv6,"");
		session.save(priv62);
		session.save(new Privilege("��������Ԥ��","showBookCarDlg()",priv62,"icon-add"));
		session.save(new Privilege("ɾ������Ԥ��","submitDeleteBookInfo()",priv62,"icon-remove"));
		
		
		priv7 = new Privilege("�����ҹ���",null,null,"icon-book");
		session.save(priv7);
		Privilege priv71 = new Privilege("��������Ϣ","MeetingServlet_MeetInfoUI",priv7,"");
		session.save(priv71);
		session.save(new Privilege("�����ҹ�����ز���","",priv71,""));
		session.save(new Privilege("��������Ϣ","",priv71,""));
//		session.save(new Privilege("ɾ��������","submitDeleteMeetingRoom()",priv71,"icon-remove"));
//		session.save(new Privilege("��������ʷ��¼","showHistoryMeetingRoom()",priv71,"icon-book"));
//		session.save(new Privilege("Ԥ����ʷ��¼","showBookMeetingRoomHistory()",priv71,"icon-book"));
		
		Privilege priv72 = new Privilege("����Ԥ��","MeetingbookServlet_MeetingBook",priv7,"");
		session.save(priv72);
		session.save(new Privilege("��������Ԥ��","showBookMeetingRoomDlg()",priv72,"icon-add"));
		session.save(new Privilege("ɾ������Ԥ��","submitDeleteBookInfo()",priv72,"icon-remove"));
		
		
		
		
		
		
		
		
		
	
		
	}
	public static void main(String[] args) {
		ApplicationContext application = new ClassPathXmlApplicationContext("applicationContext.xml");
		Installer installer = (Installer) application.getBean("installer");
		installer.install();
	}

}
