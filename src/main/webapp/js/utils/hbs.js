define(function (require, exports, module) {

    // API接口注册工具
    var requireAPI = require('utils/api');
    var API = requireAPI.init();
    // Ajax常用方法工具
    var Ajax = require('utils/ajax');

   /**
    * @desc: 封装了Handlebars常用的编译方法，通过配置化的封装，执行从请求数据，到编译模板，至最终的渲染模板到指定容器。
    */
    var HBS = {
        /**
         * compile 编译模板
         *
         * @method compile
         * @param {String}
         *            temp 模板容器
         * @param {String}
         *            json 数据
         * @return {String} 返回模板和数据编译后的HTML代码段
         */
        compile:function(temp,json){
            var template = Handlebars.compile($(temp).html());
            return template(json);
        },
        /**
         * compileForContainer 渲染模板及数据到指定容器
         *
         * @method compileForContainer
         * @param {String}
         *            temp 模板内容
         * @param {String}
         *            json 数据
         * @param {String}
         *            container 指定的容器
         * @param {Function}
         *            callback 渲染完成后的回调方法
         */
        compileForContainer:function(temp, tempClone, json, container, callback){
            // 判断是否需要备份
            $tempClone = $(temp).clone();

            // 渲染模板到指定容器
            $(container).html(this.compile(temp, json));

            // 判断是否需要克隆模板到该容器下，以便之后调用
            if(tempClone){
                $(container).append($tempClone);
            }

            // 执行回调方法
            if(callback)
                callback(json);
        },
        /**
         * AjaxForCompile 封装Ajax请求数据、渲染模板、到指定容器
         *
         * @method AjaxForCompile
         * @param {String}
         *            container 指定的容器[不可为空]
         * @param {Object}
         *            setting 配置的参数对象
         *                  @param {String}
         *                      url 请求接口的路径地址 [可为空，默认从对应的容器ID取得对应值]
         *                  @param {Object}
         *                      cmd 接口的入参 [可为空]
         *                  @param {String}
         *                      temp 模板容器或路径地址 [可为空，默认从对应的容器ID查找子元素script取得值]
         *                  @param {Boolean}
         *                      tempClone 模板是否缓存到页面 [可为空，默认缓存]
         *                  @param {Function}
         *                      callback 模板加载完成后的回调方法 [可为空，默认不执行回调]
         *
         */
        AjaxForCompile:function(container, setting){
            var thiz = this;

            // 当没有入参对象，进行初始化
            if(setting==undefined){
                setting={}
            }

            // 处理参数默认值
            var option = {
                url : setting.url ? setting.url : API.get(container.split("_")[1]),
                cmd : setting.cmd ? setting.cmd : '',
                temp : setting.temp ? $(setting.temp) : $(container).find("script"),
                tempClone : setting.tempClone ? setting.tempClone : true,
                callback : setting.callback ? setting.callback : false
            }
            if(option.url == 'null' || option.url == 'NULL'){
                // 传入数据进行编译模板到指定容器
                thiz.compileForContainer(option.temp, option.tempClone, {}, container, option.callback)
            }else{
                // 调用Ajax接口取得数据
                Ajax.postJson(option.url, option.cmd, function(json,state){
                    if(state){
                        // 传入数据进行编译模板到指定容器
                        thiz.compileForContainer(option.temp, option.tempClone, json["data"], container, option.callback)
                    }
                })
            }
        },
        getAjaxCompileHTML:function(tempId, setting){

            var _html = '';

            // 当没有入参对象，进行初始化
            if(setting==undefined){
                setting={}
            }

            // 处理参数默认值
            var option = {
                url : setting.url ? setting.url : 'NULL',
                cmd : setting.cmd ? setting.cmd : '',
                callback : setting.callback ? setting.callback : false
            }

            if(option.url == 'null' || option.url == 'NULL'){
                var template = Handlebars.compile($(tempId).html());
                    _html =  template({});
                    if(option.callback){
                        option.callback(_html);
                    }
            }else{
                // 调用Ajax接口取得数据
                Ajax.postJson(option.url, option.cmd, function(json,state){
                    if(state){
                        var template = Handlebars.compile($(tempId).html());
                        _html =  template(json);
                        if(option.callback){
                            option.callback(_html, json);
                        }
                    }
                })
            }
        }
    }
    module.exports = HBS;
});