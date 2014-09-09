<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<title>部署流程</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<script language="javascript" src="./jsp/SysManage/js/jquery.js"></script>
    <script language="javascript" src="./jsp/SysManage/js/pageCommon.js" charset="utf-8"></script>
    <script language="javascript" src="./jsp/SysManage/js/PageUtils.js" charset="utf-8"></script>
    <link type="text/css" rel="stylesheet" href="./jsp/SysManage/css/pageCommon.css" />
    <script type="text/javascript"> 
    </script>
</head>
<body> 

<!-- 标题显示 -->
<div id="Title_bar">
    <div id="Title_bar_Head">
        <div id="Title_Head"></div>
        <div id="Title"><!--页面标题-->
            <img border="0" width="13" height="13" src="./jsp/SysManage/images/title_arrow.gif"/> 部署流程
        </div>
        <div id="Title_End"></div>
    </div>
</div>

<!--显示表单内容-->
<div id="MainArea">
    <form action="startProcessInstance_Document" method="post">
        <div class="ItemBlock_Title1">
        </div>
        
        <!-- 表单内容显示 -->
        <div class="ItemBlockBorder">
            <div class="ItemBlock">
                <table id="dtable" cellpadding="0" cellspacing="0" class="mainForm">
                 	<tr>
                        <td>
                        	<s:textfield name="D_name" id="D_name" label="标题"/>
                        </td>
                    </tr>
                    <tr>
                    	<td>
                        	<s:textarea name="D_content" id="D_content" label="内容"/>
                        </td>
                    </tr>
                    <tr>
                    	<td>
                        	<s:radio list="#{'1':'串行','2':'并行'}" name="D_type" id="D_type" label="会签方式"/>
                        </td>
                    </tr>
                    <tr id="sendto">
                    	<td>
                    		<s:textfield name="D_sendto" label="会签人员"/>
                    	</td>
                    </tr>
                </table>
            </div>
        </div>
        
        <!-- 表单操作 -->
        <div id="InputDetailBar">
            <input type="image" src="./jsp/SysManage/images/save.png"/>
            <a href="javascript:history.go(-1);"><img src="./jsp/SysManage/images/goBack.png"/></a>
        </div>
    </form>
</div>
<div class="Description">
	说明：<br />
	1，上级部门的列表是有层次结构的（树形）。<br/>
	2，如果是修改：上级部门列表中不能显示当前修改的部门及其子孙部门。因为不能选择自已或自已的子部门作为上级部门。<br />
</div>

</body>
<script type="text/javascript">
	function Select_type(){
		//alert("123");
		var D_typeElementValue = document.getElementsByName("D_type");
		if(D_typeElementValue!=null){
			for(var i=0;i<D_typeElementValue.length;i++){
				if(D_typeElementValue[i].checked){
					if(D_typeElementValue[i].value=="1"){
						//alert("here1");
						var sendtoElement = document.getElementById("sendto");
						//var addtd = sendtoElement.getElementsByTagName("td");
						//alert("here2");
						var addtd = document.createElement("td");
						var addbutton = document.createElement("button");
						//alert("here3");
						addbutton.innerHTML = "添加审批人";
						addbutton.setAttribute("onclick","return addSelect();");
						//addbutton.onclick = "addSelect()";
						//addbutton.firstChild.value="添加审批人";
						addtd.appendChild(addbutton);
						sendtoElement.appendChild(addtd);
						//alert("here4");
					}
				}
			}
			
		}
	}
	function addSelect(){
		alert("添加审批人");
		var sendtoElement = document.getElementsById("dtable");
		alert("here2");
		var addtr = document.createElement("tr");
		//var firsttd = addtr.firstChild;
		//firsttd.innerHTML = "会签人员";
		var addtd1 = document.createElement("td");
		var addlabel = document.createElement("label");
		addlabel.innerHTML = "会签人员";
		alert("here3");
		addtd1.appendChild(addlabel);
		var addtd2 = document.createElement("td");
		var addinput = document.createElement("input");
		alert("here4");
		addinput.setAttribute("type","text");
		//addinput.setAttribute("name","sendto");
		alert("here5");
		addtd2.appendChild(addinput);
		addtr.appendChild(addtd1);
		addtr.appendChild(addtd2);
		alert("here6");
		sendtoElement.appendChild(addtr);
		alert("here7");
		return false;
	}
	
	
</script>
</html>

