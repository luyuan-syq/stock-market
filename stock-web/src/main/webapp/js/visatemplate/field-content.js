SYQ.FieldContent = function(options) {
	$.extend(this,options);
	this.init();
};

SYQ.FieldContent.prototype = {
	el:undefined,
	getCategoryFieldUrl:undefined,
	deleteFieldsUrl:undefined,
	getFieldCreatePageUrl:undefined,
	getFieldUpdatePageUrl:undefined,
	
	init:function(){
		this.buildDom();
		this.bindEvent();
	},
	
	buildDom:function(){
		$('.field-content-action',this.el).omButtonbar({
			btns:[
			     {
			    	 label:'新增',
			    	 id:'field-create-btn',
			    	 icons:{
			    			left:""
			    			},
			    	 onClick:this.showFieldCreateDlg.createDelegate(this)
			     },
			     {
			    	 label:'删除',
			    	 id:'field-delete-btn',
			    	 icons:{
			    			left:""
			    			},
			    	 onClick:this.showFieldsDeleteDlg.createDelegate(this)
			     }
			     ]
		});
		var fieldTable = $('#field-content table',this.el);
		fieldTable.omGrid({
	        limit : 0,
	        singleSelect:false,
	        colModel : [    
	                        {header:'编号',name:'id',width:150},
	                        {header:'名称',name:'name',width:'autoExpand'},
	                        {header:'文本类型',name:'type',renderer:this.renderTextType,width:100},
	                        {header:'显示类型',name:'displayType',renderer:this.renderDisplayType,width:100},
	                        {header:'必填',name:'required',renderer:this.renderYesOrNo,align:'center'},
	                        {header:'操作',name:'action',align:'center',renderer:this.renderFieldAction,width:120}
	        ],
	        onRefresh: this.bindFieldActionEvent.createDelegate(this),
	        dataSource : this.getCategoryFieldUrl
	    });
	},
	
	bindEvent:function(){
		$('form',this.el).apsValidate({
        	rules:{
		        categoryName:{
		        	maxlength:30
		        }
        	},
        	messages:{
        		categoryName:{
        			maxlength: '最多30个字符'
        		}
        	}
    	});
		$('form',this.el).ajaxForm({
			dataType:'json',
			success:function(result){
				if('success'==result.status){
					SYQ.Console.showTip('编辑字段类别成功', 'success');
					$('#user-config label[for="field-custom-menu"]').click();
				}else{
					SYQ.Console.showTip('编辑字段类别失败:'+result.message, 'error');
				}
			}
		});
	},
	
	renderTextType:function (value , rowData , rowIndex){
		var text;
		if('textInput'==value){
			text = '文本输入';
		}else if('textSelect'==value){
			text = '文本选择';
		}
		return text;
	},
	
	renderDisplayType:function (value , rowData , rowIndex){
		var text;
		if('text'==value){
			text='单行文本';
		}else if('textarea'==value){
			text='多行文本';
		}else if('radio'==value){
			text='单选项';
		}else if('checkbox'==value){
			text='多选项';
		}else if('select'==value){
			text='选择列表';
		}
		return text;
	},
	
	renderYesOrNo:function (value , rowData , rowIndex){
		var text = '';
		if(true==value){
			text = '是';
		}else{
			text = '否';	
		}
		return text;
	},
	
	renderValidator:function (value , rowData , rowIndex){
		var text='';
		var description = value.description;
		var validateRule = value.value;
		if(null!=description && ''!=$.trim(description)){
			text = description;
		}else if(null!=validateRule && ''!=$.trim(validateRule)){
			text = validateRule;
		}
		return text;
	},
	
	renderDefaultValue:function (value , rowData , rowIndex){
		var strArray = [];
		for(var i = 0;i < value.length;i++){
			var defaultValue = value[i];
			strArray.push(defaultValue.value);
		}
		var text = strArray.join();
		return text;
	},
	
	renderFieldAction:function (value , rowData , rowIndex){
		var fieldId = rowData.id;
		var editButton = '<button type="button" class="field-update-btn" fieldId={1}>{0}</button>'.format('编辑',fieldId);
		return editButton;
	},
	
	bindFieldActionEvent:function(){
		var fieldTable = $('#field-content table',this.el);
		var actionButton = $('button.field-update-btn',fieldTable); 
		actionButton.omButton({
			icons:{left:""}
		});
		actionButton.click(this.showFieldUpdateDlg.createDelegate(this));
		$('.aps-portal-manager').css('visibility','visible');
	},
	
	showFieldsDeleteDlg:function(){
		var fields = $('#field-content table',this.el).omGrid('getSelections',true);
		var length = fields.length;
		if(0==length){
			return;
		}
		var fieldIds = [];
		for(var i=0;i<length;i++){
			var id =fields[i].id;
			fieldIds.push(id);
		}
		var self = this;
		$.omMessageBox.confirm({
            content:'确定删除该字段吗?',
        	onClose:function(result){
        		if(result){
        			self.deleteFields(fieldIds);
        		}
        	}
        });
		
	},
	
	deleteFields:function(fieldIds){
		var option={
				url:this.deleteFieldsUrl,
				data:{
					fieldIds:JSON.stringify(fieldIds)
				},
				dataType:'json',
				success:function(result){
					if('success'==result.status){
						$('#field-delete-dlg').omDialog('close');
						$('#field-content table',this.el).omGrid('reload');
						SYQ.Console.showTip('删除字段成功', 'success');
					}else{
						SYQ.Console.showTip('删除字段失败:'+result.message, 'error');
					}
				},
				error:function(){
					SYQ.Console.showTip('系统无响应', 'error');
				}
			};
		$.ajax(option);
	},
	
	showFieldUpdateDlg:function(event){
		event.stopPropagation();
		var target = $(event.target);
		var fieldId = target.attr('fieldId');
		var dlg = $('#field-update-dlg');
		var self = this;
		var option = {
				url:self.getFieldUpdatePageUrl,
				data:{
					fieldId:fieldId
				},
				success:function(dom){
					dlg.html(dom);
				}
		};
		var defer = $.ajax(option);
		defer.done(function(){
			dlg.omDialog({
				modal:true,
				title:'编辑字段',
				width:500,
				buttons:[
				         {text:'确定',click:function(){
				        	 var form = $('form',dlg);
				        	 if(form.valid()){
				        		 form.submit();
				        	 }
				         }},
				         {text:'取消',click:function(){
				        	 dlg.omDialog('close');
				         }}
				         ]
			});
		});
	},
	
	showFieldCreateDlg:function(event){
		var self = this;
		var dlg = $('#field-create-dlg');
		var option = {
				url:self.getFieldCreatePageUrl,
				success:function(dom){
					dlg.html(dom);
				}
		};
		var defer = $.ajax(option);
		defer.done(function(){
			dlg.omDialog({
				modal:true,
				title:'新增字段',
				width:500,
				buttons:[
				         {text:'确定',click:function(){
				        	 var form = $('form',dlg);
				        	 if(form.valid()){
				        		 form.submit();
				        	 }
				         }},
				         {text:'取消',click:function(){
				        	 dlg.omDialog('close');
				         }}
				         ]
			});
		});
	}
	
};

