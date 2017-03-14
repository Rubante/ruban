<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>

<script type="text/javascript">
(function(){
	var resource = {};
	
	// 获取访问地址
	getRealPath = function(path){
		var basePath = '<s:url value="/rbac/backend/resource/" />';
		return basePath + path;
	};
	
    // 组织机构树列表查询
    resource.objectList = function(companyId){
    	resourceList(companyId);
    };
	
	// 添加
    resource.add = function(){
    	
    	var targetId = "#resource_add_form";
    	
		// 数据提交
		var submit = function(){
	    	// 成功回调
			var success = function(data){
				msgAlert(data);
				resource.search();
			}
	    	
			var options = ajaxOption(getRealPath("addSave"),success);
			$(targetId).ajaxSubmit(options);
		};
		
		var success = function(html){
	    	layer.open({
	    		type: 1,
	    		title: '添加资源',
	    		area:['800px'],
	    		content: html,
	    		btn:['添加', '关闭'],
	    		yes:function(index, layer){
	    			// 校验数据成功后提交
	    			validate($(targetId),submit);
	    		},
	    		success:function(){
	    	    	// 绑定页面校验
	    	    	bindValidate($(targetId));
	    		}
	    	});
		};
    	
		// 数据请求
		ajaxHybrid(getRealPath("addPage"),{},success);
    };
    
    // 删除资源
	resource.batchDelete = function(){
    	// 获取所有选中的ids
    	var ids = getSelections("resource_checkbox","resource_id");

    	if(ids!="") {
			// 删除确认
			layer.confirm('确定要删除么？', {
				  btn: ['确定','关闭']
				}, function(){
			    	// 成功回调
					var success = function(data){
			    		msgAlert(data);
			    		resource.search();
					}
					var data = {"ids":ids};
					ajaxJson(getRealPath("batchDelete"), data, success);
			});
    	} else {
    		layer.alert("请选择要删除的数据！");
    	}
	 };
	    
    // 删除资源
    resource.deleteSingle = function(id){
    	var data = {"id":id};
		// 成功回调
    	var success = function(data){
    		msgAlert(data);
    		resource.search();
		};
		
		// 删除确认
		layer.confirm('确定要删除么？', {
			  btn: ['确定','关闭']
			}, function(){
				ajaxJson(getRealPath("delete"), data, success);
		});
    };
	    
    // 修改资源信息
    resource.update = function(id){

		var success = function(html){
			
			var target_id = "#resource_update_form";
			
			// 提交数据
			var submit = function(){
    			var options = ajaxOption(getRealPath("updateSave"),function(data){
    				msgAlert(data);
    				resource.search();
    			});
    			$(target_id).ajaxSubmit(options);
			};

	    	layer.open({
	    		title: '修改资源信息',
	    		area: ["800px"],
	    		content: html,
	    		btn: ['修改', '关闭'],
	    		yes: function(index, layer){
	    			validate($(target_id),submit);
	    		},
	    		success: function(){
	    	    	// 页面校验
	    	    	bindValidate($(target_id));
	    		}
	    	});
		};
		
		// 请求明细数据
		var data = {"id":id};
		
		// 数据请求
		ajaxHybrid(getRealPath("updatePage"),data,success);
    };
    
    // 查看部门
    resource.detail = function(id){
    	
    	// 成功回调函数
    	var success = function(data){
    		
    		if(data.flag == '0') {
    			layer.alert("无结果");
    		} else {
		    	layer.open({
		    		title: '查看资源',
		    		area:["800px"],
		    		content: data,
		    		btn: ['关闭']
		    	});
    		}
		};
		
    	// 请求数据
    	var data = {"id":id};
		ajaxHybrid(getRealPath("detail"),data,success);
    };
    
    // 查询
    resource.search = function(){
		// 成功回调
		var success = function(html){
			$("#resourceListTd").html(html);
		};
		
		var options = ajaxOption(getRealPath("search"), success);
		// 添加分页数据
		addAjaxData(options,pagination.getPageData());
		
		$("#searchForm").ajaxSubmit(options);
    };
    
    // 资源排序
    resource.sort = function(){
		// 成功回调
		var success = function(html){
	    	layer.open({
	    		title: '资源排序',
				btn: ['保存','关闭'],
	    		area:["828px"],
			    content: html,
			    yes: function(){
			    	var ids = $("[name=resource_sort_id]");
			    	
			    	var data = "";
			    	ids.each(function(){
			    		if(data == "") {
			    			data = $(this).val();
			    		} else {
			    			data += "," + $(this).val();
			    		}
			    	});
			    	
			    	// 成功提示
			    	var success = function(data){
			    		msgAlert(data);
			    		resource.search();
			    	};
			    	
					// 数据提交
					ajaxJson(getRealPath("sort"), {"ids":data}, success);
			    }
	    	});
		};
		
		// 查询条件
		var searchData = {};
		// 数据请求
		ajaxHtml(getRealPath("sortList"), searchData, success);
		
    };

    // 查询
    resource.search = function(){
		// 成功回调
		var success = function(html){
			$("#resourceListTd").html(html);
		};
		
		var options = ajaxOption(getRealPath("search"), success);
		// 添加分页数据
		addAjaxData(options,pagination.getPageData());
		
		$("#searchForm").ajaxSubmit(options);
    };

    // 属性查看
    resource.fieldDetail = function(resourceId){
		// 数据
		var data = {"resourceId" : resourceId};
		
		// 成功回调
		var success = function(html){
	    	layer.open({
	    		title: '属性查看',
				btn: ['关闭'],
	    		area:["828px"],
			    content: html,
			    yes: function(index){
			    	layer.close(index);
			    }
	    	});
		};
		
		// 数据请求
		ajaxHtml(getFieldRealPath("detail"), data, success);
    };
    
    // 属性管理
    resource.fieldList = function(resourceId){
		// 数据
		var data = {"resourceId" : resourceId};
		
		// 成功回调
		var success = function(html){
	    	layer.open({
	    		type: 1,
	    		title: '属性查看',
	    		zIndex: layer.zIndex,
				btn: ['关闭'],
	    		area:["828px"],
			    content: html,
			    yes: function(index){
			    	layer.close(index);
			    }
	    	});
		};
		// 数据请求
		ajaxHtml(getFieldRealPath("search"), data, success);
    };
    
    window['resource'] = resource;
})();
</script>
