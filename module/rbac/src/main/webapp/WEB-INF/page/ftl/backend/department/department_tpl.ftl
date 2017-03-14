<script id="addTpl" type="text/template">
	<form id="{{target_id}}" action="${app.basePath}/rbac/department/addForm" method="post">
		<table class="kv-table">
			<tbody>
				<tr>
					<td class="kv-label">
						<i class="ruban-error-hint">名称</i>：
					</td>
					<td class="kv-content">
						<input type="text" name="name" check="notNull">
					</td>
					<td class="kv-label">
						简称：
					</td>
					<td class="kv-content">
						<input type="text" name="simpleName" >
					</td>
				</tr>
				<tr>
					<td class="kv-label">
						所属组织机构：
					</td>
					<td class="kv-content" style="height: 33px;white-space: nowrap;">
		               	<#include "/backend/company/company_select_tpl.ftl" />
					</td>
					<td class="kv-label">
						上级部门：
					</td>
					<td class="kv-content" style="height: 33px;white-space: nowrap;">
		               	<#include "/backend/department/department_select_tpl.ftl" />
					</td>
				</tr>
				<tr>
					<td class="kv-label">
						部门编码：
					</td>
					<td class="kv-content">
						<input type="text" name="code" check="notNull" maxlength="6">
					</td>
					<td class="kv-label">
						负责人：
					</td>
					<td class="kv-content">
						<input type="text" name="personName" check="notNull" maxlength="6">
					</td>
				</tr>
				<tr>
					<td class="kv-label">
						备注：
					</td>
					<td class="kv-content" colspan="3">
						<div class="textarea-wrap">
							<textarea name="memo"></textarea>
						</div>
					</td>
				</tr>
			</tbody>
		</table>
	</form>
</script>
<script id="updateTpl" type="text/template">
	<form id="{{target_id}}" action="${app.basePath}/rbac/department/updateForm" method="post">
		<table class="kv-table">
			<tbody>
				<tr>
					<td class="kv-label">
						<i class="ruban-error-hint">名称</i>：
					</td>
					<td class="kv-content">
						<input type="text" name="name" check="notNull" value="{{name}}">
						<input type="hidden" name="id" value="{{id}}">
						<input type="hidden" name="holdLock" value="{{updateLock}}">
					</td>
					<td class="kv-label">
						所属组织机构：
					</td>
					<td class="kv-content" style="height: 33px;white-space: nowrap;">
		               	<#include "/backend/company/company_select_tpl.ftl" />
					</td>
				</tr>
				<tr>
					<td class="kv-label">
						编码：
					</td>
					<td class="kv-content">
						<input type="text" name="code" check="notNull" maxlength="6" value="{{code}}">
					</td>
					<td class="kv-label">
						简称：
					</td>
					<td class="kv-content">
						<input type="text" name="simpleName" value="{{simpleName}}">
					</td>
				</tr>
				<tr>
					<td class="kv-label">
						备注：
					</td>
					<td class="kv-content" colspan="3">
						<div class="textarea-wrap">
							<textarea name="memo">{{memo}}</textarea>
						</div>
					</td>
				</tr>
			</tbody>
		</table>
	</form>
</script>
<script id="detailTpl" type="text/template">
	<table class="kv-table">
		<tbody>
			<tr>
				<td class="kv-label">
					<i>名称</i>：
				</td>
				<td class="kv-content">
					{{name}}
				</td>
				<td class="kv-label">
					所属组织机构：
				</td>
				<td class="kv-content">
	               	{{companyName}}
				</td>
			</tr>
			<tr>
				<td class="kv-label">
					编码：
				</td>
				<td class="kv-content">
					{{code}}
				</td>
				<td class="kv-label">
					简称：
				</td>
				<td class="kv-content" colspan="3">
					{{simpleName}}
				</td>
			</tr>
			<tr>
				<td class="kv-label">
					备注：
				</td>
				<td class="kv-content" colspan="3">
					<pre>{{memo}}</pre>
				</td>
			</tr>
			<tr>
			</tr>
		</tbody>
	</table>
</script>
<script id="listTpl" type="text/template">
	<table class="grid">
		<thead>
			<tr>
				<th class="grid-checkbox"><input type="checkbox" onclick="checkedByName(event,'{{target_id}}_checkbox')"></th>
				<th>序号</th>
				<th>部门名称</th>
				<th>负责人</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			{{#list}}
			<tr ondblclick="detailCompany('{{id}}');">
				<td class="grid-checkbox">
					<input type="checkbox" name="{{target_id}}_checkbox" />
					<input type="hidden" name="{{target_id}}_id" value="{{id}}"/>
				</td>
				<td>{{orderCode}}</td>
				<td>{{name}}</td>
				<td>{{personName}}</td>
				<td>
					<a href="javascript:detailDepartment('{{id}}');">查看</a>
					<a href="javascript:updateDepartment('{{id}}');">修改</a>
					<a href="javascript:deleteDepartment('{{id}}');">删除</a>
				</td>
			</tr>
			{{/list}}
		</tbody>
	</table>
</script>
<script id="sortTpl" type="text/template">
     <div class="button-group">
        <div class="button" onclick="sortDepartment(0)">
            <i class="iconfont">&#xe617;</i>
            <span class="button-label">上移</span>
        </div>
        <div class="button" onclick="sortDepartment(1)">
            <i class="iconfont">&#xe629;</i>
            <span class="button-label">下移</span>
        </div>
    </div>
	<table class="grid">
		<thead>
			<tr>
				<th class="grid-checkbox"></th>
				<th>序号</th>
				<th>名称</th>
				<th>类型</th>
			</tr>
		</thead>
		<tbody>
			{{#list}}
			<tr onclick="selectCurrent(event);">
				<td class="grid-checkbox">
					<input type="radio" name="{{target_sort_id}}_radio" />
					<input type="hidden" name="{{target_sort_id}}_id" value="{{id}}"/>
				</td>
				<td>{{orderCode}}</td>
				<td>{{name}}</td>
				<td>{{typeName}}</td>
			</tr>
			{{/list}}
		</tbody>
	</table>
</script>
<#include "/backend/company/company_select_js.ftl" />
<#include "/backend/department/department_select_js.ftl" />
<script type="text/javascript">
(function(){
	// 模板对象
	tpl = {};
	
	// 模板基础数据
	tpl.data = {'target_id':'departmentTarget','target_sort_id':'departmentSortTarget'};
	
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
	
	if(!window['department']){
		window['department'] = {};
	}
	
	window['department'].tpl = tpl;
})();
</script>