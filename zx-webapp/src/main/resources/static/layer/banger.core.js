/*========================================
 * 命名空间注册机
 ========================================*/
var Namespace = new Object();
// 定义命名空间注册函数
Namespace.register = function(name){
    var ns = name.split('.'), result = '', space = '';
    for(var i = 0, l = ns.length; i < l; i++){
        if(i != 0){
            space += '.';
        }
        space += ns[i];
        result += 'if(typeof ' + space + ' === "undefined" || typeof ' + space + ' !== "object"){' + space + ' = {};}';
        continue;
    }
    if(result.length > 0){
        eval(result);
    }
};

/*========================================
 * 获取项目根路径
========================================*/
this.rootPath = (function(){
    var href = location.href, path = location.pathname, end = href.indexOf(path);
    var root = href.substring(0, end);
    if(!root){
        return '';
    }
    return root;
})();

/*========================================
 * 浏览器类型及版本判断
========================================*/
this.userAgent = navigator.userAgent;
// Is Opera
this.isOpera = /opera[56789]|opera\/[56789]/i.test(userAgent);
// Is Ie
this.isIe = (!this.isOpera) && /MSIE/.test(userAgent);
// Is Ie6
this.isIe6 = !-[1,] && !window.XMLHttpRequest;
// Is Moz
this.isMoz = !this.isOpera && /gecko/i.test(userAgent);
// Is Chrome
this.isChrome = userAgent.indexOf('Chrome') > -1;

/*========================================
 * 判断当前页面是否为iframe引用页面
========================================*/
this.isIframePage = this.frameElement ? true : false;

/*========================================
 * 变量类型判断
========================================*/
// 返回变量类型
this.getType = function(obj){
    return obj && Object.prototype.toString.call(obj);
};
// 是否是window对象
this.isWindow = function(obj){
    return obj && obj == obj.window;
};
// 是否是数值
this.isNumber = function(num){
    return this.getType(num) === '[object Number]';
};
// 是否是字符串
this.isString = function(str){
    return this.getType(str) === '[object String]';
};
// 是否是数组
this.isArray = function(arr){
    return this.getType(arr) === '[object Array]';
};
// 是否是布尔值
this.isBoolean = function(flag){
    return this.getType(flag) === '[object Boolean]';
};
// 是否是日期对象
this.isDate = function(date){
    return this.getType(date) === '[object Date]';
};
// 是否是函数
this.isFunction = function(fn){
    return this.getType(fn) === '[object Function]';
};
// 是否是正则表达式
this.isRegex = function(rgx){
    return this.getType(rgx) === '[object RegExp]';
};
// 是否是对象
this.isObject = function(obj){
    return this.getType(obj) === '[object Object]';
};
// 是否是纯粹的对象（纯粹的对象是指通过 {} 或者 new Object() 创建的对象）
this.isPlainObject = function(obj){
    if(!obj || !this.isObject(obj) || this.isWindow(obj) || obj.nodeType){
        return false;
    }
    var hasOwnProperty = Object.prototype.hasOwnProperty;
    try{
        if(obj.constructor && !hasOwnProperty.call(obj, 'constructor') && !hasOwnProperty.call(obj.constructor.prototype, 'isPrototypeOf')){
            return false;
        }
    }catch(e){
        return false;
    }
    var key;
    for(key in obj){}
    return key === undefined || hasOwnProperty.call(obj, key);
};
// 是否是空对象
this.isEmptyObject = function(obj){
    for(var key in obj){
        return false;
    }
    return true;
};

/*========================================
 * 对象深拷贝
========================================*/
this.cloneObject = function(obj){
    var n = this.isArray(obj) ? [] : {};
    for(var key in obj){
        if(obj.hasOwnProperty(key)){
            n[key] = this.isPlainObject(obj[key]) ? this.cloneObject(obj[key]) : obj[key];
        }
    }
    return n;
};

/*========================================
 * 判断一个变量是否存在于一个数组中
 * @ target 目标变量
 * @ array 目标数组
 * @ i 用来搜索数组队列，默认值为零
========================================*/
this.inArray = function(target, array, i){
    var arrayIndexOf = Array.prototype.indexOf, len;
    if(array){
        if(arrayIndexOf){
            return arrayIndexOf.call(array, target, i);
        }
        len = array.length;
        i = i ? i < 0 ? Math.max(0, len + i) : i : 0;
        for(; i < len; i++){
            if(i in array && array[i] === target){
                return i;
            }
        }
    }
    return -1;
};

/*========================================
 * 获取事件源
========================================*/
this.getEvent = function(e){
    return e || window.event;
};

/*========================================
 * 获取事件源元素
========================================*/
this.getEventTarget = function(e){
    var ev = this.getEvent(e);
    return ev.target || ev.srcElement;
};

/*========================================
 * 禁止退格键
========================================*/
this.forbidBackSpace = function(e){
    // 获取event对象
    var ev = e || window.event;
    // 获取事件源
    var target = ev.target || ev.srcElement;
    // 获取事件源类型
    var type = target.type || target.getAttribute('type');
    // 获取作为判断条件的事件类型
    var readOnly = target.readOnly, disabled = target.disabled;
    // 处理undefined值情况
    readOnly = readOnly == undefined ? false : readOnly;
    disabled = disabled == undefined ? true : disabled;
    var keyCode = ev.keyCode;
    // 当敲退格键时，事件源类型为密码、单行或多行文本框时，
    // 并且为只读或者禁用的，退格键失效
    var flag1 = keyCode == 8 && (type == 'password' || type == 'text' || type == 'textarea') && (readOnly == true || disabled == true);
    // 当敲退格键时，事件源类型非密码、单行或多行文本框时，退格键失效
    var flag2 = keyCode == 8 && type != 'password' && type != 'text' && type != 'textarea';
    // 判断
    if(flag2 || flag1){
        return false;
    }
};
// Firefox、Opera
document.onkeypress = this.forbidBackSpace;
// Ie、Chrome
document.onkeydown = this.forbidBackSpace;

/*========================================
 * 获取、修改或添加url的某个参数
========================================*/
this.modifyUrl = function(url, key, value){
    var rgx = new RegExp('(\\\?|&)' + key + '=([^&]+)(&|$)', 'i'), match = url.match(rgx);
    if(value){
        if(match){
            return url.replace(rgx, function($0, $1, $2){
                return ($0.replace($2, value));
            });
        }else{
            if(url.indexOf('?') == -1){
                return (url + '?' + key + '=' + value);
            }else{
                return (url + '&' + key + '=' + value);
            }
        }
    }else{
        if(match){
            return match[2];
        }else{
            return '';
        }
    }
};

/*========================================
 * 字符转义
========================================*/
// 特殊字符集
this.charsets = {
    '`': '&#96;', '~': '&#126;', '!': '&#33;', '@': '&#64;', '#': '&#35;', '$': '&#36;', '%': '&#37;', '^': '&#94;', '&': '&#38;', '*': '&#42;',
    '(': '&#40;', ')': '&#41;', '-': '&#45;', '_': '&#95;', '=': '&#61;', '+': '&#43;', '\\': '&#92;', '|': '&#124;', '[': '&#91;', ']': '&#93;',
    '{': '&#123;', '}': '&#125;', ';': '&#59;', ':': '&#58;', '\'': '&#39;', '"': '&#34;', ',': '&#44;', '<': '&#60;', '.': '&#46;', '>': '&#62;',
    '/': '&#47;', '?': '&#63;'
};
// 替换特殊字符
this.charReplace = function(value){
    var chars = value.split(''), i = 0, len = chars.length;
    for(; i < len; i++){
        var o = this.charsets[chars[i]];
        if(o){
            chars[i] = o;
        }
    }
    return chars.join('');
};

/*========================================
 * 返回页面中选中的文本及其索引
========================================*/
this.getPageSelection = function(obj){
    var start, end, range, storedRange;
    if(obj.selectionStart == undefined){
        var selection = document.selection;
        if(obj.tagName.toLowerCase != 'textarea'){
            var val = obj.value;
            range = selection.createRange().duplicate();
            range.moveEnd('character', val.length);
            start = (range.text == '' ? val.length : val.lastIndexOf(range.text));
            range = selection.createRange().duplicate();
            range.moveStart('character', -val.length);
            end = range.text.length;
        }else{
            range = selection.createRange(),
                storedRange = range.duplicate();
            storedRange.moveToElementText(this[0]);
            storedRange.setEndPoint('EndToEnd', range);
            start = storedRange.text.length - range.text.length;
            end = start + range.text.length;
        }
    }else{
        start = obj.selectionStart,
            end = obj.selectionEnd;
    }
    var selected = obj.value.substring(start, end);
    return { start: start, end: end, text: selected };
};

/*========================================
 * 动态引入CSS
 ========================================*/
this.includeCss = function(src){
    var link = document.createElement('link');
    link.type = 'text/css';
    link.rel = 'stylesheet';
    link.href = src;
    document.getElementsByTagName('head').item(0).appendChild(link);
};

/*========================================
 * 动态引入JS
 ========================================*/
this.includeJs = function(src){
    var script = document.createElement('script');
    script.type = 'text/javascript';
    script.src = src;
    document.getElementsByTagName('body').item(0).appendChild(script);
};

/*========================================
 * 加减乘除运算
========================================*/
// 加
Math.plus = function(n1, n2){
    var r1, r2, m;
    try{
        r1 = n1.toString().split('.')[1].length;
    }catch(e){
        r1 = 0;
    }
    try{
        r2 = n2.toString().split('.')[1].length;
    }catch(e){
        r2 = 0;
    }
    m = Math.pow(10, Math.max(r1, r2));
    return (n1 * m + n2 * m) / m;
};
// 减
Math.minus = function(n1, n2){
    var r1, r2, m, n;
    try{
        r1 = n1.toString().split('.')[1].length;
    }catch(e){
        r1 = 0;
    }
    try{
        r2 = n2.toString().split('.')[1].length;
    }catch(e){
        r2 = 0;
    }
    m = Math.pow(10, Math.max(r1, r2));
    n = (r1 >= r2) ? r1 : r2;
    return ((n1 * m - n2 * m) / m).toFixed(n);
};
// 乘
Math.multiply = function(n1, n2){
    var m = 0, s1 = n1.toString(), s2 = n2.toString();
    try{
        m += s1.split('.')[1].length;
    }catch(e){

    }
    try{
        m += s2.split('.')[1].length;
    }catch(e){

    }
    return Number(s1.replace('.', '')) * Number(s2.replace('.', '')) / Math.pow(10, m);
};
// 除
Math.division = function(n1, n2){
    var t1 = 0, t2 = 0, r1, r2;
    try{
        t1 = n1.toString().split('.')[1].length;
    }catch(e){

    }
    try{
        t2 = n2.toString().split('.')[1].length;
    }catch(e){

    }
    with(Math){
        r1 = Number(n1.toString().replace('.', ''));
        r2 = Number(n2.toString().replace('.', ''));
    }
    return (r1 / r2) * pow(10, t2 - t1);
};

