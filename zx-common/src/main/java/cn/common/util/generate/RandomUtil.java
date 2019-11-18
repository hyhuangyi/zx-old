package cn.common.util.generate;

import java.util.Random;

/**
 * @Author luozx
 * @Date 2016/12/23 15:59.
 */
public class RandomUtil {
    private static char[] codeSequence1 = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    private static char[] codeSequence2 = {'0', '1', '3', '4', '5', '6', '7', '8', '9'};
    private static char[] codeSequence3 = {'!', '@', '#', '$', '%', '&', '*', '_'};

    /**
     * 获取固定位数的随机字符串
     *
     * @param codeCount // 随机密码字符个数
     * @return
     */
    public static String getRandom(int codeCount) {
        // 生成随机数
        Random random = new Random();
        StringBuffer randomCode = new StringBuffer();// randomCode记录随机产生的验证码
        // 随机产生codeCount个字符的验证码
        for (int i = 1; i <= codeCount; i++) {
            String strRand = "";
            if (i % 3 == 0) {
                strRand = String.valueOf(codeSequence3[random.nextInt(codeSequence3.length)]);
            } else if (i % 2 == 0) {
                strRand = String.valueOf(codeSequence2[random.nextInt(codeSequence2.length)]);
            } else {
                strRand = String.valueOf(codeSequence1[random.nextInt(codeSequence1.length)]);
            }
            randomCode.append(strRand); // 将产生的四个随机数组合在一起
        }
        return randomCode.toString();
    }

    /**
     * 获取固定位数的随机字符串
     *
     * @param codeCount // 随机密码字符个数
     * @return
     */
    public static Integer getRandomInt(int codeCount) {
        // 生成随机数
        Random random = new Random();
        StringBuffer randomCode = new StringBuffer();// randomCode记录随机产生的验证码
        // 随机产生codeCount个字符的验证码
        for (int i = 1; i <= codeCount; i++) {
            String strRand = "";
            if (i == 1) {
                strRand = String.valueOf(random.nextInt(9) + 1);
            } else {
                strRand = String.valueOf(codeSequence2[random.nextInt(codeSequence2.length)]);
            }
            randomCode.append(strRand); // 将产生的四个随机数组合在一起
        }
        return Integer.parseInt(randomCode.toString());
    }

    public static void main(String[] args) {
        System.out.println(getRandomInt(5));
    }
}
