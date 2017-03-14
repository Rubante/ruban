<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script type="text/javascript">
var company = {};

company.search=function(){
	var options = ajaxOption("../company/search");
	$('#searchForm').ajaxSubmit(options);
}
company.addForm=function(){
	var options = ajaxOption("../company/addForm",function(html){
		$("#addModal").html(html);
	});
	
	$("#companyAddForm").ajaxSubmit(options);
};
company.add=function(){
	if($("#addModal").length == 0 ){
		$("#content").append("<div id='addModal'></div>");
	}
	ajax("../company/add",null,function(html){
		$("#addModal").html(html);
	});
};
</script>