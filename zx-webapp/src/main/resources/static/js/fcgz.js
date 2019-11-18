
$(function () {
    /*切换城市*/
    $('#changeCity').click(function () {
        $('#xqlist').empty();
        $('#suggestId').val("");
        index=layer.open({
            type: 1,
            skin: 'layui-layer-rim', //加上边框
            area: ['420px', '240px'], //宽高
            content: '<ul style="width: 240px;height: 165px;margin: 5px;auto"> ' +
            '          <button type="button" class="ct" style="margin:6px">北京市</button> ' +
            '          <button type="button" class="ct" style="margin:6px">上海市</button> ' +
            '          <button type="button" class="ct" style="margin:6px">深圳市</button> ' +
            '          <button type="button" class="ct" style="margin:6px">杭州市</button> ' +
            '          <button type="button" class="ct" style="margin:6px">太原市</button> '+
            '          <button type="button" class="ct" style="margin:6px">晋城市</button> ' +
            '          <button type="button" class="ct" style="margin:6px">晋中市</button> ' +
            '          <button type="button" class="ct" style="margin:6px">中山市</button> ' +
            '          <button type="button" class="ct" style="margin:6px">临汾市</button> ' +
            '          <button type="button" class="ct" style="margin:6px">大同市</button> ' +
            '          <button type="button" class="ct" style="margin:6px">朔州市</button> ' +
            '          <button type="button" class="ct" style="margin:6px">阳泉市</button> ' +
            '          <button type="button" class="ct" style="margin:6px">长治市</button> ' +
            '          <button type="button" class="ct" style="margin:6px">忻州市</button> ' +
            '          <button type="button" class="ct" style="margin:6px">吕梁市</button>' +
            '          <button type="button" class="ct" style="margin:6px">运城市</button> ' +
            '          <button type="button" class="ct" style="margin:6px">重庆市</button> ' +
            '          <button type="button" class="ct" style="margin:6px">成都市</button> ' +
            '          <button type="button" class="ct" style="margin:6px">西昌市</button> ' +
			'          <button type="button" class="ct" style="margin:6px">芜湖市</button> ' +
            '  </ul> '

        });
    });

    /*************房屋估值*************/
    $('#showTooltips').click(function () {
        var projectName="";
        var district="";
        var cityName = $('#currentCity').html();
        if(cityName=="西昌市"){
            cityName="凉山彝族自治州"
        }
        var mj = $('#houseArea').val();
        var house=$('#suggestId').val();
        var houseArr=house.split("-");
        if(houseArr.length==2){
            district=houseArr[0];
            projectName=houseArr[1];
        }else {
            projectName=house;
        }
        //校验参数
        if(!yz()){
            return;
        }
        $.ajax({
            type: 'post',
            url: "http://op.test.zhudb.com/backend/comm/api/v1/valuation/house",
            dataType: "json",
            cossDomain: true,
            beforeSend: function (request) {
                request.setRequestHeader("certificate", "YWJj:YWJj");
            },
            data: {"enterpriseCode": "10001", "size": mj, "city": cityName, "projectName": projectName, "district":district},
            success: function (result) {
                if (result.code == "0") {
                    var value = result.data;
                  var index=  layer.open({
                        time: 10000,
                        offset: '100px',
                        shadeClose: false,
                        shade: 'background-color: rgba(0,0,0,0.3)',
                        btn: ['确定'],
                        content: "<i style=\" font-size:0.42rem;\">" + '您房屋估值结果为：'+value+'元'+ "</i>",
                        yes: function () {
                             layer.close(index);
                        },
                        style: 'background-color:rgba(52, 52, 52, 0.5);color:white; border:none;'
                    });
                }else{
                    showMessage("估值失败，请稍后再试！");
                }
            },
            error:function () {
                showMessage("系统异常，请稍后再试！");
            }
        });

    });

    /*********获取小区列表**************/
    $('#suggestId').keyup(function () {
        var key=$(this).val();
        var city=$('#currentCity').html();
        if(city=="西昌市"){
            city="凉山彝族自治州"
        }
        $.ajax({
            type: 'get',
            url: "http://op.test.zhudb.com/backend/comm/api/v1/valuation/community",
            // url:"http://192.168.70.13:8080/comm/api/v1/valuation/community",
            beforeSend: function (request) {
                request.setRequestHeader("certificate", "YWJj:YWJj");
            },
            dataType: "json",
            cossDomain: true,
            data: {"enterpriseCode": "10001", "city": city, "projectName": key},
            success: function (result) {
                var list=result.data;
                if(list==null){
                    return;
                }
                $('#xqlist').empty();

                if(list.length<6){
                    for(i=0;i<list.length;i++){
                        if(list[i].district!=""){
                            $('#xqlist').append("<li class='xq' style='margin: 0.3rem;'>"+list[i].district+'-'+list[i].name+"</li>");
                        }else{
                            $('#xqlist').append("<li class='xq' style='margin: 0.3rem'>"+list[i].name+"</li>");
                        }
                    }
                }else{
                    for(i=0;i<=5;i++){
                        if(list[i].district!=""){
                            $('#xqlist').append("<li class='xq' style='margin: 0.3rem'>"+list[i].district+'-'+list[i].name+"</li>");
                        }else{
                            $('#xqlist').append("<li class='xq' style='margin: 0.3rem'>"+list[i].name+"</li>");
                        }
                    }
                }
            }
        });

    });
});

function showMessage(message) {
    //弹框
    layer.open({
        time:3,
        offset: '100px',
        shade:false,
        // shadeClose: false,
        content: "<i style=\" font-size:0.42rem;\">"+message+"</i>",
        style:'background-color:rgba(52, 52, 52, 0.5);color:white; border:none;'
    });
}

function yz() {
    var xq = $('#suggestId').val();
    var mj = $('#houseArea').val();
    var lx = $('#houseType').val();
    if (xq == "") {
        showMessage("小区名不能为空");
        return false;
    }
    if (mj == "") {
        showMessage("房屋面积不能为空");
        return false;
    }
    var r = /^\+?[1-9][0-9]*$/;　　//正整数
    if (!r.test(mj)) {
        showMessage("房屋面积不符合规范")
        return false;
    }
    if (lx == "") {
        showMessage('房产类型不能为空');
        return false;
    }
    return true;
}

function FormatNumber(srcStr,nAfterDot)        //nAfterDot小数位数
{
    var srcStr,nAfterDot;
    var resultStr,nTen;
    srcStr = ""+srcStr+"";
    strLen = srcStr.length;
    dotPos = srcStr.indexOf(".",0);
    if (dotPos == -1){
        resultStr = srcStr+".";
        for (i=0;i<nAfterDot;i++){
            resultStr = resultStr+"0";
        }
        return resultStr;
    }
    else{
        if ((strLen - dotPos - 1) >= nAfterDot){
            nAfter = dotPos + nAfterDot + 1;
            nTen =1;
            for(j=0;j<nAfterDot;j++){
                nTen = nTen*10;
            }
            resultStr = Math.round(parseFloat(srcStr)*nTen)/nTen;
            return resultStr;
        }
        else{
            resultStr = srcStr;
            for (i=0;i<(nAfterDot - strLen + dotPos + 1);i++){
                resultStr = resultStr+"0";
            }
            return resultStr;
        }
    }
}

function clearAll() {
    $('#suggestId').val("");
    $('#xqlist').empty();
}
