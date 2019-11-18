package cn.common.util.comm;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.apache.commons.lang3.StringUtils;
import java.util.regex.Pattern;

/**
 * 描述:
 * 汉字转汉语拼音util
 *
 * @author zad
 * @create 2018-11-01 12:25
 */
public final class PinYinUtil {
    /**
     * 汉语拼音format
     */
    private static final HanyuPinyinOutputFormat PINYIN_OUTPUT_FORMAT = new HanyuPinyinOutputFormat();

    /**
     * 汉字对应正则表达式 pattern
     */
    private static final Pattern CHINESE_CHARACTER_PATTERN = Pattern.compile("[\\u4E00-\\u9FA5]");

    /**
     * 字母对应正则
     */
    private static final Pattern CHARACTER_PATTERN = Pattern.compile("[A-Za-z\\d]");

    static {
        PINYIN_OUTPUT_FORMAT.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        PINYIN_OUTPUT_FORMAT.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        PINYIN_OUTPUT_FORMAT.setVCharType(HanyuPinyinVCharType.WITH_V);
    }

    private PinYinUtil() {
        throw new AssertionError("Util禁止反射实例化");
    }

    /**
     * 将chineseCharacter中汉字转为拼音,其余所有字符直接略过,
     * 若需要额外处理,则需要额外判断
     *
     * @param chineseCharacter
     * @return
     */
    public static String getPingYin(String chineseCharacter) {
        if (StringUtils.isBlank(chineseCharacter)) {
            return StringUtils.EMPTY;
        }
        StringBuilder sb = new StringBuilder();
        try {
            for (char cc : chineseCharacter.toCharArray()) {
                // 遇到无法解析汉字则跳过,防止空指针
                String[] res = PinyinHelper.toHanyuPinyinStringArray(cc, PINYIN_OUTPUT_FORMAT);
                boolean flag = CHINESE_CHARACTER_PATTERN.matcher(String.valueOf(cc)).matches()
                        && (res != null);
                if (flag) {
                    sb.append(res[0]);
                }
                // 如果是字母,直接添加
                if (CHARACTER_PATTERN.matcher(String.valueOf(cc)).matches()) {
                    sb.append(cc);
                }
            }
        } catch (BadHanyuPinyinOutputFormatCombination e1) {
            e1.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * 将chineseCharacter中汉字转为拼音并获取首字母,其余所有字符直接略过,
     *
     * @param chineseCharacter
     * @return
     */
    public static String getPinYinInitialLetter(String chineseCharacter) {
        if (StringUtils.isBlank(chineseCharacter)) {
            return StringUtils.EMPTY;
        }
        StringBuilder sb = new StringBuilder(chineseCharacter.length());
        for (char cc : chineseCharacter.toCharArray()) {
            // 遇到无法解析汉字则跳过,防止空指针
            String[] res = PinyinHelper.toHanyuPinyinStringArray(cc);
            boolean flag = CHINESE_CHARACTER_PATTERN.matcher(String.valueOf(cc)).matches()
                    && (res != null);
            if (flag) {
                sb.append(res[0].charAt(0));
            }

            // 如果是字母数字,直接添加
            if (CHARACTER_PATTERN.matcher(String.valueOf(cc)).matches()) {
                sb.append(cc);
            }
        }
        return sb.toString();
    }
}
