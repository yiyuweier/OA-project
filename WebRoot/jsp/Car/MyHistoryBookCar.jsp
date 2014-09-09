<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!--数据表!-->
<table id="BookCarDg2">
</table>




<script type="text/javascript">
$(showBookCarHistory());
function showBookCarHistory(){
	$('#BookCarDg2').datagrid({
		url:'CarbookServlet_userCarDetails',
		fit:true,
		columns:[[
			{field:'id',title:'编号',width:40,hidden:true},
			{field:'carUsage',title:'车辆用途',width:100},
			{field:'starttime',title:'开始时间',width:80},
			{field:'endtime',title:'结束时间',width:80},
			{field:'carName',title:'车辆名称',width:40},
			{field:'code',title:'车牌号',width:40},
			{field:'bookGuest',title:'预定用户',width:40},
			{field:'booktime',title:'预定时间',width:80}
		]],
		pageSize: 10,//每页显示的记录条数，默认为10  
		pageList: [5,10,20,99],//可以设置每页记录条数的列表  
		beforePageText: '第',//页数文本框前显示的汉字  
		afterPageText: '页    共 {pages} 页',  
		displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录' ,
		fitColumns:true,
		loadMsg:'数据加载中请稍后……',  
		rownumbers: true,               
		striped: true, 
		pagination:true,
		method:"post",
		title:"我的预定记录",
	    toolbar:[{
				text:'删除',
				iconCls:'icon-book',
				handler:submitDeleteBookInfo
		}]
	});

}

function submitDeleteBookInfo(){
   	var row=$('#BookCarDg2').datagrid('getSelected');
   	//alert(row.CarName+" "+row.id);	
		if(row){
		  if(confirm("确定要删除   "+row.carUsage+" 吗？")){
			$.post(
				'CarbookServlet_deleteCarInfo?id='+row.id,
				function(data){
					var data=eval("("+data+")");
					if(data.success){
						$.messager.alert('系统提示',"删除成功");
						$('#BookCarDg2').datagrid('reload');
						$('#BookCarDg').datagrid('reload');
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
