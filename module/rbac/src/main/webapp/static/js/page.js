function toPageFirst(action){
	if($("#view\\.currentPage").val()!=1) {
		// 活动管理翻页特殊处理
		var rootTypeId = null;
		var typeId = null;
		var insuranceCompanyId = null;
		if (document.getElementById("view.rootTypeId")){
			rootTypeId = document.getElementById("view.rootTypeId").value;
		}
		if (document.getElementById("view.typeId")){
			typeId = document.getElementById("view.typeId").value;
		}
		if ($("#view\\.insuranceCompanyId")) {
			insuranceCompanyId = $("#view\\.insuranceCompanyId").val();
		}
		$("#form")[0].reset();
		if (rootTypeId != null){
			$("#view\\.rootTypeId").val(rootTypeId);
		}
		if (typeId != null){
			$("#view\\.typeId").val(typeId);	
		}
		if (insuranceCompanyId != null) {
			$("#view\\.insuranceCompanyId").val(insuranceCompanyId);
		}
		if($("#view\\.totalPages").val()>=1){
			$("#view\\.currentPage").val(1);
		}
		
    	action();
	}
}
// 符策鹏 修改view.currentPage为view.thisPage 2013/7/25
function toPagePre(action){
	if($("#view\\.thisPage").val()!=1) {
		// 活动管理翻页特殊处理
		var rootTypeId = null;
		var typeId = null;
		var insuranceCompanyId = null;
		if (document.getElementById("view.rootTypeId")){
			rootTypeId = document.getElementById("view.rootTypeId").value;
		}
		if (document.getElementById("view.typeId")){
			typeId = document.getElementById("view.typeId").value;
		}
		if ($("#view\\.insuranceCompanyId")) {
			insuranceCompanyId = $("#view\\.insuranceCompanyId").val();
		}
		$("#form")[0].reset();
		if (rootTypeId != null){
			$("#view\\.rootTypeId").val(rootTypeId);
		}
		if (typeId != null){
			$("#view\\.typeId").val(typeId);	
		}
		if (insuranceCompanyId != null) {
			$("#view\\.insuranceCompanyId").val(insuranceCompanyId);
		}
		var pageNumber = parseInt($("#view\\.thisPage").val())-1;
		if(pageNumber>=0){
			$("#view\\.currentPage").val(pageNumber);
		}
    	action();
	}
}
// 符策鹏 修改view.currentPage为view.thisPage 2013/7/25
function toPageNext(action){  
	if($("#view\\.thisPage").val()!=$("#view\\.totalPages").val()&&$("#view\\.currentPage").val()!=0) {
		// 活动管理翻页特殊处理
		var rootTypeId = null;
		var typeId = null;
		var insuranceCompanyId = null;
		if (document.getElementById("view.rootTypeId")){
			rootTypeId = document.getElementById("view.rootTypeId").value;
		}
		if (document.getElementById("view.typeId")){
			typeId = document.getElementById("view.typeId").value;
		}
		if ($("#view\\.insuranceCompanyId")) {
			insuranceCompanyId = $("#view\\.insuranceCompanyId").val();
		}
		$("#form")[0].reset();
		if (rootTypeId != null){
			$("#view\\.rootTypeId").val(rootTypeId);
		}
		if (typeId != null){
			$("#view\\.typeId").val(typeId);	
		}
		if (insuranceCompanyId != null) {
			$("#view\\.insuranceCompanyId").val(insuranceCompanyId);
		}
		$("#view\\.currentPage").val(parseInt($("#view\\.thisPage").val())+1);
    	action();
    	
   	}
}
function toPageLast(action){
	if($("#view\\.currentPage").val()!=$("#view\\.totalPages").val()) {
		// 活动管理翻页特殊处理
		var rootTypeId = null;
		var typeId = null;
		var insuranceCompanyId = null;
		if (document.getElementById("view.rootTypeId")){
			rootTypeId = document.getElementById("view.rootTypeId").value;
		}
		if (document.getElementById("view.typeId")){
			typeId = document.getElementById("view.typeId").value;
		}
		if ($("#view\\.insuranceCompanyId")) {
			insuranceCompanyId = $("#view\\.insuranceCompanyId").val();
		}
		$("#form")[0].reset();
		if (rootTypeId != null){
			$("#view\\.rootTypeId").val(rootTypeId);
		}
		if (typeId != null){
			$("#view\\.typeId").val(typeId);	
		}
		if (insuranceCompanyId != null) {
			$("#view\\.insuranceCompanyId").val(insuranceCompanyId);
		}
		$("#view\\.currentPage").val($("#view\\.totalPages").val());
    	action();
	}
}
resetCurrentPage=function(){
	$("#view\\.currentPage").val(1);
};
function toPageGo(action){
	
	var currentPage = parseInt($("#view\\.currentPage").val());
	var totalPages = parseInt($("#view\\.totalPages").val());
	// 活动管理翻页特殊处理
	var rootTypeId = null;
	var typeId = null;
	var insuranceCompanyId = null;
	if (document.getElementById("view.rootTypeId")){
		rootTypeId = document.getElementById("view.rootTypeId").value;
	}
	if (document.getElementById("view.typeId")){
		typeId = document.getElementById("view.typeId").value;
	}
	if ($("#view\\.insuranceCompanyId")) {
		insuranceCompanyId = $("#view\\.insuranceCompanyId").val();
	}
	$("#form")[0].reset();
	if (rootTypeId != null){
		$("#view\\.rootTypeId").val(rootTypeId);
	}
	if (typeId != null){
		$("#view\\.typeId").val(typeId);	
	}
	if (insuranceCompanyId != null) {
		$("#view\\.insuranceCompanyId").val(insuranceCompanyId);
	}
	// 判断转向页为是否为空
	
	if(isNaN(currentPage)){
		jAlert("请输入页码！");
		return false;
	}

	// 转向页大于总页数
	if(currentPage > totalPages||currentPage<=0&&totalPages!=0) {
		// $("#view\\.currentPage").val(totalPages);
		// 符策鹏
		jAlert("页码不存在，请重新输入！");
		return;	
	} else {
		$("#view\\.currentPage").val(currentPage);
	}
	action();
}
function checkedAll(event) {
	var obj = event.srcElement?event.srcElement:event.target;
	var nodeList = document.getElementsByName("checked");
	if(nodeList.length==0){
		jAlert("没有可选择的数据！");	
		obj.checked=false;
		return false;
	}else{
		for(var i=0; i < nodeList.length;i++) {
			// checkbox可用时选中
			if(!nodeList[i].disabled) {
				nodeList[i].checked=obj.checked;	
			}
		}
	}
}

function checkedByName(event,name) {
	var obj = event.srcElement?event.srcElement:event.target;
	var nodeList = document.getElementsByName(name);
	if(nodeList.length==0){
		jAlert("没有可选择的数据！");	
		obj.checked=false;
		return false;
	}else{
		for(var i=0; i < nodeList.length;i++) {
			// checkbox可用时选中
			if(!nodeList[i].disabled) {
				nodeList[i].checked=obj.checked;	
			}
		}
	}
}

function clickCheckedData(event) {
	var obj = event.srcElement?event.srcElement:event.target;
	
	if(obj.tagName=="INPUT") {
		return false;
	}
	obj = obj.parentNode.firstChild;
	while (!obj.tagName) {
		obj = obj.nextSibling;
	}
	obj=obj.firstChild;
	while (!obj.tagName) {
		obj = obj.nextSibling;
	}
	obj.checked=!obj.checked;
}

function dbClickCheckedData(event) {
	var obj = event.srcElement?event.srcElement:event.target;

	if(obj.tagName=="INPUT") {
		return false;
	}
	obj = obj.parentNode.firstChild;
	while (!obj.tagName) {
		obj = obj.nextSibling;
	}
	obj=obj.firstChild;
	while (!obj.tagName) {
		obj = obj.nextSibling;
	}
	obj.checked=true;
}
function getSelections(checkedName,idName,ids){
	var checkedNum = 0;
	var selecteds = document.getElementsByName(checkedName);
	var id = document.getElementsByName(idName);
	ids.value="";
	for(var i=0; i < selecteds.length;i++) {
		if(selecteds[i].checked==true) {
			checkedNum+=1;
			if(ids.value=="") {
				ids.value=id[i].value;
			} else {
				ids.value+=","+id[i].value;
			}
		}
	}
	return checkedNum;
}

function getSelectionsAndNames(checkedName,idName,nameList,ids){
	var checkedNum = 0;
	var selecteds = document.getElementsByName(checkedName);
	var id = document.getElementsByName(idName);
	var name = document.getElementsByName(nameList);
	ids.value="";
	for(var i=0; i < selecteds.length;i++) {
		if(selecteds[i].checked==true) {
			checkedNum+=1;
			if(ids.value=="") {
				ids.value=id[i].value+","+name[i].value;
			} else {
				ids.value+=","+id[i].value+","+name[i].value;
			}
		}
	}
	return checkedNum;
}
/*
 * 验证输入是否是数字
 * 
 */
onlyNumber=function(event) {
	var key = window.event ? event.keyCode : event.which;
	var keychar = String.fromCharCode(key);
	if (!(key == 8 || key == 0)) {
		reg = /\d/;
		return reg.test(keychar);
	}
	return true;
};

/*
 * 校验是否为字符
 * 
 */
onlyChar=function(event) {
	var key = window.event ? event.keyCode : event.which;
	var keychar = String.fromCharCode(key);
	if (!(key == 8 || key == 0)) {
		reg = /[a-zA-Z]/;
		return reg.test(keychar);
	}
	return true;
};

/*
 * 只能输入数字
 * 
 */
onlyNumberKeyUp=function(event){
	var obj=event.srcElement?event.srcElement:event.target;
	var pattern = /[^\d\.\/]/ig;
	if(pattern.test(obj.value)) {
		var i=getCursortPosition(event);
		obj.value=obj.value.replace(pattern,'');
		setCaretPosition(event,i);
	}
};

/*
 * 只能输入字符
 * 
 */
onlyCharKeyUp=function(event){
	var obj=event.srcElement?event.srcElement:event.target;
	var pattern = /[^a-zA-Z]/ig;
	if(pattern.test(obj.value)) {
		var i=getCursortPosition(event);
		obj.value=obj.value.replace(pattern,'');
		setCaretPosition(event,i);
	}
};

/*
 * 只能输入数字和字母
 * 
 */
onlyNumAndAlphKeyUp=function(event){
	var obj=event.srcElement?event.srcElement:event.target;
	var pattern = /[^\w\.\/]/ig;
	if(pattern.test(obj.value)) {
		var i=getCursortPosition(event);
		obj.value=obj.value.replace(pattern,'');
		setCaretPosition(event,i);
	}
};
/*
 * 用户名和邮箱输入规则，只能输入数字、字母、-、_、.
 * 
 */
accountNoRule=function(event){
	var obj=event.srcElement?event.srcElement:event.target;
	var pattern = /[^\a-zA-Z0-9\@\_\-\.]/g;
	if(pattern.test(obj.value)) {
		var i=getCursortPosition(event);
		obj.value=obj.value.replace(pattern,'');
		setCaretPosition(event,i);
	}
};
/**
 * 用户名邮箱格式校验
 * 
 */
accountNoRuleCheck=function(string){
	var pattern = /[^\a-zA-Z0-9\@\_\-\.]/g;
	if(pattern.test(string)) {
		return false;
	}
	return true;
};
/*
 * 电话输入规则，只能输入数字、-、,
 * 
 */
phoneRule=function(event){
	var obj=event.srcElement?event.srcElement:event.target;
	var pattern = /[^\d\,\-]/g;
	if(pattern.test(obj.value)) {
		var i=getCursortPosition(event);
		obj.value=obj.value.replace(pattern,'');
		setCaretPosition(event,i);
	}
};
/**
 * 电话号码格式校验
 * 
 */
phoneRuleCheck=function(string){
	var pattern = /[^\d\,\-]/g;
	if(pattern.test(string)) {
		return false;
	}
	return true;
};
/*
 * 手机输入规则，只能输入数字、,
 * 
 */
telRule=function(event){
	var obj=event.srcElement?event.srcElement:event.target;
	var pattern = /[^\d\,]/g;
	if(pattern.test(obj.value)) {
		var i=getCursortPosition(event);
		obj.value=obj.value.replace(pattern,'');
		setCaretPosition(event,i);
	}
};
/**
 * 手机号码格式校验
 * 
 */
telRuleCheck=function(string){
	var pattern = /[^\d\,]/g;
	if(pattern.test(string)) {
		return false;
	}
	return true;
};
/*
 * textarea字符长度限制
 * 
 */
isMaxLen = function(event) {
	var obj = event.srcElement?event.srcElement:event.target;
	var target = $("#"+obj.getAttribute("id").replace(".","\\."));
	var maxLength = target.attr("maxlength");
	if (target.val().length > maxLength) {
		target.blur();
		target.val(target.val().substring(0, maxLength));
		target.focus();
	}
};
/*
 * 回车+CTRL换行
 * 
 */
newline = function(event) {
	 if(event.keyCode == 13 && event.ctrlKey){
		 if (document.selection) {
			 var selectText = document.selection.createRange();
			 if(selectText){
				 if(selectText.text.length > 0)
					 selectText.text += "\r\n";
				 else
					 selectText.text = "\r\n";
				 selectText.select();
				 }
		 }
		 else{
			 var obj = event.srcElement?event.srcElement:event.target;
			 obj.value += "\r\n";
			 }
		 }
	 };
/**
 * 字符串字节长度计算（按照GBK编码）
 */
var lenForStrGBK = function(str){ 
	var byteLen=0,len=str.length; 
	if(str){ 
		for(var i=0; i<len; i++){ 
			if(str.charCodeAt(i)>255){ 
				byteLen += 2; 
				} else { 
					byteLen++; 
					} 
			}
		return byteLen;
		} else { 
			return 0; 
			} 
	};
/**
 * 字符串字节长度计算（按照UTF-8编码）
 */
var lenForStr = function(str) { 
	var totalLength = 0;
	var i;
	var charCode;
	for (i = 0; i < str.length; i++) {
		charCode = str.charCodeAt(i);
		if (charCode < 0x007F) {
			totalLength = totalLength + 1;
			} else if ((0x0080 <= charCode) && (charCode <= 0x07FF)) {
				totalLength += 2;
				} else if ((0x0800 <= charCode) && (charCode <= 0xFFFF)) {
					totalLength += 3;
					} else if ((0x10000 <= charCode) && (charCode <= 0x1FFFFF)) {
						totalLength +=4;
						}
		}
	return totalLength;
};
/*******************************************************************************
 * 获取光标位置
 * 
 * @param ctrl
 * @returns {Number}
 */
getCursortPosition=function(event) {// 获取光标位置函数
	var obj = event.srcElement?event.srcElement:event.target;
	var CaretPos = 0;	// IE Support
	if (document.selection) {
		obj.focus ();
		var Sel = document.selection.createRange ();
		Sel.moveStart ('character', -obj.value.length);
		CaretPos = Sel.text.length;
	} else if (obj.selectionStart || obj.selectionStart == '0'){
		// Firefox support
		CaretPos = obj.selectionStart;
	}

	return (CaretPos);
};

/*******************************************************************************
 * 设置光标位置
 * 
 * @param ctrl
 * @returns {Number}
 */
setCaretPosition=function(event, pos){// 设置光标位置函数
	var obj = event.srcElement?event.srcElement:event.target;
	if(obj.setSelectionRange){
		obj.focus();
		obj.setSelectionRange(pos,pos);
	} else if (obj.createTextRange) {
		var range = obj.createTextRange();
		range.collapse(true);
		range.moveEnd('character', pos);
		range.moveStart('character', pos);
		range.select();
	}
};
/**
 * 验证只能为数字和字母的组合
 */
function onlyNumAndAlph(value){
	var re=/^([a-zA-Z0-9]+)$/;
	if(!re.test(value)){
		return false;
	}
	return true;
};

/*
 * 只能输入数字
 * 
 */
onlyFloat=function(value){
	var pattern = /^\d\d+\.\d+\d$/;
	return pattern.test(value);
};
/**
 * form表单值是否改变，提示是否需要保存
 * 
 * @param callBack
 */
formChange=function(callBack, url, data, callback_s, callback_e) {
	var formId = $("#saveFormType").attr("relForm");
	if(formId==undefined){
		callBack(url, data, callback_s, callback_e);
		return;
	}
	
	var isChange = false;
	var inputs = $("#"+formId).find(":input");
	
	inputs.each(function(){
		var type =$(this).attr("type");
		if (type == 'radio' || type == 'checkbox') {
			if ($(this).attr("checked") != $(this).prop("defaultChecked")) {
				isChange = true;
		    	return;
		   }
		} else if(type=="text"||$(this).is("textarea")||type=="hidden") {
			if ($(this).val() != $(this).prop("defaultValue")) {
				isChange = true;
				return;
			}
		} else if($(this).is("select")){
			var v = $(this).val();
			$(this).find("option").each(function(){
				if($(this).prop("defaultSelected")&&$(this).val()!=v){
				     isChange = true;
				     return;
				}
			});
		}
	});
	
	if(isChange) {
		var saveFormChange=function(result){
			if(result){
				callBack(url, data, callback_s, callback_e);
			} else {
				// 直接保存时，会出现两次提示，暂且屏蔽
				// var relClick = $("#saveFormType").attr("relClick");
				// $("#"+relClick).click();
			}
		};
		jConfirm("数据已经修改，确定不需要保存？",null,saveFormChange);
	} else {
		callBack(url, data, callback_s, callback_e);
	}
};
/**
 * 显示遮罩
 */
showLoadPanel = function(){
	if(!$("#loadPanel").is(':visible')) {
		$("#loadPanel").height($(document).height());
		$("#loadPanel").show();
	}
};
hideLoadPanel = function(){
	$("#loadPanel").hide();
};
/**
 * 功能区变化
 */
contentChange=function(content, elementId){
	/**
	 * 判断是否返回的是登录页面，如果是登录页面则替换整个html页面，如果不是只改变function区域的内容
	 */
	var rex = /id=\"loginForm\"/gi;
	if(rex.test(content)){
		// 由于$("").find()方法中，不能识别出包含 body head html等标签的字符串，所以替换掉
		content = content.replace("<body","<temBY");
		content = content.replace("<head","<temHD");
		content = content.replace("<html","<temHL");
		content = content.replace("</body>","</temBY><JS>");
		content = content.replace("</head>","</temHD>");
		content = content.replace("</html>","</JS></temHL>");
		$("body").empty();
		$("head").html($(content).find("temHD").html());
		$("body").html($(content).find("temBY").html());
		// 在页面加载后， 会执行body后的一段js脚本，用after函数给加上
		$("body").after($(content).find("JS").html());
	}else{
		if(elementId == undefined) {
			$("#content").html(content);	
		} else {
			$("#"+elementId).html(content);
		}
		
		hideLoadPanel();
	}
};
function loadJs(url, callback){
    var done = false;
    var script = document.createElement('script');
    script.type = 'text/javascript';
    script.language = 'javascript';
    script.src = url;
    script.onload = script.onreadystatechange = function(){
        if (!done && (!script.readyState || script.readyState == 'loaded' || script.readyState == 'complete')){
            done = true;
            script.onload = script.onreadystatechange = null;
            if (callback){
                callback.call(script);
            }
        }
    };
    document.getElementsByTagName("head")[0].appendChild(script);
};
/**
 * jqueryAjax通用选项
 * 
 */
ajaxOption=function(url,callback_s,callback_e,b_callback){
	var options = {
			   url: url,
			   type: "POST",
			   dataType:'html',
			   beforeSend: function(){
			   		if(b_callback != undefined){
			   			b_callback();
			   		}
			  },
			   success:function(html) {
				   hideLoadPanel();
				   if(callback_s != undefined) {
					   callback_s(html);
				   } else {
					   contentChange(html);   
				   }
			   },
			   error:function(er){
				   hideLoadPanel();
				   if(callback_e != undefined) {
					   callback_e(er.responseText);   
				   } else {
					   contentChange(er.responseText);   
				   }
			   }
	};
	showLoadPanel();
	
	return options;
};
/**
 * jquery ajax 请求
 * 
 */
ajax=function(url,data,callback_s,callback_e){
	
	var callBack = function(url, data, callback_s, callback_e){
		showLoadPanel();
		if(data!=null&&data!=undefined){
			$.ajax({
				   url: url,
				   type: "POST",
				   timeout: 18000,
				   data: data,
				   dataType:'html',
				   success:function(html) {
					   if(callback_s!=null&&callback_s!=undefined) {
						  callback_s(html);
						  hideLoadPanel();
					   } else {
						   contentChange(html);   
					   }
				   },
				   error:function(er){
					   if(er.statusText=='timeout') {
						   contentChange("<red>连接服务器超时！</red>");
					   } else {
						   if(callback_e!=null&&callback_e!=undefined) {
							   callback_e(er);
							   hideLoadPanel();
						   } else {
							   contentChange(er.responseText);   
						   }
					   }
				   }
				});
		}else{
			$.ajax({
				   url: url,
				   type: "POST",
				   timeout: 18000,
				   dataType:'html',
				   success:function(html) {
					   if(callback_s!=null&&callback_s!=undefined) {
						   callback_s(html);
						   hideLoadPanel();
					   } else {
						   contentChange(html);   
					   }
				   },
				   error:function(er){
					   if(er.statusText=='timeout') {
						   contentChange("<red>连接服务器超时！</red>");
					   } else {
						   if(callback_e!=null&&callback_e!=undefined) {
							   callback_e(er);
							   hideLoadPanel();
						   } else {
							   contentChange(er.responseText);   
						   }
					   }
				   }
				});
		}
	};
	
	//避免调用ueditor时造成的输入框无法输入的bug
	$(":button:first").focus();
	
	// 有回调函数时，不做数据校验
	if(callback_s==null) {
		formChange(callBack, url, data, callback_s, callback_e);
	} else {
		callBack(url, data, callback_s, callback_e);
	}
};