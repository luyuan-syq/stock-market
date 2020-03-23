/*! aps.page.js */
//initialize
/* backbone library */
(function(){
	var _ctor = function(){};
	var _inherits = function(parent, protoProps, staticProps) {
		  var child;
		  // The constructor function for the new subclass is either defined by you
		  // (the "constructor" property in your `extend` definition), or defaulted
		  // by us to simply call `super()`.
		  if (protoProps && protoProps.hasOwnProperty('constructor')) {
		    child = protoProps.constructor;
		  } else {
		    child = function(){ return parent.apply(this, arguments); };
		  }

		  // Set the prototype chain to inherit from `parent`, without calling
		  // `parent`'s constructor function.
		  _ctor.prototype = parent.prototype;
		  child.prototype = new _ctor();

		  // Add prototype properties (instance properties) to the subclass,
		  // if supplied.
		  if (protoProps) $.extend(child.prototype, protoProps);

		  // Add static properties to the constructor function, if supplied.
		  if (staticProps) $.extend(child, staticProps);

		  // Correctly set child's `prototype.constructor`, for `instanceof`.
		  child.prototype.constructor = child;

		  // Set a convenience property in case the parent's prototype is needed later.
		  child.__super__ = parent.prototype;
		  return child;
		};
	
	var _extend = function (protoProps, classProps) {
	    var child = _inherits(this, protoProps, classProps);
	    child.extend = _extend;
	    return child;
	};
	
	APS.mvc || (APS.mvc = {});
	
	APS.mvc.Events = {
		    // Bind an event, specified by a string name, `ev`, to a `callback` function.
		    // Passing `"all"` will bind the callback to all events fired.
		    bind : function(ev, callback, context) {
		      var calls = this._callbacks || (this._callbacks = {});
		      var list  = calls[ev] || (calls[ev] = []);
		      list.push([callback, context]);
		      return this;
		    },

		    // Remove one or many callbacks. If `callback` is null, removes all
		    // callbacks for the event. If `ev` is null, removes all bound callbacks
		    // for all events.
		    unbind : function(ev, callback) {
		      var calls;
		      if (!ev) {
		        this._callbacks = {};
		      } else if (calls = this._callbacks) {
		        if (!callback) {
		          calls[ev] = [];
		        } else {
		          var list = calls[ev];
		          if (!list) return this;
		          for (var i = 0, l = list.length; i < l; i++) {
		            if (list[i] && callback === list[i][0]) {
		              list[i] = null;
		              break;
		            }
		          }
		        }
		      }
		      return this;
		    },

		    // Trigger an event, firing all bound callbacks. Callbacks are passed the
		    // same arguments as `trigger` is, apart from the event name.
		    // Listening for `"all"` passes the true event name as the first argument.
		    trigger : function(eventName) {
		      var list, calls, ev, callback, args;
		      var both = 2;
		      if (!(calls = this._callbacks)) return this;
		      while (both--) {
		        ev = both ? eventName : 'all';
		        if (list = calls[ev]) {
		          for (var i = 0, l = list.length; i < l; i++) {
		            if (!(callback = list[i])) {
		              list.splice(i, 1); i--; l--;
		            } else {
		              args = both ? Array.prototype.slice.call(arguments, 1) : arguments;
		              callback[0].apply(callback[1] || this, args);
		            }
		          }
		        }
		      }
		      return this;
		    }
	};
	
	APS.mvc.Model = function(attributes) {
		attributes || (attributes = {});
		var defaults = this.defaults || {};
		this.attributes = $.extend({}, defaults,attributes);
		this.initialize();
	};


	$.extend(APS.mvc.Model.prototype, APS.mvc.Events, {
		attr: function(key, value, options) {
			options = (options == null) ? {}: options;
			if (arguments.length == 1) {
				return this.attributes[key];
			}else if (arguments.length > 1) {
				var old = this.attributes[key];
				this.attributes[key] = value;
				if (!options.silent && old != value) {
					this.trigger('change:' + key, old, value);
				}
			}
		},
		get: function() {
			return this.attributes;
		},
		set: function(attrs, options) {
			options = (options == null) ? {}: options;
			var oldModel = this.attributes;
			var newModel = $.extend(this.attributes, attrs); 
			if (!options.silent) {
				this.trigger('change', this, oldModel, newModel);
			}
		},
		initialize: function() {
			
		}
	});
	
	APS.mvc.View = function(options) {
	    this._configure(options || {});
	    this._ensureElement();
	    this.delegateEvents();
	    this.initialize.apply(this,arguments);
	};
	
	$.extend(APS.mvc.View.prototype, APS.mvc.Events , {
		_ensureElement : function() {
			if (this.el == null) 
				throw new Error("View must have a dom element");
		},
		
	    _configure : function(options) {
	        if (this.options) options = _.extend({}, this.options, options);
	        if (options.model)      this.model      = options.model;
	        if (options.el)         this.el         = options.el;
	        this.options = options;
	    },
	    delegateEvents : function(events) {
	        if (!(events || (events = this.events))) return;
	        $(this.el).unbind();
	        
	        for (var i = 0; i < events.length; i++) {
	      	  var e = events[i];
	      	  if (null==e) continue;
	      	  if (e.length != 3) continue;
	      	  var selector = e[0],eventType = e[1],methodName = e[2];
	      	  var method = $.proxy(this,methodName);
	      	  if (!selector)
	      		  $(this.el).bind(eventType,method);
	      	  else
	      		  $(this.el).delegate(selector,eventType,method);
	        }
	    },
		initialize: function() {
			
		},
		
		remove : function() {
			this.el.remove();
		}
	});
	
	APS.mvc.Model.extend = APS.mvc.View.extend = _extend;
})();


/******   华丽丽的分割线    上面是 类包  ，  下面才是实现 ************/


(function(){
	/* each porltet model */
	APS.Portlet || (APS.Portlet = APS.mvc.Model.extend({
		defaults: {
			mode : 'view',
			state : 'normal',
			row : -1,
			col : -1,
			name : null,
			id: null
		},
		fetch : function(callback) {
			var self = this;
			$.ajax({
				url : APS.PageContext.contextPath + '/ajaxapi' + APS.PageContext.pagePath,
				//@TODO yxq
				/*
				data: {
					action: 'getportletmodel',
					id: this.attr('id')
				},
				*/
				data: {
					format:'json',
					action: 'apsgetportletmodel',
					id: this.attr('id')
				},
				async: false,
				dataType: 'json',
				type : 'get',
				success: function(result) {
					self.set(result.model, {silent:true});//这句话竟然对于新增的portlet不生效@why
				}
			});
		},
		remove : function() {
			var id = this.attr('id');
			var layoutId = this.attr('layoutId');
			var self = this;
			$.ajax({
	            url: APS.PageContext.contextPath + '/ajaxapi' + APS.PageContext.pagePath,
	            //@TODO yxq
	            /*
	            data: {
	                action: 'remove',
	                id: id,
	                layoutId : layoutId
	            },
	            */
	            data: {
	            	format:'json',
	            	action: 'remove',
	            	id: id,
	            	layoutId : layoutId
	            },
	            dataType: 'json',
	            type: 'POST',
	            success: function(result) {
	                if(result.status == 'success') {
	                   self.trigger('portlet:remove:success', self);
	                } else {
	                	self.trigger('portlet:remove:failed', self);
	                }
	            },
	            error: function(jqXhr, textStatus) {
	            	self.trigger('portlet:remove:failed', self);
	            }
	        });
		},
		
		create : function() {
			if (this.attr('id') == null) {
				var url = APS.PageContext.contextPath + '/ajaxapi' + APS.PageContext.pagePath;
				//@TODO yxq
				/*
				var data = $.extend({action:'add'}, {
					name : this.attr('name'),
					layoutId : this.attr('layoutId'),
					row : this.attr('row'),
					col : this.attr('col')
				});
				*/
				var data =  {
					action:'add',
					format:'json',
					name : this.attr('name'),
					layoutId : this.attr('layoutId'),
					row : this.attr('row'),
					col : this.attr('col')
				};
				var self = this;
				$.ajax({
			            url : url,
			            type : 'post',
			            dataType : 'json',
			            data : data,
			            success : function(result, textStatus) {
			            	if (result.status == 'success') {
			            		self.attr('id', result.entity);
			            		self.trigger('portlet:add:success', self);
			            	}else {
			            		self.trigger('portlet:add:failed', self);
			            	}
			        	},
			        	error : function(jqXhr, textStatus) {
			        		self.trigger('portlet:add:failed', self);
			        	}
				});
			}
		},
		
		setState : function(newState) {
			var self = this;
			var oldState = this.getState();
			if (newState == oldState) return;
			$.ajax({
	            url: APS.PageContext.contextPath + '/ajaxapi/' + APS.PageContext.pagePath,
	            data: {
	                action: 'window',
	                id: self.getId(),
	                state: newState
	            },
	            dataType: 'json',
	            success: function(data, textStatus) {
	            	if (data.success) {
	            		self._attributes.state = newState;
	            		self.trigger('portlet:setState:success', oldState, newState);
	            	}else {
	            		self.trigger('portlet:setState:failed', oldState, newState);
	            	}
	            },
	            error : function(jqXHR, textStatus) {
	            	self.trigger('portlet:setState:failed', oldState, newState);
	            }
			});
		},
		setMode : function(newMode) {
			var self = this;
			var oldMode = this.getMode();
			if (newMode == oldMode) return;
			
			$.ajax({
	            url: APS.PageContext.contextPath + '/ajaxapi/' + APS.PageContext.pagePath,
	            data: {
	                action: 'window',
	                id: self.getId(),
	                mode: newMode
	            },
	            dataType: 'json',
	            success: function(data, textStatus) {
	            	if (data.success) {
	            		self._attributes.mode = newMode;
	            		self.trigger('portlet:setMode:success', oldMode, newMode);
	            	}else {
	            		self.trigger('portlet:setMode:failed', oldMode, newMode);
	            	}
	            },
	            error : function(jqXHR, textStatus) {
	            	self.trigger('portlet:setMode:failed', oldMode, newMode);
	            }
			});
		},
		
		setPosition: function(newPosition /*{row:1,col:2,layoutId:'3506'}*/) {
			var newRow = parseInt(newPosition.row);
			var newCol = parseInt(newPosition.col);
			var newLayoutId = newPosition.layoutId;
			var oldPosition = this.getPosition();
			var oldRow = oldPosition.row;
			var oldCol = oldPosition.col;
			var oldLayoutId = oldPosition.layoutId;
			if (newRow >= 0 && newCol >= 0 && (oldRow != newRow || oldCol != newCol || oldLayoutId != newLayoutId)) {
				var self = this;
		        $.ajax({
		            url : APS.PageContext.contextPath + '/ajaxapi' + APS.PageContext.pagePath,
		            type : 'POST',
		            dataType : 'json',
		            data : {
		            	format: 'json',
		            	action: 'move',
		    			id: this.attr('id'),
		    			newrow: newRow,
		    			newcol: newCol,
		    			oldrow: oldRow,
		    			oldcol: oldCol,
		    			newLayoutId: newLayoutId,
		    			oldLayoutId: oldLayoutId  
		            },
		            success : function(result, textStatus) {
		            	if (result.status == 'success') {
		            		/*self.set(newPosition, {silent:true});*/
		            		//@ caihuiji, 将后台返回的 新数据，更新模型
		            		var newAttr  = {col:result.new_position.col , 
		            						row : result.new_position.row , 
		            						layoutId : result.layoutId ,
		            						id : result.id };
		            		
		            		self.set(newAttr, {silent:true});
			            	self.trigger('portlet:move:success', self, oldPosition, newPosition);
		            	}else {
		            		self.trigger('portlet:move:success', self, oldPosition, newPosition);
		            	}
		        	},
		        	error : function() {
		        		self.trigger('portlet:move:error', self, oldPosition, newPosition);
		        	}
		        });
			}
		},
		
		getPosition: function() {
			return {
					row: this.attr('row'),
					col: this.attr('col'),
					layoutId: this.attr('layoutId')
				};
		}
	}));
	
	/*each portlet view*/
	APS.PortletView || (APS.PortletView = APS.mvc.View.extend({
		orderedModes: ['view','edit','edit_defaults','config','print','help'],
		orderedStates: ['minimized','normal','maximized','solo'],
		initialize : function(options) {
			this._displayMode = this.model.attr('navStates').mode;
			this._displayState = this.model.attr('navStates').state;
			if (this._displayState == 'minimized') {
				this.minimize();
			}
			this._bindEvents();
			this.renderControl();
			this.setTitle(this.model.attr('title'));
			
			if (this._displayMode == 'view' && this._displayState == 'normal' && this.model.attr('height') > 0) {
				var h = parseInt(this.model.attr('height'));
				this.el.find('.aps-portlet-screen').height(h);
			}
		},
		//if want to override the behavior, user first need to unbind these event
		_bindEvents: function() {
			this.model.bind('portlet:remove:success', this._onRemoveSuccess,this);
		},
		
		_onRemoveSuccess: function() {
			this.el.remove();
			// @caihuiji 旧代码逻辑没有同步Layout中的PortletView和PortletModel
			APS.rootLayout.removePortlet(this.model.attr('id'));
			APS.rootLayoutView.removePortlet(this.model.attr('id'));
			if (this._displayState == 'maximized') {
				window.location.reload();
			}
		},
		
		getDisplayMode : function() {
			return this._displayMode;
		},
		
		setDisplayMode : function(mode) {
			if (mode != null && mode != this._displayMode) {
				this._displayMode = mode;
				this.refresh();
			}
		},
		
		getDisplayState: function() {
			return this._displayState;
		},
		
		minimize : function() {
			$('.aps-portlet-screen', this.el).hide();
		},
		
		setDisplayState: function(state) {
			if (state != null && state != this._displayState) {
				var refreshPage = (state == 'maximized' || this._displayState == 'maximized');
				this._displayState = state;
				this.refresh(refreshPage);
				this.trigger('portletwindow:change:state', this._displayState, state);
			}
		},
		
		getNavStates: function(mode, state) {
			var m = (mode == null) ? this._displayMode: mode;
			var s = (state == null)? this._displayState: state;
			return this.model.attr('navStates').encoded[m + ':' + s];
		},
		
		setHeight: function(h) {
			var self = this;
			$.ajax({
				url: APS.PageContext.contextPath + '/ajaxapi' + APS.PageContext.pagePath,
	            data: {
	                action: 'apsresize',
	                id: this.model.attr('id'),
	                height: h
	            },
	            dataType: 'json',
	            type: 'POST',
	            success: function(result) {
	                if(result.status == 'success') {
	                   self.trigger('portlet:resize:success', self);
	                } else {
	                	self.trigger('portlet:resize:failed', self);
	                }
	            },
	            error: function(jqXhr, textStatus) {
	            	self.trigger('portlet:resize:failed', self);
	            }
			});
		},
		
		loadContent : function(mode, state, callback) {
			var m = (mode == null) ? this._displayMode:mode;
			var s = (state == null)? this._displayState: state;
			var navStates = this.getNavStates(mode,state);
			var url = APS.PageContext.contextPath + '/portlet'+ '/_ns:' + navStates + APS.PageContext.pagePath;
			var self = this;
			$.ajax({
				url : url,
				type: 'get',
				//@TODO yxq
				/*
				data: {
					portlet : this.model.attr('name'),
					entity : this.model.attr('id'),
					encoder : 'desktop'
				},
				*/ 
				data: {
					portlet : this.model.attr('name'),
					entity : this.model.attr('id'),
					mode: m,
					state: s,
					// TODO LiuXiaotian 暂时先加上该参数保证切换模式后Action请求可用
					encoder : 'desktop'
				}, 
				success: function(markup) {
					if (callback) {
						callback.call(self, markup)
					}else {
						self.trigger('portlet:load:content', markup, mode,state)
					}
				}
				
			});
		},
		
		initResizable: function() {
			var self = this;
			var portlet = this.model;
			var contentDom = $('.aps-portlet-screen', this.el);
			contentDom.resizable('destroy');
			if (APS.rootLayout.attr('editable')
					&& this.getDisplayState() == 'normal' 
					&& this.getDisplayMode()=='view') {
				contentDom.resizable({
					delay: '20',
					handles: 'n,s',
					minHeight: 16,
					stop: function(event, ui) {
						var h = $(this).height();
						self.setHeight(h);
					}
				});
			}
		},
		
		refresh: function(refreshPage) {
			var s = this._displayState;
			var m = this._displayMode;
			var el = this.el;
			var target = $('.aps-portlet-screen', this.el);
			var self = this;
			this.loadContent(m, s, function(markup){
				if (refreshPage == true) {
					window.location.reload();
				}else if (s == 'minimized') {
					this.minimize();
				}else {
					/*
					 * hack: 在ie7和ie8下，如果ajax返回的markup中含有link标签，那么直接使用html()方法
					 * 不会应用link中的样式。采用hack的办法，先hide(),然后赋值，然后show()，这样
					 * 会让浏览器重新渲染一遍dom，这样样式就可以应用上了。
					 * 
					 * 由于jquery的resizable特性，在切换后需要重新初始化resizable
					 */
					if(m != 'view' || s != 'normal') {
						target.height('auto');
					}else {
						var h = this.model.attr('height');
						if (h > 0) {
							target.height(h);
						}
					}
					target.hide().html(markup).show(0,function(){
						self.initResizable();
					});
				}
			});
		},
		
		renderControl: function() {
			var control = $('.aps-portlet-control', this.el);
			var supports = this.model.attr('supports');
			var modes = supports.modes;
			var states = supports.states;
			var self = this;
			for (var i = 0; i < this.orderedModes.length;i++) {
				var mode = this.orderedModes[i];
				if (modes[mode] != null) {
					var modeDisplayname = modes[mode];
					$('<div/>').addClass(mode)
						.attr({'name':mode, title:modeDisplayname})
						.appendTo(control)
						.click(function(event){
							var m = $(event.currentTarget).attr('name');
							self.setDisplayMode(m);
						});
				}
			}
			
			for (var i = 0; i < this.orderedStates.length; i++) {
				var state = this.orderedStates[i];
				if (states[state] != null) {
					var stateDisplayname = states[state];
					$('<div/>').addClass(state)
					.attr({'name':state, title:stateDisplayname})
					.appendTo(control)
					.click(function(event){
						var s = $(event.currentTarget).attr('name');
						self.setDisplayState(s);
					});
				}
			}
			
			//这里需要引入国际化支持，暂时先写死
			if (APS.rootLayout.attr('editable')) {
				$('<div/>').addClass('remove')
					.attr({'name':'remove',title:'删除'})
					.appendTo(control)
					.click(function(){
						self.remove();
					});
			}
		},
		
		setTitle: function(title) {
			$('.aps-portlet-title', this.el).html(title);
		},
		
		remove : function() {
			this.model.remove();
		}
	}));
		
	/* the layout model*/
	APS.Layout || (APS.Layout = APS.mvc.Model.extend({
		_portletObjs : {},
		defaults: {
			id : null,
			name: 'aps-layouts::NestedLayoutPortlet',
			mode : 'view',
			state : 'normal',
			editable: false,
			portlets: []
		},
		initialize : function() {
			this._portletObjs = {};
			for (var i = 0; i < this.attr('portlets').length; i++) {
				var config = this.attr('portlets')[i];
				var p = new APS.Portlet(config);
				this._portletObjs[config.id]  = p;
				this._bindPortletEvent(p);
			}
		},
		
		_bindPortletEvent: function(portlet) {
			portlet.bind('portlet:add:success', this._addPortletSuccess,this);
			portlet.bind('portlet:move:success', this._movePortletSuccess, this);
		},
		
		_movePortletSuccess: function(portlet, oldPosition, newPosition) {
			for (var pid in this._portletObjs) {
				var obj = this._portletObjs[pid];
				if (pid == portlet.attr('id')) continue;
				if ((obj.attr('layoutId') == oldPosition.layoutId && obj.attr('col') == oldPosition.col )
					|| (obj.attr('layoutId') == newPosition.layoutId && obj.attr('col') == newPosition.col) ) {
					obj.fetch();
				}
			}
		},
		_addPortletSuccess: function(portlet) {
			//update other portlets position in the same column
			for (var pid in this._portletObjs) {
				var obj = this._portletObjs[pid];
				if (obj.attr('id') == portlet.attr('id')) continue;
				if (obj.attr('col') == portlet.attr('col') 
						&& obj.attr('layoutId') >= portlet.attr('layoutId')) {
					obj.fetch();
				}
			}
			this._portletObjs[portlet.attr('id')] = portlet;
			//trigger event so that the layout view will take action
			this.trigger('portlet:create:success', portlet);
		},
		
	   addPortlet: function(config) {
		    var p = new APS.Portlet(config);
		    this._bindPortletEvent(p);
			p.create();
	   },
	   
	   movePortlet: function(config) {
		   var portlet = this._portletObjs[config.id];
		   delete(config.id);
		   portlet.setPosition(config);
	   },
	   
	   getPortlet: function(portletId) {
		   return this._portletObjs[portletId];
	   },
	   // @caihuiij model 原来没有删除 porteltObjs 的方法。增加一下
	   removePortlet : function (portletId){
			 delete this._portletObjs[portletId];
	   }
	}));

	//we need a global processer to handle the event
	/* the  layout view */
	APS.LayoutView || (APS.LayoutView = APS.mvc.View.extend({
		_portletViews: {},
		_sortable : null,
		_defaultSortableOptions: {
            connectWith : ".aps-portlet-container",
            opacity : 0.8,
            cursor : 'move',
            cancel: '.aps-portlet-control,.aps-portlet-title',
            handle : '.aps-portlet-header',
            tolerance: 'pointer',
            placeholder: 'sortable-placeholder',
            forcePlaceholderSize: true,
            revert: true,
            items: "> div[portletId]",
            cursorAt: {'top': 10, 'left': 10},
	        helper: function(event, ui) {
            	var sortableHelper = $('<div />');
            	var title = $(ui).find('.aps-portlet-title').text();
            	sortableHelper.text(title);
            	sortableHelper.addClass('sortable-helper');
            	sortableHelper.css({'width': '150px', 'height': '40px', 'line-height': '40px'});
            	return sortableHelper;
        	}
		},
		initialize : function(options) {
			var sortable = this.model.attr('editable');
			if (sortable) {
				this._initSortable();
			}
			//initialize portlet view
			var portlets = this.model.attr('portlets');
			for (var i = 0; i < portlets.length;i++) {
				var data = portlets[i];
				var portlet = this.model.getPortlet(data.id);
				this._initializePortletView(portlet);
			}
			this._bindEvents();
		},
		
		_bindEvents: function() {
			this.model.bind('portlet:create:success', this.loadPortletView, this);
		},
		
		loadPortletView : function(portlet) {
			// @TODO yxq
			// 此处必须加入当前页面路径APS.PageContext.pagePath,这样portlet.fetch()在获取portlet模式时才能成功
			//否则在获取portlet模式时，由于无法获得portlet的window，所以也无法获得portlet模式，最终导致新增portlet缺少装饰
            //var url = APS.PageContext.contextPath + '/portlet'+ APS.PageContext.pagePath;
			var url = APS.PageContext.contextPath + '/apsportlet' + APS.PageContext.pagePath;
			var name  = portlet.attr('name');
			var id = portlet.attr('id');
			var row = portlet.attr('row');
			var col = portlet.attr('col');
			var layoutId = portlet.attr('layoutId');
			var self = this;
			$.ajax({
				url : url,
				// @TODO yxq
				data: {
					action: 'addPortlet',
					portlet : name,
					entity : id,
					encoder : 'desktop'
				},
				/*
				data: {
					entity : id
				},
				 */
				//@TODO yxq
				success: function(markup) {
					//append the portlet content to the proper position
					var $layout = $('div[layoutId=' + layoutId + ']');
					var $column = $layout.find('.aps-portlet-container[column=' + col + ']');
					var portletEls = $('> div[portletId]', $column);
					if (row == 0) {
						$column.prepend(markup);
					}else {
						$(portletEls[row - 1]).after(markup)
					}
					//after load the window, we should fetch the portlet model and initialize the portlet view
					portlet.fetch();
					self._initializePortletView(portlet);
				}
			});
		},
		
		_isValidPortletViewConstructor: function(){
			return true;
		},
		
		_initializePortletView: function(portlet) {
				var el = $('div[portletId=' + portlet.attr('id') + ']');
				var constructor = el.attr('view');
				if (constructor == null) constructor = 'APS.PortletView';
				if (constructor && this._isValidPortletViewConstructor(constructor)) {
					var view = new (eval(constructor))({
						model: portlet,
						el : el
					});
					this._portletViews[portlet.attr('id')] = view;
					view.initResizable();
				}
				
		},
		
		getPortletView : function(portletId) {
			return this._portletViews[portletId];
		},
		
		onSortingStart: function() {
			var containers = $('.aps-portlet-container', this.el);
			containers.addClass('sortable-sorting');
		},
		
		onSortingStop: function() {
			var containers = $('.aps-portlet-container', this.el);
			containers.removeClass('sortable-sorting');
		},
		
		onDeactivate: function() {
			var containers = $('.aps-portlet-container', this.el);
			containers.removeClass('sortable-sorting');
		},
	
		_initSortable: function() {
			var self = this;
			var sortableOptions = this.options.sortableOptions || {};
			sortableOptions = $.extend(sortableOptions, this._defaultSortableOptions, {
				start: function(e, ui) {
					self.onSortingStart();
				},
				stop: function(e, ui) {
					self.onSortingStop();
					var p = $(ui.item[0]);
					var container = p.parent();
					if ($(p).attr('portletId')) {
						var targetLayoutId = $(container.parents('div[layoutId]')[0]).attr('layoutId');
						var targetLayoutColumn = container.attr('column');
						var targetLayoutRow = $(p).prevAll('div[portletId]').length;
						var portletId = p.attr('portletId');
						self.model.movePortlet({
							id : portletId,
							layoutId:targetLayoutId,
							row:targetLayoutRow,
							col:targetLayoutColumn
						});
					}else {
						var name = $(p).attr('value');
	                    var prevSiblings = $(p).prevAll('div[portletId]');
	                    var targetLayoutRow = prevSiblings.length;
	                    var targetLayoutId =  $(container.parents('div[layoutId]')[0]).attr('layoutId');
	                    var targetLayoutColumn = container.attr('column');
	                    $(p).remove();
	                    self.model.addPortlet({
	                		name : name,
	                		layoutId : targetLayoutId,
	                		row : targetLayoutRow,
	                		col: targetLayoutColumn
	                    });
					}
				},
				deactivate: function(e, ui) {
					self.onDeactivate();
				}
			});
			var containers = $('.aps-portlet-container', this.el);
			this._sortable = containers.sortable(sortableOptions);
			
		},
		
		getSortable: function() {
			return this._sortable;
		},
		
		
		removePortlet: function(portletId) {
			delete this._portletViews[portletId];
			// @ caihuiji rmeovePortelt 没有被 portelt model 调用
			// 而且，没有同步layout 中的 _porteltViews 和 _portletModel
			
			/*var view = this._portletViews[portletId];
			if (view != null) {
				view.model.remove();
			}*/
		}	
	}));
	
	APS.initialize = function(options) {
		//initialize the site navigator object
		APS.Navigator = {
			contextPath: options.contextPath,
			servletPath: options.servletPath,
			pathInfo: options.pathInfo,
			showPortletPanel: function(position, options) {
				var _options = {};
				if(undefined != options) {
					if(options.drop == undefined) {
						_options.drop = true;
					} else {
						_options.drop = options.drop;
					}
					if(options.categoryHeight != undefined) {
						_options.categoryHeight = options.categoryHeight;
					}
				} else {
					_options.drop = true;
				}
				if (APS.portletPanelView) {
					APS.portletPanelView.show(position);
				}else {
					$.get(this.contextPath + "/widget", {widget:'portletpanel', theme: APS.PageContext.theme, skin: APS.PageContext.skin, contextPath: APS.PageContext.contextPath}, function(markup){
						$(markup).appendTo(document.body).hide();
						//@TODO yxq start
						var el = $('#aps-portlet-panel');
						_options.el = el;
						APS.portletPanelView = new APS.PortletPanelView(_options);
						//@TODO yxq end
						APS.portletPanelView.show(position);
					});
				}
			},
			
			hidePortletPanel : function() {
				if(APS.portletPanelView) {
					APS.portletPanelView.hide();
				}
			},
			
			switchPageMode : function() {
				window.location.href = options.switchPageStateUrl;
			},
			
			goToAdminConsole: function() {
				window.location.href = APS.PageContext.urls.admin;
			},
			
			goTologinPage : function() {
				window.location.href = APS.PageContext.urls.loginPage;
			},
			
			logout: function() {
				var idpURL=APS.PageContext.urls.idpLogOutURL;
				if(idpURL!=null&&idpURL.length>0) {
					$.ajax({
						url: idpURL,
						type: 'get',
						dataType: 'jsonp'
		            	}).complete(function(){
		            		window.location.href = APS.PageContext.urls.logout;
		            		});
				}else{
					window.location.href = APS.PageContext.urls.logout;
				}
			},
			
			refresh : function() {
				window.location.reload();
			},
			
			showPortletAction : function() {
				$('.aps-portlet-header .aps-portlet-control').show();
			},
			
			hidePortletAction : function() {
				$('.aps-portlet-header .aps-portlet-control').hide();
			},
			
			/**
			 * 返回页面中portlet action的显示隐藏状态.
			 * true表示显示, 否则隐藏
			 */
			getPortletActionState: function() {
				var key = 'portlet-action-state';
				var show = APS.Util.getCookie(key);
				if('true' == show) {
					show == true;
				} else {
					show = false;
				}
				return show;
			},
			/**
			 * 改变页面中portlet action的显示隐藏状态.
			 */
			changePortletActionState : function() {
				var key = 'portlet-action-state';
				var show = this.getPortletActionState();
				APS.Util.setCookie(key, !show);
				if(!show) {
					this.showPortletAction();
				} else {
					this.hidePortletAction();
				}
			},
			
			themeConfig : function(data, success, error) {
				var _data = $.extend(true, {
					format:'json',
					action: 'apsthemeoperator'
				}, data);
				
				$.ajax({
					url : APS.PageContext.contextPath + '/ajaxapi' + APS.PageContext.pagePath,
					data: _data,
					dataType: 'json',
					type : 'get',
					success: function(result, textStatus, jqXHR) {
						if($.isFunction(success)) {
							success(result, textStatus, jqXHR);
						} else {
							return result;
						}
					},
					error: function(jqXHR, textStatus, errorThrown) {
						if($.isFunction(error)) {
							error(jqXHR, textStatus, errorThrown);
						} else {
							throw new Error('主题设置或信息读取出现错误');
						}
					}
				});
			}
		};

		var initializeLayout = function() {
		    APS.rootLayout = new APS.Layout(options.layoutData);
		    var el = $('div[layoutId='+APS.rootLayout.attr('id') + ']');
		    var constructor = el.attr('view');
		    if (constructor == null) constructor = 'APS.LayoutView';
		    APS.rootLayoutView = new (eval(constructor))({
		    	el: el,
		    	model: APS.rootLayout
		    });
		};
		
		initializeLayout();
	    
	};

})();