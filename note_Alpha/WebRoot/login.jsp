<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">

    <title>云记本 记你所想</title>
    <%--<meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">--%>
    <link rel="icon" href="<%=basePath%>/favicon.ico" type="image/x-icon">
    <link rel="shortcut icon" href="<%=basePath%>/favicon.ico"
          type="image/x-icon">
    <%--<meta http-equiv="description" content="This is my page">--%>
    <link rel="stylesheet" type="text/css" href="css/login.css">
<%--    <link rel="stylesheet" type="text/css" href="css/bootstrap/bootstrap.css">--%>
    <script type="text/javascript" src="js/jquery-2.1.1.js"></script>
    <script type="text/javascript" src="js/myjs.js"></script>
    <script type="text/javascript" src="js/login.js"></script>
    <script type="text/javascript" src="js/constants.js"></script>
  <%--  <script type="text/javascript" src="js/bootstrap/bootstrap.js"></script>--%>
</head>

<body onload="login_choose('login_zhanghao')">
<%--<div class="input-group">
    <span class="input-group-addon" id="basic-addon1"><span class="glyphicon glyphicon-lock"></span></span>
    <input type="text" class="form-control" placeholder="Username" aria-describedby="basic-addon1">
</div>--%>
	<div id="d_center" class="d_center">
		<div class="d_center_opacity"></div>
		<div id="d_logo" class="d_logo">
			<img id="logo" class="logo" src="source/logo_login.png" title="云记本 记你所想">
			<div class="z">
				<div class="d_top_links">
					<a class="a_top_links" onclick="tips()">客户端下载</a>
					<a class="a_top_links" onclick="tips()">问题反馈</a>
					<a class="a_top_links" onclick="tips()">关于我们</a>
					<a class="a_top_links" onclick="tips()">使用指南</a>
					<a class="a_top_links" style="color: #990000;" onclick="tips()">关于对用户数据的保密措施</a>
				</div>
			</div>
		</div>
		<div class="k">
			<div id="d_login" class="d_login">
				<div id="d_login_choose" class="d_login_choose">
					<input type="button" id="login_saoyisao" class="login_choose" value="扫一扫登录" onclick="login_choose(this.id)">
					<input 	type="button" id="login_zhanghao" class="login_choose"	value="帐号登录" onclick="login_choose(this.id)">
				</div>
				<div id="d_login_zhanghao" class="d_login_zhanghao">
					<s:form id="login" namespace="/" >
						<span id="login_err_tip" class="login_tip"></span>
                        <%--<div class="input-group user-info-input" style="margin-top: 30px">
                            <span class="input-group-addon"><span class="glyphicon glyphicon-user" style="font-size: 18px"></span></span>
							<s:textfield id="userName" name="userInfo.userName" cssClass="form-control" placeholder="请输入用户名"  aria-describedby="basic-addon1"></s:textfield>
                        </div>
                        <div class="input-group user-info-input">
                            <span class="input-group-addon"><span class="glyphicon glyphicon-lock" style="font-size: 18px"></span></span>
							<s:password id="userPassword" name="userinfo.userPassword" cssClass="form-control" placeholder="请输入密码" aria-describedby="basic-addon1"></s:password>
                        </div>--%>
						<s:textfield id="userName" cssStyle="background:url(source/person_.png) no-repeat 10px;" name="userInfo.userName" cssClass="user_name" placeholder="请输入用户名"></s:textfield>
						<s:password id="userPassword" cssStyle="background:url(source/lock_.png) no-repeat 10px;" name="userinfo.userPassword" cssClass="user_psw" placeholder="请输入密码"></s:password>
						<s:checkbox name="remember_user" cssClass="remember_user">
							<label class="l_remember_user">记住账号</label>
						</s:checkbox>
					</s:form>
					<input type="button" class="sign_in" value="登录"  onclick="login_check()"/>
					<a onclick="tips()" class="a_login_matter">无法登录</a>
				</div>
				<div id="d_login_saoyisao" class="d_login_saoyisao">
					<div class="d_login_QRcode">
						<img src="source/QR_code.jpg" class="QRcode" id="QRcode" onmouseover="QRcode_move('')" onmouseout="QRcode_move('1')" />
						<div class="QRcode_way" id="QRcode_way"></div>
						<a class="a_saoyisao_tips">请使用</a><a href="" class="a_app_">云记本 APP </a><a class="a_saoyisao_tips_"> 扫一扫登录</a>
					</div>
				</div>
				<div id="d_login_other" class="d_login_other">
					<img src="source/QQ_logo.png" title="通过QQ帐号登录" class="QQlogo"	id="QQlogo" onmouseover="login_logo_animation_moveover(this.id)" onmouseout="login_logo_animation_moveout(this.id)" onclick="tips()">
					<img src="source/Wechat_logo.png" title="通过微信帐号登录" class="Wechatlogo" id="Wechatlogo" onmouseover="login_logo_animation_moveover(this.id)" onmouseout="login_logo_animation_moveout(this.id)" onclick="tips()">
					<img src="source/Weibo_logo.png" title="通过微博帐号登录" class="Weibologo" id="Weibologo" onmouseover="login_logo_animation_moveover(this.id)" onmouseout="login_logo_animation_moveout(this.id)" onclick="tips()">
					<input type="button" class="b_reg" value="立即注册" onclick="window.location.href='register.jsp'">
				</div>
			</div>
		</div>
	</div>
	<div id="d_app_download" class="d_app_download">
		<div class="k">
			<div class="d_app_download_logo">
				<img src="source/PC_logo.png" id="PClogo" class="PClogo"	onmouseover="app_logo_animation_moveover(this.id)" onmouseout="app_logo_animation_moveout(this.id)" onclick="tips()">
				<img src="source/Android_logo.png" id="Androidlogo" class="Androidlogo" onmouseover="app_logo_animation_moveover(this.id)" onmouseout="app_logo_animation_moveout(this.id)" onclick="tips()">
			</div>
		</div>
	</div>
	<div class="d_bottom">
		<div class="k">
			<div class="d_bottom_links">
				<a class="a_bottom_links" href="" >服务协议&nbsp;&nbsp;|</a>
				<a class="a_bottom_links" href="">版本更新&nbsp;&nbsp;|</a>
					<a class="a_bottom_links" href="">帮助中心&nbsp;&nbsp;|</a>
					<a class="a_bottom_links" href="">联系我们</a>
			</div>
		</div>
	</div>
</body>
</html>
