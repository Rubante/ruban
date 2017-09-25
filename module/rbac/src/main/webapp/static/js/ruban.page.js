/**
 * 根据名称批量选中checkBox
 * 
 * @param event
 * @param name
 * @returns
 */
function checkedByName(event, name) {
	var obj = event.srcElement ? event.srcElement : event.target;
	var nodeList = document.getElementsByName(name);
	if (nodeList.length == 0) {
		layer.alert("没有可选择的数据！",{
			  zIndex: layer.zIndex,
			  success: function(layero){
			    layer.setTop(layero);
			  }
		});
		obj.checked = false;
		return false;
	} else {
		for (var i = 0; i < nodeList.length; i++) {
			// checkBox可用时选中
			if (!nodeList[i].disabled) {
				nodeList[i].checked = obj.checked;
			}
		}
	}
}
/**
 * 获取选中的checkBox及对应id值
 * 
 * @param checkedName
 * @param idName
 * @returns
 */
function getSelections(checkedName, idName) {
	
	var selecteds = document.getElementsByName(checkedName);
	var ids = document.getElementsByName(idName);

	var idArr = new Array;
	for (var i = 0; i < selecteds.length; i++) {
		if (selecteds[i].checked == true) {
			idArr.push(ids[i].value);
		}
	}
	
	return idArr.join(",");
}
/**
 * 获取选中的radio
 * 
 * @param radioName
 * @returns
 */
function getSelectedRadio(radioName) {
	var selecteds = document.getElementsByName(radioName);

	for (var i = 0; i < selecteds.length; i++) {
		if (selecteds[i].checked == true) {
			return selecteds[i];
		}
	}
	return null;
};
// 选择日期
selectDate = function(elem){
    $.jeDate(elem,{
    	skinCell:"jedategreen",
        insTrigger:false,
        zIndex: 200010000,
        format: 'YYYY-MM-DD'
    });
};
//选择日期
selectTime = function(elem){
    $.jeDate(elem,{
    	skinCell:"jedategreen",
    	insTrigger:false,
        zIndex: 200010000,
        format: 'YYYY-MM-DD hh:mm:ss'
    });
};
getLayerConfirmOption = function(){
	var option = {
		btn: ['确定','关闭'],
		zIndex: layer.zIndex,
		success: function(layero){
			layer.setTop(layero);
		}
	};
	
	return option;
}
function loadJs(url, callback) {
	var done = false;
	var script = document.createElement('script');
	script.type = 'text/javascript';
	script.language = 'javascript';
	script.src = url;
	script.onload = script.onreadystatechange = function() {
		if (!done
				&& (!script.readyState || script.readyState == 'loaded' || script.readyState == 'complete')) {
			done = true;
			script.onload = script.onreadystatechange = null;
			if (callback) {
				callback.call(script);
			}
		}
	};
	document.getElementsByTagName("head")[0].appendChild(script);
};
/*
 * 回车+CTRL换行
 * 
 */
newline = function(event) {
	if (event.keyCode == 13 && event.ctrlKey) {
		if (document.selection) {
			var selectText = document.selection.createRange();
			if (selectText) {
				if (selectText.text.length > 0)
					selectText.text += "\r\n";
				else
					selectText.text = "\r\n";
				selectText.select();
			}
		} else {
			var obj = event.srcElement ? event.srcElement : event.target;
			obj.value += "\r\n";
		}
	}
};
/**
 * form表单值是否改变，提示是否需要保存
 * 
 * @param callBack
 */
formChange = function(callBack, url, data, callback_s, callback_e) {
	var formId = $("#saveFormType").attr("relForm");
	if (formId == undefined) {
		callBack(url, data, callback_s, callback_e);
		return;
	}

	var isChange = false;
	var inputs = $("#" + formId).find(":input");

	// 数据变化校验
	var compare = function() {
		var type = $(this).attr("type");
		if (type == 'radio' || type == 'checkbox') {
			if ($(this).attr("checked") != $(this).prop("defaultChecked")) {
				isChange = true;
				return;
			}
		} else if (type == "text" || $(this).is("textarea") || type == "hidden") {
			if ($(this).val() != $(this).prop("defaultValue")) {
				isChange = true;
				return;
			}
		} else if ($(this).is("select")) {
			var v = $(this).val();
			$(this).find("option").each(function() {
				if ($(this).prop("defaultSelected") && $(this).val() != v) {
					isChange = true;
					return;
				}
			});
		}
	};

	inputs.each(compare);

	if (isChange) {
		layer.confirm("数据已经修改，确定不需要保存？", {
		    btn: ['确定','取消'], //按钮
		    shade: false //不显示遮罩
		}, function(){
		    layer.close();
		}, function(){
			layer.close();
		});
	} else {
		callBack(url, data, callback_s, callback_e);
	}
};
/**
 * 显示遮罩
 */
showLoadPanel = function() {
	if (!$("#loadPanel").is(':visible')) {
		$("#loadPanel").height($(document).height());
		$("#loadPanel").show();
		
	}
};
/**
 * 隐藏遮罩
 */
hideLoadPanel = function() {
	$("#loadPanel").hide();
};
/**
 * 结果提示
 */
msgAlert = function(result, callback_s) {

	if (result.flag == 1) {
		layer.alert(result.msg, {
			closeBtn : 0,
			zIndex: layer.zIndex,
			success: function(layero){
				layer.setTop(layero);
			}
		}, function(index) {
			layer.close(index);
			if(callback_s) {
				callback_s(result);
			}
		});
	} else {
		errorBind(result);
	}
};
/**
 * 将后台结果绑定到前端
 */
errorBind = function(data) {

	if (data.result) {
		var errors = data.result.error;

		if (Array.isArray(errors)) {
			for (index in errors) {
				var objectName = errors[index].objectName;
				var field = errors[index].field;
				var msg = errors[index].defaultMessage;
				var target = $("#" + objectName).find("[name='" + field + "']");

				// 存在该元素，则在该元素处提示，不存在，则弹出框
				if (target.length > 0) {
					hitError(target, msg);
				} else {
					layer.alert(msg, {
						closeBtn : 0,
						zIndex: layer.zIndex,
						success: function(layero){
							layer.setTop(layero);
						}
					});
				}
			}
		} else {
			layer.alert(errors, {
				closeBtn : 0,
				zIndex: layer.zIndex,
				success: function(layero){
					layer.setTop(layero);
				}
			},function(index){
				layer.close(index);
			});
		}
	} else {
		var msg = "";
		if(data.msg) {
			msg = data.msg;
		} else {
			msg = data;
		}
		layer.alert(msg, {
			closeBtn : 0,
			zIndex: layer.zIndex,
			success: function(layero){
				layer.setTop(layero);
			}
		},function(index){
			layer.close(index);
		});
	}
};
/**
 * 页面输入错误
 */
inputError = function() {
	layer.alert("输入有错误，请修改后再提交！", {
		closeBtn : 0,
		zIndex: layer.zIndex,
		success: function(layero){
			layer.setTop(layero);
		}
	});
};
/**
 * 获取模板
 */
getTemplate = function(url){
	var template = "";
	$.ajax({
		type : "get",
		url : url,
		dataType : "text",
		async : false,
		success : function(data) {
			template = data;
		},
		error : function(data){
			console.log(JSON.stringify(data));
		}
	});
	
	return template;
};
/**
 * 根据document的id获取模板，渲染
 */
renderHtmlById = function(template, data) {

	var html = "";
	if (data) {
		html = Mustache.to_html($("#" + template).html(), data);
	} else {
		html = Mustache.to_html($("#" + template).html(), {});
	}

	return html;
};
/**
 * 根据template直接渲染
 */
renderHtmlByTemplate = function(template, data) {

	var html = "";
	if (data) {
		html = Mustache.to_html(template, data);
	} else {
		html = Mustache.to_html(template, {});
	}

	return html;
};
/**
 * jqueryAjax通用选项
 * 
 */
ajaxOption = function(url, callback_s, callback_e, b_callback, dataType) {
	var options = {
		url : url,
		type : "POST",
		beforeSend : function() {
			showLoadPanel();
			if (b_callback != undefined) {
				b_callback();
			}
		},
		success : function(data) {
			hideLoadPanel();
			if (callback_s != undefined) {
				callback_s(data);
			} else {
				msgAlert(data, function() {
					var iframe = $(window.parent.document).find("iframe");
					var location = iframe[0].src;
					iframe.attr("src", location);
				});
			}
		},
		error : function(er) {
			hideLoadPanel();
			if (callback_e != undefined) {
				callback_e(er.responseText);
			} else {
				if(er.responseText) {
					errorBind(er.responseText);
				} else {
					errorBind({msg:er.statusText});
				}
				hideLoadPanel();
			}
		}
	};

	if(dataType != null) {
		options.dataType = dataType;
	}
	
	return options;
};
ajaxOptionJson = function(url, callback_s, callback_e, b_callback) {
	return ajaxOption(url, callback_s, callback_e, b_callback, "json");
};
ajaxOptionHtml = function(url, callback_s, callback_e, b_callback) {
	return ajaxOption(url, callback_s, callback_e, b_callback, "html");
};
ajaxOptionText = function(url, callback_s, callback_e, b_callback) {
	return ajaxOption(url, callback_s, callback_e, b_callback, "text");
};
ajaxOptionHybrid = function(url, callback_s, callback_e, b_callback) {
	return ajaxOption(url, callback_s, callback_e, b_callback, null);
};
/**
 * 添加数据
 */
addAjaxData = function(ajaxOption,data){
	ajaxOption.data = data;
};
/**
 * ajax调用默认结果
 */
ajaxCallback = function(data) {
	layer.alert(data.msg,{
		zIndex: layer.zIndex,
		success: function(layero){
			layer.setTop(layero);
		}
	});
};
/**
 * 请求文本
 */
ajaxText = function(url, data, callback_s, callback_e) {
	ajax(url, "text", data, callback_s, callback_e);
};
/**
 * 请求json
 */
ajaxJson = function(url, data, callback_s, callback_e) {
	ajax(url, "json", data, callback_s, callback_e);
};
/**
 * 请求html
 */
ajaxHtml = function(url, data, callback_s, callback_e) {
	ajax(url, "html", data, callback_s, callback_e);
};
/**
 * 混合请求
 */
ajaxHybrid = function(url, data, callback_s, callback_e) {
	ajax(url, null, data, callback_s, callback_e);
};
/**
 * jquery ajax 请求
 * 
 */
ajax = function(url, dataType, data, callback_s, callback_e) {

	var callBack = function(url, data, callback_s, callback_e) {
		showLoadPanel();
		if (data) {
			var options = {
					url : url,
					type : "POST",
					timeout : 6000,
					data : data,
					async : false,
					success : function(data) {
						if (callback_s != null && callback_s != undefined) {
							callback_s(data);
							hideLoadPanel();
						} else {
							ajaxCallback(data);
						}
					},
					error : function(er) {
						if (er.statusText == 'timeout') {
							ajaxCallback("<red>连接服务器超时！</red>");
						} else {
							if (callback_e != null && callback_e != undefined) {
								callback_e(er);
								hideLoadPanel();
							} else {
								if(er.responseText) {
									console.log(JSON.stringify(er));
									ajaxCallback(er.responseText);
								} else {
									ajaxCallback({msg:er.statusText});
								}
								hideLoadPanel();
							}
						}
					}
				};
			if(dataType) {
				options.dataType = dataType;
			}
			$.ajax(options);
		} else {
			var options = {
					url : url,
					type : "POST",
					timeout : 6000,
	
					success : function(data) {
						if (callback_s != null && callback_s != undefined) {
							callback_s(data);
						} else {
							msgAlert(data);
						}
					},
					error : function(er) {
						if (er.statusText == 'timeout') {
							msgAlert("<red>连接服务器超时！</red>");
						} else {
							if (callback_e != null && callback_e != undefined) {
								callback_e(er);
							} else {
								errorBind(er);
							}
						}
					}
				};
			if(dataType) {
				options.dataType = dataType;
			}
			$.ajax(options);
		}
	};

	// 避免调用ueditor时造成的输入框无法输入的bug
	$(":button:first").focus();

	// 有回调函数时，不做数据校验
	if (callback_s == null) {
		formChange(callBack, url, data, callback_s, callback_e);
	} else {
		callBack(url, data, callback_s, callback_e);
	}
};