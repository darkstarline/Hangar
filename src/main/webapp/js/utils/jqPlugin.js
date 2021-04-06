define(function(require, exports, module) {
    /*require('lib/jquery/jquery-message/1.0.0/jquery-message.css');
    require('lib/jquery/jquery-message/1.0.0/jquery-message.js');
    require('lib/bootstrap/bootstrap-ladda/ladda-themeless.css');*/
    // require('lib/bootstrap/bootstrap-ladda/spin.js');
    // require('lib/bootstrap/bootstrap-ladda/ladda.js');


     // 自定义jQuery插件
    $.fn.selectInit = function(){
     	this.each(function() {
            $(this).find("option[value='" +  $(this).data("init") + "']").attr("selected", true)
        });
    }
});