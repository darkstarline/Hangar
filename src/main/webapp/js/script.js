
/*/!*获取到Url里面的参数*!/
(function () {
    $('[name=gundamSearch]').bind('keypress',function(event){
        if(event.keyCode == "13")
        {
            var keyword = $('[name=gundamSearch]').val();
            $(window).attr('location','../travel/travel.html?keyword='+keyword);
        }
    });
})(jQuery);*/
/*获取到Url里面的参数*/
(function ($) {
    $.getUrlParam = function (name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) return decodeURI(r[2]); return null;
    }
})(jQuery);
jQuery(function($) {
    'use strict';
    jQuery('.navbar-area-default .mean-menu').meanmenu({
        meanScreenWidth: "1199"
    });
    jQuery('.fixed-top-half .navbar-area .mean-menu').meanmenu({
        meanScreenWidth: "1550"
    });
    $(window).on('scroll',
    function() {
        if ($(this).scrollTop() > 50) {
            $('.navbar-area').addClass('is-sticky');
        } else {
            $('.navbar-area').removeClass('is-sticky');
        }
    });
    var windowTab = $(window).width();
    if (windowTab < 992) {
        $(".mobile-to-pagebody").appendTo(".receive-footer .container");
    };
    if (windowTab > 991) {
        $(".page-grid-header").on('scroll',
        function() {
            if ($(this).scrollTop() > 100) {
                $('.navbar-area').addClass('is-sticky');
            } else {
                $('.navbar-area').removeClass('is-sticky');
            }
        });
    };
    var Page = (function() {
        var config = {
            $bookBlock: $('#bb-bookblock'),
            $navNext: $('.bb-nav-next'),
            $navPrev: $('.bb-nav-prev'),
            $navFirst: $('.bb-nav-first'),
            $navLast: $('.bb-nav-last')
        },
        init = function() {
            config.$bookBlock.bookblock({
                speed: 1000,
                shadowSides: 0.8,
                shadowFlip: 0.4
            });
            initEvents();
        },
        initEvents = function() {
            var $slides = config.$bookBlock.children();
            config.$navNext.on('click touchstart',
            function() {
                config.$bookBlock.bookblock('next');
                return false;
            });
            config.$navPrev.on('click touchstart',
            function() {
                config.$bookBlock.bookblock('prev');
                return false;
            });
            config.$navFirst.on('click touchstart',
            function() {
                config.$bookBlock.bookblock('first');
                return false;
            });
            config.$navLast.on('click touchstart',
            function() {
                config.$bookBlock.bookblock('last');
                return false;
            });
            $slides.on({
                'swipeleft': function(event) {
                    config.$bookBlock.bookblock('next');
                    return false;
                },
                'swiperight': function(event) {
                    config.$bookBlock.bookblock('prev');
                    return false;
                }
            });
            $(document).keydown(function(e) {
                var keyCode = e.keyCode || e.which,
                arrow = {
                    left: 37,
                    up: 38,
                    right: 39,
                    down: 40
                };
                switch (keyCode) {
                    case arrow.left:
                        config.$bookBlock.bookblock('prev');
                        break;
                    case arrow.right:
                        config.$bookBlock.bookblock('next');
                        break;
                }
            });
        };
        return {
            init:
            init
        };
    })();
    Page.init();
    $("body").addClass("pre-loaded");
    $(window).on('scroll',
    function() {
        if ($(this).scrollTop() > 300) {
            $("#scrolltop").addClass("scrolltopactive");
        } else {
            $("#scrolltop").removeClass("scrolltopactive");
        }
    });
    $("#scrolltop").on('click',
    function() {
        $("html").animate({
            scrollTop: 0
        },
        2000);
        return false;
    });
    $("#rev_slider_24_1").show().revolution({
        sliderType: "standard",
        sliderLayout: "fullscreen",
        dottedOverlay: "none",
        delay: 9000,
        onHoverStop: 'off',
        navigation: {
            keyboardNavigation: "off",
            keyboard_direction: "horizontal",
            mouseScrollNavigation: "off",
            mouseScrollReverse: "default",
            onHoverStop: "off",
            bullets: {
                enable: true,
                hide_onmobile: false,
                style: "bullet-bar",
                hide_onleave: false,
                direction: "horizontal",
                h_align: "center",
                v_align: "bottom",
                h_offset: 0,
                v_offset: 50,
                space: 5,
                tmp: ''
            }
        },
        responsiveLevels: [1240, 1024, 778, 480],
        visibilityLevels: [1240, 1024, 778, 480],
        gridwidth: [1745, 1745, 1024, 480],
        lazyType: "none",
        shadow: 0,
        spinner: "off",
        stopLoop: "off",
        stopAfterLoops: -1,
        stopAtSlide: -1,
        shuffle: "off",
        autoHeight: "off",
        fullScreenAutoWidth: "off",
        fullScreenAlignForce: "off",
        fullScreenOffsetContainer: "",
        fullScreenOffset: "60px",
        hideThumbsOnMobile: "off",
        hideSliderAtLimit: 0,
        hideCaptionAtLimit: 0,
        hideAllCaptionAtLilmit: 0,
        debugMode: false,
        fallbacks: {
            simplifyAll: "off",
            nextSlideOnWindowFocus: "off",
            disableFocusListener: false,
        }
    });
    if ("#rev_slider_24_1") $("#rev_slider_24_1").revSliderSlicey();
    $("#video-popup").magnificPopup({
        disableOn: 0,
        type: 'iframe',
        mainClass: 'mfp-fade',
        removalDelay: 160,
        preloader: false,
        fixedContentPos: false
    });
    $(".about-blockquote-carousel").slick({
        infinite: true,
        slidesToShow: 1,
        slidesToScroll: 1,
        prevArrow: $('.blockquote-prev'),
        nextArrow: $('.blockquote-next'),
        speed: 500,
        fade: true,
        cssEase: 'linear'
    });
    $(".product-sort").niceSelect();
    $('.product-details-for').slick({
        slidesToShow: 1,
        slidesToScroll: 1,
        fade: true,
        asNavFor: '.product-details-nav',
        autoplay: false,
        prevArrow: $('.product-details-prev'),
        nextArrow: $('.product-details-next'),
    });
    $('.product-details-nav').slick({
        slidesToShow: 3,
        asNavFor: '.product-details-for',
        autoplay: false,
        focusOnSelect: true,
        vertical: true,
        prevArrow: $('.product-details-prev'),
        nextArrow: $('.product-details-next'),
        dots: false,
        responsive: [{
            breakpoint: 767,
            settings: {
                vertical: false,
            }
        },
        {
            breakpoint: 475,
            settings: {
                slidesToShow: 2,
                vertical: false,
            }
        }]
    });
    $(".related-product-carousel").slick({
        infinite: true,
        slidesToShow: 3,
        slidesToScroll: 3,
        autoplay: {
            delay: 500
        },
        prevArrow: $('.related-product-prev'),
        nextArrow: $('.related-product-next'),
        responsive: [{
            breakpoint: 1199,
            settings: {
                slidesToShow: 2,
                slidesToScroll: 2,
            }
        },
        {
            breakpoint: 575,
            settings: {
                slidesToShow: 1,
                slidesToScroll: 1,
            }
        },
        ]
    });
    $(".product-size li").on("click",
    function() {
        $(this).find("a").click(function(e) {
            e.preventDefault();
        }) ;
        $(this).addClass("active").siblings().removeClass("active");
    });
    $(".product-tab-list li").on("click",
    function() {
        var tab_modal = $(this).attr("data-product-tab");
        $(this).addClass("active").siblings().removeClass("active");
        $(".product-tab-information-item[data-product-details-tab=" + tab_modal + "]").addClass("active").siblings().removeClass("active");
    }) ;
    $(".qu-btn").on("click",
    function(e) {
        var btn = $(this),
        inp = btn.siblings(".qu-input").val();
        if (btn.hasClass("inc")) {
            var i = parseFloat(inp) + 1;
        } else {
            if (inp > 1)(i = parseFloat(inp) - 1) < 2 && $(".dec").addClass("deact");
            else i = 1;
        }
        btn.addClass("deact").siblings("input").val(i)
    });
    $(".gallery-carousel").slick({
        infinite: true,
        slidesToShow: 1,
        slidesToScroll: 1,
        speed: 1500,
        fade: true,
        cssEase: 'linear',
        autoplay: {
            delay: 500
        },
        prevArrow: $('.gallery-prev'),
        nextArrow: $('.gallery-next'),
    });
    $('.gallery-grid').magnificPopup({
        delegate: 'a',
        type: 'image',
        tLoading: 'Loading image #%curr%...',
        mainClass: 'mfp-img-mobile',
        gallery: {
            enabled: true,
            navigateByImgClick: true,
            preload: [0, 1]
        },
        image: {
            tError: '<a href="%url%">The image #%curr%</a> could not be loaded.',
        }
    });
    function newCounter2() {
        var countDate = new Date("15 October 2022 9:56:00");
        var sec = 1000;
        var min = sec * 60;
        var hr = min * 60;
        var day = hr * 24;
        var today = new Date();
        var distance = countDate - today;
        var days = Math.floor(distance / day);
        var hours = Math.floor((distance % day) / hr);
        var mins = Math.floor((distance % hr) / min);
        var secs = Math.floor((distance % min) / sec);
        $(".day1").html(days + "<span>Days</span>");
        $(".hr1").html(hours + "<span>Hrs</span>");
        $(".min1").html(mins + "<span>Mins</span>");
        $(".sec1").html(secs + "<span>Sec</span>")
        ;
        if (distance < 0) {
            clearInterval(dealCounter1);
            $(".new-counter-2").html("Session Expired");
        }
    }
    setInterval(function() {
        newCounter2();
    },
    1000);
    $(".billing-title p").on("click",
    function() {
        $(".billing-address").addClass("none");
        $(".billing-address-input").addClass("active");
    }) ;
    $('.header-carousel').slick({
        dots: true,
        speed: 500,
        fade: true,
        cssEase: 'linear',
        arrows: false,
        autoplay: true,
        autoplaySpeed: 2000,
    });
    $('.back-page').on("click",
    function() {
        history.go( - 1);
        return false;
    });
    $("#contactForm, .newsletter-form").validator().on("submit",
    function(event) {
        if (event.isDefaultPrevented()) {
            formErrorSub();
            submitMSGSub(false, "Please enter your email correctly.");
        } else {
            event.preventDefault();
        }
    });
    function callbackFunction(resp) {
        if (resp.result === "success") {
            formSuccessSub();
        } else {
            formErrorSub();
        }
    }
    function formSuccessSub() {
        $(".newsletter-form")[0].reset();
        submitMSGSub(true, "Thank you for subscribing!");
        setTimeout(function() {
            $("#validator-newsletter").addClass('hide');
        },
        4000)
    }
    function formErrorSub() {
        $(".newsletter-form").addClass("animate__animated animate__shakeX");
        setTimeout(function() {
            $(".newsletter-form").removeClass("animate__animated animate__shakeX");
        },
        1000)
    }
    function submitMSGSub(valid, msg) {
        if (valid) {
            var msgClasses = "validation-success";
        } else {
            var msgClasses = "validation-danger";
        }
        $("#validator-newsletter").removeClass().addClass(msgClasses).text(msg);
    }
    $(".newsletter-form").ajaxChimp({
        url: "",
        callback: callbackFunction
    });
});