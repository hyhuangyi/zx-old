package cn.common.util.json;

import cn.common.pojo.ResultDO;
import cn.common.servlet.BaseServletContextHolder;
import cn.common.servlet.ContextType;
import cn.common.util.string.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class JsonResUtil {
    private static Logger logger = LoggerFactory.getLogger("JsonResUtil");
    private static final String JSON_DATE_FORMAT = "yyyy-MM-dd hh:mm:ss";
    private static ObjectMapper objectMapper = new ObjectMapper();

    public JsonResUtil() {
    }
    static {
        DateFormat dateFormat = new SimpleDateFormat(JSON_DATE_FORMAT);
        objectMapper.setDateFormat(dateFormat);
    }

    //方法一 返回的是对象
    public static void renderJson(ResultDO obj) {
        HttpServletResponse response = BaseServletContextHolder.setResponseContext(ContextType.JSON_TYPE, new String[0]);
        if (StringUtils.isNotEmpty(obj.getCode())) {
            response.setStatus(Integer.valueOf(200));
        } else {
            response.setStatus(500);
        }

        renderJson(response, obj);
    }

    public static void renderJson(HttpServletResponse response, Object obj) {
        BaseServletContextHolder.setResponseContext(response, ContextType.JSON_TYPE, new String[0]);
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            JsonGenerator jsonGenerator = objectMapper.getFactory().createGenerator(outputStream, JsonEncoding.UTF8);
            jsonGenerator.writeObject(obj);
        } catch (IOException var12) {
            logger.error("jackson解析对象错误!", var12);
        } finally {
            try {
                outputStream.flush();
                outputStream.close();
            } catch (IOException var11) {
                logger.error("response输出流关闭错误!", var11);
            }

        }
    }

    //方法二
    public static void ajaxJsonResponse(ResultDO obj) throws IOException  {
        HttpServletResponse response = BaseServletContextHolder.setResponseContext(ContextType.JSON_TYPE, new String[0]);
        Writer writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "application/json");
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
        response.setHeader("Pragma", "no-cache"); // HTTP 1.0
        response.setDateHeader("Expires", 0); // Proxies.
        try{
            writer = response.getWriter();
            String json = JSONObject.toJSONString(obj);
            writer.write(json);
        }finally{
            if(writer != null){
                writer.flush();
                writer.close();
            }
        }
    }
}

