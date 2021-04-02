define(function(require, exports, module) {
	/**
	 * @desc: 利用数组存储不同环境下的接口路径，根据开发环境状态获取对应接口路径
	 */
	var API = {

		init : function() {
			return new UrlMap();
		}

	}

	function UrlMap() {
		/**
		 * 存储接口的数组，初始化两个空对象，也可以放默认值
		 * 
		 * @type {Array}
		 */
		this.APIMap = [ {}, {} ]
	}

	UrlMap.prototype = {

		/**
		 * add 注册接口的方法
		 * 
		 * @method add
		 * @param {String}
		 *            uid 接口名称的uid，是唯一
		 * @param {String}
		 *            mockSrc 本地json文件路径
		 * @param {String}
		 *            svSrc 服务器端接口路径
		 */
		add : function(uid, mockSrc, svSrc) {
			this.APIMap[0][uid] = CONF.APIBase[0] + mockSrc;
			this.APIMap[1][uid] = CONF.APIBase[1] + svSrc;
		},
		/**
		 * get 获取注册后接口的方法，根据动态环境开发环境状态获取相对应接口路径
		 * 
		 * @method get
		 * @param {String}
		 *            uid 接口名称的uid，是唯一
		 */
		get : function(uid) {
			return getContextPath()+this.APIMap[CONF.getCONFState()][uid];
		}
	}

	module.exports = API;
});