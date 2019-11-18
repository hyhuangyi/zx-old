package cn.common.util.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import javax.annotation.PostConstruct;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * 分布式锁工具类 3种
 *
 */
@Component
public class RedisLockUtil {
    private static Logger log = LoggerFactory.getLogger(RedisLockUtil.class);
    private static final String LOCK_SUCCESS = "OK";
    private static final String SET_IF_NOT_EXIST = "NX";
    private static final String SET_WITH_EXPIRE_TIME = "PX";
    private static final Long RELEASE_SUCCESS = 1L;
    /**
     * 因为静态变量并不属于对象的属性，而是属于类的属性
     * 而Spring则是基于对象的属性进行依赖注入的，所以直接注入这样做肯定不行
     * 会报NullPointerException
     * 用@PostConstruct得以解决 或者set方法上注入 注意set方法不要是静态的
     * 静态对象注入参考：
     * https://blog.csdn.net/u010003835/article/details/79223105
     * https://blog.csdn.net/twypx/article/details/80435332
     */
    private  static  RedisTemplate<Object, Object> redisTemplate;
    @Autowired
    private  RedisTemplate<Object,Object> redisTemplate_copy;
    /**
     * 初始化 init
     */
    @PostConstruct
    public void init(){
      RedisLockUtil.redisTemplate=redisTemplate_copy;
    }
    /*@Autowired
    public  void setRedisTemplate(RedisTemplate<Object, Object> redisTemplate) {
        RedisLockUtil.redisTemplate = redisTemplate;
    }*/

    /**********************方法一*****************************/
    /**
     * https://www.cnblogs.com/seesun2012/p/9214653.html
     * 加锁
     * @param key
     * @param expire
     * @return
     */
    public static boolean lock(String key, int expire) {
        long value = System.currentTimeMillis() + expire;
        //获得锁
        if(RedisUtil.setNx(key, String.valueOf(value))) {
            return true;
        }
        //未获得锁则判断是否超时，如果此时key被删了，则返回0
        long oldExpireTime = Long.parseLong(RedisUtil.get(key,"0"));
        if(oldExpireTime < System.currentTimeMillis()) {//超时或key被释放
            //超时
            long newExpireTime = System.currentTimeMillis() + expire;
            //设置新值并返回旧值
            String val=RedisUtil.getSet(key, String.valueOf(newExpireTime));
            if(val==null){
                val="0";
            }
            System.out.println(Thread.currentThread().getName()+"-"+oldExpireTime+"-"+val);
            long currentExpireTime = Long.parseLong(val);
            //如果返回的currentExpireTime和旧值相等说明是这次设置的
            if(currentExpireTime == oldExpireTime) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * 释放锁
     */
    public static void releaseLock(String key) {
        long oldExpireTime = Long.parseLong(RedisUtil.get(key).toString());
        if(oldExpireTime > System.currentTimeMillis()) {
            RedisUtil.del(key);
        }
    }


    /*****************************方法二***************************/

    /**
     *  https://blog.csdn.net/jkdcoach/article/details/80718849
     * 尝试获取分布式锁
     * @param jedis Redis客户端
     * @param lockKey 锁
     * @param requestId 请求标识
     * @param expireTime 超期时间
     * @return 是否获取成功
     */
    public static boolean tryLock(Jedis jedis, String lockKey, String requestId, int expireTime) {
        long tryTime=2000l;
        int times = (int) (tryTime/200 + (tryTime%200 > 0 ? 1 : 0));
        String result = jedis.set(lockKey, requestId, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);
        int tryTimes = 0;
        if (LOCK_SUCCESS.equals(result)) {
            return true;
        }
        while(++tryTimes < times) {
            if(LOCK_SUCCESS.equals(jedis.set(lockKey, requestId, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime))) {
                return true;
            }
        }
        return false;

    }

    /**
     * 释放分布式锁
     * @param jedis Redis客户端
     * @param lockKey 锁
     * @param requestId 请求标识
     * @return 是否释放成功
     */
    public static boolean relaceLock(Jedis jedis, String lockKey, String requestId) {

        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        Object result = jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(requestId));

        if (RELEASE_SUCCESS.equals(result)) {
            return true;
        }
        return false;

    }


    /***********************方法三**************************/
    /**
     * 	尝试加锁，默认尝试2秒 10次
     * @param key
     * @return
     */
    public static boolean tryLock(String key) {
        return tryLock(key,1000*2);
    }

    /**
     * 尝试加锁  2000ms 默认尝试10次
     * @param key
     * @param tryTime 尝试获取锁的时间,时间毫秒
     * @return
     */
    public static boolean tryLock(String key,long tryTime) {
        int times = (int) (tryTime/200 + (tryTime%200 > 0 ? 1 : 0));
        int tryTimes = 0;
        if(lockNow(key)) {
            return true;
        }
        while(++tryTimes < times) {
            if(lockNow(key)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取锁
     * @param key
     * @return
     */
    public static boolean lockNow(String key) {
        try {
            boolean lock =redisTemplate.opsForValue().setIfAbsent(key, "1");
            if(lock) {
                redisTemplate.expire(key, 20, TimeUnit.MINUTES);
            }
            return lock;
        }catch (Exception e) {
            log.error(e.getMessage(),e);
            return false;
        }
    }

    /**
     * 解锁
     * @param key
     */

    public static void unLock(String key) {
        final String finalKey = key;
        redisTemplate.execute(new RedisCallback<Boolean>() {

            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                try {
                    byte[] keyb = finalKey.getBytes("UTF-8");
                    connection.del(keyb);
                    return true;
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return false;
            }
        });
    }

}