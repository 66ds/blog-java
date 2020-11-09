package com.qianbing.blog.utils.jwt;

import com.qianbing.blog.entity.UsersEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;


/**
 *JWT工具类
 */
public class JWTUtils {

    /**
     * 过期时间
     */
    private static final long EXPIRE = 6000 * 60 * 24 * 7;

    /**
     * 加密秘钥
     */
    private static final String SECERT = "qianbing.com";

    /**
     * 令牌前缀
     */
    private static final String TOKEN_PREFIX = "qianbing";

    /**
     * subject
     */
    private static final String SUBJECT = "qianbing";

    /**
     *加密操作
     * @param user
     * @return
     */
    public static String geneJsonWebToken(UsersEntity user){
        String token = Jwts.builder().setSubject(SUBJECT)
                .claim("head_img", user.getUserProfilePhoto())
                .claim("id", user.getUserId())
                .claim("name", user.getUserName())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                .signWith(SignatureAlgorithm.HS256, SECERT).compact();
        token = TOKEN_PREFIX + token;
        return token;
    }

    /**
     * 解密操作
     * @param token
     * @return
     */
    public static Claims checkJWT(String token){
        try{
            Claims claims = Jwts.parser().setSigningKey(SECERT).parseClaimsJws(token.replaceAll(TOKEN_PREFIX,"")).getBody();
            return claims;
        }catch (Exception e){
            return null;
        }
    }
}
