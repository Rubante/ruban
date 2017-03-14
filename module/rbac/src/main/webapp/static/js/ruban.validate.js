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
			$(event.target).val(val1.join().replace(/\,/g, ""));
			setCaretPosition(event, cPos - 1);
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
			$(event.target).val(val1.join().replace(/\,/g, ""));
			setCaretPosition(event, cPos - 1);
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
			$(event.target).val(val1.join().replace(/\,/g, ""));
			setCaretPosition(event, cPos - 1);
		}
	}
};
/**
 * 显示错误信息
 * 
 * @param target
 * @param msg
 */
hitError = function(target, msg) {
	
	if (target.attr("errorId")) {
		$("#" + target.attr("errorId") + "_error").remove();
	}
	
	var rand = randomInt(10);
	target.attr("errorId", rand);

	if(target[0].type == "select-one") {
		var span = "<span id='" + rand + "_error' class='ruban-error-select'>" + msg + "</span>";
		target.after(span);	
	} else {
		var width = target.width();
		var pWidth = target.parent().width();
		if(pWidth/width>2){
			var span = "<span id='" + rand + "_error' class='ruban-error-space'>" + msg + "</span>";
			target.after(span);
		} else {
			var span = "<span id='" + rand + "_error' class='ruban-error' style='margin-right:" + (pWidth - width - 13) + "px'>" + msg + "</span>";
			target.parent().children(":last-child").after(span);
		}
	}
	
};
/**
 * 校验输入为数字
 * 
 * @param event
 */
ckNumber = function(target) {
	if (isNotNull(target.val()) && !isNumber(target.val())) {
		hitError(target, "只能输入数字");
		return 1;
	}
	return 0;
};
/**
 * 校验输入字符
 * 
 * @param event
 */
ckAlpha = function(target) {
	if (isNotNull(target.val()) && !isAlpha(target.val())) {
		hitError(target, "只能输入字符");
		return 1;
	}
	return 0;
};
/**
 * 校验输入浮点数
 * 
 * @param event
 */
ckFloat = function(target) {
	if (isNotNull(target.val()) && !isFloat(target.val())) {
		hitError(target, "只能输入数字（可以含有小数）");
		return 1;
	}
	return 0;
};
/**
 * 校验输入字符，浮点数
 * 
 * @param event
 */
ckAlphaFloat = function(target) {
	if (isNotNull(target.val()) && !(isAlpha(target.val()) || isFloat(target.val()))) {
		hitError(target, "只能输入字符，数字（可以含有小数）");
		return 1;
	}
	return 0;
};
/**
 * 校验输入手机号
 * 
 * @param event
 */
ckTel = function(target) {
	if (isNotNull(target.val()) && !isTel(target.val())) {
		hitError(target, "只能输入手机号");
		return 1;
	}
	return 0;
};
/**
 * 校验输入邮箱
 * 
 * @param event
 */
ckEmail = function(target) {
	if (isNotNull(target.val()) && !isEmail(target.val())) {
		hitError(target, "只能输入邮箱");
		return 1;
	}
	return 0;
};
/**
 * 最小长度校验
 * 
 * @param event
 * @param length
 */
ckMinLength = function(target, length) {
	if (isNotNull(target.val()) && target.val().length < length) {
		hitError(target, "最小长度为：" + length + "个字符");
		return 1;
	}
	return 0;
};
/**
 * 长度校验
 * 
 * @param event
 * @param length
 */
ckLength = function(target, length) {
	if (isNotNull(target.val()) && target.val().length != length) {
		hitError(target, "长度必须为：" + length + "个字符");
		return 1;
	}
	return 0;
};
/**
 * 非空校验
 * 
 * @param event
 * @param length
 */
ckNotNull = function(target, length) {
	if (isNull(target.val())) {
		hitError(target, "必须填写");
		return 1;
	}
	return 0;
};
/**
 * textarea字符长度限制
 * 
 * @param event
 */
textAreaMaxLen = function(target) {
	var maxLength = target.attr("maxlength");
	if (target.val().length > maxLength) {
		hitError(target, "最大长度为：" + maxLength);
		return 1;
	}
	return 0;
};
/**
 * 绑定校验规则
 */
bindValidate = function(target) {
	target.find("input[valid]").each(function(event) {
		var v = $(this).attr("valid");
		if (v == "number" || v == "alpha" || v == "char") {
			$(this).addClass("ime-mode");
		}
		var suffix = v.substring(0, 1).toUpperCase() + v.substring(1);
		$(this).keydown(function(event) {
			var fn = "input" + suffix;
			if (window[fn]) {
				return window[fn]($(event.target));
			}
		});

		$(this).keyup(function(event) {
			var fn = "input" + suffix + "2";
			if (window[fn]) {
				window[fn]($(event.target));
			}
		});
	});
	
	var checkFunction = function(event) {
		var target = $(event.target);
		var check = target.attr("check");
		var cks = check.split(",");
		for (i in cks) {
			var args = cks[i].split("=");
			v = args[0].trim();
			var fn = "ck" + v.substring(0, 1).toUpperCase() + v.substring(1);
			if (window[fn]) {
				if (cks[i].indexOf("=") >= 0) {
					window[fn](target, parseInt(args[1].trim()));
				} else {
					window[fn](target);
				}
			}
		}
	};
	
	// 校验格式示例： check='tel,minLength=10'
	target.find("[check]").each(
		function(event) {
			$(this).change(function(event){
				if ($(this).attr("errorId")) {
					$("#" + $(this).attr("errorId") + "_error").remove();
				}
				checkFunction(event);
			});
			
			$(this).keydown(function(event) {
				if ($(this).attr("errorId")) {
					$("#" + $(this).attr("errorId") + "_error").remove();
				}
				checkFunction(event);
			});
			
			$(this).blur(function(event) {
				if ($(this).attr("errorId")) {
					$("#" + $(this).attr("errorId") + "_error").remove();
				}
				checkFunction(event);
			});
	});

	target.find("textarea").change(function(event) {
		textAreaMaxLen($(event.target));
	});
};
/**
 * 手动校验
 */
validate = function(target,callback_s) {
	
	// 校验格式示例： check='tel,minLength=10'
	target.find("[check]").each(function() {
		var check = $(this).attr("check");
		var cks = check.split(",");
		for (i in cks) {
			var args = cks[i].split("=");
			v = args[0].trim();
			var fn = "ck" + v.substring(0, 1).toUpperCase() + v.substring(1);
			if (window[fn]) {
				if (cks[i].indexOf("=") >= 0) {
					window[fn]($(this), parseInt(args[1].trim()));
				} else {
					window[fn]($(this));
				}
			}
		}
	});
	
	target.find("textarea").each(function(event) {
		textAreaMaxLen($(this));
	});
	
	// 错误个数
	var nums = target.find("[id$='_error']").length;
	if(nums > 0) {
		inputError();
	} else if(callback_s != undefined) {
		callback_s();
	}
	
	return nums;
};
/**
 * 绑定校验
 */
$(document).ready(function() {
	bindValidate($(document));
});