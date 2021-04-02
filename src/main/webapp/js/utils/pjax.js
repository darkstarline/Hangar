define(function (require, exports, module) {
    var pjax = {
        container:'#pjax-container',
        init:function(){
            this._render();
            this.configure();
        },
        _render:function(){
            $(document).pjax('a[data-pjax]', this.container);
            //$(document).pjax('a[data-pjax]', this.container, {fragment: this.container,timeout:8000});
        },
        configure:function(){
            NProgress.configure({ parent: this.container });
            $(document).on('pjax:start', function() {
                NProgress.start();
            });
            $(document).on('pjax:end', function() {
                NProgress.done();
            });
        },
        openUrl:function(url){
            this._render();
            $.pjax({url: url , container: this.container})
        }
    }
    module.exports = pjax;
});