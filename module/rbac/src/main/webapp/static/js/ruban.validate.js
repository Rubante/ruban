/**
 * 控制字符
 * 
 * @param keyCode
 * @returns {Boolean}
 */
ctrlKeyCode = function(key) {
	return key == 8 || key == 9 || key == 16 || key == 17 || key >= 33
			&& key <= 47;
};
/**
 * 只能输入数字
 * 
 * @param event
 * @returns
 */
inputNumber = function(event) {
	var key = event.keyCode ? event.keyCode : event.which;
	var keyCode = String.fromCharCode(key);
	if (!ctrlKeyCode(key)) {
		if (key == 229) {
			return true;
		} else {
			// 黏贴
			if (event.ctrlKey == 1 && keyCode == 'V') {
				return true;
			}
			reg = /\d/;
			return reg.test(keyCode) && event.shiftKey == 0;
		}
	}

	return ctrlKeyCode(key);
};
/**
 * 清理中文输入法的非数字输入项
 * 
 * @param event
 */
inputNumber2 = function(event) {

	var key = event.keyCode ? event.keyCode : event.which;
	var keyCode = String.fromCharCode(key);
	// 非控制字符，则进行
	if (!ctrlKeyCode(key) && (key == 229 || event.ctrlKey == 1)) {
		var val = $(event.target).val();

		val1 = val.match(/\d+/g);
		
		if (!val1) {
			$(event.target).val('');
		} else if (val != val1) {
			var cPos = getCursortPosition(event);
			$(event.target).val(val1.join().replace(/\,/g,""));
			setCaretPosition(event,cPos - 1);
		}
	}
};
/**
 * 只能输入字符
 * 
 * @param event
 * @returns
 */
inputAlpha = function(event) {
	var key = event.keyCode ? event.keyCode : event.which;
	var keyCode = String.fromCharCode(key);
	if (!ctrlKeyCode(key)) {
		if (key == 229) {
			return true;
		} else {
			reg = /[a-zA-Z]/;
			return reg.test(keyCode);
		}

	}

	return ctrlKeyCode(key);
};
/**
 * 清理中文输入法输入的字符
 * 
 * @param event
 */
inputAlpha2 = function(event) {
	var key = event.keyCode ? event.keyCode : event.which;
	var keyCode = String.fromCharCode(key);
	if (!ctrlKeyCode(key) && (key == 229 || event.ctrlKey == 1)) {
		var val = $(event.target).val();

		val1 = val.match(/[a-zA-Z]+/g);
		if (!val1) {
			$(event.target).val('');
		} else if (val != val1) {
			var cPos = getCursortPosition(event);
			$(event.target).val(val1.join().replace(/\,/g,""));
			setCaretPosition(event,cPos - 1);
		}
	}
};
/**
 * 只能输入字符
 * 
 * @param event
 * @returns
 */
inputChar = function(event) {
	return inputNumber(event) || inputAlpha(event);
};
/**
 * 清理中文输入法输入的字符
 * 
 * @param event
 */
inputChar2 = function(event) {
	var key = event.keyCode ? event.keyCode : event.which;
	var keyCode = String.fromCharCode(key);
	if (!ctrlKeyCode(key) && (key == 229 || event.ctrlKey == 1)) {
		var val = $(event.target).val();

		val1 = val.match(/[a-zA-Z\d]+/);
		if (!val1) {
			$(event.target).val('');
		} else if (val != val1) {
			var cPos = getCursortPosition(event);
			$(event.target).val(val1.join().replace(/\,/g,""));
			setCaretPosition(event,cPos - 1);
		}
	}
};
/**
 * 显示错误信息
 * 
 * @param target
 * @param msg
 * @returns {String}
 */
hitError = function(target, msg) {
	var rand = randomInt(10);
	var span = "<span id='" + rand + "_error' class='ruban-error'>" + msg
			+ "</span>";
	$(target).attr("errorId", rand);
	$(target).after(span);
};
/**
 * 校验输入为数字
 * 
 * @param event
 */
ckNumber = function(event) {
	var obj = event.srcElement ? event.srcElement : event.target;
	if (!isNumber(obj.value)) {
		hitError(event.target, "只能输入数字");
	}
};
/**
 * 校验输入字符
 * 
 * @param event
 */
ckAlpha = function(event) {
	var obj = event.srcElement ? event.srcElement : event.target;
	if (!isAlpha(obj.value)) {
		hitError(event.target, "只能输入字符");
	}
};
/**
 * 校验输入浮点数
 * 
 * @param event
 */
ckFloat = function(event) {
	var obj = event.srcElement ? event.srcElement : event.target;
	if (!isFloat(obj.value)) {
		hitError(event.target, "只能输入数字（可以含有小数）");
	}
};
/**
 * 校验输入字符，浮点数
 * 
 * @param event
 */
ckAlphaFloat = function(event) {
	var obj = event.srcElement ? event.srcElement : event.target;
	if (!(isAlpha(obj.value) || isFloat(obj.value))) {
		hitError(event.target, "只能输入字符，数字（可以含有小数）");
	}
};
/**
 * 校验输入手机号
 * 
 * @param event
 */
ckTel = function(event) {
	var obj = event.srcElement ? event.srcElement : event.target;
	if (!isTel(obj.value)) {
		hitError(event.target, "只能输入手机号");
	}
};
/**
 * 校验输入邮箱
 * 
 * @param event
 */
ckEmail = function(event) {
	var obj = event.srcElement ? event.srcElement : event.target;
	if (!isEmail(obj.value)) {
		hitError(event.target, "只能输入邮箱");
	}
};
/**
 * 最小长度校验
 * 
 * @param event
 * @param length
 */
ckMinLength = function(event, length) {
	var obj = event.srcElement ? event.srcElement : event.target;
	if (obj.value.length < length) {
		hitError(event.target, "最小长度为：" + length);
	}
};
/**
 * textarea字符长度限制
 * 
 * @param event
 */
textAreaMaxLen = function(event) {
	var obj = event.srcElement ? event.srcElement : event.target;
	var maxLength = $(event.target).attr("maxlength");
	if ($(event.target).val().length > maxLength) {
		hitError(event.target, "最大长度为：" + maxLength);
	}
};
/**
 * 绑定校验
 */
$(document).ready(function() {
	$("input[valid]").each(function(event) {
		var v = $(this).attr("valid");
		if (v == "number" || v == "alpha" || v == "char") {
			$(this).addClass("ime-mode");
		}
		var suffix = v.substring(0, 1).toUpperCase() + v.substring(1);
		$(this).keydown(function(event) {
			var fn = "input" + suffix;
			if (window[fn]) {
				return window[fn](event);
			}
		});

		$(this).keyup(function(event) {
			var fn = "input" + suffix + "2";
			if (window[fn]) {
				window[fn](event);
			}
		});
	});
	
	// 校验格式示例： check='tel,minLength=10'
	$("input[check]").each(function(event) {
		$(this).change(function(event) {
			var check = $(this).attr("check");
			var cks = check.split(",");
			for (i in cks) {
				var args = cks[i].split("=");
				v = args[0].trim();
				var fn = "ck" + v.substring(0, 1).toUpperCase() + v.substring(1);
				if (window[fn]) {
					if (cks[i].indexOf("=") >= 0) {
						window[fn](event, parseInt(args[1].trim()));
					} else {
						return window[fn](event);
					}
				}
			}
		});

		$(this).keydown(function(event) {
			if($(this).attr("errorId")) {
				$("#" + $(this).attr("errorId") + "_error").remove();
				$(this).change();
			}
		});
	});
	
	$("textarea").change(function(event){
		textAreaMaxLen(event);
	});
});