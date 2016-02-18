<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
	<head>
		<title>控制台</title>
		<meta name="description" content="权限控制系统" />
		<jsp:include page="include.jsp"></jsp:include>
	</head>

	<body class="no-skin">
		<!-- #section:basics/navbar.layout -->
		<jsp:include page="navbar.jsp"></jsp:include>

		<!-- /section:basics/navbar.layout -->
		<div class="main-container" id="main-container">

			<!-- #section:basics/sidebar -->
			<jsp:include page="menu.jsp"></jsp:include>

			<!-- /section:basics/sidebar -->
			<div class="main-content">
				<div class="main-content-inner">
					<!-- #section:basics/content.breadcrumbs -->
					<jsp:include page="contentBar.jsp"></jsp:include>
					<jsp:include page="dashboard.jsp"></jsp:include>
				</div>
			</div><!-- /.main-content -->

			<jsp:include page="footer.jsp"></jsp:include>
		</div><!-- /.main-container -->
		<div id="loadPanel"></div>
	</body>
</html>