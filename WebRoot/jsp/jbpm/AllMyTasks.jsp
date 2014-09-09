<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<s:hidden id="useridHidden" value="%{#session.user.getId()}"/>
<table id="MyDbrwFlow">
</table>
<script type="text/javascript">
	$(function(){
	//var useridHidden = document.getElementById("useridHidden").value;
		$('#MyDbrwFlow').datagrid({
			url:'JbpmServlet_getAllTaskByUserName',
			columns:[[
				{field:'taskid',title:'ID',width:20},
				{field:'url',title:'事件',width:100,
					formatter:function(v,row,index){
						return '<a href="#" class="easyui-linkbutton" iconCls="icon-book"  onclick="showMessageWindow(\''+row.taskname+'\',\''+row.url+'\');">'+row.taskname+'</a>';
					}
				},
				{field:'tasktime',title:'时间',width:20}
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
		var p = $('#MyDbrwFlow').datagrid('getPager');  
    	$(p).pagination({  
	        pageSize: 10,//每页显示的记录条数，默认为10  
	        pageList: [5,10,15],//可以设置每页记录条数的列表  
	        beforePageText: '第',//页数文本框前显示的汉字  
	        afterPageText: '页    共 {pages} 页',  
	        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'  
       });
	});

</script>
