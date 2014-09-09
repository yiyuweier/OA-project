function LoginFmSubmit(){
	$('#FmLogin').form('submit',{
		url:'data/login.json',
		success:function(data){
			var data=eval('('+data+')');
			if(data.success){
				window.href=data.href;	
			}else
				$.messager.alert(data.errorMsg);
		}
	})	
}
function LoginFmCancel(){
	$('#FmLogin').form('reset');	
}
