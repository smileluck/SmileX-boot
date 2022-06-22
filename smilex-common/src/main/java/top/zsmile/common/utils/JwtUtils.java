package top.zsmile.common.utils;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.http.HttpStatus;
import top.zsmile.core.api.R;
import top.zsmile.core.api.ResultCode;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Date;

public class JwtUtils {

    // 过期时间为2小时
    public static final long EXPIRE_TIME = 2 * 60 * 60 * 1000;

    private static final String CLAIM_USERID = "userId";

    /**
     * 生成token
     *
     * @param userId
     * @param secret
     * @return
     */
    public static String generatorToken(Long userId, String secret) {
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        Algorithm algorithm = Algorithm.HMAC512(secret);
        return JWT.create()
                .withExpiresAt(date)
                .withClaim(CLAIM_USERID, userId)
                .sign(algorithm);
    }

    /**
     * 校验token是否正确
     *
     * @param token  密钥
     * @param secret 用户的密码
     * @return 是否正确
     */
    public static boolean verify(String token, Long userId, String secret) {
        try {
            // 根据密码生成JWT效验器
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm).withClaim(CLAIM_USERID, userId).build();
            // 效验TOKEN
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    /**
     * 获取用户ID
     *
     * @param token
     * @return
     */
    public static Long getUserId(String token) {
        try {
            DecodedJWT decode = JWT.decode(token);
            return decode.getClaim(CLAIM_USERID).asLong();
        } catch (Exception ex) {
            return null;
        }
    }

    public static void responseError(ServletResponse response, Integer code, String msg) {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        R fail = R.fail(code, msg);
        try {
            httpServletResponse.setContentType("application/json;charset=UTF-8");
            httpServletResponse.setCharacterEncoding("UTF-8");
//            httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
            httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());

//            PrintWriter writer = httpServletResponse.getWriter();
//            writer.print(JSON.toJSONString(fail));

            OutputStream outputStream = httpServletResponse.getOutputStream();
            outputStream.write(JSON.toJSONBytes(fail));
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void responseError(ServletResponse httpServletResponse, ResultCode resultCode) {
        responseError(httpServletResponse, resultCode.getCode(), resultCode.getMessage());
    }

    public static void responseError(ServletResponse httpServletResponse, ResultCode resultCode, String msg) {
        responseError(httpServletResponse, resultCode.getCode(), msg);
    }

    public static void main(String[] args) {
        String token = JwtUtils.generatorToken(292798030246051840L, "a511fd1ce46a02d5671f9d29aae96a93807ec34f46ec56b4e2e6659df25d9d89");
        System.out.println(token);
    }
}

