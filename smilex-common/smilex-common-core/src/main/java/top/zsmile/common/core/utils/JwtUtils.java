package top.zsmile.common.core.utils;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.http.HttpStatus;
import top.zsmile.common.core.api.R;
import top.zsmile.common.core.api.ResultCode;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
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
     * 获取解码后的对象
     *
     * @param token
     * @return
     */
    public static DecodedJWT getDecodedJwt(String token) {
        try {
            DecodedJWT decode = JWT.decode(token);
            return decode;
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * 获取用户ID
     *
     * @param decode
     * @return
     */
    public static Long getUserId(DecodedJWT decode) {
        try {
            return decode.getClaim(CLAIM_USERID).asLong();
        } catch (Exception ex) {
            return null;
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
            return getUserId(decode);
        } catch (Exception ex) {
            return null;
        }
    }


    public static void responseError(ServletResponse response, Integer code, String msg) {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        R fail = R.fail(code, msg);
        try {
            httpServletResponse.setHeader("Access-Control-Allow-Origin", SpringContextUtils.getHttpServletRequest().getHeader("Origin"));
            httpServletResponse.setContentType("application/json;charset=UTF-8");
            httpServletResponse.setCharacterEncoding("UTF-8");
            //设置请求的方式
            httpServletResponse.setHeader("Access-Control-Allow-Methods", "POST,GET,PATCH,DELETE,PUT");
            httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
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
}

