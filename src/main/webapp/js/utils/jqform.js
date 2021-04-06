define(function(require, exports, module) {

    // 引入jQuery Validation插件
    /*require.async('lib/jquery/jquery-validation/1.17.0/jquery.validate.js', function() {
        $.extend($.validator.messages, {
            required: "这是必填字段",
            remote: "请修正此字段",
            email: "请输入有效的电子邮件地址",
            url: "请输入有效的网址",
            date: "请输入有效的日期",
            dateISO: "请输入有效的日期 (YYYY-MM-DD)",
            number: "请输入有效的数字",
            digits: "只能输入数字",
            creditcard: "请输入有效的信用卡号码",
            equalTo: "你的输入不相同",
            extension: "请输入有效的后缀",
            maxlength: $.validator.format("最多可以输入 {0} 个字符"),
            minlength: $.validator.format("最少要输入 {0} 个字符"),
            rangelength: $.validator.format("请输入长度在 {0} 到 {1} 之间的字符串"),
            range: $.validator.format("请输入范围在 {0} 到 {1} 之间的数值"),
            max: $.validator.format("请输入不大于 {0} 的数值"),
            min: $.validator.format("请输入不小于 {0} 的数值")
        });
    });

    // 引入jQuery Form插件
    require('lib/jquery/jquery-form/4.2.2/jquery.form.min.js');
    // 引入jQuery插件，用于信提示等;
    require('utils/jqPlugin');*/

    // API接口注册工具
    var requireAPI = require('utils/api');
    var API = requireAPI.init();
    // Ajax常用方法工具
    var Ajax = require('utils/ajax');
    // Handlebars 常用方法工具
    var HBS = require('utils/hbs');


    /**
     * @desc: jQuery Form 的封装
     */
    var jqForm = {
        submit: function(form, setting, validateFlag) {
            var options = {
                //target: '#output',          //把服务器返回的内容放入id为output的元素中
                beforeSubmit: setting.beforeSubmit, //提交前的回调函数
                success: function(json) {
                    if (typeof(json.success) == "string") {
                        if (json.success == "true"||json.success) {
                            setting.success(json)
                        } else {
                            $.message({ message: json.message, type: 'error' });
                        }
                    }
                    // if (json.code == 200) {
                    //     setting.success(json)
                    // } else {
                    //     $.message({ message: json.message, type: 'error' });
                    // }
                }, //提交后的回调函数
                error: function(json) {
                    if (json) {
                        console.log("403")
                        $.message({ message: json.message, type: 'error' });
                    }
                },
                url: setting.url, //默认是form的action， 如果申明，则会覆盖
                type: 'post', //默认是form的method（get or post），如果申明，则会覆盖
                dataType: "json", //html(默认), xml, script, json...接受服务端返回的类型
                //clearForm: true,          //成功提交后，清除所有表单元素的值
                //resetForm: true,          //成功提交后，重置所有表单元素的值
                data: setting.data,
                timeout: 100000 //限制请求的时间，当请求大于3秒后，跳出请求
            }

            if (setting.dealFlag == false) {
                options.success = setting.success;
            }
            if (!validateFlag) {
                $(form).validate({
                    showErrors: function(errorMap, errorList) {
                        // 判断表单未通过的数量，大于0取消按钮loading
                        if (this.numberOfInvalids() > 0) {
                            Ladda.stopAll();
                        }
                    },
                    submitHandler: function(form) {
                        $(form).ajaxSubmit(options);
                        return false; //阻止表单默认提交
                    }
                });
                $(form).submit();
            } else {
                $(form).ajaxSubmit(options);
            }
            //alert($(form).html())
        }
    };
    module.exports = jqForm;
});