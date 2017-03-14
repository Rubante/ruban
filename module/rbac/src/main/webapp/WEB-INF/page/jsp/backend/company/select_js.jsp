<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>

<script type="text/javascript">
(function(){
	if(!window['companyDownTree']){
		window['companyDownTree'] = {};
	}
	
	var companyDownTree = window['companyDownTree'];
	
	// 隐藏所有父级选择面板
	$(document).click(function(){
		$("[id$='companyDownTreeDiv']").each(function(){
			if($(this).is(":visible")){
				$(this).hide();	
			}
		});
	});
	
	// 按钮点击选择上级机构
	companyDownTree.clickInput = function(event){
		var id = $(event.target).attr("rel");
		$('#'+id).click();
		event.preventDefault();
		event.stopPropagation();
	};
	
	// 选择上级机构
	companyDownTree.select = function(event) {

		// 设置字体颜色
		function setFontCss(treeId, treeNode) {
			if(treeNode.isParent) {
				return {
					color : "red"
				};
			} else {
				return {
					color : "blue"
				};
			}
		};

		var targetId = $(event.target).attr("rel");
		
		// 点击设置所选
		function zTreeOnClick(event, treeId, treeNode) {
			$("#" + targetId + "Id").val(treeNode.id);
			$("#" + targetId + "Name").val(treeNode.name);
			$("#" + targetId + "DownTreeDiv").hide();
			$("#" + targetId + "Name").change();
		};

		
		$("#" + "companyDownTreeDiv").toggle();

		var parentSetting = {
			check : {
				enable : false
			},
			callback : {
				onClick : zTreeOnClick
			},
			data : {
				simpleData : {
					enable : true
				}
			},
			view : {
				fontCss : setFontCss
			}
		};

		var zNodes = ${companyTree};

		$.fn.zTree.init($("#" + "companyDownTree"), parentSetting, zNodes);
		
		event.preventDefault();
		event.stopPropagation();
	};
})();
</script>