/**
 * http://www.baihang-china.com/
 * Author: dingwei 2013-11-19
 * Description: banger.tabs.js
 * Modified by: 
 * Modified contents: 
 */
/*
(function($){
	
	// 样式
	var style = {
		active: 'ui-tabs-active',
		load: 'ui-tabs-load'
	};
	
	// 共有的
	var shared = {
		// 获取页卡头容器宽度
		getContainerWidth: function(oHead){
			return oHead.parent().width();
		},
		// 获取可见页卡的总宽度
		getVisibleItemsWidth: function(oItems){
			var total = oItems.filter(':visible').length, width = total * 122 - (total - 1);
			return width;
		},
		// 获取页面路径
		getUrl: function(urls, index){
			var url = urls[index];
			if(url != null){
				switch(typeof url){
					case 'string':
						url = url;
						break;
					case 'function':
						url = url();
						break;
					default:
						url = null;
						break;
				}
				// 页面路径必须为字符串格式
				if(url && typeof url === 'string'){
					// 添加随机数参数以防止缓存
					url = modifyUrl(url, 'random', Math.random());
				}
			}
			return url;
		},
		// 加载页面
		load: function(page, url, repeat){
			if(page.attr('isloaded') === undefined){
				if(url){
					page.addClass(style.load).load(url, function(){
						$(this).removeClass(style.load);
						if(!repeat){
							$(this).attr('isloaded', '1');
						}
					});
				}
			}
		},
		// 显示指定页卡
		show: function(item, page){
			// 设置头部区域样式
			item.addClass(style.active).siblings().removeClass(style.active);
			// 设置内容区域显隐
			page.css('display', 'block').siblings().css('display', 'none');
		},
		// 静态加载
		staticLoad: function(oItems, oPages, opts){
			// 显示指定选项卡
			var index = opts.defaultShowPage, item = oItems.eq(index), page = oPages.eq(index);
			this.show(item, page);
		},
		// 单个加载
		batchLoad: function(oItems, oPages, opts){
			// 显示指定选项卡
			var index = opts.defaultShowPage, item = oItems.eq(index), page = oPages.eq(index);
			this.show(item, page);
			// 加载默认显示的选项卡
			var url = this.getUrl(opts.urls, index);
			this.load(page, url, opts.repeatLoad);
		},
		// 全部加载
		fullLoad: function(oItems, oPages, opts){
			// 显示指定选项卡
			var index = opts.defaultShowPage, item = oItems.eq(index), page = oPages.eq(index);
			this.show(item, page);
			// 循环加载所有选项卡
			var i = 0, length = oPages.length;
			for(; i < length; i++){
				var page = oPages.eq(i), url = this.getUrl(opts.urls, i);
				this.load(page, url, opts.repeatLoad);
			}
		}
	};
	
	$.fn.tabs = function(opts){
		opts = $.extend({}, {
			urls: null,
			defaultShowPage: 0,
			autoLoad: 'single',
			repeatLoad: false,
			slidingSpeed: 200,
			beforeOnClick: null,
			onClick: null
		}, opts);
		
		return this.each(function(){
			var oHead = $('ul[ishead]', this), oItems = oHead.children('li'), oBody = $('div[isbody]', this), oPages = oBody.children('div');
			// 如果urls为 null，表示页卡指向的页面已写死，不由urls地址集合控制加载
			if(opts.urls == null){
				shared.staticLoad(oItems, oPages, opts);
			}else{
				// 首次加载时是加载单个还是所有选项卡
				if(opts.autoLoad == 'single'){ // 单个
					shared.batchLoad(oItems, oPages, opts);
				}else if(opts.autoLoad == 'all'){ // 所有
					shared.fullLoad(oItems, oPages, opts);
				}
			}
			//选项卡切换
			oItems.click(function(){
				var item = $(this), index = oItems.index(this), page = oPages.eq(index);
				//判断当前点击的选项卡是否已处于激活状态
				var flag = item.hasClass(style.active);
				//非激活状态下进入
				if(!flag){
					if(opts.beforeOnClick != null && typeof opts.beforeOnClick === 'function'){
						var cItem = oItems.filter('.' + style.active), cPage = oPages.eq(oItems.index(cItem));
						if(!opts.beforeOnClick(cItem, cPage)){
							return false;
						}
					}
					// 显示指定选项卡
					shared.show(oItems.eq(index), oPages.eq(index));
					if(opts.urls != null){
						// 加载当前点击的选项卡
						shared.load(page, shared.getUrl(opts.urls, index), opts.repeatLoad);
					}
				}
				// 点击回调函数
				if(opts.onClick != null && typeof opts.onClick === 'function'){
					opts.onClick(item, page);
				}
			});
			var oLeft = $('b[isleft]'), oRight = $('b[isright]');
			// 向右滚动
			oLeft.click(function(){
				var w1 = shared.getContainerWidth(oHead); // 获取容器宽度
				var w2 = shared.getVisibleItemsWidth(oItems); // 获取可见页卡总宽度
				// 当容器宽度 < 可见页卡总宽度时执行滚动
				if(w1 < w2){
					var left = oHead.position().left;
					// 当页卡未滚动到首个页卡且当前不在滚动过程中执行滚动
					if(left < 0 && !oHead.is(':animated')){
						oHead.animate({ left: '+=' + 121 + 'px' }, opts.slidingSpeed);
					}
				}
			});
			// 向左滚动
			oRight.click(function(){
				var w1 = shared.getContainerWidth(oHead); // 获取容器宽度
				var w2 = shared.getVisibleItemsWidth(oItems); // 获取可见页卡总宽度
				// 当容器宽度 < 可见页卡总宽度时执行滚动
				if(w1 < w2){
					var left = oHead.position().left;
					// 当页卡未滚动到最后一个页卡且当前不在滚动过程中执行滚动
					if((w1 < (w2 + left)) && !oHead.is(':animated')){
						oHead.animate({ left: '-=' + 121 + 'px' }, opts.slidingSpeed);
					}
				}
			});
		});
	};
})(jQuery);
*/

(function($){
    var shared = {
        // 获取url地址
        modifyUrl: function(url){
            if(url){
                switch(typeof url){
                    case 'string':
                        url = url;
                        break;
                    case 'function':
                        url = url();
                        break;
                    default:
                        url = null;
                        break;
                }
                // 页面路径必须为字符串格式
                if(isString(url)){
                    // 添加随机数参数防止缓存
                    url = modifyUrl(url, 'random', Math.random());
                    return url;
                }
            }
            return '';
        },
        // 获取页卡头容器宽度
        getHeadContainerWidth: function(head){
            return head.parent().width();
        },
        // 获取可见页卡的总宽度
        getVisibleItemsWidth: function(lis){
            var total = lis.filter(':visible').length, width = total * 71;
            return width;
        }
    };

    $.fn.tabs = function(o){
        o = $.extend({}, {
            items: null,
            defaultShowPage: 0,
            slidingSpeed: 200,
            beforeOnSwitch: null,
            onSwitch: null
        }, o);

        return this.each(function(){
            // 获取页卡头容器、页卡躯体容器
            var head = $(this).find('.ui-tabs-items ul').eq(0), body = $(this).find('.ui-tabs-iframes').eq(0);

            // 添加动态页卡项
            if(o.items){
                var items = [], iframes = [];
                for(var i = 0, len = o.items.length; i < len; i++){
                    var item = o.items[i];
                    if(item){
                        items.push('<li item="' + i + '"><h4>' + item.display + '</h4></li>');
                        iframes.push('<div class="ui-tabs-iframe"></div>');
                    }
                }
                head.append(items.join(''));
                body.append(iframes.join(''));
            }

            // 获取页卡项集合
            var lis = head.children('li'), divs = body.children('div');

            // 预加载
            for(var i = 0, len = lis.length; i < len; i++){
                var li = lis.eq(i), idx = li.attr('item'), div = divs.eq(i), title = li.find('h4').text();
                li.attr('title', title);
                if(idx === undefined){
                    continue;
                }
                var item = o.items[idx];
                if(item.loadType == 'window.load'){
                    (function(li, div, item){
                        div.load(shared.modifyUrl(item.url), function(){
                            if(isFunction(item.loadReady)){
                                item.loadReady(li.get(0), div.get(0));
                            }
                        });
                    })(li, div, item);
                }
            }

            $(this)
            // 注册页卡切换事件
            .on('click', '.ui-tabs-items:first li', function(e){
                var i = lis.index(this), li = lis.eq(i), idx = li.attr('item'), div = divs.eq(i), flag = true;

                // 切换前回调函数
                if(isFunction(o.beforeOnSwitch)){
                    flag = o.beforeOnSwitch(this, div.get(0));
                }

                // 切换时回调函数
                if(isFunction(o.onSwitch)){
                    flag = o.onSwitch(this, div.get(0));
                }

                if(flag !== false){
                    // 切换显隐状态
                    $(this).addClass('ui-tabs-active').siblings().removeClass('ui-tabs-active');
                    div.css('display', 'block').siblings().css('display', 'none');

                    if(idx){
                        var item = o.items[idx];
                        if(item.loadType == 'tab.click'){
                            var isLoaded = div.attr('isloaded');
                            if(!isLoaded){
                                div.load(shared.modifyUrl(item.url), function(){
                                    if(isFunction(item.loadReady)){
                                        item.loadReady(li.get(0), this);
                                    }
                                    if(!item.refreshOnClick){
                                        $(this).attr('isloaded', true)
                                    }
                                });
                            }
                        }
                    }
                }

                // 阻止事件冒泡
                e.stopPropagation();
            })
            // 向左滚动
            .on('click', '.ui-tabs-action-right:first', function(e){
                var w1 = shared.getHeadContainerWidth(head), w2 = shared.getVisibleItemsWidth(lis);

                // 当页卡头容器宽度 < 可见页卡总宽度时执行滚动
                if(w1 < w2){
                    var left = head.position().left;

                    // 当页卡未滚动到最后一个页卡且当前不在滚动过程中执行滚动
                    if((w1 < (w2 + left)) && !head.is(':animated')){
                        head.animate({ left: '-=' + 71 + 'px' }, o.slidingSpeed);
                    }
                }

                // 阻止事件冒泡
                e.stopPropagation();
            })
            // 向右滚动
            .on('click', '.ui-tabs-action-left:first', function(e){
                var w1 = shared.getHeadContainerWidth(head), w2 = shared.getVisibleItemsWidth(lis);

                // 当页卡头容器宽度 < 可见页卡总宽度时执行滚动
                if(w1 < w2){
                    var left = head.position().left;
                    // 当页卡未滚动到首个页卡且当前不在滚动过程中执行滚动
                    if(left < 0 && !head.is(':animated')){
                        head.animate({ left: '+=' + 71 + 'px' }, o.slidingSpeed);
                    }
                }

                // 阻止事件冒泡
                e.stopPropagation();
            });

            // 激活指定的页卡
            lis.eq(o.defaultShowPage).click();
        });
    };
})(jQuery);
