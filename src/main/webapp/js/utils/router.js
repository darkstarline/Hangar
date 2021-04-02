define(function(require, exports, module) {

    // 引入浏览器进度插件
    require('plugin/nprogress/0.2.0/nprogress.css');
    require('plugin/nprogress/0.2.0/nprogress.js');

    // 引入Ajax工具模块
    var Ajax = require('./ajax');

    /**
     * @desc: 通过 URL的hash的方式，来控制页面路由，实现浏览器前进后退，刷新定位
     */
    var Router = {
        /**
         * 路由主内容区
         * @type {String}
         * @class container
         */
        container: '#router-container',
        /**
         * 加载js文件需配置的路径前缀，为解决路径问题
         * @type {String}
         */
        addUrl: '',
        /**
         * 默认加载页面
         * @type {String}
         * @class defaultHashPage
         */
        defaultHashPage: CONF.defaultHashPage,
        /**
         * 是否缓存页面，默认不缓存
         * @type {Boolean}
         * @class cache
         */
        cache: false,
        /**
         * init 初始化路由系统
         *
         * @method init
         */
        init: function(addUrl) {

            // 页面刷新时检查页面路由
            this.checkURL();
            // 额外路径配置
            if (addUrl) {
                this.addUrl = addUrl;
            }
            // 过滤空的路由链接
            $(document).on("click", 'a[href="#"]', function(e) {
                e.preventDefault();
            });

            // 实时检测路由变化
            $(window).on('hashchange', function() {
                Router.checkURL();
            });
        },
        /**
         * checkURL 解析路由
         * @method checkURL
         * @desc: 解析浏览器路由，当为空时，加载默认页面，否则加载解析出的页面
         */
        checkURL: function() {
            var thiz = this;
            var url = location.href;

            var arr = url.split("#");
            var hash = '';
            if (arr.length > 1) {
                hash = arr[1];
            }
            if (hash !== '') {
                thiz.loadURL(hash);
            } else {
                thiz.loadURL(thiz.defaultHashPage);
            }
        },
        /**
         * loadURL 根据解析路由加载URL页面，调用Ajax.getHTML方法
         *
         * @method loadURL
         * @param {String}
         *            url 页面请求地址
         */
        loadURL: function(url) {
            var thiz = this;
            // 存储数据
            $content = $(thiz.container).find(".content");
            $loader = $(thiz.container).find(".content_loader");

            $content.addClass("hide");
            $loader.addClass("show");

            // 动态设置页面标题
            thiz.setHeaderTitle(url);
            if (url.indexOf("http") != -1) {
                var _iframe = '<iframe src="' + url  +'" frameborder="no" border="0" marginwidth="0" marginheight="0"></iframe>';
                $loader.removeClass('show');
                $content.removeClass("hide");
                $content.html(_iframe);
                $('iframe').width("100%");
                $('iframe').height("725px");
                return false;
            }

            // 浏览器插件初始化，parent指定显示在某容器，如果为空则为body
            NProgress.configure({ parent: thiz.container, showSpinner: false });
            NProgress.start();

            // 调用Ajax加载页面，默认开启缓存，可进行配置
            Ajax.getHtml(url, function(html, state) {
                $content.html(html);
                $content.removeClass("hide");
                // 载入页面到容器
                setTimeout(function() {
                    $loader.removeClass('show');
                }, 250)
                if (state) {

                    // 加载页面对应的JS模块，并初始化
                    seajs.use(thiz.addUrl + url.split('.')[0], function(Page) {
                        Page && Page["init"] && Page.init();
                    });

                } else {
                    $content.html("<div class='error-construction'><h4>很抱歉！无法加载到您要的资源...</h4></div>");
                }
                NProgress.done();
            }, thiz.cache);
        },
        /**
         * setHeaderTitle 设置当前页标题
         *
         * @desc 获取a标签里的text设置当前页面标题，data-title='自定义标题'，可进行自定义
         */
        setHeaderTitle: function(url) {

            $a = $('a[href="' + '#' + url + '"]').eq(0);
            var title = $a.text();

            if (!title) {
                var urls = url.split("?");
                var url = urls.length > 0 && urls[0];

                $a = $('a[href="' + '#' + url + '"]').eq(0);
                title = $a.text();
            }

            // 判断是否有自定义标题
            if ($a.data("title") != undefined) {
                title = $a.data("title");
            }
            Router.setTitle(title);
        },
        setTitle: function(title) {
            $(this.container).find(".content-header h1").find("span").text(title);
        },
        changeURL: function(url, data) {
            var thiz = this;
            var urlArray = location.href.split("#");
            var base = '';
            if (urlArray.length > 1) {
                base = urlArray[0];
            }
            location.href = base + '#' + url;
            $(thiz.container).data("data", data);
        },
        getParamData: function() {
            var thiz = this;

            var depositData = $(thiz.container).data("data");
            if (depositData) {
                return depositData;
            } else {
                var urls = location.href.split("?");
                var paramUrl = urls.length > 1 && urls[1];

                var obj = {};
                if (paramUrl) {
                    var params = paramUrl.split("&");
                    if (params) {
                        for (var i = 0; i < params.length; i++) {
                            var param = params[i];
                            var nameAndValue = param.split("=");
                            if (nameAndValue.length == 2) {
                                obj[nameAndValue[0]] = nameAndValue[1];
                            }
                        }
                    }
                }

                return obj;
            }

        }
    }

    //    var r = {
    //          protocol: /([^\/]+:)\/\/(.*)/i,
    //          host: /(^[^\:\/]+)((?:\/|:|$)?.*)/,
    //          port: /\:?([^\/]*)(\/?.*)/,
    //          pathname: /([^\?#]+)(\??[^#]*)(#?.*)/
    //      };
    //   function parseUrl(url) {
    //     var tmp, res = {};
    //     res["href"] = url;
    //     for (p in r) {
    //         tmp = r[p].exec(url);
    //         res[p] = tmp[1];
    //         url = tmp[2];
    //         if (url === "") {
    //             url = "/";
    //         }
    //         if (p === "pathname") {
    //             res["pathname"] = tmp[1];
    //             res["search"] = tmp[2];
    //             res["hash"] = tmp[3];
    //         }
    //     }
    //     console.log(url);
    //     return res;
    //   };

    module.exports = Router;
});