define(function(require, exports, module) {

    // 引入Bootstrap-table插件
    require('assets/style/skin/bootstrap-table/bootstrap-table-skin-trek.css');
    require('bootstrap/bootstrap-table/1.11.1/bootstrap-table.js');
    require('utils/jqPlugin');

    // 引入中文化失效，待处理
    //seajs.use('bootstrap/bootstrap-table/1.11.1/locale/bootstrap-table-zh-CN.js');

    /**
     * @desc: BootStrap Table 的封装
     */
    var BTable = function(bstableId, url, columns) {
        this.url = url;
        this.columns = columns;
        this.btInstance = null; //jquery和BootStrapTable绑定的对象 实例对象
        this.bstableId = bstableId;
        this.queryParams = {}; // 向后台传递的自定义参数
        var $toolbar = $(bstableId).parents(".box").find("[name='toolbar']");
        var that = this;
        // 表格的默认配置参数
        this.btOption = {
            contentType: "application/x-www-form-urlencoded",
            url: url, //请求地址
            method: "get",
            ajaxOptions: { //ajax请求的附带参数
                data: {}
            },
            columns: columns ? columns : [], //列配置项
            height: "", ////默认表格高度665
            toolbar: $toolbar.length ? $toolbar : undefined, //顶部工具条
            striped: false, //是否显示行间隔色
            cache: false, //是否使用缓存,默认为true
            sortable: false, //是否启用排序
            sortOrder: "desc", //排序方式
            pageNumber: 1, //初始化加载第一页，默认第一页
            pageSize: 10, //每页的记录行数（*）
            pageList: [10, 20, 50], //可供选择的每页的行数（*）
            queryParamsType: '', //设置为 'limit' 则会发送符合 RESTFul 格式的参数
            //返回参数必须包含limit, offset, search, sort, order 否则, 需要包含: pageSize, pageNumber, searchText, sortName, sortOrder. 返回false将会终止请求。
            queryParams: function(params) {
                var pa = {
                    pageSize: params.pageSize,
                    pageNumber: params.pageNumber - 1
                }
                var defaultParams = $.extend(pa, that.queryParams);

                return defaultParams;
            }, // 向后台传递的自定义参数
            responseHandler: function(res) {
                console.log(res)
                if (res.code == 200) {
                    return {
                        "rows": res.data.content, // 具体每一个bean的列表
                        "total": res.data.totalElements // 总共有多少条返回数据
                    }
                } else {
                    $.message({ message: "查询失败！", type: "error" });
                }

            },
            sidePagination: "server", //分页方式：client客户端分页，server服务端分页（*）
            search: false, //是否显示表格搜索，此搜索是客户端搜索，不会进服务端
            strictSearch: true, //设置为 true启用 全匹配搜索，否则为模糊搜索
            showColumns: $toolbar.length ? true : false, //是否显示所有的列
            showRefresh: $toolbar.length ? true : false, //是否显示刷新按钮
            minimumCountColumns: 2, //最少允许的列数
            clickToSelect: true, //是否启用点击选中行
            searchOnEnterKey: true, //设置为 true时，按回车触发搜索方法，否则自动触发搜索方法
            pagination: true, //是否显示分页条
            icons: {
                refresh: 'glyphicon-repeat',
                toggle: 'glyphicon-list-alt',
                columns: 'glyphicon-list'
            },
            iconSize: 'outline',
            // onDblClickRow: this.onDblClickRow,
            // onClickRow: this.onClickRow,
            // onLoadSuccess: this.onLoadSuccess
        }
    };
    BTable.prototype = {
        /**
         * 初始化bootstrap table
         */
        init: function(opt) {
            // 重置查询参数，不然页面切换之间会缓存查询条件
            // 为true时，不用重置
            if (!opt) {
                this.queryParams = {};
            }
            this.setLocales();
            this.btInstance = $(this.bstableId).bootstrapTable(this.btOption);
            return this;
        },

        // 设置get 或者post
        setMethod: function(method) {
            this.btOption.method = method;
        },
        /**
         * 设置分页条显示以及分页后返回数据处理
         * @param {[boolean]} flag [description]
         * 一般用于不分页时设置，默认的为分页
         */
        setPagination: function(flag, handleFunc) {
            this.btOption.pagination = flag;
            this.btOption.responseHandler = handleFunc;
        },
        // 本地分页设置
        setClientPage: function(handleFunc) {
            var thiz = this;
            this.btOption = {
                url: thiz.url, //请求后台的URL（*）
                // contentType: "application/x-www-form-urlencoded;charset=UTF-8",
                method: 'post', //请求方式（*）
                toolbar: '[name="toolbar"]', //工具按钮用哪个容器
                striped: true, //是否显示行间隔色
                cache: false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
                pagination: true, //是否显示分页（*）
                sortable: false, //是否启用排序
                sortOrder: "asc", //排序方式
                queryParams: function(params) {
                    var defaultParams = $.extend({}, thiz.queryParams);
                    return defaultParams;
                }, // 向后台传递的自定义参数
                sidePagination: "client", //分页方式：client客户端分页，server服务端分页（*）
                pageNumber: 1, //初始化加载第一页，默认第一页
                pageSize: 10, //每页的记录行数（*）
                pageList: [10, 25, 50, 100], //可供选择的每页的行数（*）
                search: true, //是否显示表格搜索，此搜索是客户端搜索，不会进服务端
                strictSearch: false,
                showColumns: true, //是否显示所有的列
                showRefresh: true, //是否显示刷新按钮
                minimumCountColumns: 2, //最少允许的列数
                clickToSelect: true, //是否启用点击选中行
                uniqueId: "ID", //每一行的唯一标识，一般为主键列
                showToggle: true, //是否显示详细视图和列表视图的切换按钮
                cardView: false, //是否显示详细视图
                detailView: false, //是否显示父子表
                showExport: false, //是否显示导出
                exportDataType: "basic",
                responseHandler: handleFunc ? handleFunc : function(res) {
                    if (res) {
                        return {
                            "data": res.data // 具体每一个bean的列表
                        }
                    } else {
                        $.message({ message: "查询失败！", type: "error" });
                    }

                },
                columns: thiz.columns
            }
        },
        //每一行的唯一标识，一般为主键列
        setUniqueId: function(id) {
            this.btOption.uniqueId = id;
        },
        /**
         * 向后台传递的自定义参数
         * @param param
         */
        setQueryParams: function(param) {
            this.queryParams = param;
        },
        /**
         * 设置分页方式：server 或者 client
         */
        setPaginationType: function(type) {
            this.btOption.sidePagination = type;
        },

        /**
         * [updateAssets 修改对应的行数据，本地修改]
         * @param  {[type]} id 每一行的唯一标识，一般为主键列
         * @param  {[type]} data  [修改后的数据]
         */
        updateByUniqueId: function(id, data) {
            this.btInstance.bootstrapTable('updateByUniqueId', {
                id: id,
                row: data
            });
        },
        /**
         * [updateAssets 修改对应的行数据，本地修改]
         * @param  {[type]} index 索引
         * @param  {[type]} data  [修改后的数据]
         */
        updateByIndex: function(index, data) {
            this.btInstance.bootstrapTable('updateRow', {
                index: index,
                row: data
            });
        },
        // 删除表格
        destroy: function() {
            $(this.bstableId).bootstrapTable('destroy');
        },
        // 重置url参数
        resetUrl: function(url) {
            this.destroy();
            this.url = url;
        },
        /**
         * 刷新 bootstrap 表格
         * Refresh the remote server data,
         * you can set {silent: true} to refresh the data silently,
         * and set {url: newUrl} to change the url.
         * To supply query params specific to this request, set {query: {foo: 'bar'}}
         */
        refresh: function(parms) {
            if (typeof parms != "undefined") {
                this.btInstance.bootstrapTable('refreshOptions', parms);
            } else {
                this.btInstance.bootstrapTable('destroy');
                this.btInstance = $(this.bstableId).bootstrapTable(this.btOption);
                // this.btInstance.bootstrapTable('refresh');
            }
        },
        // 获取选中行数据
        getSelections: function() {
            var arrRes = this.btInstance.bootstrapTable("getSelections");
            return arrRes;
        },
        // 获取表格数据
        getData: function() {
            var arrRes = this.btInstance.bootstrapTable("getData");
            return arrRes;
        },
        setLocales: function() {
            $.extend($.fn.bootstrapTable.defaults, {
                formatLoadingMessage: function() {
                    return '正在努力地加载数据中，请稍候……';
                },
                formatRecordsPerPage: function(pageNumber) {
                    return '每页显示 ' + pageNumber + ' 条记录';
                },
                formatShowingRows: function(pageFrom, pageTo, totalRows) {
                    return '第 ' + pageFrom + ' - ' + pageTo + ' 条，总 ' + totalRows + ' 条';
                },
                formatSearch: function() {
                    return '搜索';
                },
                formatNoMatches: function() {
                    return '没有找到匹配的记录';
                },
                formatPaginationSwitch: function() {
                    return '隐藏/显示分页';
                },
                formatRefresh: function() {
                    return '刷新';
                },
                formatToggle: function() {
                    return '切换';
                },
                formatColumns: function() {
                    return '列';
                },
                formatExport: function() {
                    return '导出数据';
                },
                formatClearFilters: function() {
                    return '清空过滤';
                }
            });
        }
    };
    module.exports = BTable;
});