<script type="text/javascript">
(function(){
	if(!window['company']){
		window['company'] = {};
	}
	
	var company = window['company'];
	
	// 隐藏所有组织机构选择面板
	$(document).click(function(){
		$("[id$='_companyTreeDiv']").each(function(){
			if($(this).is(":visible")){
				$(this).hide();	
			}
		});
	});
	
	// 按钮点击选择所属机构
	company.clickCompanyInput = function(event){
		var id = $(event.target).attr("rel");
		$('#'+id).click();
		event.stopPropagation();
	};
	
	// 选择所属机构
	company.selectCompany = function(event) {

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
			$("#" + targetId + "_companyId").val(treeNode.id);
			$("#" + targetId + "_companyName").val(treeNode.name);
			$("#" + targetId + "_companyTreeDiv").hide();
			$("#" + targetId + "_companyName").change();
		};

		
		$("#" + targetId + "_companyTreeDiv").toggle();

		var setting = {
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

		$.fn.zTree.init($("#" + targetId + "_companyTree"), setting, zNodes);
		
		event.stopPropagation();
	};
})();
</script>
