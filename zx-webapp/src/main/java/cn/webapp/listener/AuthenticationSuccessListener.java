package cn.webapp.listener;

import cn.common.entity.MyUserDetails;
import cn.common.entity.Token;
import cn.common.util.jwt.JwtUtil;
import cn.common.util.redis.RedisUtil;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;
/**
 * spring security 登录成功的事件
 * 登录互踢方案
 *   第一种方案
 *      redis中存的key(key不变,token且不刷新)对应的的token值与所传的token比较,不同表示被踢了
 *   第二种方案
 *     登录成功后（同一个账号每次登录生成的key都不一样（uuid）），判断之前是否有人登陆过(logSuccessKey get)，如果有则踢掉之前登录的tokenId(tokenKey del)，
 * 并记录被踢的tokenId(kickOutKey 形如kitOut:token:pc:72455fd8-91a4-473d-bf01-516f955521db:18705621249)
 * 如果没有则记录登录成功的tokenId(logSuccessKey set)
 */
@Component
public class AuthenticationSuccessListener implements ApplicationListener<AuthenticationSuccessEvent> {

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent authenticationSuccessEvent) {
        final MyUserDetails userDetails = (MyUserDetails) authenticationSuccessEvent.getAuthentication().getPrincipal();
        //token
        Token token=userDetails.getToken();
        String tokenId = JwtUtil.tokenKey(token);
        String logSuccessKey=JwtUtil.loginSuccessKey(token);
        //查看之前是否有人登录
        Object o=RedisUtil.get(logSuccessKey);
        String oldTokenId=o==null?null:o.toString();
        if(oldTokenId!=null){//此账号已有人登录
            //踢掉上个用户
            RedisUtil.del(oldTokenId);
            //记录被踢用户
            RedisUtil.set(JwtUtil.kickOutKey(token),0,JwtUtil.EXPIRATIONTIME/1000);
        }
        //登录成功存入redis 单位秒
        RedisUtil.set(logSuccessKey,tokenId,JwtUtil.EXPIRATIONTIME/1000);
    }
}
