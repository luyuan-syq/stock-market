<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<meta name="decorator" content="none" />

<script type="text/javascript">
	$(function(){
		var userConfig = $('#user-config');
		$('.user-config-right label',userConfig).click(function(){
			var url = $(this).attr('pageUrl');
			$.ajax({
				url : url,
				type: 'post',
				success: function(data) {
					$('.user-config-left').html(data);
				},
				error: function(data) {
					
				}
			});
		});
		$('.user-config-right label:first',userConfig).click();
	});
</script>

<div id="user-config">
    <div class="user-config-right ui-buttonset-vertical display-none">
        <input type="radio" id="field-custom-menu" name="user-config-menu" checked="checked"/>
        <label for="field-custom-menu" pageUrl="./visaTemplate/getCustomField?code=${code}">字段定义</label>
    </div>
    <div class="user-config-left">
    </div>
    
    <%-- 这里定义了字段新增和编辑时用到的dom模板 --%>
    <div class="display-none">
        <%--文本输入的显示类型模板 --%>
        <div class="textInput-text-default-value-template">
            <s:textfield name="defaultValues" theme="simple" maxlength="1000"></s:textfield>
        </div>
        <div class="textInput-textarea-default-value-template">
            <s:textarea name="defaultValues" theme="simple"  onkeyup="this.value = this.value.substring(0, 1000)"></s:textarea>
        </div>
    </div>
</div>