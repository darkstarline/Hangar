define(function(require, exports, module) {
    console.log("load mytest.js");
    // API接口注册工具
    var requireAPI = require('utils/api.js');
    var API = requireAPI.init();
    var Ajax = require('utils/ajax');
    // 引入jQuery插件，用于信提示等;
    require('utils/jqPlugin');
    // 查询
    API.add("fileList", "test/t3", "test/t3");
    // API.add("catalogList", "fileList.json", "upass/fileBrowse/getCatalogs");
    // API.add("fileList", "fileList.json", "upass/fileBrowse/getFiles");
    // API.add("viewerPic", "../assets/images/1900x1200_img_7.png", "upass/download/downloadNoToken");
    // 获取信息
    // API.add("getUserInfo", "userInfo.json", "userInfo");
    // 定义当前页面对象
    var Page = {
        init: function() {
            this._render();
            console.log("init");
        },
        _render: function() {
            console.log("_render");
            Page.operate.init();
        }
    }
    Page.data = {
        "asdf":"adsfa"
    }

    Page.action = {
        // 查询所有列表接口
        queryList: function() {
            Ajax.postJson(API.get('fileList'), null, function (json, state) {
                if (state) {
                    console.log(json);
                } else {
                    console.log(state);
                }
            })
        },
    };

    // 定义页面所有的操作方法
    Page.operate = {
        init: function() {
            this.tt();
        },
        // 路径点击跳转事件
        tt: function() {
            console.log("tt");
            $("#submitLogin").on('click', function(event) {
                console.log("tt click");
                Page.action.queryList();
            });
        },
    }
    var Utils = {

    }
    module.exports = Page;
});