package cn.webapp.controller.zx;


import cn.common.entity.CaptchaDTO;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.I0Itec.zkclient.exception.ZkException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;


@Api(description = "获取验证码")
@Controller
@RequestMapping("/")
public class CaptchaController {

    // 验证码图片的宽度。  104 36
    private int width = 60;
    // 验证码图片的高度。         
    private int height = 20;
    // 验证码字符个数         
    private int codeCount = 4;
    private int xIndex = 0;
    // 字体高度         
    private int fontHeight;
    private int codeY;
    char[] codeSequence = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
            'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    /**
     * 初始化验证图片属性
     */
    public void initxuan() {
        // 从web.xml中获取初始信息         
        // 宽度         
        String strWidth = "120";
        // 高度         
        String strHeight = "40";
        // 字符个数         
        String strCodeCount = "4";
        // 将配置的信息转换成数值         
        try {
            if (strWidth != null && strWidth.length() != 0) {
                width = Integer.parseInt(strWidth);
            }
            if (strHeight != null && strHeight.length() != 0) {
                height = Integer.parseInt(strHeight);
            }
            if (strCodeCount != null && strCodeCount.length() != 0) {
                codeCount = Integer.parseInt(strCodeCount);
            }
        } catch (NumberFormatException e) {
        }
        xIndex = width / (codeCount + 2);
        fontHeight = height - 4;
        codeY = height - 6;
    }

    @ApiOperation(value = "验证码接口")
    @RequestMapping(value = "comm/captcha", method = RequestMethod.GET)
    public void getCaptcha(@ModelAttribute CaptchaDTO captchaDTO, HttpServletRequest request, HttpServletResponse resp)
            throws IOException {
        int width = captchaDTO.getWidth() == null ? this.width : captchaDTO.getWidth();
        int height = captchaDTO.getHeight() == null ? this.height : captchaDTO.getHeight();
        initxuan();
        // 定义图像buffer         
        BufferedImage buffImg = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        Graphics2D g = buffImg.createGraphics();
        // 创建一个随机数生成器类         
        Random random = new Random();
        // 将图像填充为白色         
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
        // 创建字体，字体的大小应该根据图片的高度来定。         
        Font font = new Font("Fixedsys", Font.BOLD, fontHeight);
        // 设置字体。         
        g.setFont(font);
        // 画边框。         
        g.setColor(Color.WHITE);
        g.drawRect(0, 0, width - 1, height - 1);
        // 随机产生i条干扰线，使图象中的认证码不易被其它程序探测到。         
        g.setColor(Color.BLACK);
        for (int i = 0; i < 60; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            g.drawLine(x, y, x + xl, y + yl);
        }
        // randomCode用于保存随机产生的验证码，以便用户登录后进行验证。         
        StringBuffer randomCode = new StringBuffer();
        int red = 0, green = 0, blue = 0;
        // 随机产生codeCount数字的验证码。         
        for (int i = 0; i < codeCount; i++) {
            // 得到随机产生的验证码数字。         
            String strRand = String.valueOf(codeSequence[random.nextInt(36)]);
            // 产生随机的颜色分量来构造颜色值，这样输出的每位数字的颜色值都将不同。         
            red = random.nextInt(255);
            green = random.nextInt(255);
            blue = random.nextInt(255);
            // 用随机产生的颜色将验证码绘制到图像中。         
            g.setColor(new Color(red, green, blue));

            g.drawString(strRand, i * xIndex + 5, codeY);

            // 将产生的四个随机数组合在一起。         
            randomCode.append(strRand);
        }
        // 将验证码保存到redis中。省略

        // 禁止图像缓存。
        resp.setHeader("Pragma", "no-cache");
        resp.setHeader("Cache-Control", "no-cache");
        resp.setDateHeader("Expires", 0);
        resp.setContentType("image/jpeg");
        // 将图像输出到Servlet输出流中。         
        ServletOutputStream sos = resp.getOutputStream();
        ImageIO.write(buffImg, "jpeg", sos);
        sos.close();
    }


    @Resource(name = "captchaProducer")
    private Producer captchaProducer;

    @Resource(name = "captchaProducerMath")
    private Producer captchaProducerMath;

    @ApiOperation("com.github.penggle 验证码")
    @GetMapping("comm/captcha/penggle")
    public void getKaptchaImage(String type, HttpServletRequest request, HttpServletResponse response) {
        ServletOutputStream out = null;
        try {
            HttpSession session = request.getSession();
            response.setDateHeader("Expires", 0);
            response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
            response.addHeader("Cache-Control", "post-check=0, pre-check=0");
            response.setHeader("Pragma", "no-cache");
            response.setContentType("image/jpeg");

            String capStr = null;
            String code = null;
            BufferedImage bi = null;
            if ("math".equals(type)) {
                String capText = captchaProducerMath.createText();
                capStr = capText.substring(0, capText.lastIndexOf("@"));
                code = capText.substring(capText.lastIndexOf("@") + 1);
                bi = captchaProducerMath.createImage(capStr);
            } else if ("char".equals(type)) {
                capStr = code = captchaProducer.createText();
                bi = captchaProducer.createImage(capStr);
            } else {
                throw new ZkException("验证码异常");
            }
//            session.setAttribute(Constants.KAPTCHA_SESSION_KEY, code);
//            response.setHeader("captcha",code);
            out = response.getOutputStream();
            ImageIO.write(bi, "jpg", out);
            out.flush();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
