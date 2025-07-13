package com.itheima.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;

public class JwtUtils {

    private static final String SECRET_KEY = "SVRIRUlNQQ==";          // 密钥
    private static final long EXPIRATION_TIME = 12 * 60 * 60 * 1000;  // 12小时

    /**
     * 生成JWT令牌
     */
    public static String generateJwt(Map<String,Object> claims){
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .addClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .compact();
    }

    /**
     * 解析JWT令牌
     */
    public static Claims parseJWT(String jwt){
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(jwt)
                .getBody();
    }
}
