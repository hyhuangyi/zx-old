// 获取当前窗口对象和顶级窗口对象
var w = window, wt = w.top;

// 引用顶级窗口的tabs对象
var tabs = wt.tabs;

// 注册控件命名空间
Namespace.register('banger.controls');
// 清除或隐藏其它控件
banger.controls.hide = function(){
    var style1 = {'z-index': ''}, style2 = {'display': 'none'};
    // 下拉单选框
    $('div.ui-selectbox').css(style1).find('div.options').html('').css(style2);
    // 下拉复选框
    $('div.ui-checkbox').css(style1).find('div.ui-checkbox-box').off().remove();
    // 下拉树形选择框
    $('div.ui-treeselectbox').css(style1).find('div.ui-treeselectbox-box').css(style2);
    // 输入自动匹配
    $('div.ui-automatch').css(style1).find('div.ui-automatch-box').css(style2);
};

// 在IE6浏览器下，避免iframe出现纵向滚动条时页面右侧溢出
var ie6IframeScroll = function(){
    $('html').css('overflow-y', $('html')[0].scrollHeight < $('html').height() ? 'auto' : 'scroll');
};

/**
 * 防止复重提交，selector #id
 */
function buttonDisable(selector) {
    if(typeof selector == 'string'){
        $(selector).css('pointer-events','none');
        setTimeout(function () {
            $(selector).css('pointer-events','auto');
        },3000);
    }else if(selector instanceof jQuery){
        selector.css('pointer-events','none');
        var id = selector.attr("id");
        setTimeout(function () {
            selector.css('pointer-events','auto');
        },3000);
    }
}

// 页面加载完成
$(function(){
    //
    if(isIe6 && isIframePage){
        $(wt).resize(function(){
            ie6IframeScroll();
        });
    }

    $('body')
    // ui-button.mouseover
    .on('mouseover', '.ui-button', function(){
        $(this).addClass('ui-button-hover');
    })
    // ui-button.mouseout
    .on('mouseout', '.ui-button', function(){
        $(this).removeClass('ui-button-hover ui-button-active');
    })
    // ui-button.mousedown
    .on('mousedown', '.ui-button', function(){
        $(this).addClass('ui-button-active');
    })
    // ui-button.mouseup
    .on('mouseup', '.ui-button', function(){
        $(this).removeClass('ui-button-active');
    })
    // ui-search-button.mouseover
    .on('mouseover', '.ui-search-button', function(){
        $(this).addClass($(this).hasClass('ui-search-button-clean') ? 'ui-search-button-clean-hover' : 'ui-search-button-hover');
    })
    // ui-search-button.mouseout
    .on('mouseout', '.ui-search-button', function(){
        $(this).removeClass('ui-search-button-hover ui-search-button-active ui-search-button-clean-hover ui-search-button-clean-active');
    })
    // ui-search-button.mousedown
    .on('mousedown', '.ui-search-button', function(){
        $(this).addClass($(this).hasClass('ui-search-button-clean') ? 'ui-search-button-clean-active' : 'ui-search-button-active');
    })
    // ui-search-button.mouseup
    .on('mouseup', '.ui-search-button', function(){
        $(this).removeClass('ui-search-button-active ui-search-button-clean-active');
    })
    // ui-closure-button.mouseover
    .on('mouseover', '.ui-closure-button', function(){
        $(this).addClass($(this).hasClass('ui-closure-button-cancel') ? 'ui-closure-button-cancel-hover' : 'ui-closure-button-hover');
    })
    // ui-closure-button.mouseout
    .on('mouseout', '.ui-closure-button', function(){
        $(this).removeClass('ui-closure-button-hover ui-closure-button-active ui-closure-button-cancel-hover ui-closure-button-cancel-active');
    })
    // ui-closure-button.mousedown
    .on('mousedown', '.ui-closure-button', function(){
        $(this).addClass($(this).hasClass('ui-closure-button-cancel') ? 'ui-closure-button-cancel-active' : 'ui-closure-button-active');
    })
    // ui-closure-button.mouseup
    .on('mouseup', '.ui-closure-button', function(){
        $(this).removeClass('ui-closure-button-active ui-closure-button-cancel-active');
    });

    // 在IE6浏览器下脚本处理CSS不兼容的问题
    if(isIe6){
        // 设置多行文本框的高度
        $('.ui-textarea').each(function(){
            var oArea = $(this), h = oArea.height();
            $('textarea', oArea).height(h - 2);
        });

        $('body')
        // (ui-text, ui-file, ui-textarea, ui-timepicker).mouseover
        .on('mouseover', '.ui-text, .ui-file, .ui-textarea, .ui-timepicker', function(){
            $(this).addClass('ui-text-hover');
        })
        // (ui-text, ui-file, ui-textarea, ui-timepicker).mouseout
        .on('mouseout', '.ui-text, .ui-file, .ui-textarea, .ui-timepicker', function(){
            $(this).removeClass('ui-text-hover');
        })
        // ui-link.mouseover
        .on('mouseover', '.ui-link', function(){
            $(this).addClass('ui-link-hover');
        })
        // ui-link.mouseout
        .on('mouseout', '.ui-link', function(){
            $(this).removeClass('ui-link-hover');
        });
    }
	
	/*
    // 让第一个单行或多行文本框自动获取光标
    setTimeout(function(){
        // 查找可用且可见的单行或多行文本框
        var oTexts = $(':text:enabled:visible'), oAreas =  $('textarea:enabled:visible');
        if(oTexts.length != 0){
            oTexts.filter(':first').focus();
        }else if(oAreas.length != 0){
            oAreas.filter(':first').focus();
        }
    }, 100);
    */
});
