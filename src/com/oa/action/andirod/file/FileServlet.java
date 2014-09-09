package com.oa.action.andirod.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;



import com.oa.action.andirod.servlet.OaServlet;
import com.oa.bean.DocInfo;
import com.oa.bean.DocPrivateInfo;
import com.oa.bean.MeetingInfo;
import com.oa.bean.Privilege;
import com.oa.bean.User;
import com.oa.service.impl.DocInfoService;
import com.oa.service.impl.DocPrivateInfoService;
import com.oa.service.impl.PrivilegeService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@Controller
@Scope("prototype")
public class FileServlet extends OaServlet{
	
	private Long id;
	private int flag;
	private File image; //上传的文件
    private String imageFileName; //文件名称
    private String imageContentType; //文件类型
    private String filename;//查询名字
    private String privatefilename;
    private String privilegeId;//该权限的id
    private int page;
    private int rows;
    private Long userId;
    @Resource
    DocInfo dest;
    @Resource
    DocPrivateInfo privateDest;
	@Resource
	DocInfoService docInfoService;
	@Resource
	DocPrivateInfoService docPrivateInfoService;
	@Resource
	PrivilegeService privilegeService;
	
	User user=(User) ActionContext.getContext().getSession().get("user");
	String realpath = ServletActionContext.getServletContext().getRealPath("/file");
	String privateRealPath = ServletActionContext.getServletContext().getRealPath("/PrivateImages");
	String realimagepath = ServletActionContext.getServletContext().getRealPath("/i");
	
	
	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public String getPrivatefilename() {
		return privatefilename;
	}

	public void setPrivatefilename(String privatefilename) {
		this.privatefilename = privatefilename;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
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
	

	
	public String getPrivilegeId() {
		return privilegeId;
	}

	public void setPrivilegeId(String privilegeId) {
		this.privilegeId = privilegeId;
	}
	
	

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String ShowPublicfile(){
		
		List<DocInfo> filelist=docInfoService.findAll();	
		
		 JSONArray userJsonArray = new JSONArray();
			JSONObject userJsonObject = new JSONObject();
						
   	 String a[]={"doc","ppt","docx","pdf","exe","txt","file","rar","zip","avi","xls","wma","mp4","wav","mp3","jpeg","bmp","jpg","png"};
   	 int i,j;
   	 for(i=0;i<filelist.size();i++){
   		 for(j=0;j<a.length;j++){
   			 if(filelist.get(i).getType().equals(a[j])){			
   				 break;	 
   			 }	 
   		 }		 
   		 if(j==19){
   			 if(filelist.get(i).getType()!=a[18]){   				
   			  filelist.get(i).setType("file");			 
   			 }
   		 }
   	 }
   	 
   	List<DocInfo> filelist1=docInfoService.findAllByPage1(page,rows);
   	for(DocInfo docInfo:filelist1){
   		//System.out.println(realimagepath+"\\"+docInfo.getType()+".png");
   		userJsonObject.put("fileCode",docInfo.getId());
   		userJsonObject.put("class1","i"+"/"+docInfo.getType()+".png");
   		userJsonObject.put("fileName",docInfo.getName());
   		userJsonObject.put("date",docInfo.getCreateTime().toString());
   		userJsonObject.put("owner",docInfo.getUserName());
   		//userJsonObject.put("class",docInfo.getType());
   		userJsonArray.add(userJsonObject);
   		
   		
   	}
   	 

   	outprint("{\"total\":"+filelist.size()+",\"rows\":"+userJsonArray.toString()+"}");
	 
   	 return null;
		
		
	}
	
	
public String savefile() throws IllegalStateException, IOException{
	System.out.println("image 长度: "+image.length());
	if(image.length()<100000000){
    //D:\apache-tomcat-6.0.18\webapps\struts2_upload\images
    System.out.println("111realpath: "+realpath);
    if (image != null) {
    	String url=realpath+"\\"+imageFileName;
    	 File f=new File(url);
    	 if(!(f.exists())){
        File savefile = new File(new File(realpath), imageFileName);
        if (!savefile.getParentFile().exists())
            savefile.getParentFile().mkdirs();
        FileUtils.copyFile(image, savefile);
    }
    	 else { 
    	int i=docInfoService.findByName(imageFileName);	
    	for(int k=1;k<=i;k++){
    		StringBuilder sb=new StringBuilder(imageFileName);
   	     sb.insert(imageFileName.length()-4, "["+k+"]");     
   	     String tempname=sb.toString();  	    
   	     //没找到 存进去
   	     if(!docInfoService.findByName1(tempname)){   	 
    		 File savefile = new File(new File(realpath),tempname);
             if (!savefile.getParentFile().exists())
                 savefile.getParentFile().mkdirs();
           FileUtils.copyFile(image, savefile);
           imageFileName=tempname;
         break;
   	  	     }  		
    	} 		 
   	 }
  }
    
    dest.setCreateTime(new Date());
    dest.setName(imageFileName);
    dest.setPath(realpath+"\\"+imageFileName);  
    dest.setUserId(user.getId());
    dest.setUserName(user.getU_name());  
    String lastname=imageFileName.substring(imageFileName.length()-3,imageFileName.length());
    dest.setType(lastname);
    docInfoService.addDocInfo(dest);
    //List <DocInfo> list=docInfoService.findAll();
    //ActionContext.getContext().getApplication().put("docInfolist", list);
    outprint("{\"success\":true}");
	}
    else 
		outprint("{\"success\":false}");
	  return null;
  }
  

public String savePrivateFile() throws IllegalStateException, IOException{
	
	System.out.println("image 长度: "+image.length());
	if(image.length()<100000000){
	
	System.out.println("privateRealPath: "+privateRealPath);
    if (image != null) {
    	String url=privateRealPath+"\\"+imageFileName;
    	 File f=new File(url);
    	 if(!(f.exists())){
        File savefile = new File(new File(privateRealPath), imageFileName);
        if (!savefile.getParentFile().exists())
            savefile.getParentFile().mkdirs();
        FileUtils.copyFile(image, savefile);
    }
    	 else { 
    	int j=docPrivateInfoService.findByName(imageFileName);
    	
    	for(int k=1;k<=j;k++){
    		StringBuilder sb=new StringBuilder(imageFileName);
   	     sb.insert(imageFileName.length()-4, "["+k+"]");     
   	     String tempname=sb.toString();  	    
   	     //没找到 存进去
   	     if(!docPrivateInfoService.findByName1(tempname)){   	 
    		 File savefile = new File(new File(realpath),tempname);
             if (!savefile.getParentFile().exists())
                 savefile.getParentFile().mkdirs();
           FileUtils.copyFile(image, savefile);
           imageFileName=tempname;
         break;
   	  	     }  		
    	} 		 
  	 }
   }
    
    privateDest.setCreateTime(new Date());
    privateDest.setName(imageFileName);
    privateDest.setPath(privateRealPath+"\\"+imageFileName);  
    privateDest.setUserId(user.getId());
    privateDest.setUserName(user.getU_name());  
    String lastname=imageFileName.substring(imageFileName.length()-3,imageFileName.length());
    privateDest.setType(lastname);
    docPrivateInfoService.addDocPrivateInfo(privateDest);
    List <DocPrivateInfo> list=docPrivateInfoService.findAll();
    ActionContext.getContext().getApplication().put("docPrivateInfolist", list);
    outprint("{\"success\":true}");
	}
    else 
		outprint("{\"success\":false}");
	return null;
}


  public String inputfile(){
	  
	  return "inputfile";
  }
  
  
  public String fileLoad() throws IOException{

	 
	//  String fileName = ServletActionContext.getRequest().getParameter("fileName");
	          //要处理中文乱码问题
	  //imageFileName = new String(imageFileName.getBytes("iso8859-1"),"utf-8");
	           //要下载的哪个文件
	  
	        
	        String path = ServletActionContext.getServletContext().getRealPath("/file");//得到项目的根目录
	      
	        InputStream is = new FileInputStream(path+"\\"+imageFileName);
	          
	          //下载到哪里？客户端
	          
	          //HttpServletResponse response2 = ServletActionContext.getResponse();
	          getResponse().reset(); // 非常重要
	          getResponse().setContentType("application/x-msdownload");
	          OutputStream os = getResponse().getOutputStream();
	          //弹出下载的框filename:提示用户下载的文件名
	          getResponse().setHeader("content-disposition", "attachment;filename="+java.net.URLEncoder.encode(imageFileName,"utf-8"));
	          
	          byte[] b = new byte[1024];
	          int size = is.read(b);
	         
	          while(size>0){
	              os.write(b,0,size);
	              size = is.read(b);
	          }
	          
	         is.close();
	          os.close(); 
	         
	          return null;
	  
  }
  
  
  
  
  
  public String privatefileLoad() throws IOException{

		 
		//  String fileName = ServletActionContext.getRequest().getParameter("fileName");
		          //要处理中文乱码问题
		  //imageFileName = new String(imageFileName.getBytes("iso8859-1"),"utf-8");
		           //要下载的哪个文件
		         //System.out.println("222222222222222222"+imageFileName);
		        
		        String path = ServletActionContext.getServletContext().getRealPath("/PrivateImages");//得到项目的根目录
		           InputStream is = new FileInputStream(path+"\\"+imageFileName);
		          
		          //下载到哪里？客户端
		          
		          HttpServletResponse response = ServletActionContext.getResponse();
		          response.reset(); // 非常重要
	              response.setContentType("application/x-msdownload");
		          OutputStream os = response.getOutputStream();
		          //弹出下载的框filename:提示用户下载的文件名
		          response.addHeader("content-disposition", "attachment;filename="+java.net.URLEncoder.encode(imageFileName,"utf-8"));
		         
		          byte[] b = new byte[1024];
		          int size = is.read(b);
		          while(size>0){
		              os.write(b,0,size);
		              size = is.read(b);
		          }
		          is.close();
		          os.close();
		  
		          return null;
		  
	  }
  
  public String list(){
	  List <DocInfo> list=docInfoService.findAll();
	    ActionContext.getContext().getApplication().put("docInfolist", list);
	  return "list";
  }
  
  public String PublicFileUI(){//公共文件UI
  	List<Privilege> privilegeList = privilegeService.findChildPrivileges(Long.parseLong(privilegeId));
		ActionContext.getContext().getSession().put("privilegeList", privilegeList);
		return "PublicFileUI";
  }
  public String PrivateFileUI(){//私有文件UI
	  	List<Privilege> privilegeList = privilegeService.findChildPrivileges(Long.parseLong(privilegeId));
			ActionContext.getContext().getSession().put("privilegeList", privilegeList);
			return "PrivateFileUI";
  }
  public String FileUI(){//已上传文件UI
	  	List<Privilege> privilegeList = privilegeService.findChildPrivileges(Long.parseLong(privilegeId));
			ActionContext.getContext().getSession().put("privilegeList", privilegeList);
			return "FileUI";
}
  
  //删除公共资源
  public String deleteFile(){  
	  System.out.println(22222);	
	  if(user.getId()==1){
		  System.out.println(11111);	
	  String url=realpath+"\\"+imageFileName;
	  System.out.println(url);	   
	  File f=new File(url);
	  System.out.println(f.exists());  
	  if(f.exists()){
	  f.delete();
	  }
	  
	  
	  docInfoService.deleteFile(id);
	  outprint("{\"success\":true}");
	  }
	  else 
		  outprint("{\"success\":false,\"errorMsg\":对不起，您没有权限}");
	  //return "deleteFile";	 
	  return null;
  }
  
  //删除用户已上传资源(不分页)
  public String deletePersonalFile(){  
	  //System.out.println("111111111111111111"+imageFileName+"zzzzzzzzzzzzzz");
	  String url=realpath+"\\"+imageFileName;
	  System.out.println(url);
	   
	  File f=new File(url);
	  System.out.println(f.exists());
	  
	  if(f.exists()){
	  f.delete();
	  }
	  docInfoService.deleteFile(id);
	  outprint("{\"success\":true}");
	  return null;	 
	 // return personal();	  
  }
  
  //删除个人资源
  public String deletePrivateFile(){  
	  //System.out.println("111111111111111111"+imageFileName+"zzzzzzzzzzzzzz");
	  String url=privateRealPath+"\\"+imageFileName;
	  System.out.println(url);
	   
	  File f=new File(url);
	  System.out.println(f.exists());
	  
	  if(f.exists()){
	  f.delete();
	  }
	  docPrivateInfoService.deletePrivateFile(id);
	  outprint("{\"success\":true}");
	  return null;	  
  }
  
  
  //已上传的公众资源
  public String personal(){
	  List <DocInfo> plist=docInfoService.findByUserId(user.getId());
	  
	  String a[]={"doc","ppt","docx","pdf","exe","txt","file","rar","zip","avi","xls","wma","mp4","wav","mp3","jpeg","bmp","jpg","png"};
 	 int i,j;
 	 for(i=0;i<plist.size();i++){
 		 for(j=0;j<a.length;j++){
 			 if(plist.get(i).getType().equals(a[j])){			
 				 break;	 
 			 }	 
 		 }		 
 		 if(j==19){
 			 if(plist.get(i).getType()!=a[18]){   				
 			  plist.get(i).setType("file");			 
 			 }
 		 }
 	 }
  
 	JSONArray userJsonArray = new JSONArray();
	JSONObject userJsonObject = new JSONObject();
	List <DocInfo> plist1=docInfoService.findByUserIdByPage(user.getId(),page,rows);
	for(DocInfo docInfo:plist1){
		//System.out.println(realimagepath+"\\"+docInfo.getType()+".png");
		userJsonObject.put("fileCode",docInfo.getId());
		userJsonObject.put("class1","i"+"/"+docInfo.getType()+".png");
		userJsonObject.put("fileName",docInfo.getName());
		userJsonObject.put("date",docInfo.getCreateTime().toString());
		userJsonObject.put("owner",docInfo.getUserName());
		//userJsonObject.put("class",docInfo.getType());
		userJsonArray.add(userJsonObject);
		
		
	}
	 
	 //ActionContext.getContext().put("filelist",filelist);
	
	outprint("{\"total\":"+plist.size()+",\"rows\":"+userJsonArray.toString()+"}"); 
	 return null;
 	 
	   // ActionContext.getContext().getApplication().put("plist", plist);
	  
	    //return "personal";
	  
  }  
  
  
  //没有分页（个人资源）
  public String privatefile(){
	  List <DocPrivateInfo> privatelist=docPrivateInfoService.findByUserId(user.getId());
	
	  String a[]={"doc","ppt","docx","pdf","exe","txt","file","rar","zip","avi","xls","wma","mp4","wav","mp3","jpeg","bmp","jpg","png"};
 	 int i,j;
 	 for(i=0;i<privatelist.size();i++){
 		 for(j=0;j<a.length;j++){
 			 if(privatelist.get(i).getType().equals(a[j])){			
 				 break;	 
 			 }	 
 		 }		 
 		 if(j==19){
 			 if(privatelist.get(i).getType()!=a[18]){   				
 				privatelist.get(i).setType("file");			 
 			 }
 		 }
 	 }
 	 
 	JSONArray userJsonArray = new JSONArray();
 	JSONObject userJsonObject = new JSONObject();
 	 List <DocPrivateInfo> privatelist1=docPrivateInfoService.findByUserIdByPage(user.getId(),page,rows);
 	for(DocPrivateInfo docInfo:privatelist1){
 		//System.out.println(realimagepath+"\\"+docInfo.getType()+".png");
 		userJsonObject.put("fileCode",docInfo.getId());
 		userJsonObject.put("class1","i"+"/"+docInfo.getType()+".png");
 		userJsonObject.put("fileName",docInfo.getName());
 		userJsonObject.put("date",docInfo.getCreateTime().toString());
 		userJsonObject.put("owner",docInfo.getUserName());
 		//userJsonObject.put("class",docInfo.getType());
 		userJsonArray.add(userJsonObject);
 		
 		
 	}
 	 
 	outprint("{\"total\":"+privatelist.size()+",\"rows\":"+userJsonArray.toString()+"}");
 	 
 	 return null;
 	 
	  
  }  
  
  public String Psource(){
	/*  List<String> file=new ArrayList<String>();
	  String a[]={"doc","ppt","docx","pdf","exe","txt","file","rar","zip","avi","xls"};
	  for(int i=0;i<a.length;i++){
	  file.add(a[i]);
	  }
	  ActionContext.getContext().getApplication().put("file", file);*/
	  
	  
	    return "Psource";
	  
  }  
  
  public String findbyname(){
	  System.out.println(filename);
	  List <DocInfo> findbyname=docInfoService.findByName2(filename);
	  System.out.println(findbyname.size());
	  JSONArray userJsonArray = new JSONArray();
	 	JSONObject userJsonObject = new JSONObject();
	  for(DocInfo docInfo:findbyname){
		  userJsonObject.put("fileCode",docInfo.getId());
		  userJsonObject.put("class1","i"+"/"+docInfo.getType()+".png");
		  userJsonObject.put("fileName",docInfo.getName());
		  userJsonObject.put("date",docInfo.getCreateTime().toString());
		  userJsonObject.put("owner",docInfo.getUserName());
		  userJsonArray.add(userJsonObject);
		  
	  }
	  //ActionContext.getContext().getApplication().put("findbyname",findbyname);
	  outprint(userJsonArray.toString());
	  return null;
	  
  }
  
  
  public String findprivatebyname(){
	  
	  List <DocPrivateInfo> findbyprivatename=docPrivateInfoService.findByName2(privatefilename);
	  ActionContext.getContext().getApplication().put("findbyprivatename",findbyprivatename);
	  return "findbyprivatename";
  }
  
  
  /*********************************************************/
  public String PhonePrivateFile(){
	  List <DocPrivateInfo> privatelist=docPrivateInfoService.findByUserId(userId);//user.getId()
	
	  String a[]={"doc","ppt","docx","pdf","exe","txt","file","rar","zip","avi","xls","wma","mp4","wav","mp3","jpeg","bmp","jpg","png"};
 	 int i,j;
 	 for(i=0;i<privatelist.size();i++){
 		 for(j=0;j<a.length;j++){
 			 if(privatelist.get(i).getType().equals(a[j])){			
 				 break;	 
 			 }	 
 		 }		 
 		 if(j==19){
 			 if(privatelist.get(i).getType()!=a[18]){   				
 				privatelist.get(i).setType("file");			 
 			 }
 		 }
 	 }
 	 
 	JSONArray userJsonArray = new JSONArray();
 	JSONObject userJsonObject = new JSONObject();
 	 //List <DocPrivateInfo> privatelist1=docPrivateInfoService.findByUserIdByPage(user.getId(),page,rows);
 	for(DocPrivateInfo docInfo:privatelist){
 		//System.out.println(realimagepath+"\\"+docInfo.getType()+".png");
 		userJsonObject.put("fileCode",docInfo.getId());
 		userJsonObject.put("class1","i"+"/"+docInfo.getType()+".png");
 		userJsonObject.put("fileName",docInfo.getName());
 		userJsonObject.put("date",docInfo.getCreateTime().toString());
 		userJsonObject.put("owner",docInfo.getUserName());
 		//userJsonObject.put("class",docInfo.getType());
 		userJsonArray.add(userJsonObject);
 		
 		
 	}
 	 
 	System.out.println("{\"total\":"+privatelist.size()+",\"rows\":"+userJsonArray.toString()+"}");
 	outprint("{\"total\":"+privatelist.size()+",\"rows\":"+userJsonArray.toString()+"}");
 	 
 	 return null;
 	 
	  
  }  
  
  public String ShowPublicfilePhone(){
		
		List<DocInfo> filelist=docInfoService.findAll();	
		
		 JSONArray userJsonArray = new JSONArray();
			JSONObject userJsonObject = new JSONObject();
						
 	 String a[]={"doc","ppt","docx","pdf","exe","txt","file","rar","zip","avi","xls","wma","mp4","wav","mp3","jpeg","bmp","jpg","png"};
 	 int i,j;
 	 for(i=0;i<filelist.size();i++){
 		 for(j=0;j<a.length;j++){
 			 if(filelist.get(i).getType().equals(a[j])){			
 				 break;	 
 			 }	 
 		 }		 
 		 if(j==19){
 			 if(filelist.get(i).getType()!=a[18]){   				
 			  filelist.get(i).setType("file");			 
 			 }
 		 }
 	 }
 	 
 	//List<DocInfo> filelist1=docInfoService.findAllByPage1(page,rows);
 	for(DocInfo docInfo:filelist){
 		//System.out.println(realimagepath+"\\"+docInfo.getType()+".png");
 		userJsonObject.put("fileCode",docInfo.getId());
 		userJsonObject.put("class1","i"+"/"+docInfo.getType()+".png");
 		userJsonObject.put("fileName",docInfo.getName());
 		userJsonObject.put("date",docInfo.getCreateTime().toString());
 		userJsonObject.put("owner",docInfo.getUserName());
 		//userJsonObject.put("class",docInfo.getType());
 		userJsonArray.add(userJsonObject);
 		
 		
 	}
 	 

 	outprint("{\"total\":"+filelist.size()+",\"rows\":"+userJsonArray.toString()+"}");
	 
 	 return null;
		
		
	}
  
  
  public String personalPhone(){
	  //long u=1;
	  List <DocInfo> plist=docInfoService.findByUserId(userId);
	  
	  String a[]={"doc","ppt","docx","pdf","exe","txt","file","rar","zip","avi","xls","wma","mp4","wav","mp3","jpeg","bmp","jpg","png"};
 	 int i,j;
 	 for(i=0;i<plist.size();i++){
 		 for(j=0;j<a.length;j++){
 			 if(plist.get(i).getType().equals(a[j])){			
 				 break;	 
 			 }	 
 		 }		 
 		 if(j==19){
 			 if(plist.get(i).getType()!=a[18]){   				
 			  plist.get(i).setType("file");			 
 			 }
 		 }
 	 }
  
 	JSONArray userJsonArray = new JSONArray();
	JSONObject userJsonObject = new JSONObject();
	//List <DocInfo> plist1=docInfoService.findByUserIdByPage(user.getId(),page,rows);
	for(DocInfo docInfo:plist){
		//System.out.println(realimagepath+"\\"+docInfo.getType()+".png");
		userJsonObject.put("fileCode",docInfo.getId());
		userJsonObject.put("class1","i"+"/"+docInfo.getType()+".png");
		userJsonObject.put("fileName",docInfo.getName());
		userJsonObject.put("date",docInfo.getCreateTime().toString());
		userJsonObject.put("owner",docInfo.getUserName());
		//userJsonObject.put("class",docInfo.getType());
		userJsonArray.add(userJsonObject);
		
		
	}
	 
	
	
	outprint("{\"total\":"+plist.size()+",\"rows\":"+userJsonArray.toString()+"}"); 
	 return null;
 	 
	  
  }  
  
//user静态
  public String privatefilePhone(){
	 // User user=(User) ActionContext.getContext().getSession().get("user");
	  //long u=1;
	  List <DocPrivateInfo> privatelist=docPrivateInfoService.findByUserId(userId);
	
	  String a[]={"doc","ppt","docx","pdf","exe","txt","file","rar","zip","avi","xls","wma","mp4","wav","mp3","jpeg","bmp","jpg","png"};
 	 int i,j;
 	 for(i=0;i<privatelist.size();i++){
 		 for(j=0;j<a.length;j++){
 			 if(privatelist.get(i).getType().equals(a[j])){			
 				 break;	 
 			 }	 
 		 }		 
 		 if(j==19){
 			 if(privatelist.get(i).getType()!=a[18]){   				
 				privatelist.get(i).setType("file");			 
 			 }
 		 }
 	 }
 	 
 	JSONArray userJsonArray = new JSONArray();
 	JSONObject userJsonObject = new JSONObject();
 	// List <DocPrivateInfo> privatelist1=docPrivateInfoService.findByUserIdByPage(user.getId(),page,rows);
 	for(DocPrivateInfo docInfo:privatelist){
 		//System.out.println(realimagepath+"\\"+docInfo.getType()+".png");
 		userJsonObject.put("fileCode",docInfo.getId());
 		userJsonObject.put("class1","i"+"/"+docInfo.getType()+".png");
 		userJsonObject.put("fileName",docInfo.getName());
 		userJsonObject.put("date",docInfo.getCreateTime().toString());
 		userJsonObject.put("owner",docInfo.getUserName());
 		//userJsonObject.put("class",docInfo.getType());
 		userJsonArray.add(userJsonObject);
 		
 		
 	}
 	 
 	outprint("{\"total\":"+privatelist.size()+",\"rows\":"+userJsonArray.toString()+"}");
 	 
 	 return null;
 	 
	  
  }  
  
  public String Phonefindbyname(){
	  //System.out.println(filename);
	  List <DocInfo> findbyname=docInfoService.findByName2(filename);
	  System.out.println(findbyname.size());
	  JSONArray userJsonArray = new JSONArray();
	 	JSONObject userJsonObject = new JSONObject();
	  for(DocInfo docInfo:findbyname){
		  userJsonObject.put("fileCode",docInfo.getId());
		  userJsonObject.put("class1","i"+"/"+docInfo.getType()+".png");
		  userJsonObject.put("fileName",docInfo.getName());
		  userJsonObject.put("date",docInfo.getCreateTime().toString());
		  userJsonObject.put("owner",docInfo.getUserName());
		  userJsonArray.add(userJsonObject);
		  
	  }
	  //ActionContext.getContext().getApplication().put("findbyname",findbyname);
	  //outprint(userJsonArray.toString());
	  outprint("{\"total\":"+findbyname.size()+",\"rows\":"+userJsonArray.toString()+"}");
	  return null;
	  
  }
  
  //删除公共文件
  public String PhonedeleteFile(){  
	  if(userId==1){
	  String url=realpath+"\\"+imageFileName;
	  System.out.println(url);	   
	  File f=new File(url);
	  System.out.println(f.exists());  
	  if(f.exists()){
	  f.delete();
	  }
	  
	  
	  docInfoService.deleteFile(id);
	  outprint("{\"success\":true}");
	  }
	  else 
		  outprint("{\"success\":false,\"errorMsg\":对不起，您没有权限}");
	  //return "deleteFile";	 
	  return null;
  }
  
  /*************************************************************/
}
