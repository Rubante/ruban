<div style="white-space: nowrap;">
	<input type="text" name="companyName" onclick="company.selectCompany(event)" value="{{companyName}}" style="width:150px;" rel="{{target_id}}" id="{{target_id}}_companyName" autocomplete="off" check="notNull"  />
	<input type="hidden" name="companyId" id="{{target_id}}_companyId" value="{{companyId}}" />
	<div id="{{target_id}}_companyTreeDiv" style="display: none; float: left; margin-left: -165px; margin-top: 35px; width: 160px; height: 0px;padding-left: 2px; ">
		<div style="background-color: white; position: absolute; width: 160px; min-height: 40px; max-height: 220px; overflow: auto; border: 1px solid red;">
			<div id="{{target_id}}_companyTree" class="ztree"></div>
		</div>
	</div>
	<div class="button current">
		<span class="button-label" rel="{{target_id}}_companyName" onclick="company.clickCompanyInput(event)">选择</span>
	</div>
</div>