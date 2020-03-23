SYQ.FieldCreate = function(options) {
	$.extend(this,options);
	this.init();
};

SYQ.FieldCreate.prototype = {
	el:undefined,
	isFieldExistedUrl:undefined,
	
	init:function(){
		this.buildDom();
		this.bindEvent();
	},
	
	buildDom:function(){
		var self = this;
		var form = $('form',this.el);
		form.syqValidate({
			rules:{
				fieldId:{
					required:true,
        			idLength:true,
        			idCheck:true,
        			remote:self.isFieldExistedUrl
				},
				fieldName:{
					required:true,
					idLength:true
				}
			},
			messages:{
				fieldId:{
					required:'请输入编号',
					remote:'字段已存在'
				},
				fieldName:{
					required:'请输入名称'
				}
			}
		});
		$('.default-value-create-btn',this.el).omButton({
			icons:{
				left:SYQ.PortalMananger.addImage
			}
		});
	},
	
	bindEvent:function(){
		$('form',this.el).submit(this.createField);
		$('select[name="fieldType"]',this.el).change(this.changeForm.createDelegate(this));
		$('select[name="validateRule"]',this.el).change(this.updateValidateDescription.createDelegate(this));
	},
	
	createField:function(){
		SYQ.Console.Overlay.show();
		var option = {
			dataType:'json',
			success:function(result){
				SYQ.Console.Overlay.hide();
				if(result.success){
					$('#field-create-dlg').omDialog('close');
					$('#field-content table').omGrid('reload');
					SYQ.Console.showTip('新增字段成功', 'success');
				}else{
					SYQ.Console.showTip('新增字段失败:'+result.message, 'error');
				}
			},
			error:function(){
				SYQ.Console.Overlay.hide();
				SYQ.Console.showTip('系统无响应', 'error');
			}
		};
		$(this).ajaxSubmit(option);
		return false;
	},
	
	changeForm:function(event){
		var target = $(event.target);
		var fieldType = target.val();
		if('textInput'==fieldType){
			this.loadTextInputTemplate();
		}else if('textSelect'==fieldType){
			this.loadTextSelectTemplate();
		}
	},
	
	loadTextInputTemplate:function(){
		var template = null;
		var target = null;
		template = $('.textInput-display-type-template').html();
		target = $('[template="display-type"]');
		target.html(template);
		template = $('.textInput-default-value-template').html();
		target = $('[template="default-value"]');
		target.html(template);
		template = $('.textInput-validate-rule-template').html();
		target = $('[template="validate-rule"]');
		target.html(template);
		var validateTr = target.parents('tr');
		validateTr.show();
	},
	
	loadTextSelectTemplate:function(){
		var template = null;
		var target = null;
		template = $('.textSelect-display-type-template').html();
		target = $('[template="display-type"]');
		target.html(template);
		template = $('.textSelect-default-value-template').html();
		target = $('[template="default-value"]');
		target.html(template);
		template = $('.textSelect-validate-rule-template').html();
		target = $('[template="validate-rule"]');
		var validateTr = target.parents('tr');
		validateTr.hide();
		target.html(template);
	},
	
	updateValidateDescription:function(event){
		var target = $(event.target);
		var description = $('option:selected',target).text();
		var validateDescription = $('input[name="validateDescription"]',this.el);
		validateDescription.val(description);
	}
	
};

SYQ.FieldUpdate = function(options) {
	$.extend(this,options);
	this.init();
};

SYQ.FieldUpdate.prototype = {
	el:undefined,
	
	init:function(){
		this.buildDom();
		this.bindEvent();
	},
	
	buildDom:function(){
		var form = $('#fieldUpdateForm',this.el);
		$('.default-value-create-btn',form).omButton({
			icons:{
				left:SYQ.PortalMananger.addImage
				}
		});
	    form.syqValidate({
	        rules:{
	        	fieldName:{
	        		required:true,
					idLength:true
				}
	        },
	        messages:{
	        	fieldName:{
	        		required:'请输入名称'
    			}
	        }
	    });
	},
	
	bindEvent:function(){
		$('form',this.el).submit(this.updateField);
		$('select[name="validateRule"]',this.el).change(this.updateValidateDescription.createDelegate(this));
	},
	
	updateField:function(){
		SYQ.Console.Overlay.show();
		var option = {
            dataType:'json',
            success:function(result){
            	SYQ.Console.Overlay.hide();
                if(result.success){
                    $('#field-update-dlg').omDialog('close');
                    $('#field-content table',$("#field-category")).omGrid('reload');
                    SYQ.Console.showTip('编辑字段成功', 'success');
                }else{
                	SYQ.Console.showTip('编辑字段失败:'+result.message, 'error');
                }
            },
            error:function(){
            	SYQ.Console.Overlay.hide();
            	SYQ.Console.showTip('系统无响应', 'error');
            }
        };
        $(this).ajaxSubmit(option);
        return false;
	},
	
	updateValidateDescription:function(event){
		var target = $(event.target);
		var description = $('option:selected',target).text();
		var validateDescription = $('input[name="validateDescription"]',this.el);
		validateDescription.val(description);
	}
	
};

/**
 * 添加默认值
 * 如果默认值列表中有一个输入框是空或者空字符串，那么不会添加新的默认值项
 * 本函数对dom是有要求的，'.default-value-action'与.default-value-content必须是兄弟元素
 */
function createDefaultValue(event){
	var e = event||window.event;
	var target = e.target?e.target:e.srcElement;
	var parent = $(target).parents('.default-value-action');
	var defaultValueEmpty = false;
	var defaultValueContent = parent.siblings('.default-value-content');
	var defaultValueInputSelector = 'input[name="defaultValues"]';
	$(defaultValueInputSelector,defaultValueContent).each(function(){
		var defaultValue = $(this).val();
		if(''==$.trim(defaultValue)){
			defaultValueEmpty = true;
			$(this).focus();
			return false;
		}
	});
	if(true==defaultValueEmpty){
		return;
	}else{
		var template = $('.default-value-item-template').children('.default-value-item');
		var templateDom = template.clone();
		templateDom.appendTo(defaultValueContent);
		$(defaultValueInputSelector,templateDom).focus();
	}
}

/**
 * 删除默认值
 */
function deleteDefaultValue(event){
	var e = event||window.event;
	var target = e.target?e.target:e.srcElement;
	var defaultValueItem = $(target).parent('.default-value-item');
	defaultValueItem.remove();
}

/**
 * 切换文本输入的显示类型时，如果是单行文本,默认值使用输入框，如果是多行文本，则使用textarea
 */
function changeTextInputDefaultValueDom(event) {
	var e = event||window.event;
	var target = e.target?e.target:e.srcElement;
	var displayType = $('option:selected',target).val();
	var defaultValueDomTemplate =undefined; 
	if('text'==displayType){
		defaultValueDomTemplate = $('.textInput-text-default-value-template').html();
	}else if ('textarea'==displayType) {
		defaultValueDomTemplate = $('.textInput-textarea-default-value-template').html();
	}
	var defaultValueDom = $('[template="default-value"]');
	var defaultValueInputDom = $('[name=defaultValues]',defaultValueDom);
	defaultValueInputDom.replaceWith(defaultValueDomTemplate);
}