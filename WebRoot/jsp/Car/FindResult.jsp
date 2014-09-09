<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<table id="BookCarDg1">
</table>

<script type="text/javascript">
$(selectByTime0());
   function selectByTime0(){
   
	    var a=$("#carstarttime").datetimebox("getValue");
         var b=$("#carendtime").datetimebox("getValue");  
         //alert(a);
         
        if(a<=b){
 
 	$('#BookCarDg1').datagrid({	
		url:'CarbookServlet_selectbytime?carStartTime='+a+'&carEndTime='+b,
		fit:true,
		columns:[[
			{field:'id',title:'编号',width:40,hidden:true},
			{field:'carName',title:'空闲车辆',width:100},
			{field:'code',title:'车牌号',width:100},
			{field:'carPeople',title:'载重人数',width:80},
		]],
		pageSize: 10,//每页显示的记录条数，默认为10  
		pageList: [2,5,10,20,99],//可以设置每页记录条数的列表  
		beforePageText: '第',//页数文本框前显示的汉字  
		afterPageText: '页    共 {pages} 页',  
		displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录' ,
		fitColumns:true,
		loadMsg:'数据加载中请稍后……',  
		rownumbers: true,               
		striped: true, 
		pagination:true,
		method:"post",
		title:"空闲车辆",
		
	});
	     
	}
	else 
	alert("时间顺序有误");     

	
	}
   
</script>