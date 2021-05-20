define(function(require, exports, module) {
    console.log("load mytest.js");
    // API接口注册工具
    var requireAPI = require('utils/api.js');
    var API = requireAPI.init();
    var Ajax = require('utils/ajax');
    // 引入jQuery插件，用于信提示等;
    require('utils/jqPlugin');
    // 查询
    // API.add("fileList", "travel.json", "test/t3");
    API.add("gundamStorageController", "travel.json", "gundamStorage/save");
    API.add("fileList", "retMessage.json", "gundamStorage/save");
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
        imgurl : "/Hanger/gundamFileDownload/download"
    }

    Page.action = {
        // 查询所有列表接口
        queryList: function() {
            var file=document.getElementById('formFileLg').files[0]
            var formdata = new FormData();
            formdata.append("file",file);
            formdata.append("organismId","12345");
            //创建xhr，使用ajax进行文件上传
            var xhr = new XMLHttpRequest();
            xhr.open("post","/Hanger/gundamStorage/save");
            //回调
            //将formdata上传
            xhr.send(formdata);
        },
        testService: function() {
            Ajax.postJson(API.get('fileList'), null, function (json, state) {
                if(state=='200'){
                    console.log("test service success");
                }
            })
        }
    };

    // 定义页面所有的操作方法
    Page.operate = {
        init: function() {
            this.tt();
            // this.test();
        },
        // 路径点击跳转事件
        tt: function() {
            console.log("tt");
            $("#submitLogin").on('click', function(event) {
                console.log("tt click");
                Page.action.testService();
            });
        },
        test:function(){
            // $("#test").css("background-color","yellow");
            // $("#test").width(400);
            // $("#test").height(800);
            // $("#sss").css("width","100%");
            // $("#sss").css("hight","auto");

            var newsCategory=$("#test");
            var div0=$("<div class='row'></div>");
            var div1=$("<div class='col-lg-5'></div>");
            var div2=$("<div class='news-card-grid-item-2'></div>")
            var a1=$("<a href='../single-post/single-post-1.html'></a>");
            var div3=$("<div class='news-card-thumb'></div>");
            a1.append(div3);
            div2.append(a1);
            div1.append(div2);
            div0.append(div1);
            newsCategory.append(div0);
            div1.css("background-image","url(/Hanger/test/t1)");
        }

    }
    var Utils = {

    }
    module.exports = Page;
});