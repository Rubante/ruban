(function() {
	var department = {};

	// 获取访问地址
	getRealPath = function(path) {
		return bizURL.department + path;
	};

	// 添加部门
	department.add = function() {

		// 数据提交
		var submit = function() {

			// 成功回调
			var success = function(data) {
				msgAlert(data);
				refresh();
			}

			var options = ajaxOption(getRealPath("add"), success);
			$("#department_add_form").ajaxSubmit(options);
		};

		// 获取添加页面
		var success = function(html) {
			layer.open({
				title : '添加部门',
				area : [ "800px" ],
				content : html,
				type : 1,
				fixed : false,
				btn : [ '添加', '关闭' ],
				yes : function(index, layer) {
					// 校验数据成功后提交
					validate($("#department_add_form"), submit);
				},
				success : function() {
					// 绑定页面校验
					bindValidate($("#department_add_form"));
				}
			});
		}

		// 数据请求
		ajaxHybrid(getRealPath("add"), {}, success);
	};

	// 删除部门
	department.batchDelete = function() {
		// 获取所有选中的ids
		var ids = getSelections("department_checkbox", "department_id");

		if (ids != "") {
			// 删除确认
			layer.confirm('确定要删除么？', {
				btn : [ '确定', '关闭' ]
			}, function() {
				// 成功回调
				var success = function(data) {
					msgAlert(data);
					refresh();
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

	// 部门排序
	department.sort = function() {
		// 成功回调
		var success = function(html) {
			layer.open({
				title : '部门排序',
				btn : [ '保存', '关闭' ],
				area : [ "828px" ],
				content : html,
				type : 1,
				fixed : false,
				yes : function() {
					var ids = $("[name=department_sort_id]");

					var data = "";
					ids.each(function() {
						if (data == "") {
							data = $(this).val();
						} else {
							data += "," + $(this).val();
						}
					});

					// 成功提示
					var success = function(data) {
						msgAlert(data);
						refresh();
					};

					// 数据提交
					ajaxJson(getRealPath("sort"), {
						"ids" : data
					}, success);
				}
			});
		};

		// 查询条件
		var searchData = {
			"departmentId" : dptTree.getCurrentId()
		};
		// 数据请求
		ajaxHtml(getRealPath("sortList"), searchData, success);
	};

	// 刷新界面
	refresh = function() {
		var success = function(data) {
			// 刷新部门树
			var treeObj = dptTree.init("dptZtree", eval(data), department);

			// 更新当前节点
			dptTree.selectCurrent("dptZtree");
		};

		ajaxText(getRealPath("getDptTree"), {}, success);
	};

	// 树状结构查询
	department.objectList = function(companyId) {
		departmentList(companyId);
	};

	// 列表显示
	departmentList = function(departmentId) {

		// 查询条件
		var searchData = {
			"departmentId" : departmentId
		};

		// 成功回调
		var success = function(html) {
			$("#departmentListTd").html(html);
		};

		// 数据请求
		ajaxHtml(getRealPath("list"), searchData, success);
	};

	// 双击树节点
	department.objectDetail = function(id) {
	};

	// 查看部门
	department.detail = function(id) {
		// 请求数据
		var data = {
			"id" : id
		};

		// 成功回调函数
		var success = function(data) {

			if (data.flag == '0') {
				layer.alert("无结果");
			} else {
				layer.open({
					title : '查看部门',
					area : [ "800px" ],
					content : data,
					btn : [ '关闭' ]
				});
			}
		};

		ajaxHybrid(getRealPath("detail"), data, success);
	};

	// 删除部门
	department.deleteSingle = function(id) {
		var data = {
			"id" : id
		};
		// 成功回调
		var success = function(data) {
			msgAlert(data);
			refresh();
		};

		// 删除确认
		layer.confirm('确定要删除么？', {
			btn : [ '确定', '关闭' ]
		}, function() {
			ajaxJson(getRealPath("delete"), data, success);
		});
	};

	// 选中当前行
	department.selectCurrent = function(event) {
		// 选中当前的
		var radio = $(event.target).parent().find(":radio");
		radio.prop("checked", true);
	};

	// 排序部门
	department.sortByFlag = function(flag) {
		// 取消之前选中的
		var radioSelected = getSelectedRadio("department_sort_radio");
		var tr = $(radioSelected).parent().parent();
		if (radioSelected == null) {
			parent.layer.alert("请选中要排序的行！");
			return;
		}
		// 上移
		if (flag == 0) {
			var prev = tr.prev();
			if (prev.length == 1) {
				prev.before(tr);
			} else {
				parent.layer.alert("已到达最顶部，无法再移动！");
			}
		} else {
			// 下移
			var next = tr.next();
			if (next.length == 1) {
				next.after(tr);
			} else {
				parent.layer.alert("已到达最底部，无法再移动！");
			}
		}
	};

	// 修改部门
	department.update = function(id) {

		var success = function(html) {

			// 提交数据
			var submit = function() {
				var options = ajaxOption(getRealPath("update"), function(data) {
					msgAlert(data);
					refresh();
				});
				$("#department_update_form").ajaxSubmit(options);
			};

			layer.open({
				title : '修改部门',
				area : [ "800px" ],
				content : html,
				btn : [ '修改', '关闭' ],
				yes : function(index, layer) {
					validate($("#department_update_form"), submit);
				},
				success : function() {
					// 页面校验
					bindValidate($("#department_update_form"));
				}
			});
		};

		// 请求明细数据
		var data = {
			"id" : id
		};
		ajaxHtml(getRealPath("update"), data, success);
	};

	$(document).ready(function() {
		refresh();
	});

	window['department'] = department;
})();
