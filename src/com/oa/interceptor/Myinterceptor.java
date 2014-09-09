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
		//取得请求的URL
        String url = ServletActionContext.getRequest().getRequestURL().toString();
        System.out.println("url="+url);
        User user = null;
        //对登录与注销请求直接放行,不予拦截
        if (url.indexOf("LoginServlet_Login")!=-1 || url.indexOf("LoginServlet_LoginOut")!=-1 || url.indexOf("_toAndirod")!=-1){
            System.out.println("登陆或是退出");
            System.out.println("Andirod.isIslognfromandird()"+Andirod.isIslognfromandird());
        	return ai.invoke();
        }
        else{
            //验证Session是否过期
            if(ActionContext.getContext().getSession().isEmpty()&&!Andirod.isIslognfromandird()){
            	System.out.println("session为空且andirod为false！");
                //session过期,转向session过期提示页,最终跳转至登录页面
                return "tologin";
            }
            else if(!ActionContext.getContext().getSession().isEmpty()||Andirod.isIslognfromandird()){
            	return ai.invoke();
            }
            else
            {
                user = (User)ActionContext.getContext().getSession().get("user");
                //验证是否已经登录
                if (user==null){
                	System.out.println("Session为空！-------------------------------------------------------");
                    //尚未登录,跳转至登录页面
                    return "tologin";
                }else{               
                	System.out.println("Session不为空！-------------------------------------------------------"+user.getU_name());
                    return ai.invoke();
                                 
                }               
            }           
        }
    }

}
