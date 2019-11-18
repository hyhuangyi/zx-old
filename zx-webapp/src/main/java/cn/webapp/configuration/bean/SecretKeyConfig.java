package cn.webapp.configuration.bean;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Author:zx
 * DateTime:2019/4/9
 **/
@ConfigurationProperties(prefix = "rsa.encrypt")
@Configuration
public class SecretKeyConfig {

    private String privateKey;

    private String publicKey;

    private String charset = "UTF-8";

    private boolean open = false;

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }
}
