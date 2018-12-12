/*
*   only use by register.jsp
* */

// Constants
var reg_email_norm_tip = "例：example@sina.com";
var reg_email_err_tip = "请输入有效的邮箱地址";
var reg_email_empty_tip = "请填写您的邮箱地址"
var reg_phone_norm_tip = "暂只支持大陆手机号码";
var reg_phone_err_tip = "请输入有效的手机号码";
var reg_phone_empty_tip = "请填写您的手机号码";
var reg_name_norm_tip = "设置后不能更改,中英文均可,最长10个字符";
var reg_name_existed_tip = "此用户名太受欢迎,请更换一个吧";
var reg_name_length_tip = "用户名长度不能超过10个字符";
var reg_name_empty_tip = "请填写您的用户名";
var reg_name_format_tip = "用户名仅支持中英文、数字和下划线,且不能为纯数字";
var reg_re_psw_empty_tip = "请再次输入密码";
var reg_re_psw_err_tip = "确认密码与密码不一致";
var reg_acpt_tip = "您还未接受云计本用户协议";
var reg_pass_tip = "";
//
var phone_satisfy = false;
var e_mail_satisfy = false;
var name_satisfy = false;
var psw_satisfy = false;
var re_psw_satisfy = false;

var sign_up_by_phone = true;

$(document).ready(function () {
    initEvent();
});

// initialize the event -- start --
function initEvent() {
    // children of the s:from#register -- start --
    $("#userEmail").bind({
        focus: function () {
            showTip(this.id, "i_user", reg_email_norm_tip, "tip");
        },
        blur: function () {
            checkEmailSatisfy(this.value, this.id);
            //hideTip(this.id, e_mail_status);
        }
    });
    $("#userPhone").bind({
        focus: function () {
            showTip(this.id, "i_user", reg_phone_norm_tip, "tip");
        },
        blur: function () {
            checkPhoneSatisfy(this.value, this.id);
            //hideTip(this.id, phone_status);
        }
    });
    $("#userName").bind({
        focus: function () {
            showTip(this.id, "i_user", reg_name_norm_tip, "tip");
        },
        blur: function () {
            checkNameSatisfy(this.value, this.id);
            //hideTip(this.id, name_status);
        }
    });
    $("#userPassword").bind({
        focus: function () {
            showTip(this.id, "i_user", "", "", "userPassword");
        },
        blur: function () {
            checkPswSatisfy(this.value, this.id);
            //hideTip(this.id, psw_status);
        },
        input: function () {
            checkPswSatisfy(this.value, this.id);
        }
    });
    $("#rePassword").bind({
        focus: function () {
            showTip(this.id, "i_user", "", "tip", "");
        },
        input: function () {

        },
        blur: function () {
            checkRePsw(this.value, this.id);
        }
    });
    $("#chkAgreement").bind({
        click: function () {
            acptAgreement(this.id);
        }
    });
    $("#signUp").bind({
        click: function () {
            checkRegSatisfy();
        }
    })
    $("#regSubmit").bind({})
    // children of the s:from#register -- end --
}

// initialize the page -- end --

// function start --
function showTip(targetName, targetClass, tip, tipClass, type) {
    $("#" + targetName + "_tip").stop();
    $("#" + targetName + "_tip").fadeOut(0);
    if (!isNullOrSpace(targetClass)) {
        $("#" + targetName).attr("class", targetClass);
    }
    if (!isNullOrSpace(tipClass)) {
        $("#" + targetName + "_tip").attr("class", tipClass);
    }
    $("#" + targetName + "_tip").fadeIn();
    if (isNullOrSpace(type)) {
        document.getElementById(targetName + "_tip").innerText = tip;
    }
}

function hideTip(targetName) {
    $("#" + targetName + "_tip").hide();
}

function checkEmailSatisfy(value, name) {
    if (value.length == 0) {
        hideTip(name);
        e_mail_satisfy = false;
    } else if (!value.match("^(([0-9a-zA-Z]+)|([0-9a-zA-Z]+[_.0-9a-zA-Z-]*[0-9a-zA-Z]+))@([a-zA-Z0-9-]+[.])+([a-zA-Z]{2}|net|NET|com|COM|gov|GOV|mil|MIL|org|ORG|edu|EDU|int|INT)$")) {
        document.getElementById("userEmail_tip").innerHTML = reg_email_err_tip;
        showTip(name, "i_user_err", reg_email_err_tip, "tip_err", "");
        e_mail_satisfy = false;
    } else {
        showTip(name, "i_user_pass", reg_pass_tip, "tip_pass", "");
        e_mail_satisfy = true;
    }
}

function checkPhoneSatisfy(value, name) {
    if (value.trim(" ").length == 0) {
        phone_satisfy = false;
        hideTip(name);
    } else {
        if (value.match("^(13|14|15|18)[0-9]{9}$")) {
            showTip(name, "i_user_pass", reg_pass_tip, "tip_pass", "");
            phone_satisfy = true;
        } else {
            document.getElementById("userPhone_tip").innerHTML = reg_phone_err_tip;
            phone_satisfy = false;
            showTip(name, "i_user_err", reg_phone_err_tip, "tip_err", "");
        }
    }
}

function checkNameSatisfy(value, name) {
    if (value.trim(" ").length == 0) {
        hideTip(name);
        name_satisfy = false;
    } else if (value.match("[A-Za-z0-9_\+\u4e00-\u9fa5]+$")) {
        if (value.length <= 2) {
            //document.getElementById("userName_tip").innerHTML = reg_name_existed_tip;
            showTip(name, "i_user_err", reg_name_existed_tip, "tip_err", "");
            name_satisfy = false;
        } else if (value.length > 10) {
            //document.getElementById("userName_tip").innerHTML = reg_name_length_tip;
            showTip(name, "i_user_err", reg_name_length_tip, "tip_err", "");
            name_satisfy = false;
        } else {
            if (value.match("^[0-9]+$")) {
                showTip(name, "i_user_err", reg_name_format_tip, "tip_err", "");
                name_satisfy = false;
            } else {
                $.ajax({
                    type: "post",
                    url: "checkByUserName",
                    data: {
                        regName: $("#userName").val()
                    },
                    dataType: "json",
                    success: function (re_val) {
                        var json_data = JSON.parse(re_val);
                        if (json_data.nameInfo == "existed") {
                            document.getElementById("userName_tip").innerHTML = reg_name_existed_tip;
                            showTip(name, "i_user_err", reg_name_existed_tip, "tip_err", "");
                            name_satisfy = false;
                        }
                        else {
                            showTip(name, "i_user_pass", reg_pass_tip, "tip_pass", "");
                            name_satisfy = true;
                        }
                    }/*,
					error : function(XMLHttpRequest, textStatus, errorThrown) {
						alert(XMLHttpRequest.status);
						alert(XMLHttpRequest.readyState);
						alert(textStatus);
					}*/    //测试ajax以及回调函数的执行情况//
                });
            }
        }
    } else {
        document.getElementById("userName_tip").innerHTML = reg_name_format_tip;
        showTip(name, "i_user_err", reg_name_format_tip, "tip_err", "");
        name_satisfy = false;
    }
}

function checkPswSatisfy(value, name) {
    $("#rePassword").val(null);
    showTip("rePassword", "i_user", "", "", name);
    hideTip("rePassword");
    $("#register_re_psw_tip").html(null);
    if (value.length == 0) {
        $("#userPassword_len").attr("class", "register_user_psw_check");
        $("#userPassword_cha").attr("class", "register_user_psw_check");
        $("#userPassword_spa").attr("class", "register_user_psw_check");
        $("#userPassword").attr("class", "i_user ");
        psw_satisfy = false;
        hideTip(name);
    } else {
        if (value.length < 6 || value.length > 14) {
            $("#userPassword_len").attr("class", "register_user_psw_check_err");
            $("#userPassword").attr("class", "i_user_err");
            if (value.indexOf(" ") > -1) {
                $("#userPassword_cha").attr("class", "register_user_psw_check_err");
                $("#userPassword_spa").attr("class", "register_user_psw_check_err");
            } else {
                $("#userPassword_cha").attr("class", "register_user_psw_check_pass");
                $("#userPassword_spa").attr("class", "register_user_psw_check_pass");
            }
            psw_satisfy = false;
        } else {
            $("#userPassword_len").attr("class", "register_user_psw_check_pass");
            if (value.indexOf(" ") > -1) {
                $("#userPassword_cha").attr("class", "register_user_psw_check_err");
                $("#userPassword_spa").attr("class", "register_user_psw_check_err");
                $("#userPassword").attr("class", "i_user_err");
                psw_satisfy = false;
            } else {
                $("#userPassword_cha").attr("class", "register_user_psw_check_pass");
                $("#userPassword_spa").attr("class", "register_user_psw_check_pass");
                $("#userPassword").attr("class", "i_user_pass");
                psw_satisfy = true;
            }
        }
    }
}

function checkRePsw(value, name) {
    if (value.length == 0) {
        hideTip(name);
        re_psw_satisfy = false;
    } else {
        if ($("#userPassword").val() == value) {
            showTip(name, "i_user_pass", "", "tip_pass", "");
            re_psw_satisfy = true;
        } else {
            showTip(name, "i_user_err", reg_re_psw_err_tip, "tip_err", "");
            re_psw_satisfy = false;
        }
    }
}

function acptAgreement(id) {
    if(document.getElementById(id).checked == true){
        showTip("chkAgreement", "", "", "tip_pass", "");
    } else {
        hideTip("chkAgreement");
    }

}

function checkRegSatisfy() {
    if ((phone_satisfy && name_satisfy && psw_satisfy && re_psw_satisfy) && document.getElementById("chkAgreement").checked == true ||
        (e_mail_satisfy && name_satisfy && psw_satisfy && re_psw_satisfy && document.getElementById("chkAgreement").checked == true)) {
        $.ajax({
            type: "post",
            url: "signUp",
            data: {
                userEmail: $("#userEmail").val(),
                userPhone: $("#userPhone").val(),
                userName: $("#userName").val(),
                userPassword: $("#userPassword").val(),
                rePassword: $("#rePassword").val()
            },
            dataType: "json",
            success: function (re_val) {
                var json_data = JSON.parse(re_val);
                if (json_data.result == _SUCCESS) {
                    window.location.href = "js.jsp"
                } else {
                    if (json_data.emailInfo != _SUCCESS) {
                        if (json_data.emailInfo == _FORMAT_ERR) {
                            showTip("userEmail", "i_user_err", reg_email_err_tip, "tip_err", "");
                        } else if (json_data.emailInfo == _EMPTY && !sign_up_by_phone) {
                            showTip("userEmail", "i_user_err", reg_email_empty_tip, "tip_err", "");
                        }
                        e_mail_satisfy = false;
                    }
                    if (json_data.phoneInfo != _SUCCESS) {
                        if (json_data.phoneInfo == _FORMAT_ERR) {
                            showTip("userPhone", "i_user_err", reg_phone_err_tip, "tip_err", "");
                        } else if (json_data.phoneInfo == _EMPTY && sign_up_by_phone) {
                            showTip("userEmail", "i_user_err", reg_phone_empty_tip, "tip_err", "");
                        }
                        phone_satisfy = false;
                    }
                    if (json_data.nameInfo != _SUCCESS) {
                        if (json_data.nameInfo == _EMPTY) {
                            showTip("userName", "i_user_err", reg_name_empty_tip, "tip_err", "");
                        } else if (json_data.nameInfo == _FORMAT_ERR) {
                            showTip("userName", "i_user_err", reg_name_format_tip, "tip_err", "");
                        } else if (json_data.nameInfo == _MAX_LENGTH_ERR) {
                            showTip("userName", "i_user_err", reg_name_length_tip, "tip_err", "");
                        } else if (json_data.nameInfo == _EXISTED || json_data.nameInfo == _MIN_LENGTH_ERR) {
                            showTip("userName", "i_user_err", reg_name_existed_tip, "tip_err", "");
                        }
                        name_satisfy = false;
                    }
                    if (json_data.passwordSpaceInfo == _ERROR) {
                        $("#userPassword_spa").attr("class", "register_user_psw_check_err");
                        showTip("rePassword", "i_user", "", "", "userPassword");
                        psw_satisfy = false;
                    }
                    if (json_data.passwordCharInfo == _ERROR) {
                        $("#userPassword_cha").attr("class", "register_user_psw_check_err");
                        showTip("rePassword", "i_user", "", "", "userPassword");
                        psw_satisfy = false;
                    }
                    if (json_data.passwordLengthInfo == _ERROR) {
                        $("#userPassword_len").attr("class", "register_user_psw_check_err");
                        showTip("rePassword", "i_user", "", "", "userPassword");
                        psw_satisfy = false;
                    }
                    if (json_data.rePasswordInfo == _ERROR) {
                        showTip("rePassword", "i_user_err", reg_re_psw_err_tip, "tip_err", "");
                        re_psw_satisfy = false;
                    }
                }
            }
        });
    } else {
        if (sign_up_by_phone) {
            if ($("#userPhone").val().length == 0) {
                showTip("userPhone", "i_user_err", reg_phone_empty_tip, "tip_err", "");
            }
        } else {
            if ($("#userEmail").val().length == 0) {
                showTip("userEmail", "i_user_err", reg_email_empty_tip, "tip_err", "");
            }
        }
        if ($("#userName").val().length == 0) {
            showTip("userName", "i_user_err", reg_name_empty_tip, "tip_err", "");
        }
        if ($("#userPassword").val().length == 0) {
            $("#userPassword_len").attr("class", "register_user_psw_check_err");
            showTip("userPassword", "i_user_err", "", "", "userPassword");
        }
        if ($("#rePassword").val().length == 0) {
            showTip("rePassword", "i_user_err", reg_re_psw_empty_tip, "tip_err", "");
        }
        if (document.getElementById("chkAgreement").checked == false) {
            showTip("chkAgreement", "", reg_acpt_tip, "tip_err", "");
        }
    }
}

function otherWayReg() {
    hideTip("userEmail");
    hideTip("userPhone");
    if ($("#userPhone").css("display") == "block") {
        $("#userPhone").fadeOut(300);
        $("#l_note_phone").fadeOut(300);
        $("#userEmail").fadeIn(300);
        $("#l_note_email").fadeIn(300);
        document.getElementById("a_theway_reg").innerHTML = "通过手机注册";
        document.getElementById("userPhone").value = null;
        $("#userEmail").attr("class", "i_user");
        e_mail_satisfy = false;
        sign_up_by_phone = false;
    } else {
        $("#userPhone").fadeIn(300);
        $("#l_note_phone").fadeIn(300);
        $("#userEmail").fadeOut(300);
        $("#l_note_email").fadeOut(300);
        document.getElementById("a_theway_reg").innerHTML = "通过邮箱注册";
        document.getElementById("userEmail").value = null;
        $("#userPhone").attr("class", "i_user");
        phone_satisfy = false;
        sign_up_by_phone = true;
    }
}

// function end --