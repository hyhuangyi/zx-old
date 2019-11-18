package cn.common.util.jwt;

import cn.common.util.redis.RedisUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录Token的生成和解析
 * 
 */
public class JwtToken {

	/** token秘钥，请勿泄露，请勿随便修改 backups:JKKLJOoasdlfj */
	public static final String SECRET = "<.?*&#@<??+_+Y1/Pm*%$";
	/** token 过期时间: 1天 */
	public static int calendarField = Calendar.MINUTE;
	public static  int calendarInterval = 60*24;

	/**
	 * JWT生成Token.<br/>
	 * 
	 * JWT构成: header, payload, signature
	 * 
	 * @param userId
	 *            登录成功后用户user_id, 参数user_id不可传空
	 */
	public static String createToken(String userId,String loginType) throws Exception {
		Date iatDate = new Date();
		// expire time
		Calendar nowTime = Calendar.getInstance();
		nowTime.add(calendarField, calendarInterval);
		Date expiresDate = nowTime.getTime();

		// header Map
		Map<String, Object> map = new HashMap<>();
		map.put("alg", "HS256");
		map.put("typ", "JWT");

		// build token
		// param backups {iss:Service, aud:APP}
		String token = JWT.create().withHeader(map) // header
				.withClaim("iss", "Service") // payload
				.withClaim("aud", loginType).withClaim("userId", null == userId ? null : userId)
				.withIssuedAt(iatDate) // sign time
				.withExpiresAt(expiresDate) // expire time
				.sign(Algorithm.HMAC256(SECRET)); // signature
		return token;
	}
	/**
	 * 
	 * @param userId 用户id
	 * @param time 指定失效时间 
	 * @param loginType:区分是安卓还是web  web/ app
	 * @return
	 */
	public static String createTokenByUserId(String userId,int time,String loginType) {

		String token=null;
		try {
			token=	createToken(userId,loginType);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			token=null;
		}
		RedisUtil.set(loginType+":"+userId, token, time);
		return token;
	}

	/**
	 * 解密Token
	 * 
	 * @param token
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Claim> verifyToken(String token) {
		Map<String, Claim> map =null;
		JWTVerifier verifier;
		try {
			verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
			DecodedJWT jwt = verifier.verify(token);
			map=jwt.getClaims();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			map=null;
		}
		return map ;
	}

	/**
	 * 根据Token获取redistoken
	 * 
	 * @param token
	 * @return user_id
	 */
	public static String getTokenKey(String token) {
		Map<String, Claim> claims = verifyToken(token);
		String tokenKey=null;
		if(claims!=null) {
			Claim userId_Claim = claims.get("userId");
			Claim aud_Claim = claims.get("aud");
			tokenKey =aud_Claim.asString()+":"+userId_Claim.asString();
		}
		return tokenKey;
	}

	public static String getToken(String token) {
		String tokenKey=getTokenKey(token);
		String tokenTo=null;
		if(tokenKey!=null) {
			if(RedisUtil.hasKey(tokenKey)) {
				tokenTo =RedisUtil.get(tokenKey).toString();
			}
		}
			return tokenTo;
		}

		public static void main(String[] args) throws Exception {
			String token=	JwtToken.createToken("1", "web");

			String keyString=JwtToken.getTokenKey(token+1);

			System.out.println(keyString);
		}


	}
