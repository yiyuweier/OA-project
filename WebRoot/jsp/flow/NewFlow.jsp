<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<table id="newflowDg">
</table>
<script type="text/javascript">
	$(function load(){
		$('#newflowDg').datagrid({
			url:'show_flowcategory',
			fit:true,
			columns:[[
				{field:'ck',title:'选择',width:40,checkbox:true},
				{field:'flowname',title:'流程名称',width:40},
				{field:'flowbelong',title:'流程所属分类',width:80},
				{field:'flowdesc',title:'流程描述',width:80},
				{field:'newflow',title:'发情流程',width:80,
					formatter:function(v,row,index){
						return '<a href="#" class="easyui-linkbutton" iconCls="icon-book" onclick="Open2(\''+row.flowname+'\',\''+row.newflow+'\');">发起</a>';
					}
				},
				{field:'viewimage',title:'查看流程图',width:80,
					formatter:function(v,row,index){
						return '<a href="#" class="easyui-linkbutton" iconCls="icon-book" onclick="Open2(\''+row.flowname+'\',\''+row.viewimage+'\');">查看</a>';
					}
				}
			]],
			fitColumns:true,
			loadMsg:'数据加载中请稍后……',  
			rownumbers: true,               
			striped: true, 
			pagination:true,
			method:"get",
			title:"用户管理" 
		});
		var p = $('#newflowDg').datagrid('getPager');  
    	$(p).pagination({  
	        pageSize: 10,//每页显示的记录条数，默认为10  
	        pageList: [5,10,15],//可以设置每页记录条数的列表  
	        beforePageText: '第',//页数文本框前显示的汉字  
	        afterPageText: '页    共 {pages} 页',  
	        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'  
        /*onBeforeRefresh:function(){ 
            $(this).pagination('loading'); 
            alert('before refresh'); 
            $(this).pagination('loaded'); 
        }*/ 
       });
	});
</script>
