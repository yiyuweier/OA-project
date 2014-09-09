<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<!--数据表
<div id="bookcartb">
	<s:iterator value="#session.privilegeList">
		<s:if test="#session.user.hasPrivilegeByName(P_name)">
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="javascript:${P_url}">${P_name}</a>
		</s:if>
    </s:iterator>
</div>!-->
<table id="BookCarDg">
</table>
<!--编辑!-->
<div id="BookCarDlg" class="easyui-window" closed="true" resizable="false"
    style="width: 280px; height: 250px; padding: 10px 5px;"  maximizable="false"
    modal="true" title="预定车辆" minimizable="false" collapsible="false">
  <form id="BookCarFm" method="post">
    <table>
      <!--      <tr>
        <td>编号：</td>
        <td></td>
      </tr>-->
      <tr>
        <td>车辆用途：</td>
        <td><input type="text" class="easyui-validatebox" data-options="required:true" name="carUsage"/></td>
      </tr>
      <tr>
        <td>开始时间：</td>
        <td>
		<input  name="carStartTime" class="easyui-datetimebox"  id="starttime" />
		</td>
      </tr>
      <tr>
        <td>结束时间</td>
        <td><input  name="carEndTime" class="easyui-datetimebox" id="endtime" /></td>
      </tr>
      <tr>
        <td>车辆名称：</td>
        <td>
	<select id="CarId" name="CarId">
	   
	</select>
		</td>
      </tr>
   <!--    <tr>
        <td>预定客户：</td>
        <td><input type="text" class="easyui-validatebox" data-options="required:true" /></td>
      </tr>--> 
      <tr>
        <td><a href="#" class="easyui-linkbutton" iconcls="icon-ok" onclick="submitaddInfo();" >预定</a></td>
      </tr>
    </table>
  </form>
</div>
<div id="tbBookCar" style="padding:5px; height:auto;"> 开始时间：
  <input name='carstarttime' class='easyui-datetimebox' id='carstarttime' />
  截止时间：
  <input name='carendtime' class='easyui-datetimebox' id='carendtime' />
  <a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="selectByTime1();"  >查询</a>
	<s:iterator value="#session.privilegeList">
		<s:if test="#session.user.hasPrivilegeByName(P_name)">
			<a href="#" class="easyui-linkbutton" iconCls="${P_image}" plain="true" onclick="javascript:${P_url}">${P_name}</a>
		</s:if>
    </s:iterator>
   <a href="#" class="easyui-linkbutton" iconCls="icon-book" plain="true" onclick="showBookCarHistory();">我的预定记录</a> </div>
<script type="text/javascript">
$(load());

function load(){
	$('#BookCarDg').datagrid({
		url:'CarbookServlet_list',
		fit:true,
		columns:[[
			//{field:'ck',title:'选择',width:40,checkbox:true},
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
		method:"get",
		title:"车辆预定信息管理",
		toolbar:'#tbBookCar'
	});	
}


//预定会议室
function submitaddInfo(){
		$('#BookCarFm').form('submit',{
			url:'CarbookServlet_addcarinfo',
			success:function(data){
				var data=eval('('+data+')');
	            if(data.success){
	                 $.messager.alert('添加成功提示',"预定成功");
	                  $('#BookCarDlg').window('close');
	                 $('#BookCarDg').datagrid('reload');
                     load();
	            }else
	                $.messager.alert('系统提示',data.errorMsg);
			}
		})
	}
	
function submitDeleteBookInfo(){
   	var row=$('#BookCarDg').datagrid('getSelected');
   	//alert(row.CarName+" "+row.id);	
		if(row){
		  if(confirm("确定要删除   "+row.carUsage+" 吗？")){
			$.post(
				'CarbookServlet_deleteCarInfo?id='+row.id,
				function(data){
					var data=eval("("+data+")");
					if(data.success){
						$.messager.alert('系统提示',"删除成功");
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
	
function selectByTime1(){
	var url="./jsp/Car/FindResult.jsp";
	Open("空闲车辆",url);
	
	}

function loadagain(){
//open('/OASystem/jsp/Car/BookCar.jsp');
  $(load());
  
  //alert(111111111);
}

//编辑对话框显示
function showBookCarDlg(){

		$('#BookCarDlg').window('open');
		$('#BookCarDlg').form('clear');
}

//查看历史记录
function showBookCarHistory(){
   var url="./jsp/Car/MyHistoryBookCar.jsp";
	Open("我的车辆历史记录",url);
}


$(document).ready(function(){  
   $("#endtime").datetimebox({  
        required:true,  
        onChange: function(date){  
         var a=$("#starttime").datetimebox("getValue"); 
         var b=$("#endtime").datetimebox("getValue");          
         var selObj=$('#CarId');
         var displaySelect=document.getElementById("CarId");    
         var childs=displaySelect.childNodes;  
         if(a<b){   
          if(a.length!=0){
              $.post(
               "CarbookServlet_selectbytime1?carStartTime="+a+"&carEndTime="+b,
               function(data){          
             for(var m = childs.length - 1; m >= 0; m--) {           
                    displaySelect.removeChild(childs[m]);      
             }       
            var notbook=eval('('+data+')');
               for(var i=0;i<notbook.length;i++){
                   var value1=notbook[i].id;
                   var text=notbook[i].carName;
                   var code=notbook[i].code;
               selObj.append("<option value='"+value1+"'>"+text+" "+code+"</option>");
               }
               
               }
              
              )}
        } 
        else 
        alert("时间顺序有误！"); 
        }
        
    }); 
     
    }); 
 
</script>
