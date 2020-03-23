$(function() {
	var countryId = $("#countryId").val();
	var principalId = $("#principalId").val();
	var uploadVisaValue = '../newVisa/uploadVisaValue';
	var cafileId = '';
	var visaFileTemplateValue = '';
	// loadVisaTemplate($("#personId").val(), $("#taskId").val());

	// 判断SYQ对象是否存在，不存在则添加一个
	if (!window.SYQ) {
		SYQ = {};
	}
	// 为SYQ对象定义app方法
	SYQ.TextListManager = function(options) {
		this.namespace = options.namespace;
		this.CONTEXT_PATH = options.CONTEXT_PATH;
		this.ENGINE_CONTEXT_PATH = options.ENGINE_CONTEXT_PATH;
		this.urls = options.urls;

		this.fileTemplateGrid = $(".text-list-grid");
		this.$uploadVisaFileTemplateDialog = $("#upload-visaFileTemplate-dialog");
		this.uploadFtDialog = '';
		this.omFileUpload = '';
		this.grid_limit = 10;
		this.grid_height = 400;
		this.uploadFileActionData = {}; 

		// 调用初始化方法
		this.initialize();

	};

	// 扩展方法
	$
			.extend(
					SYQ.TextListManager.prototype,
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
							$("#bigIconFile").omTooltip({
								trackMouse : true,
								html : '<div>用于个人认证修改密码</div>',
								contentEL : '#iconInfoTip',
								maxWidth : 400
							});
						},
						_buildDialog : function() {

							var self = this;
							self.omFileUpload = $('#file_upload')
									.omFileUpload(
											{
												method:'POST',
												action : uploadVisaValue,
												swf : 'js/omui/swf/om-fileupload.swf',
												onComplete : function(ID,
														fileObj, response,
														data, event) {
													// 解析内容
													var result = JSON
															.parse(response);
													if (result.error) {
														alert(result.error);
														return;
													}else{
														visaFileTemplateValue = result.success;
														SYQ.Console
														.showTip(
																"上传签证资料成功!",
																'info',
																5000);
													}
													
												}
											});
							self.uploadFtDialog = this.$uploadVisaFileTemplateDialog
									.omDialog({
										title : '上传标准文件',
										height : '390',
										width : '520',
										resizable : false,
										modal : true,
										autoOpen : false,
										buttons : [
												{
													text : "提交",
													click : function() {
														var data = {};
														data.url = visaFileTemplateValue;
														var personId = $("#personId").val();
														var taskId = $("#taskId").val();
														var countryCode = $("#countryCode").val();
														if (!cafileId) {
															cafileId = 0;
														}
														data.cafileId = cafileId;
														data.flowStatus = 1;
														data.personId = personId;
														data.taskId = taskId;
														data.countryCode = countryCode;
														$.ajax({
															url : './newVisa/submitVisaInfo',
															type : 'POST',
															data : data,
															success : function(data) {
																if (data.success) {
																	SYQ.Console.showTip('操作成功', 'info', 5000);
																	window.location.reload();

																} else {
																	SYQ.Console.showTip('操作失败', 'info', 5000);
																}
															}
														});
													}
												},
												{
													text : "取消",
													click : function() {
														self.uploadFtDialog
																.omDialog('close');
													}
												} ]
									});
							

						},
						/** ************构建表格************************************************** */
						_buildGrid : function() {
							var self = this;
							var $el = this.fileTemplateGrid;
							return $el.omGrid({
								limit : 0,
								// height : this.grid_height,
								extraData : self._searchData(),
								singleSelect : true,
								showIndex : true,
								pageStat : '共 {total} 条数据',
								dataSource : this.urls.getFileTemplates,
								colModel : this._createColModel(),
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
							postData.countryId = countryId;
							postData.principalId = principalId;
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
								menu.find(".upload").click(function(event) {
									self.uploadFtDialog
									.omDialog('open');
									
									var source = $(event.currentTarget);
									if (source.is("div")) {
										source = $(event.target);
									}
									cafileId = source.attr('name');
									
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
							
						},

						/** ************************************************************************** */
						/**
						 * 构造Grid的 colModel 数据结构
						 * 
						 * @returns {Array}
						 */
						_createColModel : function() {
							var that = this;
							var colModels = [ {
								header : '文件清单',
								name : 'name',
								width : '600',
								editor : {},
								renderer : function(colValue, rowData, rowIndex) {
									if(rowData.type == 0) {
										return "&lt&lt" + colValue + "&gt&gt";
									}else{
										return  colValue ;
									}
								}
							} ];

							var actionColModel = {
								header : '操作',
								name : 'operations',
								align : 'center',
								width : '200',
								editor : {
									editable : true
								},
								renderer : function(colValue, rowData, rowIndex) {
									var menu = "<div id='op-menu-" + rowIndex
											+ "' class='aps-op-menu'>";

									if (rowData.flowStatus == 1) {
										menu += "<div class='view' id='view' type='view' name='"
												+ rowData.id + "' >待审核</div>";

									}
									if (rowData.flowStatus == 2) {
										menu += "<div class='view' id='view' type='view' name='"
												+ rowData.id + "' >审核拒绝</div>";

									}
									if (rowData.flowStatus == 2) {
										menu += "<div class='view' id='view' type='view' name='"
												+ rowData.id + "' >通过</div>";

									}
									if (rowData.type == 0) {
										if (!rowData.flowStatus) {

											menu += "<div class='edit' id='edit-account' type='edit' name='"
													+ rowData.id
													+ "'><a href='"
													+ that.CONTEXT_PATH
													+ "fileTemplate/"
													+ rowData.url
													+ "' >模板下载</a></div>";
											menu += "<div class='upload' id='add' type='add' name='"
													+ rowData.cafileId + "'>上传</div>";
										}
									}
									if (rowData.type == 1) {
										if (!rowData.flowStatus) {
											menu += "<div class='view' id='view' type='view' name='"
													+ rowData.id
													+ "' onclick='updateFlowStatus("
													+ rowData.cafileId
													+ ",1)'>完成</div>";
										}

									}

									menu += "</div>";
									return menu
											+ "<a class='placeholder aps-op-btn'></a>";
								}
							};
							colModels.push(actionColModel);

							return colModels;
						},
						// public method
						refresh : function() {
							this.fileTemplateGrid.omGrid('reload');
						},
						/** *************************************************************** */
						_buildBtnBar : function() {
							$("#country-accordion").omAccordion({
								width : '100%',
								height : 500
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
							removeGridRowsURL = this.urls.deleteFileTemplateUrl;
							$gridEl = this.fileTemplateGrid;

							if (null == principalName) {
								dels = $gridEl.omGrid('getSelections', true);
								if (dels.length <= 0) {
									SYQ.Console.showTip('请选择要移除的文件清单', 'info');
									return;
								}
							}
							var self = this;
							$.omMessageBox
									.confirm({
										title : '确认删除',
										content : '确认删除清单',
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
																					"删除清单成功!",
																					'info',
																					5000);
																} else {
																	// 删除失败时处理被隐藏的
																	// rows
																	$gridEl
																			.omGrid('cancleChanges');
																	SYQ.Console
																			.showTip(
																					"删除清单失败！",
																					'info',
																					5000);
																}
															}

														});
											}
										}
									});
						}

					});

});

function loadVisaTemplate(personId, taskId) {
	var countryApplyVisaInfo = $("div[name=countryApplyVisaInfo]");

	$.each(countryApplyVisaInfo, function(index, value, array) {
		var data = {};
		data.id = personId;
		data.code = $(value).attr("code");
		data.taskId = taskId;
		$.ajax({
			url : './newVisa/getInfo',
			type : 'GET',
			data : data,
			success : function(dataHtml) {
				$(value).html(dataHtml);
			}
		});
	});

}

function updateFlowStatus(cafileId, status) {
	var personId = $("#personId").val();
	var taskId = $("#taskId").val();
	var countryCode = $("#countryCode").val();
	var data = {};
	if (!cafileId) {
		cafileId = 0;
	}
	data.cafileId = cafileId;
	data.flowStatus = status;
	data.personId = personId;
	data.taskId = taskId;
	data.countryCode = countryCode;
	$.ajax({
		url : './newVisa/updateVisaFileStatus',
		type : 'POST',
		data : data,
		success : function(data) {
			if (data.success) {
				SYQ.Console.showTip('操作成功', 'info', 5000);
				window.location.reload();

			} else {
				SYQ.Console.showTip('操作失败', 'info', 5000);
			}
		}
	});
}