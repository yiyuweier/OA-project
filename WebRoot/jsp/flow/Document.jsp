<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
   
   <link rel="stylesheet" href="./resource/css/toolbar.css" type="text/css"/>
   <link rel="stylesheet" href="./resource/css/common.css" type="text/css"/>
   <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
     
    <link rel="stylesheet" href="./jqeasyui/themes/gray/easyui.css" type="text/css"></link>
	<link rel="stylesheet" href="./jqeasyui/themes/gray/icon.css" type="text/css"></link>
	<link rel="stylesheet" href="./jsp/zTree/css/zTreeStyle/zTreeStyle.css" type="text/css">
	<script type="text/javascript" src="./jqeasyui/jquery-1.4.4.min.js"></script>
	<script type="text/javascript" src="./jqeasyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="./jsp/artdialog/artDialog.js"></script>
	<script type="text/javascript" src="./jsp/artdialog/artDialog.plugins.js"></script>
	<script type="text/javascript" src="./jsp/artdialog/extend/F.js"></script>
	<script type="text/javascript" src="./jsp/js/jquery/jquery.myajax.js"></script>
	<script type="text/javascript" src="./jsp/js/jquery/jquery.form.js"></script>
	<script type="text/javascript" src="./jsp/zTree/js/jquery.ztree.core-3.5.js"></script>
	<script type="text/javascript" src="./resource/js/selectemp.js"></script>
	<script type="text/javascript" src="./jsp/ckeditor/ckeditor.js"></script>
	<script type="text/javascript" src="./jsp/ckfinder/ckfinder.js"></script>
     
   
   <script type="text/javascript">
   window.onload=function(){
   		var editor = CKEDITOR.replace('D_content');
   		CKFinder.SetupCKEditor(editor,'/jsp/ckfinder/');
   }
   
   	var tabObj;
 	$(function() {
	 	tabObj = $( "#tabs" ).tabs();	
	 });
	 
	 
	 function showusers(id,type){
			//alert("here");
			var xmlhttp;
			if (window.XMLHttpRequest){// code for IE7+, Firefox, Chrome, Opera, Safari
  				xmlhttp=new XMLHttpRequest();
  			}else{// code for IE6, IE5
  				xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
  			}
			xmlhttp.onreadystatechange=function(){
  				if (xmlhttp.readyState==4 && xmlhttp.status==200){
  					//alert(xmlhttp.responseText);
  					var usernamelist = xmlhttp.responseText.substring(1,xmlhttp.responseText.length-1);
  					//alert(usernamelist);
  					var arry = usernamelist.split(",");
  					var ParentElement = document.getElementById("list1").childNodes;
  					if(ParentElement.length!=0){
  						//alert("ParentElement的长度为："+ParentElement.length);
  						for(var j=0;j<=ParentElement.length;j++){
  							//alert("元素："+ParentElement[j]);
  							document.getElementById("list1").removeChild(ParentElement[0]);
  						}
  					}
  					for(var i=0;i<arry.length;i++){
  						//alert(arry[i]);
  						var newElement = document.createElement("option");
  						newElement.innerHTML=arry[i];
  						document.getElementById("list1").appendChild(newElement);
  					}
  					//document.getElementById("list1").list=arry;
    				//document.getElementById("userlistdiv").innerHTML="";
    			}
  			}
  			if(type=="department"){
  				xmlhttp.open("POST","GetUsers_FromDarpId.action?id="+id,true);
  			}else if(type=="role"){
  				xmlhttp.open("POST","GetUsers_FromRoleId.action?id="+id,true);
  			}
			
			xmlhttp.send();
	}

var zTreeDepartmentObj,
	zTreeDepartmentSetting = {
		view: {
			selectedMulti: true,//是否能够多选
			expandSpeed: ""//树形列表打开时的动画速度
		},
		async: {
                enable: true,//设置为异步加载
                type: "post",//传输方式为post
                url:"zTreeJson_action.action",//处理action的url
                autoParam:["id"]//自动传递给action的id
        },
        data: {
                simpleData: {
                enable: true,
                idKey: "id",
                pIdKey: "pId",
                rootPId: 0
               }
        }
	},
	zTreeDepartmentNodes = [{"id":"-1","pId":null,"name":"全部部门",click:"alert('123');",isParent:true}];
	
var zTreeRoleObj,
	zTreeRoleSetting = {
		view: {
			selectedMulti: true,//是否能够多选
			expandSpeed: ""//树形列表打开时的动画速度
		},
		async: {
                enable: true,//设置为异步加载
                type: "post",//传输方式为post
                url:"zTreeJson_getUserByRole.action",//处理action的url
                autoParam:["id"]//自动传递给action的id
        },
        data: {
                simpleData: {
                enable: true,
                idKey: "id",
                pIdKey: "pId",
                rootPId: 0
               }
        }
	},
	zTreeRoleNodes = [{"id":"-1","pId":null,"name":"全部职务",click:"alert('123');",isParent:true}];
	$(document).ready(function(){
		zTreeDepartmentObj = $.fn.zTree.init($("#DeptTreeDiv"), zTreeDepartmentSetting, zTreeDepartmentNodes);
		zTreeRoleObj = $.fn.zTree.init($("#TitleTreeDiv"), zTreeRoleSetting, zTreeRoleNodes);
	});
   
   	
   </script>
<style type="text/css">
.summary{
	background-color: #e9e9e9;
	padding-top: 3px;
	padding-bottom: 3px;
	border:1px solid #aaa;
}
.trackopinion{
	background-color: #F3F5F6;
	padding-top: 4px;
	padding-bottom: 3px;
	padding-left: 2px;
	border-left:1px solid #aaa;
	border-right:1px solid #aaa;
	line-height:24px;
}
.width-65px{
 width:65px;
}
.width-261px{
 width:201px;
}
.width-279px{
 width:219px;
}
.width-277px{
 width:217px;
}
.width-658px{
 width:638px;
}
.width-656px{
 width:636px;
}
#trackDescription{
 width:194px;
 max-width:194px;
 width:196px\9;
 max-width:196px\9;
 margin: 0;
}
</style>
</head>
<body>
<div class="f-toolbar">
  <div style="text-align:left;float:left;margin:0px;"><button id="button_send" onclick="save()"><img src="./resource/image/send.png">立即发送</button></div>
  <div style="text-align:left;float:left;margin:0px;"><button id="button_save" onclick="save(0)"><img src="./resource/image/save.gif">保存待发</button></div>
  <div style="text-align:left;float:left;margin:0px;"><button id="button_flow" onclick="viewFlowDialog()"><img src="./resource/image/flow.png">查看流程</button></div>
  <div style="text-align:left;float:left;margin:0px;"><button id="button_attach" onclick="addAttach()"><img src="./resource/image/attach.png">添加附件</button></div>
  <div style="text-align:left;float:left;margin:0px;"><button id="button_affair" onclick="addAffair()"><img src="./resource/image/affair.png">添加关联事项</button></div>
  <div style="text-align:left;float:left;margin:0px;"><button id="button_template_save" onclick="saveTemplate()"><img src="./resource/image/saveas.gif">存为模板</button></div>
  <div style="text-align:left;float:left;margin:0px;"><button id="button_template_use" onclick="useTemplate()"><img src="./resource/image/template_use.png">模板调用</button></div>
  <div style="text-align:left;float:left;margin:0px;"><button id="button_template_manage" onclick="manageTemplate()"><img src="./resource/image/template_manage.png">模板管理</button></div>
  <div style="text-align:left;float:left;margin:0px;"><button id="button_advance" onclick="showAdvance()"><img src="./resource/image/advance.png">高级</button></div>
</div>
<form id="myform" name="myform" action="startProcessInstance_Document" method="post" onsubmit="return false;" enctype="multipart/form-data">
	<div class="summary" id="summaryDiv">
 		<div class="divline">
  			<div class="col-label width-65px">标题：</div>
  			<div class="col-input" id="col_subject">
   				<input name="D_name" id="D_name" type="text" class="input-text" maxlength="200" defaultValue="<点击此处填写标题>" value="<点击此处填写标题>" onfocus='checkDefSubject(this, true)' onblur="checkDefSubject(this, false)">
  			</div>
  			<div class="col-label width-65px">保密程度：</div>
  			<div class="col-input" style="width:62px;">
   				<select style="width:60px;word-wrap:normal;" id="secretLevel" name="secretLevel">
    
   				</select>
  			</div>
  			<div class="col-label width-65px">紧急程度：</div>
  			<div class="col-input" style="width:62px;">
   				<select style="width:60px;word-wrap:normal;" id="urgentLevel" name="urgentLevel">
    
   				</select>
  			</div>
 		</div>
 		
 		<div id="addAttachDlgMain" class="panel window" style="display: none; z-index: 9005;">
 			<div class="panel-header panel-header-noborder window-header" style="width: 338px;">
 				<div class="panel-title">添加附件</div><div class="panel-tool">
 					<a class="panel-tool-close" onclick="add();"></a>
 				</div>
 			</div>
	  		<table>
			    <tbody><tr>
			      <td>附件：</td>
			      <td>
				  <input type="file" id="FJInput" name="image">
				  </td>
			    </tr>
				<tr>
			      <td colspan="2">
			      <a iconcls="icon-ok" class="easyui-linkbutton l-btn" href="#" onclick="add();" group="">添加</a>
				  </td>
			    </tr>
			</tbody></table>
		</div>
 		
 		<div class="divline">
 			<div class="col-label width-65px">发送到：</div>
  			<div class="col-input">
    			<input id="trackInput" name="D_sendto" type="text" class="input-text" style="cursor:pointer;" readonly maxlength="200" title="<点击此处新建流程>" value="<点击此处新建流程>" onclick="$('#userPicker').window('open');return false;">
  			</div>
  			<div class="col-input">
  				<s:radio list="#{'1':'串行','2':'并行'}" name="D_type" id="D_type" label="会签方式"/>
  			</div>
 		</div>
	</div>
   	<textarea name="D_content" id="D_content"></textarea>
</form>
  
	
	
	
	
	
	<div id="userPicker"  class="easyui-window" title="选择接收人" closed="true" resizable="false"
     maximizable="false"
    modal="true" title="选择用户" minimizable="false" collapsible="false" >
		<div id="tabs" class="easyui-tabs"
			style="margin-left:0;padding:0;width: 600px;height: 400px;">
			<div title="部门">
				<div id="tabs-1"
					style="margin-left:2px;padding:1px;width:230px;height:400px;">
					<div
						style="overflow-y:scroll;overflow-x:auto;height:170px;width:218px;border:1px solid #aaa;display:block;margin-top:5px;"
						id="deptDiv">
						<ul id="DeptTreeDiv" class="ztree" style="margin:0;">
						</ul>
					</div>
				</div>
			</div>
			<div title="职务">
				<div id="tabs-2"
					style="margin-left:2px;padding:1px;width:230px;height:400px;">
					<div
						style="overflow-y:scroll;overflow-x:auto;height:170px;width:218px;border:1px solid #aaa;display:block;margin-top:5px;"
						id="titleDiv">
						<ul id="TitleTreeDiv" class="ztree" style="margin:0;">
						</ul>
					</div>
				</div>
			</div>
			<div title="组">
				<div id="tabs-3"
					style="margin-left:2px;padding:1px;width:230px;height:400px;">
					<div
						style="overflow-y:scroll;overflow-x:auto;height:170px;width:218px;border:1px solid #aaa;display:block;margin-top:5px;"
						id="teamDiv">
						<ul id="TeamTreeDiv" class="ztree" style="margin:0;">
							<li>销售一组</li>
							<li>销售二组</li>
							<li>销售三组</li>
						</ul>
					</div>
				</div>
			</div>
			<div title="组合查询">
				<div id="tabs-4"
					style="margin-left:2px;padding:1px;width:230px;height:400px;">
					<div
						style="height:170px;width:218px;border:1px solid #aaa;display:block;margin:0px;"
						id="queryDiv">
						<div style="height:20px;font-weight:bold;padding-top:5px;">查询条件：</div>
						<div style="height:20px;">
							部门：<input type="text" readonly class="input-search"
								style="width:163px;" id="deptName" name="deptName"
								onclick="selectDeptObj.select();">
						</div>
						<div class="hidden" style="padding-left:32px;">
							<input type="checkbox" id="isContainSub" name="isContainSub"
								checked><label for="isContainSub">包含下级部门</label>
						</div>
						<div style="margin-top:2px;">
							职务：<input type="text" readonly class="input-search"
								style="width:163px;" id="titleName" name="titleName"
								onclick="selectTitleObj.select();">
						</div>
						<div style="height:20px;padding-top:5px;text-align:right;">
							<button onclick="query()" title="查询" class="button">查询</button>
						</div>
					</div>
				</div>
			</div>
		</div>

		<div style="position:absolute;top:250px;left:11px;" id="userlistdiv">
			<select multiple id="list1" name="list1"
				style="width:220px;height:180px;border:1px solid #aaa;overflow-y:scroll;overflow-x:auto;"
				ondblclick="addSelected()" size="10">

			</select>
		</div>
		<div
			style="position:absolute;top:68px;left:285px;width:220px;height:350px;">
			<select multiple id="list2" name="list2"
				style="width:220px;height:360px;" ondblclick="removeSelected()"></select>
		</div>
		<div style="position:absolute;top:140px;left:237px;width:46px;">
			<button onClick="removeOrgSelected()" class="button"
				style="width:20px;padding:0;" title="移除选中"><</button>
			<button onClick="addOrgSelected()" class="button"
				style="width:20px;padding:0;" title="添加选中">></button>
		</div>
		<div style="position:absolute;top:300px;left:237px;width:46px;">
			<button onClick="removeSelected()" class="button"
				style="width:20px;padding:0;" title="移除选中"><</button>
			<button onClick="addSelected()" class="button"
				style="width:20px;padding:0;" title="添加选中">></button>
			<br> <br> <br> <br>
			<button onClick="removeAll()" class="button"
				style="width:20px;padding:0;" title="移除全部"><<</button>
			<button onClick="addAll()" class="button"
				style="width:20px;padding:0;" title="添加全部">>></button>
		</div>
		<div style="position:absolute;top:200px;left:505px;width:20px;">
			<img src="resource/image/up.png" style="cursor:pointer;" title="向上移动"
				onclick="moveUp()"> <br> <br> <img
				src="resource/image/down.png" style="cursor:pointer;" title="向下移动"
				onclick="moveDown()">
		</div>
		<div align="center" style="margin-top:0px;">
			<BUTTON class=Btnnormal
				onmousedown="javascript:this.className='Btnover'"
				onmouseover="javascript:this.className='Btnover'" title='确定' id="ok"
				onmouseout="javascript:this.className='Btnnormal'" onclick="ok();">&nbsp;&nbsp;确定&nbsp;&nbsp;</BUTTON>
				
				
			&nbsp;
			<BUTTON class=Btnnormal
				onmousedown="javascript:this.className='Btnover'"
				onmouseover="javascript:this.className='Btnover'" title='取消'
				accessKey=S onmouseout="javascript:this.className='Btnnormal'"
				onclick="closeArtDialog();">&nbsp;&nbsp;取消&nbsp;&nbsp;</BUTTON>
			&nbsp;
		</div>
	</div>

	 
</body>

<script type="text/javascript" src="/common/components/upload/js/upload.js"></script>
<script type="text/javascript">
	
	
	function createFlow(){
  		alert("here");
  		window.showModalDialog('./jsp/js/selectemp/selectempforcollaborate1.jsp');
	}  
   	function closeNewDiv(id){
  		var newDiv = document.getElementById(id);
   		newDiv.style.display = "none";
  	}
  	var docEle = function() {
   		return document.getElementById(arguments[0]) || false;
	}
	function openNewDiv(id) {
    	//新激活图层
   		var newDiv = document.getElementById(id);
   		newDiv.style.display = "block";
   		newDiv.style.position = "absolute";
   		newDiv.style.zIndex = "9999";
   		newDiv.style.width = "600px";
   		newDiv.style.height = "350px";
   		newDiv.style.top = "50px";
   		newDiv.style.left = (parseInt(document.body.scrollWidth) - 300) / 2 + "px"; // 屏幕居中
   		newDiv.style.background = "EEEEEE";
   		newDiv.style.border = "1px solid #0066cc";
   		newDiv.style.padding = "5px";
   		newDiv.style.overflow = "auto";
    	return false;
   }
   
   function save(){
   		var D_name = document.getElementById("D_name").value;
   		if(D_name==null||D_name=="<点击此处填写标题>"){
   			alert("标题不能为空！请填写标题！");
   			return false;
   		}
   		var D_seneto = document.getElementById("trackInput").value;
   		if(D_seneto==null||D_seneto=="<点击此处新建流程>"){
   			alert("发送到不能为空！请填写发送到！");
   			return false;
   		}
   		var D_type1 = document.getElementById("D_type1");
   		var D_type2 = document.getElementById("D_type2");
   		if((!D_type1.checked)&&(!D_type2.checked)){
   			alert("会签方式不能为空！请选择会签方式！");
   			return false;
   		}
   		var D_content = CKEDITOR.instances.D_content.getData();
   		if(D_content==null||D_content==""){
   			alert("内容不能为空！请填写内容！");
   			return false;
   		}
   		document.myform.submit();
   
   }
   
   function ok(){
   		var SelectedElement = document.getElementById("list2");
   		var selectedValue;
   		if(SelectedElement.length!=0){
   			selectedValue = SelectedElement[0].value;
   		}
   		for(var i=1;i<SelectedElement.length;i++){
   			selectedValue = selectedValue + ","+ SelectedElement[i].value;
   		}
   		alert(selectedValue);
   		document.getElementById("trackInput").value = selectedValue;
   		$('#userPicker').window('close');
   }
   
   	function addAttach(){
   		alert("添加附件！");
		var addAttachDlg = document.getElementById("addAttachDlgMain");
		alert("获取div:addAttachDlgMain");
		addAttachDlg.style.display = "block";
		addAttachDlg.style.width = "338px";
		addAttachDlg.style.left = "387px";
   		addAttachDlg.style.height = "150px";
   		addAttachDlg.style.top = "98px";
	}
	function add(){
		document.getElementById("addAttachDlgMain").style.display = "none";
	}
	
	
   
   
</script>

</html>
