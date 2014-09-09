<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<div id="documentforresulttb">
	<!--  <a href="#" class="easyui-linkbutton" iconCls="icon-folderadd" plain="true" onclick="javascript:findByName()">查询</a>-->
	<s:iterator value="#session.privilegeList">
		<s:if test="#session.user.hasPrivilegeByName(P_name)">
			<a href="#" class="easyui-linkbutton" iconCls="${P_image}" plain="true" onclick="javascript:${P_url}">${P_name}</a>
		</s:if>
    </s:iterator>
</div>
<!--数据表!-->

<table id="DocumentDgResult">
</table>

<script type="text/javascript">
$(findResult());
	function findResult(){
	var fileName=document.getElementById('fileName').value;
	//alert(name);
	 //$('#DocumentDgResult').datagrid('loadData',{total:0,rows:[]}); 
		$('#DocumentDgResult').datagrid({
			fit:true, 
			url:'FileServlet_findbyname?filename='+fileName,
			animate: true,
			columns:[[
				//{field:'ck',checkbox:true},
				{field:'fileCode',title:'文件编号',width:5,hidden:true},
				{field:'class1',title:'类型',width:8,formatter:buildImg},
				{field:'fileName',title:'文件名称',width:80},
				{field:'date',title:'创建时间',width:60},
				{field:'owner',title:'所有者',width:40}
			]], 
			pageSize: 10,//每页显示的记录条数，默认为10  
		    pageList: [2,5,10,20,99],//可以设置每页记录条数的列表  
		beforePageText: '第',//页数文本框前显示的汉字  
		afterPageText: '页    共 {pages} 页',  
		displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录' ,
		fitColumns:true,
		toolbar:'#documentforresulttb',
		loadMsg:'数据加载中请稍后……',  
		rownumbers: true,               
		striped: true, 
		pagination:true,
		method:"post",
		title:"文件查询",
		
			
	});	
	}
	
	
		function downloadForPublic(){
	
	var row=$('#DocumentDgResult').datagrid('getSelected');	
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


function submitDeleteFileForPublic(){
		var row=$('#DocumentDgResult').datagrid('getSelected');
		//alert(row.fileCode);
		if(row){
		    if(confirm("确定要删除"+row.fileName+"吗？")){
			$.post(
				'FileServlet_deleteFile?imageFileName='+row.fileName+'&id='+row.fileCode,
				function(data){
					var data=eval("("+data+")");
					if(data.success){
						$.messager.alert('系统提示',"删除成功");
						$('#DocumentDgResult').datagrid('reload');
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
</script>