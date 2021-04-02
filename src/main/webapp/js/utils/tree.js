define(function (require, exports, module) {


    // 引入Bootstrap-treeview树结构插件
    require('bootstrap/bootstrap-treeview/1.2.0/bootstrap-treeview.min.css');
    require('bootstrap/bootstrap-treeview/1.2.0/bootstrap-treeview.min.js');

    // API接口注册工具
    var requireAPI = require('utils/api');
    var API = requireAPI.init();
    // Ajax常用方法工具
    var Ajax = require('utils/ajax');

   /**
    * @desc: 封装了Handlebars常用的编译方法，通过配置化的封装，执行从请求数据，到编译模板，至最终的渲染模板到指定容器。
    */
    var Tree = {
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
        }
    }
    module.exports = Tree;
});