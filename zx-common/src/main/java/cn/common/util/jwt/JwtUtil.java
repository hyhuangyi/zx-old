package cn.common.util.jwt;

import cn.common.entity.MyUserDetails;
import cn.common.entity.Token;
import cn.common.util.redis.RedisUtil;
import com.alibaba.fastjson.JSONObject;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * Created by huangYi on 2018/8/26
 **/
public class JwtUtil {
    //token heard头
    public static final String AUTHORIZATION = "authorization";
    //签名key
    public static final String SIGNINGKEY = "springBoot-demo";
    //过期时间 2小时 以毫秒计算
    public static final long EXPIRATIONTIME = 7200 * 1000L;

    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    /**
     * 生成/刷新 token str
     * @param username
     * @return
     */
    public static String getToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
                .signWith(SignatureAlgorithm.HS512, SIGNINGKEY)
                .compact();
    }

    public static Claims parseToken(String token) throws Exception {

        // 解析 Token
        Claims claims = Jwts.parser()
                // 验签
                .setSigningKey(SIGNINGKEY)
                .parseClaimsJws(token)
                .getBody();
        return claims;
    }

    /*获取redis token key*/
    public static String tokenKey(Token token){
        /*uuid的值每次登录都不一样 互踢需要下面2个key配合*/
        return "token:pc:"+token.getUuid()+":"+token.getPhone();
    }

    /**
     * 登录成功的key
     * @param token
     * @return
     */
    public static String loginSuccessKey(Token token){
        return "token:pc:loginSuccess:"+token.getPhone();
    }

    /**
     * 被踢的key
     * @param token
     * @return
     */
    public static String kickOutKey(Token token){
        String tokenId = tokenKey(token);
        return "kitOut:"+tokenId;
    }

    /**
     * 解析token
     *
     * @param token
     * @return
     */
    public static Token getJwtToken(String token) throws Exception {
        Claims claims = JwtUtil.parseToken(token);
        // String user = claims.getSubject();
        //if (null != user) {
        //    return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
        // }
        return JSONObject.parseObject(claims.getSubject(), Token.class);
    }

    /**
     * 从redis中读取token并返回User
     *
     * @return
     */
    public static MyUserDetails getUserDetail(Token token) {
        Object o = RedisUtil.get(tokenKey(token));
        String tokenStr=o==null?null:o.toString();
        Token t = JSONObject.parseObject(tokenStr, Token.class);
        if (t == null) {
            return null;
        }
        MyUserDetails userDetails = new MyUserDetails(t);
        return userDetails;
    }

    /**
     * 判断当前用户是否被T下线过
     *
     * @return
     */
    public static boolean isKickOut(Token token) {
        Object o = RedisUtil.get(kickOutKey(token));
        String val=o==null?null:o.toString();
        if (val != null) {
            //60秒后过期，删除此状态
            RedisUtil.set(kickOutKey(token), 0, 60);
            return true;
        }
        return false;
    }

    /**
     * 刷新token时间
     * @param token
     * @return
     */
    public static String refreshToken(Token token){
        //刷新redis失效时间
        saveTokenInfo(token);
        //刷新jwt失效时间
        return getToken(JSONObject.toJSONString(token));
    }

    /**
     * 保存token到redis中
     */
    public static void saveTokenInfo(Token token){
        //防止并发时token过期，手动增加一点延迟
        RedisUtil.set(tokenKey(token),JSONObject.toJSONString(token),EXPIRATIONTIME/1000+60L);
    }

}
