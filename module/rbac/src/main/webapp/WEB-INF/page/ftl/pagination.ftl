<table style="width:100%;">
	<tr style="color:#0088cc;border-bottom: 1px solid #dadada;border-left: 1px solid #dadada">
		<td style="width:100px;padding:1px 3px 1px 5px;">
			<span>每页:</span>
			<select id="selectPages" onchange="pagination.changePageSize(event)" style="padding-left:3px; color:#0088cc; border:0; width: 60px; height:100%;margin:0; border-right:1px solid #dadada;border-left:1px solid #dadada">
				<option value="10">10条</option>
				<option value="20">20条</option>
				<option value="30">30条</option>
				<option value="40">40条</option>
			</select>
		</td>
		<td style="padding:1px 3px 1px 5px; border-right:1px solid #dadada;text-align: right;">
			<div style="display: inline-block;">总共:{{resultInfo.total}}条</div>
			<input type="hidden" name="pageNum" id="pageNum" value="{{resultInfo.pageNum}}" />
			<input type="hidden" name="pageSize" id="pageSize" value="{{resultInfo.pageSize}}" />
			<input type="hidden" name="pages" id="pages" value="{{resultInfo.pages}}" />
		</td>
		<td style="text-align: right;">
			<div class="pagination">
				<ul>
					<li><a href="javascript:pagination.homePage();${action}();">首页</a></li>
					<li><a href="javascript:pagination.toPagePre(${action});">上一页</a></li>
					{{#pagination.list}}
					<li {{#active}}class="active"{{/active}}>
						<a href="javascript:pagination.setPageNum('{{value}}');${action}();">{{value}}</a>
					</li>
					{{/pagination.list}}
					<li><a href="javascript:pagination.toPageNext(${action});">下一页</a></li>
					<li><a href="javascript:pagination.lastPage();${action}();">尾页</a></li>
					<li>
						<a href="javascript:pagination.toPageGo(${action});">转</a>
						<input type="text" id="goPageNum" value="{{resultInfo.pageNum}}" style="text-align:right; margin:0;padding:0 3px 0 0;height:34px;width:50px;" maxlength="6"/>
					</li>
				</ul>
			</div>
		</td>
	</tr>
</table>
<script type="text/javascript">
(function(){
	pagination = {};
	
	$(document).ready(function(){
		// 设定每页条数
		$("#selectPages").val($("#pageSize").val());
	});
	// 设置页码
	pagination.setPageNum = function(pageNum){
		$("#pageNum").val(pageNum);
	};
	
	// 获取数据
	pagination.getPageData = function(){
		var data = {};
		data.pageNum = $("#pageNum").val();
		data.pageSize = $("#pageSize").val();
		return data;
	};
	
	// 更改每页记录数
	pagination.changePageSize = function(event){
		var pageSize = $(event.target).val();
		$("#pageSize").val(pageSize);
		$("#pageNum").val(1);
	};
	// 上一页
	pagination.toPagePre = function(action) {
		if ($("#pageNum").val() != 1) {
			var pageNumber = parseInt($("#pageNum").val()) - 1;
			if (pageNumber >= 0) {
				$("#pageNum").val(pageNumber);
			}
			action();
		} else {
			parent.layer.alert("已到达第一页！");
		}
	};
	// 下一页
	pagination.toPageNext = function(action) {
		if ($("#pageNum").val() != $("#pages").val()
				&& $("pageNum").val() != 0) {
			$("#pageNum").val(parseInt($("#pageNum").val()) + 1);
			action();
		} else {
			parent.layer.alert("已到达最后一页！");
		}
	};
	// 首页
	pagination.homePage = function() {
		$("#pageNum").val(1);
	};
	// 尾页
	pagination.lastPage = function() {
		var pages = parseInt($("#pages").val());
		$("#pageNum").val(pages);
	};
	// 跳转
	pagination.toPageGo = function(action) {

		var currentPage = parseInt($("#goPageNum").val());
		var totalPages = parseInt($("#pages").val());

		// 判断转向页为是否为空
		if (isNaN(currentPage)) {
			parent.layer.alert("请输入正确的页码！");
			return false;
		}

		// 转向页大于总页数
		if (currentPage > totalPages || currentPage <= 0 && totalPages != 0) {
			parent.layer.alert("页码不存在，请重新输入！");
			return;
		} else {
			$("#pageNum").val(currentPage);
		}
		action();
	};
	
	window['pagination'] = pagination;
})();
</script>