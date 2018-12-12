$(function () {
    init();
}(jQuery));

function init() {
    signContainer_changer();
    resize();
}

$(window).resize(function () {
    resize();
});

function resize() {
    $('#signContainer').css('margin-top', document.getElementById('main').offsetHeight * 0.12 + 'px');
}

function signContainer_changer() {
    $('.signContainer-fill').stop();
    var collection = $('.signContainer-changer').children();
    var isSignIn = $('#signContainer-signUpContainer').length == 0;
    if (isSignIn) {
        $('.signContainer-fill').animate({
            opacity: '0'
        }, 100, function () {
            $('.signContainer').fadeIn();
            commonFunc.loadModule($('.signContainer-fill'), '../html/signModule/signUp.html', function () {
                $('.signContainer-fill').animate({
                    height: $('.signContainer-signInContainer').innerHeight() + 'px',
                    opacity: '1'
                }, 300);
                collection[0].innerText = $.i18n.prop('signUp-had-account');
                collection[1].innerText = $.i18n.prop('signUp-goto-signIn');
            });
        });
    } else {
        $('.signContainer-fill').animate({
            opacity: '0'
        }, 100, function () {
            $('.signContainer').fadeIn();
            commonFunc.loadModule($('.signContainer-fill'), '../html/signModule/signIn.html', function () {
                $('.signContainer-fill').animate({
                    height: $('.signContainer-signInContainer').innerHeight() + 'px',
                    opacity: '1'
                }, 300);
                collection[0].innerText = $.i18n.prop('signIn-non-account');
                collection[1].innerText = $.i18n.prop('signIn-goto-signUp');
                $('.signContainer').fadeIn();
            });
        });
    }
}