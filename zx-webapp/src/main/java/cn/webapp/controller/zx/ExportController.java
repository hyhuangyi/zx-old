package cn.webapp.controller.zx;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.common.entity.BlackList;
import cn.common.entity.BlackListExport;
import cn.common.entity.Demo;
import cn.common.util.file.EasyPoiUtil;
import cn.webapp.controller.BaseController;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.I0Itec.zkclient.exception.ZkException;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

@Api(description = "导入导出")
@RequestMapping("/comm")
@RestController
public class ExportController extends BaseController {
    @ApiOperation(value = "jxl导出测试")
    @RequestMapping(value = "/jxl/export", method = RequestMethod.GET)
    public void test(HttpServletResponse response) {
        List<Demo> list=new ArrayList<>();
        SimpleDateFormat datefor=new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        for(int i=0;i<49;i++){
            calendar.add(Calendar.DATE,+1);
            list.add(new Demo(datefor.format(calendar.getTime())));
        }
        Map<String, Object> model = new HashMap<>();
        model.put("actives", list);
        String modelName = "/xls/demo.xlsx";
        logger.info("--------------------------开始导出------------------------------");
        downFileFromModel(response, modelName, model);
    }

    @ApiOperation(value = "easy poi 导入测试")
    @PostMapping(value = "/poi/import")
    public List<BlackList> poiExport(@RequestParam MultipartFile file){
        if(file.isEmpty()){
            throw new ZkException("文件不能为空");
        }
        ImportParams params = new ImportParams();
        List<BlackList> list=null;
        params.setHeadRows(1);
        try {
          list = ExcelImportUtil.importExcel(file.getInputStream(), BlackList.class,params);
          System.out.println(JSON.toJSON(list));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @ApiOperation(value = "easy poi 导出测试")
    @GetMapping(value = "/poi/export")
    public void export(HttpServletResponse response){
        Workbook workbook = bigExcel(1, new ExportParams("黑名单列表", "黑名单列表"));
        downLoadExcel("黑名单列表.xls", response, workbook);
        ExcelExportUtil.closeExportBigExcel();
    }

    /**
     *  导出订黑名单
     */
    private Workbook bigExcel(Integer pageNum,  ExportParams exportParams) {
        //分页查询数据
       List<BlackListExport> list=new ArrayList<>();
       for(int i=0;i<50000;i++){
           list.add(new BlackListExport(i+"",i+"",i+""));
       }
       Workbook workbook = ExcelExportUtil.exportBigExcel(exportParams, BlackListExport.class, list);
       return workbook;
    }

    private static void downLoadExcel(String fileName, HttpServletResponse response, Workbook workbook) {
        try {
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            response.setHeader("content-Type", "application/vnd.ms-excel");
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
        }
    }

    @ApiOperation(value = "easyPoiUtil 导出测试")
    @GetMapping(value = "/poi/export1")
    public void export1(HttpServletResponse response){
        List<BlackListExport> list=new ArrayList<>();
        for(int i=0;i<100000;i++){
            list.add(new BlackListExport(i+"",i+"",i+""));
        }
        EasyPoiUtil.exportExcel(list,"zx","zx",BlackListExport.class,"zx.xls",response);
    }

    /**
     * 如果填充不同sheet得data数据列表使用相同得list对象进行填充的话，
     * 会出现第一次填充得sheet有数据，后续其他使用相同list对象进行data填充得sheet没有数据展示。
     * @param response
     */
    @ApiOperation(value = "多sheet 导出测试")
    @GetMapping(value = "/poi/export2")
    public void export2(HttpServletResponse response){
        // 查询数据,此处省略
        List list = new ArrayList<>();
        list.add(new BlackListExport("姓名1","备注1","手机1")) ;
        list.add(new BlackListExport("姓名2","备注2","手机2")) ;
        list.add(new BlackListExport("姓名3","备注3","手机3")) ;

        List list2 = new ArrayList<>();
        list2.add(new BlackListExport("姓名-1","备注-1","手机-1")) ;
        list2.add(new BlackListExport("姓名-2","备注-2","手机-2")) ;
        list2.add(new BlackListExport("姓名-3","备注-3","手机-3")) ;
        List<Map<String,Object>> sheetsList = new ArrayList<>();
        for(int i=1;i<=2;i++){
        // 设置导出配置
        // 创建参数对象（用来设定excel得sheet得内容等信息）
        ExportParams params = new ExportParams() ;
        // 设置sheet得名称
        params.setSheetName("表格"+i);

        //创建sheet使用的map
        Map<String,Object> dataMap = new HashMap<>();
        // title的参数为ExportParams类型，目前仅仅在ExportParams中设置了sheetName
        dataMap.put("title",params) ;
        // 模版导出对应得实体类型
        dataMap.put("entity",BlackListExport.class) ;
        // sheet中要填充得数据
       if(i%2==0){
           dataMap.put("data",list) ;
       }else {
           dataMap.put("data",list2) ;
       }

        sheetsList.add(dataMap);
       }
        EasyPoiUtil.exportExcel(sheetsList,"hy.xls",response);
    }
}
