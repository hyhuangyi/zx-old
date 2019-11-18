/**
 * http://www.baihang-china.com/
 * Author: dingwei 2013-11-04
 * Description: banger.flexigrid.js
 * Modified by: 
 * Modified contents: 
**/
(function($){
	// jQuery 1.9 support, browser object has been removed in 1.9.
	var browser = $.browser;
	if(!browser){
		function userAgentMatch(ua){
			ua = ua.toLowerCase();
			var match = /(chrome)[ \/]([\w.]+)/.exec(ua) || /(webkit)[ \/]([\w.]+)/.exec(ua) || /(opera)(?:.*version|)[ \/]([\w.]+)/.exec(ua) || /(msie) ([\w.]+)/.exec(ua) || ua.indexOf('compatible') < 0 && /(mozilla)(?:.*? rv:([\w.]+)|)/.exec(ua) || [];
			return {
				browser: match[1] || '',
				version: match[2] || '0'
			};
		};
		var matched = userAgentMatch(navigator.userAgent);
		browser = {};
		if(matched.browser){
			browser[matched.browser] = true;
			browser.version = matched.version;
		}
		// Chrome is Webkit, but Webkit is also Safari.
		if(browser.chrome){
			browser.webkit = true;
		}else if(browser.webkit){
			browser.safari = true;
		}
	}
	
	/**
     * Start code from jQuery ui
     *
     * Copyright 2011, AUTHORS.txt (http://jqueryui.com/about)
     * Dual licensed under the MIT or GPL Version 2 licenses.
     * http://jquery.org/license
     * 
     * http://docs.jquery.com/UI
     */
    if(typeof $.support.selectstart != 'function'){
        $.support.selectstart = 'onselectstart' in document.createElement('div');
    }
    if(typeof $.fn.disableSelection != 'function'){
        $.fn.disableSelection = function(){
            return this.bind(($.support.selectstart ? 'selectstart' : 'mousedown') + '.ui-disableSelection', function(e){
                e.preventDefault();
            });
        };
    }
   
   // DOM
   function D(){
		var a = arguments, selector = a[0], doc = a[1];
		if(this == window){
			return new D(selector, doc);
		}
		if(selector.nodeName){
			this.node = selector;
		}else{
			var chr = selector.charAt(0);
			if(chr == '#'){
				selector = selector.substr(1);
				this.node = document.getElementById(selector);
			}else if(chr == '.'){
				var nodes = (doc || document).getElementsByTagName('*'), len = nodes.length;
				selector = selector.substr(1);
				for(var i = 0; i < len; i++){
					var node = nodes[i];
					if(D(node).hasClass(selector)){
						if(!this.node){
							this.node = [node];
						}else{
							this.node.push(node);
						}
					}
				}
			}else{
				this.node = (doc || document).getElementsByTagName(selector);
			}
		}
	};
	// 创建元素
	D.create = function(selector){
		return document.createElement(selector);
	};
	// 去除左右空格
   	D.prototype.trim = function(str){
		return str == null ? '' : str.toString().replace(/^\s+/, '').replace(/\s+$/, '');
	};
	// 获取或设置元素innerHTML
	D.prototype.html = function(html){
		if(html !== undefined){
			this.node.innerHTML= html;
			return this;
		}
		return this.node.innerHTML;
	};
	// 获取或设置元素的style样式属性
	D.prototype.style = function(key, value){
		var a = arguments, len = a.length, key = a[0], value = a[1];
		if(len == 1){
			var type = typeof key;
			if(type == 'string'){
				if(this.node.style[key]){
					return this.node.style[key];
				}else if(this.node.currentStyle){
			        return this.node.currentStyle[key];
			    }else if(document.defaultView && document.defaultView.getComputedStyle){
			        style = key.replace(/([A-Z])/g, '-$1').toLowerCase();
			        return document.defaultView.getComputedStyle(this.node, null).getPropertyValue(key);
			    }
			    return null;
			}else if(type == 'object'){
				for(var i in key){
					this.node.style[i] = key[i];
				}
				return this;
			}
		}else{
			this.node.style[key] = value;
			return this;
		}
	};
	// 获取或设置元素的标签属性
	D.prototype.attr = function(){
		var a = arguments, key = a[0], value = a[1];
		if(a.length == 2){
			this.node.setAttribute(key, value);
			return this;
		}else if(a.length == 1){
			var type = typeof key;
			if(type == 'string'){
				return this.node.getAttribute(key);
			}else if(type == 'object'){
				for(var i in key){
					this.node.setAttribute(i, key[i]);
				}
				return this;
			}
		}
	};
	// 移除元素属性
	D.prototype.removeAttr = function(attrs){
		var keys = attrs.split(' '), i = 0, len = keys.length;
		for(; i < len; i++){
			this.node.removeAttribute(keys[i]);
		}
		return this;
	};
	// 获取父节点
	D.prototype.parent = function(){
		return (this.node && this.node.parentNode) ? this.node.parentNode : null;
	};
	// 获取同辈元素
	D.prototype.siblings = function(){
		var nodes = [], p = this.node.parentNode, child;
		if(p){
			child = p.childNodes;
			if(child && child.length && child.length > 0){
				for(var i = 0, l = child.length; i < l; i++){
					var node = child[i];
					if(node.nodeType === 1 && node !== this.node){
						nodes.push(node);
					}
				}
			}
		}
		return nodes;
	};
	// 获取第一个子节点
	D.prototype.firstChild = function(){
		var first = null;
		if(!this.node || !this.node.firstChild){
			return first;
		}
		first = this.node.firstChild;
		while(first && first.nodeType !== 1){
			first = first.nextSibling;
		}
		return first;
	};
	// 查找最后一个子节点
	D.prototype.lastChild = function(){
		var last = null;
		if(!this.node || !this.node.lastChild){
			return last;
		}
		last = this.node.lastChild;
		while(last && last.nodeType !== 1){
			last = last.previousSibling;
		}
		return last;
	};
	// 添加子节点
	D.prototype.append = function(node){
		if(typeof node == 'string'){
			this.node.innerHTML += node;
		}else{
			this.node.appendChild(node);
		}
		return this;
	};
	// 前置新节点
	D.prototype.prepend = function(node){
		if(typeof node == 'string'){
			this.node.innerHTML = node + this.node.innerHTML;
		}else{
			this.node.insertBefore(node, this.firstChild());
		}
		return this;
	};
	// 移除节点
	D.prototype.remove = function(){
		var parent = this.parent();
		parent.removeChild(this.node);
	};
	// 清空子节点
	D.prototype.empty = function(){
		while(this.node.firstChild){
			this.node.removeChild(this.node.firstChild);
		}
		return this;
	};
	// 将一个新节点添加到指定节点之前
	D.prototype.before = function(node){
		var parent = this.parent();
		if(parent){
			if(node && node.nodeName){
				var first = D(parent).firstChild();
				parent.insertBefore(node, first);
			}
		}
		return this;
	};
	// 判断元素是否含有指定的类名
	D.prototype.hasClass = function(value){
		if(this.node && this.node.nodeType === 1 && (' ' + this.node.className + ' ').replace(/[\n\t\r]/g, ' ').indexOf(' ' + value + ' ') > - 1){
			return true;
		}
		return false;
	};
	// 添加样式类
	D.prototype.addClass = function(value){
		if(value && typeof value === 'string'){
			var cs = value.split(/\s+/);
			if(this.node && this.node.nodeType === 1){
				if(!this.node.className && cs.length === 1){
					this.node.className = value;
				}else{
					var dc = ' ' + this.node.className + ' ';
					for(var i = 0, len = cs.length; i < len; i++){
						if(!~dc.indexOf(' ' + cs[i] + ' ')){
							dc += cs[i] + ' ';
						}
					}
					this.node.className = this.trim(dc);
				}
			}
		}
		return this;
	};
	// 移除类名
	D.prototype.removeClass = function(value){
		if((value && typeof value === 'string') || value === undefined){
			var cs = (value || '').split(/\s+/);
			if(this.node && this.node.nodeType === 1 && this.node.className){
				if(value){
					var dc = (' ' + this.node.className + ' ').replace(/[\n\t\r]/g, ' ');
					for(var i = 0, len = cs.length; i < len; i++ ){
						dc = dc.replace(' ' + cs[i] + ' ', ' ');
					}
					this.node.className = this.trim(dc);
				}else{
					this.node.className = '';
				}
			}
		}
		return this;
	};
	// 切换类名
	D.prototype.toggleClass = function(value){
		if(this.hasClass(value)){
			this.removeClass(value);
		}else{
			this.addClass(value);
		}
	};
	
	// 判断是否为ie6浏览器
	var isIe6 = !-[1,] && !window.XMLHttpRequest;
	
	// 获取变量类型
	var getType = function(obj){
		return Object.prototype.toString.call(obj);
	};
   
   // 主函数
   $.addFlex = function(t, o){
   		
		if(t.flexigrid){
			return false;
		}
		
		// 扩展自定义配置
		o = $.extend({
			height: 'auto', // 高度
			width: 'auto', // 宽度
			resizable: false, // 是否允许调整表格尺寸
			heightResizable: true, // 是否允许调整表格高度
			widthResizable: true, // 是否允许调整表格宽度
			striped: true, // 是否应用隔行换色效果
			nowrap: true, // 单元格内文本是否不换行
			multiSelect: false, // 是否启用多选
            colResizable: true, // 是否允许调整列宽
            colMovable: true, // 是否允许拖动列，即调整列顺序
			colMinWidth: 30, // 最小列宽
			data: null, // 数据集合
			autoLoad: true, // 自动加载
			url: false, // 请求地址
			method: 'POST', // 请求方式
			params: null, // 请求参数
			dataType: 'json', // 数据类型
			loadingTips: '数据加载中', // 加载数据时的文本提示
			noDataTips: '未搜索到匹配项',
			total: 0, // 共多少条数据
			page: 1, // 当前页码
			pages: 1, // 共多少页
			rowIdProperty: 'id', // 行id属性
			usePage: false, // 是否使用分页
			useRp: true, // 是否使用每页显示多少条数据的条件选项
			rpOptions: [10, 15, 20, 25, 30, 50], // 每页显示多少条数据的条件选项
			defaultRp: 15, // 默认每页显示多少条数据
			preProcessData: null, // 预处理数据
			onSortChange: null, // 排序事件处理函数
			onDoubleClick: null, // 行双击事件处理函数
			onError: null, // 请求出错时的处理函数
			onComplete: null, // 请求完成时的处理函数
            extendGridClass: function(g){ // 扩展g公共类
                return g;
            },
			// 
            colBuilder: {
            	all: null
            },
            // 
            factory: {
                extendDataCol: function(o, field, col){
                    col = (typeof o.colBuilder[field] == 'function') ? o.colBuilder[field](col) : col;
                    if(typeof o.colBuilder['all'] == 'function'){
                        return o.colBuilder['all'](col);
                    }else{
                        return col;
                    }
                }
            }
		}, o);
		
		// 创建g公共类
		var g = {
			// 
			data: null,
			// 判断是否为ie6浏览器
			isIe6: !-[1,] && !window.XMLHttpRequest,
			// 获取变量类型
			getType: function(obj){
				return Object.prototype.toString.call(obj);
			},
			// 设置相关标签高度
			fixHeight: function(){
				var hBox = $(this.box).outerHeight(), hHead = $(this.head).outerHeight(), hBody = $(this.body).outerHeight();
				// 设置调节网格宽度的标签高度
				if(o.height != 'auto' && o.resizable && o.widthResizable){
					$(this.widthResizer).css({ 'height': hBox });
				}
				// 设置数据加载遮罩层的高度
				$(this.load).css({ 'height': hBody - 1 });
				// 设置调节列宽的标签高度
				var hDrag = hHead + hBody;
				$('div', this.drag).each(function(){
					$(this).height(hDrag);
				});
			},
			// 设置调节列宽的标签位置
			setDragerPosition: function (){
				$(g.drag).css({
					'top': this.head.offsetTop
				});
				var ths = $('thead tr th:visible', this.head), scrollLeft = this.head.scrollLeft, cellLeft = -scrollLeft, cellPatch = this.cellPatch;
				if(scrollLeft > 0){
					cellLeft -= Math.floor(o.dgwidth / 2);
				}
				$('div', g.drag).hide();
				ths.each(function(){
					var i = ths.index(this), cellPos = parseInt($('div', this).width());
					if(cellLeft == 0){
						cellLeft -= Math.floor(o.dgwidth / 2);
					}
					cellPos = cellPos + cellLeft + cellPatch;
					if(isNaN(cellPos)){
						cellPos = 0;
					}
					$('div:eq(' + i + ')', g.drag).css({
						'left': cellPos + 'px'
					}).show();
					cellLeft = cellPos;
				});
			},
			// 
			addCellProp: function(){
				var ths = D('th', this.head).node, trs = D('tr', this.body).node, trsLen = trs.length;
				for(var i = 0; i < trsLen; i++){
					var tr = trs[i], tds = D('td', tr).node, tdsLen = tds.length;
					for(var j = 0; j < tdsLen; j++){
						var th = ths[j], thInner = D('div', th).node[0], td = tds[j], tdInner = D.create('div');
						if(th){
							if(o.sortField && D(th).attr('sort') == o.sortField){
								D(td).addClass('sort');
							}
							if(D(th).attr('hide')){
								D(td).style('display', 'none');
							}
							D(tdInner).style({ 'width': D(thInner).style('width'), 'textAlign': D(th).attr('align') });
						}
						// 超过部分折行
						if(o.nowrap == false){
							D(tdInner).style('white-space', 'normal');
						}
						D(tdInner).html(D(td).html());
						D(td).removeAttr('width').empty().append(tdInner);
						g.addTitleToCell(div);
					}
				}
			},
			// 添加单元格title属性
			addTitleToCell: function(div){
				if(o.addTitleToCell){
					$(div).attr('title', $(div).text());
				}
			},
			// 
			addGridProp: function(){
				// 
				if(o.multiSelect){
					$('table', g.head).unbind().on('click.input', 'input[mark="check"]', function(e){
						var flag = $(this).attr('checked') ? true : false;
						$(':checkbox[mark="check"]:enabled', g.body).attr('checked', flag).parents('tr')[flag ? 'addClass' : 'removeClass']('selected');
					});
				}
				
				// 
				$('table', g.body).unbind().on('click', 'tr', function(e){
					// 获取事件源对象
					var tr = this, elem = e.target || e.srcElement;
					
					var $elem = $(elem), mark = $elem.attr('mark');
					
					switch(mark){
						// 操作按钮
						case 'action':
							var di = $elem.attr('data'), bi = $elem.attr('button');
							if(o.action && o.action.buttons){
								var btn = o.action.buttons[bi];
								if(btn && btn.onClick && typeof btn.onClick == 'function'){
									btn.onClick(g.data.rows[di], tr);
								}
							}
							break;
						// 复选按钮
						case 'check':
							var checked = $elem.attr('checked');
							$(tr)[checked ? 'addClass' : 'removeClass']('selected');
							var len1 = $(':checkbox[mark="check"]:enabled:checked', g.body).length, len2 = $(':checkbox[mark="check"]:enabled', g.body).length;
							if(len1 == len2){
								$(':checkbox[mark="check"]', g.head).attr('checked', 'checked');
							}else{
								$(':checkbox[mark="check"]', g.head).removeAttr('checked');
							}
							break;
						default: break;
					}
					
					if(!o.multiSelect){
						$(tr).addClass('selected').siblings().removeClass('selected');
					}
					
					// 自定义单击事件
					if(o.onRowClick && typeof o.onRowClick == 'function'){
						o.onRowClick(this, g);
					}
				});
				
				if(g.isIe6){
					$('tbody tr', g.body).hover(function(e){
						$(this).addClass('hover');
					}, function(){
						$(this).removeClass('hover');
					});
				}
			},
			// 去除列头内全选按钮的选中状态
			cleanChecked: function(){
				$(':checkbox[mark="check"]', g.head).attr('checked', false);
			},
			// 横向滚动
			scroll: function(){
				this.head.scrollLeft = this.body.scrollLeft;
				this.setDragerPosition();
			},
			// 搜索
			doSearch: function(params){
				o.params = $.extend(o.params, params);
				o.newPage = 1;
				this.populate();
			},
			// 排序
			changeSort: function(th){
				// 如果正在加载则返回不处理
				if(this.isLoading){
					return false;
				}
				var $th = $(th);
				if($th.attr('sort') == o.sortField){
					o.sortType = (o.sortType == 'asc') ? 'desc' : 'asc';
				}
				o.sortField = $th.attr('sort');
				$th.addClass('sort').siblings().removeClass('sort');
				$('.asc', this.head).removeClass('asc');
				$('.desc', this.head).removeClass('desc');
				$('div', th).addClass(o.sortType);
				if(o.onSortChange && typeof o.onSortChange == 'function'){ // 自定义排序
					o.onSortChange(o.sortField, o.sortType);
				}else{
					this.populate();
				}
			},
			// 分页
			changePage: function(type){
				// 如果正在加载则返回不处理
				if(this.isLoading){
					return false;
				}
				switch(type){
					case 'first': // 首页
						o.newPage = 1;
						o.url = 'data' + o.newPage + '.txt';
						break;
					case 'prev': // 上一页
						if(o.page > 1){
							o.newPage = parseInt(o.page, 10) - 1;
						}
						o.url = 'data' + o.newPage + '.txt';
						break;
					case 'next': // 下一页
						if(o.page < o.pages){
							o.newPage = parseInt(o.page, 10) + 1;
						}
						o.url = 'data' + o.newPage + '.txt';
						break;
					case 'last': //尾页
						o.newPage = o.pages;
						o.url = 'data' + o.newPage + '.txt';
						break;
					case 'jump':
						var num = parseInt($('.page', this.page).val(), 10);
						if(isNaN(num)){
							num = 1;
						}
						if(num < 1){
							num = 1;
						}else if(num > o.pages){
							num = o.pages;
						}
						$('.page', this.page).val(num);
						o.newPage = num;
						o.url = 'data' + o.newPage + '.txt';
						break;
				}
				// 
				if(o.onPageChange && typeof o.onPageChange == 'function'){
					o.onPageChange(o.newPage);
				}else{
					this.populate();
				}
			},
			// 填充数据
			populate: function(){
				// 
				if(o.multiSelect){
					this.cleanChecked();
				}
				// 若当前正在加载数据或url地址未定义则返回不处理
				if(this.isLoading || !o.url){
					return false;
				}
				// 标注当前正在加载数据
				this.isLoading = true;
				// 添加数据加载遮罩层
				$(this.body).after(this.load);
				// 
				if(!o.newPage){
					o.newPage = 1;
				}
				// 
				if(o.page > o.pages){
					o.page = o.pages;
				}
				// 定义请求参数
				var params = $.extend({ 'page': o.newPage, 'rp': o.defaultRp, 'sortfield': o.sortField, 'sorttype': o.sortType }, o.params);
				// 发起请求
				$.ajax({
					type: o.method,
					url: o.url,
					data: params,
					dataType: o.dataType,
					success: function(data){ // 成功
						g.addData(data);
					},
					error: function(XMLHttpRequest, textStatus, errorThrown){ // 失败
						try{
							if(o.onError && typeof o.onError == 'function'){
								o.onError(XMLHttpRequest, textStatus, errorThrown);
							}
						}catch(e){  }
					}
				});
			},
			// 解析数据
			addData: function(data){
				// 
				if(!data){
                    if(o.onComplete && typeof o.onComplete == 'function'){
                    	o.onComplete(this);
                    }
					return false;
				}
				// 预处理数据
				if(o.preProcessData && typeof o.preProcessData == 'function'){
					data = o.preProcessData(data);
				}
				// 
				data = $.extend({ total: 0, page: 0, rows: [] }, data);
				// 
				this.data = data;
				// 共多少条数据
				o.total = data.total;
				// 当前页码
				o.page = data.page;
				// 计算共多少页
				o.pages = Math.ceil(o.total / o.defaultRp);
				// 如果总数为零
				if(o.total == 0){
					D(t).empty();
					D(g.load).html('<span class="no">' + o.noDataTips + '</span>');
					o.page = 1;
					o.pages = 1;
					this.buildPager();
                    if(o.onComplete && typeof o.onComplete == 'function'){
                    	o.onComplete(this);
                    }
                    // 指示未在加载
					this.isLoading = false;
					return false;
				}
				// 
				this.buildPager();
				// 
				if(data.rows){
					// 创建tbody
					var tbody = D.create('tbody'), rows = data.rows, rowsLen = rows.length, cols = D('th', g.head).node, colsLen = cols.length;
					// 遍历创建行
					for(var i = 0; i < rowsLen; i++){
						var row = rows[i];
						if(row && row.cols){
							var tr = D.create('tr'), id = row[o.rowIdProperty];
							// 
							D(tr).attr('data', i);
							// 添加行id属性
							if(id){
								D(tr).attr({ 'id': 'flex' + id });
							}
							// 添加行name属性
							if(row.name){
								D(tr).attr('name', row.name);
							}
							// 添加行class属性
							if(row.style){
								D(tr).addClass(row.style);
							}
							// 隔行换色
							if(o.striped && i % 2){
								D(tr).addClass('odd');
							}
							// 遍历创建列
							for(var j = 0; j < colsLen; j++){
								var col = cols[j], td = D.create('td');
								// 设置单元格文本对齐方式
								D(td).attr('align', D(col).attr('align'));
								// 填充数据列
								if(D(col).attr('axis')){
									var idx = D(col).attr('axis'), cell = null;
									// Array数组格式
									if(isArray(row.cols)){
	                                    cell = row.cols[idx];
	                                }
	                                // Json对象格式
	                                else if(isPlainObject(row.cols)){
	                                    cell = row.cols[o.fields[idx].field];
	                                }
	                                cell = o.factory.extendDataCol(o, D(col).attr('sort'), cell);
	                                D(td).html(cell || '');
								}
								// 填充复选列
								else if(D(col).attr('col') == 'check'){
									var checked = '', disabled = '';
									if(row.checked === true){
										checked = ' checked="checked"';
									}
									if(row.checkable === false){
										disabled = ' disabled="disabled"';
									}
									D(td).html('<input type="checkbox"' + checked + disabled + ' class="ui-flexigrid-checkbox" mark="check" data="' + i + '" />');
								}
								// 填充操作列
								else if(D(col).attr('col') == 'action' && o.action && o.action.buttons){
									var btns = o.action.buttons, btnLen = btns.length;
									for(var k = 0; k < btnLen; k++){
										var btn = btns[k];
										if(btn){
											var label = D.create('label');
											D(label).addClass('ui-link mr5').attr({ 'mark': 'action', 'data': i, 'button': k }).html(btn.display);
											D(td).append(label);
										}
									}
								}
								D(tr).append(td);
							}
							D(tbody).append(tr);
						}
					}
				}
				D(t).empty().append(tbody);
				var ta = D(t).node;
				D(g.body).empty().append(ta);
				this.addCellProp();
				this.addGridProp();
				this.head.scrollLeft = this.body.scrollLeft;
				this.setDragerPosition();
				// 移除数据加载遮罩层
				D(g.load).remove();
				// 触发完成时的回调函数
				if(o.onComplete){
					o.onComplete(this);
				}
				// 指示未在加载
				this.isLoading = false;
			},
			// 重新构建分页栏
			buildPager: function(){
				// 修改当前页码
				$('.page', this.page).val(o.page);
				// 修改总页数
				$('.pages', this.page).text(o.pages);
			}
		};
		
		// 扩展g公共类
		g = o.extendGridClass(g);
		
		// 重置表格属性
		$(t).attr({ 'border': 0, 'cellPadding': 0, 'cellSpacing': 0 }).removeAttr('width');
		
		g.box = D.create('div'); // 创建全局容器
		g.menu = D.create('div'); // 创建菜单容器
		g.head = D.create('div'); // 创建表头容器
		g.colSwicth = D.create('div'); // 创建表头字段列表显示或隐藏的开关
		g.colList = D.create('div'); // 创建表头字段列表的容器
		g.body = D.create('div'); // 创建表容器
		g.load = D.create('div'); // 创建数据加载时的遮罩层
		g.drag = D.create('div'); // 创建列拖动按钮的容器
		g.page = D.create('div'); // 创建分页容器
		g.heightResizer = D.create('div'); // 创建调节grid高度的按钮容器
		g.widthResizer = D.create('div'); // 创建调节grid宽度的按钮容器
		
		// 填充表头
		if(o.fields){
			var thead = D.create('thead'), tr = D.create('tr');
			// 复选列列头
			if(o.multiSelect){
				var th = D.create('th');
				// 设置列宽、文本对齐方式等属性
				$(th).attr({ 'width': 20, 'align': 'center', 'col': 'check' }).html('<input type="checkbox" class="ui-flexigrid-checkbox" mark="check" />');
				$(tr).append(th);
			}
			// 数据列列头
			var i = 0, fields = o.fields, len = fields.length;
			for(; i < len; i++){
				var cell = fields[i];
				if(cell){
					var th = D.create('th');
					// 设置列宽、文本对齐方式，填充文本
					$(th).attr({ 'width': cell.width || 100, 'align': cell.align || 'left', 'axis': i, 'col': 'field' }).html(cell.display || '');
					// 
					if(cell.sortable && cell.field){
						$(th).attr('sort', cell.field);
					}
					$(tr).append(th);
				}
			}
			// 操作列列头
			if(o.action){
				var cell = o.action, th = D.create('th');
				// 设置列宽、文本对齐方式，填充文本
				$(th).attr({ 'width': cell.width || 100, 'align': cell.align || 'left', 'col': 'action' }).html(cell.display || '');
				$(tr).append(th);
			}
			$(thead).append(tr);
			$(t).prepend(thead);
		}
		
		// 创建全局容器
		var boxStyle = 'ui-flexigrid' + (browser.msie ? ' ie' : '') + (o.novstripe ? ' novstripe' : '');
		$(g.box).addClass(boxStyle).css('width', o.width);
		$(t).before(g.box);
		$(g.box).append(t);
		
		// 创建表头
		$(g.head).addClass('ui-flexigrid-head clearfix').html('<div class="ui-flexigrid-head-inner"><table border="0" cellpadding="0" cellspacing="0"></table></div>');
		$(t).before(g.head);
		var thead = $('thead', t);
		if(thead.length > 0){
			$('table', g.head).append(thead);
		}
		var i = 0, ths = $('tr:first th', g.head), len = ths.length;
		for(; i < len; i++){
			var th = ths[i], sort = $(th).attr('sort'), div = D.create('div');
			if(sort){
				// 如果当前列为默认排序的列，则添加排序样式
				if(sort == o.sortField){
					$(th).addClass('sort');
					$(div).addClass(o.sortType);
				}
				// click event
				$(th).click(function(){
					// 
					if(!$(this).hasClass('hover')){
						return false;
					}
					// 排序
					g.changeSort(this);
				});
			}
			// 设置宽度
			if(!$(th).attr('width')){
				$(th).attr('width', 100);
			}
			// 
			if(!o.fields){
				$(th).attr('axis', i);
			}
			// 隐藏该列
			if($(th).attr('hide')){
				$(th).style('display', 'none');
			}
			$(div).css({ 'width': $(th).attr('width'), 'textAlign': $(th).attr('align') || 'left' }).html($(th).html());
			$(th).empty().append(div).removeAttr('width').mousedown(function(e){
				// g.dragStart('colMovable', e, this);
			}).hover(function(e){
				// 
				if(!g.colresize && !$(this).hasClass('move') && !g.colCopy){
					$(this).addClass('hover');
				}
				// 
				if($(this).attr('sort') != o.sortField && !g.colCopy && !g.colresize && $(this).attr('sort')){
					$('div', this).addClass(o.sortType);
				}else if($(this).attr('sort') == o.sortField && !g.colCopy && !g.colresize && $(this).attr('sort')){
					var cls = (o.sortType == 'asc') ? 'desc' : 'asc';
					$('div', this).removeClass(o.sortType).addClass(cls);
				}
			}, function(e){
				$(this).removeClass('hover');
				// 
				var thSort = $(this).attr('sort');
				if(thSort != o.sortField){
					$('div', this).removeClass(o.sortType);
				}else{
					var cls = o.sortType == 'asc' ? 'desc' : 'asc';
					$('div', this).addClass(o.sortType).removeClass(cls);
				}
			});
		}
		
		// 创建表
		g.body.className = 'ui-flexigrid-body';
		$(t).before(g.body);
		$(g.body).css({
			height: (o.height == 'auto') ? 'auto' : o.height + 'px'
		}).scroll(function(e){
			g.scroll();
		}).append(t);
		/*
		$(g.body).addClass('ui-flexigrid-body').css('height', o.height).append(t).scroll(function(e){
			g.scroll();
		});
		*/
		g.addCellProp();
		g.addGridProp();
		// 添加条纹效果
		if(o.striped){
			var trs = $('tbody tr', g.body), len = trs.length;
			for(var i = 0; i < len; i++){
				if(i % 2){
					$(trs[i]).addClass('odd');
				}
			}
		}
		//$(g.box).append(g.body);
		
		// 创建数据加载遮罩层
		var bt = g.body.offsetTop, bw = $(g.body).width(), bh = $(g.body).height();
		$(g.load).addClass('ui-flexigrid-load').css({ 'top': bt, 'height': bh, 'line-height': bh + 'px' }).html('<span class="load">' + o.loadingTips + ' </span>');
		
        // 创建列宽调节标签
        if(o.colResizable){
        	var ths = $('thead tr th', g.head), th = ths.get(0);
        	if(ths.length > 0){
                var ht = g.head.offsetTop, hh = $(g.head).outerHeight(), bh = $(g.body).outerHeight();
                $(g.drag).addClass('ui-flexigrid-drag').css({ 'top': ht });
                $(g.box).append(g.drag);
        		g.cellPatch = 0;
        		g.cellPatch += (isNaN(parseInt($('div', th).css('borderLeftWidth'), 10)) ? 0 : parseInt($('div', th).css('borderLeftWidth'), 10));
        		g.cellPatch += (isNaN(parseInt($('div', th).css('borderRightWidth'), 10)) ? 0 : parseInt($('div', th).css('borderRightWidth'), 10));
                g.cellPatch += (isNaN(parseInt($('div', th).css('paddingLeft'), 10)) ? 0 : parseInt($('div', th).css('paddingLeft'), 10));
                g.cellPatch += (isNaN(parseInt($('div', th).css('paddingRight'), 10)) ? 0 : parseInt($('div', th).css('paddingRight'), 10));
                g.cellPatch += (isNaN(parseInt($(th).css('borderLeftWidth'), 10)) ? 0 : parseInt($(th).css('borderLeftWidth'), 10));
                g.cellPatch += (isNaN(parseInt($(th).css('borderRightWidth'), 10)) ? 0 : parseInt($(th).css('borderRightWidth'), 10));
                g.cellPatch += (isNaN(parseInt($(th).css('paddingLeft'), 10)) ? 0 : parseInt($(th).css('paddingLeft'), 10));
                g.cellPatch += (isNaN(parseInt($(th).css('paddingRight'), 10)) ? 0 : parseInt($(th).css('paddingRight'), 10));
                ths.each(function(){
                    var dg = document.createElement('div');
                    if(!o.dgwidth){
                        o.dgwidth = 5;
                    }
                    $(dg).css({ 'width': o.dgwidth, 'height': hh + bh }).mousedown(function(e){
                    	/*
                        g.dragStart('colresize', e, this);
                        */
                    }).dblclick(function(e){
                    	/*
                        g.autoResizeColumn(this);
                        */
                    });
                    if(g.isIe6){
                    	/*
                        g.fixHeight($(g.box).height());
                        $(dg).hover(function(){
                            g.fixHeight();
                            $(this).addClass('hover');
                        }, function(){
                            if(!g.colresize){
                                $(this).removeClass('hover');
                            }
                        });
                        */
                    }
                    $(g.drag).append(dg);
                });
        	}
        }
		
		// 创建分页栏
		if(o.usePage){
			var html = [];
			html.push('<div class="ui-flexigrid-page-inner clearfix">');
			html.push('<div class="group"><div class="button first"><span>首页</span></div><div class="button prev"><span>上一页</span></div><div class="button next"><span>下一页</span></div><div class="button last"><span>尾页</span></div></div>');
			html.push('<div class="separator"></div>');
			html.push('<div class="group"><div class="current"><label class="word">第</label><input type="text" class="page" value="1" /><label class="word">页</label></div><div class="total"><label class="word">共</label><label class="word pages">1</label><label class="word">页</label></div></div>');
			html.push('<div class="separator"></div>');
			// 是否输出每页多少条选项
			if(o.useRp){
				var opts = [], selected = '', i = 0, len = o.rpOptions.length;
				for(; i < len; i++){
					var opt = o.rpOptions[i];
					selected = (opt == o.defaultRp) ? ' selected="selected"' : '';
					opts.push('<option value="'  + opt + '"' + selected + '>' + opt + '</option>');
				}
				html.push('<div class="group"><select name="rp" class="rp">' + opts.join('') + '</select></div>');
			}
			html.push('</div>');
			$(g.page).addClass('ui-flexigrid-page clearfix').html(html.join(''));
			// 首页
			$('.first', g.page).click(function(){
				g.changePage('first');
			});
			// 上一页
			$('.prev', g.page).click(function(){
				g.changePage('prev');
			});
			// 下一页
			$('.next', g.page).click(function(){
				g.changePage('next');
			});
			// 尾页
			$('.last', g.page).click(function(){
				g.changePage('last');
			});
			// 跳转
			$('.page', g.page).keydown(function(e){
				if(e.keyCode == 13){
                    g.changePage('jump');
				}
			});
			// 每页显示多少条
			if(o.useRp){
				$('select', g.page).change(function(){
					if(o.onRpChange){
						o.onRpChange($(this).val());
					}else{
						o.newPage = 1;
						o.defaultRp = $(this).val();
						g.populate();
					}
				});
			}
			if(g.isIe6){
				$('.button, .page', g.page).hover(function(){
					$(this).addClass('hover');
				}, function(){
					$(this).removeClass('hover');
				});
			}
			$(g.box).append(g.page);
		}
		
		// 创建调节网格高度的标签
		if(o.resizable && o.heightResizable && o.height != 'auto'){
			$(g.heightResizer).addClass('ui-flexigrid-ver-resizer').html('<span></span>').mousedown(function(e){
				/*
				g.dragStart('vresize', e);
				*/
			});
			if(g.isIe6){
				$(g.heightResizer).hover(function(){
					$(this).addClass('hover');
				}, function(){
					$(this).removeClass('hover');
				});
			}
			$(g.box).append(g.heightResizer);
		}
		
		// 创建调节网格宽度的标签
		if(o.resizable && o.widthResizable && o.width != 'auto'){
			$(g.widthResizer).addClass('ui-flexigrid-hor-resizer').css('height', $(g.box).outerHeight()).html('<span></span>').mousedown(function(e){
				/*
				g.dragStart('vresize', e, true);
				*/
			});
			if(g.isIe6){
				$(g.widthResizer).hover(function(){
					$(this).addClass('hover');
				}, function(){
					$(this).removeClass('hover');
				});
			}
			$(g.box).append(g.widthResizer);
		}
		
		// 设置相关标签高度
		g.fixHeight();
		
		// add document events
		$(document).mousemove(function(e){
			/*
			g.dragMove(e);
			*/
		}).mouseup(function(e){
			/*
			g.dragEnd();
			*/
		}).hover(function(){
			
		}, function(){
			/*
			g.dragEnd();
			*/
		});
		
		// browser adjustments
		if(g.isIe6){
			$(g.box).addClass('ie6');
			if(o.width != 'auto'){
				$(g.box).addClass('ie6fullwidthbug');
			}
		}
		
		// Load data
		if(o.url && o.autoLoad){
			g.populate();
		}
		
		// Make grid functions accessible
		t.o = o;
		t.grid = g;
		t.flexigrid = 1;
		
		return t;
	};
	
	// 用于判断文档是否已完成加载
	var docIsReady = false;
	
	// 
	$(function(){
		
		docIsReady = true;
		
	});
	
	// 入口
	$.fn.flexigrid = function(o){
		
		return this.each(function(){
			
			var table = this;
			
			if(!docIsReady){
				
				$(function(){
					
					$.addFlex(table, o);
					
				});
				
			}else{
				
				$.addFlex(table, o);
				
			}
			
		});
		
	};
	
	// 搜索
	$.fn.flexSearch = function(params){
		return this.each(function(){
			if(this.o && this.grid){
				this.grid.doSearch(params);
			}
		});
	};
	
	// 刷新
	$.fn.flexReload = function(opts){
		return this.each(function(){
			if(this.o && this.grid){
				this.grid.populate();
			}
		});
	};
	
	// 
	$.fn.flexOptions = function(opts){
		return this.each(function(){
			if(this.o){
				$.extend(this.o, opts);
			}
		});
	};
	
	// 
	$.fn.flexToggleCol = function(cid, visible){
		
		return this.each(function(){
			
			if(this.grid){
				
				this.grid.toggleCol(cid, visible);
				
			}
			
		});
		
	};
	
	// Function to add data to grid
	$.fn.flexAddData = function(data){
		return this.each(function(){
			if(this.grid){
				this.grid.addData(data);
			}
		});
	};
	
	// 是否可以选中grid中的文本
	$.fn.noSelect = function(o){
		var prevent = (o === null) ? true : o;
		if(prevent){
			return this.each(function(){
				if(browser.msie || browser.safari){
					$(this).bind('selectstart', function(){
						return false;
					});
				}else if(browser.mozilla){
					$(this).css('MozUserSelect', 'none');
					$('body').trigger('focus');
				}else if(browser.opera){
					$(this).bind('mousedown', function(){
						return false;
					});
				}else{
					$(this).attr('unselectable', 'on');
				}
			});
		}else{
			return this.each(function(){
				if(browser.msie || browser.safari){
					$(this).unbind('selectstart');
				}else if(browser.mozilla){
					$(this).css('MozUserSelect', 'inherit');
				}else if(browser.opera){
					$(this).unbind('mousedown');
				}else{
					$(this).removeAttr('unselectable', 'on');
				}
			});
		}
	};
	
	// Returns the selected rows as an array, taken and adapted from http://stackoverflow.com/questions/11868404/flexigrid-get-selected-row-columns-values
	$.fn.selectedRows = function(o){
		var arReturn = [], arRow = [], selector = $(this.selector + ' .selected');
		$(selector).each(function(i, row){
			arRow = [];
			var idr = $(row).data('id');
			$.each(row.cells, function(c, cell){
				var col = cell.abbr;
				var val = cell.firstChild.innerHTML;
				if (val == '&nbsp;') val = '';      // Trim the content
        		        var idx = cell.cellIndex;                

				arRow.push({
					Column: col,        // Column identifier
					Value: val,         // Column value
					CellIndex: idx,     // Cell index
					RowIdentifier: idr  // Identifier of this row element
				});
			});
			arReturn.push(arRow);
		});
		return arReturn;
	};
	
})(jQuery);
