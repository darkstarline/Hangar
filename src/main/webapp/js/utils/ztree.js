define(function (require, exports, module) {


    // 引入Bootstrap-treeview树结构插件
    require('lib/jquery/jquery-ztree/3.5.30/css/zTreeStyle/zTreeStyle.css');
    require('lib/jquery/jquery-ztree/3.5.30/js/jquery.ztree.all.min.js');

    // API接口注册工具
    var requireAPI = require('utils/api');
    var API = requireAPI.init();
    // Ajax常用方法工具
    var Ajax = require('utils/ajax');

   /**
    * @desc: jQuery ztree 的封装
    */
    var zTree = function (ztreeId, setting,  expand) {
        this.instance = null;                 //jQuery ztree绑定的对象
        this.ztreeId = ztreeId;
        this.setting = setting;
        this.expand = expand;
    };
    zTree.prototype = {
        /**
         * 初始化jQuery ztree
         */
        init: function (treeData) {
            var ztreeId = this.ztreeId;
            var defaults = {
                view: {
                    selectedMulti: false
                }
            };
            this.setting = $.extend(defaults,this.setting);
            this.instance =  $.fn.zTree.init($(ztreeId), this.setting, treeData);
            if (this.expandAll) {
                this.instance.expandAll(this.expand);
            } else {
                this.instance.expandAll(false);
            }
            return this;
        },
        getInstance : function () {
            return this.instance;
        },
        /**
         * 选中id列表所指定的节点
         */
        setCheckedNodesById : function (checkedIds) {
            var instance = this.instance;
            for(var i=0; i<checkedIds.length; i++){
                var id = checkedIds[i];
                var node = instance.getNodeByParam('id',id,null);
                if(node){
                    node.checked = true;
                    instance.updateNode(node);
                }
            }
        },

        /**
         * 获得选中节点id组成的数据
         */
        getSelectedIdArray : function () {
            var nodes = this.instance.getCheckedNodes(true);
            var array = [];
            if(nodes != null && nodes.length > 0){
                for(var i=0; i<nodes.length; i++){
                    array.push(nodes[i]['id']);
                }
            }
            return array;
        },

        /**
         * 获得全部节点数据
         */
        getAllNodes : function () {
            var nodes = this.instance.transformToArray(this.instance.getNodes());
            return nodes;
        },

        /**
         * 根据TID，获得数据
         */
        getNodeByTId: function (tId) {
            var node = this.instance.getNodeByTId(tId);
            return node;
        },
        /**
         * 根据ID，获得数据
         */
        getNodeById: function (id) {
            return this.instance. treeObj.getNodeByParam("id", id,null);
        },
        // 刷新
        refresh:function(){
            this.instance.reAsyncChildNodes(null,"refresh")
        }
    };
    module.exports = zTree;
});