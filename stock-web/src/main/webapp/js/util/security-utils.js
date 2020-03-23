SYQ.Security = SYQ.Security || {};
SYQ.Security.Utils = {
	createRoundConnerButton: function(button, buttonType){
		var left = $("<span class='round-conner-button"+buttonType+"-left'></span>");
		var right = $("<span class='round-conner-button"+buttonType+"-right'></span>");
		var center = $("<span class='round-conner-button"+buttonType+"-center'></span>");
		button.wrap(left).wrap(right).wrap(center);
		var text = button.text();
		button.text("").addClass("cursor-click");
		$("<span class='round-conner-button-text'>"+text+"</span>").appendTo(button);
	},
	
	convertToGridData : function(data) {
		var newData = undefined;
		if(data instanceof Object) {
			newData = data;
		} else {
			newData = JSON.parse(data);
		}

		if(undefined != newData.success) {
			return {total:0, rows:[]};
		}
		
		var processData = $.extend(true, {}, newData);
		if(newData.rows instanceof Array) {
			var rows = processData.rows;
			for(rowIndex in rows) {
				var row = rows[rowIndex];
				var attrs = row.attributes;
				for(key in attrs) {
					if(attrs.hasOwnProperty(key)) {
						row[key] = attrs[key];
					}
				}
				row.attributes = undefined;
			}
		} else {
			var attrs = processData.attributes;
			for(key in attrs) {
				if(attrs.hasOwnProperty(key)) {
					processData[key] = attrs[key];
				}
			}
			processData.attributes = undefined;
		}
		
		return processData;
	},
	tipInput: function(jqueryInput, text){
		jqueryInput.addClass("aps-input-tip");
		jqueryInput.focus(function(event){
			var source = $(event.target);
			var value = source.val();
			if(value == text){
				source.val("");
				jqueryInput.removeClass("aps-input-tip").addClass("aps-input-tip-enable");
			}
		}).blur(function(event){
			var source = $(event.target);
			var value = source.val();
			if(value == ""){
				source.val(text);
				jqueryInput.removeClass("aps-input-tip-enable").addClass("aps-input-tip");
			}
		});
	}
};