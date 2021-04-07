define(function(require, exports, module) {
    console.log("load mytest.js");
    // API接口注册工具
    var requireAPI = require('utils/api.js');
    var API = requireAPI.init();
    var Ajax = require('utils/ajax');
    // 引入jQuery插件，用于信提示等;
    require('utils/jqPlugin');
    // 查询
    API.add("fileList", "travel.json", "");
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
            Page.action.queryList();
            Page.operate.init();
        }
    }
    Page.data = {

    }

    Page.action = {
        // 查询所有列表接口
        queryList: function() {
            Ajax.postJson(API.get('fileList'), null, function (json, state) {
                var organismList=eval('json.'+"organismList");
                for(var i=0;i<organismList.length;i++){
                    var row=$(".news-category");
                    console.log(row.innerHTMl);
                    var organism=organismList[i];
                    console.log(organism);
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

        },
    }
    var Utils = {

    }
    module.exports = Page;
});
