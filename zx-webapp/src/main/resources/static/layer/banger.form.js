/**
 * http://www.baihang-china.com/
 * Author: dingwei 2013-10-30
 * Description: banger.form.js
 * Modified by:
 * Modified contents:
 **/
Namespace.register('banger.form');

// 重置表单
banger.form.reset = function(selector, callback){
    var form = $(selector).get(0);
    // reset
    form.reset();
    try{
        // reset autotips
        var texts = $('[autotips]');
        texts.each(function(){
            var text = $(this), val = text.val(), tips = text.attr('autotips'), color = text.attr('color');
            if(val == ''){
                text.val(tips).css('color', color);
            }
        });
        // reset selectbox
        $('select', form).change().selectbox();
    }catch(e){

    }
    // callback
    if(callback && typeof callback === 'function'){
        callback();
    }
};

var toResetForm = function(id, callback){
    banger.form.reset(id, callback);
};

var cleanForm = toCleanForm = resetForm = toResetForm;
