var selected;
var interval;


var create_type;

var open_file_content;
var open_file_name;
var open_file_size;
var open_file_date;
var file_lastModified_date;
var open_file_path;
var find_data_div_target_count = 0;
var find_data_div_count = 0;
var write_to_the_span_string_sign = "";
var find_data_div_target_count = 0;
var start = 0;
var start_array;
var interval_b;
var before_file_operate_div = null;
var write_to_the_span_string = "";
var file_operate_div;
var timeout = null;

var upload_file_id = 0;
var temp_upload_file;
var interval_c;
var create_type;

//---鼠标拉动左侧栏调整大小 -- start--
var params = {
    width: 0,
    currentX: 0,
    currentY: 0,
    flag: false
};
//获取相关CSS属性
var getCss = function (o, key) {
    // getComputedStyle是为了兼容FF浏览器
    return o.currentStyle ? o.currentStyle[key] : document.defaultView.getComputedStyle(o, false)[key];
};
var adjust_size_left = function (target, controller, callback) {
    if (getCss(target, "width") !== "auto") {
        params.width = getCss(target, "width");
    }
    controller.onmousedown = function (event) {
        params.flag = true;
        if (event.preventDefault) {
            event.preventDefault();
        } else {
            event.returnValue = false;
        }
        var e = event;
        params.currentX = e.clientX;
    };
    window.onmouseup = function () {
        params.flag = false;
        params.width = getCss(target, "width");
    };
    /*    controller.onmouseout = function() {
            params.flag = false;
            params.width = getCss(target, "width");
        };*/
    window.onmousemove = function (event) {
        var e = event ? event : window.event;
        if (params.flag) {
            var nowX = e.clientX;
            var disX = nowX - params.currentX;
            target.style.width = parseInt(params.width) + disX + "px";
        }
    }
};
//---鼠标拉动左侧栏调整大小 -- end --

//-- 点击左侧栏组件的事件 -- start --
var history_left_div_click = null;

function left_div_click(element_id) {
    if (history_left_div_click == null) {
        adjust_left_div_style(null, element_id);
    } else {
        if (history_left_div_click == element_id) {
            adjust_left_div_style(history_left_div_click, element_id);
        } else {
            adjust_left_div_style(history_left_div_click, element_id);
        }
    }
    load_user_file(element_id);
}

// -- 点击左侧栏组件的事件 -- start --

function load_user_file(element_id) {

}

function adjust_left_div_style(history, current) {
    history_left_div_click = current;
    var current_element = $("#" + current);
    if (history != null) {
        if (history == current) {
            var history_element = $("#" + history);
            history_element.attr("class", "left_center_each");
            history_element.find(".icon_file_arrow_open").attr("class", "icon_file_arrow_close");
            /* $("#"+history+"_tree_root").empty();*/
            $("#" + history + "_tree_root").fadeOut(0);
            $("#icon" + history.substr(4, current.length)).attr("class", "icon" + history.substr(4, history.length));
            history_left_div_click = null;
        } else {
            current_element.attr("class", "left_center_each_active");
            current_element.find(".icon_file_arrow_close").attr("class", "icon_file_arrow_open");
            /*$("#"+current+"_tree_root").empty();*/
            $("#" + current + "_tree_root").fadeIn(0);
            $("#icon" + current.substr(4, current.length)).attr("class", "icon" + current.substr(4, current.length) + "active");
        }
    } else {
        current_element.attr("class", "left_center_each_active");
        current_element.find(".icon_file_arrow_close").attr("class", "icon_file_arrow_open");
        /*$("#"+current+"_tree_root").empty();*/
        $("#" + current + "_tree_root").fadeIn(0);
        $("#icon" + current.substr(4, current.length)).attr("class", "icon" + current.substr(4, current.length) + "active");
    }
}

function left_icon_click(cur_id) {

}

function left_bar_adjust_background_color(mode, id) {
    if (mode == "hover") {
        $("#div_" + id).css("background-color", "rgba(0, 102, 255, 0.1)");
    } else {
        $("#div_" + id).css("background-color", "rgba(0, 0, 0, 0)");
    }
}

function load_Sys() {
    $.ajax({
        url: "json/sys_framework_info.json",
        dataType: "json",
        success: function (result) {
            var sys_left_bar = document.getElementById("left_center_container");
            for (i = 0; i < result.left_file_desc.length; i++) {
                var a = document.createElement("div");
                a.setAttribute("class", result.left_file_desc_property.div_class);
                a.setAttribute("onmouseout", result.left_file_desc_property.onmouseout);
                a.setAttribute("onclick", result.left_file_desc_property.onclick);
                a.setAttribute("onmouseover", result.left_file_desc_property.onmouseover);
                var b = document.createElement("div");
                b.setAttribute("class", result.left_file_desc_property.inner_div_class);
                a.appendChild(b);
                var d = document.createElement("i");
                d.setAttribute("class", result.left_file_arrow.class);
                d.setAttribute("id", result.left_file_desc[i].id + result.left_file_arrow.id);
                d.setAttribute("onclick", result.left_file_arrow.onclick);
                d.setAttribute("onmouseover", result.left_file_arrow.onmouseover);
                d.setAttribute("onmouseout", result.left_file_arrow.onmouseout);
                b.appendChild(d);
                var c = document.createElement("input");
                c.setAttribute("class", result.left_file_desc_property.inner_btn_class);
                c.setAttribute("type", "button");
                c.setAttribute("id", result.left_file_desc[i].id);
                c.setAttribute("value", result.left_file_desc[i].desc);
                b.appendChild(c);
                var e = document.createElement("div");
                e.setAttribute("id", result.left_file_desc[i].id + result.left_file_root_container.id);
                e.setAttribute("class", result.left_file_root_container.class);

                sys_left_bar.appendChild(a);
                sys_left_bar.appendChild(e);
            }
        }
    })
}

//--
function eject_upload_confirm_div(string) {
    if (string == "eject_upload_confirm") {
        $(".upload_confirm_eject").animate({
            top: 0
        }, 200, function () {
            $("#" + string).attr("class", "pick_up_upload_confirm");
            $("#" + string).attr("src", "source/pick_up_center.png");
            $("#" + string).attr("id", "pick_up_upload_confirm");
            $("#upload_confirm_operation").fadeIn(300);
            $("#upload_confirm_div_label").hide();
        });
    }
    else {
        $(".upload_confirm_eject").animate({
            top: -171
        }, 300, function () {
            $("#" + string).attr("class", "eject_upload_confirm");
            $("#" + string).attr("src", "source/eject_center.png");
            $("#" + string).attr("id", "eject_upload_confirm");
            $("#upload_confirm_operation").hide();
            $("#upload_confirm_div_label").fadeIn(300);
        });
    }
}

function create_upload_file() {
    if (document.getElementById("upload_" + (upload_file_id - 1) + "_input") != null) {
        alert(document.getElementById("upload_" + (upload_file_id - 1) + "_input").value == "");
        if (document.getElementById("upload_" + (upload_file_id - 1) + "_input").value != "") {
            var a = document.createElement("input");
            a.setAttribute("type", "file");
            a.setAttribute("class", "upload_confirm_file");
            a.setAttribute("onchange", "create_upload_file_info(this.value," + upload_file_id + ",'new')");
            a.setAttribute("id", "upload_" + upload_file_id + "_input");

            var f = document.getElementById("b");
            f.appendChild(a);

            $("#upload_" + upload_file_id + "_input").click();
            upload_file_id++;
        }
        else {
            document.getElementById("upload_" + (upload_file_id - 1) + "_input").click();
        }
    }
    else {
        var a = document.createElement("input");
        a.setAttribute("type", "file");
        a.setAttribute("class", "upload_confirm_file");
        a.setAttribute("onchange", "create_upload_file_info(this.value," + upload_file_id + ",'new')");
        a.setAttribute("id", "upload_" + upload_file_id + "_input");

        var f = document.getElementById("b");
        f.appendChild(a);

        $("#upload_" + upload_file_id + "_input").click();
        upload_file_id++;
    }
}

/*function upload_file_check(String) {
	if(String==null){
		var a=document.getElementById("b");
		var b=document.getElementById("upload_"+Id+"_input");
		a.removeChild("b");
		alert("1");
	}
}*/

function re_upload_file(Id) {
    $("#upload_" + Id + "_input").attr("onchange", "create_upload_file_info(this.value," + Id + ",'re')");
    $("#upload_" + Id + "_input").click();
    temp_upload_file = $("#upload_" + Id + "_input").val();
}

function create_upload_file_info(String, Id, Condition) {
    if (String == "") {
    }
    else {
        upload_file_name_arr = String.split('\\');
        upload_file_name = upload_file_name_arr[upload_file_name_arr.length - 1];
        if (Condition == "new") {
            var a = document.createElement("div");
            a.setAttribute("id", "upload_" + Id + "_div");
            a.setAttribute("class", "upload_confirm_file_div");

            var b = document.createElement("input");
            b.setAttribute("id", "upload_" + Id + "_label");
            b.setAttribute("type", "checkbox");
            b.setAttribute("checked", "true");
            b.setAttribute("class", "upload_confirm_file_checkbox");
            b.setAttribute("label", upload_file_name);
            b.setAttribute("name", upload_file_name);
            a.appendChild(b);

            var c = document.createElement("label");
            c.setAttribute("class", "upload_confirm_file_name");
            c.setAttribute("id", "upload_confirm_file_name_" + Id);
            c.innerHTML = upload_file_name;
            a.appendChild(c);

            var d = document.createElement("img");
            d.setAttribute("id", Id + "_DEL");
            d.setAttribute("class", "_DEL");
            d.setAttribute("title", "取消上传此文件");
            d.setAttribute("src", "source/upload_del.png");
            d.setAttribute("onclick", "upload_cancel(this.id)");
            a.appendChild(d);

            var e = document.createElement("img");
            e.setAttribute("id", Id + "_RE");
            e.setAttribute("class", "_RE");
            e.setAttribute("title", "重新选择上传文件");
            e.setAttribute("src", "source/upload_refresh.png");
            e.setAttribute("onclick", "re_upload_file(" + Id + ")");
            a.appendChild(e);

            var f = document.createElement("img");
            f.setAttribute("id", Id + "_CF");
            f.setAttribute("class", "_CF");
            f.setAttribute("title", "单独上传此文件");
            f.setAttribute("src", "source/upload_confirm.png");
            d.setAttribute("onclick", "$(#upload" + Id + "_div).click()");
            a.appendChild(f);

            var g = document.getElementById("b");
            g.appendChild(a);
        }
        else if (Condition == "re") {
            document.getElementById("upload_confirm_file_name_" + Id).innerHTML = upload_file_name;
        }
    }

    /*	var formData = new FormData($("#upload_file")[0]);
        $.ajax({
              url: "/Admin/ContentManage/SaveEdit",
              type: "POST",
              data: formData,
              contentType: false, //必须false才会避开jQuery对 formdata 的默认处理 XMLHttpRequest会对 formdata 进行正确的处理
              processData: false, //必须false才会自动加上正确的Content-Type
              success: function (data) {
               if (data == "OK") {
                alert("保存成功");
               }
               else {
                alert("保存失败：" + data);
               }
              }
        });*/
}

function tips() {
    alert("此功能未未开放！");
}






/*function hideTip(String) {
    $("#" + String + "_tip").hide();
}*/

/*function hide_err(String) {
    $("#" + String + "_err").hide();
}*/

/*function hide_pass(String) {
    $("#" + String + "_pass").hide();
}*/

function d_left_help_eject() {
    $(".d_left_help").stop();
    $(".d_left_help").animate({
        width: '200'
    }, 200, function () {
        $(".QRcode ").animate({
            width: '110'
        });
    });
}

function d_left_help_inject() {
    $(".d_left_help").stop();
    $(".QRcode").stop();
    $(".QRcode").animate({
        width: '0'
    }, 200, function () {
        $(".d_left_help").animate({
            width: '30'
        }, 200);
    });
}

/*function tip_err(name) {
    $("#" + name).attr("class", "i_user_err");
    $("#" + name + "_tip").attr("class", "tip_err");
    $("#" + name + "_tip").fadeIn();
}*/

/*function tip_pass(name) {
    $("#" + name).attr("class", "i_user_pass");
    $("#" + name + "_tip").attr("class", "tip_pass");
    document.getElementById(name + "_tip").innerHTML = "";
    $("#" + name + "_tip").fadeIn();
}*/

/*function tip(name) {
    $("#" + name + "_tip").attr("class", "tip");
    $("#" + name).attr("class", "i_user");
    $("#" + name + "_tip").hide();
}*/

/*function hidePswErr() {
    $("#userPassword_tip_not_div").hide();
}*/






/*function get_Dir_from_path(String, value) {
    $("#a").hide();
    $("#a").empty();
    $("#a").fadeIn(100);
    current_file_path = String;
    $.ajax({
        type: "post",
        url: "get_Dir_path",
        data: {current_path: String},
        dataType: "json",
        success: function (re_val) {
            var json_data = JSON.parse(re_val);


        }
    });
}*/

/*function get_Dir_from_id(String, require) {
    $.ajax({
        type: "post",
        url: "get_Dir_id",
        data: {current_Dir_id: String},
        dataType: "json",
        success: function (re_val) {
            var json_data = JSON.parse(re_val);
            get_Dir_from_path(json_data.current_this_path + "\\" + json_data.current_this_id);
            if (require == "no_refresh") {
                previous_file_id = json_data.current_this_id;
                current_file_path = json_data.current_this_path + "\\" + json_data.current_this_id;
            }
            else {
                if (previous_file_id == null) {
                    var i = document.createElement("td");
                    i.setAttribute("class", "a_file_delimiter");
                    i.innerText = ">";
                    document.getElementById("file_links").appendChild(i);

                    var l = document.createElement("td");
                    l.setAttribute("id", json_data.current_this_id);
                    l.setAttribute("class", "a_file_links");
                    /!*l.setAttribute("onclick", "file_link(this.id)"); *!/
                    l.innerText = json_data.current_this_name;
                    document.getElementById("file_links").appendChild(l);
                    $("#" + class_type).attr("class", "a_local_");

                    var d = document.createElement("td");
                    d.setAttribute("id", "td" + json_data.current_this_id);
                    d.setAttribute("class", "file_link");
                    document.getElementById("file_links").appendChild(d);
                }
                else {
                    var i = document.createElement("td");
                    i.setAttribute("class", "a_file_delimiter");
                    i.innerText = ">";
                    document.getElementById("td" + previous_file_id).appendChild(i);

                    var l = document.createElement("td");
                    l.setAttribute("id", json_data.current_this_id);
                    l.setAttribute("class", "a_file_links");
                    l.innerText = json_data.current_this_name;
                    document.getElementById("td" + previous_file_id).appendChild(l);

                    var d = document.createElement("td");
                    d.setAttribute("id", "td" + json_data.current_this_id);
                    d.setAttribute("class", "file_link");
                    document.getElementById("td" + previous_file_id).appendChild(d);

                    $("#" + previous_file_id).attr("class", "a_file_links_");
                    $("#" + previous_file_id).attr("onclick", "file_link(this.id)");
                }
            }
            previous_file_id = json_data.current_this_id;
        }
    });
}*/

/*function check_File_encryption(String, type, value) {
    if (type.substring(0, 4) == "txtx") {
        show("open_jm");
        var btn = document.getElementById("setup_B");
        btn.onclick = function () {
            $.ajax({
                type: "post",
                url: "check_file_psw",
                data: {file_jm_open_psw: $("#modality_input_text_A").val(), current_path: String},
                dataType: "json",
                success: function (re_val) {
                    var json_data = JSON.parse(re_val);
                    if (json_data.result == "success") {
                        $("#modality_tip").attr("class", "modality_tip_pass");
                        $("#modality_tip").html("密码通过");
                        interval_a = setInterval("$('#giveup_B').click();clearInterval(interval_a);get_File_from_id(" + String + "," + "'" + type + "'" + "," + "'" + value + "'" + ");", 1000);
                    }
                    else {
                        $("#modality_tip").attr("class", "modality_tip_err");
                        $("#modality_tip").html("密码错误");
                    }
                }
            });
        };
    }
    else {
        get_File_from_id(String, type, value);
    }
}*/

/*function get_File_from_id(String, type, value) {
    $.ajax({
        type: "post",
        url: "get_File_path",
        data: {current_path: String},
        dataType: "json",
        success: function (re_val) {
            var json_data = JSON.parse(re_val);
            if (type.slice(0, 4) == "txts" || type.slice(0, 4) == "txtx") {
                $("#a").hide();
                $("#a").load("opentxt.jsp");
                $("#a").fadeIn(100);
                open_file_content = json_data.file_content;
                open_file_size = json_data.file_size;
                open_file_date = json_data.file_create_date;
                open_file_lastModified_date = json_data.file_lastmodified_date;
                open_file_name = json_data.file_name;
                open_file_path = json_data.file_path;
            } else {
                alert("未开放！");
            }
        }
    });
}*/

//-----
//---字符保存—加密---
function file_jm_save() {
    if ($("#modality_input_text_A").val().length == "0") {
        $("#modality_tip").attr("class", "modality_tip_err");
        $("#modality_tip").html("请输入密码");
    }
    else if ($("#modality_input_text_A").val().indexOf(" ") > -1) {
        $("#modality_tip").attr("class", "modality_tip_err");
        $("#modality_tip").html("不允许有空格");
    }
    else if ($("#modality_input_text_A").val().length != 8) {
        $("#modality_tip").attr("class", "modality_tip_err");
        $("#modality_tip").html("密码必须为8位数字");
    }
    else if ($("#modality_input_text_A").val() != $("#modality_input_text_B").val()) {
        $("#modality_tip").attr("class", "modality_tip_err");
        $("#modality_tip").html("密码与确认密码不一致");
    }
    else {
        $.ajax({
            type: "post",
            url: "file_jm_save",
            data: {
                file_password: $("#modality_input_text_A").val(),
                file_save_name: $("#t_title").val(),
                file_save_content: $("#text_area").val()
            },
            dataType: "json",
            success: function (re_val) {
                var json_data = JSON.parse(re_val);
                if (json_data.result == "success") {
                    $("#modality_tip").attr("class", "modality_tip_pass");
                    $("#modality_tip").html("恭喜，文件已加密");
                    interval_a = setInterval("$('#giveup_B').click();clearInterval(interval_a)", 1000);
                }
                else if (json_data.result == "error") {
                    $("#modality_tip").attr("class", "modality_tip_err");
                    $("#modality_tip").html("加密保存失败，请稍后再试");
                }
            }
        });
    }
}

//------
//--字符操作界面—弹出子菜单---
function eject_file_operate_div(operate) {
    var opentxt_operate_div = document.getElementById("opentxt_operate_div");

    var opentxt_operate_div_back = document.createElement("img");
    opentxt_operate_div_back.setAttribute("src", "source/tool_bar_back.png");
    opentxt_operate_div_back.setAttribute("style", "float: left;cursor: pointer;margin-top: 7px;");
    opentxt_operate_div_back.setAttribute("onclick", "tool_bar_back()");

    var opentxt_operate_div_label = document.createElement("label");
    opentxt_operate_div_label.setAttribute("class", "find_div_label");

    var opentxt_operate_div_target = document.createElement("input");
    opentxt_operate_div_target.setAttribute("type", "text");
    opentxt_operate_div_target.setAttribute("id", "find_data_target");
    opentxt_operate_div_target.setAttribute("class", "find_div_data_target");
    opentxt_operate_div_target.setAttribute("oninput", "find_div_clean_count()");

    var opentxt_operate_div_div_bt = document.createElement("input");
    opentxt_operate_div_div_bt.setAttribute("id", "find_data_div_bt");
    opentxt_operate_div_div_bt.setAttribute("type", "button");
    opentxt_operate_div_div_bt.setAttribute("class", "find_div_bt");
    opentxt_operate_div_div_bt.setAttribute("onclick", "find_data()");

    var opentxt_operate_div_checkbox = document.createElement("input");
    opentxt_operate_div_checkbox.setAttribute("id", "find_data_div_checkbox");
    opentxt_operate_div_checkbox.setAttribute("type", "checkbox");
    opentxt_operate_div_checkbox.setAttribute("style", "float: left;margin-top: 11px;margin-left: 15px;");

    var opentxt_operate_div_checkbox_label = document.createElement("label");
    opentxt_operate_div_checkbox_label.setAttribute("class", "find_div_checkbox");

    var opentxt_operate_div_result = document.createElement("div");
    opentxt_operate_div_result.setAttribute("id", "find_data_div_result");
    opentxt_operate_div_result.setAttribute("style", "float: left;display:none;");

    var opentxt_operate_div_result_label_a = document.createElement("label");
    opentxt_operate_div_result_label_a.setAttribute("class", "find_div_result");
    opentxt_operate_div_result_label_a.innerText = "查找结果：共有";

    var opentxt_operate_div_result_count = document.createElement("label");
    opentxt_operate_div_result_count.setAttribute("id", "find_data_div_result_count");
    opentxt_operate_div_result_count.setAttribute("class", "find_div_result");
    opentxt_operate_div_result_count.setAttribute("style", "color: red;margin-left: 0px;");

    var opentxt_operate_div_result_label_b = document.createElement("label");
    opentxt_operate_div_result_label_b.setAttribute("class", "find_div_result");
    opentxt_operate_div_result_label_b.setAttribute("style", "margin-left: 0px;");
    opentxt_operate_div_result_label_b.innerText = "个";

    var opentxt_operate_div_result_target_label = document.createElement("label");
    opentxt_operate_div_result_target_label.setAttribute("class", "find_div_result");
    opentxt_operate_div_result_target_label.setAttribute("id", "find_data_div_target_label");
    opentxt_operate_div_result_target_label.setAttribute("style", "color: red;font-size: 17px;font-weight: bold;margin-left: 0px;margin-top: 4px;");

    var opentxt_operate_div_result_label_c = document.createElement("label");
    opentxt_operate_div_result_label_c.setAttribute("class", "find_div_result");
    opentxt_operate_div_result_label_c.setAttribute("style", "margin-left: 0px;");

    var opentxt_operate_div_result_target_count = document.createElement("label");
    opentxt_operate_div_result_target_count.setAttribute("class", "find_div_result");
    opentxt_operate_div_result_target_count.setAttribute("id", "find_data_div_target_count");
    opentxt_operate_div_result_target_count.setAttribute("style", "color: green;margin-left: 0px;");

    var opentxt_operate_div_result_label_d = document.createElement("label");
    opentxt_operate_div_result_label_d.setAttribute("class", "find_div_result");
    opentxt_operate_div_result_label_d.setAttribute("style", "margin-left: 0px;");
    opentxt_operate_div_result_label_d.innerText = "个";

    var opentxt_operate_div_result_before = document.createElement("input");
    opentxt_operate_div_result_before.setAttribute("id", "find_data_div_before");
    opentxt_operate_div_result_before.setAttribute("type", "button");
    opentxt_operate_div_result_before.setAttribute("value", "查找上一个");
    opentxt_operate_div_result_before.setAttribute("class", "find_div_data_next");
    opentxt_operate_div_result_before.setAttribute("style", "margin-left: 10px;");
    opentxt_operate_div_result_before.setAttribute("onclick", "find_data_order(this.id)");

    var opentxt_operate_div_result_next = document.createElement("input");
    opentxt_operate_div_result_next.setAttribute("id", "find_data_div_next");
    opentxt_operate_div_result_next.setAttribute("type", "button");
    opentxt_operate_div_result_next.setAttribute("value", "查找下一个");
    opentxt_operate_div_result_next.setAttribute("class", "find_div_data_next");
    opentxt_operate_div_result_next.setAttribute("style", "margin-left: 5px;");
    opentxt_operate_div_result_next.setAttribute("onclick", "find_data_order(this.id)");

    var opentxt_operate_div_result_finish = document.createElement("input");
    opentxt_operate_div_result_finish.setAttribute("id", "find_data_div_finish");
    opentxt_operate_div_result_finish.setAttribute("type", "button");
    opentxt_operate_div_result_finish.setAttribute("value", "查找完毕！");
    opentxt_operate_div_result_finish.setAttribute("class", "find_div_data_next");
    opentxt_operate_div_result_finish.setAttribute("style", "color: red;background-color: white;cursor: default;display: none;");


    if (operate == "find_data") {
        opentxt_operate_div_label.innerText = "请输入需要查找的字符：";

        var opentxt_operate_div_div = document.createElement("div");
        opentxt_operate_div_div.setAttribute("style", "float: left;width: 60px;");

        var opentxt_operate_div_div_stop_bt = document.createElement("img");
        opentxt_operate_div_div_stop_bt.setAttribute("id", "find_data_div_stop_bt");
        opentxt_operate_div_div_stop_bt.setAttribute("src", "source/find_stop_red.png");
        opentxt_operate_div_div_stop_bt.setAttribute("class", "find_div_stop_bt");
        opentxt_operate_div_div_stop_bt.setAttribute("onclick", "find_div_stop()");

        var opentxt_operate_div_div_pause_bt = document.createElement("img");
        opentxt_operate_div_div_pause_bt.setAttribute("id", "find_data_div_pause_bt");
        opentxt_operate_div_div_pause_bt.setAttribute("src", "source/find_pause_green.png");
        opentxt_operate_div_div_pause_bt.setAttribute("class", "find_div_pause_bt");
        opentxt_operate_div_div_pause_bt.setAttribute("onclick", "find_div_pause()");

        var opentxt_operate_div_div_play_bt = document.createElement("img");
        opentxt_operate_div_div_play_bt.setAttribute("id", "find_data_div_play_bt");
        opentxt_operate_div_div_play_bt.setAttribute("src", "source/find_play_green.png");
        opentxt_operate_div_div_play_bt.setAttribute("class", "find_div_play_bt");
        opentxt_operate_div_div_play_bt.setAttribute("onclick", "find_div_play()");

        opentxt_operate_div_checkbox_label.innerText = "开启自动查找";
        opentxt_operate_div_result_label_c.innerText = "，已选中第";

        opentxt_operate_div_div.appendChild(opentxt_operate_div_div_bt);
        opentxt_operate_div_div.appendChild(opentxt_operate_div_div_stop_bt);
        opentxt_operate_div_div.appendChild(opentxt_operate_div_div_pause_bt);
        opentxt_operate_div_div.appendChild(opentxt_operate_div_div_play_bt);

        opentxt_operate_div_result.appendChild(opentxt_operate_div_result_label_a);
        opentxt_operate_div_result.appendChild(opentxt_operate_div_result_count);
        opentxt_operate_div_result.appendChild(opentxt_operate_div_result_label_b);
        opentxt_operate_div_result.appendChild(opentxt_operate_div_result_target_label);
        opentxt_operate_div_result.appendChild(opentxt_operate_div_result_label_c);
        opentxt_operate_div_result.appendChild(opentxt_operate_div_result_target_count);
        opentxt_operate_div_result.appendChild(opentxt_operate_div_result_label_d);
        opentxt_operate_div_result.appendChild(opentxt_operate_div_result_before);
        opentxt_operate_div_result.appendChild(opentxt_operate_div_result_next);
        opentxt_operate_div_result.appendChild(opentxt_operate_div_result_finish);

        opentxt_operate_div.appendChild(opentxt_operate_div_back);
        opentxt_operate_div.appendChild(opentxt_operate_div_label);
        opentxt_operate_div.appendChild(opentxt_operate_div_target);
        opentxt_operate_div.appendChild(opentxt_operate_div_div_bt);
        opentxt_operate_div.appendChild(opentxt_operate_div_div);
        opentxt_operate_div.appendChild(opentxt_operate_div_checkbox);
        opentxt_operate_div.appendChild(opentxt_operate_div_checkbox_label);
        opentxt_operate_div.appendChild(opentxt_operate_div_result);
    }
    else if (operate == "replace_data") {
        opentxt_operate_div_label.innerText = "请输入需要替换的字符：";

        var opentxt_operate_div_result_label_e = document.createElement("label");
        opentxt_operate_div_result_label_e.setAttribute("class", "find_div_label");
        opentxt_operate_div_result_label_e.setAttribute("style", "margin-left: 5px;");
        opentxt_operate_div_result_label_e.innerText = "替换为：";

        var opentxt_operate_div_target_re = document.createElement("input");
        opentxt_operate_div_target_re.setAttribute("type", "text");
        opentxt_operate_div_target_re.setAttribute("id", "replace_data_div_target");
        opentxt_operate_div_target_re.setAttribute("style", "color: green;");
        opentxt_operate_div_target_re.setAttribute("class", "find_div_data_target");

        var opentxt_operate_div_bt_re = document.createElement("input");
        opentxt_operate_div_bt_re.setAttribute("id", "repalce_div_bt");
        opentxt_operate_div_bt_re.setAttribute("type", "button");
        opentxt_operate_div_bt_re.setAttribute("class", "replace_div_bt");
        opentxt_operate_div_bt_re.setAttribute("onclick", "replace_data()");

        opentxt_operate_div_checkbox.setAttribute("id", "replace_data_div_checkbox");
        opentxt_operate_div_checkbox_label.innerText = "开启全局替换";
        opentxt_operate_div_result_label_c.innerText = "，已替换";
        opentxt_operate_div_result_target_count.setAttribute("id", "repalce_data_div_target_count");
        opentxt_operate_div_result_target_count.innerText = "0";

        opentxt_operate_div_result.appendChild(opentxt_operate_div_result_label_a);
        opentxt_operate_div_result.appendChild(opentxt_operate_div_result_count);
        opentxt_operate_div_result.appendChild(opentxt_operate_div_result_label_b);
        opentxt_operate_div_result.appendChild(opentxt_operate_div_result_target_label);
        opentxt_operate_div_result.appendChild(opentxt_operate_div_result_label_c);
        opentxt_operate_div_result.appendChild(opentxt_operate_div_result_target_count);
        opentxt_operate_div_result.appendChild(opentxt_operate_div_result_label_d);
        opentxt_operate_div_result.appendChild(opentxt_operate_div_result_before);
        opentxt_operate_div_result.appendChild(opentxt_operate_div_result_next);
        opentxt_operate_div_result.appendChild(opentxt_operate_div_result_finish);

        opentxt_operate_div.appendChild(opentxt_operate_div_back);
        opentxt_operate_div.appendChild(opentxt_operate_div_label);
        opentxt_operate_div.appendChild(opentxt_operate_div_target);
        opentxt_operate_div.appendChild(opentxt_operate_div_div_bt);
        opentxt_operate_div.appendChild(opentxt_operate_div_result_label_e);
        opentxt_operate_div.appendChild(opentxt_operate_div_target_re);
        opentxt_operate_div.appendChild(opentxt_operate_div_bt_re);
        opentxt_operate_div.appendChild(opentxt_operate_div_checkbox);
        opentxt_operate_div.appendChild(opentxt_operate_div_checkbox_label);
        opentxt_operate_div.appendChild(opentxt_operate_div_result);
    }
    $("#text_area").attr("readonly", "readonly");
    $("#tool_bar_main").animate({
        marginLeft: -$("#tool_bar_main").width(),
    }, 500, function () {
        $("#opentxt_operate_div").fadeIn();
    });
}

//------
//---字符操作界面—返回主菜单---
function tool_bar_back() {
    $("#text_area_span").text("");
    $("#text_area_span").css("margin-top", "2px");
    $("#opentxt_operate_div").fadeOut();
    clearInterval(interval_b);
    $("#tool_bar_main").animate({
        marginLeft: "0px",
    }, 500, function () {
        $("#text_area").removeAttr("readonly");
        $("#opentxt_operate_div").empty();
    });
}

//-----
//---字符查找---
function find_data() {
    find_div_clean_count();
    text_area = document.getElementById("text_area");
    text_area_value = text_area.value.toString();
    find_data_div_target = $("#find_data_target").val();
    start_array = new Array();
    while (start < text_area_value.length) {
        index = text_area_value.substring(start, text_area_value.length).indexOf(find_data_div_target);
        if (index != -1) {
            write_to_the_span_string_sign = "<input type='button' class='a_' id=a" + find_data_div_count + "></a>";
            write_to_the_span_string += text_area_value.substring(start, 1 + start + index).replace(/ /g, "&nbsp;") + write_to_the_span_string_sign;
            if (find_data_div_target_count == 0) {
                find_data_div_target_count++;
                write_to_the_span(write_to_the_span_string, "0");
                find_data_select(index, index + 1, find_data_div_target_count);
            }
            start = 1 + start + index;
            find_data_div_count++;
            start_array[find_data_div_count] = start;
        }
        else {
            write_to_the_span_string += text_area_value.substring(start, text_area_value.length).replace(/ /g, "&nbsp;");
            write_to_the_span(write_to_the_span_string, "1");
            start = text_area_value.length;
        }
    }
    $('#find_data_div_result_count').html(find_data_div_count);
    $('#find_data_div_target_label').html('"' + find_data_div_target + '"');
    $('#find_data_div_result').fadeIn(300);
    if (find_data_div_count == 1) {
        $('#find_data_div_finish').show();
        $("#find_data_div_finish").css("margin-left", "7px");
    }
    else {
        $('#find_data_div_finish').show();
        $("#find_data_div_finish").css("margin-left", "-160px");
        $("#find_data_div_before").show();
        $("#find_data_div_next").show();
    }
    if ($("#find_data_div_checkbox").is(":checked")) {
        interval_b = setInterval('auto_find_data();', 1000);
        $("#find_data_div_stop_bt").show();
        $("#find_data_div_pause_bt").show();
        $("#find_data_target").attr("disabled", "disabled");
        $("#find_data_target").attr("class", "find_div_data_target_disabled");
        $("#find_data_div_before").attr("disabled", "disabled");
        $("#find_data_div_next").attr("disabled", "disabled");
        $("#find_data_div_play_bt").hide();
        $("#find_data_div_bt").hide();
    }
    $("#find_data_div_checkbox").attr("disabled", "disabled");
}

//------
//---字符查找—倒序&正序---
function find_data_order(order) {
    $("#text_area").stop();
    if (order == "find_data_div_next") {
        if (find_data_div_target_count < find_data_div_count) {
            find_data_div_target_count++;
            find_data_select(start_array[find_data_div_target_count] - 1, start_array[find_data_div_target_count], find_data_div_target_count);
            $('#find_data_div_finish').hide();
            if (find_data_div_target_count == find_data_div_count) {
                $('#find_data_div_finish').css("margin-left", "-80px");
                $('#find_data_div_finish').show();
            }
        }
    }
    else {
        if (find_data_div_target_count > 1) {
            find_data_div_target_count--;
            find_data_select(start_array[find_data_div_target_count] - 1, start_array[find_data_div_target_count], find_data_div_target_count);
            $('#find_data_div_finish').hide();
            if (find_data_div_target_count == 1) {
                $('#find_data_div_finish').css("margin-left", "-160px");
                $('#find_data_div_finish').show();
            }
        }
    }
}

//-----
//---字符查找—向位置层写入字符---
function write_to_the_span(string, condition) {
    var a = string.replace(/\n/g, "<br/>");
    if (condition == "1") {
        if (index = a.lastIndexOf("<br/>") != -1) {
            a += "<br/><br/>";
        }
    }
    $("#text_area_span").html(a);
    $("#a0").attr("class", "a_select");
}

//-----
//---字符查找—选中字符---
var history_of_sign = 0;

function find_data_select(a, b, c) {
    $("#text_area_span").css("margin-top", -$("#text_area").height());
    text_area.setSelectionRange(a, b);
    text_area.focus();
    var sign = document.getElementById("a" + (c - 1));
    $("#a" + (c - 1)).attr("class", "a_select");
    $("#a" + history_of_sign).attr("class", "a_");
    /*	alert(sign.offsetTop);*/
    $("#text_area").animate({
        scrollTop: sign.offsetTop - 300,
    });
    $('#find_data_div_target_count').html(c);
    history_of_sign = c - 1;
}

//-----
//---开启自动查找功能---
function auto_find_data() {
    find_data_div_target_count++;
    find_data_select(start_array[find_data_div_target_count] - 1, start_array[find_data_div_target_count], find_data_div_target_count);
    $("#find_data_div_finish").hide();
    if (find_data_div_target_count == find_data_div_count) {
        $("#find_data_div_finish").css("margin-left", "-80px");
        $("#find_data_div_finish").show();
        find_div_stop();
    }
}

//-----
//---字符查找—结束自动查找---
function find_div_stop() {
    clearInterval(interval_b);
    $("#find_data_div_stop_bt").hide();
    $("#find_data_div_pause_bt").hide();
    $("#find_data_div_play_bt").hide();
    $("#find_data_div_bt").show();
    $("#find_data_target").removeAttr("disabled");
    $("#find_data_div_before").removeAttr("disabled");
    $("#find_data_div_next").removeAttr("disabled");
    $("#find_data_div_checkbox").removeAttr("disabled");
    $("#find_data_div_target").removeAttr("disabled");
    $("#find_data_div_checkbox").attr("checked", false);
    $("#find_data_target").attr("class", "find_div_data_target");
}

//-----
//---字符查找—暂停自动查找---
function find_div_pause() {
    clearInterval(interval_b);
    $("#find_data_div_pause_bt").hide();
    $("#find_data_div_play_bt").show();
}

//-----
//---字符查找—继续自动查找---
function find_div_play() {
    $("#find_data_div_pause_bt").show();
    $("#find_data_div_play_bt").hide();
    interval_b = setInterval("auto_find_data()", 1000);
}

//-----
//---字符查找—当输入一个字符时，清空缓存---
function find_div_clean_count() {
    start = 0;
    find_data_div_count = 0;
    write_to_the_span_string = "";
    write_to_the_span_string_sign = "";
    find_data_div_target_count = 0;
    find_data_div_target_count = 0;
    $("#find_data_div_play_bt").hide();
    $("#find_data_div_result").hide();
    $("#text_area_span").text("");
    $("#find_data_div_before").hide();
    $("#find_data_div_next").hide();
    $("#find_data_div_finish").hide();
    $("#find_data_div_checkbox").removeAttr("disabled");
    clearInterval(interval_b);
}

//-----
//---字符替换---
var count = 0;
history_of_replace = -1;

function replace_data() {
    if (history_of_replace != find_data_div_target_count) {
        var replace_path_front = text_area.value.substring(0, start_array[find_data_div_target_count] - 1);
        var replace_path_rear = text_area.value.substring(start_array[find_data_div_target_count]);
        $("#repalce_data_div_target_count").html(++count);
        text_area.value = replace_path_front + $("#replace_data_div_target").val() + replace_path_rear;
        $("#a" + (find_data_div_target_count - 1)).css({
            "background-color": "rgba(0,255,0,0.8)",
            "border": "1px solid rgba(0,255,0,1)"
        });
    }
    history_of_replace = find_data_div_target_count;
}



//--- Create Form Jump --Start --
function newFormJump(jumpVal, body) {
    var jumpForm = document.createElement("form");
    jumpForm.setAttribute("action", jumpVal);
    jumpForm.setAttribute("method", "post");
    jumpForm.setAttribute("style", "display : none")
    body.appendChild(jumpForm);
    $("#form" + jumpVal).submit();
    body.removeChild(jumpForm);
}

//--- Create Form Jump --End --

function isNullOrSpace(target) {
    if(target == null || target.toString().trim() == ""){
        return true;
    } else {
        return false;
    }
}
