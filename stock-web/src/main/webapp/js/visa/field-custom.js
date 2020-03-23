
SYQ.FieldCustom = function(options) {
	$.extend(this,options);
	this.init();
};

SYQ.FieldCustom.prototype = {
	el:undefined,
	getFieldPageUrl:undefined,
	updateCategoryUrl:undefined,
	deleteCategoryUrl:undefined,
	isCategoryExistedUrl:undefined,
	validator:undefined,
	
	init:function(){
		this.buildDom();
		this.bindEvent();
	},
	
	buildDom:function(){
		var self = this;
        $('.category-create-btn',this.el).omButton({
        	width:156,
        	icons:{
        		left:""
        	}
        });
        this.validator = $('#category-create-dlg form').syqValidate({
        	rules:{
        		categoryId:{
        			required:true,
        			idLength:true,
        			idCheck:true,
        			remote:self.isCategoryExistedUrl
        		},
		        categoryName:{
		        	required:true,
		        	idLength:true
		        }
        	},
        	messages:{
        		categoryId:{
        			required:'请输入编号',
    				remote:'类别已存在'
        		},
        		categoryName:{
        			required:'请输入名称'
        		}
        	}
	    });
        $('.aps-portal-manager').show();
	},
	
	bindEvent:function(){
		$('.category-title',this.el).click(this.renderFieldPage.createDelegate(this));
		$('.category-title',this.el).dblclick(this.showCategoryUpdateDiv.createDelegate(this));
		$('.category-title-update input',this.el).blur(this.updateCategory.createDelegate(this));
		$('.category-title-update input',this.el).keyup(this.updateCategoryByEnter.createDelegate(this));
		$('.category-create-btn',this.el).click(this.showCreateCategoryDlg.createDelegate(this));
		$('.category-delete-btn',this.el).click(this.showDeleteCategoryDlg.createDelegate(this));
		$('#aps-console-field-view-switch div',this.el).click(this.toggleFieldLeft.createDelegate(this));
	},
	
	renderFieldPage:function(event){
		var selected = $('.field-category.selected',this.el);
		selected.removeClass('selected');
		var target = $(event.target);
		var fieldCategory = target.parents('.field-category');
		fieldCategory.addClass('selected');
		var categoryId = fieldCategory.attr('categoryId');
		$.ajax({
			url:this.getFieldPageUrl,
			data:{
				categoryId:categoryId,
				code:countryCode
			},
			success:function(result){
				$('.field-custom-right').empty();
				$('.field-custom-right').html(result);
			}
		});
	},
	
	showCreateCategoryDlg:function(){
		var self = this;
		var dlg = $('#category-create-dlg');
		dlg.omDialog({
			modal:true,
			title:'新增类别',
			width:'500',
			onClose:function(){
				self.validator.resetValidate(true);
			},
			buttons:[
			        {text:'确定',click:function(){
			        	var form = $('form',dlg);
			        	 if(form.valid()){
			        		 self.createCategory();
			        	 }
			        }},
			        {text:'取消',click:function(){
			        	dlg.omDialog('close');
			        }}
			        ]
		});
	},
	
	createCategory:function(){
		SYQ.Console.Overlay.show();
		var option = {
			dataType:'json',
			data:{code:countryCode},
			success:function(result){
				SYQ.Console.Overlay.hide();
				if(result.success){
					$('#category-create-dlg').omDialog('close');
					$('#user-config label[for="field-custom-menu"]').click();
					SYQ.Console.showTip('新增字段类别成功', 'success');
				}else{
					SYQ.Console.showTip('新增字段类别失败:'+result.message, 'error');
				}
			},
			error:function(){
				SYQ.Console.showTip('系统无响应', 'error');
			}
		};
		var form = $('#category-create-dlg form');
		$(form).ajaxSubmit(option);
	},
	
	showDeleteCategoryDlg:function(event){
		var dom = $(event.target);
		var self = this;
		var categoryId = dom.parents('.field-category').attr('categoryId');
		$.omMessageBox.confirm({
            content:'确定删除该类别吗?',
        	onClose:function(result){
        		if(result){
        			self.deleteCategory(categoryId);
        		}
        	}
        });
	},
	
	deleteCategory:function(categoryId){
		var option={
				url:this.deleteCategoryUrl,
				data:{
					categoryId:categoryId,
					code:countryCode
				},
				dataType:'json',
				success:function(result){
					if(result.success){
						$('#category-delete-dlg').omDialog('close');
						$('#user-config label[for="field-custom-menu"]').click();
						SYQ.Console.showTip('删除字段类别成功', 'success');
					}else{
						SYQ.Console.showTip('删除字段类别失败:'+result.message, 'error');
					}
				},
				error:function(){
					SYQ.Console.showTip('系统无响应', 'error');
				}
			};
		$.ajax(option);
	},
	
	toggleFieldLeft:function(event){
		var target = $(event.target);
		$('.field-custom-left',this.el).toggle();
		var parent = target.parent();
		parent.toggleClass('aps-console-column-exspand').toggleClass('aps-console-column-colspand');
	},
	
	showCategoryUpdateDiv:function(event){
		//由于双击类别的位置不一样是，event.target有可能不一样，需要进行处理
		var currentTarget = $(event.target);
		var categoryTitleDom = null;
		var categoryName = null;
		if(currentTarget.hasClass('category-title-text')){
			categoryTitleDom = currentTarget.parents('.category-title');
		}else if(currentTarget.hasClass('category-title')){
			categoryTitleDom = currentTarget;
		}else{
			return;
		};
		var categoryDom = categoryTitleDom.parents('.field-category');
		//类别的名称保存在父dom：field-cateogry的categoryName属性中
		categoryName = categoryDom.attr('categoryName');
		var categoryUpdateDom = categoryTitleDom.siblings('.category-title-update');
		var categoryUpdateInput = $('input',categoryUpdateDom);
		categoryUpdateInput.val(categoryName);
		categoryTitleDom.hide();
		categoryUpdateDom.show();
		categoryUpdateInput.focus();
	},
	
	updateCategory:function(event){
		var target = $(event.target);
		var newCategoryName = target.val();
		var categoryUpdateDom = target.parents('.category-title-update');
		var categoryTitleDom = categoryUpdateDom.siblings('.category-title');
		var categoryTitleTextDom = $('.category-title-text',categoryTitleDom);
		var categoryDom = target.parents('.field-category');
		var categoryId = categoryDom.attr('categoryId');
		var currentCategoryName = categoryDom.attr('categoryName');
		if( ''==$.trim(newCategoryName) || newCategoryName==currentCategoryName ){
			categoryUpdateDom.hide();
			categoryTitleDom.show();
		}else{
			var option = {
					url:this.updateCategoryUrl,
					type:'POST',
					data:{
						categoryId:categoryId,
						categoryName:newCategoryName,
						code:countryCode
					},
					dataType:'json',
					success:function(result){
						if(result.success){
							SYQ.Console.showTip('编辑字段类别成功', 'success');
							categoryDom.attr('categoryName',newCategoryName);
							categoryTitleTextDom.text(newCategoryName);
							categoryUpdateDom.hide();
							categoryTitleDom.show();
						}else{
							SYQ.Console.showTip('编辑字段类别失败:'+result.message, 'error');
						}
					},
					error:function(){
						SYQ.Console.showTip('系统无响应', 'error');
					}
			};
			$.ajax(option);
		}
	},
	
	updateCategoryByEnter:function(event){
		if(13==event.keyCode){
			this.updateCategory(event);
		}
	}
	
};
