<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="company.jsp"></jsp:include>
<div class="widget-box widget-color-orange">
	<div class="widget-body">
		<div class="widget-main">
			<div class="row">
				<div class="col-sm-12">
					<form id="searchForm" class="form-inline" role="form">
						<div class="form-group">
							<label for="code">编码</label>
							<input type="text" id="code" name="code" placeholder="编码" value="${searchForm.code}" />
						</div>
						<input type="hidden" name="startIndex" value="${startIndex}"/>
						<button onclick="company.search();" type="button" class="btn btn-success btn-sm">查询</button>
					</form>
				</div>
			</div>
			<hr>
		</div>
	</div>
</div>

<div class="table-header">
	<div class="row">
		<div class="col-sm-3">
			<i class="ace-icon fa fa-table"></i>
			查询结果
		</div>
		<div class="col-sm-2 pull-right">
			<div class="pull-right" onclick="javascript:alert(2323);" style="margin-right:10px;cursor:pointer;">
				<i class="ace-icon fa fa-trash-o bigger-160 media-middle"></i>
			</div>
			<div class="pull-right" onclick="javascript:company.add();" style="margin-right:10px;cursor:pointer;">
				<i class="fa fa-plus-circle bigger-160 media-middle"></i>
			</div>
		</div>
	</div>
</div>
<div class="row">
	<div class="col-xs-12">
		<!-- PAGE CONTENT BEGINS -->
		<div class="row">
			<div class="col-xs-12">
				<table id="sample-table-1" class="table table-striped table-bordered table-hover">
					<thead>
						<tr>
							<th class="center">
								<label class="position-relative">
									<input type="checkbox" class="ace" />
									<span class="lbl"></span>
								</label>
							</th>
							<th>编码</th>
							<th>名称</th>
							<th class="hidden-480">地址</th>

							<th>
								<i class="ace-icon fa fa-clock-o bigger-110 hidden-480"></i>
								类型
							</th>
							<th class="hidden-480">状态</th>

							<th></th>
						</tr>
					</thead>

					<tbody>
						<c:forEach items="${list}" var="r">
						<tr>
							<td class="center">
								<label class="position-relative">
									<input type="checkbox" class="ace" />
									<span class="lbl"></span>
								</label>
							</td>

							<td><c:out value="${r.name}" /></td>
							<td><c:out value="${r.code}" /></td>
							<td class="hidden-480"><c:out value="${r.address}" /></td>
							<td><c:out value="${r.email}" /></td>

							<td class="hidden-480">
								<span class="label label-sm label-warning">Expiring</span>
							</td>

							<td>
								<div class="hidden-sm hidden-xs btn-group">
									<button class="btn btn-xs btn-success">
										<i class="ace-icon fa fa-check bigger-120"></i>
									</button>

									<button class="btn btn-xs btn-info">
										<i class="ace-icon fa fa-pencil bigger-120"></i>
									</button>

									<button class="btn btn-xs btn-danger">
										<i class="ace-icon fa fa-trash-o bigger-120"></i>
									</button>

									<button class="btn btn-xs btn-warning">
										<i class="ace-icon fa fa-flag bigger-120"></i>
									</button>
								</div>

								<div class="hidden-md hidden-lg">
									<div class="inline position-relative">
										<button class="btn btn-minier btn-primary dropdown-toggle" data-toggle="dropdown" data-position="auto">
											<i class="ace-icon fa fa-cog icon-only bigger-110"></i>
										</button>

										<ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
											<li>
												<a href="#" class="tooltip-info" data-rel="tooltip" title="View">
													<span class="blue">
														<i class="ace-icon fa fa-search-plus bigger-120"></i>
													</span>
												</a>
											</li>

											<li>
												<a href="#" class="tooltip-success" data-rel="tooltip" title="Edit">
													<span class="green">
														<i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
													</span>
												</a>
											</li>

											<li>
												<a href="#" class="tooltip-error" data-rel="tooltip" title="Delete">
													<span class="red">
														<i class="ace-icon fa fa-trash-o bigger-120"></i>
													</span>
												</a>
											</li>
										</ul>
									</div>
								</div>
							</td>
						</tr>
						</c:forEach>
					</tbody>
				</table>
				<nav>
				  <ul class="pagination pull-right no-margin">
				    <li>
				      <a href="#">
				      	<i class="ace-icon fa fa-angle-double-left"></i>
				      </a>
				    </li>
				    <li><a href="#">1</a></li>
				    <li><a href="#">2</a></li>
				    <li><a href="#">3</a></li>
				    <li><a href="#">4</a></li>
				    <li><a href="#">5</a></li>
				    <li>
				      <a href="#">
				      	<i class="ace-icon fa fa-angle-double-right"></i>
				      </a>
				    </li>
				  </ul>
				</nav>
			</div><!-- /.span -->
		</div><!-- /.row -->
		<!-- PAGE CONTENT ENDS -->
	</div><!-- /.col -->
</div><!-- /.row -->
<script type="text/javascript">
$(document).on('click', 'th input:checkbox' , function(){
	var that = this;
	$(this).closest('table').find('tr > td:first-child input:checkbox')
	.each(function(){
		this.checked = that.checked;
		$(this).closest('tr').toggleClass('selected');
	});
});
</script>