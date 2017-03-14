<input type="text" name="parentName" onclick="department.selectParent(event)" value="{{parentName}}" style="width:150px;" rel="{{target_id}}" id="{{target_id}}_parentName" autocomplete="off" check="notNull"  />
<input type="hidden" name="parentId" id="{{target_id}}_parentId" value="{{parentId}}" />
<div id="{{target_id}}_parentTreeDiv" style="display: none; float: left; margin-left: -173px; margin-top: 35px; width: 160px; height: 0px;">
	<div style="background-color: white; position: absolute; width: 160px; min-height: 40px; max-height: 220px; overflow: auto; border: 1px solid red;">
		<div id="{{target_id}}_parentTree" class="ztree"></div>
	</div>
</div>
<div class="button current">
	<span class="button-label" rel="{{target_id}}_parentName" onclick="department.selectByInput(event)">选择</span>
</div>