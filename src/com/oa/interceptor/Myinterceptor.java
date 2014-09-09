package com.oa.interceptor;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.oa.bean.Andirod;
import com.oa.bean.User;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class Myinterceptor extends AbstractInterceptor{

	@Override
	public String intercept(ActionInvocation ai) throws Exception {
		//ȡ�������URL
        String url = ServletActionContext.getRequest().getRequestURL().toString();
        System.out.println("url="+url);
        User user = null;
        //�Ե�¼��ע������ֱ�ӷ���,��������
        if (url.indexOf("LoginServlet_Login")!=-1 || url.indexOf("LoginServlet_LoginOut")!=-1 || url.indexOf("_toAndirod")!=-1){
            System.out.println("��½�����˳�");
            System.out.println("Andirod.isIslognfromandird()"+Andirod.isIslognfromandird());
        	return ai.invoke();
        }
        else{
            //��֤Session�Ƿ����
            if(ActionContext.getContext().getSession().isEmpty()&&!Andirod.isIslognfromandird()){
            	System.out.println("sessionΪ����andirodΪfalse��");
                //session����,ת��session������ʾҳ,������ת����¼ҳ��
                return "tologin";
            }
            else if(!ActionContext.getContext().getSession().isEmpty()||Andirod.isIslognfromandird()){
            	return ai.invoke();
            }
            else
            {
                user = (User)ActionContext.getContext().getSession().get("user");
                //��֤�Ƿ��Ѿ���¼
                if (user==null){
                	System.out.println("SessionΪ�գ�-------------------------------------------------------");
                    //��δ��¼,��ת����¼ҳ��
                    return "tologin";
                }else{               
                	System.out.println("Session��Ϊ�գ�-------------------------------------------------------"+user.getU_name());
                    return ai.invoke();
                                 
                }               
            }           
        }
    }

}
