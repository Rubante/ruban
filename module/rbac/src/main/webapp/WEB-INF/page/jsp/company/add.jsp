<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div class="modal fade" id="myModal" tabindex="-1" role="dialog">
	<div class="modal-dialog" role="document">
		<div class="modal-content" style="border:solid 4px #438eb9;">
			<div class="modal-header" style="background:#438eb9;height:25px;">
				<button type="button" class="close" data-dismiss="modal" style="margin-top:-18px;">
					<span style="color:white;" aria-hidden="true">&times;</span>
				</button>
				<div class="modal-title" style="margin-top:-11px;color:white;">新建公司</div>
			</div>
			<div class="modal-body">
				<!-- PAGE CONTENT BEGINS -->
				<form:form modelAttribute="companyForm" class="form-horizontal" role="form">
					<!-- #section:elements.form -->
					<div class="form-group form-group-sm">
						<label class="col-sm-3 control-label no-padding-right" for="code"> 编码 </label>
		
						<div class="col-sm-9">
							<input type="text" id="code" name="code" placeholder="编码" class="col-xs-10 col-sm-5" />
							<form:errors path="code"></form:errors>
						</div>
					</div>
					<div class="form-group form-group-sm">
						<label class="col-sm-3 control-label no-padding-right" for="name"> 公司名 </label>
		
						<div class="col-sm-9">
							<input type="text" id="name" name="name" placeholder="公司名" class="col-xs-10 col-sm-8" />
						</div>
					</div>
		
					<div class="form-group form-group-sm">
						<label class="col-sm-3 control-label no-padding-right" for="address"> 地址 </label>
						
						<div class="col-sm-9">
							<input type="text" id="address" name="address" placeholder="地址" class="form-control" />
						</div>
					</div>
		
					<div class="form-group form-group-sm">
						<label class="col-sm-3 control-label no-padding-right" for="postcode"> 邮编 </label>
						
						<div class="col-sm-9">
							<input type="text" id="postcode" name="postcode" placeholder="邮编" class="col-xs-10 col-sm-5" />
						</div>
					</div>
		
					<div class="form-group form-group-sm">
						<label class="col-sm-3 control-label no-padding-right" for="title"> 抬头 </label>
						
						<div class="col-sm-9">
							<input type="text" id="title" name="title" placeholder="抬头" class="col-xs-10 col-sm-8" />
						</div>
					</div>
				</form:form>
			</div>
			<div class="modal-footer">
				<button onclick="addForm();" type="button" class="btn btn-primary btn-xs">保存</button>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
$(document).ready(function(){
	$("#myModal").modal('toggle');
	$("#myModal").draggable({
	    handle: ".modal-header"
	});

	// 居中
	$("#myModal").css({
		'margin-top': function () {
			var height = $(window).height()-$(this).height();
			if(height >0 ) {
				return height/ 2;	
			} else {
				return 0;
			}
			
		}
	});
});
</script>