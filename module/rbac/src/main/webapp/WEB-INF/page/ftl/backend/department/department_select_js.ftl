<script type="text/javascript">
(function(){
	if(!window['department']){
		window['department'] = {};
	}
	
	var company = window['department'];
	
	// 隐藏所有父级选择面板
	$(document).click(function(){
		$("[id$='_parentTreeDiv']").each(function(){
			if($(this).is(":visible")){
				$(this).hide();	
			}
		});
	});
	
	// 按钮点击选择上级部门
	company.selectByInput = function(event){
		var id = $(event.target).attr("rel");
		$('#'+id).click();
		event.stopPropagation();
	};
	
	// 选择上级机构
	company.selectParent = function(event) {

		// 选择部门，必须先选择公司
		var companyId = $("[id$='_companyId'").val();
		if(companyId == undefined || companyId == ""){
			parent.layer.alert("请先选择所属组织机构");
			$("#companyId").focus();
			return;
		}
		
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
		
    	// 成功回调函数
    	var success = function(data){
    		console.log(JSON.stringify(data));
    		if(data.flag == '1') {
    			// 动态获取当前所选组织机构编码
    			var zNodes = eval(data);

    			$.fn.zTree.init($("#" + targetId + "_parentTree"), comParentSetting, zNodes);
    		} else {
    			layer.alert("无结果");
    		}
		};
		
		var searchData = {"companyId" : companyId};
		ajaxJson("${app.basePath}/rbac/backend/department/getCompanyTree",searchData,success,success);
		
		event.stopPropagation();
	};
})();
</script>
