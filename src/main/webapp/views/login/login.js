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
    API.add("gundamStorageController", "travel.json", "gundamStorage/save");
    API.add("gundamCover", "travel.json", "upass/fileBrowse/cover");
    // API.add("fileList", "fileList.json", "upass/fileBrowse/getFiles");
    // API.add("viewerPic", "../assets/images/1900x1200_img_7.png", "upass/download/downloadNoToken");
    // 获取信息
    // API.add("getUserInfo", "userInfo.json", "userInfo");
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
            Ajax.postJson(API.get('gundamStorageController'), null, function (json, state) {
                console.log(json);
                if(state){
                    $(window).attr('location','../../index.html');
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
                // var cmd = Utils.getSaveParam();
                // cmd = Utils.jsonToStr(cmd);
                var cmd = "Hanger";
                console.log(cmd);
                Page.action.login(cmd);
                event.preventDefault();
            });
        },
        aotu2base64save: function (){

        }
    }
    var Utils = {
        justiceWatching: function (organism){

        },
    }
    module.exports = Page;
});
