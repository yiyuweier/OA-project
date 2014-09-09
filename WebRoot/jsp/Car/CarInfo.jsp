<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!--数据表!-->
<table id="CarDg" class="easyui-datagrid">
</table>


<div id="carinfotb">
	
		<s:if test='#session.user.hasPrivilegeByName("车辆管理相关操作")'>
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="showNewCarDlg();">新增车辆</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="showEditCarDlg();">编辑车辆</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="submitDeleteCar();">删除车辆</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-book" plain="true" onclick="showHistoryCar();">车辆历史记录</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-book" plain="true" onclick="showBookCarHistory();">车辆预定记录</a>
		</s:if>
    
</div>
<!--新增!-->
<div id="NewCarDlg" class="easyui-window" closed="true" resizable="false"
    style="width: 280px; height: 250x; padding: 10px 5px;"  maximizable="false"
    modal="true" title="新增车辆" minimizable="false" collapsible="false">
  <form id="NewCarFm" method="post" name="NewCarFm">
    <table>
    <tr>
        <td>车辆名称：</td>
        <td><input type="text"  class="easyui-validatebox" data-options="required:true" name="carName" /></td>
      </tr>
      <tr>
        <td>载重人数：</td>
        <td><input type="text" class="easyui-validatebox" data-options="required:true" name="carPeople" /></td>
      </tr>
      <tr>
        <td>车牌号：</td>
        <td><input type="text" class="easyui-validatebox" data-options="required:true"  name="code" /></td>
      </tr>
      <tr>
        <td>司机姓名：</td>
        <td><input type="text" class="easyui-validatebox" data-options="required:true"  name="driverName" /></td>
      </tr>
      <tr>
        <td>司机电话：</td>
        <td><input type="text" class="easyui-validatebox" data-options="required:true"  name="driverPhone" /></td>
      </tr>
           
      <tr>
        <td>状态：</td>
        <td><input type="radio" name="carStatus" value="1" checked/>可用
        <input type="radio" name="carStatus" value="0" />不可用
        </td>
      </tr> 
      
      <tr>
        <td align="center" colspan="2"><a href="#" class="easyui-linkbutton" iconcls="icon-search"  onclick="submitaddCar()">新增</a></td>
      </tr>
    </table>
  </form>
</div>
<!--编辑!
resizable="false"
-->
<div id="EditCarDlg" class="easyui-window" closed="true" 
    style="width: 300px; height: 300px; padding: 10px 5px;"  maximizable="false"
    modal="true" title="编辑车辆" minimizable="false" collapsible="false">
  <form id="EditCarFm" method="post">
    <table>
     <!--   <tr>
        <td>编号：</td>
         </tr>
      -->
        <td><input type="hidden" class="easyui-validatebox" data-options="required:true" name="id" /></td>
     
      <tr>
        <td>车辆名称：</td>
        <td><input type="text"  class="easyui-validatebox" data-options="required:true" name="carName" /></td>
      </tr>
      <tr>
        <td>载重人数：</td>
        <td><input type="text" class="easyui-validatebox" data-options="required:true" name="carPeople" /></td>
      </tr>
      <tr>
        <td>车牌号：</td>
        <td><input type="text" class="easyui-validatebox" data-options="required:true"  name="code" /></td>
      </tr>
      <tr>
        <td>司机姓名：</td>
        <td><input type="text" class="easyui-validatebox" data-options="required:true"  name="driverName" /></td>
      </tr>
      <tr>
        <td>司机电话：</td>
        <td><input type="text" class="easyui-validatebox" data-options="required:true"  name="driverPhone" /></td>
      </tr>
       
      <tr>
      
      <!--  <input type="text" class="easyui-validatebox" data-options="required:true" name="state"  />-->
        <td>状态：</td>
        <td><input type="radio" name="carStatus" value="1" />可用
        <input type="radio" name="carStatus" value="0" />不可用
        </td>
      </tr> 
      <tr>
        <td style="text-align:center;" colspan="2"><a href="#" class="easyui-linkbutton" iconcls="icon-ok" onclick="submitUpdateCar()" >编辑</a></td>
      </tr>
    </table>
  </form>
</div>


<script type="text/javascript">
$(function(){
load0();
}

);
function load0(){
	$('#CarDg').datagrid({
		url:'CarServlet_list',
		fit:true,
		columns:[[
			{field:'id',title:'车辆编号',width:40,hidden:true},
			{field:'carName',title:'车辆名称',width:50},
			{field:'carPeople',title:'载重人数',width:40},
			{field:'code',title:'车牌号',width:50},
			{field:'carStatus',title:'状态',width:50},
			{field:'driverName',title:'司机姓名',width:50},
			{field:'driverPhone',title:'司机电话',width:60},
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
		title:"车辆管理",
		toolbar:'#carinfotb'
		//[
		//	{
		//		text:'新增',
		//		iconCls:'icon-add',
		//		handler:showNewCarDlg 
		//	},
		//	{
		//		text:'编辑',
		//		iconCls:'icon-edit',
		//		handler:showEditCarDlg 
		//	},{
		//		text:'删除',
		//		iconCls:'icon-remove',
		//		handler:submitDeleteCar
		//	},{
		//		text:'车辆历史记录',
		//		iconCls:'icon-book',
		//		handler:showHistoryCar
		//	},{
		//		text:'车辆预定记录',
		//		iconCls:'icon-book',
		//		handler:showBookCarHistory
		//	}
		//]
	});
	
}
//新增对话框显示
function showNewCarDlg(){
//$('#NewCarDlg').form('clear');
	$('#NewCarDlg').window('open');
}

//提交添加
	function submitaddCar(){
	if(form_onsubmit()&&form_onsubmit1()){
		$('#NewCarFm').form('submit',{
			url:'CarServlet_addcar',
			success:function(data){
				var data=eval('('+data+')');
	            if(data.success){
	                 $.messager.alert('添加成功提示',"添加车辆成功");
	                 $('#NewCarDlg').window('close');
	                 $('#CarDg').datagrid('reload');
                     load0();
	            }else
	                $.messager.alert('系统提示',data.errorMsg);
			}
		})
	}
	else alert("提交失败");
	}
//编辑对话框显示
function showEditCarDlg(){
	var row=$('#CarDg').datagrid('getSelected');
	if(row){  
     $('#EditCarDlg').window('open');
	if(row.carStatus=="可用")
	 row.carStatus=1; 
	 else
	 row.carStatus=0;  
     $('#EditCarFm').form('load',row);

	}else
		$.messager.alert('系统提示','请选择编辑的行');
}

//删除会议室
function submitDeleteCar(){ 
var row=$('#CarDg').datagrid('getSelected');
		if(row){
		  if(confirm("确定要删除  "+row.carName+"  吗？")){
			$.post(
				'CarServlet_deleteCar?id='+row.id,
				function(data){
					var data=eval("("+data+")");
					if(data.success){
						$.messager.alert('系统提示',"删除成功");
						$('#CarDg').datagrid('reload');
						load0();
					}else
					{
						$.messager.alert('系统提示',data.errorMsg);
					}
				}
			)
		}
		}else //if(row==undefined)
			$.messager.alert('系统提示','请选择要删除的行');
	}
	
function submitUpdateCar(){
             $('#EditCarFm').form('submit',{
			url:'CarServlet_updateCar',
			success:function(data){
				var data=eval('('+data+')');
	            if(data.success){
	                 $.messager.alert('添加成功提示',"更新车辆成功");
	                 $('#EditCarDlg').window('close');
	                 $('#CarDg').datagrid('reload');
                     load0();
	            }else
	                $.messager.alert('系统提示',data.errorMsg);
			}
		})

}

function showHistoryCar(){
alert("carinfo:showhistoryCar");
var url="./jsp/Car/HistoryCar.jsp";
	Open("车辆历史记录",url);
} 

function showBookCarHistory(){
   var url="./jsp/Car/HistoryBookCar.jsp";
	Open("车辆预定历史记录",url);
}


function form_onsubmit() { 
var a=document.NewCarFm.carPeople.value;
var patten = /^-?\d+$/;
 if(!(patten.test(a))){
  alert("车辆人数请输入整数"); 
document.NewCarFm.carPeople.focus();
return false;     
}
else 
   return true;
}

function form_onsubmit1() { 
var a=document.NewCarFm.driverPhone.value;
var patten = /^-?\d+$/;
 if(!(patten.test(a))){
  alert("司机电话请输入数字"); 
document.NewCarFm.driverPhone.focus();
return false;     
}
else 
   return true;
}
</script>
