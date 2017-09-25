layer && layer.config({
    skin:'layer-ext-espresso',
    extend:'espresso/style.css'
});

/**
 * 校验上传图片格式
 * 
 */
imgCheck=function(Sting){
	var index = Sting.lastIndexOf(".");
	var ext = Sting.substring(index + 1, Sting.length);
	if (index < 0){
		return false;
	} else if (ext != "png" && ext != "PNG" && ext != "jpg" && ext!= "JPG"){
		return false;
	}
	return true;
};
/**
 * excel文件前端校验
 * 
 */
excelCheck=function(Sting){
	var index = Sting.lastIndexOf(".");
	var ext = Sting.substring(index + 1, Sting.length);
	if (index < 0){
		return false;
	} else if (ext != "xls" && ext != "XLS" && ext != "xlsx" && ext != "XLSX"){
		return false;
	}
	return true;
};