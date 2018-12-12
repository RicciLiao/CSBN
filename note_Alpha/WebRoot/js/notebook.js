/*
*   only use by notebook.jsp
* */
var create_type = 0;
var temp_onclick = null;
var temp_val = "";
var current_item_guid = null;
var previous_item_link_guid = null;
var current_item_name = null;
var class_type;
var interval_a = null;
var itemContent;
var itemSize;
var itemCreateDate;
var itemLastModifyDate;
var itemName;
var itemGuid;
var is_sup_item_search = false;

function show(String) {
    var a = document.createElement("div");
    a.setAttribute("class", "modality_content");

    var b = document.createElement("div");
    b.setAttribute("class", "modality_titile");

    var c = document.createElement("label");
    c.setAttribute("class", "modality_titile_label");
    b.appendChild(c);

    var d = document.createElement("label");
    d.setAttribute("class", "modality_label");


    var e = document.createElement("input");
    e.setAttribute("type", "text");
    e.setAttribute("id", "modality_input_text_A");
    e.setAttribute("class", "modality_input_text");

    var f = document.createElement("input");
    f.setAttribute("type", "button");
    f.setAttribute("value", "创建");
    f.setAttribute("class", "modality_setup_B");
    f.setAttribute("id", "setup_B");
    f.setAttribute("onclick", "create()");

    var j = document.createElement("span");
    j.setAttribute("id", "modality_tip");
    j.setAttribute("class", "");

    var g = document.createElement("input");
    g.setAttribute("type", "button");
    g.setAttribute("value", "放弃");
    g.setAttribute("class", "modality_giveup_B");
    g.setAttribute("id", "giveup_B");
    g.setAttribute("onclick", "jQuery('.modality').fadeOut(500);jQuery('.modality').empty()");
    if (String == "add_file_dir" || String == "add_file_txts" || String == "add_file_rec" || String == "add_file_rec" || String == "add_file_vid" || String == "save_jm"
        || String == "upload_file" || String == "open_jm") {
        if (String == "add_file_dir") {
            d.innerHTML = "请输入新建文件夹的名字";
            c.innerHTML = "新建文件夹";
            create_type = 1;
        }
        else if (String == "add_file_txts") {
            d.innerHTML = "请输入新建云记本的名字";
            c.innerHTML = "新建云记本";
            create_type = 2;
        }
        else if (String == "add_file_rec") {
            d.innerHTML = "请输入新建云录音的名字";
            c.innerHTML = "新建云录音";
            create_type = 3;
        }
        else if (String == "add_file_vid") {
            d.innerHTML = "请输入新建云视频的名字";
            c.innerHTML = "新建云视频";
            create_type = 4;
        }
        else if (String == "save_jm") {
            d.innerHTML = "请输入加密密码";
            c.innerHTML = "加密保存";
            e.setAttribute("type", "password");
            e.setAttribute("placeholder", "密码为4至8个数字");
            f.setAttribute("value", "加密");
            f.setAttribute("onclick", "en_save_()");

            var h = document.createElement("label");
            h.setAttribute("class", "modality_label");
            h.innerHTML = "请再次输入密码";

            var i = document.createElement("input");
            i.setAttribute("type", "password");
            i.setAttribute("id", "modality_input_text_B");
            i.setAttribute("class", "modality_input_text");

        }
        else if (String == "upload_file") {
            // pending
        }
        else if (String == "open_jm") {
            d.innerHTML = "请输入此文件的密码";
            c.innerHTML = "打开加密文件";
            f.setAttribute("value", "打开");
            f.setAttribute("onclick", null);
            e.setAttribute("type", "password");
        }
        a.appendChild(b);
        a.appendChild(d);
        a.appendChild(e);
        if (h != undefined) {
            a.appendChild(h);
        }
        if (i != undefined) {
            a.appendChild(i);
        }
        a.appendChild(f);
        a.appendChild(g);
        a.appendChild(j);

        document.getElementById("modality").appendChild(a);
        $("#modality").show();
        $("#modality_input_text_A").focus();
    }
    else if (String == "add_file_other") {
        $("#add_file_other").val("新建其他  | ◀");
        $("#add_file_other").attr("onclick", "hide(this.id)");
        $("#add_file_rec").show();
        $("#add_file_vid").animate({
            marginLeft: $("#add_file_other").outerWidth(true) - 1
        }, 400);
        $("#add_file_rec").animate({
            marginLeft: $("#add_file_other").outerWidth(true) - 1,
        }, 400, function () {
            $("#add_file_other").attr("class", "add_file_other_border");
            $("#add_file_vid").show();
            $("#add_file_vid").animate({
                marginLeft: $("#add_file_vid").outerWidth(true) - 2,
            }, 300);
        });
    }
}

function hide(String) {
    $("#add_file_other").val("新建其他  | ▶");
    $("#add_file_other").attr("onclick", "show(this.id)");
    $("#add_file_vid").animate({
        marginLeft: $("#add_file_rec").outerWidth(true) - '112',
    }, 400, function () {
        $("#add_file_vid").hide();
        $("#add_file_rec").animate({
            marginLeft: $("#add_file_other").outerWidth(true) - '125',
        }, 300, function () {
            $("#add_file_rec").hide();
            $("#add_file_other").attr("class", "add_");
        });
    });
}

function create() {
    if ($("#modality_input_text_A").val().trim().length == 0) {
        $("#modality_tip").text("名字不能为空！");
        $("#modality_tip").attr("class", "modality_tip_err");
    }
    else {
        $.ajax({
            type: "post",
            url: "createItem",
            data: {
                itemType: create_type,
                itemName: $("#modality_input_text_A").val(),
                itemGuid: current_item_guid,
                //class_type: class_type,
                userId: $("#current_user_id").val()
            },
            dataType: "json",
            success: function (re_val) {
                var json_data = JSON.parse(re_val);
                if (json_data.result == "success") {
                    if (class_type == "allfile") {
                        if (current_item_guid == null) {
                            loadUserSpace();
                        } else {
                            loadItem(current_item_guid, false);
                        }
                    }
                    else {
                        file_link(previous_item_link_guid);
                    }
                    if (create_type == _ITEM_TYPE_DIR) {
                        $('#file_result_a').html("文件夹 <label style='color:blue'>" + $("#modality_input_text_A").val() + "</label>创建成功！");
                    }
                    else {
                        $('#file_result_a').html("文件 <label style='color:blue'>" + $("#modality_input_text_A").val() + "</label>创建成功！");
                    }
                    $('#file_result').fadeIn(500);
                    interval_c = setInterval("$('#file_result').fadeOut(500);clearInterval(interval_c)", 1500);
                }
                else if (json_data.result == "error") {
                    alert("err");
                }
                $('#giveup_B').click();
                bar();
            }
        });
    }
}

function loadUserSpace() {
    current_item_guid = null;
    $.ajax({
        type: "post",
        url: "loadUserSpace",
        dataType: "json",
        success: function (re_val) {
            var json_data = JSON.parse(re_val);
            buildItemStructure(json_data);
        }
    });
    bar();
}

function loadItem(id, type) {
    current_item_guid = id;
    current_item_name = $("#" + current_item_guid).children("input").val();
    $.ajax({
        type: "post",
        url: "loadItem",
        data: {itemGuid: id},
        dataType: "json",
        success: function (re_val) {
            var json_data = JSON.parse(re_val);
            if (json_data.loadType == _ITEM_TYPE_DIR) {
                buildItemStructure(json_data);
                if (type) {
                    if (previous_item_link_guid != null) {
                        var i = document.createElement("td");
                        i.setAttribute("class", "a_file_delimiter");
                        i.innerText = ">";
                        document.getElementById("td" + previous_item_link_guid).appendChild(i);

                        var l = document.createElement("td");
                        l.setAttribute("id", current_item_guid);
                        l.setAttribute("class", "a_file_links");
                        /*l.setAttribute("onclick", "file_link(this.id)"); */
                        l.innerText = current_item_name;
                        document.getElementById("td" + previous_item_link_guid).appendChild(l);

                        var d = document.createElement("td");
                        d.setAttribute("id", "td" + current_item_guid);
                        d.setAttribute("class", "file_link");
                        document.getElementById("td" + previous_item_link_guid).appendChild(d);

                        $("#" + previous_item_link_guid).attr("class", "a_file_links_");
                        $("#" + previous_item_link_guid).attr("onclick", "file_link(this.id)");
                    } else {
                        var i = document.createElement("td");
                        i.setAttribute("class", "a_file_delimiter");
                        i.innerText = ">";
                        document.getElementById("file_links").appendChild(i);

                        var l = document.createElement("td");
                        l.setAttribute("id", current_item_guid);
                        l.setAttribute("class", "a_file_links");
                        /*l.setAttribute("onclick", "file_link(this.id)"); */
                        l.innerText = current_item_name;
                        document.getElementById("file_links").appendChild(l);
                        $("#" + class_type + "td").attr("class", "a_local_");

                        var d = document.createElement("td");
                        d.setAttribute("id", "td" + current_item_guid);
                        d.setAttribute("class", "file_link");
                        document.getElementById("file_links").appendChild(d);
                    }
                }
                previous_item_link_guid = current_item_guid;
            } else if (json_data.loadType == _ITEM_TYPE_TXT) {
                $("#a").load("opentxt.jsp");
                itemContent = json_data.itemContent;
                itemSize = json_data.itemSize;
                itemCreateDate = json_data.itemCreateDate;
                itemLastModifyDate = json_data.itemLastModifyDate;
                itemName = json_data.itemName;
                itemGuid = json_data.itemGuid;
            }
        }
    });
}

function buildItemStructure(json_data) {
    $("#a").empty();
    var itemCount = json_data.itemInfo.length;
    if (itemCount == "0") {
        var d = document.createElement("img");
        d.setAttribute("src", "source/center.png");
        d.setAttribute("class", "img_center");
        document.getElementById("a").appendChild(d);
    } else {
        for (var i = 0; i < itemCount; i++) {
            var d = document.createElement("div");
            d.setAttribute("id", json_data.itemInfo[i].itemGuid);
            d.setAttribute("onmouseover", "showBtnContainer(this.id)");
            d.setAttribute("onmouseout", "hideBtnContainer(this.id)");
            var a = document.createElement("input");
            var id = json_data.itemInfo[i].itemGuid;
            a.setAttribute("id", id + "rename");
            a.setAttribute("class", "a_file_name");
            a.setAttribute("onclick", "renameItem('" + id + "',this.value)");
            a.setAttribute("onchange", "rename_finish('" + id + "',this.value)");
            a.setAttribute("onblur", "recoverEvent('" + id + "')");
            a.setAttribute("type", "text");
            a.setAttribute("readonly", "true");
            a.value = json_data.itemInfo[i].itemName.toString();
            d.appendChild(a);
            // btn container
            var item_btn_container = document.createElement("div");
            item_btn_container.setAttribute("class", "item_btn_container");
            item_btn_container.setAttribute("id", id + "btn_container");
            // delete btn
            var b = document.createElement("a");
            b.setAttribute("id", id + "delete");
            b.setAttribute("class", "item_delete_btn");
            b.setAttribute("title", "删除");
            b.setAttribute("onclick", "itemDelBtn(this.id)");
            item_btn_container.appendChild(b);
            // --
            if (json_data.itemInfo[i].itemPassword != "") {
                d.setAttribute("class", json_data.itemInfo[i].itemTypeDesc + "_lock_B");
            }
            else {
                d.setAttribute("class", json_data.itemInfo[i].itemTypeDesc + "_B");
            }
            if (json_data.itemInfo[i].itemType == _ITEM_TYPE_DIR) {
                d.setAttribute("onclick", "identifyItemType(this.id)");
            } else {
                // class btn
                b = document.createElement("a");
                b.setAttribute("id", id + "class");
                b.setAttribute("class", "item_class_btn");
                b.setAttribute("title", "分类此文档");
                b.setAttribute("onclick", "itemClassBtn(this.id)");
                item_btn_container.appendChild(b);
                // tag btn
                b = document.createElement("a");
                b.setAttribute("id", id + "tag");
                b.setAttribute("class", "item_tag_btn");
                b.setAttribute("title", "为此文档添加标签");
                b.setAttribute("onclick", "itemTagBtn(this.id)")
                item_btn_container.appendChild(b);
                // share btn
                b = document.createElement("a");
                b.setAttribute("id", id + "share");
                b.setAttribute("class", "item_share_btn");
                b.setAttribute("title", "共享此文档")
                b.setAttribute("onclick", "itemShareBtn(this.id)");
                item_btn_container.appendChild(b);
                // --
                d.setAttribute("onclick", "identifyItemType(this.id)");
            }
            d.appendChild(item_btn_container);
            document.getElementById("a").appendChild(d);
        }
    }
}

function showBtnContainer(id) {
    $("#" + id + "btn_container").stop();
    $("#" + id + "btn_container").fadeIn();
}

function hideBtnContainer(id) {
    $("#" + id + "btn_container").stop();
    $("#" + id + "btn_container").fadeOut();
}

function buttonActive(String, value) {
    previous_item_link_guid = null;
    class_type = String;
    $("#file_links").empty();
    var n = document.createElement("td");
    n.setAttribute("id", String + "td");
    n.setAttribute("class", "a_local");
    n.innerText = value;
    document.getElementById("file_links").appendChild(n);
    $("#a").empty();
    if (String == "allfile") {
        $("#" + String).removeClass("left_B");
        $("#" + String).addClass("left_B_active");
        $("#i_allfile").attr("src", "source/file_a_.png");
        //loadUserSpace();
        if (String != "recyclebin" && sessionStorage.getItem("back") == "recyclebin") {
            $("#recyclebin").removeClass("left_B_active");
            $("#recyclebin").addClass("left_B");
            $("#i_recyclebin").attr("src", "source/recycle_bin_black.png");
            alert("此区域未开放");
        }
    }
    else if (String == "recyclebin") {
        $("#" + String).removeClass("left_B");
        $("#" + String).addClass("left_B_active");
        $("#i_recyclebin").attr("src", "source/recycle_bin_red.png");
        /*		get_Dir_from_path($("#user_root_path").val(),value);
                current_file_path=$("#user_root_path").val();*/
        if (String != "allfile" && sessionStorage.getItem("back") == "allfile") {
            $("#allfile").removeClass("left_B_active");
            $("#allfile").addClass("left_B");
            $("#i_allfile").attr("src", "source/file_a.png");
            alert("此区域未开放");
        }
    }
    else {
        if (String != "allfile" && sessionStorage.getItem("back") == "allfile") {
            $("#allfile").removeClass("left_B_active");
            $("#allfile").addClass("left_B");
            $("#i_allfile").attr("src", "source/file_a.png");
            alert("此区域未开放");
        }
        else if (String != "recyclebin" && sessionStorage.getItem("back") == "recyclebin") {
            $("#recyclebin").removeClass("left_B_active");
            $("#recyclebin").addClass("left_B");
            $("#i_recyclebin").attr("src", "source/recycle_bin_black.png");
            alert("此区域未开放");
        }
    }
    $("#" + sessionStorage.getItem("back")).removeClass("left_B_active");
    $("#" + sessionStorage.getItem("back")).addClass("left_B");
    $("#" + String).removeClass("left_B");
    $("#" + String).addClass("left_B_active");
    n.setAttribute("onclick", "buttonActive(" + "'" + String + "'" + "," + "'" + value + "'" + ")");
    sessionStorage.setItem("back", String);
}

function file_link(String) {
    loadItem(String, false);
    $("#" + String).attr("class", "a_file_links");
    $("#td" + String).empty();
}

function rename_finish(id, value) {
    $.ajax({
        type: "post",
        url: "renameItem",
        data: {reName: value, itemGuid: id},
        dataType: "json",
        success: function (re_val) {
            var json_data = JSON.parse(re_val);
            if (json_data.result == "success") {
                $('#file_result_a').text('文件重命名成功！');
                $('#file_result').fadeIn(500);
                interval_c = setInterval("$('#file_result').fadeOut(500);clearInterval(interval_c)", 1500);
            }
            else {
                $('#file_result_a').text('文件重命名失败，请稍后再试！');
                $('#file_result').fadeIn(500);
                interval_c = setInterval("$('#file_result').fadeOut(500);clearInterval(interval_c)", 1500);
                $("#" + id + "rename").val(temp_val);
            }
        }
    });
}

function renameItem(id, value) {
    $("#" + id + "rename").removeAttr("readonly");
    temp_onclick = document.getElementById(id).onclick;
    $("#" + id).removeAttr("onclick");
    document.getElementById(id + "rename").setSelectionRange(0, value.length);
    temp_val = value;
}

function identifyItemType(id) {
    $.ajax({
        type: "post",
        url: "identifyItemType",
        data: {itemGuid: id},
        dataType: "json",
        success: function (re_val) {
            var json_data = JSON.parse(re_val);
            if (json_data.result == _ITEM_TYPE_DIR) {
                loadItem(id, true);
            } else if (json_data.result == _ITEM_TYPE_TXT) {
                checkItemEnc(id);
            }
        }
    });
}

function checkItemEnc(id) {
    $.ajax({
        type: "post",
        url: "checkItemEnc",
        data: {itemGuid: id},
        dataType: "json",
        success: function (re_val) {
            var json_data = JSON.parse(re_val);
            if (isNullOrSpace(json_data.result)) {
                loadItem(id);
            } else {
                show("open_jm");
                var btn = document.getElementById("setup_B");
                btn.onclick = function () {
                    $.ajax({
                        type: "post",
                        url: "checkItemPsw",
                        data: {
                            itemGuid: id,
                            itemPsw: $("#modality_input_text_A").val()
                        },
                        dataType: "json",
                        success: function (re_val) {
                            var json_data = JSON.parse(re_val);
                            if (json_data.result == "success") {
                                $("#modality_tip").attr("class", "modality_tip_pass");
                                $("#modality_tip").html("密码通过");
                                interval_a = setInterval("$('#giveup_B').click();clearInterval(interval_a);loadItem('" + id + "', false);", 1000);
                            }
                            else {
                                $("#modality_tip").attr("class", "modality_tip_err");
                                $("#modality_tip").html("密码错误");
                            }
                        }
                    });
                }
            }
        }
    });
}

function buildItemContent() {
    document.getElementById("text_area").value = itemContent;
    document.getElementById("l_size").innerHTML = itemSize;
    document.getElementById("l_date").innerHTML = itemCreateDate;
    if (isNullOrSpace(itemLastModifyDate)) {
        document.getElementById("l_mdate").innerHTML = "您还未修改过这个文件哦 ^_^";
    }
    else {
        document.getElementById("l_mdate").innerHTML = itemLastModifyDate;
    }
    document.getElementById("t_title").value = itemName;
    document.getElementById("current_item_guid").value = itemGuid;
}

function recoverEvent(id) {
    document.getElementById(id).onclick = temp_onclick;
}

//---用户信息—保持弹出---
function show_user_info() {
    $("#user_info_div").stop();
    clearInterval(interval);
    $("#user_info_div").show();
}

//-----
//---用户信息—延迟隐藏---
function hide_user_info() {
    $("#user_info_div").stop();
    interval = setInterval("$('#user_info_div').fadeOut(100)", 500);
}

//-----
//---主界面收起&弹出(左右)---
function pickUpLeft(String) {
    if (String == "pick_up_left") {
        $("#center").animate({
            width: '98.5%'
        }, 200, function () {
            if (document.getElementById("tool_bar_main") == null) {
            }
            else {
                if (document.getElementById("tool_bar_main").style.marginLeft != "0px") {
                    $("#tool_bar_main").animate({
                        marginLeft: -document.getElementById("tool_bar_main").offsetWidth,
                    }, 300);
                }
            }
        });
        $("#left").animate({
            left: "-165"
        }, 300, function () {
            $("#pick_up_left").attr("src", "source/eject_left.png");
            $("#pick_up_left").attr("class", "eject_left");
            $("#pick_up_left").attr("title", "点击这里可以弹出左侧栏哦  ^_^");
            $("#pick_up_left").attr("id", "eject_left");
        });
    }
    else {
        $("#center").animate({
            width: '88%'
        }, 400, function () {
            if (document.getElementById("tool_bar_main") == null) {
            }
            else {
                if (document.getElementById("tool_bar_main").style.marginLeft !== "0px") {
                    $("#tool_bar_main").animate({
                        marginLeft: -document.getElementById("tool_bar_main").offsetWidth,
                    }, 300);
                }
            }
        });
        $("#left").animate({
            left: "0"
        }, 300, function () {
            $("#eject_left").attr("src", "source/pick_up_left.png");
            $("#eject_left").attr("class", "pick_up_left");
            $("#eject_left").attr("title", "点击这里可以收起左侧栏哦 ~");
            $("#eject_left").attr("id", "pick_up_left");
        });
    }
}

//-----
//---计算&显示空间大小---
function bar() {
    $.ajax({
        type: "post",
        url: "getItemSize",
        dataType: "json",
        success: function (re_val) {
            var json_data = JSON.parse(re_val);
            var file_size_percentage = (json_data.itemSize / 1073741824) / ($("#current_user_size").val().substring(0, 1)) * 100;
            if (file_size_percentage.toString().substring(1, 2) == ".") {
                $("#progress").html(file_size_percentage.toString().substring(0, 1) + "%");
            } else {
                $("#progress").html(file_size_percentage.toString().substring(0, 2) + "%");
            }
            var progress = (file_size_percentage.toString().substring(0, 2) / 100) * $("#bar_outside").width();
            $("#bar_inside").animate({
                width: progress,
            }, 800);
            $("#bar_tip").animate({
                marginLeft: progress + 2,
            }, 800, function () {
                $("#surplus_space").html(json_data.strItemSize + "/" + $("#current_user_size").val() + _SPACE_SIZE_UNIT);
                $("#surplus_space").fadeIn(800);
            });
        }
    });
}

//---主界面收起&弹出(上下)---
function pickUpCenter(String) {
    if (String == "pick_up_center") {
        $("#pick_up_center").attr("src", "source/eject_center.png");
        $("#pick_up_center").attr("class", "eject_center");
        $("#toolbar").animate({
            marginTop: "-55",
        }, 300, function () {
            $("#pick_up_center").attr("title", "点击这里可以弹出上侧栏哦  ^_^");
            $("#pick_up_center").attr("id", "eject_center");
            $(".a_content").css("height", "93%");
            $(".div_text").css("height", "89%");
        });
    }
    else {
        $("#eject_center").attr("src", "source/pick_up_center.png");
        $("#eject_center").attr("class", "pick_up_center");
        $("#toolbar").animate({
            marginTop: "0",
        }, 300, function () {
            $("#eject_center").attr("title", "点击这里可以收起上侧栏哦  ^_^");
            $("#eject_center").attr("id", "pick_up_center");
            $(".a_content").css("height", "85%");
            $(".div_text").css("height", "535px");
        });
    }
}

//---字符操作界面--收起&弹出主菜单---
function _tool_bar(String) {
    if (String == "hide_tool_bar") {
        $("#hide_tool_bar").attr("src", "source/eject_center.png");
        $("#hide_tool_bar").attr("class", "show_tool_bar");
        $("#text_area").attr("class", "text_area_");
        $("#tool_bar").animate({
            marginTop: "-36px"
        }, function () {
            $("#hide_tool_bar").attr("title", "点击这里可以弹出上侧编辑栏哦  ^_^");
            $("#hide_tool_bar").attr("id", "show_tool_bar");
        });
    }
    else if (String == "show_tool_bar") {
        $("#show_tool_bar").attr("src", "source/pick_up_center.png");
        $("#show_tool_bar").attr("class", "hide_tool_bar");
        $("#tool_bar").animate({
            marginTop: "0px"
        }, function () {
            $("#text_area").attr("class", "text_area");
            $("#show_tool_bar").attr("title", "点击这里可以收起上侧编辑栏哦  ^_^");
            $("#show_tool_bar").attr("id", "hide_tool_bar");
        });
    }
}

//---字符保存---
function save_() {
    $.ajax({
        type: "post",
        url: "itemSave",
        data: {
            itemName: $("#t_title").val(),
            itemContent: $("#text_area").val(),
            itemGuid: $("#current_item_guid").val()
        },
        dataType: "json",
        success: function (re_val) {
            var json_data = JSON.parse(re_val);
            if (json_data.result == "success") {
                $('#a_file_save_result').text('文件保存成功！');
                $(".file_save_result").fadeIn(500);
                interval_a = setInterval("$('#file_save_result').fadeOut(500);clearInterval(interval_a)", 1500);
            }
            else if (json_data.result == "error") {
                alert("保存失败！请稍后重试！");
            }
        }
    });
}

//---字符保存—加密---
function en_save_() {
    if ($("#modality_input_text_A").val().length == "0") {
        $("#modality_tip").attr("class", "modality_tip_err");
        $("#modality_tip").html("请输入密码");
    }
    else if ($("#modality_input_text_A").val().indexOf(" ") > -1) {
        $("#modality_tip").attr("class", "modality_tip_err");
        $("#modality_tip").html("不允许有空格");
    }
    else if ($("#modality_input_text_A").val().length < 4 || $("#modality_input_text_A").val().length > 8) {
        $("#modality_tip").attr("class", "modality_tip_err");
        $("#modality_tip").html("密码必须为4至8位数字");
    }
    else if ($("#modality_input_text_A").val() != $("#modality_input_text_B").val()) {
        $("#modality_tip").attr("class", "modality_tip_err");
        $("#modality_tip").html("密码与确认密码不一致");
    }
    else {
        $.ajax({
            type: "post",
            url: "itemEnSave",
            data: {
                itemName: $("#t_title").val(),
                itemContent: $("#text_area").val(),
                itemGuid: $("#current_item_guid").val(),
                itemPsw: $("#modality_input_text_A").val()
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

//---文件下载---
function download_this_file() {
    window.location.href = "downloadItem?itemGuid=" + $("#current_item_guid").val();
}

//---文档分类
function itemClassBtn(id) {
    temp_onclick = document.getElementById(id.substring(0, 32)).onclick;
    $("#" + id.substring(0, 32)).removeAttr("onclick");
    var container = document.getElementById("modality");
    var a = document.createElement("div");
    a.setAttribute("class", "modality_content");
    var b = document.createElement("div");
    b.setAttribute("class", "modality_titile");
    a.appendChild(b);
    var c = document.createElement("label");
    c.setAttribute("class", "modality_titile_label");
    c.innerText = "文档分类";
    b.appendChild(c);
    a.appendChild(b);
    var e = document.createElement("div");
    e.setAttribute("style", "margin-left:50px;margin-top:20px");
    var d = document.createElement("label");
    d.setAttribute("class", "modality_label");
    d.setAttribute("style", "width:auto;");
    d.innerText = "文档类别：";
    e.appendChild(d);
    var item_class = document.createElement("select");
    item_class.setAttribute("class", "itemClassSelector");
    item_class.setAttribute("onclick", "iClassSelect(this.value)");
    e.appendChild(item_class);
    a.appendChild(e);
    d = document.createElement("label");
    d.setAttribute("class", "modality_label");
    d.setAttribute("style", "float: left;width:auto;display: block;margin-top:0");
    d.innerText = "类别描述：";
    var n = document.createElement("div");
    n.setAttribute("style", "width:210px;float: left;");
    var item_class_desc = document.createElement("div");
    item_class_desc.setAttribute("style", "margin-left:50px;margin-top:20px;");
    item_class_desc.appendChild(d);
    item_class_desc.appendChild(n);
    a.appendChild(item_class_desc);
    var f = document.createElement("input");
    f.setAttribute("type", "button");
    f.setAttribute("value", "确定");
    f.setAttribute("class", "modality_setup_B");
    f.setAttribute("id", "setup_B");
    f.setAttribute("onclick", "create()");
    a.appendChild(f);
    var j = document.createElement("span");
    j.setAttribute("id", "modality_tip");
    j.setAttribute("class", "");
    a.appendChild(j);
    var g = document.createElement("input");
    g.setAttribute("type", "button");
    g.setAttribute("value", "放弃");
    g.setAttribute("class", "modality_giveup_B");
    g.setAttribute("id", "giveup_B");
    g.setAttribute("onclick", "jQuery('.modality').fadeOut(500);jQuery('.modality').empty();recoverEvent('" + id.substring(0, 32) + "')");
    a.appendChild(g);
    container.appendChild(a);
    $("#modality").show();
    $.ajax({
        type: "post",
        url: "loadItemClass",
        data: {itemGuid: id.substring(0, 32)},
        dataType: "json",
        success: function (re_val) {
            var json_data = JSON.parse(re_val);
            var i = document.createElement("option");
            i.setAttribute("value", "");
            item_class.appendChild(i);
            var l = document.createElement("label");
            l.setAttribute("style", "display:none");
            l.setAttribute("id", "iClassDesc");
            item_class_desc.appendChild(l);
            for (var z = 0; z < json_data.itemClasses.length; z++) {
                i = document.createElement("option");
                i.setAttribute("value", json_data.itemClasses[z].classId);
                if (json_data.itemClass == json_data.itemClasses[z].classId) {
                    i.innerText = json_data.itemClasses[z].className + "（当前）";
                } else {
                    i.innerText = json_data.itemClasses[z].className;
                }
                l = document.createElement("label");
                l.innerText = json_data.itemClasses[z].classDesc;
                l.setAttribute("style", "display:none");
                l.setAttribute("id", json_data.itemClasses[z].classId + "iClassDesc");
                l.setAttribute("class", "item_classes_desc");
                n.appendChild(l);
                item_class.appendChild(i);
            }
            if (!isNullOrSpace(json_data.itemClass)) {
                item_class.value = json_data.itemClass;
                iClassSelect(json_data.itemClass);
            }
        }
    });
}

//---文档标签
function itemTagBtn(id) {
    temp_onclick = document.getElementById(id.substring(0, 32)).onclick;
    $("#" + id.substring(0, 32)).removeAttr("onclick");

}

//---删除
function itemDelBtn(id) {
    temp_onclick = document.getElementById(id.substring(0, 32)).onclick;
    $("#" + id).removeAttr("onclick");

}

//文件共享
function itemShareBtn(id) {
    temp_onclick = document.getElementById(id.substring(0, 32)).onclick;
    $("#" + id.substring(0, 32)).removeAttr("onclick");

}

var itemClassSelectHistory = "";

function iClassSelect(value) {
    var currId = "";
    if (isNullOrSpace(value)) {
        currId = value;
    } else {
        currId = value + "iClassDesc";
    }
    if (isNullOrSpace(itemClassSelectHistory)) {
        $("#" + currId).fadeIn();
    } else {
        if (itemClassSelectHistory != currId) {
            $("#" + itemClassSelectHistory).fadeOut(0);
            $("#" + currId).fadeIn();
        } else {
            return;
        }
    }
    itemClassSelectHistory = currId;
}

function itemSearchTxtA(type) {
    $("#item_search_txt").stop();
    if (type == "onfocus") {
        $("#item_search_txt").animate({
            width: "200px"
        }, 300);
    } else if (type == "onblur") {
        if ($("#item_search_txt").val().trim().length == 0) {
            $("#item_search_txt").animate({
                width: "10px"
            }, 300);
        }
    } else if (type == "oninput") {
        if ($("#item_search_txt").val().trim().length != 0) {
            $("#item_search_btn").removeAttr("disabled");
            $("#item_search_btn").attr("class", "item_search_btn_A");
        } else {
            $("#item_search_btn").attr("disabled", "disabled");
            $("#item_search_btn").attr("class", "item_search_btn");
        }
    }
}

function supItemSearchBtn() {
    if (is_sup_item_search) {
        $("#sup_item_search_condition").animate({
            height: "0px"
        }, 300, function () {
            is_sup_item_search = false;
            document.getElementById("a_content").style.height = "85%";
            itemSearchTxtA("onblur");
            $("#sup_item_search_img").attr("class", "sup_item_search_img");
            $("#sup_item_search_condition").hide();
        });

    } else {
        $("#sup_item_search_img").attr("class", "_sup_item_search_img");
        $("#sup_item_search_condition").show();
        $("#sup_item_search_condition").animate({
            height: "70px"
        }, 300, function () {
            document.getElementById("a_content").style.height = "75%";
            is_sup_item_search = true;
            loadItemClasses();
            $("#item_search_btn").removeAttr("disabled");
            $("#item_search_btn").attr("class", "item_search_btn_A");
        });
    }
}

function itemSearch_AddTag() {
    var a = document.createElement("div");
    a.setAttribute("class", "modality_content");

    var b = document.createElement("div");
    b.setAttribute("class", "modality_titile");

    var c = document.createElement("label");
    c.setAttribute("class", "modality_titile_label");
    c.innerText = "添加标签";
    b.appendChild(c);
    a.appendChild(b);

    var e = document.createElement("div");
    e.setAttribute("style", "width:80%;margin:0 auto;margin-top: 20px;");
    e.setAttribute("id", "modality_item_classes_container");
    a.appendChild(e);

    var f = document.createElement("input");
    f.setAttribute("type", "button");
    f.setAttribute("value", "添加");
    f.setAttribute("class", "modality_setup_B");
    f.setAttribute("id", "setup_B");
    f.setAttribute("onclick", "addItemSearchTag()");
    a.appendChild(f);

    var j = document.createElement("span");
    j.setAttribute("id", "modality_tip");
    j.setAttribute("class", "");
    a.appendChild(j);

    var g = document.createElement("input");
    g.setAttribute("type", "button");
    g.setAttribute("value", "放弃");
    g.setAttribute("class", "modality_giveup_B");
    g.setAttribute("id", "giveup_B");
    g.setAttribute("onclick", "jQuery('.modality').fadeOut(500);jQuery('.modality').empty()");
    a.appendChild(g);

    document.getElementById("modality").appendChild(a);
    $("#modality").show();

    $.ajax({
        type: "post",
        url: "loadItemTags",
        dataType: "json",
        success: function (re_val) {
            var json_data = JSON.parse(re_val);
            for (var z = 0; z < json_data.itemTags.length; z++) {
                var h = document.createElement("div");
                var i = document.createElement("input");
                i.setAttribute("type", "checkbox");
                i.setAttribute("id", json_data.itemTags[z].tagId);
                i.setAttribute("value", json_data.itemTags[z].tagName);
                h.appendChild(i);
                var k = document.createElement("label");
                k.innerText = json_data.itemTags[z].tagName;
                h.appendChild(k);
                e.appendChild(h);
            }
        }
    });
}

function addItemSearchTag() {
    var a = document.getElementById("modality_item_classes_container").children;
    var json = "[";
    for (var i = 0; i < a.length; i++) {
        var b = a[i].children;
        if (b[0].checked) {
            json += '{"value":"' + b[0].id + '","name":"' + b[0].value + '"},';
        }
    }
    if (json.length != 1) {
        json = json.substring(0, json.length - 1);
    }
    json += "]";
    var json_data = JSON.parse(json);
    var c = document.getElementById("item_tag");
    for (var z = 0; z < json_data.length; z++) {
        var f = document.createElement("div");
        f.setAttribute("class", "itemTag_tag");
        f.setAttribute("id", json_data[z].value + "itemTag_tag");
        var d = document.createElement("label");
        d.setAttribute("class", "itemTag_tag_label")
        d.innerText = json_data[z].name;
        f.appendChild(d);
        var e = document.createElement("input");
        e.setAttribute("style", "display:none");
        e.setAttribute("readonly", "readonly");
        e.setAttribute("value", json_data[z].value);
        f.appendChild(e);
        var g = document.createElement("a");
        g.setAttribute("class", "itemTag_tag_del")
        g.setAttribute("onclick", "");
        f.appendChild(g);
        c.appendChild(f)
        $("#giveup_B").click();
    }
}

function loadItemClasses() {
    var a = document.getElementById("item_class");
    $.ajax({
        type: "post",
        url: "loadItemClasses",
        dataType: "json",
        success: function (re_val) {
            var json_data = JSON.parse(re_val);
            var h = document.createElement("option");
            h.setAttribute("value", "");
            h.innerText = "";
            a.appendChild(h)
            for (var z = 0; z < json_data.itemClasses.length; z++) {
                h = document.createElement("option");
                h.setAttribute("value", json_data.itemClasses[z].classId);
                h.innerText = json_data.itemClasses[z].className;
                a.appendChild(h);
            }
        }
    });
}

function itemSearch() {
    var item_content = $("#item_search_txt").val();
    var item_create_date = $("#item_create_date").val();
    var item_modify_date = $("#item_modify_date").val();
    var item_class = $("#item_class").val();
    var item_tag = "";
    var a = document.getElementById("item_tag").getElementsByTagName("input");
    for (var z = 0; z < a.length; z++) {
        item_tag += a[z].value + ",";
    }
    var item_range = $("#item_range").val();
    $.ajax({
        type: "post",
        url: "itemSearch",
        data: {
            itemContent: item_content,
            itemCreateDate: item_create_date,
            itemModifyDate: item_modify_date,
            itemClass: item_class,
            itemTag: item_tag.substring(0, item_tag.length - 1),
            itemRange: item_range
        },
        dataType: "json",
        success: function (re_val) {
            var json_data = JSON.parse(re_val);
            /*console.log(json_data);*/
            $("#a").empty();
            var a = document.getElementById("a");
            for (var z = 0; z < json_data.result.length; z++) {
                var b = document.createElement("div");
                b.setAttribute("id", json_data.result[z].itemGuid + "container");
                b.setAttribute("class", "sup_item_search_result_div");
                b.setAttribute("onmouseover", "supItemSearchResultMO(this.id, 'onmouseover','" + json_data.result[z].itemPassword + "')");
                b.setAttribute("onmouseout", "supItemSearchResultMO(this.id, 'onmouseout','" + json_data.result[z].itemPassword + "')");
                var c = document.createElement("div");
                c.setAttribute("id", json_data.result[z].itemGuid + "img");
                if (isNullOrSpace(json_data.result[z].itemPassword)) {
                    c.setAttribute("class", "item_search_result_txt_b");
                } else {
                    c.setAttribute("class", "item_search_result_txt_lock_b");
                }
                b.appendChild(c);
                var d = document.createElement("div");
                d.setAttribute("class", "sup_item_search_result_content");
                var table = document.createElement("table");
                table.setAttribute("style", "margin-top: 10px;");
                table.setAttribute("cellspacing", "10");
                var tr;
                var td;
                tr = document.createElement("tr");

                td = document.createElement("td");
                td.setAttribute("style", "font-size:13px;")
                td.innerText = "文档名称：";
                tr.appendChild(td);
                td = document.createElement("td");
                td.setAttribute("style", "font-size:14px;color:blue;width:200px;")
                td.innerHTML = json_data.result[z].itemName;
                tr.appendChild(td);

                td = document.createElement("td");
                td.setAttribute("style", "font-size:13px;")
                td.innerText = "文档创建日期：";
                tr.appendChild(td);
                td = document.createElement("td");
                td.setAttribute("style", "font-size:14px;color:blue;width:300px;")
                td.innerHTML = json_data.result[z].itemCreateDateStr;
                tr.appendChild(td);

                td = document.createElement("td");
                td.setAttribute("style", "font-size:13px;")
                td.innerText = "文件类别：";
                tr.appendChild(td);
                td = document.createElement("td");
                td.setAttribute("style", "font-size:14px;color:blue;")
                td.innerHTML = json_data.result[z].itemClassDesc;
                tr.appendChild(td);
                table.appendChild(tr);

                tr = document.createElement("tr");
                td = document.createElement("td");
                td.setAttribute("style", "font-size:13px;")
                td.innerText = "文档大小：";
                tr.appendChild(td);
                td = document.createElement("td");
                td.setAttribute("style", "font-size:14px;color:blue;width:200px;")
                td.innerHTML = json_data.result[z].itemSize;
                tr.appendChild(td);

                td = document.createElement("td");
                td.setAttribute("style", "font-size:13px;")
                td.innerText = "文档最后修改日期：";
                tr.appendChild(td);
                td = document.createElement("td");
                td.setAttribute("style", "font-size:14px;color:blue;width:300px;")
                td.innerHTML = json_data.result[z].itemLastModifiedDateStr;
                tr.appendChild(td);

                td = document.createElement("td");
                td.setAttribute("style", "font-size:13px;")
                td.innerText = "文件标签：";
                tr.appendChild(td);
                td = document.createElement("td");
                td.setAttribute("style", "font-size:14px;color:blue;")
                td.innerHTML = json_data.result[z].itemTagDesc;
                tr.appendChild(td);
                table.appendChild(tr);
                d.appendChild(table);

                table = document.createElement("table");
                table.setAttribute("style", "margin-left: 7px;");
                tr = document.createElement("tr");
                td = document.createElement("td");
                td.setAttribute("style", "font-size:13px;")
                td.innerText = "文档内容预览：";
                tr.appendChild(td);
                td = document.createElement("td");
                td.setAttribute("style", "font-size:14px;color:blue;")
                td.innerHTML = json_data.result[z].itemContent;
                tr.appendChild(td);
                table.appendChild(tr);
                d.appendChild(table);

                b.appendChild(d);
                a.appendChild(b);
            }
        }
    });
}

function supItemSearchResultMO(id, type, psw) {
    var a = id.substring(0, 32);
    if (type == "onmouseover") {
        if (isNullOrSpace(psw)) {
            $("#" + a + "img").attr("class", "item_search_result_txt_b_hover");
        } else {
            $("#" + a + "img").attr("class", "item_search_result_txt_lock_b_hover");
        }
    } else if (type == "onmouseout") {
        if (isNullOrSpace(psw)) {
            $("#" + a + "img").attr("class", "item_search_result_txt_b");
        } else {
            $("#" + a + "img").attr("class", "item_search_result_txt_lock_b");
        }
    }
}

function webSocketMsg(re_val) {
    var json_data = JSON.parse(re_val);

    var a = document.createElement("div");
    a.setAttribute("class", "modality_content");

    var b = document.createElement("div");
    b.setAttribute("class", "modality_titile");

    var c = document.createElement("label");
    c.setAttribute("class", "modality_titile_label");
    c.innerText = "待办事项";
    b.appendChild(c);
    a.appendChild(b);

    var y = document.createElement("div");
    y.setAttribute("style", "width:100%;padding:10px");
    a.appendChild(y);

    var f = document.createElement("input");
    f.setAttribute("type", "button");
    f.setAttribute("value", "确定");
    f.setAttribute("class", "modality_setup_B");
    f.setAttribute("style", "margin-left: 170;")
    f.setAttribute("id", "setup_B");
    f.setAttribute("onclick", "jQuery('.modality').fadeOut(500);jQuery('.modality').empty()");
    a.appendChild(f);

    var j = document.createElement("span");
    j.setAttribute("id", "modality_tip");
    j.setAttribute("class", "");
    a.appendChild(j);

    var table = document.createElement("table");
    table.setAttribute("style", "margin-top: 10px;");
    table.setAttribute("cellspacing", "10");

    var tr;
    var td;
    tr = document.createElement("tr");
    td = document.createElement("td");
    td.setAttribute("style", "font-size:14px;")
    td.innerText = "任务名称：";
    tr.appendChild(td);
    td = document.createElement("td");
    td.setAttribute("style", "font-size:14px;color:blue;width:260px;word-wrap: break-word;display: block;")
    td.innerHTML = json_data.result.taskName;
    tr.appendChild(td);
    table.appendChild(tr);

    tr = document.createElement("tr");
    td = document.createElement("td");
    td.setAttribute("style", "font-size:14px;")
    td.innerText = "任务描述：";
    tr.appendChild(td);
    td = document.createElement("td");
    td.setAttribute("style", "font-size:14px;color:blue;width:260px;word-wrap: break-word;display: block;")
    td.innerHTML = json_data.result.taskDesc;
    tr.appendChild(td);
    table.appendChild(tr);

    tr = document.createElement("tr");
    td = document.createElement("td");
    td.setAttribute("style", "font-size:14px;")
    td.innerText = "任务创建日期：";
    tr.appendChild(td);
    td = document.createElement("td");
    td.setAttribute("style", "font-size:14px;color:blue;width:260px;word-wrap: break-word;display: block;")
    td.innerHTML = json_data.result.strTaskCreateDate;
    tr.appendChild(td);
    table.appendChild(tr);

    tr = document.createElement("tr");
    td = document.createElement("td");
    td.setAttribute("style", "font-size:14px;")
    td.innerText = "任务触发日期：";
    tr.appendChild(td);
    td = document.createElement("td");
    td.setAttribute("style", "font-size:14px;color:blue;width:260px;word-wrap: break-word;display: block;")
    td.innerHTML = json_data.result.strTaskDate;
    tr.appendChild(td);
    table.appendChild(tr);

    tr = document.createElement("tr");
    td = document.createElement("td");
    td.setAttribute("style", "font-size:14px;")
    td.innerText = "任务附件：";
    tr.appendChild(td);
    td = document.createElement("td");
    td.setAttribute("style", "font-size:14px;color:blue;width:260px;word-wrap: break-word;display: block;")
    td.innerHTML = json_data.result.itemName;
    tr.appendChild(td);
    table.appendChild(tr);

    y.appendChild(table);
    document.getElementById("modality").appendChild(a);
    $("#modality").show();
    if(json_data.result.taskLvl == "1"){
        interval = setInterval('alert("\\n                               您有1个待办事项 ^_^ ！\\n\\n\\n                                                  ------云记本系统");clearInterval(interval);', 500);
    }
}

