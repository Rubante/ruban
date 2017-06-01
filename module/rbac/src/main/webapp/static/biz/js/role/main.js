(function() {
	var rubanRole = {};

	// 获取访问地址
	getRealPath = function(path) {
		return bizURL.rubanRole + path;
	};

	// 添加角色
	rubanRole.add = function() {

		var targetId = "#role_add_form";

		// 数据提交
		var submit = function() {

			// 成功回调
			var success = function(data) {
				msgAlert(data);
				// 更新数据
				rubanRole.search();
			}

			var options = ajaxOption(getRealPath("addSave"), success);
			$(targetId).ajaxSubmit(options);
		};

		var success = function(html) {
			layer.open({
				title : '添加角色',
				area : [ '770px' ],
				content : html,
				btn : [ '添加', '关闭' ],
				yes : function(index, layer) {
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


	// 删除角色
	rubanRole.batchDelete = function() {
		// 获取所有选中的ids
		var ids = getSelections("role_checkbox", "role_id");

		if (ids != "") {
			// 删除确认
			layer.confirm('确定要删除么？', {
				btn : [ '确定', '关闭' ]
			}, function() {
				// 成功回调
				var success = function(data) {
					msgAlert(data);
					// 更新数据
					rubanRole.search();
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

	// 查询角色
	rubanRole.search = function() {

		// 成功回调
		var success = function(html) {
			$("#roleListTd").html(html);
		};

		var options = ajaxOptionHtml(getRealPath("search"), success);
		// 添加分页数据
		addAjaxData(options, pagination.getPageData());

		$("#searchForm").ajaxSubmit(options);
	};

	// 修改角色
	rubanRole.update = function(id) {

		var success = function(html) {

			var target_id = "#role_update_form";

			// 提交数据
			var submit = function() {
				var options = ajaxOption(getRealPath("updateSave"), function(data) {
					msgAlert(data);
					// 更新数据
					rubanRole.search();
				});
				$(target_id).ajaxSubmit(options);
			};

			layer.open({
				title : '修改角色',
				area : [ "770px" ],
				content : html,
				btn : [ '修改', '关闭' ],
				yes : function(index, layer) {
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

	// 查看角色
	rubanRole.detail = function(id) {

		// 成功回调函数
		var success = function(data) {

			if (data.flag == '0') {
				layer.alert("无结果");
			} else {
				layer.open({
					title : '查看角色',
					area : [ "800px" ],
					content : data,
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

	// 删除角色
	rubanRole.deleteSingle = function(id) {
		var data = {
			"id" : id
		};
		// 成功回调
		var success = function(data) {
			msgAlert(data);
			// 更新数据
			rubanRole.search();
		};

		// 删除确认
		layer.confirm('确定要删除么？', {
			btn : [ '确定', '关闭' ]
		}, function() {
			ajaxJson(getRealPath("delete"), data, success);
		});
	};

	// 初始化权限树
	initResourceTree = function(roleId) {
		// 成功回调
		var success = function(data) {
			$("#resourceTree").html(data);
			
			var param = {"roleId": roleId};
			ajaxJson(getRealPath("grantList"), param, function(list){
				for(var i = 0; i < list.length; i++){
					$("#CHK_" + list[i].resourceId).prop("checked", "checked");
				}
			});
		};
		
		var data = {};
		ajaxHtml(bizURL.resourceTree, data, success);
	};

	// 角色授权
	rubanRole.grantPage = function(id) {

		// 成功回调函数
		var success = function(html) {
			
			$("#content").html(html);
			
			// 初始化权限树
			initResourceTree(id);
		};

		// 请求数据
		var data = {
			"id" : id
		};
		ajaxHtml(getRealPath("grantPage"), data, success);
	};
	
	// 角色授权保存
	rubanRole.grantSave = function() {
		
		var resources = new Array();
		
		$("[name='resource_checkbox']").each(function(index){

			var checked = $(this).prop("checked");

			if(checked) {
				var rid = $(this).attr("rid");
				resources.push(rid);
			}
		});
		
		var param = {
			"id" : $("#roleId").val(),
			"resourceIds" : resources.join(",")
		};
		
		ajaxJson(getRealPath("grantSave"), param, function(data){
			msgAlert(data);
		})
	};
	
	// 资源选择
	rubanRole.checkResource = function(event) {

		var target = $(event.target);
		var pid = target.attr("pid");
		
		// 取消选择
		if(!target.prop('checked')){
			$("[pid^='" + pid + "']").prop("checked", false);
		} else {
			// 选中父级
			var ids = pid.split("_");
			if(ids != undefined && ids.length > 0){
				for(var i = 0; i < ids.length; i++){
					console.log(ids[i]);
					$("#CHK_" + ids[i]).prop("checked", true);
				}
			}
		}

	};
	
	// 启用角色
	rubanRole.enable = function(id) {
		var data = {
			"id" : id
		};
		// 成功回调
		var success = function(data) {
			msgAlert(data);
			rubanRole.search();
		};

		// 删除确认
		layer.confirm('确定要启用么？', {
			btn : [ '确定', '关闭' ]
		}, function() {
			ajaxJson(getRealPath("enable"), data, success);
		});
	};

	// 禁用角色
	rubanRole.unable = function(id) {
		var data = {
			"id" : id
		};
		// 成功回调
		var success = function(data) {
			msgAlert(data);
			rubanRole.search();
		};

		// 删除确认
		layer.confirm('确定要禁用么？', {
			btn : [ '确定', '关闭' ]
		}, function() {
			ajaxJson(getRealPath("unable"), data, success);
		});
	};
	
	window['rubanRole'] = rubanRole;
})();
