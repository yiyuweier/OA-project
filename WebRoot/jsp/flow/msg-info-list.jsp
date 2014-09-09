<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!doctype html>
<html lang="en">

  <head>
    <title>列表</title>
    <%@include file="/jsp/menu/s.jsp"%>
    <script type="text/javascript">
var config = {
    id: 'msg-infoGrid',
    pageNo: ${page.pageNo},
    pageSize: ${page.pageSize},
    totalCount: ${page.totalCount},
    resultSize: ${page.resultSize},
    pageCount: ${page.pageCount},
    orderBy: '${page.orderBy == null ? "" : page.orderBy}',
    asc: ${page.asc},
    params: {
        'filter_LIKES_name': '${param.filter_LIKES_name}'
    },
	selectedItemClass: 'selectedItem',
	gridFormId: 'msg-infoGridForm',
	exportUrl: 'msg-info-export.do'
};

var table;

$(function() {
	table = new Table(config);
    table.configPagination('.m-pagination');
    table.configPageInfo('.m-page-info');
    table.configPageSize('.m-page-size');
});
    </script>
  </head>

  <body>

    <div class="row-fluid">

	  <!-- start of main -->
      <section id="m-main" class="span10">
	  <article class="m-blank">
		<div class="pull-right">
		  每页显示
		  <select class="m-page-size">
		    <option value="10">10</option>
		    <option value="20">20</option>
		    <option value="50">50</option>
		  </select>
		  条
		</div>

	    <div class="m-clear"></div>
	  </article>

      <article class="m-widget">
        <header class="header">
		  <h4 class="title">列表</h4>
		</header>
        <div class="content">
<form id="msg-infoGridForm" name="msg-infoGridForm" method='post' action="msg-info-remove.do" class="m-form-blank">
  <table id="msg-infoGrid" class="m-table table-hover">
    <thead>
      <tr>
        <th width="10" class="m-table-check"><input type="checkbox" name="checkAll" onchange="toggleSelectedItems(this.checked)"></th>
        <th class="sorting" name="id">编号</th>
        <th class="sorting" name="name">消息名称</th>
        <th class="sorting" name="name">接收人</th>
        <th class="sorting" name="name">发送人</th>
        <th class="sorting" name="name">状态</th>
      </tr>
    </thead>

    <tbody>
      <s:iterator value="#session.messagelist">
      <tr>
        <td><input type="checkbox" class="selectedItem a-check" name="selectedItem" value="${id}"></td>
        <td>${id}</td>
        <td><s:a href="Jbpm_getDetailTask?executionId=%{execution_id}&state=%{state}&taskId=%{task_id}&userid=%{#session.user.getId()}">${M_name}</s:a></td>
        <td>${sendto}</td>
        <td>${initiator}</td>
        <td>${state == true? '已读':'未读'}</td>
        
      </tr>
      </s:iterator>
    </tbody>
  </table>
</form>
        </div>
      </article>

	  <article>
	    <div class="m-page-info pull-left">
		  共100条记录 显示1到10条记录
		</div>

		<div class="btn-group m-pagination pull-right">
		  <button class="btn btn-small">&lt;</button>
		  <button class="btn btn-small">1</button>
		  <button class="btn btn-small">&gt;</button>
		</div>

	    <div class="m-clear"></div>
      </article>

      <div class="m-spacer"></div>

      </section>
	  <!-- end of main -->
	</div>

  </body>

</html>
