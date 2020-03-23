(function() {
	if(!window.APS) {
		APS = {};
	}
	
	APS.Login = function(options) {
		this.username = options.username;
		this.password = options.password;
		this.APS_CONTEXT_PATH = options.APS_CONTEXT_PATH;
		this.errorCode = options.errorCode;
		this.$respMsg = $('span[name=respMsg]');
		this.$rememberPwd = $('.remember-password');
		this.$pwd = $('#password'),
		this.$username = $('#username');
		this.$submit = $('#submit');
		
		this.initialize();
	};
	
	$.extend(APS.Login.prototype, {
		initialize : function() {
			// 根据cookie 回填用户名,密码
			var c_userName = APS.Util.getCookie('aps-login-username');
			var c_pwd = APS.Util.getCookie('aps-login-pwd');
			if(0 != c_userName.length && 0 != c_pwd.length) {
				this.$username.val(c_userName);
				this.$pwd.val(c_pwd);
			} 
			// 用户名输入框聚焦
			this.$username.focus();

			// 密码为空和获取焦点
			if($.trim(this.$pwd.val()).length != 0) {
				this.$pwd.addClass('focus');
			}
			
			
			// 验证输入
			this.validate();
			this.bindEvent();
			// 消除登录时服务器返回的错误信息
			// this.clearRespMsgEvent();
			
			// 显示服务器响应的错误信息
			if(this.errorCode) {
				var msg;
				if(1 == this.errorCode) {
					msg = '用户名不能为空，请重新登录';
				} else if (2 == this.errorCode) {
					msg = '密码不能为空，请重新登录';
				} else if (3 == this.errorCode) {
					msg = '用户被禁用，请联系管理员';
				} else if (4 == this.errorCode) {
					msg = '输入密码超过指定次数，请稍候再登录';
				} else if (5 == this.errorCode) {
					msg = '证书被禁用，请联系管理员';
				} else if (6 == this.errorCode) {
					msg = '证书失效，请联系管理员';
				} else if (7 == this.errorCode) {
					msg = '用户名或者密码错误，请重新登录';
				}  else if (8 == this.errorCode) {
					msg = '任务编码不能为空，请重新登录';
				}  else if (9 == this.errorCode) {
					msg = '任务编码不存在，请重新登录';
				}else {
					msg = '未知错误，如果重复出现此错误请联系管理员';
				}
				this.$respMsg.html('<span class="resp-error">' + msg + '</span>');
			} else {
				this.$respMsg.empty();
			}
		},
		bindEvent : function() {
			var self = this;
			$('#guest').click(function() {
				window.location.href = self.APS_CONTEXT_PATH + '/portal';
				return false;
			});
			// 判断是否启用了浏览器cookie功能
			this.$rememberPwd.change(function() {
				var $el = $(this);
				if($el.attr('checked') == 'checked' && !APS.Util.isCookieEnable()) {
					$('#aps-login-form .password-service-warning').html('<span class="content">请启用浏览器的cookie功能</span>');
				} else {
					$('#aps-login-form .password-service-warning').empty();
				}
			});
		},
		// 消除登录时服务器返回的错误信息
		clearRespMsgEvent : function() {
			var self = this;
			this.$username.click(function() {
				self.$respMsg.empty();
			});
			this.$pwd.click(function() {
				self.$respMsg.empty();
			});
		},
		
		validate : function() {
			/*
			 * 当获取焦点时提示
			 */
			var self = this;
			this.$username.focus(function() {
				if($.trim(self.$username.val()).length == 0) {
					self.$username.addClass('focus');
				}
			}).blur(function() {
				if($.trim(self.$username.val()).length == 0) {
					self.$username.removeClass('focus');
				}
			});
			this.$pwd.focus(function() {
				if($.trim(self.$pwd.val()).length == 0) {
					self.$pwd.addClass('focus');
				}
			}).blur(function() {
				if($.trim(self.$pwd.val()).length == 0) {
					self.$pwd.removeClass('focus');
				}
			});
			
			this.$submit.click(function() {
				var goon = false;
				if($.trim(self.$username.val()).length == 0) {
					goon = false;
					self.$username.focus();
					return false;
				} else {
					goon = true;
				};
				
				if($.trim(self.$pwd.val()).length == 0) {
					goon = false;
					if( !self.$username.is(":focus")) {
						self.$pwd.focus();
					}
					return false;
				} else {
					goon = true;
					if(self.$rememberPwd.attr('checked') == 'checked') {
						try {
							APS.Util.setCookie('aps-login-username', $.trim(self.$username.val()), 7);
							APS.Util.setCookie('aps-login-pwd', $.trim(self.$pwd.val()), 7);
						} catch(e) {
							// 勾选记住密码选项时已经处理, 这里不重复处理
						}
					}
				};
				
				return goon;
			});
		}
		
		
	});
})();

function checkFormForCode() {
	var code = $("#code");
	var respMsg = $('span[name=respMsg]');
	respMsg.html('');
	if($.trim(code.val()).length == 0) {
		respMsg.html('<span class="resp-error">任务编码不能为空</span>');
		return;
	}
	
	$("#softkey_form").submit();
}