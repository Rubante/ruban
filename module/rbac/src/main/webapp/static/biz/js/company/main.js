(function() {
	var company = {};

	// 获取访问地址
	getRealPath = function(path) {
		return bizURL.company + path;
	};

	// 添加组织机构
	company.add = function() {

		var addTargetId = "#addForm";

		var layerIndex = 0;
		
		// 数据提交（添加保存）
		var submit = function() {

			// 成功回调
			var success = function(data) {
				msgAlert(data);
				company.search();
				layer.close(layerIndex);
			}

			var options = ajaxOption(getRealPath("addSave"), success);
			$(addTargetId).ajaxSubmit(options);
		};

		// 请求添加html页面
		var success = function(html) {
			layer.open({
				title : '添加组织机构',
				area : [ "828px", "540px" ],
				content : html,
				btn : [ '添加', '关闭' ],
				type : 1,
				fixed : false,
				yes : function(index, layer) {
					layerIndex = index;
					// 校验数据成功后提交
					validate($(addTargetId), submit);
				},
				success : function() {
					// 绑定页面校验
					bindValidate($(addTargetId));
				}
			});
		};

		// 数据请求
		ajaxHtml(getRealPath("addPage"), {}, success);
	};

	// 组织机构排序
	company.sort = function() {
		// 成功回调
		var success = function(html) {
			layer.open({
				title : '组织机构排序',
				btn : [ '保存', '关闭' ],
				area : [ "828px" ],
				content : html,
				type : 1,
				fixed : false,
				yes : function(index, layer) {
					var ids = $("[name=company_sort_id]");

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
						company.search();
						layer.close(index);
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
			"type" : $("#type").val(),
			"name" : $("#name").val()
		};
		// 数据请求
		ajaxHtml(getRealPath("sortList"), searchData, success);
	};

	// 列表显示
	company.companyList = function(companyId) {
		if (companyId != undefined) {
			// 查询条件
			var searchData = {
				"companyId" : companyId
			};

			// 成功回调
			var success = function(html) {
				$("#companyListTd").html(html);
			};

			// 数据请求
			ajaxHtml(getRealPath("list"), searchData, success);
		}
	};
    
    // 查询
	company.search = function(){

		// 成功回调
		var success = function(html){
			$("#companyListTd").html(html);
		};
    	
		
		var options = ajaxOptionHtml(getRealPath("search"), success);
		// 添加分页数据
		addAjaxData(options,pagination.getPageData());
		
		$("#searchForm").ajaxSubmit(options);
    };
    
	// 查看组织机构
	company.detail = function(id) {
		// 请求数据
		var data = {
			"id" : id
		};

		// 成功回调函数
		var success = function(html) {
			layer.open({
				title : '查看组织机构',
				area : [ "800px", "540px" ],
				content : html,
				type : 1,
				fixed : false,
				btn : [ '关闭' ]
			});
		};

		ajaxHtml(getRealPath("detail"), data, success);
	};

	// 删除组织机构
	company.deleteSingle = function(id) {
		var data = {
			"id" : id
		};
		// 成功回调
		var success = function(data) {
			msgAlert(data);
			company.search();
		};

		// 删除确认
		layer.confirm('确定要删除么？', {
			btn : [ '确定', '关闭' ]
		}, function() {
			ajaxJson(getRealPath("delete"), data, success);
		});
	};

	// 删除组织机构
	company.batchDelete = function() {
		// 获取所有选中的ids
		var ids = getSelections("company_checkbox", "company_id");

		if (ids != "") {
			// 删除确认
			layer.confirm('确定要删除么？', {
				btn : [ '确定', '关闭' ]
			}, function() {
				// 成功回调
				var success = function(data) {
					msgAlert(data);
					company.search();
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
	
	// 修改组织机构
	company.update = function(id) {

		var layerIndex = 0;
		
		var success = function(html) {
			var targetId = "#updateForm";
			// 提交数据
			var submit = function() {
				var options = ajaxOption(getRealPath("updateSave"), function(data) {
					msgAlert(data);
					company.search();
					layer.close(layerIndex);
				});
				$(targetId).ajaxSubmit(options);
			};

			layer.open({
				title : '修改组织机构',
				area : [ "800px", "540px" ],
				content : html,
				type : 1,
				fixed : false,
				btn : [ '修改', '关闭' ],
				yes : function(index, layer) {
					validate($(targetId), submit);
					
					layerIndex = index;
				},
				success : function() {
					// 页面校验
					bindValidate($(targetId));
				}
			});
		};

		// 请求明细数据
		var data = {
			"id" : id
		};
		ajaxHtml(getRealPath("updatePage"), data, success);
	};

	// 打开下级节点
	company.openFolder = function(event){
		var tr = $(event.target).parent().parent();
		var pathCode = tr.attr("pathCode");
		$("[pathCode^='" + pathCode + "']").show();
		
		var target = $(event.target);
		target.hide();
		$("#"+target.attr("rel")).show();
		
		// 展示子节点
		$("#childDisplay").val("1");
	};
	
	// 隐藏下级节点
	company.closeFolder = function(event){
		var tr = $(event.target).parent().parent();
		var pathCode = tr.attr("pathCode");
		$("tr[pathCode!='" + pathCode + "'][pathCode^='" + pathCode + "']").hide();
		
		var target = $(event.target);
		target.hide();
		$("#"+target.attr("rel")).show();
		
		// 展示子节点
		$("#childDisplay").val("0");
	};
	
	// 选中当前行
	company.selectCurrent = function(event) {
		// 选中当前的
		var radio = $(event.target).parent().find(":radio");
		radio.prop("checked", true);
	};
	
	// 根节点列表显示
	company.rootCompanyList = function(type) {
		// 查询条件
		var searchData = {
			"type" : type,
			"companyId" : "0"
		};

		// 成功回调
		var success = function(html) {
			$("#companyListTd").html(html);
		};

		// 数据请求
		ajaxHtml(getRealPath("list"), searchData, success);
	};
	
	window['company'] = company;
})();
