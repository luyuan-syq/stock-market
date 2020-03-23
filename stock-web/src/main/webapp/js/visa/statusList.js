(function() {
	// 判断SYQ对象是否存在，不存在则添加一个
	if (!window.SYQ) {
		SYQ = {};
	}
	// 为SYQ对象定义app方法
	SYQ.VisaStatus = function(options) {
		this.namespace = options.namespace;
		this.CONTEXT_PATH = options.CONTEXT_PATH;
		this.ENGINE_CONTEXT_PATH = options.ENGINE_CONTEXT_PATH;
		this.urls = options.urls;

		this.$appAdd = $(".tab-app");
		this.visaStatusGrid = $(".visaStatus-grid");
		this.grid_limit = 10;
		this.grid_height = 400;

		// 调用初始化方法
		this.initialize();

	};

	// 扩展方法
	$
			.extend(
					SYQ.VisaStatus.prototype,
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
							this._buildBtnBar();

							this._buildGrid();
							// this.getData();
							this._buildDialog();

							this._bindOpMenuEvent();

						},
						_iconTip : function() {
						},
						_buildDialog : function() {

						},
						/** ************构建表格************************************************** */
						_buildGrid : function() {
							var self = this;
							var $el = this.visaStatusGrid;
							return $el.omGrid({
								limit : this.grid_limit,
								// height : this.grid_height,
								singleSelect : false,
								showIndex : true,
								pageStat : '共 {total} 条数据',
								dataSource : this.urls.getVisaStatusURL,
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
						/**
						 * 
						 * @param $el
						 * @param principalType
						 * @param single
						 *            值为true, 则渲染第一行的按钮, 否则渲染全部
						 */
						_renderBtnInGrid : function($el, single) {
							
						},
						_bindOpMenuEvent : function() {
							
						},

						/** ************************************************************************** */
						/**
						 * 构造Grid的 colModel 数据结构
						 * 
						 * @returns {Array}
						 */
						_createColModel : function() {
							    var statusStr = '';
								var colModels = [ {
									header : '姓名',
									name : 'userName',
									width : '80'
								}, {
									header : '联系电话',
									name : 'contactPhone',
									width : '100'
								}, {
									header : '手机',
									name : 'mobile',
									width : '120'

								}, {
									header : '邮箱',
									name : 'contactEmail',
									width : '120'

								}, {
									header : '部门',
									name : 'deptName',
									width : '80'

								} , {
									header : '身份证号',
									name : 'idNumber',
									width : '150'

								} , {
									header : '签证状态状态',
									name : 'flowStatus',
									width : '150',
									renderer : function(colValue, rowData, rowIndex) {
										if (colValue == 0){
											return "未办理";
										} else if(colValue == 1) {
											return "待审核";
										} else if(colValue == 2) {
											statusStr += '_'+rowIndex;
											return "审核未通过";
										} else if(colValue == 3) {
											return "办理中";
										} else if(colValue == 4) {
											return "完成";
										}else {
											return "未知状态";
										}
		                            }
								} , {
									header : '签证审核信息',
									name : 'flowMsg',
									width : '140'

								}];
								
								
							return colModels;
						},
						/** *************************************************************** */
						_buildBtnBar : function() {

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
							$gridEl = this.passportGrid;

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
										self.passportGrid.omGrid('reload');
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
							 * self.passportGrid.omGrid('insertRow', 'begin',
							 * row, true);//向grid中插入数据
							 * self._renderBtnInGrid(self.passportGrid, true);
							 * SYQ.Console.showTip(self.CREATE_APP_SUCCESS,
							 * 'success'); addAppDialog.omDialog('close'); }
							 * else { var msg = result.json;
							 * SYQ.Console.showTip(msg, 'error'); } } else {
							 * if(result.success) {
							 * 
							 * self.passportGrid.omGrid('reload');
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
										self.passportGrid.omGrid('reload');
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
							this.passportGrid.omGrid('reload');
						}

					});
})();