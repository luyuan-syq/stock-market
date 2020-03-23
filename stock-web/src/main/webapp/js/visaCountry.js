var visaCountry;
$(function() {
	// 为SYQ对象定义app方法
	SYQ.VisaCountry = function(options) {
		this.namespace = options.namespace;
		this.CONTEXT_PATH = options.CONTEXT_PATH;
		this.ENGINE_CONTEXT_PATH = options.ENGINE_CONTEXT_PATH;
		this.urls = options.urls;

		// dialog 使用了omDialog, dom结构会被移动
		this.$addCountryDialog = $('#create-country-dialog');
		this.$editCountryDialog = $('#edit-country-dialog');
		this.viewAppDialog = $('#view-app-dialog');

		// 调用初始化方法
		this.initialize();
	};
	$.extend(SYQ.VisaCountry.prototype, {
		log : function(title, content) {
			if (!$.browser.msie) {
				console.log(title, content);
			} else {
				// console.log(title, content);
			}
		},
		initialize : function() {
			this._render();
		},
		_render : function() {
			this._buildTree();
			this._buildDialog();
		},
		_buildTree : function() {
			onInitGroupTree();
		},
		_buildDialog : function() {
			var self = this;
			var addAccountDialogTemp = this.$addCountryDialog.omDialog({
				title : '添加国家',
				height : '390',
				width : '520',
				resizable : false,
				modal : true,
				autoOpen : false,
				buttons : [ {
					text : "确定",
					click : function() {
						self._submitAddCountry();
					}
				}, {
					text : "取消",
					click : function() {
						addAccountDialogTemp.omDialog('close');
					}
				} ]
			});
			
			var editAccountDialogTemp = this.$editCountryDialog.omDialog({
				title : '编辑国家',
				height : '390',
				width : '520',
				resizable : false,
				modal : true,
				autoOpen : false,
				buttons : [ {
					text : "确定",
					click : function() {
						self._submitEditCountry();
					}
				}, {
					text : "取消",
					click : function() {
						editAccountDialogTemp.omDialog('close');
					}
				} ]
			});

		},
		_submitAddCountry : function() {
			var self = this;
			var url = this.urls.addCountryURL;
			var _$form = this.$addCountryDialog.find('form#country-info-form');
			var _$formValidate = _$form.syqValidate({
				rules : {
					'code' : {
						required : true,
						idCheck : true,
						idLength : true,
						remote : self.urls.countryIsNotExitURL
					},
					'name' : {
						required : true,
						maxlength : 30

					}
				},
				messages : {
					'code' : {
						required : "请输入国家编码",
						remote : "国家编号已存在"
					},
					'name' : {
						required : "请输入国家名称",
						maxlength : "最长{0}个字符"
					}
				}
			});

			_$formValidate.resetValidate();
			if (!_$form.valid()) {
				return false;
			}
			var data = _$form.formToArray();
			$.ajax({
				url : url,
				data : data,
			    type : "post",
				success : function(data) {
					if(data.success) {
						$.jstree._reference('visaCountryTree').refresh();
					}else{
						SYQ.Console.showTip('添加国家失败', 'info',
								5000);
					}
					
				},
				error : function(e) {
					SYQ.Console.showTip('添加国家失败', 'info',
							5000);
				}
			});
			this.$addCountryDialog.omDialog('close');
		},
		_openEditCountryDialog : function(id) {
			var url = this.urls.editCountryBeforeURL+id;
			var self = this;
			$.ajax({
				url : url,
				data : {},
				success : function(html) {
						self._createEditCountryDialog(html);
					
				}
			});
		},
		_createEditCountryDialog : function(html) {
			this.$editCountryDialog.html(html);
			
			var editCountryDialogTemp = this.$editCountryDialog
					.omDialog('open');
		},
		_submitEditCountry : function() {
			var self = this;
			var url = this.urls.editCountryURL;
			var _$form = this.$editCountryDialog.find('form#country-info-form');
			var _$formValidate = _$form.syqValidate({
				rules : {
					'code' : {
						required : true,
						idCheck : true,
						idLength : true,
						remote : self.urls.countryIsNotExitURL
					},
					'name' : {
						required : true,
						maxlength : 30

					}
				},
				messages : {
					'code' : {
						required : "请输入国家编码",
						remote : "国家编号已存在"
					},
					'name' : {
						required : "请输入国家名称",
						maxlength : "最长{0}个字符"
					}
				}
			});

			_$formValidate.resetValidate();
			if (!_$form.valid()) {
				return false;
			}
			var data = _$form.formToArray();
			$.ajax({
				url : url,
				data : data,
			    type : "post",
				success : function(data) {
					if(data.success) {
						$.jstree._reference('visaCountryTree').refresh();
					}else{
						SYQ.Console.showTip('添加国家失败', 'info',
								5000);
					}
					
				},
				error : function(e) {
					SYQ.Console.showTip('添加国家失败', 'info',
							5000);
				}
			});
			this.$editCountryDialog.omDialog('close');
		},
		_deleteCountry : function(id) {
			var url = this.urls.deleteCountryURL+"?id="+id
			$.ajax({
				url : url,
				data : {},
				success : function(data) {
					if(!data.success) {
						SYQ.Console.showTip('删除国家失败', 'info',
								5000);
					}
					$.jstree._reference('visaCountryTree').refresh();
					
				},
				error : function(e) {
					SYQ.Console.showTip('删除国家失败', 'info',
							5000);
				}
			});
		}
	});

	// 调用
	visaCountry = new SYQ.VisaCountry({
		namespace : '',
		ENGINE_CONTEXT_PATH : '',
		CONTEXT_PATH : '',
		urls : {
			addCountryURL : 'visa/addCountry',
			countryIsNotExitURL : 'visa/countryIsNotExit',
			deleteCountryURL : 'visa/deleteCountry',
			editCountryBeforeURL : 'visa/editCountryBefore?id=',
			editCountryURL : 'visa/editCountry'
		}
	});
})

function onInitGroupTree() {
	$.ajaxSetup({
		cache : false
	});
	$("#visaCountryTree")
			.jstree(
					{
						"plugins" : [ "json_data", "contextmenu", "types",
								"themes", "crrm", "search", "ui" ],
						core : {
							strings : {
								loading : "加载中，请稍后……",
								new_node : "输入名称"
							}
						},
						json_data : {
							"ajax" : {
								"url" : "visa/ajaxCountryTree",
								"data" : function(NODE) {
									return {
										name : "china",
									};
								}
							}
						},
						search : {
							"case_insensitive" : true,
							"ajax" : {
								"url" : "./user.htm?method=selectAllUtsNodes",
								"dataType" : "json",
								"data" : function(n) {
									return {
										selectDN : $("#fixDN").val()
									};
								}
							}
						},
						themes : {
							theme : "classic", // 设置theme主题，默认是"default"，可选值：default、apple、classic、default-rtl
							url : false, // 设置theme css文件的路径
							dots : true, // 是否显示虚线点
							icons : true
						// 是否显示节点前的图标

						},
						contextmenu : {
							select_node : true,
							show_at_node : false,
							items : function(nodeitem) {
								return {
									create : null,
									rename : null,
									remove : null,
									ccp : null,
									createApp : {
										separator_before : false,
										separator_after : true,
										label : "创建国家",
										icon : "./images/tree/create.png",
										visible : function() {
											if ($(nodeitem).attr("icon") == "root")
												return true;
											return false;
										},
										action : function(NODE) {
											visaCountry.$addCountryDialog
													.omDialog('open');
											return;
										}
									},
									motifyUtsNode : {
										separator_before : false,
										separator_after : true,
										label : "修改",
										icon : "./images/tree/rename.png",
										visible : function() {
											if ($(nodeitem).attr("icon") == "country")
												return true;
											return false;

										},
										action : function(NODE) {
											
											visaCountry._openEditCountryDialog($(NODE).attr(
															"id"));
										}
									},
									deleteUtsNode : {
										separator_before : false,
										separator_after : true,
										label : "删除",
										icon : "./images/tree/remove.png",
										visible : function() {
											if ($(nodeitem).attr("icon") == "country")
												return true;
											return false;
										},
										action : function(NODE) {
											$.jstree._reference('visaCountryTree')
													.remove(NODE);
											return true;
										}
									}
								}
							}
						}
					})
			.bind(
					"select_node.jstree",
					function(event, data) {
						if (data.rslt.obj.attr("icon") != undefined) {
							var id = $(data.rslt.obj).attr("id");
							 // $('#detail').omTabs('activate', 0);
							  $('#detail').omTabs('reload', 0 , "./account/list?id="+id);
							  alert("------");
							return;
						}
					}).bind("search.jstree", function(e, data) {
				if (data.rslt.nodes.length == 0) {
					alert("没有查询到符合条件的内容!!!");
					return;
				}
				var node = data.rslt.nodes[0];
				searchbut = true;
				$.jstree._reference('groupTree').select_node(node, true, "");
			}).bind("remove.jstree", function(even, data) {
				visaCountry._deleteCountry(data.rslt.obj.attr("id"));
				return false;
			}).bind(
					"loaded.jstree",
					function(event, data) {
						var clickNode = $("a[class='clicked jstree-clicked']");
						if (clickNode != undefined)
							$.jstree._reference('groupTree').select_node(
									clickNode.parent(), true, "");
					})
}