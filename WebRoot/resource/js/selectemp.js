function addSelected(){
	var options = $("#list2 option");
	$("#list1 option:selected").each(function(){
		if(!isListContains(options,$(this).val())){
		  $("#list2").append("<option value='"+$(this).val()+"' title='"+$(this).attr("title")+"'>"+$(this).text()+"</option>");　　
		};
	});
}
function removeSelected(){
　　$("#list2 option:selected").each(function(){
　　　　$(this).remove();　
　　});
}
function addAll(){	
	var options = $("#list2 option");
	$("#list1 option").each(function(){
	  if(!isListContains(options,$(this).val())){
		  $("#list2").append("<option value='"+$(this).val()+"' title='"+$(this).attr("title")+"'>"+$(this).text()+"</option>");　  
	  };	　
	});
}
function removeAll(){
  $("#list2").empty();
}
function isListContains(options,value){
	var result = false;
	options.each(function(){		
		if($(this).val()==value){
			result = true;
			return false;
		}
	});
	return result;
}
//向上排序
function moveUp(){
 var box = document.all.list2;
 var temp_opts = new Object();
 var i = box.selectedIndex;
 var v;
 var text;
 var title;
 var c;

if(i!=0 && i!=-1){
    v =   box.options[i-1].value;
    text =  box.options[i-1].text;
    title = box.options[i-1].title;
    c = box.options[i-1].className;


    box.options[i-1].value =   box.options[i].value;
    box.options[i-1].text =  box.options[i].text;
    box.options[i-1].title =  box.options[i].title;
    box.options[i-1].className = box.options[i].className;


    box.options[i].value = v;
    box.options[i].text = text;
    box.options[i].title = title;
    box.options[i].className = c;

    box.options[i-1].selected = true;
    box.options[i].selected = false;
 }
}


//向下排序
function moveDown(box){
 var box = document.all.list2;
 var temp_opts = new Object();
 var i = box.selectedIndex;
 var v;
 var text;
 var title;
 var c;


if(i!=box.options.length-1 && i!=-1){

    v =   box.options[i+1].value;
    text =  box.options[i+1].text;
    c = box.options[i+1].className;

    box.options[i+1].value =   box.options[i].value;
    box.options[i+1].text =  box.options[i].text;
    box.options[i+1].title =  box.options[i].title;
    box.options[i+1].className = box.options[i].className;

    box.options[i].value = v;
    box.options[i].text = text;
    box.options[i].title = title;
    box.options[i].className = c;

    box.options[i].selected = false;
    box.options[i+1].selected = true;
 }
}

function findDeptEmpListByName(){
	 var deptId = 0;
	 if(deptTreeObj.getSelectedNodes().length>0 && $('#deptEmpName').val()==''){
		deptId = deptTreeObj.getSelectedNodes()[0].id;
	 }
	 $('#empName').val($('#deptEmpName').val());
	 $('#findBy').val('DeptEmp'); 
	 $('#deptId').val(deptId);
	 findEmpList();
}
function findTitleEmpListByName(){
	 var titleId = 0;
	 if(titleTreeObj.getSelectedNodes().length>0 && $('#titleEmpName').val()==''){
			titleId = titleTreeObj.getSelectedNodes()[0].id;
	 }
	 $('#empName').val($('#titleEmpName').val());
	 $('#findBy').val('TitleEmp'); 
	 $('#titleId').val(titleId);
	 findEmpList();
}
function query(){
	 $('#findBy').val('Query'); 
	 $('#containsSub').val($("#isContainSub").attr("checked")==true); 
	 if($('#deptName').val()==''){
		 $('#deptId').val(0);
	 }
	 if($('#titleName').val()==''){
		 $('#titleId').val(0);
	 }
	 findEmpList(); 
}
function findEmpList(){
	ajaxSubmitForm('findByNameForm',function(responseJSON){
		 var empList = $.json.decode(responseJSON);
		 $("#list1").empty();
		 for(var i=0;i<empList.length;i++){
			$("#list1").append("<option value='P"+empList[i].id+"' title='"+empList[i].name+"'>"+empList[i].text+"</option>");　
		 }
	 });
}
function deptTreeOnClick(event, treeId, treeNode) {
	deptTreeObj.expandNode(treeNode);
	//点击查询属于这个部门的所有人员
	if(treeNode.isParent==false){
		$('#findBy').val('Dept'); 
		$('#deptId').val(treeNode.id);
		findEmpList();
	}
}
function titleTreeOnClick(event, treeId, treeNode) {
	//点击查询属于这个职务的所有人员
	if(treeNode.isParent==false){
		$('#findBy').val('Title'); 
		$('#titleId').val(treeNode.id);
		findEmpList();
	}
}
function teamTreeOnClick(event, treeId, treeNode) {
	//点击查询属于这个职务的所有人员
	if(treeNode.isParent==false){
		$('#findBy').val('Team'); 
		$('#teamId').val(treeNode.id);
		findEmpList();
	}
}