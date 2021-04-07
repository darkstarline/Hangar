// 全局配置
window.CONF = {


    localPort: '8000',


    seajsVersion: null,

    base: ["/", "/"],


    APIBase: ["/js/json/", "/"],



    defaultHashPage: 'gundam.html',


    systemTheme:'skin-blue',


    getCONFState: function() {
        return window.location.href.indexOf(this.localPort) >= 0 ? 0 : 1;
    }
}


function getContextPath() {
    var contextPath = document.location.pathname;
    var index =contextPath.substr(1).indexOf("/");
    contextPath = contextPath.substr(0,index+1);
    if(contextPath=="/views"){
        contextPath="";
    }
    return contextPath;
}

seajs.config({


    alias: {
        /*'handlebars': 'Hanger/js/handlebars/4.0.11/handlebars.js',
        'mockjs': 'Hanger/js/mockjs/1.0.1/mock.js',
        'My97DatePicker':'Hanger/js/My97DatePicker/WdatePicker.js'*/
    },


    preload: [
        /*'handlebars',
        'mockjs',
        'My97DatePicker'*/
    ],


    paths: {
        // 'global': 'script/global',
        'utils': 'Hanger/js/utils',
        // 'bootstrap': 'lib/bootstrap',
        // 'plugin':'lib/plugin'
    },


    vars: {
        'locale': 'en-us'
    },


    map: [
        ['.css', '.css?v=' + CONF.seajsVersion]
        //['.js', '.js?v=' + CONF.seajsVersion]
    ],


    debug: true,


    base: CONF.base[CONF.getCONFState()],


    charset: 'utf-8'
});