<!DOCTYPE html>
<html >
<head>
    <base href="${pageContext.request.contextPath}">
    <meta charset="UTF-8">
    <title>Login</title>
    <link rel="stylesheet" href="/web/css/style.css">
</head>

<body>
  <hgroup>
  <h1>Login</h1>
</hgroup>
<form>
  <div class="group">
    <input id="email" type="text"><span class="highlight"></span><span class="bar"></span>
    <label>Email</label>
  </div>
  <div class="group">
    <input id="password" type="password"><span class="highlight"></span><span class="bar"></span>
    <label>Password</label>
  </div>
  <p id="login-error" class="error" role="alert" style="">Email or password is invalid</p>
  <button id="login-signin" type="button" class="button buttonBlue">Login</button>
</form>
<footer>
<a href="https://teko.facebook.com/" target="_blank">
<img src="/web/images/logo.png" height="40" width="100"></a>
</footer>
</body>
</html>
