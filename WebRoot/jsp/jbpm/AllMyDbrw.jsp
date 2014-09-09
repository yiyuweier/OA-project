<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<s:hidden id="useridHidden" value="%{#session.user.getId()}"/>
<table id="MyFlow">
</table>
<script type="text/javascript">
	$(function(){
	var useridHidden = document.getElementById("useridHidden").value;
		$('#MyFlow').datagrid({
			url:'DashboardDbrwServlet_AllDbrw?userid='+useridHidden,
			columns:[[
				{field:'dbrwname',title:'名称',width:40},
				{field:'dbrwtime',title:'时间',width:20},
				{field:'url',title:'事件',width:60,
					formatter:function(v,row,index){
						return '<a href="#" class="easyui-linkbutton" iconCls="icon-book"  onclick="Open2(\''+row.dbrwname+'\',\''+row.url+'\');">处理</a>';
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
	});

</script>
