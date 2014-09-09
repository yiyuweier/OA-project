<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<div id="documenttbForPrivate">
<!--   <input name='fileName' type='text' id='fileName' />
	<a href="#" class="easyui-linkbutton" iconCls="icon-folderadd" plain="true" onclick="javascript:findByName()">查询</a> -->
	<s:iterator value="#session.privilegeList">
		<s:if test="#session.user.hasPrivilegeByName(P_name)">
			<a href="#" class="easyui-linkbutton" iconCls="${P_image}" plain="true" onclick="javascript:${P_url}">${P_name}</a>
		</s:if>
    </s:iterator>
</div>


<table id="DocumentDgForPrivate">
</table>

<div id="UploadPrivateFileDlg" class="easyui-window" closed="true" style="width:300px; height:120px;" maximizable="false"   modal="true" title="上传文件" minimizable="false" collapsible="false">
  <form id="UploadPrivateFileFm" method="post" enctype="multipart/form-data">
    <table>
      <tr>
        <td>文件：</td>
        <td><input type="file" name="image"/>
        </td>
      </tr>
	        <tr>
        <td colspan="2" style="text-align:center;">
        
		<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="submitPrivateUpload();">提交</a>
		</td>
 
      </tr>
    </table>
  </form>
</div>


<script type="text/javascript">

$(function(){
		$('#DocumentDgForPrivate').datagrid({
			fit:true, 
			url:'FileServlet_privatefile',
			animate: true,
			columns:[[
				//{field:'ck',checkbox:true},
				{field:'fileCode',title:'文件编号',width:80,hidden:true},
				{field:'class1',title:'类型',width:8,formatter:buildImg},
				{field:'fileName',title:'文件名称',width:80},
				{field:'date',title:'创建时间',width:60},
				{field:'owner',title:'所有者',width:40}
			]], 
			pageSize: 10,//每页显示的记录条数，默认为10  
		    pageList: [5,10,20,99],//可以设置每页记录条数的列表  
		    beforePageText: '第',//页数文本框前显示的汉字  
		    afterPageText: '页    共 {pages} 页',  
		    displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录' ,
			rownumbers: true,               
			striped: true, 
			pagination:true,
			fitColumns:true,
			toolbar:'#documenttbForPrivate'
			//[{
			//	text:'上传私人文件',
			//	iconCls:'icon-folderadd',
			//	handler:showUploadPrivateFile
			//},{
			//	text:'下载文件',
			//	iconCls:'icon-foldergo',
			//	handler:download2
			//	//DownloadFile
			//},{
			//    text:'删除',
			//	iconCls:'icon-foldergo',
			//	handler:submitDeleteFile		
			//}	
			//]
		});
		
	})
	
	function showUploadPrivateFile(){
		$('#UploadPrivateFileDlg').window('open');
	}
	
	function submitPrivateUpload(){	
	$('#UploadPrivateFileFm').form('submit',{
			url:'FileServlet_savePrivateFile',
			success:function(data){
				var data=eval('('+data+')');
	            if(data.success){
	                 $.messager.alert('添加成功提示',"上传成功");
	                  $('#UploadPrivateFileDlg').window('close');
	                  $('#DocumentDgForPrivate').datagrid('reload');
                     loaded();
	            }else
	                 $.messager.alert('系统提示',"文件不要超过100M");
			}
		})
	
	  
	
	
	}
	function downloadForPrivate(){
	var row=$('#DocumentDgForPrivate').datagrid('getSelected');	
	if(row){
	var form=$("<form>");//定义一个form表单
     form.attr("style","display:none");
     form.attr("target","");
     form.attr("method","post");
     form.attr("action","FileServlet_privatefileLoad"+"?imageFileName="+row.fileName);
   $("body").append(form);//将表单放置在web中
  //form.append(input1);
  form.submit();//表单提交 	
  }
	 else 
  $.messager.alert('系统提示',"请选择要下载的文件");
	}
	
	function buildImg(value,row,index){	
	    
	    return '<img src='+value+' width="20" height="20">';
	 }


function submitDeleteFileForPrivate(){
		var row=$('#DocumentDgForPrivate').datagrid('getSelected');
		//alert(row.fileCode);
		if(row){
		    if(confirm("确定要删除"+row.fileName+"吗？")){
			$.post(
				'FileServlet_deletePrivateFile?imageFileName='+row.fileName+'&id='+row.fileCode,
				function(data){
					var data=eval("("+data+")");
					if(data.success){
						$.messager.alert('系统提示',"删除成功");
						$('#DocumentDgForPrivate').datagrid('reload');
						loaded();
					}else
					{
						$.messager.alert('系统提示',data.errorMsg);
					}
				}
			)
			}
		}else
			$.messager.alert('系统提示','请选择要删除的行');
	}








</script>