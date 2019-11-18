package cn.common.util.generate;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.IOUtils;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;

/**
 * @author huangy
 * @date 2019/7/29 14:46
 */
public class SinaDwz {
    public static String createShortUrl(String longUrl){
        BufferedReader reader = null;
        try {
            longUrl = URLEncoder.encode(longUrl, "GBK");
            URL url = new URL("http://api.t.sina.com.cn/short_url/shorten.json?source=2546260130&url_long=" + longUrl);
            InputStream iStream = url.openStream();
            reader = new BufferedReader(new InputStreamReader(iStream));
            String jsonStr = reader.readLine();
            JSONObject jsonObj = JSONObject.parseArray(jsonStr).getJSONObject(0);
            return jsonObj.getString("url_short");
        } catch (Exception e) {
            return longUrl;
        } finally {
            IOUtils.closeQuietly(reader);
        }
    }
    public static void main(String[] args) {
        System.out.println(createShortUrl("http://test.newloan.app.souguakeji.com/web/regist.html?channel=0c3fcc2b5734aef595297bf0c3e9d715"));
    }
}
