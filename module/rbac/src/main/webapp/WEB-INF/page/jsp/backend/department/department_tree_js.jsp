<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<script type="text/javascript">
(function(){
	var dptTree = {};
	
	var currentId = "";
	var currentTId = "";
	
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
		if(treeNode.isParent){
			// 展示遮罩
			showLoadPanel();
			// 查询条件
			${param.module}.objectList(treeNode.id);

			currentId = treeNode.id;
			currentTId = treeNode.tId;
			
			// 隐藏遮罩
			hideLoadPanel();
		}
	};
	
	// 双击查看叶子节点
	function zTreeBeforeDblClick(treeId, treeNode, clickFlag){
		// 叶子节点且有根
		if (!treeNode.isParent) {
			${param.module}.objectDetail(treeNode.id);
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
	dptTree.init = function(treeId,zNodes){
		return $.fn.zTree.init($("#"+treeId), setting, zNodes);
	}
	
	// 获取当前节点ID
	dptTree.selectCurrent = function(treeId){
		
		var treeObj = $.fn.zTree.getZTreeObj(treeId);

		if(currentId != "") {
			// 查询条件
			${param.module}.objectList(currentId);
		} else {
			var nodes = treeObj.getNodes();

			${param.module}.objectList(nodes[0].id);
			currentId = nodes[0].id;
		}

		if(currentTId != "") {
			var node = treeObj.getNodeByTId(currentTId);
			treeObj.selectNode(node,false,true);
		} else {
			var nodes = treeObj.getNodes();

			treeObj.selectNode(nodes[0],false,true);
			currentTId = nodes[0].tId;
		}
	}
	
	// 获取当前选中节点
	dptTree.getCurrentId = function(){
		return currentId;
	};

	window['dptTree'] = dptTree;
})();
</script>