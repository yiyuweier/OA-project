<%@page contentType="text/html;charset=UTF-8"%>
<!doctype html>
<html lang="en">

  <head>
    <title>流程列表</title>
    <%@include file="/jsp/menu/s.jsp"%>
  </head>

  <body>
	<!-- start of main -->
    <section id="m-main" class="span10" style="float:right">

      <article class="m-widget">
        <header class="header">
		  <h4 class="title">列表</h4>
		</header>
		<div class="content">

  <table id="demoGrid" class="m-table table-hover">
    <thead>
      <tr>
        <th class="sorting" name="id">编号</th>
        <th class="sorting" name="name">流程定义</th>
        <th class="sorting" name="createTime">创建时间</th>
        <th class="sorting" name="endTime">结束时间</th>
        <th class="sorting" name="assignee">负责人</th>
        <th width="170">&nbsp;</th>
      </tr>
    </thead>

    <tbody>
      <c:forEach items="" var="item">
      <tr>
	    <td></td>
	    <td></td>
	    <td><fmt:formatDate value="" pattern="yyyy-MM-dd HH:mm:ss" /></td>
	    <td><fmt:formatDate value="" pattern="yyyy-MM-dd HH:mm:ss" /></td>
	    <td><tags:user userId=""/></td>
        <td>
          <a href="workspace-viewHistory.do?processInstanceId=">历史</a>
          <a href="workspace-endProcessInstance.do?processInstanceId=">终止</a>
        </td>
      </tr>
      </c:forEach>
    </tbody>
  </table>
        </div>
      </article>

    </section>
	<!-- end of main -->
	</div>

  </body>

</html>
