package com.excelsecu.cmsystem.shiro;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;

public class JwtUtil {

    // JWT-account
    public static final String ACCOUNT = "username";
    // JWT-currentTimeMillis
    private static final String CURRENT_TIME_MILLIS = "currentTimeMillis";
    // 失效时间：一天
    public static final long REFRESH_TOKEN_EXPIRE_TIME = 24 * 60 * 60 * 1000L;

    private static final String SECRET_KEY = "storewebkey";
    public static final String UID = "uid";

    /**
     * 校验token是否正确
     *
     * @param token
     * @return
     */
    public static boolean verify(String token) {
        String secret = getClaim(token, ACCOUNT).asString() + SECRET_KEY;
        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTVerifier verifier = JWT.require(algorithm)
                .build();
        verifier.verify(token);
        return true;
    }

    /**
     * 获得Token中的信息无需secret解密也能获得
     *
     * @param token
     * @param claim
     * @return openCode
     */
    public static Claim getClaim(String token, String claim) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim(claim);
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 生成签名
     *
     * @param account
     * @param currentTimeMillis
     * @return
     */
    public static String sign(String account, String uid, String currentTimeMillis) {
        // 帐号加JWT私钥加密
        String secret = account + SECRET_KEY;
        // 此处过期时间，单位：毫秒
        Date date = new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRE_TIME);
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.create()
                .withClaim(ACCOUNT, account)  // 注意Claim设置用的是Key-Value
                .withClaim(UID, uid)
                .withClaim(CURRENT_TIME_MILLIS, currentTimeMillis)
                .withExpiresAt(date)
                .sign(algorithm);
    }

}
