<!DOCTYPE html> 
<html lang="en"> 
<head> 
    <meta charset="utf-8"> 
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
	<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <title>信息考核管理系统_uimaker</title>
    <#import "/common/app.ftl" as app>
	<link href="${app.basePath}/static/css/base.css" rel="stylesheet">
	<link href="${app.basePath}/static/css/platform/login.css" rel="stylesheet">
	<script type="text/javascript" src="${app.basePath}/static/js/jquery.js"></script>
	<script type="text/javascript" src="${app.basePath}/static/js/global.js"></script>
	<script type="text/javascript" src="${app.basePath}/static/js/lib/layer/layer.js"></script>
	<script type="text/javascript">
		$('body').on('click', '.notice-list>li>a', function(){
		     layer.open({
		        type: 2,
		        title: '',
		        shadeClose: true,
		        shade: 0.8,
		        area: ['1100px', '640px'],
		        content: 'inform.html' //iframe的url
		    }); 
		})
	</script>
</head> 
<body>
	<div class="left-bg"></div>
    <div class="right-bg"></div>
    <div class="login-logo"></div>
    <div class="login-center">
    	<div class="login-content">
    		<div class="notice">
    			<div class="notice-title">
    				<i class="notice-icon"></i>
    				<span>通知公告</span>
    			</div>
    			<ul class="notice-list">
    				<li class="list-item ellipsis">
    					<a href="notice.html" target="_blank">
    						<i class="rt-icon"></i>
    						2016国家公务员考试行测选择
    					</a>
    				</li>
    				<li class="list-item ellipsis">
    					<a href="notice.html" target="_blank">
	    					<i class="rt-icon"></i>
	    					行政机关公务员管理系统
    				   </a>
    				</li>
    				<li class="list-item ellipsis">
    					<a href="notice.html" target="_blank">
	    					<i class="rt-icon"></i>
	    					公务员怎么考核比考什么重要
	    				</a>
    				</li>
    				<li class="list-item ellipsis">
    					<a href="notice.html" target="_blank">
	    					<i class="rt-icon"></i>
	    					公务员考核关键看平时--新闻报道
	    				</a>
    				</li>
    				<li class="list-item ellipsis">
    					<a href="notice.html" target="_blank">
	    					<i class="rt-icon"></i>
	    					关于公务员平时考核的实施方案
	    				</a>
    				</li>
    				<li class="list-item ellipsis">
    					<a href="notice.html" target="_blank">
	    					<i class="rt-icon"></i>
	    					我国公务员考核制度存在的问题及原因
	    				</a>
    				</li>
    			</ul>
    		</div>
    		<div class="login-input">
    			<script type="text/javascript">
    				submit=function(){
    					document.getElementById("form").submit();
    				}
    			</script>
    			<form id="form" action="/rbac/login" method="post">
	    			<div class="login-label">用户登录</div>
	    			<div class="username">
	    				<i class="username-icon"></i>
	    				<input type="text" placeholder="用户">
	    			</div>
	    			<div class="password">
	    				<i class="password-icon"></i>
	    				<input type="password" placeholder="密码">
	    			</div>
	    			<div class="login-btn" onclick="submit();">马上登录</div>
    			</form>
    		</div>
    	</div>
    </div>
</body>
</html>
