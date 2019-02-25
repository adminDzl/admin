<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en" >

<head>
	<title>${pd.SYSNAME}</title>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<link rel="stylesheet"  href="static/login/css/style.css">
	<script type="text/javascript" src="static/js/jquery-1.5.1.min.js"></script>

	<script src="./../../../../static/login/js/jquery.easing.1.3.js"></script>
	<script src="./../../../../static/login/js/jquery.mobile.customized.min.js"></script>
	<script src="./../../../../static/login/js/camera.min.js"></script>
	<script type="text/javascript" src="./../../../../static/js/jquery.tips.js"></script>
	<script type="text/javascript" src="./../../../../static/js/jquery.cookie.js"></script>
</head>

<body>

  <body>
	<div class="login">
		<div class="login-screen">
			<div class="app-title">
				<h1>智能园区管理后台</h1>
			</div>

			<div class="login-form">
				<div class="control-group">
				<input type="text" class="login-field" value="" placeholder="请输入用户名" id="loginname" name="loginname">
				<label class="login-field-icon fui-user" for="loginname"></label>
				</div>

				<div class="control-group">
				<input type="password" class="login-field" value="" placeholder="请输入密码" id="password" name="password">
				<label class="login-field-icon fui-lock" for="password"></label>
				</div>

				<span class="pull-right">
					<a onclick="severCheck();" class="btn btn-primary btn-large btn-block" id="to-recover">登录</a>
				</span>
				
			</div>
		</div>
	</div>

	<script type="text/javascript">

		//服务器校验
		function severCheck(){
			if(check()){

				var loginname = $("#loginname").val();
				var password = $("#password").val();
				var code = "qq313596790fh"+loginname+",fh,"+password+"QQ978336446fh"+",fh,";
				$.ajax({
					type: "POST",
					url: 'login_login',
					data: {KEYDATA:code,tm:new Date().getTime()},
					dataType:'json',
					cache: false,
					success: function(data){
						if("success" == data.result){
							saveCookie();
							window.location.href="main/index";
						}else if("usererror" == data.result){
							$("#loginname").tips({
								side : 1,
								msg : "用户名或密码有误",
								bg : '#FF5080',
								time : 15
							});
							$("#loginname").focus();
						}else if("codeerror" == data.result){
							$("#code").tips({
								side : 1,
								msg : "验证码输入有误",
								bg : '#FF5080',
								time : 15
							});
							$("#code").focus();
						}else{
							$("#loginname").tips({
								side : 1,
								msg : "缺少参数",
								bg : '#FF5080',
								time : 15
							});
							$("#loginname").focus();
						}
					}
				});
			}
		}

		//		$(document).ready(function() {
		//			changeCode();
		//			$("#codeImg").bind("click", changeCode);
		//		});

		$(document).keyup(function(event) {
			if (event.keyCode == 13) {
				$("#to-recover").trigger("click");
			}
		});

		function genTimestamp() {
			var time = new Date();
			return time.getTime();
		}

		function changeCode() {
			$("#codeImg").attr("src", "code.do?t=" + genTimestamp());
		}

		//客户端校验
		function check() {

			if ($("#loginname").val() == "") {

				$("#loginname").tips({
					side : 2,
					msg : '用户名不得为空',
					bg : '#AE81FF',
					time : 3
				});

				$("#loginname").focus();
				return false;
			} else {
				$("#loginname").val(jQuery.trim($('#loginname').val()));
			}

			if ($("#password").val() == "") {

				$("#password").tips({
					side : 2,
					msg : '密码不得为空',
					bg : '#AE81FF',
					time : 3
				});

				$("#password").focus();
				return false;
			}
//			if ($("#code").val() == "") {
//
//				$("#code").tips({
//					side : 1,
//					msg : '验证码不得为空',
//					bg : '#AE81FF',
//					time : 3
//				});
//
//				$("#code").focus();
//				return false;
//			}

			$("#loginbox").tips({
				side : 1,
				msg : '正在登录 , 请稍后 ...',
				bg : '#68B500',
				time : 10
			});

			return true;
		}

		function savePaw() {
			if (!$("#saveid").attr("checked")) {
				$.cookie('loginname', '', {
					expires : -1
				});
				$.cookie('password', '', {
					expires : -1
				});
				$("#loginname").val('');
				$("#password").val('');
			}
		}

		function saveCookie() {
			if ($("#saveid").attr("checked")) {
				$.cookie('loginname', $("#loginname").val(), {
					expires : 7
				});
				$.cookie('password', $("#password").val(), {
					expires : 7
				});
			}
		}
		function quxiao() {
			$("#loginname").val('');
			$("#password").val('');
		}

		jQuery(function() {
			var loginname = $.cookie('loginname');
			var password = $.cookie('password');
			if (typeof(loginname) != "undefined"
					&& typeof(password) != "undefined") {
				$("#loginname").val(loginname);
				$("#password").val(password);
				$("#saveid").attr("checked", true);
				$("#code").focus();
			}
		});
	</script>
	<script>
		//TOCMAT重启之后 点击左侧列表跳转登录首页
		if (window != top) {
			top.location.href = location.href;
		}
	</script>

</body>

</body>

</html>
