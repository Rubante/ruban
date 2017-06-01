(function() {
	var resource = {};

	// 获取访问地址
	getRealPath = function(path) {
		return bizURL.resource + path;
	};

	// 组织机构树列表查询
	resource.objectList = function(companyId) {
		resourceList(companyId);
	};

	// 添加
	resource.add = function() {

		var targetId = "#resource_add_form";

		var layerIndex = 0;

		// 数据提交
		var submit = function() {
			// 成功回调
			var success = function(data) {
				msgAlert(data);
				resource.search();
				layer.close(layerIndex);
			}

			var options = ajaxOption(getRealPath("addSave"), success);
			$(targetId).ajaxSubmit(options);
		};

		var success = function(html) {
			layer.open({
				title : '添加资源',
				area : [ '800px' ],
				content : html,
				type : 1,
				fixed : false,
				btn : [ '添加', '关闭' ],
				yes : function(index, layer) {
					layerIndex = index;
					// 校验数据成功后提交
					validate($(targetId), submit);
				},
				success : function() {
					// 绑定页面校验
					bindValidate($(targetId));
				}
			});
		};

		// 数据请求
		ajaxHybrid(getRealPath("addPage"), {}, success);
	};

	// 批量删除资源
	resource.batchDelete = function() {
		// 获取所有选中的ids
		var ids = getSelections("resource_checkbox", "resource_id");

		if (ids != "") {
			// 删除确认
			layer.confirm('确定要删除么？', {
				btn : [ '确定', '关闭' ]
			}, function() {
				// 成功回调
				var success = function(data) {
					msgAlert(data);
					resource.search();
				}
				var data = {
					"ids" : ids
				};
				ajaxJson(getRealPath("batchDelete"), data, success);
			});
		} else {
			layer.alert("请选择要删除的数据！");
		}
	};

	// 删除资源
	resource.deleteSingle = function(id) {
		var data = {
			"id" : id
		};
		// 成功回调
		var success = function(data) {
			msgAlert(data);
			resource.search();
		};

		// 删除确认
		layer.confirm('确定要删除么？', {
			btn : [ '确定', '关闭' ]
		}, function() {
			ajaxJson(getRealPath("delete"), data, success);
		});
	};

	// 启用资源
	resource.enable = function(id) {
		var data = {
			"id" : id
		};
		// 成功回调
		var success = function(data) {
			msgAlert(data);
			resource.search();
		};

		// 删除确认
		layer.confirm('确定要启用么？', {
			btn : [ '确定', '关闭' ]
		}, function() {
			ajaxJson(getRealPath("enable"), data, success);
		});
	};

	// 禁用资源
	resource.unable = function(id) {
		var data = {
			"id" : id
		};
		// 成功回调
		var success = function(data) {
			msgAlert(data);
			resource.search();
		};

		// 删除确认
		layer.confirm('确定要禁用么？', {
			btn : [ '确定', '关闭' ]
		}, function() {
			ajaxJson(getRealPath("unable"), data, success);
		});
	};

	// 修改资源信息
	resource.update = function(id) {

		var success = function(html) {

			var layerIndex = 0;

			var target_id = "#resource_update_form";

			// 提交数据
			var submit = function() {
				var options = ajaxOption(getRealPath("updateSave"), function(
						data) {
					msgAlert(data);
					resource.search();
					layer.close(layerIndex);
				});
				$(target_id).ajaxSubmit(options);
			};

			layer.open({
				title : '修改资源信息',
				area : [ "800px" ],
				content : html,
				type : 1,
				fixed : false,
				btn : [ '修改', '关闭' ],
				yes : function(index, layer) {
					layerIndex = index;
					validate($(target_id), submit);
				},
				success : function() {
					// 页面校验
					bindValidate($(target_id));
				}
			});
		};

		// 请求明细数据
		var data = {
			"id" : id
		};

		// 数据请求
		ajaxHybrid(getRealPath("updatePage"), data, success);
	};

	// 查看资源
	resource.detail = function(id) {

		// 成功回调函数
		var success = function(data) {

			if (data.flag == '0') {
				layer.alert("无结果");
			} else {
				layer.open({
					title : '查看资源',
					area : [ "800px", "460px" ],
					content : data,
					type : 1,
					fixed : false,
					btn : [ '关闭' ]
				});
			}
		};

		// 请求数据
		var data = {
			"id" : id
		};
		ajaxHybrid(getRealPath("detail"), data, success);
	};

	// 查询
	resource.search = function() {
		// 成功回调
		var success = function(html) {
			$("#resourceListTd").html(html);
		};

		var options = ajaxOption(getRealPath("search"), success);
		// 添加分页数据
		addAjaxData(options, pagination.getPageData());

		$("#searchForm").ajaxSubmit(options);
	};

	// 资源排序
	resource.sort = function() {
		// 成功回调
		var success = function(html) {
			$("#content").html(html);
		};

		// 查询条件
		var searchData = {};
		// 数据请求
		ajaxHtml(getRealPath("sortList"), searchData, success);
	};

	// 排序结果保存
	resource.sortSave = function() {
		var ids = $("[name=resource_sort_id]");
		var levels = $("[name=resouce_sort_level]");
		var pathIds = $("[name=resource_path_id]");
		var parentIds = $("[name=resource_parent_id]");

		var dataId = new Array();
		ids.each(function() {
			dataId.push($(this).val());
		});

		var dataLevel = new Array();
		levels.each(function() {
			dataLevel.push($(this).val());
		});

		var pathId = new Array();
		pathIds.each(function() {
			pathId.push($(this).val());
		});

		var parentId = new Array();
		parentIds.each(function() {
			parentId.push($(this).val());
		});

		// 成功提示
		var success = function(data) {
			msgAlert(data);
		};
		var data = {
			"ids" : dataId.join(","),
			"levels" : dataLevel.join(","),
			"pathIds" : pathId.join(","),
			"parentIds" : parentId.join(",")
		};

		console.log(data);
		// 数据提交
		ajaxJson(getRealPath("sort"), data, success);
	}

	// 查询
	resource.search = function() {
		// 成功回调
		var success = function(html) {
			$("#resourceListTd").html(html);
		};

		var options = ajaxOption(getRealPath("search"), success);
		// 添加分页数据
		addAjaxData(options, pagination.getPageData());

		$("#searchForm").ajaxSubmit(options);
	};

	// 隐藏下级列表
	resource.closeFolder = function(event) {
		var target = $(event.target).parent().parent();
		var pid = target.attr("pid");

		$("[id^='close_" + pid + "']").show();
		$("[id^='open_" + pid + "']").hide();

		$("[pid^='" + pid + "_']").hide();

		event.preventDefault();
		event.stopPropagation();
	};

	// 显示下级列表
	resource.openFolder = function(event) {
		var target = $(event.target).parent().parent();
		var pid = target.attr("pid");

		$("[id^='close_" + pid + "']").hide();
		$("[id^='open_" + pid + "']").show();

		$("[pid^='" + pid + "_']").show();

		event.preventDefault();
		event.stopPropagation();
	};

	window['resource'] = resource;
})();
