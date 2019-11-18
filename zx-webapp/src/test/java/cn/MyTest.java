package cn;

import cn.common.dao.mapper.CityDao;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.UnsupportedEncodingException;

/**
 * Created by huangYi on 2018/8/18
 **/
public class MyTest extends BaseJunitTest {
    @Autowired
    private CityDao cityDao;
    @Test
    public void testCity(){
        logger.info(cityDao.queryAllCity().toString());
    }
    @Test
    public  void testStr(){
        String str="子轩是是是是德";
        try {
            /**
             *String.getBytes(String decode)方法会根据指定的decode编码返回某字符串在该编码下的byte数组表示。
             *而与getBytes相对的，可以通过new String(byte[], decode)的方式来还原这个“中”字时，
             * 这个new String(byte[], decode)实际是使用decode指定的编码来将byte[]解析成字符串。
             * */
            byte b1[]=str.getBytes("utf-8");
            byte b2[]=str.getBytes("gbk");
            /**
             * ISO8859-1编码的编码表中，根本就没有包含汉字字符，当然也就无法通过"中".getBytes("ISO8859-1");
             *来得到正确的“中”字在ISO8859-1中的编码值了，所以再通过new String()来还原就无从谈起了*
             */
            byte b3[]=str.getBytes("ISO8859-1");

            System.out.println(new String(b1,"utf-8"));
            System.out.println(new String(b2,"gbk"));
            System.out.println(new String(b3,"ISO8859-1"));
            /**
             * 有时候，为了让中文字符适应某些特殊要求（如http header头要求其内容必须为iso8859-1编码），
             * 可能会通过将中文字符按照字节方式来编码的情况，如String s_iso88591 = new String("中".getBytes("UTF-8"),"ISO8859-1")，
             * 这样得到的s_iso8859-1字符串实际是三个在 ISO8859-1中的字符，在将这些字符传递到目的地后，
             * 目的地程序再通过相反的方式String s_utf8 = new String(s_iso88591.getBytes("ISO8859-1"),"UTF-8")来得到正确的中文汉字“中”。
             * 这样就既保证了遵守协议规定、也支持中文。
             */
            String s=new String (str.getBytes("utf8"),"iso8859-1");
            String s2=new String(s.getBytes("iso8859-1"),"utf8");
            System.out.println("s2="+s2);

            /**
             * 测试str.getBytes()默认的编码
             * */
            System.out.println(new String(str.getBytes(),"utf8"));

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void test(){
        int j=0;
        for(int i=0;i<100;i++){
            System.out.println("i="+i);
            j=i++;
            System.out.println(i);
            System.out.println("j="+j);
        }
    }

}
