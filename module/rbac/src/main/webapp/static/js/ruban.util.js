/**
 * 去掉空格
 * 
 * @returns
 */
String.prototype.trim = function() {
	return this.replace(/^\s+/, '').replace(/\s+$/, '');
};
/**
 * 去掉左空格
 * 
 * @returns
 */
String.prototype.ltrim = function() {
	return this.replace(/^\s+/, '');
};
/**
 * 去掉右空格
 * 
 * @returns
 */
String.prototype.rtrim = function() {
	return this.replace(/\s+$/, '');
};
/**
 * 空值判断
 */
isNull = function(value){
	if (value == '') {
		return true;
	}
	return false;
};
/**
 * 非空值判断
 */
isNotNull = function(value){
	return !isNull(value);
};
/**
 * 是否为邮箱
 * 
 * @param email
 * @returns
 */
isEmail = function(email) {
	var reg = /^[a-zA-Z\d]+@[a-zA-Z\d]+([\w\d\-]*\.[\w\d\-])*[a-zA-Z]{2,8}$/;
	return reg.test(email);
};
/**
 * 是否为数字
 * 
 * @param d
 * @returns
 */
isNumber = function(d) {
	reg = /^\d+$/;
	return reg.test(d);
};
/**
 * 是否为字符
 * 
 * @param w
 * @returns
 */
isAlpha = function(w) {
	reg = /^[a-zA-Z]+$/i;

	return reg.test(w);
};
/**
 * 是否为浮点数
 * 
 * @param f
 * @returns
 */
isFloat = function(f) {
	reg = /^\d+(\.\d)?\d*$/;

	return reg.test(f);
};
/**
 * 是否字母或数字
 * 
 * @param an
 * @returns
 */
isAlphaNumber = function(an) {
	reg = /^[a-zA-Z0-9]+$/;

	return reg.test(an);
};
/**
 * 是否为汉字
 * 
 * @param ch
 */
isChinese = function(ch) {
	reg = /^[\u4e00-\u9fa5]+$/;

	return reg.test(ch);
};
/**
 * 手机号校验
 * 
 * @param tel
 */
isTel = function(tel) {
	reg = /^(0)?(13|14|15|17|18)\d{9}$/;

	return reg.test(tel);
};
/**
 * 校验文件名是否为图片格式
 * 
 * @param fileName
 */
ck4Img = function(fileName) {
	reg = /\.(png|jpg|gif)$/ig;

	return reg.test(fileName);
};
/**
 * 校验文件名是否为excel
 * 
 * @param fileName
 */
ck4Xls = function(fileName) {
	reg = /\.(xls|XLS|xlsx|XLSX)$/ig;

	return reg.test(fileName);
};
/**
 * 随机数
 * 
 * @param len
 * @returns {Number}
 */
randomInt = function(len) {
	var result = "";
	if (len > 16) {
		result += randomInt(len - 16);
		len = 16;
	}

	if (len >= 1) {
		var base = Math.pow(10, len);
		result += Math.round(Math.random() * base);
	}
	return result;
};
/**
 * 生成字母随机数
 * 
 * @param len
 * @returns {String}
 */
randomAlpha = function(len) {

	// 65 - 90 A-Z
	// 97 - 122 a-z
	var rand = randomInt(len).toString();
	rand += randomInt(len).toString();

	var ret = "";
	for (var i = 0; i < rand.length; i += 2) {
		var split = parseInt(rand.substring(i, i + 2));
		split %= 26;
		split += 97;
		var char = String.fromCharCode(Math.round(split));
		var flag = randomInt(1);

		if (flag % 3 == 0) {
			char = char.toUpperCase();
		}
		ret += char;
	}

	return ret;
};
/**
 * 生成字母数字随机数
 * 
 * @param len
 * @returns {String}
 */
randomAlphaInt = function(len) {
	randInt = String(randomInt(len));
	randAlpha = randomAlpha(len);

	var ret = "";
	for (var i = 0; i < len; i++) {
		var flag = randomInt(1);
		if (flag % 2 == 0) {
			ret += randInt.substring(i, i + 1);
		} else {
			ret += randAlpha.substring(i, i + 1);
		}
	}

	return ret;
};
/**
 * 按照GBK编码计算字符串的字节长度
 * 
 * @param str
 * @returns {Number}
 */
lenByte4GBK = function(str) {
	var byteLen = 0, len = str.length;
	if (str) {
		for (var i = 0; i < len; i++) {
			if (str.charCodeAt(i) > 255) {
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
 * 按UTF-8计算字符串的字节长度
 * 
 * @param str
 * @returns {Number}
 */
lenByte4UTF8 = function(str) {
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
			totalLength += 4;
		}
	}
	return totalLength;
};
/**
 * 获取光标位置
 * 
 * @param event
 * @returns {Number}
 */
getCursortPosition = function(event) {// 获取光标位置函数
	var obj = event.srcElement ? event.srcElement : event.target;
	var CaretPos = 0; // IE Support
	if (document.selection) {
		obj.focus();
		var Sel = document.selection.createRange();
		Sel.moveStart('character', -obj.value.length);
		CaretPos = Sel.text.length;
	} else if (obj.selectionStart || obj.selectionStart == '0') {
		// Firefox support
		CaretPos = obj.selectionStart;
	}

	return (CaretPos);
};
/**
 * 设置光标位置
 * 
 * @param event
 * @returns {Number}
 */
setCaretPosition = function(event, pos) {// 设置光标位置函数
	var obj = event.srcElement ? event.srcElement : event.target;
	if (obj.setSelectionRange) {
		obj.focus();
		obj.setSelectionRange(pos, pos);
	} else if (obj.createTextRange) {
		var range = obj.createTextRange();
		range.collapse(true);
		range.moveEnd('character', pos);
		range.moveStart('character', pos);
		range.select();
	}
};
/**
 * 加载JS，并执行回调
 * 
 * @param url
 * @param callback
 */
loadJs = function(url, callback) {
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