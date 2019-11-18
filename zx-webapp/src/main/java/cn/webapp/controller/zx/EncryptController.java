package cn.webapp.controller.zx;

import cn.common.entity.City;
import cn.common.exception.ZXException;
import cn.common.util.encrypt.AESUtils;
import cn.common.util.encrypt.DESUtils;
import cn.common.util.encrypt.RSAUtils;
import cn.webapp.aop.annotation.Decrypt;
import cn.webapp.aop.annotation.Encrypt;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * @author huangy
 * @date 2019/8/1 18:20
 */
@Api(description = "加解密测试")
@RestController
@RequestMapping("/comm")
public class EncryptController {
    public static String publicKey="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCKPy5OLOYwVElx-ewHcvP6lVPhCxdQPz9zIysBfKythrvWcYENMqmzYpdvNWLkM01O4MCCva3-zYDK_nhNJGhbtXNDbPppZP2aKczYX9fkpAu864aLutPBq_FbT86-LHWSMdtQrDEGJfbJh83a7mqYeVS-LQSF0vXcQh2x3HjgLQIDAQAB";
    public static String privateKey="MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIo_Lk4s5jBUSXH57Ady8_qVU-ELF1A_P3MjKwF8rK2Gu9ZxgQ0yqbNil281YuQzTU7gwIK9rf7NgMr-eE0kaFu1c0Ns-mlk_ZopzNhf1-SkC7zrhou608Gr8VtPzr4sdZIx21CsMQYl9smHzdruaph5VL4tBIXS9dxCHbHceOAtAgMBAAECgYBsvFMUg_Ytzy3Rauy90ymZT7igyhN1N7oVOnUJ_2DmP5IgkjgaBF47zPElNE1rBfrpbpecfcqMHmENyOcgbeFyEINECz70kEnLn-w62LL84adaq_P-e7shOxg2U_JYg93iY0E5chuzxDK9hSIWiuGrn0yWsdbC4Ecjjq5Osj8EAQJBAMKb-PXAtFybXNrqfNH7jKkNYSVQ36YB5Mi1RRV-Y02DEIADyqRY731vD-DcZFOijX86yeg2tsoZpZ4WDvUWey0CQQC1243gVUQubdnpxnKsPZtJPLROxVOQCBr8U9LnPRGAh7qgzeZ4alkPDYUUPUvFI2a0l2dr08KDpyYeXcunpxkBAkAffPUvblrAkTLZCQ_hOf5sbAnH6-oEBlJqV0oyTBztMcO9st9a8Djv5sR-8aoaDjcu2uhIdnHlCXlOtur8pQKRAkB4tGh_7LhbhMgtlW1Ji36FZshavDiRkf-vWUfT-cGjKPOjheaiKBxClHuvClw_Vhb2aMPZWu1xisEPSLxC4_MBAkEAp9nrx-jNj0WBZPjI0Xl98iopUV-vPR6QL6P-3Ly5P0z1mQSV4PzhZtEmkLrXRoYxIt0GqV7qB-KCQZ03-j2Ung";

    @ApiOperation("AES测试")
    @GetMapping("aes")
    public City AES(String data){
        String json;
        try {
            json= AESUtils.decrypt(data,AESUtils.KEY);
        } catch (Exception e) {
            throw new ZXException("AES解密异常");
        }
        City city=JSONObject.parseObject(json,City.class);
        return city;
    }

    @ApiOperation("DES测试")
    @GetMapping("des")
    public City DES(String data){
        String json;
        try {
          json=DESUtils.decrypt(data,AESUtils.KEY);
        } catch (Exception e) {
            throw new ZXException("DES解密异常");
        }
        City city=JSONObject.parseObject(json,City.class);
        return city;
    }

    @ApiOperation("RSA测试")
    @GetMapping("rsa")
    public City RSA(String data){
        String json;
        try {
            json= RSAUtils.privateDecrypt(data,RSAUtils.getPrivateKey(privateKey));
        } catch (Exception e) {
            throw new ZXException("RSA解密异常");
        }
        City city=JSONObject.parseObject(json,City.class);
        return city;
    }

    @Decrypt
    @ApiOperation("对传过来的加密参数解密")
    @PostMapping("/decryption")
    public City Decryption(@RequestBody City city){
        return city;
    }

    @Encrypt
    @ApiOperation("对返回值进行加密")
    @GetMapping("/encryption")
    public City encryption(){
        City city = new City();
        city.setCode("10000");
        city.setFullName("杭州");
        return city;
    }

    public static void main(String[] args) throws Exception{
        City city=new City();
        city.setCode("10000");
        city.setFullName("杭州");
        String json= JSONObject.toJSONString(city);

        //89DD9CAC8DBCCF998E2341DF8CCF5335EA9BF43F4FE2585451FF62E4001CDDCFD6FCF361E918D1338A34925D46FF8410
        System.out.println(AESUtils.encrypt(json,AESUtils.KEY));
        //uvlMMvh7WLtGi2vGowW6UWvqW/qQX0fuYzZJVR+iviVQlF2Vw4Nr+g==
        System.out.println(DESUtils.encrypt(json,DESUtils.KEY));

        //私钥加密 值固定
        System.out.println(RSAUtils.privateEncrypt(json,RSAUtils.getPrivateKey(privateKey)));
        //公钥加密 值不定
        System.out.println(RSAUtils.publicEncrypt(json,RSAUtils.getPublicKey(publicKey)));
        //私钥解密
        System.out.println(RSAUtils.privateDecrypt("dAZsnSyPOAuYLF8mcnQIXUtPmKFTbHQR2CTBwI23zs1MnL9iIgefXY1i34ffqFKKrEnkpijd4b1LjNFQCJG-JtflirjoJbwN4zkjHf7-_kmkywK-S5WAl8vcWdlv3cVksyLr1CDa8w5-AZtIILW08E1W60QRalTsVzdApQxb6QI",RSAUtils.getPrivateKey(privateKey)));
        //公钥解密
        System.out.println(RSAUtils.publicDecrypt("TX2DAhabm6Mgvvfono5JE-gxGa1llyA_UR-IUBHdtRIbfQch3qbQfO6-iB9UVA6uSzUFzxPyOKGpP4NoUjSh-AfZqU5R74rV0sFO8OJuiFR01LHmKFPY39kZTbCQst9LNPpwWZquA7_CPA8ynO0qD8BWbTKHXlOnCTPVunHjhwo",RSAUtils.getPublicKey(publicKey)));
    }
}
