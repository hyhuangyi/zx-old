package cn.common.util.encrypt;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.SecureRandom;

/**
 * @author huangy 对称加密（symmetric）AES
 * @date 2019/7/17 15:10
 */
public class AESUtils {
    private static final String KEY_ALGORITHM = "AES";
    private static final String CHARSET_NAME = "utf-8";//编码
    public static final String KEY = "1qaz2wsx3edc4rfv1";//默认的密码 key

    /**
     * 加密
     *
     * @param content 需要被加密的字符串
     * @return 密文
     */
    public static String encrypt(String content, String key) throws Exception {
        SecretKeySpec secretKeySpec = createSecretKeySpec(key);
        if (secretKeySpec == null) {
            return null;
        }
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);// 创建密码器
        byte[] byteContent = content.getBytes(CHARSET_NAME);
        try {
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);// 初始化为加密模式的密码器
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        byte[] result = cipher.doFinal(byteContent);// 加密
        return AESUtils.parseByte2HexStr(result);

    }

    /**
     * 解密AES加密过的字符串
     *
     * @param strResult AES加密过过的内容
     * @return 明文
     */
    public static String decrypt(String strResult, String key) throws Exception {
        byte[] content = AESUtils.parseHexStr2Byte(strResult);
        SecretKeySpec secretKeySpec = createSecretKeySpec(key);
        if (secretKeySpec == null) {
            return null;
        }
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);// 创建密码器
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);// 初始化为解密模式的密码器
        byte[] result = cipher.doFinal(content);
        return new String(result); // 明文

    }

    /**
     * 生成密钥key对象
     *
     * @param key
     * @return
     */
    private static SecretKeySpec createSecretKeySpec(String key) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance(KEY_ALGORITHM);// 创建AES的Key生产者
        kgen.init(128, new SecureRandom(key.getBytes()));// 利用用户密码作为随机数初始化出
        // 128位的key生产者
        //加密没关系，SecureRandom是生成安全随机数序列，password.getBytes()是种子，只要种子相同，序列就一样，所以解密只要有password就行
        SecretKey secretKey = kgen.generateKey();// 根据用户密码，生成一个密钥
        byte[] enCodeFormat = secretKey.getEncoded();// 返回基本编码格式的密钥，如果此密钥不支持编码，则返回
        SecretKeySpec secretKeySpec = new SecretKeySpec(enCodeFormat, KEY_ALGORITHM);// 转换为AES专用密钥
        return secretKeySpec;
    }


    /**
     * 将二进制转换成16进制
     *
     * @param buf
     * @return
     */
    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 将16进制转换为二进制
     *
     * @param hexStr
     * @return
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    public static void main(String[] args) throws Exception{
        String content = "子轩";
        System.out.println("加密之前：" + content);
        // 加密
        String hexStrResult = AESUtils.encrypt(content, KEY);
        System.out.println("加密：" + hexStrResult);
        // 解密
        String decrypt = AESUtils.decrypt(hexStrResult, KEY);
        System.out.println("解密：" + decrypt);
    }

}
