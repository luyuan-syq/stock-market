// 定义处理Ajax请求的Ajax API基类
APS.AjaxAction= {
	initSettingParam : function(settings){
		if(null==settings){
			settings = {};
			settings.data = {};
		}
		if(null==settings.data){
			settings.data = {};
		}
		return settings;
	},

	/*
	 * @TODO 过滤参数,返回的参数对象中只存储paramValidator的required属性设置为true的参数
	 * expectedParams中存储paramValidator paramValidator数据格式如
	 * 'id':{required:true,type:'number'}
	 * required标识该参数是否必须的,type标识该属性通过typeof获取的类型
	 * required == true,该参数必须存在并且不能为null;required == false,该参数可以不出现或者可以出现值为null
	 */
	validate : function(settings,expectedParams){
		
		if(null==expectedParams){
			throw new Error('expectedParams不能为空');
		}
        // 校验dataType
        if(null==settings.dataType||'json'==settings.dataType){
            settings.data.format='json';
            settings.dataType='json';
        }else if('xml'==settings.dataType){
            settings.data.format='xml';
            settings.dataType='xml';
        }else{
			throw new Error('dataType的值只能是json或者xml');
        }
		if(null==settings||null==settings.data){
			throw new Error('参数params不能为空');
		}
        // 判断params是否为object类型，使用expectedParams校验用户参数params
        if('object'!=jQuery.type(settings.data)){
			throw new Error('参数params必须是object类型');
        }
        var validParams= {}
        for(var key in expectedParams){
			var validator = expectedParams[key];
			var value=settings.data[key];
            // 如果value为空，判断这个key是不是必须的
			if(null==value){
                if(validator.required){
					throw new Error('参数'+key+'不能为空');
                }
			}else{
	            // 校验value的类型
	            if(typeof(value)!=validator.type){
					throw new Error('参数'+key+'类型不正确');
	            }
	            // 使用校验函数validateMethod校验value
	            if(null!=validator.validateMethod){
					var result=validator.validateMethod(value)
					if(!result){
						throw new Error('参数'+key+'的值格式不正确');
					}
	            }
	            validParams[key]=value;
            }
		}
        
        settings.data=validParams;
        return settings;
	},

	ajax : function(_settings){
    	var _settings = jQuery.extend(
        	{
        		type : 'get',
        		dataType : 'json',
        		timeout : 0,
        		async : true,
        		cache : true
        	},
        	_settings
        );
    	
    	// 如果dataType是xml，返回xml格式的数据
    	if(_settings.dataType=='xml'){
    		_settings.data.format='xml'
    	}
    	// 返回ajax的延迟对象
    	return jQuery.ajax(_settings);
	},

	executeAction : function(settings,action,url){
        if(null==url){
	        settings.url=APS_CONTEXT_PATH + '/ajaxapi'+APS.PageContext.pagePath;
        }
		settings.data.action=action;
		return this.ajax(settings);
	}
}

// 定义处理Portlet的AjaxPortletAction API
APS.AjaxPortletAction = {};

jQuery.extend(APS.AjaxPortletAction,APS.AjaxAction,{
	/*
	 * @TODO 向layoutId指定的Layout Portlet添加Portlet 
	 * @param data:{name,row,col,layoutId}
	 * name是必须的,对应portlet的name属性,数据格式是samples::JspPortlet
	 * layoutId是Layout Portlet的id
	 */
	addPortlet : function(settings) {
    	var expectedParams= {
			'name':{required:true,type:'string'},
			'row':{required:true,type:'number'},
			'col':{required:true,type:'number'},
			'layoutId':{required:true,type:'number'},
			'page':{required:false,type:'string'}
		}
    	settings = this.validate(settings,expectedParams);
        return this.executeAction(settings,'add',null);
	},

	/*
	 * @TODO Move Portlet portlet placement action @param
	 * data:{id,newLayoutId,oldLayoutId,newRow,newCol,oldRow,oldCol}
	 * id,newLayoutId,oldLayoutId,newRow,newCol,oldRow,oldCol是必须参数
	 */
	movePortlet : function(settings){
    	var expectedParams = {
			'id': {required: true,type: 'number'},
			'newRow': {required: true,type: 'number'},
			'newCol': {required: true,type: 'number'},
			'oldRow': {required: true,type: 'number'},
			'oldCol': {required: true,type: 'number'},
			'newLayoutId': {required: true,type: 'number'},
			'oldLayoutId': {required: true,type: 'number'},
			'page':{required:false,type:'string'}
		}
    	settings = this.validate(settings,expectedParams);
        return this.executeAction(settings,'move',null);
	},

	 /*
	 * @TODO 删除layoutId指定的Layout Portlet中由id所指定的Portlet
	 * @param data:{id,layoutId}
	 * id是必须参数对应数据库中fragment_id字段,是Portlet的fragmentId
	 * layoutId是Layout Portlet的id
	 */
	removePortlet: function(settings){
    	var expectedParams = {
			'id': {required: false,type: 'number'},
			'layoutId': {required:true,type: 'number'},
			'page':{required:false,type:'string'}
		}
    	settings = this.validate(settings,expectedParams);
    	return this.executeAction(settings,'remove',null);
	},

	/*
	 * @TODO 使用height来修改指定Portlet的高度
	 * @param data:{id,height}
	 *            id,height是必须参数
	 */
	resizePortlet : function(settings){
		var expectedParams = {
			'id': {required: true,type: 'number'},
			'height': {required: true,type: 'number'}
		}
		settings = this.validate(settings,expectedParams);
        return this.executeAction(settings,'resize',null);
	},

	/*
	 * @TODO Get Portlets retrieves the portlet list available to the current
	 * subject @param data:{category,[way2getPortlets,filter,filterTypes]}
	 * category是必须的参数,值必须为all或者search
	 * way2getPortlets,filter,filterTypes是非必须参数
	 */

	getPortlets : function(settings) {
        var categoryValidate=function(category){
			if("all"==category.toLowerCase()||"search"==category.toLowerCase()) {
				// category必须是all或者search;
                return true;
			}else{
                return false;
            }
        }
        var expectedParams = {
            'category': {required: true,type: 'string',validateMethod: categoryValidate},
            'way2getPortlets': {required: false,type: 'string'},
            'filterTypes': {required: false,type: 'string'},
            'filter': {required: false,type: 'string'}
        }
        settings = this.validate(settings,expectedParams);
        return this.executeAction(settings,'getportlets',null);
	},

	/*
	 * @TODO Get Portlet Actions retrieves the current set of valid actions for
	 * one or more portlet windows @param data:{id} id是必须的参数
	 */
	// 其实id可以是一个数组，但jquery的ajax发给后台时，后台取不到这个id数组，所以现在先规定id只能传一个字符串
	getPortletActions : function(settings){
		var expectedParams = {
                'id':{required:true,type:'string'},
                'pid':{required:false,type:'string'}
        }
        settings = this.validate(settings,expectedParams);
        return this.executeAction(settings,'getactions',null);
	},

	/*
	 * @TODO Changes the window state or portlet mode for a given portlet window
	 * @param data:{id,navState,mode||state||(model&&state)}
	 * id是必须参数,mode或state至少存在一个
	 */
	changeWindow : function(settings){
		var expectedParams = {
				'id': {required: true,type: 'number'},
				'navState': {required: true,type: 'string'},
				'mode': {required: false,type: 'string'},
				'state': {required: false,type: 'string'}
		}
		settings = this.validate(settings,expectedParams);
    	if((null==settings.data.mode)&&(null==settings.data.state)){
    		throw new Error("A portlet window's Window State or Portlet Mode must be provided");
    	}
    	var	url = APS_CONTEXT_PATH + '/ajaxapi/_ns_' + settings.data.navState+APS.PageContext.pagePath;
	    return this.executeAction(settings,'window',url);
	}
});

APS.BaseSiteAction= {
		execute : function(method,settings){},

		add : function(settings){
			return this.execute('add', settings);
		},

		update : function(settings){
			return this.execute('info', settings);
		},

		/*
		 * @TODO 删除站点资源 @data:{path} path是必须参数
		 */
		remove : function(settings){
			return this.execute('remove',settings);
		},

		/*
		 * @TODO 复制站点资源
		 */
		copy : function(settings){
			return this.execute('copy',settings);
		},

		/*
		 * @TODO 移动站点资源 @data:{path,destination,name} path是必须参数
		 */
		move : function(settings){
			return this.execute('move',settings);
		},

		/*
		 * @TODO 为站点资源添加元数据 @data:{path,name,lang,value}
		 * path,name,lang,value是必须参数
		 */
		addMetadata : function(settings){
			var expectedParams = {
					'path':{required:true,type:'string'},
					'name':{required:true,type:'string'},
					'lang':{required:true,type:'string'},
					'value':{required:true,type:'string'}
			}
			settings = this.validate(settings,expectedParams);
			return this.execute('addMeta',settings);
		},

		/*
		 * @TODO 为站点资源删除元数据 @data:{path,name,lang} path,name,lang,value是必须参数
		 */
		removeMetadata : function(settings){
			var expectedParams = {
					'path':{required:true,type:'string'},
					'name':{required:true,type:'string'},
					'lang':{required:true,type:'string'}
			}
			settings = this.validate(settings,expectedParams);
			return this.execute('removeMeta',settings);
		},
		/*
		 * @TODO @param
		 * data:{method="updateMeta",name,lang,oldName,oldLang,path,value}
		 */
		updateMetadata : function(settings){
			var expectedParams = {
					'path':{required:true,type:'string'},
					'name':{required:true,type:'string'},
					'lang':{required:true,type:'string'},
					'value':{required:true,type:'string'},
					'oldname':{required:true,type:'string'},
					'oldlang':{required:true,type:'string'}
			}
			settings = this.validate(settings,expectedParams);
			return this.execute('updateMeta',settings);
		},

		/*
		 * @TODO @param data:{method="addSecref",path,name,kind}
		 */
		addSecurityReference : function(settings){
			var expectedParams = {
					'path':{required:true,type:'string'},
					'name':{required:true,type:'string'},
					'kind':{required:true,type:'string'}
			}
			settings = this.validate(settings,expectedParams);
			return this.execute('addSecref',settings);
		},

		/*
		 * @TODO @param data:{method="removeSecref",path,name,kind}
		 */
		removeSecurityReference : function(settings){
			var expectedParams = {
					'path':{required:true,type:'string'},
					'name':{required:true,type:'string'},
					'kind':{required:true,type:'string'}
			}
			settings = this.validate(settings,expectedParams);
			return this.execute('removeSecref',settings);
		},

		/*
		 * @TODO @param data:{method="updateSecref",path,name,kind,oldname}
		 */
		updateSecurityReference : function(settings){
			var expectedParams = {
					'path':{required:true,type:'string'},
					'name':{required:true,type:'string'},
					'kind':{required:true,type:'string'},
					'oldname':{required:true,type:'string'}
			}
			settings = this.validate(settings,expectedParams);
			return this.execute('updateSecref',settings);
		},

		/*
		 * @TODO @param data:{method="removeSecdef",path,id}
		 */
		removeSecurityDef : function(settings){
			var expectedParams = {
					'path':{required:true,type:'string'},
					'id':{required:true,type:'string'}
			}
			settings = this.validate(settings,expectedParams);
			return this.execute('removeSecdef',settings);
		}
};

APS.AjaxLinkAction = {};

jQuery.extend(APS.AjaxLinkAction,APS.AjaxAction,APS.BaseSiteAction,{
	/*
	 * @TODO Retrieve a single link and updates various parts of the PSML link
	 * AJAX Parameters: action = updatelink General methods: method = add |
	 * remove Info methods: | info Meta methods: | @param data:{[link]}
	 * link可选参数参数
	 */
	execute : function(method,settings){
        settings.data.method=method;
		return this.executeAction(settings,'updatelink',null);
	},

	/*
	 * @TODO 查找目标Link 
	 * @param data:{[link]}
	 * link是可选参数参数,如果不指定link或者link=null,会以/作为目标查询
	 * link格式是/_user/baidu.link,对link表的path字段
	 */
	getLink : function(settings){
    	var expectedParams = {
    		'link':{required:false,type:'string'}
        }
        settings = this.validate(settings,expectedParams);
    	return this.executeAction(settings,'getlink',null);
	},

	/*
	 * @TODO 添加Link 
	 * @param data:{path,[url?],[title?],[shortTitle?]} 
	 * path必选参数参数用于指定目标Link路径,数据格式是:	/_user/baidu.link
	 * url用于指定目标Link的URL,数据格式是:	①http://www.baidu.com	②/welcome.psml
	 * title用于指定目标Link的title
	 * shortTitle用于指定目标Link的shortTitle
	 */
	addLink : function(settings){
		var expectedParams = {
			'path':{required: true,type: 'string'},
			'url':{required: false,type: 'string'},
			'title':{required: false,type: 'string'},
			'shortTitle':{required: false,type: 'string'}
		}
		settings = this.validate(settings,expectedParams);
		return this.add(settings);
	},

	/*
	 * @TODO 根据提供的参数更新Link 
	 * @param data:{path,[url?],[title?],[shortTitle?],[target?],[hidden?]} 
	 * path是必选参数参数,用于指定目标Link
	 */
	updateLink : function(settings){
		var expectedParams = {
			'path': {required: true,type: 'string'},
			'url': {required: false,type: 'string'},
			'target': {required: false,type: 'string'},
			'title': {required: false,type: 'string'},
			'shortTitle': {required: false,type: 'string'},
			'hidden': {required: false,type: 'boolean'}
		}
		settings = this.validate(settings,expectedParams);
		return this.update(settings);
	},
	/*
	 * @TODO 删除目标Link 
	 * @param data:{path}
	 * path格式是/_user/baidu.link,对应link表path字段
	 */
	removeLink : function(settings){
		var expectedParams = {
			'path':{required: true,type: 'string'}	
		}
		settings = this.validate(settings,expectedParams);
		return this.remove(settings);
	},
	
	/*
	 * @TODO 将path指定的Link复制到目标Link
	 * data:{method="copy",destination,resourceName,path}
	 * path是必选参数,目标Link全路径
	 * destination是必选参数,作用:目标的资源文件夹folder的路径 destination值格式是: /_user/
	 * resourceName是必选参数,作用:目标资源文件名 resourceName值格式是: welcome.link
	 * 最终被复制的文件路径是:destination/resourceName
	 */
	copyLink : function(settings){
		var expectedParams = {
			'path':{required: true,type: 'string'},
			'destination':{required: true,type: 'string'},
			'resourceName':{required: true,type: 'string'}
		}
		settings = this.validate(settings,expectedParams);
		return this.copy(settings);
	},
	
	/*
	 * @TODO 将path指定Link移动到destination/resourceName所指定的Link
	 * data:{method="copy",destination,resourceName,path}
	 * path是必选参数,目标Link全路径
	 * destination是必选参数,作用:目标的资源文件夹folder的路径 destination值格式是: /_user/
	 * resourceName是必选参数,作用:目标资源文件名 resourceName值格式是: welcome.link
	 * 最终被复制的文件路径是:destination/resourceName
	 */
	moveLink : function(settings){
		var expectedParams = {
			'path':{required: true,type: 'string'},
			'destination':{required: true,type: 'string'},
			'resourceName':{required: true,type: 'string'}
		}
		settings = this.validate(settings,expectedParams);
		return this.move(settings);
	}
});


APS.AjaxPageAction = {};

jQuery.extend(APS.AjaxPageAction,APS.AjaxAction,APS.BaseSiteAction,{

	execute : function(method,settings){
	    settings.data.method=method;
		return this.executeAction(settings,'updatepage',null);
	},
	
	/*
	 * @TODO Get Page retrieves a page from the Page Manager store and PSML format
	 * @param data: {[page?,fragments?,layoutId?,entity?]}
	 * page,fragments,layoutId,entity是可选参数
	 * page是psml的全路径,如果不指定,会获取当前Page
	 */
	getPage : function(settings){
		settings = this.initSettingParam(settings);
        var expectedParams = {
                'page':  {required: false,type: 'string'},
                'fragments': {required: false,type: 'string'},
                'layoutId': {required: false,type: 'string'},
                'entity': {required: false,type: 'string'}
        }
        settings = this.validate(settings,expectedParams);
    	return this.executeAction(settings,'getpage',null);
	},

	/*
	 * @TODO 用指定属性值更新目标文件
	 * @param
	 * data:{method="info",[path,title,shortTitle,layoutDecorator,portletDecorator,theme,hidden,layout]}
	 * 所有参数都是可选的,用于修改Page的属性
	 */
	updatePage : function(settings){

		settings = this.initSettingParam(settings);
		var expectedParams = {
			'path':{required:false,type:'string'},
			'title':{required:false,type:'string'},
			'shortTitle':{required:false,type:'string'},
			'layoutDecorator':{required:false,type:'string'},
			'portletDecorator':{required:false,type:'string'},
			'theme':{required:false,type:'string'},
			'hidden':{required:false,type:'string'},
			'layout':{required:false,type:'string'},
			'layouts':{required:false,type:'string'}
		}
		settings = this.validate(settings,expectedParams);
		return this.update(settings);
	},

	/*
	 * @TODO @param data:{method="addMeta",name,lang,[path,value]}
	 */
	addMetadata : function(settings){
		var expectedParams = {
			'name':{required:true,type:'string'},
			'lang':{required:true,type:'string'}
		}
		settings = this.validate(settings,expectedParams);
    	return this.execute('addMeta',settings);
	},


	/*
	 * @TODO @param data:{method="removeMeta",name,lang,[path]}
	 */
	removeMetadata : function(settings){
		var expectedParams = {
			'name':{required:true,type:'string'},
			'lang':{required:true,type:'string'}
		}
		settings = this.validate(settings,expectedParams);
		return this.execute('removeMeta',settings);
	},

	/*
	 * @TODO @param data:{method="addSecref",name,kind,[path]}
	 */
	addSecurityReference : function(settings){
		var expectedParams = {
			'name':{required:true,type:'string'},
			'kind':{required:true,type:'string'}
		}
		settings = this.validate(settings,expectedParams);
		return this.execute('addSecref',settings)
	},

	/*
	 * @TODO @param data:{method="updateSecref",name,oldName,kind,[path]}
	 */
	updateSecurityReference : function(settings){
		var expectedParams = {
			'name':{required:true,type:'string'},
			'kind':{required:true,type:'string'},
			'oldname':{required:true,type:'string'}
		}
		settings = this.validate(settings,expectedParams);
		return this.execute('updateSecref',settings);
	},

	/*
	 * @TODO @param data:{method="removeSecref",name,kind,[path]}
	 */
	removeSecurityReference : function(settings){
		var expectedParams = {
			'name':{required:true,type:'string'},
			'kind':{required:true,type:'string'}
		}
		settings = this.validate(settings,expectedParams);
		return this.execute('removeSecref',settings);
	},

	/*
	 * @TODO @param data:{method="removeSecdef",id,[path]}
	 */
	removeSecurityDef : function(settings){
		var expectedParams = {
			'id':{required:true,type:'string'}
		}
		settings = this.validate(settings,expectedParams);
		return this.execute('removeSecdef',settings);
	},

	/*
	 * @TODO @param data:{method="add",sizes,[path,title,shortTitle]}
	 */
	addPage : function(settings){
		var expectedParams = {
			'sizes':{required:true,type:'string',validateMehotd:function(sizes){
				if(null==settings.data.sizes){
					throw new Error("参数params.sizes是必须的");
				}

				if(!jQuery.isArray(settings.data.sizes)){
					throw new Error("参数params.sizes必须是数组");
					}
				}
			},
            'path':{required:false,type:'string'},
            'title':{required:false,type:'string'},
            'shortTitle':{required:false,type:'string'}

		}
		settings = this.validate(settings,expectedParams);
		var _sizes = settings.data.sizes.toString();
		settings.data.sizes=_sizes;
		return this.add(settings);

	},

	/*
	 * @TODO 将path指定的文件复制到目标文件 @param
	 * data:{method="copy",destination,resourceName,[path?]}
	 * destination是必选参数,作用:目标的资源文件夹folder的路径 destination值格式是: /_user/liuxiaotian
	 * resourceName是必选参数,作用:目标资源文件名 resourceName值格式是: welcome.psml
	 * 最终被复制的文件路径是:destination/resourceName
	 * path是可选参数,作用:后台获取path指定的Page 不指定path参数或path值为null,后台会获取当前Page path值的格式是:/workbench.psml
	 */
	copyPage : function(settings){
		var expectedParams = {
			'resourceName': {required: true,type: 'string'},
			'destination': {required: true,type: 'string'},
            'path': {required: false,type: 'string'}
		}
		settings = this.validate(settings,expectedParams);
		return this.copy(settings);
	},

	/*
	 * @TODO 将path指定的文件移动到目标文件
	 * @param data:{method="move",destination,name,[path?]}
	 * destination是必选参数,作用:目标的资源文件夹folder的路径 destination值格式是: /_user/liuxiaotian
	 * resourceName是必选参数,作用:目标资源文件名 resourceName值格式是: welcome.psml
	 * 目标文件最终被移动到的文件路径是:destination/resourceName
	 * path是可选参数,作用:后台获取path指定的Page 不指定path参数或path值为null,后台会获取当前Page path值的格式是:/workbench.psml
	 */
	movePage : function(settings){
		var expectedParams = {
			'resourceName':{required:true,type:'string'},
			'destination':{required:true,type:'string'},
            'path':{required:false,type:'string'}
		}
		settings = this.validate(settings,expectedParams);
		return this.move(settings);
	},

	/*
	 * @TODO 将指定Page删除 
	 * @param data:{method="remove",[path]}
	 * path如果为null或没有指定,则删除当前Page
	 * 格式如:/_user/liuxiaotian/welcome.psml
	 */
	removePage : function(settings){
		settings = this.initSettingParam(settings);
		var expectedParams = {
			'path':{required:false,type:'string'}
		}
		settings = this.validate(settings,expectedParams );
		return this.remove(settings);
	},

	/*
	 * @TODO @param data:{method="updateFragment",[id,layout,path,title]}
	 */
	updateFragment : function(settings){
        var expectedParams = {
            'id':{required:true,type:'string'},
            'title':{required:false,type:'string'},
            'layout':{required:false,type:'string'},
            'path':{required:false,type:'string'}
		}
        settings = this.validate(settings,expectedParams);
		return this.execute('updateFragment',settings);
	},

	/*
	 * @TODO @param data:{method="updateLayout",sizes,[path]}
	 */
	updateLayout : function(settings){
		var validateSize=function(param){
			if(null==param){
	    		throw new Error("参数params.sizes是必须的");
	    	}

			if(!jQuery.isArray(param)){
				throw new Error("参数params.sizes必须是数组");
			}
		}
		var expectedParams = {
			'sizes':{required:true,type:'string',validateMethod:validateSize},
			'path':{required:false,type:'string'}
		}
		settings = this.validate(settings,expectedParams);
		var _sizes = settings.data.sizes.toString();
		return this.execute('updateLayout',settings);

	},

	/*
	 * @TODO @param data:{method="addFragment",layoutId,layout,[path]}
	 */
	addFragment : function(settings){
		var expectedParams = {
				'layoutId':{required:true,type:'string'},
				'layout':{required:true,type:'string'},
				'path':{required:false,type:'string'}
		}
		settings = this.validate(settings,expectedParams);
		return this.execute('addFragment',settings);
	},

	/*
	 * @TODO @param data:{method="removeFragment",[id,path]}
	 */
	removeFragment : function(settings){
		settings = this.initSettingParam(settings);
        var expectedParams = {
            'id':{required:false,type:'string'},
            'path':{required:false,type:'string'}
        }
        settings = this.validate(settings,expectedParams);
		return this.execute('removeFragment',settings);
	},

	/*
	 * @TODO 
	 * @param
	 * data:{method="updatePortletDecorator",id,[path,portletDecorator]}
	 */
	updatePortletDecorator : function(settings){
		settings = this.initSettingParam(settings);
        var expectedParams = {
            'id':{required:true,type:'string'},
            'portletDecorator':{required:false,type:'string'},
            'path':{required:false,type:'string'}
        }
        settings = this.validate(settings,expectedParams);
		return this.execute('updatePortletDecorator',settings);
	},

	/*
	 * @TODO @param data:{method="updateTheme",[path,theme]}
	 */
	updateLayoutDecorator : function(settings){
		settings = this.initSettingParam(settings);
        var expectedParams = {
            'path':{required:false,type:'string'},
            'theme':{required:false,type:'string'}
        }
        settings = this.validate(settings,expectedParams);
		return this.execute('updateTheme',settings);
	}

});

APS.AjaxFolderAction = {};

jQuery.extend(APS.AjaxFolderAction,APS.AjaxAction,APS.BaseSiteAction,{
	
	execute : function(method,settings){
	    settings.data.method=method;
		return this.executeAction(settings,'updatefolder',null);
	},
	
	/*
	 * @TODO 获取Folder
	 * @param data:{[folder?]} folder是可选参数,如果为null或者不指定,后台以/来初始化该值
	 * folder值格式是: _user/liuxiaotian或/_user/liuxiaotian或null
	 */
	getFolder : function(settings){
		settings = this.initSettingParam(settings);
    	var expectedParams = {
            'folder': {required: false,type: 'string'}
        }
    	settings = this.validate(settings,expectedParams);
    	return this.executeAction(settings,'getfolder',null);
	},
	/*
	 * @TODO Get the immediate contents of a folder in JSON format @param
	 * 获取指定Folder本身的信息以及该Folder下的子Folders,Pages,Links
	 * data:{data,[format]}
	 * format是可选参数,默认json
	 * data:是json格式的对象,data中必须传递widgetId,格式如下
	 * data : {
     * 			data : {
     *				widgetId : '_user/liuxiaotian'
     *			}
	 * }
	 */
	getFolderlist : function(settings){
		if(!settings.data.data){
			throw new Error("参数data是必须的");
		}
		settings.data.data = JSON.stringify(settings.data.data);
		var expectedParams = {
			'data':{required: true,type: 'string'},
			'format':{required: false,type: 'string'}
		}
		settings = this.validate(settings,expectedParams);
    	return this.executeAction(settings,'getfolderlist',null);
	},

	/*
	 * @TODO Get the immediate contents of a folder in Ajax Format @param
	 * @param data:{data} data是必须参数
	 * data:是string型参数,指定了目标Folder的路径,格式是_user/liuxiaotian
	 */
	getFolderslist : function(settings){
		var expectedParams = {
			'data':{required:true,type:'string'}
		}
		settings = this.validate(settings,expectedParams);
    	return this.executeAction(settings,'getfolders',null);
	},
	/*
	 * @TODO Get Pages retrieves all pages for the given folder @param
	 * 返回指定Folder下的所有Page
	 * @param data:{[folder]}
	 * folder可选参数,指定某个Folder
	 */
	getPages : function(settings){
		settings = this.initSettingParam(settings);
        var expectedParams = {
            'folder': {required:true,type:'string'}
        }
        settings = this.validate(settings,expectedParams);
        return this.executeAction(settings,'getpages',null);
	},
	
	/*
	 * @TODO 添加Folder
	 * @param data:{path,[title?]}
	 * path是必须参数,用于指定目标路径,格式为_user/liuxiaotian
	 * title是非必须参数,用于设置资源文件夹名
	 */
	addFolder : function(settings){
		var expectedParams = {
			'path':{required:false,type:'string'}
		}
		settings = this.validate(settings,expectedParams);
		this.add(settings);
	},

	/*
	 * @TODO 根据提供的数据更新Folder
	 * @param:{path,[title?],[shortTitle?],[layoutDecorator?],[portletDecorator?],[defaultPage?],[hidden?]}
	 * path是必须参数,用于指定目标Folder路径,格式为_user/liuxiaotian
	 * 其它参数都为可选,用于指定目标Folder更新的属性
	 */
	updateFolder : function(settings){
		var expectedParams = {
			'path': {required: true,type: 'string'},
			'title': {required: false,type: 'string'},
			'shortTitle': {required: false,type: 'string'},
			'layoutDecorator': {required: false,type: 'string'},
			'portletDecorator': {required: false,type: 'string'},
			'defaultPage': {required: false,type: 'string'},
			'hidden': {required: false,type: 'boolean'}
		}
		settings = this.validate(settings,expectedParams);
		this.update(settings);
	},
	
	/*
	 * @TODO 根据path删除Folder
	 * @param: {path}
	 * path是必须参数,用于指定目标Folder路径,格式为_user/liuxiaotian
	 */
	removeFolder : function(settings){
		this.remove(settings);
	},
	
	/*
	 * @TODO 根据path复制Folder
	 * @param	data: {path,resourceName,destination}
	 * path是必须参数,用于指定目标Folder路径,格式为_user/liuxiaotian
	 * destination是必选参数,作用:目标的Folder的路径 destination值格式是: /_user
	 * resourceName是可选参数,作用:指定目标Folder名如果没有指定,默认为字符串null
	 * 最终被复制的Folder路径是:destination/resourceName
	 * 无法指定title、shortTitle等属性
	 */
	copyFolder : function(settings){
		var expectedParams = {
			'resourceName': {required: false,type: 'string'},
			'destination': {required: true,type: 'string'},
	        'path': {required: true,type: 'string'},
			'title': {required: false,type: 'string'}
		}
		settings = this.validate(settings,expectedParams);
		this.copy(settings);
	},
	
	/*
	 * @TODO 将path指定Folder移动到目标Folder
	 * @param	data: {path,resourceName,destination}
	 * path是必须参数,用于指定目标Folder路径,格式为_user/liuxiaotian
	 * destination是必选参数,作用:目标的Folder的路径 destination值格式是: /_user
	 * resourceName是可选参数,作用:指定目标Folder名如果没有指定,默认为字符串null
	 * 最终被复制的Folder路径是:destination/resourceName
	 * 无法指定title、shortTitle等属性
	 */
	moveFolder : function(settings){
		var expectedParams = {
			'path': {required: true,type: 'string'},
			'destination': {required: true,type: 'string'},
			'resourceName': {required: false,type: 'string'}
		}
		settings = this.validate(settings,expectedParams);
		this.move(settings);
	}

});

APS.AjaxMenuAction = {};

jQuery.extend(APS.AjaxMenuAction,APS.AjaxAction,{
	/*
	 * @TODO Get menu action retrieves a menu defined for the addressed page
	 * @param data:{name} name是必须参数
	 */
	getMenu : function(settings){
		var expectedParams = {
			'menuName':{required:true,type:'string'}
		}
		settings = this.validate(settings,expectedParams);
    	return this.executeAction(settings,'getmenu',null);
	},

	/*
	 * @TODO Get menus action retrieves all menu names defined for the addressed
	 * page @param data:{[includeMenuDefs]} includeMenuDefs是可选参数
	 */
	getMenus : function(settings){
		// param
        var expectedParams = {
            'includeMenuDefs':{required:false,type:'string'}
        }
        settings = this.validate(settings,expectedParams);
        return this.executeAction(settings,'getmenus',null);
	}
});

APS.AjaxUserAction = {};

jQuery.extend(APS.AjaxUserAction,APS.AjaxAction,{
	/*
	 * @Todo Retrieve user information of the current user
	 */
	getUserInfo: function(settings){
		settings=this.initSettingParam(settings);
        return this.executeAction(settings,'getuserinfo',null);
	},

	/*
	 * @TODO returns the list of currently logged in users and optionally also
	 * the offline users and number of guest user sessions
	 * @param:{guest,userinfo,offline,all}
	 */
	getUserList : function(settings){
		var expectedParams = {
			'guest':{required:false,type:'boolean'},
			'userInfo':{required:false,type:'boolean'},
			'offline':{required:false,type:'boolean'},
			'all':{required:false,type:'boolean'}
		}
		settings = this.validate(settings,expectedParams);
		return this.executeAction(settings,'getuserlist',null);
	}
});

APS.AjaxUserPreferenceAction = {};

jQuery.extend(APS.AjaxUserPreferenceAction,APS.AjaxAction,{
	getUserPreferences: function(settings){
		var expectedParams  = {
			"resourceId": {required:true, type:"string"},
			"userName": {required:true, type:"string"},
			"key": {required:false, type:"string"}
		}
		settings = this.validate(settings,expectedParams);
		return this.executeAction(settings, "getuserpreferences", null);
	},
	updateUserPreference: function(settings){
		var expectedParams  = {
			"resourceId": {required:true, type:"string"},
			"userName": {required:true, type:"string"},
			"key":{required:true, type:"string"},
			"value":{required:true, type:"string"},
			"mode":{required:true, type:"string"}// 指定新增偏好的模式，覆盖或者连续增加
		}
		settings = this.validate(settings,expectedParams);
		return this.executeAction(settings, "updateuserpreference", null);
	}
});
/*
 * @Todo AJAX Parameters: action = constraints method = add-def | update-def |
 * remove-def | add-global | remove-global name = name of constraint definition
 * or global definition xml = the constraints payload, same format as PSML
 * constraint defs
 */
APS.AjaxSecurityConstraintsAction = {};

jQuery.extend(APS.AjaxSecurityConstraintsAction,APS.AjaxAction,{
	execute:function(method,settings){
        settings.data.method=method;
		return this.executeAction(settings,'constraints',null);
	},

	/*
	 * @TODO @param:{name,xml}
	 */
	addDef:function(settings){
		var expectedParams = {
			'name':{required:true,type:'string'},
			'xml':{required:true,type:'string'}
		}
		settings = this.validate(settings,expectedParams);
		return this.execute('addDef',settings);
	},

	/*
	 * @TODO @param:{name}
	 */
	removeDef:function(settings){
		var expectedParams = {
			'name':{required:true,type:'string'}
		}
		settings = this.validate(settings,expectedParams);
		return this.execute('removeDef',settings);
	},

	/*
	 * @TODO @param:{xml}
	 */
	updateDef:function(settings){
		var expectedParams= {
				'xml':{required:true,type:'string'}
		}
		settings = this.validate(settings,expectedParams);
		return this.execute('updateDef',settings);
	},

	/*
	 * @TODO @param:{name}
	 */
	addGlobal:function(settings){
		var expectedParams = {
			'name':{required:true,type:'string'}
		}
		settings = this.validate(settings,expectedParams);
		return this.execute('addGlobal',settings);
	},

	/*
	 * @TODO @param:{name}
	 */
	removeGlobal:function(settings){
		var expectedParams = {
			'name':{required:true,type:'string'}
		}
		settings = this.validate(settings,expectedParams);
		return this.execute('removeGlobal',settings);
	}
});

/* AjaxDecorationAction 对应后台的GetThemesAction */
/*
 * @Todo Get Portal-wide themes lists @param:(pageDecorations,
 * portletDecorations, layouts, desktopPageDecorations,
 * desktopPortletDecorators)
 */
APS.AjaxDecorationAction = {};

jQuery.extend(APS.AjaxDecorationAction,APS.AjaxAction,{
	/*
	 * @TODO @param:{[type]}
	 */
	getDecorations : function(settings){
        var expectedParams = {
            'type': {required: false,type: 'string'},
            'name': {required: false,type: 'string'}
        }
        settings = this.validate(settings,expectedParams);
        return this.executeAction(settings,'getthemes',null);
	}
});

/* AjaxMultipleAction的实现完善，待重构 */
/*
 * The purpose of this object is to run several AJAX actions and aggregate the
 * results into a single response. The sample request URL is shown below:
 * http://host:port/ajaxapi?action=multiple&commands=(action;name,value,name,value)(action;name,value)
 * 
 * aps: @author:yuangxingqin 如果某个参数的值为空，可以传入含空格的字符串" "， 或者不传入这个参数和它的值
 */
APS.AjaxMultipleAction = {};

jQuery.extend(APS.AjaxMultipleAction,APS.AjaxAction,{
	/*
	 * @TODO @param:{commads}
	 */
	aggregate : function(settings){
		var expectedParams = {
			'commands':{required:true,type:'string'}
		}
		settings = this.validate(settings,expectedParams);
		return this.executeAction(settings,'multiple',null);
	}
});