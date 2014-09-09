<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<div class="easyui-layout" data-options="fit:true" style="padding:5px;">
  <div region="west" style="width:200px;padding:5px;" title="资源目录" split="true">
    <ul class="easyui-tree" >
      <li> <span>所有资源</span>
        <ul>
          <li>公共资源</li>
          <li>个人资源</li>
        </ul>
      </li>
    </ul>
  </div>
  <div region="center" style="border:none;">
    <table id="DocumentDg">
    </table>
  </div>
</div>
<div id="UploadFileDlg" class="easyui-window" closed="true" style="width:300px; height:120px;" maximizable="false"   modal="true" title="上传文件" minimizable="false" collapsible="false">
  <form id="UploadFileFm" method="post" enctype="multipart/form-data">
    <table>
      <tr>
        <td>文件：</td>
        <td><input type="file" />
        </td>
      </tr>
	        <tr>
        <td colspan="2" style="text-align:center;">
		<a href="#" class="easyui-linkbutton" iconCls="icon-ok">提交</a>
		</td>
 
      </tr>
    </table>
  </form>
</div>
<script type="text/javascript">
	$(function(){
		$('#DocumentDg').datagrid({
			fit:true, 
			url:'data/document.json',
			animate: true,
			columns:[[
				{field:'ck',checkbox:true},
				{field:'class',title:'文件类型',width:60},
				{field:'fileName',title:'文件名称',width:100},
				{field:'date',title:'创建时间',width:60},
				{field:'owner',title:'所有者',width:40}
			]], 
			rownumbers: true,               
			striped: true, 
			pagination:true,
			fitColumns:true,
			toolbar:[{
				text:'上传文件',
				iconCls:'icon-folderadd',
				handler:showUploadFile
			},{
				text:'下载文件',
				iconCls:'icon-foldergo',
				handler:DownloadFile
			}]
		});
		var p = $('#DocumentDg').datagrid('getPager');  
		$(p).pagination({  
			pageSize: 10,//每页显示的记录条数，默认为10  
			pageList: [5,10,20,99],//可以设置每页记录条数的列表  
			beforePageText: '第',//页数文本框前显示的汉字  
			afterPageText: '页    共 {pages} 页',  
			displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录' 
		}); 
	})
	function showUploadFile(){
		$('#UploadFileDlg').window('open');
	}
	function DownloadFile(){
		var row=$('#DocumentDg').datagrid('getSelected');
		if(row){
		
		}else
			$.messager.alert('系统提示','请选择要下载的文件');
	}
</script>
