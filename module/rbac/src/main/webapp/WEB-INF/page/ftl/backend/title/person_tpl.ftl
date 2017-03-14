<script id="listTpl" type="text/template">
	<table class="grid">
		<thead>
			<tr>
				<th class="grid-checkbox"><input type="checkbox"></th>
				<th>序号</th>
				<th>部门内设机构</th>
				<th>姓名</th>
				<th>登录名</th>
				<th>姓别</th>
				<th>职务</th>
				<th>岗位</th>
				<th>职级</th>
				<th>操作</th>
			</tr>
		</thead>
		
		<tbody>
			{{#list}}
			<tr>
				<td class="grid-checkbox"><input type="checkbox"></td>
				<td>{{id}}</td>
				<td>{{companyName}}</td>
				<td>{{name}}</td>
				<td>{{title}}</td>
				<td>{{sex}}</td>
				<td>{{title}}</td>
				<td>{{job}}</td>
				<td></td>
				<td><a href="javascript:updatePerson('{{id}}')">修改</a></td>
			</tr>
			{{/list}}
		</tbody>
	</table>
</script>
<script id="addTpl" type="text/template">
<form id="{{target_id}}" action="${app.basePath}/rbac/person/addForm" method="post" enctype="multipart/form-data">
	<div class="add-manage">
		<table class="kv-table">
			<tbody>
				<tr>
					<td class="kv-label">
						姓名：
					</td>
					<td class="kv-content">
						<input type="text" name="name" maxlength="10">
					</td>
					<td class="kv-label">
						编号：
					</td>
					<td class="kv-content">
						<input type="text" name="code" maxlength="10">
					</td>
					<td class="kv-label">
						所属机构：
					</td>
					<td class="kv-content">
						<#include "/backend/person/company_select_tpl.ftl" />
					</td>
				</tr>
				<tr>
					<td class="kv-label">
						所属部门：
					</td>
					<td class="kv-content">
						
					</td>
					<td class="kv-label">
						职务：
					</td>
					<td class="kv-content">
						<select name="titleId">
							<option value="0">经理</option>
							<option value="1">主管</option>
						</select>
					</td>
					<td class="kv-label">
						岗位：
					</td>
					<td class="kv-content">
						<select name="jobId" class="btn-select">
							<option value="0">java开发者</option>
							<option value="1">linux工程师</option>
						</select>
					</td>
				</tr>
				<tr>
					<td class="kv-label">
						入职时间：
					</td>
					<td class="kv-content">
						<input type="text" name="entryDate" readonly="readonly" onClick="WdatePicker({crossFrame:false})" />
					</td>
					<td class="kv-label">
						离职时间：
					</td>
					<td class="kv-content">
						<input type="text" name="departureDate" readonly="readonly" onClick="WdatePicker({crossFrame:false})" />
					</td>
					<td class="kv-label">
						薪资：
					</td>
					<td class="kv-content">
						<input type="text" name="salary">
					</td>
				</tr>
				<tr>
					<td class="kv-label">
						身份证号：
					</td>
					<td class="kv-content">
						<input type="text" name="cardid" maxlength="18">
					</td>
					<td class="kv-label">
						住址：
					</td>
					<td class="kv-content" colspan="3">
						<input type="text" name="address" style="width:95%;" maxlength="60">
					</td>
				</tr>
				<tr>
					<td class="kv-label">
						邮箱：
					</td>
					<td class="kv-content">
						<input type="text" name="email" maxlength="20">
					</td>
					<td class="kv-label">
						出生日期：
					</td>
					<td class="kv-content">
						<input type="text" name="birthday" readonly="readonly" onClick="WdatePicker({crossFrame:false})" />
					</td>
					<td class="kv-label">
						性别：
					</td>
					<td class="kv-content">
						<select name="sex">
							{{#item}}
								<option value="{{code}}">{{value}}</option>
							{{/item}}
						</select>
					</td>
				</tr>
				<tr>
					<td class="kv-label">
						备注：
					</td>
					<td class="kv-content" colspan="3">
						<div class="textarea-wrap">
							<textarea name="memo" check="maxlength=200"></textarea>
						</div>
					</td>
					<td class="kv-label">
						照片：
					</td>
					<td class="kv-content">
						<a href="javascript:" class="file">
							<span><em>+</em>添加图片</span>
							<input id="{{target_id}}_photo" type="file" name="photo">
						</a>
						<img id="{{target_id}}_photo_img" height="90px" />
					</td>
				</tr>
			</tbody>
		</table>
	</div>
</form>
</script>
<script id="updateTpl" type="text/template">
<form id="{{target_id}}" action="${app.basePath}/rbac/person/updateForm" method="post" enctype="multipart/form-data">
	<div class="add-manage">
		<table class="kv-table">
			<tbody>
				<tr>
					<td class="kv-label">
						姓名：
					</td>
					<td class="kv-content">
						<input type="text" name="name" value="{{name}}" maxlength="10">
						<input type="hidden" name="id" value="{{id}}">
						<input type="hidden" name="holdLock" value="{{updateLock}}">
					</td>
					<td class="kv-label">
						编号：
					</td>
					<td class="kv-content">
						<input type="text" name="code" value="{{code}}" maxlength="10">
					</td>
					<td class="kv-label">
						所属机构：
					</td>
					<td class="kv-content">
						<#include "/backend/person/company_select_tpl.ftl" />
					</td>
				</tr>
				<tr>
					<td class="kv-label">
						所属部门：
					</td>
					<td class="kv-content">
						
					</td>
					<td class="kv-label">
						职务：
					</td>
					<td class="kv-content">
						<select name="titleId" value="{{titleId}}">
							<option value="0">经理</option>
							<option value="1">主管</option>
						</select>
					</td>
					<td class="kv-label">
						岗位：
					</td>
					<td class="kv-content">
						<select name="jobId" value="{{jobId}}">
							<option value="0">java开发者</option>
							<option value="1">linux工程师</option>
						</select>
					</td>
				</tr>
				<tr>
					<td class="kv-label">
						入职时间：
					</td>
					<td class="kv-content">
						<input type="text" name="entryDate" value="{{entryDate}}" readonly="readonly" onClick="WdatePicker({crossFrame:false})" />
					</td>
					<td class="kv-label">
						离职时间：
					</td>
					<td class="kv-content">
						<input type="text" name="departureDate" value="{{departureDate}}" readonly="readonly" onClick="WdatePicker({crossFrame:false})" />
					</td>
					<td class="kv-label">
						薪资：
					</td>
					<td class="kv-content">
						<input type="text" name="salary" value="{{salary}}" check="number" maxlength="10">
					</td>
				</tr>
				<tr>
					<td class="kv-label">
						身份证号：
					</td>
					<td class="kv-content">
						<input type="text" name="cardid" value="{{cardid}}" maxlength="18">
					</td>
					<td class="kv-label">
						住址：
					</td>
					<td class="kv-content" colspan="3">
						<input type="text" name="address" value="{{address}}" style="width:95%;" maxlength="60">
					</td>
				</tr>
				<tr>
					<td class="kv-label">
						邮箱：
					</td>
					<td class="kv-content">
						<input type="text" name="email" value="{{email}}" maxlength="20">
					</td>
					<td class="kv-label">
						出生日期：
					</td>
					<td class="kv-content">
						<input type="text" name="birthday" value="{{birthday}}" readonly="readonly" onClick="WdatePicker({crossFrame:false})" />
					</td>
					<td class="kv-label">
						性别：
					</td>
					<td class="kv-content">
						<select name="sex" value="{{sex}}" >
							{{#item}}
								<option value="{{code}}">{{value}}</option>
							{{/item}}
						</select>
					</td>
				</tr>
				<tr>
					<td class="kv-label">
						备注：
					</td>
					<td class="kv-content" colspan="3">
						<div class="textarea-wrap">
							<textarea name="memo" maxlength="200">{{memo}}</textarea>
						</div>
					</td>
					<td class="kv-label">
						照片：
					</td>
					<td class="kv-content" style="vertical-align:text-top;">
						<a href="javascript:" class="file">
							<span><em>+</em>添加图片</span>
							<input id="{{target_id}}_photo" type="file" name="photo">
						</a>
						<img id="{{target_id}}_photo_img" height="90px" src="data:image/png;base64,{{photo}}" />
						<a href="javascript:deletePhoto('{{target_id}}_photo')" id="{{target_id}}_photo_btn">删除</a>
						<input type="hidden" name="delPhoto" value="0" id="{{target_id}}_photo_del" />
					</td>
				</tr>
			</tbody>
		</table>
	</div>
</form>
</script>
<#include "/backend/person/company_select_js.ftl" />
<script type="text/javascript">
(function(){
	// 模板对象
	tpl = {};
	
	// 模板基础数据
	tpl.data = {'target_id':'personTarget','target_sort_id':'personSortTarget'};
	
	tpl.targetId = "#" + tpl.data.target_id;

	tpl.targetSortId = "#" + tpl.target_sort_id;
	
	tpl.docId = tpl.data.target_id;
	
	tpl.docSortId = tpl.data.target_sort_id;

	
	// 添加模板
	tpl.getAddTpl = function(data){
		// 合并数据
		data = $.extend(data,tpl.data);
		
		return renderHtml("addTpl",data);
	};
	
	// 修改模板
	tpl.getUpdateTpl = function(data) {
		// 合并数据
		data = $.extend(data,tpl.data);
		
		return renderHtml("updateTpl",data);
	};
	
	// 详情模板
	tpl.getDetailTpl = function(data) {
		// 合并数据
		data = $.extend(data,tpl.data);
		
		return renderHtml("detailTpl",data);
	};
	
	// 列表模板
	tpl.getListTpl = function(data){
		// 合并数据
		if(data) {
			data = $.extend(data,tpl.data);	
		} else {
			data = tpl.data;
		}
		
		return renderHtml("listTpl",data);
	};
	
	// 排序模板
	tpl.getSortTpl = function(data){
		// 合并数据
		if(data) {
			data = $.extend(data,tpl.data);	
		} else {
			data = tpl.data;
		}
		
		return renderHtml("sortTpl",data);
	};
	
	if(!window['person']){
		window['person'] = {};
	}
	
	// 获取target
	window['person'].getTarget = function(suffix){
		if(suffix) {
			return tpl.targetId + "_"+ suffix;	
		} else {
			return tpl.targetId;
		}
		
	};
	
	// 获取排序target
	window['person'].getSortTarget = function(suffix){
		return tpl.targetSortId + "_"+ suffix;
	};
	
	window['person'].tpl = tpl;
})();
</script>