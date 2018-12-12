<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">

<link rel="stylesheet" type="text/css" href="css/notebook.css">
<script type="text/javascript" src="js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="js/myjs.js"></script>
<script type="text/javascript">
	$(function() {
		filelength=${request.filelength};
		filedata = "${request.filename}";
		get(filedata,filelength);
	});
</script>
</head>
<body  >
	<div id="toolbar" class="toolbar">
		<input type="button" id="upload_file" class="upload_file" value="上传" onclick=" ">
		<input type="button" id="add_dir" class="add_dir" value="新建文件夹"  onclick="show(this.id)">
		<input type="button" id="add_file" class="add_dir" value="新建云计本" onclick="show(this.id)">
	</div>
	<div id="a" style="overflow :auto;width: 100%;height: 500px;"></div>
</body>
</html>
