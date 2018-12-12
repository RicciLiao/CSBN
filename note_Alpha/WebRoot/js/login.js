/*
*   only use by login.jsp
* */


function login_choose(String) {
    if (String == selected) {
    } else {
        $("#d_" + selected).fadeOut(300);
        $("#d_" + String).fadeIn(500);
        $("#" + String).addClass("login_choose_selected");
        $("#" + selected).removeClass("login_choose_selected");
        selected = String;
    }
}

function login_check() {
    if ($("#userName").val().trim(" ").length == 0 ||
        $("#userPassword").val().trim(" ").length == 0) {
        $("#login_err_tip").html("请输入用户名和密码");
        $("#login_err_tip").css("color", "red");
    }
    else {
        $.ajax({
            type: "post",
            url: "login",
            data: {
                userName: $("#userName").val(),
                userPassword: $("#userPassword").val()
            },
            dataType: "json",
            success: function (re_val) {
                var json_data = JSON.parse(re_val);
                if (json_data.result == _SUCCESS) {
                    window.location.href = "loginSuccess?jsessionid=" + json_data.jsessionid;
                    $("#login_err_tip").html("登录成功");
                    $("#login_err_tip").css("color", "green");
                } else if (json_data.result == _PASSWORD_ERR) {
                    $("#login_err_tip").html("密码错误");
                    $("#login_err_tip").css("color", "red");
                }
                else if (json_data.result == _NAME_ERR) {
                    $("#login_err_tip").html("用户名不存在");
                    $("#login_err_tip").css("color", "red");
                }
            }
        });
    }
}
function QRcode_move(String) {
    if (String == "1") {
        $("#QRcode").stop();
        $("#QRcode_way").stop();
        $("#QRcode").animate({
            left: '100'
        });
        $("#QRcode_way").fadeOut(0);
    } else {
        $("#QRcode").stop();
        $("#QRcode_way").stop();
        $("#QRcode").animate({
            left: '30'
        }, function () {
            $("#QRcode_way").fadeIn(800);
        });
    }
}

function login_logo_animation_moveover(String) {
    $("#" + String).stop();
    $("#" + String).animate({
        opacity: '0.9'
    }, 200);
    $("#" + String).addClass(String + "_ ");
}

function login_logo_animation_moveout(String) {
    $("#" + String).stop();
    $("#" + String).animate({
        opacity: '0.2'
    }, 800, function () {
        $("#" + String).removeClass(String + "_ ");
    });
}

function app_logo_animation_moveover(String) {
    $("#" + String).stop();
    $("#" + String).addClass(String + "_ ");
}

function app_logo_animation_moveout(String) {
    $("#" + String).stop();
    $("#" + String).removeClass(String + "_ ");
}