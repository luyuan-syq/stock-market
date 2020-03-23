$(function(){
	// 判断SYQ对象是否存在，不存在则添加一个
	if (!window.SYQ) {
		SYQ = {};
	}
	
	SYQ.ToDoFaff = function(options) {
		this.namespace = options.namespace;
		this.CONTEXT_PATH = options.CONTEXT_PATH;
		this.ENGINE_CONTEXT_PATH = options.ENGINE_CONTEXT_PATH;
		this.urls = options.urls;
		
		this.passportGrid = $(".passport-grid");
		this.visaGrid = $(".visa-grid");
		this.grid_limit = 10;
		this.grid_height = 400;
		// 调用初始化方法
		this.initialize();
	};
	
	// 扩展方法
	$
			.extend(SYQ.ToDoFaff.prototype,{
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
					this._buildPassportGrid();
					this._buildVisaGrid();
				},
				_buildPassportGrid : function() {
					var self = this;
					var $el = this.passportGrid;
					return $el.omGrid({
						limit : 0,
						dataSource : this.urls.getDaiBanPassportURL,
						colModel : this._createPassportColModel()
					});
				},
				_buildVisaGrid : function() {
					var self = this;
					var $el = this.visaGrid;
					return $el.omGrid({
						limit : 0,
						dataSource : this.urls.getDaiBanVisaURL,
						colModel : this._createVisaColModel()
					});
				},
				_searchData : function() {
					var postData = {};
					
					postData.sortField = "create_time desc";
					
					return postData;
				},

				/** ************************************************************************** */
				/**
				 * 构造Grid的 colModel 数据结构
				 * 
				 * @returns {Array}
				 */
				_createPassportColModel : function() {
					var colModels = [ {
						header : '任务类别',
						name : '',
						width : 100,
						align : 'center',
						editor : {},
						renderer : function(colValue, rowData, rowIndex) {
							if(rowData.flowStatus == 0){
								return "录入护照申请资料";
							}else if(rowData.flowStatus == 2) {
							   return "补充资料"
							}else if(rowData.flowStatus == 4) {
							   return "待归档"
							}else {
								return "无";
							}
			            }
					},{
						header : '姓名',
						name : 'userName',
						width : 60,
						align : 'center',
						editor : {}
					},{
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
					},{
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
					},{
						header : '出生地',
						name : 'placeBirth',
						width : 120,
						align : 'center',
						editor : {}
					},  {
						header : '工作单位',
						name : 'deptName',
						width : 200,
						align : 'center',
						editor : {}
					},  {
						header : '身份证号',
						name : 'idNumber',
						width : 160,
						align : 'center',
						editor : {}
					}, {
						header : '职务',
						name : 'business',
						align : 'center',
						width : 60,
						editor : {}
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
								return "待审核";
							} else if(colValue == 2) {
								return "审核拒绝";
							} else if(colValue == 3) {
								return "办理中";
							} else if(colValue == 4) {
								return "已借出";
							}else if(colValue == 5) {
								return "已归档";
							}else {
								return "未知状态";
							}
			            }
					} , {
						header : '护照审核信息',
						name : 'flowMsg',
						width : '120'

					},{
						header : '操作',
						name : 'operations',
						align : 'center',
						width : 100,
						renderer : function(colValue, rowData, rowIndex) {
							var personId = rowData.id;
							var menu = "<div id='op-menu-" + rowIndex + ">";
							
							menu += "<div class='luru' id='luru-passport' name='" + rowData.id + "'>";
							if (rowData.flowStatus == 0){
							    menu += "<a href='passport/applyPassportInfo?personId="+personId+"'>录入相关资料</a>";
							} else if(rowData.flowStatus == 2){
								menu += "<a href='passport/applyPassportInfo?personId="+personId+"'>补充资料</a>";
							}else {
								menu += "<span>资料已录入</span>"
							}
							
							menu += "</div></div>";
							return menu;
						}
					} ];
					return colModels;
				},/** ************************************************************************** */
				/**
				 * 构造Grid的 colModel 数据结构
				 * 
				 * @returns {Array}
				 */
				_createVisaColModel : function() {
					var colModels = [{
						header : '任务类别',
						name : '',
						width : 100,
						align : 'center',
						editor : {},
						renderer : function(colValue, rowData, rowIndex) {
							
							if(rowData.flowStatus == 0){
								return "录入签证资料";
							}else if(rowData.flowStatus == 2) {
							   return "补充资料"
							}else {
								return "无";
							}
						}
					},  {
						header : '账户姓名',
						name : 'userName',
						width : 100,
						align : 'center',
						editor : {}
					}, {
						header : '出差国家',
						name : 'countryName',
						width : 100,
						align : 'center',
						editor : {}
					}, {
						header : '工作单位',
						name : 'deptName',
						width : 200,
						align : 'left',
						editor : {}
					},  {
						header : '身份证号',
						name : 'idNumber',
						width : 200,
						align : 'left',
						editor : {}
					}, {
						header : '单位电话',
						name : 'mobile',
						align : 'left',
						width : 100,
						editor : {}
					}, {
						header : '手机',
						name : 'phone',
						align : 'left',
						width : 100,
						editor : {}
					}, {
						header : '邮箱地址',
						name : 'email',
						align : 'left',
						width : 100,
						editor : {}
					},{
						header : '操作',
						name : 'operations',
						align : 'center',
						width : 200,
						renderer : function(colValue, rowData, rowIndex) {
							var aName = "无";
							if(rowData.flowStatus == 0){
								aName = "提交资料";
							}else if(rowData.flowStatus == 2) {
								aName =  "补充资料"
							}
							
							var menu = "<div id='op-menu-" + rowIndex
									+ ">";
							menu += "<div class='luru' id='luru-passport' name='"
									+ rowData.id + "'><a href='newVisa/applyVisaInfo?visaPrincipalId="+rowData.visaPrincipalId+"'>"+aName+
									"</a></div>";
							menu += "</div>";
							return menu;
						}
					} ];
					return colModels;
				}
			})
})
