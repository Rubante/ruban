<script type="text/javascript">
(function(){
	var companyTree = {};
	
	var currentId = "";
	var currentTId = "";
	var isRoot = true;
	
	// 设置字体颜色
	function setFontCss(treeId, treeNode) {
		if (treeNode.isParent) {
			return {
				color : "green",
				"font-weight" : "bold"
			};
		} else {
			return {
				color : "blue"
			};
		}
	};

	// 鼠标单击事件
	function zTreeBeforeClick(treeId, treeNode, clickFlag) {
		// 非根节点
		if(treeNode.parentTId){
			// 展示遮罩
			showLoadPanel();

			if (treeNode.isParent) {
				// 查询条件
				objectList(treeNode.id);
			} else {
				objectList();
			}

			currentId = treeNode.id;
			currentTId = treeNode.tId;
			isRoot = false;
			
			// 隐藏遮罩
			hideLoadPanel();
		} else {
			isRoot = true;
		}
	};
	
	// 双击查看叶子节点
	function zTreeBeforeDblClick(treeId, treeNode, clickFlag){
		// 叶子节点且有根
		if (!treeNode.isParent && treeNode.parentTId) {
			objectDetail(treeNode.id);
		}
	};
	
	// 右键操作
	function zTreeOnRightClick(event, treeId, treeNode){
		if (!treeNode.isParent) {

		}
	};
	
	var setting = {
		check : {
			enable : false
		},
		callback : {
			beforeClick : zTreeBeforeClick,
			beforeDblClick : zTreeBeforeDblClick,
			onRightClick: zTreeOnRightClick
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

	// 初始化一颗树
	companyTree.init = function(treeId,zNodes){
		return $.fn.zTree.init($("#"+treeId), setting, zNodes);
	}
	
	// 获取当前节点ID
	companyTree.selectCurrent = function(treeId){
		var treeObj = $.fn.zTree.getZTreeObj(treeId);
		
		if(currentId != "" && !isRoot) {
			// 查询条件
			objectList(currentId);
		} else {
			var nodes = treeObj.getNodes();
			
			objectList(nodes[0].children[0].id);
			currentId = nodes[0].children[0].id;
		}
		
		if(currentTId != "" && !isRoot) {
			var node = treeObj.getNodeByTId(currentTId);
			treeObj.selectNode(node,false,true);
		} else {
			var nodes = treeObj.getNodes();

			treeObj.selectNode(nodes[0].children[0],false,true);
			currentTId = nodes[0].children.tId;
		}
	}
	
	// 获取当前选中节点
	companyTree.getCurrentId = function(){
		return currentId;
	};
	
	window['companyTree'] = companyTree;
})();
</script>