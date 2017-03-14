<!DOCTYPE html> 
<html lang="en"> 
<head>
	<#include "/include.ftl" />
	<link href="${app.basePath}/static/css/backend/organ_manger.css" rel="stylesheet">
	<link href="${app.basePath}/static/js/lib/ztree/css/metroStyle/metroStyle.css" rel="stylesheet">

	<script type="text/javascript" src="${app.basePath}/static/js/lib/ztree/js/jquery.ztree.core.min.js"></script>
</head> 
<body>
    <div class="container">
        <div id="inner-hd">
            <div class="crumbs">
				<i class="crumbs-arrow"></i>
				<a href="javascript:;" class="crumbs-label">机构管理</a>
			</div>
        </div>

        <div id="inner-bd">
        	
            <div class="kv-group-outer">
		        <div class="kv-group clearfix">
		            <div class="kv-item kv-col-1">
		                <div class="item-lt">数据导入：</div>
		                <div class="item-rt">
		                    <a href="javascript:;" class="a-upload">
								<input type="file" name="" id="">选择文件
							</a>
							<span class="text-tip">未选择任何文件</span>
							<div class="button current">
								<span class="button-label">导入全部</span>
							</div>
							
							<div class="tip-area">
								<a href="javascript:;">内设机构人员信息关系年底关系模板下载</a>
							</div>
		                </div>
		            </div>
		            
		        </div>
		    </div>

            <div class="button-group">
		        <div class="button current add">
		            <i class="iconfont">&#xe620;</i>
		            <span class="button-label">新增</span>
		        </div>
		        <div class="button delete">
		            <i class="iconfont">&#xe609;</i>
		            <span class="button-label">删除</span>
		        </div>
				<div class="button sort">
		            <i class="iconfont">&#xe61a;</i>
		            <span class="button-label">部门排序</span>
		        </div>
		    </div>
			
			<table class="kv-table" style="margin-bottom: 5px;">
				<tbody>
					<tr>
						<td class="kv-label" style="width:20%;vertical-align: top;">
							<div id="ztree1" class="ztree"></div>
						</td>
						<td id="departmentListTd" class="kv-content" style="padding: 10px;vertical-align: top;">
						</td>
					</tr>
				</tbody>
			</table>
        </div>

        <div id="inner-ft">
            
        </div>
    </div>
    
    <#include "/backend/company/company_tree_js.ftl" />
    <#include "/backend/department/department_tpl.ftl" />
    <#include "/footer.ftl" />
    
	<script type="text/javascript">
		
		// 获取访问地址
		getRealPath = function(path){
			var basePath = "${app.basePath}/rbac/backend/department/";
			return basePath + path;
		};
	    
	    // 准备数据：页面需要的初始化数据，下拉列表
	    prepareData = function(){
			// 准备数据
			var result = {};
			
			var success = function(data){
				result['item'] = data.result.item;
			}
			
			ajaxJson(getRealPath("prepare"), {}, success);

			return result;
	    };
    
	    // 添加部门
		$('body').on('click', '.add', function(){
			
			// 准备数据
			pData = prepareData();
			var html = department.tpl.getAddTpl(pData);
			
			// 数据提交
			var submit = function(){
				
		    	// 成功回调
				var success = function(data){
					msgAlert(data);
					refresh();
				}
		    	
    			var options = ajaxOption(getRealPath("addForm"),success);
    			$(department.tpl.targetId).ajaxSubmit(options);
			};
			
	    	layer.open({
	    		title: '添加部门',
	    		area:["800px"],
	    		content: html,
	    		btn: ['添加', '关闭'],
	    		yes:function(index, layer){
	    			// 校验数据成功后提交
	    			validate($(department.tpl.targetId),inputError,submit);
	    		},
	    		success:function(){
	    	    	// 绑定页面校验
	    	    	bindValidate($(department.tpl.targetId));
	    		}
	    	});
		 });

	    // 删除部门
		$('body').on('click', '.delete', function(){
	    	// 获取所有选中的ids
	    	var ids = getSelections(department.tpl.docId + "_checkbox",department.tpl.docId + "_id");

	    	if(ids!="") {
				// 删除确认
				layer.confirm('确定要删除么？', {
					  btn: ['确定','关闭']
					}, function(){
				    	// 成功回调
						var success = function(data){
				    		msgAlert(data);
							refresh();
						}
						var data = {"ids":ids};
						ajaxJson(getRealPath("batchDelete"), data, success);
				});
	    	} else {
	    		layer.alert("请选择要删除的数据！");
	    	}
		 });
	    
	    // 部门排序
	    $('body').on('click', '.sort', function(){
			// 成功回调
			var success = function(data){
				var html = department.tpl.getSortTpl(data.result);
		    	layer.open({
		    		title: '部门排序',
					btn: ['保存','关闭'],
		    		area:["828px"],
				    content: html,
				    yes: function(){
				    	var ids = $("[name="+department.tpl.docSortId + "_id]");
				    	
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
				    		refresh();
				    	};
				    	
						// 数据提交
						ajaxJson(getRealPath("sort"),{"ids":data},success);
				    }
		    	});
			};
			
			// 查询条件
			var searchData = {"companyId" : companyTree.getCurrentId()};
			// 数据请求
			ajaxJson(getRealPath("list"),searchData,success);
	    });
		
	    // 刷新界面
	    refresh = function(){
			var success = function(data){
				// 刷新部门树
				var treeObj = companyTree.init("ztree1", eval(data));
				
				// 更新当前节点
				companyTree.selectCurrent("ztree1");
			};
			
	    	ajaxText(getRealPath("getCompanyTree"),{},success);
	    };
	    
	    // 树状结构查询
	    objectList = function(companyId){
	    	departmentList(companyId);
	    };
	    
		// 列表显示
		departmentList = function(companyId){
			if(companyId != undefined){
				// 查询条件
				var searchData = {"companyId" : companyId};
				
				// 成功回调
				var success = function(data){
					var html = department.tpl.getListTpl(data.result);
					$("#departmentListTd").html(html);
				};
				
				// 数据请求
				ajaxJson(getRealPath("list"),searchData,success);
			} else {
				var html = department.tpl.getListTpl();
				$("#departmentListTd").html(html);
			}
		};
		
		// 双击树节点
		objectDetail = function(id) {
		};
		
	    // 查看部门
	    detailDepartment = function(id){
	    	// 请求数据
	    	var data = {"id":id};
	    	
	    	// 成功回调函数
	    	var success = function(data){
	    		if(data.flag == '1') {
					var html = department.tpl.getDetailTpl(data.result.department);
			    	layer.open({
			    		title: '查看部门',
			    		area:["800px"],
			    		content: html,
			    		btn: ['关闭']
			    	});
	    		} else {
	    			layer.alert("无结果");
	    		}
			};
			
			ajaxJson(getRealPath("detail"),data,success);
	    };
	    
	    // 删除部门
	    deleteDepartment = function(id){
	    	var data = {"id":id};
			// 成功回调
	    	var success = function(data){
	    		msgAlert(data);
    			refresh();
			};
			
			// 删除确认
			layer.confirm('确定要删除么？', {
				  btn: ['确定','关闭']
				}, function(){
					ajaxJson(getRealPath("delete"), data, success);
			});
	    };
	    
	    // 选中当前行
	    selectCurrent = function(event){
	    	// 选中当前的
	    	var radio = $(event.target).parent().find(":radio");
	    	radio.prop("checked",true);
	    };
	    
	    // 排序部门
	    sortDepartment = function(flag){
	    	// 取消之前选中的
	    	var radioSelected = getSelectedRadio(department.tpl.docSortId + "_radio");
    		var tr = $(radioSelected).parent().parent();
    		if(radioSelected == null) {
    			parent.layer.alert("请选中要排序的行！");
    			return;
    		}
    		// 上移
    		if(flag==0) {
        		var prev = tr.prev();
        		if(prev.length==1) {
        			prev.before(tr);
        		} else {
        			parent.layer.alert("已到达最顶部，无法再移动！");
        		}
    		} else {
    			// 下移
        		var next = tr.next();
        		if(next.length==1) {
        			next.after(tr);
        		} else {
        			parent.layer.alert("已到达最底部，无法再移动！");
        		}
    		}
	    };
	    
	    // 修改部门
	    updateDepartment = function(id){

			var success = function(data){
				console.log(JSON.stringify(data));
				var pData = prepareData();
				// 准备数据
				var view =  $.extend(pData,data.result.department);
				
				var html = department.tpl.getUpdateTpl(view);
				
				// 提交数据
				var submit = function(){
	    			var options = ajaxOption(getRealPath("updateForm"),function(data){
	    				msgAlert(data);
	    				refresh();
	    			});
	    			$(department.tpl.targetId).ajaxSubmit(options);
				};
				
		    	layer.open({
		    		title: '修改部门',
		    		area: ["800px"],
		    		content: html,
		    		btn: ['修改', '关闭'],
		    		yes: function(index, layer){
		    			validate($(department.tpl.targetId),inputError,submit);
		    		},
		    		success: function(){
		    			$(department.tpl.targetId+"_type").val(data.result.department.type);
		    	    	// 页面校验
		    	    	bindValidate($(department.tpl.targetId));
		    		}
		    	});
			};
			
			// 请求明细数据
			var data = {"id":id};
			ajaxJson(getRealPath("detail"),data,success);
	    };
	    
	    $(document).ready(function(){
	    	refresh();
	    });
	</script>
</body> 
</html>
