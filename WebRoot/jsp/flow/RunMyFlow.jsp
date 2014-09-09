<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<s:hidden id="useridHidden" value="%{#session.user.getId()}"/>
<table id="MyFlow">
</table>
<script type="text/javascript">
	$(function(){
	var useridHidden = document.getElementById("useridHidden").value;
		$('#MyFlow').datagrid({
			url:'DashboardMyflowServlet_toComputeAllRunFlow?userid='+useridHidden,
			columns:[[
				{field:'M_name',title:'流程名称',width:40},
				{field:'M_date',title:'创建时间',width:60},
				{field:'url',title:'',width:20,
					formatter:function(v,row,index){
						return '<a href="#" class="easyui-linkbutton" iconCls="icon-book"  onclick="Open(\''+row.M_name+'\',\''+row.url+'\');">详细</a>';
					}
				}
			]],
			fit:true,
			rownumbers: true, 
			fitColumns:true,
			loadMsg:'数据加载中请稍后……',                
			striped: true, 
			pagination:true,
			method:"get",
			title:"我的流程" 
		});
		var p = $('#MyFlow').datagrid('getPager');  
    	$(p).pagination({  
	        pageSize: 10,//每页显示的记录条数，默认为10  
	        pageList: [5,10,15],//可以设置每页记录条数的列表  
	        beforePageText: '第',//页数文本框前显示的汉字  
	        afterPageText: '页    共 {pages} 页',  
	        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'  
       });
	});

</script>
