define(function(require, exports, module) {
    // API接口注册工具
    var requireAPI = require('utils/api');
    var API = requireAPI.init();
    var Ajax = require('utils/ajax');
    // Bootstrap-Table 常用方法工具
    var BTable = require('utils/btable');
    //Bootstrap-Dialog 常用方法工具
    var BDialog = require('utils/bdialog');
    // 引入jQuery插件，用于信提示等;
    require('utils/jqPlugin');
    // 查询
    // API.add("fileList", "fileList.json", "cmsapp/upass/fileBrowse");
    // API.add("catalogList", "fileList.json", "upass/fileBrowse/getCatalogs");
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

        }
    }
    Page.data = {

    }


    Page.action = {
        // 查询所有列表接口
        queryList: function() {

        },
    };

    // 定义页面所有的操作方法
    Page.operate = {
        // 路径点击跳转事件
        routerClick: function() {

        },
    }
    var Utils = {

    }
    module.exports = Page;
});