/*!
 * myajax在jquery和jquery.form的基础上完成，使用详见各个方法说明
 * 2009-06-06 by liguangchao
 */


/**
 @说明 用于form的提交
 @参数 formId form的Id属性
      callback 回调函数
      dataType 返回值类型 text|json|xml|script|html
*/
function ajaxSubmitForm(formId,callback,dataType){
 var form = $('#'+formId);
 form.ajaxForm();
 var queryString = form.formSerialize();
 $.post(form.attr('action'),queryString,callback,dataType);
}

/**
 @说明 用于异步查询
 @参数 url 查询地址
      callback 回调函数
      dataType 返回值类型 text|json|xml|script|html
*/
function ajaxQuery(url,callback,dataType){
 $.post(url,'',callback,dataType);
}

/**
  @说明 用于异步带异常操作的查询
 @参数 url 查询地址
      callback 回调函数
      dataType 返回值类型 text|json|xml|script|html
      exceptcall 异常回调函数
*/
function ajaxQueryException(url,callback,dataType,exceptcall){
	$.ajax({
    url: url,
    type: 'POST',
    dataType: dataType,
    timeout: 1000000,
    error: exceptcall,
    success:callback
});
	
}