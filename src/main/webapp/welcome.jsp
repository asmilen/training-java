<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
	    <meta charset="UTF-8">
        <title>Trang chủ</title>
        <base href="${pageContext.request.contextPath}/">
        <link href="web/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
        <link href="web/css/core.css" rel="stylesheet" type="text/css" />
        <link href="web/css/components.css" rel="stylesheet" type="text/css" />
		<link href="web/css/pages.css" rel="stylesheet" type="text/css" />
    </head>
    <body>
    	<div class="account-pages"></div>
		<div class="clearfix"></div>
        <div class="wrapper-page">
            <div class="ex-page-content text-center">
                <h1>WELCOME, ${email}</h1><br>
                <h3 class="text-muted">Chúc mừng bạn đã đăng nhập thành công</h3>
                <br>
                <a class="btn btn-default waves-effect waves-light" href="logout">Đăng xuất</a>
            </div>
        </div>
	</body>
</html>
