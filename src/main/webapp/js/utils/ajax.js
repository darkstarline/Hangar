define(function(require, exports, module) {

    require('lib/jquery/jquery-ajax-upload/2.0.2/jquery.ajaxupload.js');
    require('utils/jqPlugin');
    /**
     * @desc: 通过 HTTP 请求加载远程数据，底层依赖jQuery的AJAX实现。当前接口实现了对jQuery AJAX接口的进一步封装。
     */
    var Ajax = {
        /**
         * 请求状态码
         * @type {Object}
         */
        code: {
            /**
             * 成功返回码 200
             * @type {Number} 1
             * @property SUCC
             */
            SUCC: 200
        },
        /**
         * 请求的数据类型
         * @type {Object}
         * @class reqDataType
         */
        dataType: {
            /**
             * 返回html类型
             * @type {String}
             * @property HTML
             */
            HTML: "html",
            /**
             * 返回json类型
             * @type {Object}
             * @property JSON
             */
            JSON: "json",
            /**
             * 返回text字符串类型
             * @type {String}
             * @property TEXT
             */
            TEXT: "text"
        },
        /**
         * 发送信息至服务器时内容编码类型
         * @type {Object}
         * @class reqDataType
         */
        contentType: {
            /**
             * 发送x-www-form-urlencoded类型
             * @type {String}
             * @property DEFAULT
             */
            DEFAULT: "application/x-www-form-urlencoded",
            /**
             * 发送json类型
             * @type {Object}
             * @property JSON
             */
            JSON: "application/json"
        },
        /**
         * 超时,默认超时30000ms
         * @type {Number} 10000ms
         * @property TIME_OUT
         */
        TIME_OUT: 60000,
        /**
         * 显示请求成功信息
         *
         * @type {Boolean} false
         * @property SHOW_SUCC_INFO
         */
        SHOW_SUCC_INFO: false,
        /**
         * 显示请求失败信息
         *
         * @type {Boolean} false
         * @property SHOW_ERROR_INFO
         */
        SHOW_ERROR_INFO: false,
        /**
         * GetHtml是对Rose.ajax的封装,为创建 "GET" 请求方式返回 "hmtl" 数据类型
         *
         * @method GetHtml
         * @param {String}
         *            url HTTP(GET)请求地址
         * @param {Object}
         *            cmd json对象参数
         * @param {Function}
         *            callback [optional,default=undefined] GET请求成功回调函数
         */
        getHtml: function(url, callback, cache) {
            dataType = this.dataType.HTML;
            this.ajaxBase(url, 'GET', '', dataType, this.contentType.DEFAULT, callback, false, cache);
        },
        /**
         * GetJson是对ajax的封装,为创建 "GET" 请求方式返回 "JSON"(text) 数据类型
         * @param {String}
         *            url HTTP(GET)请求地址
         * @param {Object}
         *            cmd json对象参数
         * @param {Function}
         *            callback [optional,default=undefined] GET请求成功回调函数
         */
        getJson: function(url, cmd, callback) {
            if (arguments.length !== 3)
                callback = cmd, cmd = '';
            this.ajaxBase(url, 'GET', cmd, this.dataType.TEXT, this.contentType.DEFAULT, callback);
        },
        /**
         * GetJson是对ajax的封装,为创建 "GET" 请求方式返回 "JSON"(text) 数据类型
         * 采用同步阻塞的get方式调用ajax
         * @param {String}
         *            url HTTP(GET)请求地址
         * @param {Object}
         *            cmd json对象参数
         * @param {Function}
         *            callback [optional,default=undefined] GET请求成功回调函数
         */
        getJsonAsync: function(url, cmd, callback) {
            if (arguments.length !== 3)
                callback = cmd, cmd = '';
            this.ajaxBase(url, 'GET', cmd, this.dataType.TEXT, this.contentType.DEFAULT, callback, true);
        },
        /**
         * PostJson是对ajax的封装,为创建 "POST" 请求方式返回 "JSON"(text) 数据类型
         * @param {String}
         *            url HTTP(POST)请求地址
         * @param {Object}
         *            cmd json对象参数
         * @param {Function}
         *            callback [optional,default=undefined] POST请求成功回调函数
         */
        postJson: function(url, cmd, callback) {
            if (arguments.length !== 3)
                callback = cmd, cmd = '';
            this.ajaxBase(url, 'POST', cmd, this.dataType.TEXT, this.contentType.DEFAULT, callback);
        },
        /**
         * PostJsonAsync是对ajax的封装,为创建 "POST" 请求方式返回 "JSON"(text) 数据类型,
         * 采用同步阻塞的post方式调用ajax
         * @param {String}
         *            url HTTP(POST)请求地址
         * @param {Object}
         *            cmd json对象参数
         * @param {Function}
         *            callback [optional,default=undefined] POST请求成功回调函数
         */
        postJsonAsync: function(url, cmd, callback) {
            if (arguments.length !== 3)
                callback = cmd, cmd = '';
            this.ajaxBase(url, 'POST', cmd, this.dataType.TEXT, this.contentType.DEFAULT, callback, true);
        },
        /**
         * upload是对jquery.ajax.upload的封装
         * @param {String}
         *            url HTTP(POST)请求地址
         * @param {Object}
         *            cmd json对象参数
         * @param {Function}
         *            callback [optional,default=undefined] POST请求成功回调函数
         */
        upload: function(url, cmd, callback) {
            var thiz = this;
            $.ajaxUpload({
                url: url,
                data: cmd,
                success: function(data, status, xhr) {
                    var isSuc = thiz.printReqInfo(data);
                    if (callback && data) {
                        callback(data || {}, isSuc);
                    }
                }
            });
        },
        /**
         * 基于jQuery ajax的封装，可配置化
         *
         * @method ajax
         * @param {String}
         *            url HTTP(POST/GET)请求地址
         * @param {String}
         *            type POST/GET
         * @param {Object}
         *            cmd json参数命令和数据
         * @param {String}
         *            dataType 返回的数据类型
         * @param {String}
         *            contentType 发送的数据类型
         * @param {Function}
         *            callback [optional,default=undefined] 请求成功回调函数,返回数据data和isSuc
         */
        ajaxBase: function(url, type, cmd, dataType, contentType, callback, sync, cache) {
            var thiz = this;
            if (typeof(cmd) == "object" && type == "POST") {
                cmd = JSON.stringify(cmd);
                contentType = thiz.contentType.JSON;
            }
            $.ajax({
                url: url,
                type: type,
                data: cmd,
                cache: cache ? true : false,
                dataType: dataType,
                contentType: contentType,
                async: sync ? false : true,
                timeout: thiz.TIME_OUT,
                success: function(data) {
                    if (!data) {
                        return;
                    }
                    if (dataType == "html") {
                        callback(data, true);
                        return;
                    }
                    try {
                        data = eval("(" + data + ")");
                        //超时重定向至登陆页
                        if (data.code == '9527') {
                            window.parent.location.href = 'login.html';
                            return;
                        }
                    } catch (e) {
                        alert(url + ":JSON Format Error:" + e.toString());
                    }
                    var isSuc = thiz.printReqInfo(data);
                    if (callback && data) {
                        callback(data || {}, isSuc);
                    }
                },
                error: function(e, textStatus) {
                    if (textStatus == "timeout") {
                        return false;
                    }
                    var retErr = {};
                    retErr['code'] = "404";
                    retErr['message'] = "网络异常或超时，请稍候再试！";
                    callback(retErr, false);
                },
                complete: function() {}
            });
        },
        /**
         * 打开请求返回代码和信息
         *
         * @method printRegInfo
         * @param {Object}
         *            data 请求返回JSON数据
         * @return {Boolean} true-成功; false-失败
         */
        printReqInfo: function(data) {
            if (!data) {
                return false;
            }
            if (data.message&&data.message.indexOf("成功")==-1){
                return false;
            }else{
                return true;
            }
            // if (typeof(data.success) == "boolean") { return data.success }

            // if (typeof(data.success) == "string") {
            //     if (data.success == "true") {
            //         return true;
            //     } else {
            //         return false;
            //     }
            // }

            // var code = data.code,
            // 	succ = this.code.SUCC;
            // return !!(code == succ);
        }
    };

    module.exports = Ajax;
});