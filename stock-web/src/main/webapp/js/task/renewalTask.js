(function() {
	// 判断SYQ对象是否存在，不存在则添加一个
	if (!window.SYQ) {
		SYQ = {};
	}
	// 为SYQ对象定义app方法
	SYQ.Task = function(options) {
		this.namespace = options.namespace;
		this.CONTEXT_PATH = options.CONTEXT_PATH;
		this.ENGINE_CONTEXT_PATH = options.ENGINE_CONTEXT_PATH;
		this.urls = options.urls;

		this.$appAdd = $(".tab-app");
		this.taskGrid = $(".task-grid");
		this.grid_limit = 10;
		this.grid_height = 400;
		// dialog 使用了omDialog, dom结构会被移动
		this.$addtaskDialog = $('#create-task-dialog');
		this.$edittaskDialog = $('#edit-task-dialog');
		this.viewAppDialog = $('#view-app-dialog');

		// 调用初始化方法
		this.initialize();

		this.CREATE_APP_SUCCESS = '添加任务成功';
		this.EDIT_APP_SUCCESS = '编辑任务成功';
		this.LOOKUP_APP_INFO_FAIL = '查看任务信息失败';
	};

	// 扩展方法
	$
			.extend(
					SYQ.Task.prototype,
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
							
							this._buildTab();
							// 初始化表格上面的 ButtonBar, 并绑定事件
							// this._buildBtnBar();
							this._buildGrid();
							// this.getData();
							this._buildDialog();

							this._bindOpMenuEvent();
							
							this._bindSearch();
							this._buildclear();

						},
						_iconTip : function() {
							$("#bigIconFile").omTooltip({
								trackMouse : true,
								html : '<div>用于个人认证修改密码</div>',
								contentEL : '#iconInfoTip',
								maxWidth : 400
							});
						},
						_buildTab : function() {
							
							var self = this;
							var target =  $('#aps-task-tabs');
							target.omTabs({
								border : false,
								onActivate : function(n, event) {
									if (n == 0) {
									} else {
									}
								}
					            });

						},
						_bindSearch : function() {
							var self = this;
							 $("#search-panel").omPanel({
					             title : "高级搜索",collapsible:true
					          });
					            $("#taskBeginTime").omCalendar({dateFormat : 'yy-mm-dd H:i:s',showTime:true});
					            $("#taskEndTime").omCalendar({dateFormat : 'yy-mm-dd H:i:s',showTime:true});
					            $('span#button-search').omButton({
					          	    icons : {left : './images/search.png'},
					          	    width : 70,
						          	onClick : function(event){
						          		self._buildGrid();
						            }
					       	 	});
					            $('span#button-clear').omButton({
					          	    icons : {left : './images/search.png'},
					          	    width : 70,
						          	onClick : function(event){
						          		self._buildclear();
						            }
					       	 	});
						},
						_buildclear : function(){
							$("#taskName").val("");
							$("#taskBeginTime").val("");
							$("#taskEndTime").val("");
							$("#taskCountry").val("");
							$("#instructionNo").val("");
							$("#headerName").val("");
							$("#searchStatus").val("");
						},
						_buildDialog : function() {
							var self = this;
							var addtaskDialogTemp = this.$addtaskDialog
									.omDialog({
										title : '添加任务',
										height : '390',
										width : '520',
										resizable : false,
										modal : true,
										autoOpen : false,
										buttons : [
												{
													text : "确定",
													click : function() {
														self.$addtaskDialog
																.find(
																		'form#task-info-form')
																.submit();
													}
												},
												{
													text : "取消",
													click : function() {
														addtaskDialogTemp
																.omDialog('close');
													}
												} ]
									});
							// addAppDialogTemp.omDialog("close");

							var edittaskDialogTemp = this.$edittaskDialog
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
														self.$edittaskDialog
																.find(
																		'form#task-info-form')
																.submit();
													}
												},
												{
													text : "取消",
													click : function() {
														edittaskDialogTemp
																.omDialog('close');
													}
												} ]
									});
							// editAppDialogTemp.omDialog("close");

						},
						/** ************构建表格************************************************** */
						_buildGrid : function() {
							var self = this;
							var $el = this.taskGrid;
							return $el.omGrid({
								limit : this.grid_limit,
								// height : this.grid_height,
								extraData : self._searchData(),
								singleSelect : false,
								showIndex : true,
								pageStat : '共 {total} 条数据',
								dataSource : this.urls.getTaskURL,
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
							var taskName = $("#taskName").val();
							if(taskName != "") {
								postData.taskName = taskName;
							}
							var taskBeginTime = $("#taskBeginTime").val();
							if(taskBeginTime != "") {
								postData.taskBeginTime = taskBeginTime;
							}
							var taskEndTime = $("#taskEndTime").val();
							if(taskEndTime != "") {
								postData.taskEndTime = taskEndTime;
							}
							var taskCountry = $("#taskCountry").val();
							if(taskCountry != "") {
								postData.taskCountry = taskCountry;
							}
							var instructionNo = $("#instructionNo").val();
							if(instructionNo != "") {
								postData.instructionNo = instructionNo;
							}
							var headerName = $("#headerName").val();
							if(headerName != "") {
								postData.headerName = headerName;
							}
							var searchStatus = $("#searchStatus").val();
							if(searchStatus != "") {
								postData.status = searchStatus;
							}
							
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
//								menu.find(".edit").click(function(event) {
//									self._openCreatetaskDialog(event);
//									menu.hide();
//									event.stopPropagation();
//								});
								menu.find(".delete").click(function(event) {
									self._removeGridRows(event);
									menu.hide();
									event.stopPropagation();
								});
//								menu.find(".allocation").click(function(event) {
//									self._openAllocationtaskDialog(event);
//									menu.hide();
//									event.stopPropagation();
//								});
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
										_self.taskGrid.find(
												"div.aps-op-menu:visible")
												.hide();
									});
							
							$("body").delegate("#generateTaskCode12","click",function(event){
								_self._createTaskCode(1);
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
								header : '批件号',
								name : 'instructionNo',
								width : '100'
							},{
								header : '任务名称',
								name : 'taskName',
								width : '100'
							}, {
								header : '团长名称',
								name : 'headerName',
								width : '100'
							},{
								header : '团长身份证号',
								name : 'headerIdCard',
								width : '100'
							},{
								header : '任务编码',
								name : 'taskCode',
								width : '100'
							}, {
								header : '任务开始时间',
								name : 'taskBeginTime',
								width : '200',
								renderer : function(colValue, rowData, rowIndex) {
									if(colValue == "" || colValue == null){
										return "";
									}
                                    return formattime(colValue);
                                }
							}, {
								header : '任务结束时间',
								name : 'taskEndTime',
								width : '200',
								renderer : function(colValue, rowData, rowIndex) {
									if(colValue == "" || colValue == null){
										return "";
									}
                                    return formattime(colValue);
                                }
							}, {
								header : '操作人ID',
								name : 'operatorId',
								width : '100'
							}, {
								header : '状态',
								name : 'status',
								width : '100',
								renderer : function(colValue, rowData, rowIndex) {
									if(colValue == -1){
										return "新建";
									}else if(colValue == 1) {
									   return "办理中"
									}else if(colValue == 2) {
									   statusStr += '_'+rowIndex;
									   return "完成"
									}else if(colValue == 3) {
									   return "关闭"
									}else if(colValue == 4) {
									   statusStr += '|'+rowIndex;
									   return "护照换发函提交中"
									}else if(colValue == 5) {
									   statusStr += '['+rowIndex;
									   return "换发函审核中"
									}else if(colValue == 6) {
									   statusStr += ']'+rowIndex;
									   return "复函已发出"
									}else if(colValue == 7) {
									   statusStr += '-'+rowIndex;
									   return "信息登记中"
									}else if(colValue == 8) {
									   return "换发完成"
									}else {
										return "待换发";
									}
                                }
							} ];
							
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
									
									if(statusStr.indexOf("_"+rowIndex)>=0 ){
									menu += "<div class='edit' id='edit-account' type='edit' name='"
									     + rowData.id + "'><button id = '" + rowData.id+ "' type='button'  onclick = '_buttonOpStatus(this)' value='4'>提交换发函</button></div>";
									}else if(statusStr.indexOf("|"+rowIndex)>=0 ){
									menu += "<div class='edit' id='edit-account' type='edit' name='"
									     + rowData.id + "'><button id = '" + rowData.id+ "' type='button'  onclick = '_buttonOpStatus(this)' value='5'>函件审核中</button></div>";
									}else if(statusStr.indexOf("["+rowIndex)>=0 ){
									menu += "<div class='edit' id='edit-account' type='edit' name='"
									     + rowData.id + "'><button id = '" + rowData.id+ "' type='button'  onclick = '_buttonOpStatus(this)' value='6'>发送复函</button></div>";
									}else if(statusStr.indexOf("]"+rowIndex)>=0 ){
									menu += "<div class='edit' id='edit-account' type='edit' name='"
									     + rowData.id + "'><button id = '" + rowData.id+ "' type='button'  onclick = '_buttonOpStatus(this)' value='7'>登记</button></div>";
									}else if(statusStr.indexOf("-"+rowIndex)>=0 ){
									menu += "<div class='edit' id='edit-account' type='edit' name='"
									     + rowData.id + "'><button id = '" + rowData.id+ "' type='button'  onclick = '_buttonOpStatus(this)' value='8'>换发完成</button></div>";
									}else {
										menu += "<div class='edit' id='edit-account' type='edit' name='"
										     + rowData.id + "'>无</div>";
									}
									menu += "</div>";
									return menu
											+ "<a class='placeholder aps-op-btn'></a>";
								}
							};
							
							// 管理员模式登录
							if (globleTaskCode == null || globleTaskCode == ''){
								colModels.push(actionColModel);
							}

							return colModels;
						},
						/** *************************************************************** */
						_buildBtnBar : function() {
							var self = this;
							$('.buttonbar').omButtonbar({
								btns : [ {
									label : "导出任务列表",
									id : "add-app",
									onClick : function() {
										self._openCreatetaskDialog();
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
							var taskNamesKey = '';

							taskNamesKey = 'id';
							removeGridRowsURL = this.urls.deleteTaskURL;
							$gridEl = this.taskGrid;
							if (null == principalName) {
								dels = $gridEl.omGrid('getSelections', true);
								if (dels.length <= 0) {
									SYQ.Console.showTip('请选择要移除的任务', 'info');
									return;
								}
							}
							var self = this;
							$.omMessageBox
									.confirm({
										title : '确认删除',
										content : '确认删除任务',
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
													data[taskNamesKey] = principalNames
															.toString();
												} else {
													data[taskNamesKey] = principalName;
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
																					"删除任务成功!",
																					'info',
																					5000);
																} else {
																	// 删除失败时处理被隐藏的
																	// rows
																	$gridEl
																			.omGrid('cancleChanges');
																	SYQ.Console
																			.showTip(
																					"删除人任务失败！",
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
						_openCreatetaskDialog : function(event) {
							var self = this;
							var data = null;
							var actionType = '';
							var source = null;
							var url = this.urls.addtaskBeforeURL;
							if (null == event) {
								actionType = 'create';
							} else {
								url = this.urls.editTaskBeforeURL;
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
										self._createtaskDialog(html,
												actionType);
										// self._iconTip();
									} else {
										self._edittaskDialog(html,
												actionType);
									}
								}
							});
						},
						_openAllocationtaskDialog : function(event) {
							var self = this;
							var data = null;
							var actionType = '';
							var source = null;
							var url = this.urls.addtaskBeforeURL;
							if (null == event) {
								actionType = 'create';
							} else {
								url = this.urls.editTaskBeforeURL;
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
										self._createtaskDialog(html,
												actionType);
										// self._iconTip();
									} else {
										self._edittaskDialog(html,
												actionType);
									}
								}
							});
						},
						_edittaskDialog : function(html, actionType) {
							this.$edittaskDialog.html(html);
							var self = this;
							var edittaskDialogTemp = this.$edittaskDialog
									.omDialog('open');
							var _$form = this.$edittaskDialog
									.find('form#task-info-form');
							_$form.submit(function() {
								self._submitEdittaskInfo($(this), actionType,
										edittaskDialogTemp);
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
						_createtaskDialog : function(html, actionType) {
							this.$addtaskDialog.html(html);
							var self = this;
							var _$form = this.$addtaskDialog
									.find('form#task-info-form');
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
							var addtaskDialogTemp = this.$addtaskDialog
									.omDialog('open');
							_$form.submit(function() {
								self._submittaskInfo($(this), actionType,
										addtaskDialogTemp);
								return false;
							});
						},
						_submittaskInfo : function($form, actionType,
								addtaskDialog) {
							var self = this;
							if (!$form.valid()) {
								return false;
							}
							var data = $form.formToArray();
							var url = 'create' == actionType ? self.urls.addtaskURL
									: self.urls.updatetaskURL;
							var options = {
								iframe : false,
								url : url,
								success : function(data, status, xhr) {
									if (data.success) {
										SYQ.Console.showTip('添加任务成功', 'info',
												5000);
										self.taskGrid.omGrid('reload');
										self.$addtaskDialog
												.omDialog("close");

									} else {
										SYQ.Console.showTip('添加任务失败', 'info',
												5000);
									}
								}
							};
							$form.ajaxSubmit(options);
							addtaskDialog.omDialog('close');

							/***************************************************
							 * $.ajax({ url: url, data: data, dataType: 'json',
							 * success: function(result) {
							 * if(actionType=='create') { if(result.success) {
							 * var newApp = result.newApp; var row =
							 * SYQ.Security.Utils.convertToGridData(newApp.app);
							 * self.taskGrid.omGrid('insertRow', 'begin',
							 * row, true);//向grid中插入数据
							 * self._renderBtnInGrid(self.taskGrid, true);
							 * SYQ.Console.showTip(self.CREATE_APP_SUCCESS,
							 * 'success'); addAppDialog.omDialog('close'); }
							 * else { var msg = result.json;
							 * SYQ.Console.showTip(msg, 'error'); } } else {
							 * if(result.success) {
							 * 
							 * self.taskGrid.omGrid('reload');
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
						_submitEdittaskInfo : function($form, actionType,
								edittaskDialog) {
							var self = this;
							if (!$form.valid()) {
								return false;
							}
							var data = $form.formToArray();
							var url = self.urls.updatetaskURL;
							var options = {
								iframe : false,
								url : url,
								success : function(data, status, xhr) {
									if (data.success) {
										SYQ.Console.showTip('修改任务成功', 'info',
												5000);
										self.taskGrid.omGrid('reload');
										self.$addtaskDialog
												.omDialog("close");

									} else {
										SYQ.Console.showTip('修改任务失败', 'info',
												5000);
									}
								}
							};
							$form.ajaxSubmit(options);
							edittaskDialog.omDialog('close');

							return false;
						},
						_createTaskCode : function(codeNum){
							var self = this;
                            var data = {codeNum:codeNum};
							$.ajax({
								url : self.urls.addCodeForTaskURL,
								data : data,
								dataType : 'json',
								success : function(
										result) {
									if (result.success) {
									
										$("input[name=taskCode]").val(result.code);
									} else {
										SYQ.Console
												.showTip(
														"创建编码失败！",
														'info',
														5000);
									}
								}

							});
							
						},
						// public method
						refresh : function() {
							this.taskGrid.omGrid('reload');
						}

					});
})();

function _buttonOpStatus(obj) {
	var opStatus = obj.value;
	var taskId = obj.id;
	var postData = {
			id:taskId,
			taskStatus:opStatus
	}
	
	$.ajax({
		url : './task/operateStatus',
		type : 'POST',
		dataType : "json",
		data : postData,
		success : function(data) {
			if (data.success) {
				SYQ.Console.showTip('操作成功', 'success', 4000,new function(){
				});
				renewalTask._buildGrid();
			} else {
				SYQ.Console.showTip('操作失败', 'error', 5000);
			}
		}
	});
	return false;
 };