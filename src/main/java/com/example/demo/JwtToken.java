package com.example.demo;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtToken {
    private static Key key;
    static{
        key = MacProvider.generateKey();
    }

    /**
     *  加密
     * @param subject 主题
     * @param payParams 参数
     * @param ttlMillis 时长
     * @return
     */
    public static  String getToken(String subject, Map<String,Object> payParams, long ttlMillis){
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS512 ;
        long nowMillis = System. currentTimeMillis();
        Date now = new Date( nowMillis);

        if(null == payParams){
            payParams = new HashMap<>();
        }

        payParams.put(Claims.ISSUED_AT,now);
        payParams.put(Claims.SUBJECT,subject);
        JwtBuilder builder = Jwts. builder()
                .setClaims(payParams)
                .signWith(signatureAlgorithm, key);

        //超时
        if (ttlMillis >= 0){
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date( expMillis);
            System.out.println(exp);
            builder.setExpiration( exp);
        }
        return builder.compact();
    }

    /**
     *  解密
     * @param jwt token
     * @return payload内容
     */
    public static Claims parseJWT(String jwt){
        try {
            return Jwts.parser().setSigningKey(key)
                    .parseClaimsJws(jwt)
                    .getBody();
        } catch (Exception e) {
            //TODO 日志
            e.printStackTrace();
            return null;
        }
    }
}
