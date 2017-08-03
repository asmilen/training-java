<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html >
<head>
    <base href="${pageContext.request.contextPath}/">
    <meta charset="UTF-8">
    <title>Login</title>
    <link rel="stylesheet" href="web/css/style.css">
</head>

<body>
    <hgroup>
        <h1>Login</h1>
    </hgroup>
    <form method="POST">
        <div class="group">
            <input id="email" name="email"><span class="highlight"></span><span class="bar"></span>
            <label>Email</label>
        </div>
        <div class="group">
            <input id="password" type="password" name="password"><span class="highlight"></span><span class="bar"></span>
            <label>Password</label>
        </div>
        <c:if test="${empty error}">
            <p id="login-error" class="error" role="alert" style="">${error}</p>
        </c:if>
        <button id="login-signin" type="submit" class="button buttonBlue">Login</button>
    </form>
    <footer>
        <a href="https://teko.facebook.com/" target="_blank">
        <img src="web/images/logo.png" height="40" width="100"></a>
    </footer>
</body>
</html>
