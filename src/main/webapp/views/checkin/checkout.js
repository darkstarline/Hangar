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
            Ajax.postInfoAndFile(API.get('gundamStorageController'), cmd, function(json, state) {
                if (json.success) {
                    console.log("success");
                    // $.message(json.message+"保存成功！");
                    // Page.action.getCatalogTree();
                }else{
                    console.log(json.message);
                    $.message({message:json.message,type:"error"});
                }
            })
        },
        checkinCover:function(cover){

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
                // cmd = Utils.jsonToStr(cmd);
                console.log(cmd);
                Page.action.checkin(cmd);
            });
        },
        aotu2base64save: function (){

        }
    }
    var Utils = {
        justiceWatching: function (organism){
            
        },
        /*img2base64:function(img){
            //TODO 增加文件类型的判断
            var img64;
            var reader=new FileReader()
            reader.readAsDataURL(img);
            reader.onload =function (e){
                console.log(e.target.result);
                img64=e.target.result;
            }
            return img64;
        },*/
        // 鉴权参数处理
        getSaveParam: function() {
            var authData = this.getObjectData($("#baseInfo"));
            var coverData=this.getCoverData($("#coverImg"));
            //合并多个数据
            var allData=$.extend(true, {},authData,coverData);
            return this.toFormData(allData);
        },
        toFormData:function(data){
            var formData=new FormData();
            for (var key in data) {
                formData.append(key,data[key]);
            }
            return formData;
        },
        // 表单获取数据 json对象数据
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
        getCoverData:function(obj,callback){
            //TODO 图片转base64加密
            var data = {};
            var img = $(obj).find("input")[0].files[0];
            // var img = document.getElementById('cover').files[0];
            // var _key = $(obj).find("input")[0].name;
            var _key = "file";
            data[_key]=img;
            //这个不知道为啥取不到，暂时搁置
            /*$(obj).find("input,select,textarea").each(function(index, el) {
                if ('file' == $(el).attr('type')) {
                    var _key = $(el).attr('name');
                    var img = $(el).files[0];
                    //var _value = Utils.img2base64(file);
                } else {
                    var _key = $(el).attr('name');
                    var _value = $(el).val();
                }
                data[_key] = _value;
            });*/
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
