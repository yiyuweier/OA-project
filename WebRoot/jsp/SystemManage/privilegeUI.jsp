<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>配置权限</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <script language="javascript" src="./resource/js/pageCommon.js" charset="utf-8"></script>
    <script language="javascript" src="./resource/js/PageUtils.js" charset="utf-8"></script>
    <link type="text/css" rel="stylesheet" href="./resource/css/pageCommon.css" />
	<script language="javascript" src="./resource/js/jquery.treeview.js"></script>
	<link type="text/css" rel="stylesheet" href="./resource/css/file.css" />
	<link type="text/css" rel="stylesheet" href="./resource/css/jquery.treeview.css" />
	
	<script type="text/javascript" src="jqeasyui/jquery.js"></script>
	<script type="text/javascript" src="jqeasyui/jquery.easyui.js"></script>
	<script type="text/javascript" src="jqeasyui/locale/easyui-lang-zh_CN.js" ></script>
	<link href="css/index.css" rel="stylesheet" type="text/css" />

	<script type="text/javascript">
 		// 选择所有
    	$(function(){
			// 指定事件处理函数
			$("[name=privilegeIdlist]").click(function(){
				
				// 当选中或取消一个权限时，也同时选中或取消所有的下级权限
				$(this).siblings("ul").find("input").attr("checked", this.checked);
				
				// 当选中一个权限时，也要选中所有的直接上级权限
				if(this.checked == true){
					$(this).parents("li").children("input").attr("checked", true);
				}
				
			});
		});
    	$(function(){
    		$("#tree").treeview();
    	});
    </script>
</head>
<body>

<!-- 标题显示 -->
<div id="Title_bar">
    <div id="Title_bar_Head">
        <div id="Title_Head"></div>
        <div id="Title"><!--页面标题-->
            <img border="0" width="13" height="13" src="../style/images/title_arrow.gif"/> 配置权限
        </div>
        <div id="Title_End"></div>
    </div>
</div>

<!--显示表单内容-->
<div id=MainArea>
    <form method="post" id="privilegeFm">
    	<s:hidden name="id" id="hiddenroleid"/>
        <div class="ItemBlock_Title1"><!-- 信息说明 --><div class="ItemBlock_Title1">
        	<img border="0" width="4" height="7" src="./jsp/SysManage/images/item_point.gif" /> 正在为【${R_name}】配置权限 </div> 
        </div>
        
        <!-- 表单内容显示 -->
        <div class="ItemBlockBorder">
            <div class="ItemBlock">
                <table cellpadding="0" cellspacing="0" class="mainForm">
					<!--表头-->
					<thead>
						<tr align="LEFT" valign="MIDDLE" id="TableTitle">
							<td width="300px" style="padding-left: 7px;">
								<!-- 如果把全选元素的id指定为selectAll，并且有函数selectAll()，就会有错。因为有一种用法：可以直接用id引用元素 -->
								<input type="CHECKBOX" id="cbSelectAll" onClick="selectAll(this.checked)"/>
								<label for="cbSelectAll">全选</label>
							</td>
						</tr>
					</thead>
                   
			   		<!--显示数据列表-->
					<tbody id="TableData">
						<tr class="TableDetail1">
							<!-- 显示权限树 -->
							<td>
								<ul id="tree">
								<s:iterator value="#application.Topprivilegelist">
									<li>
										<input type="checkbox" name="privilegeIdlist" value="${id}" id="cb_${id}" <s:property value="%{id in privilegeIdlist ? 'checked' : ''}"/> />
										<label for="cb_${id}"><span class="folder">${P_name}</span></label>
										<ul>
										<s:iterator value="children">
											<li>
												<input type="checkbox" name="privilegeIdlist" value="${id}" id="cb_${id}" <s:property value="%{id in privilegeIdlist ? 'checked' : ''}"/> />
												<label for="cb_${id}"><span class="folder">${P_name}</span></label>
												<ul>
												<s:iterator value="children">
													<li>
														<input onclick="doparentChecked(this.checked)" type="checkbox" name="privilegeIdlist" value="${id}" id="cb_${id}" <s:property value="%{id in privilegeIdlist ? 'checked' : ''}"/> />
														<label for="cb_${id}"><span class="folder">${P_name}</span></label>
													</li>
												</s:iterator>
												</ul>
											</li>		
										</s:iterator>
										</ul>
									</li>
								</s:iterator>
								</ul>
							</td>
						</tr>
					</tbody>
                </table>
            </div>
        </div>
        <!-- 权限树的js -->
        <!-- 表单操作 -->
        <div id="InputDetailBar">
            <a href="#" onclick="privilegeFmSubmit();"><img src="./resource/image/save.png"/></a>
        </div>
    </form>
</div>

<div class="Description">
	说明：<br />
	1，选中一个权限时：<br />
	&nbsp;&nbsp;&nbsp;&nbsp; a，应该选中 他的所有直系上级。<br />
	&nbsp;&nbsp;&nbsp;&nbsp; b，应该选中他的所有直系下级。<br />
	2，取消选择一个权限时：<br />
	&nbsp;&nbsp;&nbsp;&nbsp; a，应该取消选择 他的所有直系下级。<br />
	&nbsp;&nbsp;&nbsp;&nbsp; b，如果同级的权限都是未选择状态，就应该取消选中他的直接上级，并递归向上做这个操作。<br />

	3，全选/取消全选。<br />
	4，默认选中当前岗位已有的权限。<br />
</div>

</body>
<script type="text/javascript">
	function privilegeFmSubmit(){
		var Hiddenroleid=document.getElementById("hiddenroleid").value;
		//alert(Hiddenroleid);
		$('#privilegeFm').form('submit',{
			url:'RoleServlet_privilege?Roleid='+Hiddenroleid,
			success:function(data){
				var data=eval('('+data+')');
	            if(data.success){
	            	//$.messager.alert('系统提示',"修改权限成功！");
	            	alert("修改权限成功！");
	            }else
	                $.messager.alert('系统提示',data.errorMsg);
			}
		})
	}
</script>
</html>