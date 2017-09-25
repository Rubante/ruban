<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>鲁班监控平台-管理员信息</title>
</head>
<script type="text/javascript">
//启用用户
startUser = function(event) {

	id = event.data.id;
	
	var data = {
		"id" : id	
	};
	
	//提示框
	layer.confirm('确定要启用该用户么？', {
	  btn: ['确定','取消'] //按钮
	}, function(index){

		$.ajax({
			type : 'POST',
			url : '<s:url value="/user/start.do" />',
			dataType : "json",
			data : data,
			success : function(data) {
				if(data.flag == 0) {
					layer.msg(data.msg, {time:3000});
				} else {
					layer.msg(data.msg, {time:3000, icon:5});
				}

				$("#start_" + id).removeClass("btn-success");
				$("#start_" + id).addClass("btn-disabled");
				$("#stop_" + id).removeClass("btn-disabled");
				$("#stop_" + id).addClass("btn-warning");
				
				$("#start_" + id).unbind("click");
				$("#stop_" + id).bind("click", {"id" : id}, stopUser);
			},
			error : function(ex){
				layer.msg(ex, {time:3000, icon:5});
			}
		});
	});
};
//禁用用户
stopUser = function(event) {

	id = event.data.id;
	var data = {
		"id" : id	
	};
	
	//提示框
	layer.confirm('确定要禁用该用户么？', {
	  btn: ['确定','取消'] //按钮
	}, function(index){

		$.ajax({
			type : 'POST',
			url : '<s:url value="/user/stop.do" />',
			dataType : "json",
			data : data,
			success : function(data) {
				if(data.flag == 0) {
					layer.msg(data.msg, {time:3000});
				} else {
					layer.msg(data.msg, {time:3000, icon:5});
				}
				
				$("#stop_" + id).removeClass("btn-warning");
				$("#stop_" + id).addClass("btn-disabled");
				$("#start_" + id).removeClass("btn-disabled");
				$("#start_" + id).addClass("btn-success");
				
				$("#stop_" + id).unbind("click");
				$("#start_" + id).bind("click", {"id" : id}, startUser);
			},
			error : function(ex){
				layer.msg(ex, {time:3000, icon:5});
			}
		});
	});
};
// 添加用户
addUser=function(){	
	var username = $("#username").val();
	var name = $("#name").val();
	var password = $("#password").val();
	var confirmPassword = $("#confirmPassword").val();
	var tel = $("#tel").val();
	var telChk = $("#telState").bootstrapSwitch("state");
	var telState = telChk?"1":"0";
	var email = $("#email").val();
	var emailChk = $("#emailState").bootstrapSwitch("state");
	var emailState = emailChk?"1":"0";
	var wechat = $("#wechat").val();
	var wechatChk = $("#wechatState").bootstrapSwitch("state");
	var wechatState = wechatChk?"1":"0";
	
	var qq = $("#qq").val();
	
	// 输入校验：用户名
	if(username.trim() == '') {
		layer.tips('必须填写', '#username', {
			time: 2000,
			tips: [1,"red"]
		});
		return true;
	}
	
	// 输入校验：姓名
	if(name.trim() == '') {
		layer.tips('必须填写', '#name', {
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
	
	// 输入校验：密码一致性
	if(password != confirmPassword) {
		layer.tips('密码不一致', '#confirmPassword', {
			time: 2000,
			tips: [1,"red"]
		});
		return true;
	}
	
	// 输入校验：电话
	if(tel.trim() == '') {
		layer.tips('必须填写', '#tel', {
			time: 2000,
			tips: [1,"red"]
		});
		return true;
	}
	
	// 输入校验：邮箱
	if(email.trim() == '') {
		layer.tips('必须填写', '#email', {
			time: 2000,
			tips: [1,"red"]
		});
		return true;
	}
	
	var data = {
		"username" : username,
		"name" : name,
		"password" : password,
		"tel" : tel,
		"telState" : telState,
		"email" : email,
		"emailState" : emailState,
		"wechat" : wechat,
		"wechatState" : wechatState,
		"qq" : qq
	};

	$.ajax({
		type : 'POST',
		url : '<s:url value="/user/add.do" />',
		data : data,
		dataType : "json",
		success : function(data) {
			if(data.flag == 0) {
				layer.msg(data.msg, {time:3000});
				var html = Mustache.to_html($("#template").html(), data.value);
				
				$("#userList").append(html);
				
				$("#start_" + data.value.id).bind("click", {"id": data.value.id}, startUser);
				
			} else {
				layer.msg(data.msg, {time:3000, icon:5});
			}
			$("#userAddModal").modal('hide');
		},
		error : function(ex){
			layer.msg('添加失败！', {time:3000, icon:5});
		}
	});
};
//修改用户页面
updateUserPage=function(id){
	
	$("#usernameUpdate").val($("#username_"+id).val());
	$("#nameUpdate").val($("#name_"+id).val());
	$("#telUpdate").val($("#tel_"+id).val());

	// 状态设定
	if($("#telState_" + id).val() == "1"){
		$("#telStateUpdate").bootstrapSwitch("state", true);
	} else {
		$("#telStateUpdate").bootstrapSwitch("state", false);
	}
	
	$("#emailUpdate").val($("#email_"+id).val());
	
	// 状态设定
	if($("#emailState_" + id).val() == "1"){
		$("#emailStateUpdate").bootstrapSwitch("state", true);
	} else {
		$("#emailStateUpdate").bootstrapSwitch("state", false);
	}
	
	$("#wechatUpdate").val($("#wechat_"+id).val());

	// 状态设定
	if($("#wechatState_" + id).val() == "1"){
		$("#wechatStateUpdate").bootstrapSwitch("state", true);
	} else {
		$("#wechatStateUpdate").bootstrapSwitch("state", false);
	}
	
	$("#qqUpdate").val($("#qq_"+id).val());
	
	$("#idUpdate").val(id);
	
	$("#userUpdateModal").modal("show");
};
// 报警设置
updateWarnPage = function(id){
	$("#nameWarn").val($("#name_"+id).val());
	$("#userWarnModal").modal("show");
};
// 保存用户所属配置
updateWarn = function(){
	var cpuWarnChk = $("#cpuWarn").bootstrapSwitch("state");
	var cpuWarn = cpuWarnChk?"1":"0";
	var hitWarnChk = $("#hitWarn").bootstrapSwitch("state");
	var hitWarn = hitWarnChk?"1":"0";
	
	var data = {
		"cpuWarn" : cpuWarn,
		"hitWarn" : hitWarn
	};
	
	console.log(data);
	
	//提示框
	layer.confirm('确定要修改么？', {
	  btn: ['确定','取消'] //按钮
	}, function(index){
		
		$.ajax({
			type : 'POST',
			url : '<s:url value="/user/warn.do" />',
			data : data,
			dataType : "json",
			success : function(data) {
				if(data.flag == 0) {
					layer.msg(data.msg, {time:3000});
					
					$("#userWarnModal").modal('hide');
					
					$("#userList").append();
				} else {
					layer.msg(data.msg, {time:3000, icon:5});
				}
			},
			error : function(ex){
				layer.msg(ex, {time:3000, icon:5});
			}
		});	
	});
}
//修改用户
updateUser=function(){
	var id = $("#idUpdate").val();
	var username = $("#usernameUpdate").val();
	var name = $("#nameUpdate").val();
	var tel = $("#telUpdate").val();
	var telChk = $("#telStateUpdate").bootstrapSwitch("state");
	var telState = telChk?"1":"0";
	var email = $("#emailUpdate").val();
	var emailChk = $("#emailStateUpdate").bootstrapSwitch("state");
	var emailState = emailChk?"1":"0";
	var wechat = $("#wechatUpdate").val();
	var wechatChk = $("#wechatStateUpdate").bootstrapSwitch("state");
	var wechatState = wechatChk?"1":"0";
	var qq = $("#qqUpdate").val();
	
	// 输入校验：用户名
	if(username.trim() == '') {
		layer.tips('必须填写', '#usernameUpdate', {
			time: 2000,
			tips: [1,"red"]
		});
		return true;
	}
	
	// 输入校验：姓名
	if(name.trim() == '') {
		layer.tips('必须填写', '#nameUpdate', {
			time: 2000,
			tips: [1,"red"]
		});
		return true;
	}
	
	// 输入校验：电话
	if(tel.trim() == '') {
		layer.tips('必须填写', '#telUpdate', {
			time: 2000,
			tips: [1,"red"]
		});
		return true;
	}
	
	// 输入校验：邮箱
	if(email.trim() == '') {
		layer.tips('必须填写', '#emailUpdate', {
			time: 2000,
			tips: [1,"red"]
		});
		return true;
	}

	var data = {
		"id" : id,
		"username" : username,
		"name" : name,
		"tel" : tel,
		"telState" : telState,
		"email" : email,
		"emailState" : emailState,
		"wechat" : wechat,
		"wechatState" : wechatState,
		"qq" : qq
	};

	//提示框
	layer.confirm('确定要修改么？', {
	  btn: ['确定','取消'] //按钮
	}, function(index){
		
		$.ajax({
			type : 'POST',
			url : '<s:url value="/user/update.do" />',
			data : data,
			dataType : "json",
			success : function(data) {
				if(data.flag == 0) {
					layer.msg(data.msg, {time:3000});
					
					$("#username_span_" + id).text(username);
					$("#username_" + id).val(username);
					$("#name_span_" + id).text(name);
					$("#name_" + id).val(name);
					$("#tel_span_" + id).text(tel);
					$("#tel_" + id).val(tel);
					$("#telState_span_" + id).text(telState);
					$("#telState_" + id).val(telState);
					$("#email_span_" + id).text(email);
					$("#email_" + id).val(email);
					$("#emailState_span_" + id).text(emailState);
					$("#emailState_" + id).val(emailState);
					$("#wechat_span_" + id).text(wechat);
					$("#wechat_" + id).val(wechat);
					$("#wechatState_span_" + id).text(wechatState);
					$("#wechatState_" + id).val(wechatState);
					$("#qq_span_" + id).text(qq);
					$("#qq_" + id).val(qq);
					
					$("#userUpdateModal").modal('hide');
					
					$("#userList").append();
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
//修改密码页面
updatePasswordPage=function(id){
	$("#usernamePassUpdate").val($("#username_"+id).val());
	
	$("#namePassUpdate").val($("#name_"+id).val());
	
	$("#passwordIdUpdate").val(id);
	
	$("#passwordUpdateModal").modal("show");
};
//修改密码
updatePassword=function(){
	var id = $("#passwordIdUpdate").val();
	var password = $("#passwordUpdate").val();
	var confirmPassword = $("#confirmPasswordUpdate").val();
	
	// 输入校验：密码
	if(password.trim() == '') {
		layer.tips('必须填写', '#passwordUpdate', {
			time: 2000,
			tips: [1,"red"]
		});
		return true;
	}
	
	// 输入校验：密码一致性
	if(password != confirmPassword) {
		layer.tips('密码不一致', '#confirmPasswordUpdate', {
			time: 2000,
			tips: [1,"red"]
		});
		return true;
	}

	
	var data = {
		"id" : id,
		"password" : password
	};

	//提示框
	layer.confirm('确定要修改密码么？', {
	  btn: ['确定','取消'] //按钮
	}, function(index){
		
		$.ajax({
			type : 'POST',
			url : '<s:url value="/user/updatePassword.do" />',
			data : data,
			dataType : "json",
			success : function(data) {
				if(data.flag == 0) {
					layer.msg(data.msg, {time:3000});
					
					$("#passwordUpdateModal").modal('hide');
					
					$("#userList").append();
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
//删除用户
deleteUser=function(id){
	
	var data = {
		"id" : id
	};
	//提示框
	layer.confirm('确定要删除么？', {
	  btn: ['确定','取消'] //按钮
	}, function(index){
		
		$.ajax({
			type : 'POST',
			url : '<s:url value="/user/delete.do" />',
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
	$("[type='checkbox']").bootstrapSwitch();
	<c:forEach items="${list}" var="item">
		<c:if test='${item.state==0}'>
			$("#start_" + ${item.id}).bind("click", {"id":${item.id}}, startUser);
		</c:if>
		<c:if test='${item.state==1}'>
			$("#stop_" + ${item.id}).bind("click", {"id":${item.id}}, stopUser);
		</c:if>
	</c:forEach>
});
</script>
<script id="template" type="text/template">
	<tr id="data_{{id}}">
		<td><span id="username_span_{{id}}">{{username}}</span><input type="hidden" value="{{username}}" id="username_{{id}}" /></td>
		<td><span id="name_span_{{id}}">{{name}}</span><input type="hidden" value="{{name}}" id="name_{{id}}" /></td>
		<td>
			<span id="tel_span_{{id}}">{{tel}}</span>
			<input type="hidden" value="{{tel}}" id="tel_{{id}}" />
			<input type="hidden" value="{{telState}}" id="telState_{{id}}" />
		</td>
		<td>
			<span id="email_span_{{id}}">{{email}}</span>
			<input type="hidden" value="{{email}}" id="email_{{id}}" />
			<input type="hidden" value="{{emailState}}" id="emailState_{{id}}" />
		</td>
		<td>
			<span id="wechat_span_{{id}}">{{wechat}}</span>
			<input type="hidden" value="{{wechat}}" id="wechat_{{id}}" />
			<input type="hidden" value="{{wechatState}}" id="wechatState_{{id}}" />
		</td>
		<td><span id="qq_span_{{id}}">{{qq}}</span><input type="hidden" value="{{qq}}" id="qq_{{id}}" /></td>
		<td>
			<button type="button" class="btn btn-info" onclick="updateUserPage('{{id}}')">修改信息</button>
			<button type="button" class="btn btn-info" onclick="updatePasswordPage('{{id}}')">修改密码</button>
			<button type="button" class="btn btn-warning" onclick="deleteUser('{{id}}')">删除</button>
			<button type="button" id="start_{{id}}" class="btn btn-success">启用</button>
			<button type="button" id="stop_{{id}}" class="btn btn-disabled">禁用</button>
		</td>
	</tr>
</script>
<body>
	<div class="page-header">
		<h3>用户管理</h3>
	</div>

	<div class="panel panel-default">
		<div class="panel-heading">
			<h3 class="panel-title">用户列表</h3>
		</div>
		<div class="panel-body">
			<div style="margin-bottom: 10px;">
				<div style="display: inline-block;"><button type="button" class="btn btn-info" data-toggle="modal" data-target="#userAddModal">添加用户</button></div>
			</div>
			<table class="table">
				<thead>
					<tr>
						<th>登陆账号</th>
						<th>姓名</th>
						<th>手机</th>
						<th>邮箱</th>
						<th>微信</th>
						<th>QQ</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody id="userList">
					<c:forEach items="${list}" var="item">
						<tr id="data_${item.id}">
							<td>
								<span id="username_span_${item.id}">${item.username}</span>
								<input type="hidden" value="${item.username}" id="username_${item.id}" />
							</td>
							<td>
								<span id="name_span_${item.id}">${item.name}</span>
								<input type="hidden" value="${item.name}" id="name_${item.id}" />
							</td>
							<td>
								<span id="tel_span_${item.id}">${item.tel}</span>
								<input type="hidden" value="${item.tel}" id="tel_${item.id}" />
								<input type="hidden" value="${item.telState}" id="telState_${item.id}" />
							</td>
							<td>
								<span id="email_span_${item.id}">${item.email}</span>
								<input type="hidden" value="${item.email}" id="email_${item.id}" />
								<input type="hidden" value="${item.emailState}" id="emailState_${item.id}" />
							</td>
							<td>
								<span id="wechat_span_${item.id}">${item.wechat}</span>
								<input type="hidden" value="${item.wechat}" id="wechat_${item.id}" />
								<input type="hidden" value="${item.wechatState}" id="wechatState_${item.id}" />
							</td>
							<td>
								<span id="qq_span_${item.id}">${item.qq}</span>
								<input type="hidden" value="${item.qq}" id="qq_${item.id}" />
							</td>
							<td>
								<button type="button" class="btn btn-info" onclick="updateUserPage('${item.id}')">修改信息</button>
								<button type="button" class="btn btn-info" onclick="updateWarnPage('${item.id}')">报警设置</button>
								<button type="button" class="btn btn-info" onclick="updatePasswordPage('${item.id}')">修改密码</button>
								<button type="button" class="btn btn-warning" onclick="deleteUser('${item.id}')">删除</button>
								<button type="button" id="start_${item.id}" class="btn <c:if test='${item.state==0}'>btn-success</c:if> <c:if test='${item.state==0}'>btn-disabled</c:if>">启用</button>
								<button type="button" id="stop_${item.id}" class="btn <c:if test='${item.state==1}'>btn-warning</c:if> <c:if test='${item.state==1}'>btn-disabled</c:if>">禁用</button>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>

	<!-- 用户添加 -->
	<div class="modal fade" id="userAddModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">添加后台用户</h4>
				</div>
				<div class="modal-body">
					<form role="form">
						<div class="form-group">
							<label for="username">用户名(登陆账号)</label>
							<input type="text" class="form-control" id="username" maxlength="25" placeholder="请输入用户名">
						</div>
						<div class="form-group">
							<label for="username">姓名</label>
							<input type="text" class="form-control" id="name" maxlength="10" placeholder="请输入姓名">
						</div>
						<div class="form-group">
							<div class="col-md-6 col-xs-12">
								<label for="password">密码</label>
								<input type="password" class="form-control" maxlength="10" id="password" placeholder="请输入密码" value="">
							</div>
							<div class="col-md-6 col-xs-12">
								<label for="confirmPassword">确认密码</label>
								<input type="password" class="form-control" maxlength="10" id="confirmPassword" placeholder="请输入确认密码" value="">
							</div>
						</div>
						
						<div class="form-group">
							<div class="col-md-8 col-xs-12">
								<label for="tel">手机号</label>
								<input type="tel" class="form-control" id="tel" maxlength="11" placeholder="请输入手机号" value="">
							</div>
							<div class="col-md-4 col-xs-12">
								<label for="tel">短信报警</label>
								<div class="switch" data-on="success" data-off="warning">
								    <input type="checkbox" id="telState" />
								</div>
							</div>
						</div>
						<div class="form-group">
							<div class="col-md-8 col-xs-12">
								<label for="email">邮箱</label>
								<input type="text" class="form-control" id="email" maxlength="25" placeholder="请输入邮箱" value="">
							</div>
							<div class="col-md-4 col-xs-12">
								<label for="tel">邮件报警</label>
								<div class="switch" data-on="success" data-off="warning">
								    <input type="checkbox" id="emailState" />
								</div>
							</div>
						</div>
						<div class="form-group">
							<div class="col-md-8 col-xs-12">
								<label for="wechat">微信</label>
								<input type="text" class="form-control" id="wechat" maxlength="25" placeholder="请输入微信号" value="">
							</div>
							<div class="col-md-4 col-xs-12">
								<label for="tel">微信报警</label>
								<div class="switch" data-on="success" data-off="warning">
								    <input type="checkbox" id="wechatState" />
								</div>
							</div>
						</div>
						<div class="form-group">
							<label for="qq">QQ</label>
							<input type="tel" class="form-control" id="qq" maxlength="13" placeholder="请输入QQ号" value="">
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" onclick="addUser()">保存</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal -->
	</div>
	<!-- 用户修改 -->
	<div class="modal fade" id="userUpdateModal" tabindex="-1" role="dialog" aria-labelledby="updateModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="updateModalLabel">修改用户信息</h4>
				</div>
				<div class="modal-body">
					<form role="form">
						<div class="form-group">
							<label for="username">登陆账号</label>
							<input type="hidden" id="idUpdate" value="" />
							<input type="text" readonly="readonly" class="form-control" maxlength="25" id="usernameUpdate" placeholder="请输入登陆账号">
						</div>
						<div class="form-group">
							<label for="username">姓名</label>
							<input type="text" class="form-control" maxlength="10" id="nameUpdate" placeholder="请输入姓名">
						</div>
						<div class="form-group">
							<div class="col-md-8 col-xs-12">
								<label for="tel">手机号</label>
								<input type="tel" class="form-control" maxlength="11" id="telUpdate" placeholder="请输入手机号" value="">
							</div>
							<div class="col-md-4 col-xs-12">
								<label for="tel">短信报警</label>
								<div class="switch" data-on="success" data-off="warning">
								    <input id="telStateUpdate" type="checkbox" checked="checked" />
								</div>
							</div>
						</div>
						<div class="form-group">
							<div class="col-md-8 col-xs-12">
								<label for="email">邮箱</label>
								<input type="text" class="form-control" maxlength="25" id="emailUpdate" placeholder="请输入邮箱" value="">
							</div>
							<div class="col-md-4 col-xs-12">
								<label for="tel">邮件报警</label>
								<div class="switch" data-on="success" data-off="warning">
								    <input id="emailStateUpdate" type="checkbox" checked="checked" />
								</div>
							</div>
						</div>
						<div class="form-group">
							<div class="col-md-8 col-xs-12">
								<label for="wechat">微信</label>
								<input type="text" class="form-control" maxlength="25" id="wechatUpdate" placeholder="请输入微信号" value="">
							</div>
							<div class="col-md-4 col-xs-12">
								<label for="tel">微信报警</label>
								<div class="switch" data-on="success" data-off="warning">
								    <input id="wechatStateUpdate" type="checkbox" checked="checked" />
								</div>
							</div>
						</div>
						<div class="form-group">
							<label for="qq">QQ</label>
							<input type="tel" class="form-control" maxlength="13" id="qqUpdate" placeholder="请输入QQ号" value="">
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" onclick="updateUser()">保存</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal -->
	</div>
	<!-- 报警设置 -->
	<div class="modal fade" id="userWarnModal" tabindex="-1" role="dialog" aria-labelledby="warnModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="updateModalLabel">报警设置</h4>
				</div>
				<div class="modal-body">
					<form role="form">
						<div class="form-group">
							<label for="username">登陆账号</label>
							<input type="hidden" id="idWarnUpdate" value="" />
							<input type="text" id="nameWarn" readonly="readonly" class="form-control" maxlength="25">
						</div>
						<div class="form-group">
							<div class="panel panel-default">
							    <div class="panel-heading">
							        <h3 class="panel-title">
							           memcached报警
							        </h3>
							    </div>
							    <div class="panel-body">
									<div class="col-md-4 col-xs-12">
										<label for="tel">CPU使用率</label>
										<div class="switch" data-on="success" data-off="warning">
										    <input id="cpuWarn" type="checkbox" />
										</div>
									</div>
									<div class="col-md-4 col-xs-12">
										<label for="tel">命中率</label>
										<div class="switch" data-on="success" data-off="warning">
										    <input id="hitWarn" type="checkbox" />
										</div>
									</div>
							    </div>
							</div>
						</div>
						<div class="form-group">
							<div class="panel panel-default">
							    <div class="panel-heading">
							        <h3 class="panel-title">
							           jvm报警
							        </h3>
							    </div>
							    <div class="panel-body">
									<div class="col-md-4 col-xs-12">
										<label for="tel">CPU使用率</label>
										<div class="switch" data-on="success" data-off="warning">
										    <input id="telStateUpdate" type="checkbox" checked="checked" />
										</div>
									</div>
									<div class="col-md-4 col-xs-12">
										<label for="tel">命中率</label>
										<div class="switch" data-on="success" data-off="warning">
										    <input id="telStateUpdate" type="checkbox" checked="checked" />
										</div>
									</div>
							    </div>
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" onclick="updateWarn()">保存</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal -->
	</div>
	<!-- 密码修改 -->
	<div class="modal fade" id="passwordUpdateModal" tabindex="-1" role="dialog" aria-labelledby="passwordUpdateModal" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="updateModalLabel">修改用户密码</h4>
				</div>
				<div class="modal-body">
					<form role="form">
						<div class="form-group">
							<label for="username">登陆账号</label>
							<input type="hidden" id="passwordIdUpdate" value="" />
							<input type="text" readonly="readonly" class="form-control" maxlength="25" id="usernamePassUpdate" placeholder="请输入登陆账号">
						</div>
						<div class="form-group">
							<label for="username">姓名</label>
							<input type="text" readonly="readonly" class="form-control" maxlength="10" id="namePassUpdate" placeholder="请输入姓名">
						</div>
						<div class="form-group">
							<label for="password">密码</label>
							<input type="password" class="form-control" maxlength="10" id="passwordUpdate" placeholder="请输入密码" value="">
						</div>
						<div class="form-group">
							<label for="confirmPassword">确认密码</label>
							<input type="password" class="form-control" maxlength="10" id="confirmPasswordUpdate" placeholder="请输入确认密码" value="">
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" onclick="updatePassword()">保存</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal -->
	</div>
</body>
</html>