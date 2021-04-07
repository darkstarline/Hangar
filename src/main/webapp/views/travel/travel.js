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
                   Utils.justiceWatching(organismList[i]);
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
            $("#dplink").on('click', function(event) {
                alert("tt click");
            });
        },
    }
    var Utils = {
        justiceWatching: function (organism){
            var newsCategory=$("#news-category");
            var x="<div class=\"row\" id=\"row0\" name=\"row0\">\n" +
                "       <div class=\"col-lg-5\">\n" +
                "        <div class=\"news-card-grid-item-2\">\n" +
                "         <a href=\"../single-post-1.html\"><div class=\"news-card-thumb news-thumb-bg-1\">这里的背景是图片</div></a>\n" +
                "        </div>\n" +
                "       </div>\n" +
                "       <div class=\"col-lg-7\">\n" +
                "        <div class=\"news-card-grid-item-2 news-card-grid-dark\">\n" +
                "         <h3><a href=\"../single-post-1.html\">这是标题</a></h3>\n" +
                "         <p>这是简介</p>\n" +
                "         <div class=\"news-grid-action\">\n" +
                "          <p class=\"news-author  author-name\">by- <span>这是操作员</span></p>\n" +
                "          <p class=\"news-card-link\">\n" +
                "           <a href=\"../single-post-1.html\">这是跳转详细界面链接</a>\n" +
                "          </p>\n" +
                "         </div>\n" +
                "        </div>\n" +
                "       </div>\n" +
                "      </div>";
            var div0=$("<div class='row'></div>");
            var div1=$("<div class='col-lg-5'></div>");
            var div2=$("<div class='news-card-grid-item-2'></div>")
            var a1=$("<a href='../single-post-1.html'></a>");
            var div3=$("<div class='news-card-thumb news-thumb-bg-1'></div>").text("这里因该是背景图片");
            a1.append(div3);
            div2.append(a1);
            div1.append(div2);
            div0.append(div1);
            newsCategory.append(div0);
            var div4=$("<div class='col-lg-7'></div>");
            var div5=$("<div class='news-card-grid-item-2 news-card-grid-dark'></div>");
            var h1=$("<h3></h3>");
            var a2=$("<a href='../single-post-1.html'></a>").text("扎古一");
            var p1=$("<p></p>").text("U.C.0075年5月，以MS-05所获得的数据为基础进行改良的MS-05B后期生产型扎古Ⅰ完工。从外观上看，本机与MS-05最大的区别是胸部装甲的形状。U.C.0077年10月，本机开始大规模量产，至大战结束为止，共生产了793架，绝大多数配给了教导机动大队使用，是一年战争初期的主力MS，通称为“旧扎古”。本机的最大缺陷，则是由于动力管内藏而导致出力不高。此机种比初期生产型的综合性能有了很大的提高。装备方面有105mm机关炮、280mm核弹头火箭筒、热能斧。还装备有用于接近战用的盾。因为此时联邦还没有机动战士，所以不考虑加强它在格斗战方面的实力。");
            var div6=$("<div class='news-grid-action'></div>");
            var p2=$("<p class='news-author author-name'></p>").text("夏亚");
            var p3=$("<p class='news-card-link'></p>");
            var a3=$("<a></a>").text("跳转到红色有角三倍速");
            h1.append(a2);
            div5.append(h1);
            div5.append(p1);
            div5.append(div6);
            div4.append(div5);
            p3.append(a3);
            div6.append(p2);
            div6.append(p3);
            div0.append(div4);
        }
    }
    module.exports = Page;
});
