define(function(require, exports, module) {
    console.log("load js");
    // API接口注册工具
    var requireAPI = require('utils/api');
    var API = requireAPI.init();
    // Bootstrap-Table 常用方法工具
    // var BTable = require('utils/btable');
    // Ajax
    var Ajax = require('utils/ajax');
    // Handlebars
    // var HBS = require('utils/hbs');
    //Bootstrap-Dialog 常用方法工具
    // var BDialog = require('utils/bdialog');
    // jQuery-Form 常用方法工具
    var jqForm = require('utils/jqform');
    // 引入jQuery插件，用于信提示等;
    require('utils/jqPlugin');
    // 查询
    API.add("login", "retMessage.json", "login");
    // 定义当前页面对象
    var Page = {
        init: function() {
            this._render();
        },
        _render: function() {
            Page.operate.init();
        }
    }
    Page.data = {

    }

    Page.action = {
        login: function (cmd){
            Ajax.postJson(API.get('login'), cmd, function (json, state) {
                console.log(json);
                if(state){
                    $(window).attr('location','../../index.html');
                }else{
                    //TODO 失败信息提示
                    // Page.operate.showLogginErro(json.message);
                }
            })
        },
    };

    // 定义页面所有的操作方法
    Page.operate = {
        init: function() {
            this.login();
        },
        // 路径点击跳转事件
        login: function() {
            $("#loginForm").on("submit",function(event) {
                var cmd = Utils.getObjectData($("#loginForm"));
                cmd = Utils.jsonToStr(cmd);
                console.log(cmd);
                Page.action.login(cmd);
                event.preventDefault();
            });
        },
        aotu2base64save: function (){

        },
        showLogginErro: function (msg) {
            Login.login.find(".login-error-info span").html(msg);
            Login.login.find(".login-error-info").show();
        }
    }
    var Utils = {
        justiceWatching: function (organism){

        },
        getObjectData: function(obj,callback) {
            var data = {};
            $(obj).find("input,select,textarea").each(function(index, el) {
                var _key = $(el).attr('name');
                var _value = $(el).val();
                data[_key]=_value;
            });
            if (callback) {
                callback(data);
            }
            return data;
        },
        // json转string
        jsonToStr: function(json) {
            var str = "";
            for (var key in json) {
                if (typeof(json[key]) == "object") {} else {
                    str += "&" + key + "=" + json[key];
                }
            }
            return str.substring(1);;
        },
    }
    module.exports = Page;
});
