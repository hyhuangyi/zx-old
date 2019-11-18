package cn.common.util.generate;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;


/**
 * 生成码工具类
 * @author huangy
 *
 */

public final class CodeUtil
{

    private CodeUtil()
    {
    }

    /**
     * java自动补零得到一定位数的数字字符串
     * @param oriString
     * @param length
     * @return
     */
    public static String getLengthString(String oriString, int length) {
        String retString = oriString;
        if (oriString.length() == length) {
            return retString;
        } else {
            for (int i = 0; i < length - oriString.length(); i++) {
                retString = "0" + retString;
            }
        }
        return retString;
    }
    /**
     * 生成6位注册邀请码
     * @param num 码位数
     * @return String
     */
    public static String generateInviteCode()
    {
        return generateCode(6);
    }
    
    /**
     * 生成4位登陆校验码 
     * @return String
     * @since loan_v1.0
     */
    public static String generateLoginValidateCode() {
        return generateCode(4);
    }
    
    /**
     * 生成码
     * @param num 码位数
     * @return String
     */
    public static String generateCode(int num)
    {
        String source = "23456789ABCDEFGHJKLMNPQRSTUVWXYZ";// 去掉1和i ，0和o
        StringBuilder sb = new StringBuilder();
        for (int j = 0; j < num; j++)
        {
            String nowStr = String.valueOf(source.charAt((int) Math.floor(Math.random() * source.length())));
            sb.append(nowStr);
        }
        return sb.toString();
    }
    
    public static BufferedImage generateCodeImage(String code) {
        int width = 60, height = 30;
        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        Random random = new Random();
        g.setColor(getRandColor(200, 250));
        g.fillRect(0, 0, width, height);

        g.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        g.setColor(getRandColor(160, 200));
        for (int i = 0; i < 155; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            g.drawLine(x, y, x + xl, y + yl);
        }
        char[] codeChar = code.toCharArray();
        for (int i = 0; i < codeChar.length; i++) {
            g.setColor(new Color(20 + random.nextInt(110), 20 + random
                    .nextInt(110), 20 + random.nextInt(110)));
            g.drawString(codeChar[i] + "", 13 * i + 6, 25);
        }
        return image;
    }
    
    public static Color getRandColor(int fc, int bc) {
        Random random = new Random();
        if (fc > 255)
            fc = 255;
        if (bc > 255)
            bc = 255;
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    } 
    
    public static BufferedImage generateVaildateCodeImage(String code) {
        return generateCodeImage(code);
    }

    public static void main(String[] args) throws Exception{
        File outputfile  = new File("f:/save.png");
        BufferedImage bm= generateVaildateCodeImage(generateCode(4));
        ImageIO.write(bm,"png",outputfile);

    }
}
