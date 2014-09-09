<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<div id="documenttbForPublic">
    <input name='fileName' type='text' id='fileName' />
	<a href="#" class="easyui-linkbutton" iconCls="icon-folderadd" plain="true" onclick="javascript:OpenfindByName()">查询</a>
	<s:iterator value="#session.privilegeList">
		<s:if test="#session.user.hasPrivilegeByName(P_name)">
			<a href="#" class="easyui-linkbutton" iconCls="${P_image}" plain="true" onclick="javascript:${P_url}">${P_name}</a>
		</s:if>
    </s:iterator>
</div>


<table id="DocumentDgForPublic">
</table>


<div id="UploadFileDlgForPublic" class="easyui-window" closed="true" style="width:300px; height:120px;" maximizable="false"   modal="true" title="上传文件" minimizable="false" collapsible="false">
  <form id="UploadFileFm" method="post" enctype="multipart/form-data">
    <table>
      <tr>
        <td>文件：</td>
        <td><input type="file" name="image"/>
        </td>
      </tr>
	        <tr>
        <td colspan="2" style="text-align:center;">
		<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="submitUploadForPublic();">提交</a>
		</td>
 
      </tr>
    </table>
  </form>
</div>

<script type="text/javascript">
$(function(){
		$('#DocumentDgForPublic').datagrid({
			fit:true, 
			url:'FileServlet_ShowPublicfile',
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
			toolbar:'#documenttbForPublic'
			//[{
			//	text:'查询',
			//	iconCls:'icon-folderadd',
			//	handler:findByName
			//},{
			//	text:'上传公众文件',
			//	iconCls:'icon-folderadd',
			//	handler:showUploadFile
			//},{
			//	text:'下载文件',
			//	iconCls:'icon-foldergo',
			//	handler:download1
				//DownloadFile
			//},{
			//    text:'删除',
			//	iconCls:'icon-foldergo',
			//	handler:submitDeleteFile		
			//}	
			//]
		});
		
		
	 // $("<tr><td >查询名称：<input id='filename' type='text' /></td></tr>").prependTo(".datagrid-toolbar table tbody tr");	
	
		
	})
	
	function showUploadFileForPublic(){
		$('#UploadFileDlgForPublic').window('open');
	}
	
	function submitUploadForPublic(){
		$('#UploadFileFm').form('submit',{
			url:'FileServlet_savefile',
			success:function(data){
				var data=eval('('+data+')');
	            if(data.success){
	             
	                 $.messager.alert('添加成功提示',"上传成功");
	                 $('#UploadFileDlgForPublic').window('close');
	                 $('#DocumentDgForPublic').datagrid('reload');
                     load();
	            }else
	                 $.messager.alert('系统提示',"文件不要超过100M");
			}
		})
	}
	
	function submitDeleteFileForPublic(){
		var row=$('#DocumentDgForPublic').datagrid('getSelected');
		//alert(row.fileCode);
		if(row){
		    if(confirm("确定要删除"+row.fileName+"吗？")){
			$.post(
				'FileServlet_deleteFile?imageFileName='+row.fileName+'&id='+row.fileCode,
				function(data){
					var data=eval("("+data+")");
					if(data.success){
						$.messager.alert('系统提示',"删除成功");
						$('#DocumentDgForPublic').datagrid('reload');
						//load();
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
	
	function downloadForPublic(){
	
	var row=$('#DocumentDgForPublic').datagrid('getSelected');	
	//var rowResult=$('#DocumentDgResult').datagrid('getSelected');	
	if(row){
	
	var form=$("<form>");//定义一个form表单
     form.attr("style","display:none");
     form.attr("target","");
     form.attr("method","post");
     form.attr("action","FileServlet_fileLoad"+"?imageFileName="+row.fileName);
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
	 
	 function OpenfindByName(){
	
	var url="./jsp/Document/FindResult.jsp";
	Open("公共文件查询",url);
	}
</script>