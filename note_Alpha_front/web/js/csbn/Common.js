(function (commonFunc) {

    commonFunc.popWindow = function (url, size) {
        commonFunc.loadModule($('.modal-body'), url);
        if (size != null) {
            $('.modal-dialog').addClass(size);
        }
        $('#modalWindow').modal();
    };


    commonFunc.loadProperties = function () {
        jQuery.i18n.properties({
            name: 'language',
            path: '../resource/i18n/',
            mode: 'map',
            language: 'zh_CN',
            cache: false,
            encoding: 'UTF-8',
            callback: function () {
                var insertEle = $('[data-i18n]');
                insertEle.each(function () {
                    $(this).html($.i18n.prop($(this).attr('data-i18n')));
                });
                var insertInputEle = $('[data-i18n-input]');
                insertInputEle.each(function () {
                    var selectAttr = $(this).attr('data-i18n-attr');
                    if (!selectAttr) {
                        selectAttr = "value";
                    }
                    $(this).attr(selectAttr, $.i18n.prop($(this).attr('data-i18n-input')));
                });
            }
        });
    };

    commonFunc.loadModule = function (container, module, callBack) {
        var xmlHttp = window.XMLHttpRequest ? new XMLHttpRequest() : new ActiveXObject("Microsoft.XMLHTTP")
        xmlHttp.open("GET", module, false);
        xmlHttp.send();
        if (xmlHttp.readyState == 4) {
            switch (xmlHttp.status) {
                case 200:
                    if (callBack == null) {
                        container.load(module);
                    } else {
                        container.load(module, callBack);
                    }
                    break;
                default:
                    window.location.href = 'errorModule/loseModule.html';
                    break;
            }
        }
    }

    commonFunc.openPreLoader = function () {
        $('#modalLoader').modal({
            backdrop: false,
            keyboard: false
        });
    }

    commonFunc.closePreLoader=function () {
        $('#modalLoader').modal('dispose');
    }

}(commonFunc = {}))