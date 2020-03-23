(function() {
	// 判断SYQ对象是否存在，不存在则添加一个
	if (!window.SYQ) {
		SYQ = {};
	}
	// 为SYQ对象定义app方法
	SYQ.Account = function(options) {
		this.namespace = options.namespace;
		this.CONTEXT_PATH = options.CONTEXT_PATH;
		this.ENGINE_CONTEXT_PATH = options.ENGINE_CONTEXT_PATH;
		this.urls = options.urls;

		this.$appAdd = $(".tab-app");
		this.accountGrid = $(".account-grid");
		this.grid_limit = 10;
		this.grid_height = 400;
		// dialog 使用了omDialog, dom结构会被移动
		this.viewAppDialog = $('#view-app-dialog');

		// 调用初始化方法
		this.initialize();

		this.CREATE_APP_SUCCESS = '添加外事人员账号成功';
		this.EDIT_APP_SUCCESS = '编辑应用成功';
		this.LOOKUP_APP_INFO_FAIL = '查看应用信息失败';
	};

	// 扩展方法
	$
			.extend(
					SYQ.Account.prototype,
					{
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
							// 初始化表格上面的 ButtonBar, 并绑定事件
							//this._buildBtnBar();

							this._buildGrid();
							// this.getData();
							// this._buildDialog();

							this._bindOpMenuEvent();

						},
						_iconTip : function() {
							$("#bigIconFile").omTooltip({
								trackMouse : true,
								html : '<div>用于个人认证修改密码</div>',
								contentEL : '#iconInfoTip',
								maxWidth : 400
							});
						},
						_buildDialog : function() {
							var self = this;
							var addAccountDialogTemp = this.$addAccountDialog
									.omDialog({
										title : '添加外事人员',
										height : '390',
										width : '520',
										resizable : false,
										modal : true,
										autoOpen : false,
										buttons : [
												{
													text : "确定",
													click : function() {
														self.$addAccountDialog
																.find(
																		'form#account-info-form')
																.submit();
													}
												},
												{
													text : "取消",
													click : function() {
														addAccountDialogTemp
																.omDialog('close');
													}
												} ]
									});
							var editAccountDialogTemp = this.$editAccountDialog
									.omDialog({
										title : '编辑应用',
										height : '390',
										width : '520',
										resizable : false,
										modal : true,
										autoOpen : false,
										buttons : [
												{
													text : "确定",
													click : function() {
														self.$editAccountDialog
																.find(
																		'form#account-info-form')
																.submit();
													}
												},
												{
													text : "取消",
													click : function() {
														editAccountDialogTemp
																.omDialog('close');
													}
												} ]
									});
							// editAppDialogTemp.omDialog("close");

						},
						/** ************构建表格************************************************** */
						_buildGrid : function() {
							var self = this;
							var $el = this.accountGrid;
							return $el.omGrid({
								limit : this.grid_limit,
								// height : this.grid_height,
								extraData : self._searchData(),
								singleSelect : false,
								showIndex : true,
								pageStat : '共 {total} 条数据',
								dataSource : this.urls.getAccountURL,
								colModel : this._createColModel(),
								preProcess : function(data) {
									return SYQ.Security.Utils
											.convertToGridData(data);
								},
								onBeforeEdit : function(rowIndex, rowData) {
									return false;
								},
								onRefresh : function(nowPage, pageRecords,
										event) {
									self._renderBtnInGrid($el, false);
								}
							});
						},
						_searchData : function() {
							var postData = {};
							
							postData.sortField = "create_time desc";
							
							return postData;
						},
						/**
						 * 
						 * @param $el
						 * @param principalType
						 * @param single
						 *            值为true, 则渲染第一行的按钮, 否则渲染全部
						 */
						_renderBtnInGrid : function($el, single) {
							var self = this;
							var btnOptions = {
								label : '操作',
								width : 75,
								onClick : function(event) {
									var v = $el.find("div.aps-op-menu:visible")
											.hide();
									var dropDownMenu = $(event.currentTarget.previousSibling);
									/*
									 * dropDownMenu.mouseleave(function(event){
									 * if( $(this).is(":visible")){ var menu =
									 * $(this); setTimeout(function(){
									 * menu.fadeOut(200); }, 1000); } });
									 */
									// 重新计算菜单的位置
									var left = $(this).offset().left
											- $el.offset().left - 100 + "px";
									if (dropDownMenu.is(":hidden")) {
										dropDownMenu.fadeIn(200).css({
											left : left
										});
									} else {
										dropDownMenu.fadeOut(200);
									}
									v.hide();
									event.stopPropagation();
									return false;
								},
								icons : {
									left : "css/images/16/op-btn-icon.png"
								}
							};

							var bindBtnEvent = function() {
								var menu = $(this);
								menu.find(".edit").click(function(event) {
									self._openCreateAccountDialog(event);
									menu.hide();
									event.stopPropagation();
								});
								menu.find(".delete").click(function(event) {
									self._removeGridRows(event);
									menu.hide();
									event.stopPropagation();
								});
							};

							if (single) {
								$($el.find(".placeholder")[0]).omButton(
										btnOptions);
								$($el.find("div.aps-op-menu")[0]).each(
										bindBtnEvent);
							} else {
								$el.find(".placeholder").omButton(btnOptions);
								$el.find("div.aps-op-menu").each(bindBtnEvent);
							}
						},
						_bindOpMenuEvent : function() {
							var _self = this;
							$("body").live(
									'click',
									function() {
										_self.accountGrid.find(
												"div.aps-op-menu:visible")
												.hide();
									});
						},

						/** ************************************************************************** */
						/**
						 * 构造Grid的 colModel 数据结构
						 * 
						 * @returns {Array}
						 */
						_createColModel : function() {
							var statusStr='';
							var colModels = [ {
								header : '出差人员名称',
								name : 'userName',
								align : 'center',
								width : '100'
							}, {
								header : '性别',
								name : 'sexey',
								width : 30,
								align : 'center',
								renderer : function(colValue, rowData, rowIndex) {
									if(colValue == 0){
										return "男";
									}else if(colValue == 1) {
									   return "女"
									}else {
										return "未知性别";
									}
					            }
							}, {
								header : '出生日期',
								name : 'birthday',
								width : 120,
								align : 'center',
								renderer : function(colValue, rowData, rowIndex) {
									if(colValue == "" || colValue == null){
										return "";
									}
					                return formattime(colValue);
					            }
							}, {
								header : '出生地',
								name : 'placeBirth',
								width : 120,
								align : 'center',
							}, {
								header : '工作单位',
								name : 'deptName',
								width : 200,
								align : 'center',
							} , {
								header : '身份证号',
								name : 'idNumber',
								width : 160,
								align : 'center',
							} , {
								header : '职务',
								name : 'business',
								align : 'center',
								width : 60,
							}, {
								header : '对外身份',
								name : 'identity',
								align : 'left',
								width : 60,
								renderer : function(colValue, rowData, rowIndex) {
									if(colValue == 0){
										return "组员";
									}else if(colValue == 1) {
									   return "组长"
									}else if(colValue == 2) {
									   return "团员"
									}else if(colValue == 3) {
									   return "团长"
									}else {
										return "未知身份";
									}
					            }
							}, {
								header : '是否有集团领导班子成员',
								name : 'isLeader',
								align : 'left',
								width : 140,
								renderer : function(colValue, rowData, rowIndex) {
									if(colValue == 0){
										return "否";
									}else if(colValue == 1) {
									   return "是"
									}else {
										return "未知";
									}
					            }
							}, {
								header : '护照审核状态',
								name : 'flowStatus',
								width : '80',
								renderer : function(colValue, rowData, rowIndex) {
									if (colValue == 0){
										return "新建";
									} else if(colValue == 1) {
										statusStr += '_'+rowIndex
										return "待审核";
									} else if(colValue == 2) {
										statusStr += '|'+rowIndex
										return "审核拒绝";
									} else if(colValue == 3) {
										statusStr += '|<'+rowIndex
										return "办理中";
									} else if(colValue == 4) {
										statusStr += '|'+rowIndex
										return "已借出";
									}else if(colValue == 5) {
										statusStr += '|'+rowIndex
										return "已归档";
									}else {
										return "未知状态";
									}
	                            }
							} , {
								header : '护照审核信息',
								name : 'flowMsg',
								width : '120'

							}];

							var actionColModel = {
								header : '操作',
								name : 'operations',
								align : 'center',
								width : '120',
								editor : {
									editable : true
								},
								renderer : function(colValue, rowData, rowIndex) {
										var menu = "<div id='op-menu-" + rowIndex
										         + "' class='aps-op-menu'>";
										if (statusStr.indexOf("_"+rowIndex)>=0){
								            menu += "<div class='edit' id='edit-account' type='edit' name='"
									             + rowData.id + "'><a href='passport/auditPassportInfo?personId="+rowData.id+"&dealFlag=1'>审核</a></div>";
								            menu += "</div>";
										} else if (statusStr.indexOf("|"+rowIndex)>=0){
								            menu += "<div class='view' id='edit-account' type='edit' name='"
									             + rowData.id + "'><a href='passport/auditPassportInfo?personId="+rowData.id+"&dealFlag=2'>详情</a></div>";
								            menu += "</div>";	
										} else if (statusStr.indexOf("<"+rowIndex)>=0){
								            menu += "<div class='view' id='edit-account' type='edit' name='"
									             + rowData.id + "'><a href='passport/addBefore?idNumber="+rowData.idNumber+"'>添加</a></div>";
								            menu += "</div>";	
										}else {
								            menu += "<div class='view' id='edit-account' type='edit' name='"
									             + rowData.id + "'><a>无</a></div>";
								            menu += "</div>";
										}
								        return menu + "<a class='placeholder aps-op-btn'></a>";	
									
								}
							};
							colModels.push(actionColModel);

							return colModels;
						},
						/** *************************************************************** */
						_buildBtnBar : function() {
							var self = this;
							$('.buttonbar').omButtonbar({
								btns : [ {
									label : "添加",
									id : "add-app",
									onClick : function() {
										self._openCreateAccountDialog();
									},
									icons : {
										left : 'css/images/16/add.png'
									}
								}, {
									separtor : true
								} ]
							});
							$('.buttonbar').omButtonbar({
								btns : [ {
									label : "删除",
									id : "delete-app",
									onClick : function() {
										self._removeGridRows();
									},
									icons : {
										left : 'css/images/16/delete.png'
									}
								}, {
									separtor : true
								} ]
							});

						},

						/** ********************* */
						/**
						 * 删除Grid中的行
						 * 
						 * @param event
						 *            jQuery对象, 当点击行内操作按钮时需要从event获取数据
						 */
						_removeGridRows : function(event) {
							/*
							 * 当点击行内操作按钮时, 从event获取要删除项的唯一标识
							 */
							var source = null;
							var principalName = null;
							var dels = null;
							if (event != null && event instanceof jQuery.Event) {
								source = $(event.currentTarget);
								if (source.is("span")) {
									source = $(event.target);
								}
								principalName = $(source).attr('name');
							}

							var removeGridRowsURL = '';
							// 后台获取数据principalNames的 Key
							var accountNamesKey = '';

							accountNamesKey = 'id';
							removeGridRowsURL = this.urls.deleteAppURL;
							$gridEl = this.accountGrid;

							if (null == principalName) {
								dels = $gridEl.omGrid('getSelections', true);
								if (dels.length <= 0) {
									SYQ.Console.showTip('请选择要移除的账号', 'info');
									return;
								}
							}
							var self = this;
							$.omMessageBox
									.confirm({
										title : '确认删除',
										content : '确认删除账号',
										onClose : function(success) {
											if (success) {
												var data = {};
												if (null == principalName) {
													/***************************
													 * for(var i = dels.length -
													 * 1; i >= 0; i-- ) {
													 * $gridEl.omGrid('deleteRow',dels[i]); }
													 * var rows =
													 * $gridEl.omGrid('getChanges',
													 * 'delete');
													 **************************/
													var principalNames = new Array(
															dels.length);
													for (var i = dels.length - 1; i >= 0; i--) {
														principalNames[i] = dels[i]['id'];
													}
													data[accountNamesKey] = principalNames
															.toString();
												} else {
													data[accountNamesKey] = principalName;
												}

												$
														.ajax({
															url : removeGridRowsURL,
															data : data,
															dataType : 'json',
															success : function(
																	result) {
																if (result.success) {
																	$gridEl
																			.omGrid('reload');
																	SYQ.Console
																			.showTip(
																					"删除账号成功!",
																					'info',
																					5000);
																} else {
																	// 删除失败时处理被隐藏的
																	// rows
																	$gridEl
																			.omGrid('cancleChanges');
																	SYQ.Console
																			.showTip(
																					"删除账号失败！",
																					'info',
																					5000);
																}
															}

														});
											}
										}
									});
						},
						/** ********************* */
						/**
						 * 添加应用, 修改应用对话框
						 * 
						 * @param event
						 *            null表示create, 否则表示edit, 需要的数据从event获取
						 */
						_openCreateAccountDialog : function(event) {
							var self = this;
							var data = null;
							var actionType = '';
							var source = null;
							var url = this.urls.addAccountBeforeURL;
							if (null == event) {
								actionType = 'create';
							} else {
								url = this.urls.editAccountBeforeURL;
								actionType = 'edit';
								source = $(event.currentTarget);
								if (source.is("span")) {
									source = $(event.target);
								}
								data = {
									id : $(source).attr('name')
								};
							}
							$.ajax({
								url : url,
								data : data,
								success : function(html) {
									if ('create' == actionType) {
										self._createAccountDialog(html,
												actionType);
										// self._iconTip();
									} else {
										self._editAccountDialog(html,
												actionType);
									}
								}
							});
						},
						_editAccountDialog : function(html, actionType) {
							this.$editAccountDialog.html(html);
							var self = this;
							var editAccountDialogTemp = this.$editAccountDialog
									.omDialog('open');
							var _$form = this.$editAccountDialog
									.find('form#account-info-form');
							_$form.submit(function() {
								self._submitEditAccountInfo($(this), actionType,
										editAccountDialogTemp);
								return false;
							});
							var $formValidate = _$form.syqValidate({
								rules : {
									'application.appName' : {
										required : true,
										/* idLength :true */
										maxlength : 6
									},
									'application.appLoginURL' : {
										required : true,
										maxlength : 100
									},
									'application.description' : {
										maxlength : 100
									}
								},
								messages : {
									'application.appName' : {
										required : "请输入应用名称",
										maxlength : "最长{0}个字符"
									},
									'application.appLoginURL' : {
										required : "请输入应用URL",
										maxlength : "最长{0}个字符"
									},
									'application.description' : {
										maxlength : "最长{0}个字符"
									}
								}
							});
						},
						_createAccountDialog : function(html, actionType) {
							this.$addAccountDialog.html(html);
							var self = this;
							var _$form = this.$addAccountDialog
									.find('form#account-info-form');
							var _$formValidate = _$form.syqValidate({
								rules : {
									'application.appId' : {
										required : true,
										idCheck : true,
										idLength : true,
										remote : self.urls.appIsExitURL
									},
									'application.appName' : {
										required : true,
										maxlength : 6

									},
									'application.appLoginURL' : {
										required : true,
										maxlength : 100

									},
									'application.description' : {
										maxlength : 100
									}
								},
								messages : {
									'application.appId' : {
										required : "请输入应用编号",
										remote : "应用编号已存在"
									},
									'application.appName' : {
										required : "请输入应用名称",
										maxlength : "最长{0}个字符"
									},
									'application.appLoginURL' : {
										required : "请输入应用URL",
										maxlength : "最长{0}个字符"
									},
									'application.description' : {
										maxlength : "最长{0}个字符"
									}
								}
							});

							_$formValidate.resetValidate();
							var addAccountDialogTemp = this.$addAccountDialog
									.omDialog('open');
							_$form.submit(function() {
								self._submitAccountInfo($(this), actionType,
										addAccountDialogTemp);
								return false;
							});
						},
						_submitAccountInfo : function($form, actionType,
								addAccountDialog) {
							var self = this;
							if (!$form.valid()) {
								return false;
							}
							var data = $form.formToArray();
							var url = 'create' == actionType ? self.urls.addAccountURL
									: self.urls.updateAccountURL;
							var options = {
								iframe : false,
								url : url,
								success : function(data, status, xhr) {
									if (data.success) {
										SYQ.Console.showTip('添加外事账号成功', 'info',
												5000);
										self.accountGrid.omGrid('reload');
										self.$addAccountDialog
												.omDialog("close");

									} else {
										SYQ.Console.showTip('添加外事账号失败', 'info',
												5000);
									}
								}
							};
							$form.ajaxSubmit(options);
							addAccountDialog.omDialog('close');

							/***************************************************
							 * $.ajax({ url: url, data: data, dataType: 'json',
							 * success: function(result) {
							 * if(actionType=='create') { if(result.success) {
							 * var newApp = result.newApp; var row =
							 * SYQ.Security.Utils.convertToGridData(newApp.app);
							 * self.accountGrid.omGrid('insertRow', 'begin',
							 * row, true);//向grid中插入数据
							 * self._renderBtnInGrid(self.accountGrid, true);
							 * SYQ.Console.showTip(self.CREATE_APP_SUCCESS,
							 * 'success'); addAppDialog.omDialog('close'); }
							 * else { var msg = result.json;
							 * SYQ.Console.showTip(msg, 'error'); } } else {
							 * if(result.success) {
							 * 
							 * self.accountGrid.omGrid('reload');
							 * SYQ.Console.showTip(self.EDIT_APP_SUCCESS,
							 * 'success'); addAppDialog.omDialog('close'); }
							 * else { var msg = result.json;
							 * SYQ.Console.showTip(msg, 'error'); } }
							 *  }
							 * 
							 * });
							 **************************************************/
							return false;
						},
						_submitEditAccountInfo : function($form, actionType,
								editAccountDialog) {
							var self = this;
							if (!$form.valid()) {
								return false;
							}
							var data = $form.formToArray();
							var url = self.urls.updateAccountURL;
							var options = {
								iframe : false,
								url : url,
								success : function(data, status, xhr) {
									if (data.success) {
										SYQ.Console.showTip('修改外事账号成功', 'info',
												5000);
										self.accountGrid.omGrid('reload');
										self.$addAccountDialog
												.omDialog("close");

									} else {
										SYQ.Console.showTip('修改外事账号失败', 'info',
												5000);
									}
								}
							};
							$form.ajaxSubmit(options);
							editAccountDialog.omDialog('close');

							return false;
						},
						// public method
						refresh : function() {
							this.accountGrid.omGrid('reload');
						}

					});
})();