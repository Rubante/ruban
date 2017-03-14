<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>

<script type="text/javascript">
(function(){
	var resourceField = {};
	
	// 获取属性访问地址
	getFieldRealPath = function(path){
		var basePath = '<s:url value="/rbac/backend/resourceField/" />';
		return basePath + path;
	};
	
    // 查询
    resourceField.search = function(){
		layer.closeAll();
		resource.fieldList($("#searchResourceId").val());
    };

    
	// 添加
    resourceField.add = function(resourceId){
    	
    	var targetId = "#resourceField_add_form";
    	
		// 数据提交
		var submit = function(){
			
	    	// 成功回调
			var success = function(data){
				msgAlert(data, function(){
					resourceField.search()
				});
			}
	    	
			var options = ajaxOption(getFieldRealPath("addSave"),success);
			$(targetId).ajaxSubmit(options);
		};
		
		var success = function(html){
	    	layer.open({
	    		type: 1,
	    		title: '添加资源属性',
	    		area:['800px'],
	    		zIndex: layer.zIndex,
	    		content: html,
	    		btn:['添加', '关闭'],
	    		yes:function(index){
	    			// 校验数据成功后提交
	    			var result = validate($(targetId),submit);
	    			if(result==0) {
	    				layer.close(index);
	    			}
	    		},
	    		success:function(){
	    	    	// 绑定页面校验
	    	    	bindValidate($(targetId));
	    	    	$("#addResourceId").val(resourceId);
	    		}
	    	});
		};
    	
		// 数据请求
		ajaxHybrid(getFieldRealPath("addPage"),{},success);
    };
	    
    // 删除资源属性
    resourceField.deleteSingle = function(id){
    	var data = {"id":id};
		// 成功回调
    	var success = function(data){
    		msgAlert(data, function(){
				resourceField.search()
			});
		};
		
		// 删除确认
		layer.confirm('确定要删除么？', getLayerConfirmOption(), 
			function(){
				ajaxJson(getFieldRealPath("delete"), data, success);
		});
    };
	    
    // 修改资源属性
    resourceField.update = function(id){

		var success = function(html){
			
			var target_id = "#resourceField_update_form";
			
			// 提交数据
			var submit = function(){
    			var options = ajaxOption(getFieldRealPath("updateSave"),function(data){
    				msgAlert(data, function(){
    					resourceField.search()
    				});
    			});
    			$(target_id).ajaxSubmit(options);
			};

			layer.open({
	    		title: '修改资源属性',
	    		area: ["800px"],
	    		zIndex: layer.zIndex,
	    		content: html,
	    		btn: ['修改', '关闭'],
	    		yes: function(index){
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
		ajaxHybrid(getFieldRealPath("updatePage"),data,success);
    };
    
    // 查看资源属性
    resourceField.detail = function(id){
    	
    	// 成功回调函数
    	var success = function(data){
    		
    		if(data.flag == '0') {
    			layer.alert("无结果",{
    				zIndex: layer.zIndex,
    				success: function(layero){
    					layer.setTop(layero);
    				}
    			});
    		} else {
		    	layer.open({
		    		title: '查看资源属性',
		    		area:["800px"],
		    		zIndex: layer.zIndex,
		    		content: data,
		    		btn: ['关闭']
		    	});
    		}
		};
		
    	// 请求数据
    	var data = {"id":id};
		ajaxHybrid(getFieldRealPath("detail"),data,success);
    };
    
    window['resourceField'] = resourceField;
})();
</script>
