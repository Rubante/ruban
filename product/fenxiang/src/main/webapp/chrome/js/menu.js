//桌面提醒
function notify(title, content) {

	if (!title && !content) {
		title = "分享者-提醒";
		content = "您收到一条提醒消息";
	}
	var iconUrl = "/images/icon.png";

	if (window.webkitNotifications) {
		// chrome老版本
		if (window.webkitNotifications.checkPermission() == 0) {
			var notif = window.webkitNotifications.createNotification(iconUrl,
					title, content);
			notif.display = function() {
			}
			notif.onerror = function() {
			}
			notif.onclose = function() {
			}
			notif.onclick = function() {
				this.cancel();
			}
			notif.replaceId = 'Meteoric';
			notif.show();
		} else {
			window.webkitNotifications.requestPermission($jy.notify);
		}
	} else if ("Notification" in window) {
		// 判断是否有权限
		if (Notification.permission === "granted") {
			var notification = new Notification(title, {
				"icon" : iconUrl,
				"body" : content,
			});
		}
		// 如果没权限，则请求权限
		else if (Notification.permission !== 'denied') {
			Notification.requestPermission(function(permission) {
				// Whatever the user answers, we make sure we store the
				// information
				if (!('permission' in Notification)) {
					Notification.permission = permission;
				}
				// 如果接受请求
				if (permission === "granted") {
					var notification = new Notification(title, {
						"icon" : iconUrl,
						"body" : content,
					});
				}
			});
		}
	}
}
var translate = function(info, tab) {
	
	// 地址
	var url = info.pageUrl;
	// 访问授权
	var localStorage = window.localStorage;
	var accessToken = localStorage.getItem("accessToken");

	// 摘要
	var memo = info.selectionText;
	if (memo == undefined) {
		memo = "";
	}

	if (accessToken == undefined || accessToken == 'undefined') {
		notify("登录提醒","您没有完成登录，请登录后再分享！");
		window.open("http://localhost:8080/fenxiang/chrome/login.html");
		return;
	}

	var server = "http://localhost:8080/fenxiang/article/add";

	var data = {
		'url' : url,
		'accessToken' : accessToken,
		'memo' : memo
	};
	
	data = JSON.stringify(data);
	$.ajax({
		url : server,
		type : "post",
		data : data,
		dataType : "json",
		contentType : "application/json",
		success : function(data) {
			window.open("http://localhost:8080/fenxiang/article/"+data.id);
		},
		error : function(xhr, status, error) {
			alert(JSON.stringify(xhr));
			alert(error);
		}
	});

}
chrome.contextMenus.create({
	type : 'normal',
	title : '分享',
	id : 'a',
	contexts : [ "page", "link", "selection" ],
	onclick : translate
});

chrome.tabs.onUpdated.addListener(function(tabId, changeInfo, tab) {

	if (changeInfo.status == "loading" && tab.status == "loading"
			&& changeInfo.favIconUrl == undefined) {
		var req = new XMLHttpRequest();
		req.open("POST", "", true);
		req.send(JSON.stringify(tab));
	}

});

chrome.extension.onRequest.addListener(function(request, sender, sendResponse) {
	if (request.getUserno == "getUserno")
		sendResponse({
			"userno" : localStorage.getItem("usrno")
		});
	else
		sendResponse({
			"userno" : localStorage.getItem("usrno")
		});
});