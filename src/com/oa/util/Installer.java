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
		
		//保存超级管理员
		User user = new User();
		user.setLoginname("admin");
		user.setU_name("超级管理员");
		user.setPassword(DigestUtils.md5Hex("admin"));
		session.save(user);
		
		//保存权限数据
		Privilege priv1,priv2,priv3,priv4,priv5,priv6,priv7,priv11,priv12,priv13,priv14;
		priv1 = new Privilege("系统管理",null,null,"icon-cog");	
		session.save(priv1);
		
		priv11 = new Privilege("岗位管理","RoleServlet_roleManageUI",priv1,"");	
		session.save(priv11);
		session.save(new Privilege("岗位添加","showNewPostionDlg()",priv11,"icon-add"));
		session.save(new Privilege("岗位删除","deleterole()",priv11,"icon-remove"));
		session.save(new Privilege("岗位修改","showEditPostionDlg()",priv11,"icon-edit"));
		
		priv12 = new Privilege("部门管理","DepartmentServlet_DepartmentManageUI",priv1,"");
		session.save(priv12);
		session.save(new Privilege("部门添加","showNewDepartment()",priv12,"icon-add"));
		session.save(new Privilege("部门删除","submitDeleteDepartment()",priv12,"icon-remove"));
		session.save(new Privilege("部门修改","showEditDepartment()",priv12,"icon-edit"));
		
		priv13 = new Privilege("用户管理","UserServlet_userManageUI",priv1,"");
		session.save(priv13);
		session.save(new Privilege("用户添加","showNewUserDlg()",priv13,"icon-add"));
		session.save(new Privilege("用户删除","submitDeleteUser()",priv13,"icon-remove"));
		session.save(new Privilege("用户修改","showEditUserDlg()",priv13,"icon-edit"));
		session.save(new Privilege("初始化密码","submitResetPassword()",priv13,"icon-cog"));
		
//		priv14 = new Privilege("流程管理","FlowDeployServlet_FlowManageUI",priv1);
//		session.save(priv14);
		
		priv14 = new Privilege("流程管理","FlowDeployServlet_FlowManageUI",priv1,"");
		session.save(priv14);
		session.save(new Privilege("新建流程分类","showNewFlowCategoryDlg()",priv14,"icon-add"));
		session.save(new Privilege("部署流程","showDeployFlowDlg()",priv14,"icon-add"));
		session.save(new Privilege("编辑流程","showEditFlowDlg()",priv14,"icon-edit"));
		session.save(new Privilege("删除流程","submitDeleteFlow()",priv14,"icon-remove"));
		
//		priv2 = new Privilege("我的流程",null,null,"icon-arrow");
//		session.save(priv2);
//		session.save(new Privilege("发起新的流程","./jsp/flow/NewFlow.jsp",priv2,""));
//		session.save(new Privilege("运行的流程","./jsp/flow/RunMyFlow.jsp",priv2,""));
//		
//		priv3 = new Privilege("我的任务",null,null,"");
//		session.save(priv3);
//		session.save(new Privilege("待办任务","./jsp/jbpm/AllMyTasks.jsp",priv3,""));
		
		priv2 = new Privilege("协同办公",null,null,"icon-arrow");
		session.save(priv2);
		session.save(new Privilege("新建事项","./jsp/flow/NewFlow.jsp",priv2,""));
		session.save(new Privilege("运行事项","./jsp/flow/RunMyFlow.jsp",priv2,""));
		session.save(new Privilege("待办事项","./jsp/jbpm/AllMyTasks.jsp",priv2,""));
		
		
//		priv9 = new Privilege("站内消息",null,null);
//		session.save(priv9);
//		session.save(new Privilege("收件箱","msg_info_listReceive",priv9));
//		session.save(new Privilege("发件箱","",priv9));
//		session.save(new Privilege("新建消息","",priv9));
		
		priv4 = new Privilege("文件管理",null,null,"icon-foldermanage");
		session.save(priv4);
		
//		Privilege priv41 = new Privilege("我的文件","FileServlet_FileUI",priv4,"");
		Privilege priv42 = new Privilege("公共资源","FileServlet_PublicFileUI",priv4,"");
		Privilege priv43 = new Privilege("个人资源","FileServlet_PrivateFileUI",priv4,"");
		Privilege priv44 = new Privilege("已上传资源","FileServlet_FileUI",priv4,"");
//		session.save(priv41);
		session.save(priv42);
		session.save(priv43);
		session.save(priv44);
//		session.save(new Privilege("上传公众文件","showUploadFile()",priv41,"icon-folderadd"));
//		session.save(new Privilege("下载文件","download1()",priv41,"icon-foldergo"));
//		session.save(new Privilege("删除文件","submitDeleteFile()",priv41,"icon-foldergo"));
		session.save(new Privilege("上传公共文件","showUploadFileForPublic()",priv42,"icon-folderadd"));
		session.save(new Privilege("下载公共文件","downloadForPublic()",priv42,"icon-foldergo"));
		session.save(new Privilege("删除公共文件","submitDeleteFileForPublic()",priv42,"icon-foldergo"));
		
		session.save(new Privilege("上传个人文件","showUploadPrivateFile()",priv43,"icon-folderadd"));
		session.save(new Privilege("下载个人文件","downloadForPrivate()",priv43,"icon-foldergo"));
		session.save(new Privilege("删除个人文件","submitDeleteFileForPrivate()",priv43,"icon-foldergo"));
		
		session.save(new Privilege("下载已上传公共文件","download1()",priv44,"icon-foldergo"));
		session.save(new Privilege("删除已上传公共文件","submitDeleteFile()",priv44,"icon-foldergo"));
		
//		session.save(new Privilege("添加文件","",priv10));
		
		priv5 = new Privilege("通讯录",null,null,"icon-recordbook");
		session.save(priv5);
		session.save(new Privilege("通讯录","./jsp/Contacts/Contacts.jsp",priv5,""));
		
		priv6 = new Privilege("车辆管理",null,null,"icon-car");
		session.save(priv6);
		Privilege priv61 = new Privilege("车辆信息","CarServlet_CarInfoUI",priv6,"");
		session.save(priv61);
		session.save(new Privilege("车辆管理相关操作","",priv61,""));
		session.save(new Privilege("车辆信息","",priv61,""));
//		session.save(new Privilege("删除车辆","submitDeleteCar()",priv61,"icon-remove"));
//		session.save(new Privilege("车辆历史记录","showHistoryCar()",priv61,"icon-book"));
//		session.save(new Privilege("车辆预定记录","showBookCarHistory()",priv61,"icon-book"));
		
		
		Privilege priv62 = new Privilege("车辆预定","CarbookServlet_CarBookUI",priv6,"");
		session.save(priv62);
		session.save(new Privilege("新增车辆预定","showBookCarDlg()",priv62,"icon-add"));
		session.save(new Privilege("删除车辆预定","submitDeleteBookInfo()",priv62,"icon-remove"));
		
		
		priv7 = new Privilege("会议室管理",null,null,"icon-book");
		session.save(priv7);
		Privilege priv71 = new Privilege("会议室信息","MeetingServlet_MeetInfoUI",priv7,"");
		session.save(priv71);
		session.save(new Privilege("会议室管理相关操作","",priv71,""));
		session.save(new Privilege("会议室信息","",priv71,""));
//		session.save(new Privilege("删除会议室","submitDeleteMeetingRoom()",priv71,"icon-remove"));
//		session.save(new Privilege("会议室历史记录","showHistoryMeetingRoom()",priv71,"icon-book"));
//		session.save(new Privilege("预定历史记录","showBookMeetingRoomHistory()",priv71,"icon-book"));
		
		Privilege priv72 = new Privilege("会议预定","MeetingbookServlet_MeetingBook",priv7,"");
		session.save(priv72);
		session.save(new Privilege("新增会议预定","showBookMeetingRoomDlg()",priv72,"icon-add"));
		session.save(new Privilege("删除会议预定","submitDeleteBookInfo()",priv72,"icon-remove"));
		
		
		
		
		
		
		
		
		
	
		
	}
	public static void main(String[] args) {
		ApplicationContext application = new ClassPathXmlApplicationContext("applicationContext.xml");
		Installer installer = (Installer) application.getBean("installer");
		installer.install();
	}

}
