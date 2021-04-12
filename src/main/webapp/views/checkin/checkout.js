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
                    Utils.justiceWatching(organismList[i]);
                }
            })
        },
        checkin: function (cmd){
            $.message({ message: "操作中", type: "loading", time: 99999 });
            Ajax.postJson(API.get('gundamStorageController'), cmd, function(json, state) {
                if (json.success) {
                    console.log("success");
                    // $.message(json.message+"保存成功！");
                    // Page.action.getCatalogTree();
                }else{
                    console.log(json.message);
                    $.message({message:json.message,type:"error"});
                }
            })
        }
    };

    // 定义页面所有的操作方法
    Page.operate = {
        init: function() {
            this.aircraftStorage();
        },
        // 路径点击跳转事件

        aircraftStorage: function() {
            $(".btn").on('click', function(event) {
                var cmd = Utils.getSaveParam();
                cmd = Utils.jsonToStr(cmd);
                console.log(cmd);
                Page.action.checkin(cmd);
            });
        },
    }
    var Utils = {
        justiceWatching: function (organism){
            
        },
        // 鉴权参数处理
        getSaveParam: function() {
            var authData = this.getObjectData($("#baseInfo"), true);
            //合并多个数据
            return $.extend(true,{},authData);
        },
        // 表单获取数据 json对象数据
        getObjectData: function(obj, authFlag, callback) {
            var data = {};
            $(obj).find("input,select,textarea").each(function(index, el) {
                var _key = $(el).attr('name');
                var _value = $(el).val();
                data[_key] = _value;
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
