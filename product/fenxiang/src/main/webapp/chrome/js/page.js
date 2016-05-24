$(document).ready(function() {
	var localStorage = window.localStorage;
	$("#fx").click(function() {
		chrome.tabs.getSelected(function(tab) {
			alert(tab.url);
		});
	});

	$("#closeAlert").click(function() {
		$("#inputAlert").hide();
	});
	$("#login").click(function() {

		var data = {};
		data.name = $("#userno").val();
		data.password = $("#password").val();

		if (data.name == '' || data.name == undefined) {
			$("#inputAlert").show();
			return;
		}
		if (data.password == '' || data.password == undefined) {
			$("#inputAlert").show();
			return;
		}

		$.ajax({
			url : "http://localhost:8080/fenxiang/test/user/",
			type : "POST",
			data : JSON.stringify(data),
			dataType : "JSON",
			contentType : "application/json",
			success : function(data) {
				localStorage.setItem("accessToken", "23232"+data.accessToken);
				$("#loginDiv").hide();
				$("#test").html("<h2>成功登录，只要你不重装该插件，就不需要登陆</h2>");
			},
			error : function(xhr, status, error) {
				alert(JSON.parse("{'a':'a'}"));
				alert(JSON.stringify(xhr));
				alert(JSON.stringify(error));
				alert(JSON.stringify(status));
			}
		});
	});
});