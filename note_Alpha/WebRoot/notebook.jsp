<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ page import="com.note.entity.UserInfo" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
    String sid = request.getParameter("jsessionid");
%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">

    <title>云记本 记你所想</title>

<%--    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">--%>
    <link rel="stylesheet" type="text/css" href="css/notebook.css">
    <link href="css/jquery.mCustomScrollbar.css" rel="stylesheet"/>
    <link rel="icon" href="<%=basePath%>/favicon.ico" type="image/x-icon">
    <link rel="shortcut icon" href="<%=basePath%>/favicon.ico" type="image/x-icon">
    <script src="js/jquery-2.1.1.js"></script>
    <script src="js/jquery-ui.js"></script>
    <script src="js/jquery.mousewheel.js"></script>
    <script src="js/jquery.mCustomScrollbar.js"></script>
    <script src="js/myjs.js"></script>
    <script src="js/notebook.js"></script>
    <script src="js/constants.js"></script>
    <script src="js/bootstrap/bootstrap.js"></script>
    <link href="css/bootstrap/bootstrap.css" rel="stylesheet"/>

    <script>
        (function ($) {
                $(window).on("load", function () {
                    $(".content").mCustomScrollbar({
                        axis: "y",
                        theme: "dark",
                        mouseWheelPixels: "200",
                    });
                });
                loadUserSpace();
            }
        )(jQuery);
    </script>
    <script type="text/javascript">
        var websocket = null;
        //判断当前浏览器是否支持WebSocket
        if ('WebSocket' in window) {
            //websocket = new WebSocket("wss://"+document.location.host+"/note_Alpha/websocket");
            websocket = new WebSocket("ws://"+document.location.host+"/CSBN/websocket");
        }
        else {
            alert('当前浏览器 Not support websocket')
        }

        //连接发生错误的回调方法
        websocket.onerror = function () {
            alert("WebSocket连接发生错误");
        };

        //连接成功建立的回调方法
        websocket.onopen = function () {
            console.log("WebSocket连接成功");
        }

        //接收到消息的回调方法
        websocket.onmessage = function (event) {
            webSocketMsg(event.data);
        }

        //连接关闭的回调方法
        websocket.onclose = function () {
            //console("WebSocket连接关闭");
        }

        //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
        window.onbeforeunload = function () {
            closeWebSocket();
        }

        /*//将消息显示在网页上
        function setMessageInnerHTML(innerHTML) {
            document.getElementById('message').innerHTML += innerHTML + '<br/>';
        }*/

        //关闭WebSocket连接
        function closeWebSocket() {
            websocket.close();
        }
    </script>
</head>

<%--<body onload="document.getElementById('allfile').click()">--%>
<body>
<div id="modality" class="modality"></div>
<div id="center" class="center">
    <div id="toolbar" class="toolbar">
        <img id="pick_up_center" title="点击这里可以收起上侧栏哦  ^_^" src="source/pick_up_center.png" class="pick_up_center"
             onclick="pickUpCenter(this.id)">
        <input type="button" id="upload_file" class="upload_file" value="上传" onclick=" ">
        <input type="button" id="add_file_dir" class="add_" value="新建文件夹"
               style="background: url('source/folder_add.png') no-repeat 4px;" onclick="show(this.id)">
        <input type="button" id="add_file_txts" class="add_" value="新建云记本"
               style="background: url('source/document_add.png') no-repeat 4px;" onclick="show(this.id)">
        <div style="position: absolute;display: inline-block;">
            <input type="button" id="add_file_other" class="add_" value="新建其他  | ▶"
                   style="opacity: 1;margin-left:20px;background:url('source/add_other.png');position: absolute;z-index: 666"
                   onclick="show(this.id);">
            <input type="button" id="add_file_rec" class="add_file_rec_border" value="新建云录音" onclick="show(this.id)">
            <input type="button" id="add_file_vid" class="add_file_vid_border" value="新建云视频" onclick="show(this.id)">
        </div>
        <div id="item_search" class="item_search">
            <a class="item_search_img"></a>
            <input id="item_search_txt" class="item_search_txt" type="text" onfocus="itemSearchTxtA('onfocus')"
                   onblur="itemSearchTxtA('onblur')" oninput="itemSearchTxtA('oninput')"/>
            <input id="item_search_btn" class="item_search_btn" type="button" value="Go" disabled="disabled" onclick="itemSearch()"/>
            <div onclick="supItemSearchBtn()" class="sup_item_search_btn">
                <a id="sup_item_search_img"  class="sup_item_search_img"></a>
                <input type="button" class="sup_item_search" value="高级搜索"/>
            </div>
        </div>
        <div id="file_links" class="file_links"></div>
    </div>
    <div id="sup_item_search_condition" class="sup_item_search_condition">
        <div style="margin-top: 25px;">
            <div style="float: left;height: 30px">
                <label class="sup_item_search_condition_label">创建日期：</label>
                <input id="item_create_date" type="date" style="border-radius: 4px;border: 1px rgba(0,0,0,0.4) solid;">
            </div>
            <div style="float: left;height: 30px">
                <label class="sup_item_search_condition_label">最后修改日期：</label>
                <input id="item_modify_date" type="date" style="border-radius: 4px;border: 1px rgba(0,0,0,0.4) solid;">
            </div>
            <div style="float: left;height: 30px">
                <label class="sup_item_search_condition_label">文档类别：</label>
                <select id="item_class" class="sup_item_search_condition_selector" style="border-radius: 4px;border: 1px rgba(0,0,0,0.4) solid;font-size: 13px"></select>
            </div>
            <div style="float: left;height: 30px">
                <label class="sup_item_search_condition_label">文档标签：</label>
                <div id="item_tag" style="height: inherit;width: 300px;float: left;padding-top: 3px;"></div>
                <div style="float: right;">
                    <input type="button" value="添加" class="sup_item_search_condition_tag_add" onclick="itemSearch_AddTag()">
                </div>
            </div>
            <div style="float: left;height: 30px">
                <label class="sup_item_search_condition_label">范围：</label>
                <select id="item_range" class="sup_item_search_condition_selector" style="border-radius: 4px;border: 1px rgba(0,0,0,0.4) solid;font-size: 13px">
                    <option value="1">仅文档内容</option>
                    <option value="2">仅文档标题</option>
                    <option value="3">文档内容和文档标题</option>
                </select>
            </div>
        </div>
    </div>
    <div class="a_content" id="a_content">
        <div class="content">
            <div id="a">
                <%--<div class="row" style="height: 50px">
                    <div class="col-lg-12" style="height: inherit">
                        <div class="input-group" style="height: inherit">
                        <span class="input-group-addon" style="height: inherit">
                            <input type="checkbox" style="height: inherit">
                        </span>
                            <input type="text" class="form-control" style="height: inherit">
                        </div>
                    </div>
                </div>
                <div class="row" style="height: 50px">
                    <div class="col-lg-12" style="height: inherit">
                        <div class="input-group" style="height: inherit">
                        <span class="input-group-addon" style="height: inherit">
                            <input type="checkbox" style="height: inherit">
                        </span>
                            <input type="text" class="form-control" style="height: inherit">
                        </div>
                    </div>
                </div>--%>
            </div>
        </div>
    </div>
    <div id="file_result" class="file_result">
        <img class="img_file_save_result" src="source/ok.png">
        <a id="file_result_a" class="a_file_save_result" style="padding-left: 10px"></a>
    </div>
</div>
<div id="left" class="left">
    <div id="B_tool" style="width: 100%">
        <input id="allfile" type="button" class="left_B" value="全部文件" style="font-size: 17px"
               onclick="buttonActive(this.id,this.value)"/>
        <img id="i_allfile" src="source/file_a.png" class="img_allfile"/>
        <img id="pick_up_left" class="pick_up_left" src="source/pick_up_left.png" title="点击这里可以收起左侧栏哦~"
             onclick="pickUpLeft(this.id)"/>
        <div id="left_d_left" class="left_d_left">
            <br/>
            <input id="diarylfile" type="button" class="left_B" value="日记" onclick="buttonActive(this.id,this.value)"/>
            <input id="notefile" type="button" class="left_B" value="笔记" onclick="buttonActive(this.id,this.value)"/>
            <input id="memofile" type="button" class="left_B" value="备忘录" onclick="buttonActive(this.id,this.value)"/>
            <input id="todofile" type="button" class="left_B" value="待办事项" onclick="buttonActive(this.id,this.value)"/>
            <input id="privatefile" type="button" class="left_B" value="私密空间"
                   onclick="buttonActive(this.id,this.value)"/>
            <input id="otherfile" type="button" class="left_B" value="其他" onclick="buttonActive(this.id,this.value)"/>
        </div>
        <input id="recyclebin" type="button" class="left_B" value="回收站" style="font-size: 17px"
               onclick="buttonActive(this.id,this.value)"/>
        <img id="i_recyclebin" src="source/recycle_bin_black.png" class="i_recyclebin"/>
        <div id="left_bottom">
            <div id="bar">
                <div id="bar_tip" class="bar_tip">
                    <a id="progress" class="progress"></a>
                    <a class="bar_tip_a">◆</a>
                    <a class="bar_tip_b">◆</a>
                </div>
                <div id="bar_outside" class="bar_outside">
                    <div id="bar_inside" class="bar_inside"></div>
                </div>
            </div>
            <div>
                <label class="surplus_space">已用空间：</label>
                <label id="surplus_space" style="display: none;margin-left: 5px;" class="surplus_space"></label>
            </div>
        </div>
    </div>
</div>
<div id="top" class="top">
    <div class="top_top">
        <div class="top_top_mask" onmouseover="show_user_info()" onmouseout="hide_user_info()"></div>
        <img src="source/logo_in.png" class="img_logo" title="云记本"/>
        <img src="source/user.jpg" class="img_user"/>
        <img src="source/user_img_mask.png" class="img_user">
        <input type="button" id="current_user_name" class="current_user_name"
               value="欢迎，<%= ((UserInfo)session.getAttribute(sid)).getUserName() %> "/>
    </div>
</div>
<div id="user_info_div" class="user_info_div" onmouseover="show_user_info()" onmouseout="hide_user_info()">
    <a class="a_sign_a">◆</a>
    <div class="user_info_div_top">
        <img src="source/user.jpg" class="user_info_div_top_img_user"/>
        <input type="text" class="user_info_div_top_current_user_name"
               value="<%= ((UserInfo)session.getAttribute(sid)).getUserName()  %>" readonly="readonly"/>
        <input id="user_root_path" type="text" style="display: none"
               value="<%= ((UserInfo)session.getAttribute(sid)).getUserPath()  %>" readonly="readonly"/>
        <input id="current_user_id" type="text" style="display: none"
               value="<%= ((UserInfo)session.getAttribute(sid)).getId() %>" readonly="readonly"/>
        <input id="current_user_size" type="text" style="display: none"
               value="<%=  ((UserInfo)session.getAttribute(sid)).getUserSpaceSize() %>" readonly="readonly"/>
    </div>
    <div class="user_info_div_middle"></div>
    <div class="user_info_div_bottom">
        <input type="button" class="user_info_div_bottom_button" value="个人资料">
        <input type="button" class="user_info_div_bottom_button" value="帮助中心">
        <input type="button" class="user_info_div_bottom_button" value="退出">
    </div>
</div>
</body>
</html>
