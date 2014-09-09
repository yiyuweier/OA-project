<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!doctype html>
<html lang="en">
<%@include file="/jsp/menu/s.jsp"%>
  <head>
    <title>流程列表</title>
  </head>
  <body>
    <div class="row-fluid">
    <!-- start of main -->
    <section id="m-main" class="span10" style="float:left">
	<s:if test="#session.FlowCategories==null">没有可用的流程！请联系管理员添加相应的流程</s:if>
	<s:iterator value="#session.FlowCategories">
    	<div class="page-header">
        	<h3><s:property value="%{F_name}"/></h3>
      	</div>
		<s:iterator value="flowProcess">
        	<div class="well span2">
          		<h4><s:property value="%{F_name}"/>&nbsp;</h4>
          		<p><s:property value="%{F_desc}"/>&nbsp;</p>
          		<div class="btn-group">
            		<a class="btn btn-small" href="workplace_${F_key}?flowProcessId=${id}"><li class="icon-play"></li>发起</a>
            		<!-- 
            		<a class="btn btn-small" href="ShowFlowImage_viewAllImage?flowProcessId=${F_id}">图形</a>
            		 -->
            		 <a class="btn btn-small" href="jsp/jbpm/AllImage.jsp?flowname=${F_name}">图形</a>
          		</div>
        	</div>
      	</s:iterator>
	</s:iterator>

    </section>
    <!-- end of main -->
    </div>
    
   
  </body>
<script type="text/javascript">
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
   		newDiv.style.width = "400px";
   		newDiv.style.height = "300px";
   		newDiv.style.top = "100px";
   		newDiv.style.left = (parseInt(document.body.scrollWidth) - 300) / 2 + "px"; // 屏幕居中
   		newDiv.style.background = "EEEEEE";
   		newDiv.style.border = "1px solid #0066cc";
   		newDiv.style.padding = "5px";
   		newDiv.style.overflow = "auto";
   		
   		
   	
   		// mask图层
   		//var newMask = document.createElement("div");
   		//newMask.id = m;
   		//newMask.style.position = "absolute";
   		//newMask.style.zIndex = "1";
   		//newMask.style.width = document.body.scrollWidth + "px";
   		//newMask.style.height = document.body.scrollHeight + "px";
   		//newMask.style.top = "0px";
   		//newMask.style.left = "0px";
   		//newMask.style.background = "#000";
   		//newMask.style.filter = "alpha(opacity=40)";
   		//newMask.style.opacity = "0.40";
   		//document.body.appendChild(newMask);
   		// 关闭mask和新图层
   		//var newA = document.createElement("a");
   		//newA.href = "#";
   		//newA.innerHTML = "关闭激活层";
   		//newA.onclick = function() {
    		//document.body.removeChild(docEle(_id));
    		//document.body.removeChild(docEle(m));
    		//return false;
   		//}
   		//newDiv.appendChild(newA);
   
    	return false;
   }


</script>
</html>
