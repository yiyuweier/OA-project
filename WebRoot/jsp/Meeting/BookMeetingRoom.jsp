<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<!--数据表!-->

<table id="BookMeetingRoomDg">
</table>
<!--编辑!-->
<div id="BookMeetingRoomDlg" class="easyui-window" closed="true" resizable="false"
    style="width: 280px; height: 250px; padding: 10px 5px;"  maximizable="false"
    modal="true" title="预定会议室" minimizable="false" collapsible="false">
  <form id="BookMeetingRoomFm" method="post">
    <table>
      <!--      <tr>
        <td>编号：</td>
        <td></td>
      </tr>-->
      <tr>
        <td>会议名称：</td>
        <td><input type="text" class="easyui-validatebox" data-options="required:true" name="name"/></td>
      </tr>
      <tr>
        <td>开始时间：</td>
        <td>
		<input  name="startTime" class="easyui-datetimebox"  id="starttime" />
		</td>
      </tr>
      <tr>
        <td>结束时间</td>
        <td><input  name="endTime" class="easyui-datetimebox" id="endtime" /></td>
      </tr>
      <tr>
        <td>会议室名称：</td>
        <td>
	<select id="meetingRoomId" name="meetingRoomId">
	   
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
<div id="tbBookMeetingRoom" style="padding:5px; height:auto;"> 开始时间：
  <input name='starttime' class='easyui-datetimebox' id='Mstarttime' />
  截止时间：
  <input name='endtime' class='easyui-datetimebox' id='Mendtime' />
  <a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="selectByTime();"  >查询</a>
   
	<s:iterator value="#session.privilegeList">
		<s:if test="#session.user.hasPrivilegeByName(P_name)">
			<a href="#" class="easyui-linkbutton" iconCls="${P_image}" plain="true" onclick="javascript:${P_url}">${P_name}</a>
		</s:if>
    </s:iterator>

   <a href="#" class="easyui-linkbutton" iconCls="icon-book" plain="true" onclick="showBookMeetingRoomHistory();">历史记录</a> </div>
<script type="text/javascript">
$(load());

function load(){
	$('#BookMeetingRoomDg').datagrid({
		url:'MeetingbookServlet_book',
		fit:true,
		columns:[[
			//{field:'ck',title:'选择',width:40,checkbox:true},
			{field:'id',title:'编号',width:40,hidden:true},
			{field:'meetingName',title:'会议名称',width:60},
			{field:'starttime',title:'开始时间',width:80},
			{field:'endtime',title:'结束时间',width:80},
			{field:'roomName',title:'会议室名称',width:40},
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
		title:"会议室管理",
		toolbar:'#tbBookMeetingRoom' 
	});
	/*var p = $('#BookMeetingRoomDg').datagrid('getPager');  
	$(p).pagination({  
		pageSize: 10,//每页显示的记录条数，默认为10  
		pageList: [5,10,20,99],//可以设置每页记录条数的列表  
		beforePageText: '第',//页数文本框前显示的汉字  
		afterPageText: '页    共 {pages} 页',  
		displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录' 
	});*/
}


//预定会议室
function submitaddInfo(){
		$('#BookMeetingRoomFm').form('submit',{
			url:'MeetingbookServlet_addroominfo',
			success:function(data){
				var data=eval('('+data+')');
	            if(data.success){
	                 $.messager.alert('添加成功提示',"预定成功");
	                  $('#BookMeetingRoomDlg').window('close');
	                 $('#BookMeetingRoomDg').datagrid('reload');
                     load();
	            }else
	                $.messager.alert('系统提示',data.errorMsg);
			}
		})
	}
	
function submitDeleteBookInfo(){
   	var row=$('#BookMeetingRoomDg').datagrid('getSelected');
   	//alert(row.meetingName+" "+row.id);	
		if(row){
		  if(confirm("确定要删除"+row.meetingName+"吗？")){
			$.post(
				'MeetingbookServlet_delete1?id='+row.id,
				function(data){
					var data=eval("("+data+")");
					if(data.success){
						$.messager.alert('系统提示',"删除成功");
						$('#BookMeetingRoomDg').datagrid('reload');
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
	

function loadagain(){
//open('/OASystem/jsp/Meeting/BookMeetingRoom.jsp');
  $(load());
// open('MeetingbookServlet_ApplyChange');
  //alert(111111111);
}

//编辑对话框显示
function showBookMeetingRoomDlg(){
        $('#BookMeetingRoomDlg').form('clear');
		$('#BookMeetingRoomDlg').window('open');
		
}

//查看个人历史记录
function showBookMeetingRoomHistory(){
   var url="./jsp/Meeting/MyHistoryBookMeetingRoom.jsp";
	Open("我的历史记录",url);
}

function selectByTime(){
var url="./jsp/Meeting/FindResult.jsp";
	Open("空闲会议室",url);
 
}


$(document).ready(function(){  
   $("#endtime").datetimebox({  
        required:true,  
        onChange: function(date){  
         var a=$("#starttime").datetimebox("getValue"); 
         var b=$("#endtime").datetimebox("getValue");          
         var selObj=$('#meetingRoomId');
         var displaySelect=document.getElementById("meetingRoomId");    
         var childs=displaySelect.childNodes;    
         if(a<b){
          if(a.length!=0){
              $.post(
               "MeetingbookServlet_selectbytime1?startTime="+a+"&endTime="+b,
               function(data){          
             for(var m = childs.length - 1; m >= 0; m--) {           
                    displaySelect.removeChild(childs[m]);      
             }       
            var notbook=eval('('+data+')');
               for(var i=0;i<notbook.length;i++){
               var value1=notbook[i].id;
               var name=notbook[i].name;
               var people=notbook[i].people;
               selObj.append("<option value='"+value1+"'>"+name+"   "+people+"人"+"</option>");
               }
               
               }
              
              )}
        }  else 
           alert("时间顺序有误！");
        }
    });  
    }); 
 
</script>
