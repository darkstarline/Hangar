define(function(require, exports, module) {
    console.log("load mytest.js");
    // API接口注册工具
    var requireAPI = require('utils/api.js');
    var API = requireAPI.init();
    var Ajax = require('utils/ajax');
    // 引入jQuery插件，用于信提示等;
    require('utils/jqPlugin');
    // 查询
    API.add("getMessage", "single-post.json", "gundamSearch/euro");
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
            Page.action.query();
            Page.operate.init();
        }
    }
    Page.data = {

    }

    Page.action = {
        // 查询所有列表接口
        query: function() {
            var codeName = $.getUrlParam('MS');
            console.log(codeName);
            var cmd = {
                organismCodeName : codeName
            }
            Ajax.postJson(API.get('getMessage'), cmd, function (json, state) {
                //异步生成json数据
                /*if(json.state){
                    // var organism=eval('json.'+"data");
                    // Utils.justiceWatching(organism);
                    Utils.justiceWatching(json.data);
                }*/
                Utils.justiceWatching(json.data);
                // window.location.href = "http://www.baidu.com";
            })
        },
    };

    // 定义页面所有的操作方法
    Page.operate = {
        init: function() {
            this.tt();
            this.search();
        },
        // 路径点击跳转事件
        tt: function() {
        },
        search : function () {
            $('[name=gundamSearch]').bind('keypress',function(event){
                if(event.keyCode == "13")
                {
                    var keyword = $('[name=gundamSearch]').val();
                    var searchUrl = "../travel/travel.html?gh="+keyword;
                    $(window).attr('location',searchUrl);
                    // $(window).attr('location','../../index.html');
                    return false;
                }
            });
        }
    }
    var Utils = {
        justiceWatching: function (organism){
            var gundamInfo=$("#organism");
            var introduction=$("#introduction");
            var p0=$("<p></p>").text(organism.introduction);
            introduction.append(p0);
            // gundamInfo.text("54663546");
            var x="<ul>\n" +
                "        <li>Lorem ipsum dolor sit amet, consete.</li>\n" +
                "        <li>Ture sadipscing elitr, sed diam.</li>\n" +
                "        <li>Nonumy eirmo od tey mpor invidunt.</li>\n" +
                "        <li>Uti labore et dolorer magna aliqu.</li>\n" +
                "        <li>Lorem ipsum dolor sit amet, consete.</li>\n" +
                "        <li>Ture sadipscing elitr, sed diam.</li>\n" +
                "        <li>Nonumy eirmo od tey mpor invidunt.</li>\n" +
                "        <li>Uti labore et dolorer magna aliqu.</li>\n" +
                "        <li>Lorem ipsum dolor sit amet, consete.</li>\n" +
                "        <li>Ture sadipscing elitr, sed diam.</li>\n" +
                "        <li>Nonumy eirmo od tey mpor invidunt.</li>\n" +
                "        <li>Uti labore et dolorer magna aliqu.</li>\n" +
                "        <li>Lorem ipsum dolor sit amet, consete.</li>\n" +
                "        <li>Ture sadipscing elitr, sed diam.</li>\n" +
                "        <li>Nonumy eirmo od tey mpor invidunt.</li>\n" +
                "        <li>Uti labore et dolorer magna aliqu.</li>\n" +
                "        <li>Lorem ipsum dolor sit amet, consete.</li>\n" +
                "        <li>Ture sadipscing elitr, sed diam.</li>\n" +
                "        <li>Nonumy eirmo od tey mpor invidunt.</li>\n" +
                "        <li>Uti labore et dolorer magna aliqu.</li>\n" +
                "       </ul>";
            var ul=$("<ul></ul>");
            var li0=$("<li></li>").text(organism.organismNumber);
            var li1=$("<li></li>").text(organism.organismCodeName);
            var li2=$("<li></li>").text(organism.jpnName);
            var li3=$("<li></li>").text(organism.enName);
            var li4=$("<li></li>").text(organism.cnName);
            var li5=$("<li></li>").text(organism.animation);
            var li6=$("<li></li>").text(organism.organismType);
            var li7=$("<li></li>").text(organism.belong);
            var li8=$("<li></li>").text(organism.manufacturer);
            var li9=$("<li></li>").text(organism.organismSieze);
            var li10=$("<li></li>").text(organism.netWeight);
            var li11=$("<li></li>").text(organism.fullWeight);
            var li12=$("<li></li>").text(organism.armoredStructure);
            var li13=$("<li></li>").text(organism.output);
            var li14=$("<li></li>").text(organism.propulsion);
            var li15=$("<li></li>").text(organism.acceleration);
            var li16=$("<li></li>").text(organism.sensorRadius);
            var li17=$("<li></li>").text(organism.fixedArmed);
            var li18=$("<li></li>").text(organism.dubut);
            var li19=$("<li></li>").text(organism.cockpit);
            var li20=$("<li></li>").text(organism.pilot);
            var li21=$("<li></li>").text(organism.chooseWeapons);
            var li22=$("<li></li>").text(organism.degreeTime);
            var li23=$("<li></li>").text(organism.groundSpeed);
            var li24=$("<li></li>").text(organism.waterSpeed);
            ul.append(li0);
            ul.append(li1);
            ul.append(li2);
            ul.append(li3);
            ul.append(li4);
            ul.append(li5);
            ul.append(li6);
            ul.append(li7);
            ul.append(li8);
            ul.append(li9);
            ul.append(li10);
            ul.append(li11);
            ul.append(li12);
            ul.append(li13);
            ul.append(li14);
            ul.append(li15);
            ul.append(li16);
            ul.append(li17);
            ul.append(li18);
            ul.append(li19);
            ul.append(li20);
            ul.append(li21);
            ul.append(li22);
            ul.append(li23);
            ul.append(li24);
            gundamInfo.append(ul);
        }
    }
    module.exports = Page;
});
