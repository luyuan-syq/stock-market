(function() {
	// 判断SYQ对象是否存在，不存在则添加一个
	if (!window.SYQ) {
		SYQ = {};
	}
	// 为SYQ对象定义app方法
	SYQ.TaskCode = function(options) {
		this.namespace = options.namespace;
		this.CONTEXT_PATH = options.CONTEXT_PATH;
		this.ENGINE_CONTEXT_PATH = options.ENGINE_CONTEXT_PATH;
		this.urls = options.urls;

		this.$taskCodeTabs = $(".taskCode-manager-tabs");
		this.taskCodeGrid = $(".taskCode-grid");
		this.grid_limit = 10;
		this.grid_height = 400;
		// dialog 使用了omDialog, dom结构会被移动
		this.$createTaskCodeDialog = $('#create-taskCode-dialog');

		// 调用初始化方法
		this.initialize();

	};

	// 扩展方法
	$
			.extend(
					SYQ.TaskCode.prototype,
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
							this._buildBtnBar();

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
						_bindSearch : function() {
							var self = this;
							 $("#search-panel").omPanel({
					             title : "高级搜索",collapsible:true
					          });
					            $("#createBeginTime").omCalendar({dateFormat : 'yy-mm-dd H:i:s',showTime:true});
					            $("#createEndTime").omCalendar({dateFormat : 'yy-mm-dd H:i:s',showTime:true});
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
							$("#searchTaskCode").val("");
							$("#createBeginTime").val("");
							$("#createEndTime").val("");
							$("#searchStatus").val("");
						},
						_searchData : function() {
							var postData = {};
							var taskCode = $("#searchTaskCode").val();
							if(taskCode != "") {
								postData.taskCode = taskCode;
							}
							var userName = $("#searchUserName").val();
							if(userName != "") {
								postData.userName = userName;
							}							
							var createBeginTime = $("#createBeginTime").val();
							if(createBeginTime != "") {
								postData.createBeginTime = createBeginTime;
							}
							var createEndTime = $("#createEndTime").val();
							if(createEndTime != "") {
								postData.createEndTime = createEndTime;
							}
							var searchStatus = $("#searchStatus").val();
							if(searchStatus != "") {
								postData.status = searchStatus;
							}
							
							postData.sortField = "create_time desc";
							
							return postData;
						},
						_buildTab : function() {
							var self = this;
							self.$taskCodeTabs.omTabs({
					                switchMode : 'click',
					                closable : false,
					                border : false,
					                onActivate : function(n,event) {
					                	if(n == 1) {
					                		self.$createTaskCodeDialog
											.omDialog('open');
					                	}
					                }
					            });

						},
						_buildDialog : function() {
							var self = this;

							var createTaskCodeDialog = this.$createTaskCodeDialog
									.omDialog({
										title : '批量创建编码',
										height : '390',
										width : '520',
										resizable : false,
										modal : true,
										autoOpen : false,
										buttons : [
												{
													text : "确定",
													click : function() {
														self._createTaskCode();
													}
												},
												{
													text : "取消",
													click : function() {
														createTaskCodeDialog
																.omDialog('close');
													}
												} ]
									});
							// editAppDialogTemp.omDialog("close");

						},
						/** ************构建表格************************************************** */
						_buildGrid : function() {
							var self = this;
							var $el = this.taskCodeGrid;
							return $el.omGrid({
								limit : this.grid_limit,
								method : 'POST',
								extraData : self._searchData(),
								// height : this.grid_height,
								singleSelect : false,
								showIndex : true,
								pageStat : '共 {total} 条数据',
								dataSource : this.urls.getTaskCodeURL,
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
										_self.taskCodeGrid.find(
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
								header : '任务编码',
								name : 'taskCode',
								width : '120',
								sort:function(rowData1,rowData2){
	                            }

							}, {
								header : '创建时间',
								name : 'createTime',
								width : '160',
								renderer : function(colValue, rowData, rowIndex) {
                                    return formattime(colValue);
                                },
                                sort:function(rowData1,rowData2){
	                            }
							}, {
								header : '创建人',
								name : 'operatorId',
								width : '100',
								sort:function(rowData1,rowData2){
	                            }
							}, {
								header : '最后操作时间',
								name : 'operatorTime',
								width : '160',
								renderer : function(colValue, rowData, rowIndex) {
									if(colValue == "" || colValue == null){
										return "";
									}
                                    return formattime(colValue);
                                },
                                sort:function(rowData1,rowData2){
	                            }
							}, {
								header : '外事人员',
								name : 'userName',
								width : '80'
							}, {
								header : '状态',
								name : 'status',
								width : '100',
								renderer : function(colValue, rowData, rowIndex) {
									if(colValue == 1){
										statusStr += '_'+rowIndex;
										return "未分配";
									}else if(colValue == 2) {
										return "在用";
									}else if(colValue == 3) {
										return "已关闭";
									}else {
										return "未知状态";
									}
                                },
                                sort:function(rowData1,rowData2){
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
									   menu += "<div class='edit' id='edit-account' type='edit' name='"
											+ rowData.id + "'>";
									
									if(statusStr.indexOf("_"+rowIndex)>=0){
									   menu += "<a href='task/addNewBeforeURL?code="+rowData.taskCode+"'>创建任务</a>";
									}else {
									   menu += "无";
									}
//									menu += "<div class='create' id='delete' type='delete' name='"
//											+ rowData.id + "'>授权</div>";
									menu += "</div></div>";
									return menu
											+ "<a class='placeholder aps-op-btn'></a>";
								}
							};
							colModels.push(actionColModel);

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
							$gridEl = this.taskCodeGrid;

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
						_createTaskCode : function(){
							var self = this;
							var codeNum = $("input[name=codeNum]").val();
                            var data = {codeNum:codeNum};
							$.ajax({
								url : self.urls.addTaskCodeURL,
								data : data,
								dataType : 'json',
								success : function(
										result) {
									if (result.success) {
										SYQ.Console
												.showTip(
														"创建编码成功!",
														'info',
														5000);
										$("input[name=codeNum]").val("");
										self.taskCodeGrid.omGrid('reload');
									} else {
										SYQ.Console
												.showTip(
														"创建编码失败！",
														'info',
														5000);
									}
								}

							});
							
							self.$createTaskCodeDialog.omDialog('close');
							self.$taskCodeTabs.omTabs('activate', 0);
							
						},
						// public method
						refresh : function() {
							this.taskCodeGrid.omGrid('reload');
						}

					});
})();