<script type="text/javascript">
(function(){
	if(!window['company']){
		window['company'] = {};
	}
	
	var company = window['company'];
	
	// 隐藏所有父级选择面板
	$(document).click(function(){
		$("[id$='_parentTreeDiv']").each(function(){
			if($(this).is(":visible")){
				$(this).hide();	
			}
		});
	});
	
	// 按钮点击选择上级机构
	company.clickParentInput = function(event){
		var id = $(event.target).attr("rel");
		$('#'+id).click();
		event.stopPropagation();
	};
	
	// 选择上级机构
	company.selectCompanyParent = function(event) {

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
			$("#" + targetId + "_parentId").val(treeNode.id);
			$("#" + targetId + "_parentName").val(treeNode.name);
			$("#" + targetId + "_parentTreeDiv").hide();
			$("#" + targetId + "_parentName").change();
		};

		
		$("#" + targetId + "_parentTreeDiv").toggle();

		var comParentSetting = {
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

		var zNodes = ${ztree};

		$.fn.zTree.init($("#" + targetId + "_parentTree"), comParentSetting, zNodes);
		
		event.stopPropagation();
	};
})();
</script>
