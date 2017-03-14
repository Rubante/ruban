<!DOCTYPE html> 
<html lang="en"> 
<head>
	<#include "/include.ftl" />
	<link href="${app.basePath}/static/css/backend/person_manger.css" rel="stylesheet">
	<link href="${app.basePath}/static/js/lib/ztree/css/metroStyle/metroStyle.css" rel="stylesheet">

	<script type="text/javascript" src="${app.basePath}/static/js/lib/ztree/js/jquery.ztree.core.min.js"></script>
	
	<script type="text/javascript" src="${app.basePath}/static/js/lib/My97DatePicker/WdatePicker.js"></script>
</head> 
<body>
    <div class="container">
        <div id="inner-hd">
            <div class="crumbs">
				<i class="crumbs-arrow"></i>
				<a href="javascript:;" class="crumbs-label">人员管理</a>
			</div>
        </div>

        <div id="inner-bd">
        	<div class="tab-wraper">
        		<ul class="tab">
		           	<li class="current"><a href="${app.basePath}/rbac/backend/person/main">机构人员信息管理</a></li>
		           	<li><a href="query.html">人员账号查询</a></li>
		           	<li><a href="noaccount.html">未设置账号人员</a></li>
		           	<li><a href="sort.html">单位人员拍序</a></li>
		           	<li><a href="auth.html">人员权限设置</a></li>
	            </ul>
        	</div>

            <div class="kv-group-outer">
		        <div class="kv-group clearfix">
			        <div class="kv-item-wrap" style="max-width: 900px;">
			        	<div class="kv-item kv-col-1">
			                <div class="item-lt">部门内设机构：</div>
			                <div class="item-rt">
			                    <select>
			                        <option>不限</option>
			                    </select>
			                </div>
			            </div>
			            <div class="kv-item kv-col-2">
			                <div class="item-lt">岗位：</div>
			                <div class="item-rt">
			                    <select>
			                        <option>不限</option>
			                    </select>
			                </div>
			            </div>
			            <div class="kv-item kv-col-3">
			                <div class="item-lt">职级：</div>
			                <div class="item-rt">
			                   <select>
			                        <option>不限</option>
			                    </select>
			                </div>
			            </div>
			            <div class="kv-item">
			                <div class="item-lt">性别：</div>
			                <div class="item-rt">
			                   <select>
			                        <option>不限</option>
			                        <option>女</option>
			                        <option>男</option>
			                    </select>
			                </div>
			            </div>

			            <div class="kv-item kv-col-1">
			                <div class="item-lt">人员名称：</div>
			                <div class="item-rt">
			                   <input type="text" placeholder="填写人员名称" >
			                </div>
			            </div>

			            <div class="kv-item kv-col-2">
			                <div class="item-lt">职务：</div>
			                <div class="item-rt">
			                    <input type="text" placeholder="职务" >
			                </div>
			            </div>
			            <div class="kv-item kv-col-3">
			                <div class="item-lt">登录账号：</div>
			                <div class="item-rt">
			                    <input type="text" placeholder="职务" onClick="WdatePicker()">
			                </div>
			            </div>
			            <div class="kv-item kv-col-1">
			                <div class="item-lt">权限：</div>
			                <div class="item-rt">
			                    <label><input type="checkbox">单位领导</label>
			                    <label><input type="checkbox">单位领导</label>
			                    <label><input type="checkbox">单位领导</label>
			                    <label><input type="checkbox">单位领导</label>
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
		        <div class="button">
		            <i class="iconfont">&#xe621;</i>
		            <span class="button-label">修改</span>
		        </div>
		        <div class="button">
		            <i class="iconfont">&#xe613;</i>
		            <span class="button-label">密码重置</span>
		        </div>
		        <div class="button">
		            <i class="iconfont">&#xe609;</i>
		            <span class="button-label">删除</span>
		        </div>
		        <div class="button">
		            <i class="iconfont">&#xe617;</i>
		            <span class="button-label">上移</span>
		        </div>
		        <div class="button">
		            <i class="iconfont">&#xe629;</i>
		            <span class="button-label">下移</span>
		        </div>
		    </div>
			
			<table class="kv-table">
				<tbody>
					<tr>
						<td class="kv-label" style="width:230px;">
							<div id="ztree1" class="ztree"></div>
						</td>
						<td id="personListTd" class="kv-content" valign="top" style="padding: 10px;width:auto;vertical-align: top;">
						
						</td>
					</tr>
				</tbody>
				
			</table>
			
			
        </div>

        <div id="inner-ft">
            
        </div>
    </div>
	<#include "/backend/person/company_tree_js.ftl" />
	<#include "/backend/person/person_tpl.ftl" />

	<script type="text/javascript">
		// 获取访问地址
		getRealPath = function(path){
			var basePath = "${app.basePath}/rbac/backend/person/";
			return basePath + path;
		};
		
	    // 准备数据：页面需要的初始化数据,如下拉列表等
	    prepareData = function(){
			// 准备数据
			var result = {};
			
			var success = function(data){
				result['item'] = data.result.item;
			}
			
			ajaxJson(getRealPath("prepare"), {}, success);

			return result;
	    };
	    
	    // 删除图片,设置图片为删除状态
	    deletePhoto = function(photoId){
	    	$("#" + photoId + "_del").val("1");
	    	$("#" + photoId + "_img").attr("src","");
	    	$("#" + photoId + "_btn").hide();
	    };
	    
	    // 刷新界面
	    refresh = function(){
			var success = function(data){
				// 刷新组织机构树
				var treeObj = personCompanyTree.init("ztree1", eval(data));
				
				// 更新当前节点
				personCompanyTree.selectCurrent("ztree1");
			};
			
	    	ajaxText(getRealPath("getTree"),{},success);
	    };
	    
		// 根据机构查询人员列表
		personList = function(companyId){
			if(companyId != undefined){
				// 查询条件
				var searchData = {"companyId" : companyId};
				
				// 成功回调
				var success = function(data){
					var html = person.tpl.getListTpl(data.result);

					$("#personListTd").html(html);
				};
				
				
				// 数据请求
				ajaxJson(getRealPath("list"),searchData,success);
			} else {
				var html = person.tpl.getListTpl();

				$("#personListTd").html(html);
			}
		};
		
	    $('body').on('click', '.add', function(){
	    	var pData = prepareData();
	    	var html = person.tpl.getAddTpl(pData);
	    	
			// 数据提交
			var submit = function(){
				
		    	// 成功回调
				var success = function(data){
					msgAlert(data);
					refresh();
				}
		    	
    			var options = ajaxOption(getRealPath("addForm"),success);
    			$(person.getTarget()).ajaxSubmit(options);
			};
			
	    	layer.open({
	    		title: '添加人员',
	    		area:['1150px'],
	    		content: html,
	    		btn:['保存', '取消'],
	    		yes:function(index, layer){
	    			// 校验数据成功后提交
	    			validate($(person.getTarget()),inputError,submit);
	    		},
	    		success:function(){
	    	    	// 绑定页面校验
	    	    	bindValidate($(person.getTarget()));
	    	    	
	    	    	$(person.getTarget("photo")).change(function(){
	    	    		var imgSrc = URL.createObjectURL($(this)[0].files[0]);
	    	    		$(person.getTarget("photo_img")).attr("src", imgSrc);
	    	    		$(person.getTarget("photo_btn")).show();
	    	    		$(person.getTarget("photo_del")).val("0");
	    	    	});
	    		}
	    	});
	    });
	    
	    // 修改人员信息
	    updatePerson = function(id){

			var success = function(data){
				var pData = prepareData();
				// 准备数据
				var view =  $.extend(pData,data.result.person);
				
				var html = person.tpl.getUpdateTpl(view);
				
				// 提交数据
				var submit = function(){
	    			var options = ajaxOption(getRealPath("updateForm"),function(data){
	    				msgAlert(data);
	    				refresh();
	    			});
	    			$(person.getTarget()).ajaxSubmit(options);
				};
				
		    	layer.open({
		    		title: '修改人员信息',
		    		area: ["1150px"],
		    		content: html,
		    		btn: ['修改', '关闭'],
		    		yes: function(index, layer){
		    			validate($(person.getTarget()),inputError,submit);
		    		},
		    		success: function(){
		    			$(person.getTarget("type")).val(data.result.person.type);
		    	    	// 页面校验
		    	    	bindValidate($(person.getTarget()));
		    	    	
		    	    	$(person.getTarget("photo")).change(function(){
		    	    		var imgSrc = URL.createObjectURL($(this)[0].files[0]);
		    	    		$(person.getTarget("photo_img")).attr("src", imgSrc);
		    	    		$(person.getTarget("photo_btn")).show();
		    	    		$(person.getTarget("photo_del")).val("0");
		    	    	});
		    		}
		    	});
			};
			
			// 请求明细数据
			var data = {"id":id};
			ajaxJson(getRealPath("detail"),data,success);
	    };
	    
    	var zNodes = ${ztree};

    	personCompanyTree.init("ztree1", zNodes);
    	
    	$(document).ready(function(){
    		personList();
    		refresh();
    	});
	</script>
    
</body> 
</html>
