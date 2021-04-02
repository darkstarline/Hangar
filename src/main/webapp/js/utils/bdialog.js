define(function (require, exports, module) {

    // 引入Bootstrap-dialog插件
    require('lib/bootstrap/bootstrap-dialog/1.35.4/css/bootstrap-dialog.css');
    require('lib/bootstrap/bootstrap-dialog/1.35.4/js/bootstrap-dialog.js');


    // API接口注册工具
    var requireAPI = require('utils/api');
    var API = requireAPI.init();
    // Ajax常用方法工具
    var Ajax = require('utils/ajax');
    // Handlebars 常用方法工具
    var HBS = require('utils/hbs');


    /**
    * @desc: BootStrap Dialog 的封装
    */
    var BDialog = {
        showAjaxMessage:function(title,hbsTd,url,buttons){

            BootstrapDialog.show({
                title: title,
                message: function(dialog) {
                    var content = HBS.getAjaxCompileHTML(hbsTd,{
                        url : url,
                        cmd : {"date":'1'},
                        callback : function(html){
                            alert('加载完成，调用getRequestA回调方法！')
                        }
                    });
                    return $(content);
                },
                buttons: buttons
            });
        },
        confirm:function(message,callback){

            if (arguments.length !== 2)
                callback = message, message = '您确定要删除吗？';

            BootstrapDialog.confirm({
                size:BootstrapDialog.SIZE_SMALL,
                title: '确认信息',
                message: message,
                type: BootstrapDialog.TYPE_WARNING, // <-- Default value is BootstrapDialog.TYPE_PRIMARY
                closable: true, // <-- Default value is false
                draggable: true, // <-- Default value is false
                btnCancelLabel: '取消', // <-- Default value is 'Cancel',
                btnOKLabel: '确定', // <-- Default value is 'OK',
                btnOKClass: 'btn-warning', // <-- If you didn't specify it, dialog type will be used,
                callback: function(result) {
                    // result will be true if button was click, while it will be false if users close the dialog directly.
                    if(result) {
                        callback(true);
                    }else {
                        callback(false);
                    }
                }
            });
        }
    };
    module.exports = BDialog;
});