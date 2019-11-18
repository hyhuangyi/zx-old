/**
 * Java版短网址(ShortUrl)的算法
 * 1.  将长网址用md5算法生成32位签名串，分为4段,，每段8个字符。
 * 2.  对这4段循环处理，取每段的8个字符, 将他看成16进制字符串与0x3fffffff(30位1)的位与操作，超过30位的忽略处理。多了也没用因为下面要分成6段  嘿嘿正好取整。注意用Long型变量（长度问题  你懂得）
 * 3.  将每段得到的30位字符（后台以long十进制显示）又分成6段，通过移位运算将每5位分别与字符数组求与运算（0x0000003D），得到其在字符数组中的索引并取出拼串。
 * 4.  这样一个md5字符串可以获得4个6位串，取里面的任意一个就可作为这个长url的短url地址。
 * 跳转原理：当我们生成短链接之后，只需要在表中（数据库或者NoSql ）存储原始链接与短链接的映射关系即可。当我们访问短链接时，只需要从映射关系中找到原始链接，即可跳转到原始链接。
 * eg:http://6du.in/0W13as
 * 备注(六度网址：http://www.6du.in/)
 */
package cn.common.util.generate;

import cn.common.util.encrypt.Md5Util;

public class ShortUrlGenerator {

    public static String[] shortUrl(String url) {
        // 可以自定义生成 MD5 加密字符传前的混合 KEY
        String key = "libotao";
        // 要使用生成 URL 的字符
        String[] chars = new String[]{"a", "b", "c", "d", "e", "f", "g", "h",
                "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t",
                "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
                "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H",
                "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
                "U", "V", "W", "X", "Y", "Z"
        };
        // 对传入网址进行 MD5 加密
        String sMD5EncryptResult = (Md5Util.getMD5(key + url));
        String hex = sMD5EncryptResult;
        String[] resUrl = new String[4];
        //得到 4组短链接字符串
        for (int i = 0; i < 4; i++) {
            // 把加密字符按照 8 位一组 16 进制与 0x3FFFFFFF 进行位与运算
            String sTempSubString = hex.substring(i * 8, i * 8 + 8);
            // 这里需要使用 long 型来转换，因为 Inteper .parseInt() 只能处理 31 位 , 首位为符号位 , 如果不用 long ，则会越界
            long lHexLong = 0x3FFFFFFF & Long.parseLong(sTempSubString, 16);
            String outChars = "";
            //循环获得每组6位的字符串
            for (int j = 0; j < 6; j++) {
                // 把得到的值与 0x0000003D 进行位与运算，取得字符数组 chars 索引
//(具体需要看chars数组的长度   以防下标溢出，注意起点为0)
                long index = 0x0000003D & lHexLong;
                // 把取得的字符相加
                outChars += chars[(int) index];
                // 每次循环按位右移 5 位
                lHexLong = lHexLong >> 5;
            }
            // 把字符串存入对应索引的输出数组
            resUrl[i] = outChars;
        }
        return resUrl;
    }

    public static void main(String[] args) {
        // 长连接： http://www.young-sun.com
        // 新浪解析后的短链接为： http://***/Nvqqem
        String sLongUrl = "http://www.young-sun.com"; // 3BD768E58042156E54626860E241E999
        String[] aResult = shortUrl(sLongUrl);
        // 打印出结果
        for (int i = 0; i < aResult.length; i++) {
            System.out.println("The string [" + i + "] is " + aResult[i]);
        }
    }
}
