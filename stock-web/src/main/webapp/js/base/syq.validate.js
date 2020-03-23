/*! aps.validate.js */
/**
 * aps validate 
 * settings 参数 有 rules ,messages , getLabelContainer
 * rules 和 message 相同于  jquery validate 的 rules messages
 * 
 * getLabelContainer 可重写，用于定位获得包含label 的 container 
 * 默认的 getLabelContainer 是查找 tabel 的第三列。
 * 
 * 
 * 事件 
 * on success - 成功时候，触发  
 * on error  - 错误时候，触发
 * 
 * 
 * 不过需要多传入 
 * 
 * getLabelContainer 
 */
;(function ($){
	$.validator.addMethod("idCheck", function(value){
    	var reg = /^(\w)+$/;
        return reg.test(value);
	}, '字母数字或下划线组成');
	
	$.validator.addMethod("idLength", function(value){
		if(null != value && value.length <= 40) {
			return true;
		}
		return false;
	}, '最长40个字符');
	$.validator.addMethod("positive", function(value, element) {
		return (value.match(/^[1-9]*[1-9][0-9]*$/) && !isNaN(parseInt(value)) && 1 <= parseInt(value) && parseInt(value) <= 65535);
	}, '端口号范围为1~65535');
	$.validator.addMethod('noLineBegin',function(value){
		return /^[^\n\s\r\t_]+.*$/.test(value);
	},'不能以空格或下划线开始'),
	
	
	$.fn.extend({
		syqValidate:function (settings){
			
			//子类可重写 ，用于定位获得包含 label 的 container 
			var getLabelContainer = function ($container){
				return $container.parents('td').next();
			};
			// 出现错误后触发
			var onError = function (errorMap, errorList){
			};
			// 出现成功后触发
			var onSuccess = function ($ele){
			};
			// $textfield - 文本域
			// 需要显示的 label
			// return false - 阻止show 事件 
			var onBeforeShow = function ($textfield,$prompt){
				return true;
			};
			// $textfield - 文本域
			// 需要显示的 label
			// return false - 阻止show 事件 
			var onBeforeHide = function ($textfield,$prompt){
				return true;
			};
			
			if(settings){
				(settings.getLabelContainer) && (getLabelContainer = settings.getLabelContainer );
				(settings.onSuccess) && (onSuccess = settings.onSuccess );
				(settings.onError) && (onError = settings.onError );
				(settings.onBeforeShow) && (onBeforeShow = settings.onBeforeShow );
				(settings.onBeforeHide) && (onBeforeHide = settings.onBeforeHide );
			};
			
			
			
			var defaults = {
				validateOnEmpty : true,
				errorElement: "label",
				ignore: 'aps-valid-ignore',
				errorClass: 'aps-valid-error',
				validClass: 'aps-valid',
				errorPlacement:  function($errorLabel, $ele) {
					 //默认是 td 的第三列
					getLabelContainer($ele).prepend($errorLabel);
				},
				showErrors:function(errorMap, errorList) {
					var $currentElements = $(this.currentElements);
					
					//判断是否keyup 时候产生错误，true - hide prompt , false - show
					$currentElements.each(function(index,obj){
						var $labelContainer = getLabelContainer($(obj));
						var $promptLabel  = $labelContainer.find('.aps-form-prompt');
						var $successLabel  = $labelContainer.find('.aps-valid-success');
						if(errorList.length != 0 ){
							$promptLabel.hide();
						}/*else{
							//$promptLabel.show();
						}*/
					});
					
					onError.apply(this,[errorMap, errorList]);
					this.defaultShowErrors();
				},
				success: function($ele) {
					$ele.addClass('aps-valid-success');
					onSuccess.apply(this,[$ele]);
					
					//当有提示和成功的时候，showErrors 运行在 success后，所以此处hide $promptLabel
					var $promptLabel  = $ele.parents('td').find('.aps-form-prompt');
					( $promptLabel.length > 0 ) && $promptLabel.hide();
				}
			};
			
			 settings = $.extend(true, {}, settings,defaults);
			
			$(this).delegate(':text,textarea,:password', 'focus', function(event) {
				var $textfield = $(this);
				var labelContainer = getLabelContainer($textfield);
				var $success = labelContainer.find('.aps-valid-success');
				var $prompt = labelContainer.find('.aps-form-prompt');
				
				/* 是否存在 成功提示*/
				var unSuccess = true;
				if($success.length != 0 && !$success.is(":hidden")){
					unSuccess = false;
				}
				
				
				// 没有错误
				if($textfield.hasClass('aps-valid-error')  && 
						!labelContainer.find('.aps-valid-error').is(':hidden')){
					return ;
				}
				
				//输入了信息，不提示
				if($textfield.val().length != 0 ){
					return ;
				}
				//验证通过，但输入了信息，也会输出提示
//				if($textfield.hasClass('aps-valid')){
//					return ;
//				}
				//prompt 存在并有内容,而且 这个 成功提示是没有的,  触发
				if( !!$prompt && $prompt.length!=0 && $prompt.text().length>0 &&  unSuccess &&  onBeforeShow($textfield,$prompt ) ){
					$prompt.show();
				}
			}).delegate(':text,textarea,:password', 'blur', function(event) {
				var $textfield = $(this);
				var $prompt = getLabelContainer($textfield).find('.aps-form-prompt');
				
				if( !!$prompt && onBeforeHide($textfield,$prompt)){					
						$prompt.hide();
				}
				//$textfield.valid();
			});
			
			/*XXX 在dialog 情况下，选中 input  没有触发 focus 事件，触发一下*/
			/* 其实在必填请情况下有问题， 后期改进*/
			$(this).find(':text,textarea,:password').each(function (k,v){
				if($(this).is(":focus")){
					$(this).focus();
				}
			})
			
			/** 
			 * 重置验证 isClear - true -清除所有input 框的内容
			 * */
			this.resetValidate = function (isClear){
				//远程验证是否存在的时候，有数据缓存在前台。调用这个方法可以清除缓存
				this.valid();
				
				!!isClear && this.resetForm();
				
				this.find("label.syq-form-prompt").hide();
				this.find("label.syq-valid-error").hide();
				this.find("label.syq-valid-success").hide();
			};
			$(this).validate(settings);
			
			return this;
		}
	});
})(jQuery);