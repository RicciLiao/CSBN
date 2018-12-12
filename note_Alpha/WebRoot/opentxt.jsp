<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <script type="text/javascript">
        var text_area = document.getElementById("text_area");
        var text_area_span = document.getElementById("text_area_span");
        (function () {
                if (document.getElementById("toolbar").offsetTop == "-55") {
                    $(".div_text").css("height", "89%");
                }
                text_area = document.getElementById("text_area");
                buildItemContent();
                text_area.onscroll = function () {
                    document.getElementById("text_area_span").scrollTop = text_area.scrollTop
                };
                $("#text_area").change(function () {
                    find_div_clean_count();
                });
            }()
        );
    </script>
</head>

<body>
<input type="button" id="back" class="back" value="返回" onclick="loadItem(previous_item_link_guid, false)">
<label id="l_title" class="l_title">文件名字&nbsp;~~</label>
<label id="l_text" class="l_text">文件内容&nbsp;~~</label>
<input class="download_this_file" type="button" value="下载此文件" onclick="download_this_file()">
<div id=" " class="d_l_">
    <label class="l_size">文件大小：</label><label id="l_size" style="margin-left: 10px;" class="l_size"></label>
    <label class="l_date">创建日期：</label><label id="l_date" style="margin-left: 10px;" class="l_date"></label>
    <label class="l_mdate">最后一次修改日期：</label><label id="l_mdate" style="margin-left: 10px;" class="l_mdate"></label>
    <input id="current_item_guid" type="text" readonly="readonly" style="display: none"/>
</div>
<div id="div_text" class="div_text">
    <input type="text" id="t_title" class="t_title">
    <img id="hide_tool_bar" src="source/pick_up_center.png" class="hide_tool_bar" title="点击这里可以收起上侧编辑栏哦  ^_^"
         onclick="_tool_bar(this.id) ">
    <div id="div_text_b" class="div_text_b">
        <div id="tool_bar" class="tool_bar">
            <div id="tool_bar_main"
                 style="height: 37px;width: 100%;float:left;margin-left:0px;background-color: white;">
                <input type="button" id="save_" class="save_" value="保存" onclick="save_()">
                <input type="button" id="save_jm" class="save_jm" value="加密保存" onclick="show(this.id)">
                <input type="button" id="find_data" class="find_data" value="查找"
                       onclick="eject_file_operate_div(this.id)">
                <input type="button" id="replace_data" class="replace_data" value="替换"
                       onclick="eject_file_operate_div(this.id)">
                <input type="button" id="turnto_data" class="turnto_data" value="转到" onclick="tips()">
                <a class="z">字体：</a>
                <select id="font_" class="font_" disabled="disabled">
                    <option class="option_" value="常规">&nbsp;&nbsp;常&nbsp;规</option>
                    <option class="option_bold" value="加粗">&nbsp;&nbsp;加&nbsp;粗</option>
                    <option class="option_italics" value="斜体">&nbsp;&nbsp;斜&nbsp;体</option>
                </select>
                <input type="button" id="time_data" class="time_data" value="插入时间" onclick="tips()">
                <input type="button" id="count_data" class="count_data" value="统计" onclick="tips()">
                <%--				<input type="button" id="count_data" class="count_data" value="统计" onclick="tips()">
                                <input type="button" id="count_data" class="count_data" value="统计" onclick="tips()">
                                <input type="button" id="count_data" class="count_data" value="统计" onclick="tips()">--%>
            </div>
            <div id="opentxt_operate_div" class="find_div"></div>
        </div>
        <textarea id="text_area" class="text_area" style="z-index: 999;background: rgba(0,0,0,0);"></textarea>
        <span id="text_area_span" class="text_area"
              style="margin-left: 2px;color: rgba(0,0,0,0);float: left;margin-top: -445px;z-index: 0"></span>

    </div>
    <div id="file_save_result" class="file_save_result">
        <img class="img_file_save_result" src="source/ok.png">
        <a id="a_file_save_result" class="a_file_save_result"></a>
    </div>
</div>
</body>
</html>
