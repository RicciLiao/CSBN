<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<script>
    //window.history.replaceState(null, "", "register");
</script>
<script type="text/javascript" src="<%=path%>/js/jquery-2.1.1.js"></script>
<script type="text/javascript" src="<%=path%>/js/myjs.js"></script>
<script type="text/javascript" src="<%=path%>/js/constants.js"></script>
<script type="text/javascript" src="<%=path%>/js/register.js"></script>

<head>
    <base href="<%=basePath%>">
    <title>注册—云记本</title>
    <link rel="icon" href="<%=basePath%>/favicon.ico" type="image/x-icon">
    <link rel="shortcut icon" href="<%=basePath%>/favicon.ico" type="image/x-icon">
    <meta http-equiv="description" content="This is my page">
    <link rel="stylesheet" type="text/css" href="<%=path%>/css/register.css">
</head>
<body>
<div class="d_drag_back"></div>
<div class="d_top">
    <!--  	<input type="button" value="test" onclick="check_the_satisfy()"></button> -->
    <div class="z" style="background-color: black;">
        <div class="d_top_links">
            <a class="a_top_links" href="">客户端下载</a>
            <a class="a_top_links" href="">问题反馈</a>
            <a class="a_top_links" href="">关于我们</a>
            <a class="a_top_links" href="">使用指南</a>
            <a class="a_top_links" style="color: red;" href="">关于对用户数据的保密措施</a>
        </div>
        <div class="d_top_bottom"></div>
    </div>
</div>
<div class="d_main">
    <div class="d_main_top">
        <img class="register_logo" src="source/logo_register.png" title="云记本 记你所想">
    </div>
    <div class="d_main_center">
        <s:form id="register" theme="simple" method="post" namespace="/" action="signUp">
            <p class="p">
                <label id="l_note_email" class="l_note" style="display: none;">邮箱地址: </label>
                <s:textfield id="userEmail"
                             cssStyle='background:url(source/mail_.png) no-repeat 10px;color: #8B0000;display: none;'
                             cssClass="i_user" name="userInfo.userEmail" placeholder="请输入电子邮箱地址"
                ></s:textfield>
                <label id="l_note_phone" class="l_note">手机号: </label>
                <s:textfield id="userPhone"
                             cssStyle="background:url(source/phone_.png) no-repeat 10px;color: #8B0000;IME-MODE: disabled;"
                             cssClass="i_user" name="userInfo.userPhone" placeholder="请输入手机号码"
                ></s:textfield>
                <span class="tip" id="userPhone_tip"></span>
                <span class="tip" id="userEmail_tip"></span>
            </p>
            <div class="d_drag"></div>
            <p class="p">
                <label class="l_note">用户名: </label>
                <s:textfield id="userName"
                             cssStyle="background:url(source/person_.png) no-repeat 10px;" cssClass="i_user"
                             name="userInfo.userName" placeholder="请设置用户名"></s:textfield>
                <span class="tip" id="userName_tip"></span>
            </p>
            <p class="p">
                <label class="l_note">验证码: </label>
                <s:textfield id="verCode"
                             cssStyle="background:url(source/key_.png) no-repeat 10px;"
                             cssClass="i_user" name="verCode"
                             placeholder="请输入验证码"></s:textfield>
            </p>
            <p class="p">
                <label class="l_note">密码: </label>
                <s:password id="userPassword"
                            cssStyle="background:url(source/lock_.png) no-repeat 10px;" cssClass="i_user"
                            name="userInfo.userPassword" placeholder="请设置登录密码"></s:password>
            </p>
            <p class="p">
                <label class="l_note">确认密码: </label>
                <s:password id="rePassword" name="userInfo.userRePassword"
                            cssStyle="background:url(source/lock_.png) no-repeat 10px;" cssClass="i_user"
                            placeholder="请再次输入登录密码"></s:password>
                <span class="tip" id="rePassword_tip"></span>
            </p>
            <p class="p">
				<span id="d_agreement" class="d_agreement">
					<s:checkbox id="chkAgreement" name="chkAgreement" cssClass="c_agreement"></s:checkbox>
					<label class="l_agreement">已阅读并接受</label>
                    <a class="a_agreement" href="login.jsp">《云记本用户协议》</a>
				</span>
                <span class="tip" id="chkAgreement_tip"></span>
            </p>
            <s:submit id="regSubmit" name="regSubmit" cssStyle="display:none"></s:submit>
            <input type="button" id="signUp" value="注册" class="sign_up"/>
            <label class="l_login">已有帐号？去</label>
            <%--<a class="a_login" onclick="newFormJump('loginJump', document.body)">登录</a>--%>
            <a class="a_login" href="login.jsp">登录</a>
        </s:form>
        <div class="register_user_psw_tip" id="userPassword_tip">
            <a class="a_sign_b">◆</a>
            <a class="a_sign_a">◆</a>
            <label id="userPassword_len" class="register_user_psw_check">长度为6~14个字符</label>
            <label id="userPassword_cha" class="register_user_psw_check">支持数字,大小写字母和标点符号</label>
            <label id="userPassword_spa" class="register_user_psw_check">不允许有空格</label>
        </div>
        <a id="a_theway_reg" class="a_theway_reg" onclick="otherWayReg()">通过邮箱注册</a>
    </div>
</div>
<div class="d_bottom">
    <div class="z">
        <div class="d_bottom_links">
            <a class="a_bottom_links" href="">服务协议&nbsp;&nbsp;|</a>
            <a class="a_bottom_links" href="">版本更新&nbsp;&nbsp;|</a>
            <a class="a_bottom_links" href="">帮助中心&nbsp;&nbsp;|</a>
            <a class="a_bottom_links" href="">联系我们</a>
        </div>
    </div>
</div>
<div class="d_left_help">
    <div class="d_left_help_touch" onclick="" onmouseover="d_left_help_eject()" onmouseout="d_left_help_inject()"></div>
    <textarea class="t_left_help" readonly="readonly">遇&#010;到&#010;问&#010;题</textarea>
    <textarea class="t_left_help_eamil" readonly="readonly">请用微信"扫一扫”下面的二维码后联系客服:</textarea>
</div>
<img src="source/QR_code.jpg" class="QRcode"/>
</body>
</html>
