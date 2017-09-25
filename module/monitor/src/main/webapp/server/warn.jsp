<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>鲁班监控平台-报警配置</title>
</head>
<script type="text/javascript">
//发送测试邮件
sendEmail = function(){
	$("#sendEmail").unbind("click");

	layer.msg("邮件请求已发送，请等待！", {time:3000});
	$.ajax({
		type : 'GET',
		url : '<s:url value="/warn/email/sendTest.do" />',
		dataType : "json",
		success : function(data) {
			$("#sendEmail").bind("click", sendEmail);
			if(data.flag == 0) {
				layer.msg(data.msg, {time:3000});
			} else {
				layer.msg(data.msg, {time:3000, icon:5});
			}
		},
		error : function(ex){
			$("#sendEmail").bind("click", sendEmail);
			layer.msg('发送失败！', {time:3000, icon:5});
		}
	});
};
//保存邮件配置
saveEmailConfig = function(){
	var smtp = $("#smtp").val();
	var port = $("#port").val();
	var account = $("#account").val();
	var password = $("#password").val();
	var testEmail = $("#testEmail").val();
	
	// 输入校验：邮件服务器
	if(smtp.trim() == '') {
		layer.tips('必须填写', '#smtp', {
			time: 2000,
			tips: [1,"red"]
		});
		return true;
	}
	
	// 输入校验：端口
	if(port.trim() == '') {
		layer.tips('必须填写', '#port', {
			time: 2000,
			tips: [1,"red"]
		});
		return true;
	}
	
	// 输入校验：邮箱账号
	if(account.trim() == '') {
		layer.tips('必须填写', '#account', {
			time: 2000,
			tips: [1,"red"]
		});
		return true;
	}
	
	// 输入校验：密码
	if(password.trim() == '') {
		layer.tips('必须填写', '#password', {
			time: 2000,
			tips: [1,"red"]
		});
		return true;
	}
	
	var data = {
		"smtp" : smtp,
		"port" : port,
		"account" : account,
		"password" : password,
		"testEmail" : testEmail
	};
	//提示框
	layer.confirm('确定要保存么？', {
	  btn: ['确定','取消'] //按钮
	}, function(index){
		$.ajax({
			type : 'POST',
			url : '<s:url value="/warn/email/config.do" />',
			data : data,
			dataType : "json",
			success : function(data) {
				if(data.flag == 0) {
					layer.msg(data.msg, {time:3000});
				} else {
					layer.msg(data.msg, {time:3000, icon:5});
				}
			},
			error : function(ex){
				layer.msg('保存失败！', {time:3000, icon:5});
			}
		});
	});	
};
// 保存短信平台配置
saveSmsConfig = function(){
	var appKey = $("#appKey").val();
	var secretKey = $("#secretKey").val();
	
	// 输入校验：appid
	if(appKey.trim() == '') {
		layer.tips('必须填写', '#appKey', {
			time: 2000,
			tips: [1,"red"]
		});
		return true;
	}
	
	// 输入校验：secretKey
	if(secretKey.trim() == '') {
		layer.tips('必须填写', '#secretKey', {
			time: 2000,
			tips: [1,"red"]
		});
		return true;
	}
	
	var data = {
		"appKey" : appKey,
		"secretKey" : secretKey
	};
	//提示框
	layer.confirm('确定要保存么？', {
	  btn: ['确定','取消'] //按钮
	}, function(index){
		$.ajax({
			type : 'POST',
			url : '<s:url value="/warn/sms/config.do" />',
			data : data,
			dataType : "json",
			success : function(data) {
				if(data.flag == 0) {
					layer.msg(data.msg, {time:3000});
				} else {
					layer.msg(data.msg, {time:3000, icon:5});
				}
			},
			error : function(ex){
				layer.msg('保存失败！', {time:3000, icon:5});
			}
		});
	});	
};
// 添加短信模板
addSms=function(){
	var scene = $("#scene").val();
	var signature = $("#signature").val();
	var templateId = $("#templateId").val();
	var content = $("#smsContent").val();
	
	// 输入校验：场景
	if(scene.trim() == '') {
		layer.tips('必须填写', '#scene', {
			time: 2000,
			tips: [1,"red"]
		});
		return true;
	}
	
	// 输入校验：签名
	if(signature.trim() == '') {
		layer.tips('必须填写', '#signature', {
			time: 2000,
			tips: [1,"red"]
		});
		return true;
	}
	
	// 输入校验：模板ID
	if(templateId.trim() == '') {
		layer.tips('必须填写', '#templateId', {
			time: 2000,
			tips: [1,"red"]
		});
		return true;
	}
	
	// 输入校验：模板内容
	if(content.trim() == '') {
		layer.tips('必须填写', '#content', {
			time: 2000,
			tips: [1,"red"]
		});
		return true;
	}
	
	var data = {
		"scene" : scene,
		"signature" : signature,
		"templateId" : templateId,
		"content" : content
	};
	//提示框
	layer.confirm('确定要修改么？', {
	  btn: ['确定','取消'] //按钮
	}, function(index){
		$.ajax({
			type : 'POST',
			url : '<s:url value="/warn/sms/add.do" />',
			data : data,
			dataType : "json",
			success : function(data) {
				if(data.flag == 0) {
					layer.msg(data.msg, {time:3000});
					var html = Mustache.to_html($("#template").html(), data.value);
					
					$("#templateList").append(html);
				} else {
					layer.msg(data.msg, {time:3000, icon:5});
				}
				$("#smsTemplateAdd").modal('hide');
			},
			error : function(ex){
				layer.msg('添加失败！', {time:3000, icon:5});
			}
		});
	});
};
//修改短信模板页面
updateSmsPage=function(id){

	$("#sceneUpdate").val($("#scene_"+id).val());
	$("#signatureUpdate").val($("#signature_"+id).val());
	$("#templateIdUpdate").val($("#templateId_"+id).val());
	$("#contentUpdate").val($("#content_"+id).val());
	$("#idUpdate").val(id);
	
	$("#smsTemplateUpdate").modal("show");
};
//修改短信模板
updateSms=function(){
	var scene = $("#sceneUpdate").val();
	var signature = $("#signatureUpdate").val();
	var templateId = $("#templateIdUpdate").val();
	var content = $("#contentUpdate").val();
	var id = $("#idUpdate").val();
	
	// 输入校验：场景
	if(scene.trim() == '') {
		layer.tips('必须填写', '#scene', {
			time: 2000,
			tips: [1,"red"]
		});
		return true;
	}
	
	// 输入校验：签名
	if(signature.trim() == '') {
		layer.tips('必须填写', '#signature', {
			time: 2000,
			tips: [1,"red"]
		});
		return true;
	}
	
	// 输入校验：模板ID
	if(templateId.trim() == '') {
		layer.tips('必须填写', '#templateId', {
			time: 2000,
			tips: [1,"red"]
		});
		return true;
	}
	
	// 输入校验：模板内容
	if(content.trim() == '') {
		layer.tips('必须填写', '#content', {
			time: 2000,
			tips: [1,"red"]
		});
		return true;
	}
	
	var data = {
		"id" : id,
		"scene" : scene,
		"signature" : signature,
		"templateId" : templateId,
		"content" : content
	};

	//提示框
	layer.confirm('确定要修改么？', {
	  btn: ['确定','取消'] //按钮
	}, function(index){
		
		$.ajax({
			type : 'POST',
			url : '<s:url value="/warn/sms/update.do" />',
			data : data,
			dataType : "json",
			success : function(data) {
				if(data.flag == 0) {
					layer.msg(data.msg, {time:3000});
					
					$("#scene_span_" + id).text(scene);
					$("#scene_" + id).val(scene);
					$("#signature_span_" + id).text(signature);
					$("#signature_" + id).val(signature);
					$("#templateId_span_" + id).text(templateId);
					$("#templateId_" + id).val(templateId);
					$("#content_span_" + id).text(content);
					$("#content_" + id).val(content);
					
					$("#smsTemplateUpdate").modal('hide');
					
					$("#templateList").append();
				} else {
					layer.msg(data.msg, {time:3000, icon:5});
				}
			},
			error : function(ex){
				layer.msg(ex, {time:3000, icon:5});
			}
		});	
	});
};
//删除短信模板
deleteSms=function(id){
	
	var data = {
		"id" : id
	};
	//提示框
	layer.confirm('确定要删除么？', {
	  btn: ['确定','取消'] //按钮
	}, function(index){
		
		$.ajax({
			type : 'POST',
			url : '<s:url value="/warn/sms/delete.do" />',
			data : data,
			dataType : "json",
			success : function(data) {
				if(data.flag == 0) {
					layer.msg(data.msg, {time:3000});
					
					$("#data_"+id).remove();
				} else {
					layer.msg(data.msg, {time:3000, icon:5});
				}
			},
			error : function(ex){
				layer.msg(ex, {time:3000, icon:5});
			}
		});	
	});
};
$(document).ready(function(){
	$("#sendEmail").bind("click", sendEmail);
});
</script>
<script id="template" type="text/template">
	<tr id="data_{{id}}">
		<td>
			<span id="scene_span_{{id}}">{{scene}}</span>
			<input type="hidden" value="{{scene}}" id="scene_{{id}}" />
		</td>
		<td>
			<span id="signature_span_{{id}}">{{signature}}</span>
			<input type="hidden" value="{{signature}}" id="signature_{{id}}" />
		</td>
		<td>
			<span id="templateId_span_{{id}}">${item.templateId}</span>
			<input type="hidden" value="{{templateId}}" id="templateId_{{id}}" />
		</td>
		<td>
			<span id="content_span_{{id}}">{{content}}</span>
			<input type="hidden" value="{{content}}" id="content_{{id}}" />
		</td>
		<td>
			<button type="button" class="btn btn-default" onclick="updateSmsPage('{{id}}')">修改</button>
			<button type="button" class="btn btn-warning" onclick="deleteSms('{{id}}')">删除</button>
		</td>
	</tr>
</script>
<body>
	<div class="page-header">
		<h3>报警设置</h3>
	</div>
	<ul class="nav nav-tabs">
	    <li class="active">
	        <a href="#sms" data-toggle="tab">短信</a>
	    </li>
	    <li><a href="#email" data-toggle="tab">邮箱</a></li>
	    <li><a href="#wechat" data-toggle="tab">微信</a></li>
	</ul>
	<div class="tab-content">
	    <div class="tab-pane fade in active" id="sms">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title">短信平台(阿里大于)</h3>
				</div>
				<div class="panel-body">
					<form role="form">
						<div class="form-group">
							<label for="name">appkey</label>
							<input type="text" value="${appKey}" class="form-control" id="appKey" placeholder="appkey">
						</div>
						<div class="form-group">
							<label for="name">secretKey</label>
							<input type="text" value="${secretKey}" class="form-control" id="secretKey" placeholder="secretKey">
						</div>
						<button type="button" class="btn btn-default" onclick="saveSmsConfig();">保存</button>
					</form>
				</div>
			</div>
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title">短信模板</h3>
				</div>
				<div class="panel-body">
					<div style="margin-bottom: 10px;">
						<div style="display: inline-block;">
							<button type="button" class="btn btn-info" data-toggle="modal" data-target="#smsTemplateAdd">添加短信模板</button>
						</div>
					</div>
					<table class="table">
						<thead>
							<tr>
								<th>场景名称</th>
								<th>签名</th>
								<th>模板ID</th>
								<th>短信内容</th>
								<th>修改时间</th>
							</tr>
						</thead>
						<tbody id="templateList">
							<c:forEach items="${templateList}" var="item">
								<tr id="data_${item.id}">
									<td>
										<span id="scene_span_${item.id}">${item.scene}</span>
										<input type="hidden" value="${item.scene}" id="scene_${item.id}" />
									</td>
									<td>
										<span id="signature_span_${item.id}">${item.signature}</span>
										<input type="hidden" value="${item.signature}" id="signature_${item.id}" />
									</td>
									<td>
										<span id="templateId_span_${item.id}">${item.templateId}</span>
										<input type="hidden" value="${item.templateId}" id="templateId_${item.id}" />
									</td>
									<td>
										<span id="content_span_${item.id}">${item.content}</span>
										<input type="hidden" value="${item.content}" id="content_${item.id}" />
									</td>
									<td>
										<button type="button" class="btn btn-default" onclick="updateSmsPage('${item.id}')">修改</button>
										<button type="button" class="btn btn-warning" onclick="deleteSms('${item.id}')">删除</button>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
	    </div>
	    <div class="tab-pane fade" id="email">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title">邮箱配置</h3>
				</div>
				<div class="panel-body">
					<form role="form">
						<div class="form-group">
							<label for="name">邮件发送服务器(SMTP)</label>
							<input type="text" value="${smtp}" class="form-control" id="smtp" placeholder="邮件发送服务器">
						</div>
						<div class="form-group">
							<label for="name">服务器(SMTP)端口</label>
							<input type="text" value="${port}" class="form-control" id="port" placeholder="port">
						</div>
						<div class="form-group">
							<label for="name">邮箱账号</label>
							<input type="text" value="${account}" class="form-control" id="account" placeholder="account">
						</div>
						<div class="form-group">
							<label for="name">邮箱密码/授权码</label>
							<input type="text" value="${password}" class="form-control" id="password" placeholder="password">
						</div>
						<div class="form-group">
							<div class="col-md-10 col-xs-10">
								<label for="name">测试接收的邮件地址</label>
								<input type="text" value="${testEmail}" class="form-control" id="testEmail" placeholder="testEmail">
							</div>
							<div class="col-md-2 col-xs-2">
								<label>&nbsp;</label>
								<button type="button" id="sendEmail" class="form-control btn btn-default">测试</button>		
							</div>
						</div>
						
						<button type="button" class="btn btn-default" style="margin-top:15px;" onclick="saveEmailConfig();">保存</button>
					</form>
				</div>
			</div>
	    </div>
	    <div class="tab-pane fade" id="wechat">
	        <p>jMeter 是一款开源的测试软件。它是 100% 纯 Java 应用程序，用于负载和性能测试。</p>
	    </div>
	</div>
	
	<!-- 短信模板添加 -->
	<div class="modal fade" id="smsTemplateAdd" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">添加短信模板</h4>
				</div>
				<div class="modal-body">
					<form role="form">
						<div class="form-group">
							<label for=scene>应用场景</label>
							<input type="text" class="form-control" id="scene" placeholder="请输入模板应用场景">
						</div>
						<div class="form-group">
							<label for="signature">签名</label>
							<input type="text" class="form-control" id="signature" placeholder="请输入签名" value="">
						</div>
						<div class="form-group">
							<label for="templateId">模板ID</label>
							<input type="text" class="form-control" id="templateId" placeholder="请输入模板ID" value="">
						</div>
						<div class="form-group">
							<label for="content">模板内容</label>
							<textarea class="form-control" id="smsContent" placeholder="请输入模板内容"></textarea>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" onclick="addSms()">保存</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal -->
	</div>
	<!-- 服务器修改 -->
	<div class="modal fade" id="smsTemplateUpdate" tabindex="-1" role="dialog" aria-labelledby="updateModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="updateModalLabel">修改短信模板内容</h4>
				</div>
				<div class="modal-body">
					<form role="form">
						<div class="form-group">
							<input type="hidden" id="idUpdate" value="" />
							<label for="sceneUpdate">应用场景</label>
							<input type="text" class="form-control" id="sceneUpdate" placeholder="请输入模板应用场景">
						</div>
						<div class="form-group">
							<label for="signatureUpdate">签名</label>
							<input type="text" class="form-control" id="signatureUpdate" placeholder="请输入签名" value="">
						</div>
						<div class="form-group">
							<label for="templateIdUpdate">模板ID</label>
							<input type="text" class="form-control" id="templateIdUpdate" placeholder="请输入模板ID" value="">
						</div>
						<div class="form-group">
							<label for="contentUpdate">模板内容</label>
							<textarea class="form-control" id="contentUpdate" placeholder="请输入模板内容"></textarea>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" onclick="updateSms()">保存</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal -->
	</div>
</body>
</html>