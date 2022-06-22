package top.zsmile.auth.filter;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import top.zsmile.auth.token.OAuth2Token;
import top.zsmile.common.constant.CommonConstant;
import top.zsmile.common.utils.JwtUtils;
import top.zsmile.core.api.R;
import top.zsmile.core.api.ResultCode;
import top.zsmile.core.utils.SpringContextUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * oauth2过滤器
 *
 * @author zz@gmail.com
 */
public class OAuth2Filter extends BasicHttpAuthenticationFilter {

    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
        //获取请求token
        String token = getRequestToken((HttpServletRequest) request);

        if (StringUtils.isBlank(token)) {
            return null;
        }

        return new OAuth2Token(token);
    }

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }

//    @Override
//    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
//        try {
//            return executeLogin(request, response);
//        } catch (Exception e) {
//            JwtUtils.responseError(response, ResultCode.NO_AUTH, CommonConstant.S_INVALID_TOKEN);
//            return false;
//        }
//    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        //获取请求token，如果token不存在，直接返回401
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        String token = getRequestToken(httpServletRequest);
        if (StringUtils.isBlank(token)) {
//            httpServletResponse.setContentType("application/json;charset=utf-8");
//            httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
//            httpServletResponse.setHeader("Access-Control-Allow-Origin", httpServletRequest.getHeader("Origin"));
//            String json = JSON.toJSONString(R.fail(ResultCode.NO_AUTH, CommonConstant.S_INVALID_TOKEN));
//            httpServletResponse.getWriter().print(json);

            JwtUtils.responseError(response, ResultCode.NO_AUTH, CommonConstant.S_INVALID_TOKEN);
            return false;
        }
        return executeLogin(request, response);
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        //处理登录失败的异常
        Throwable throwable = e.getCause() == null ? e : e.getCause();
//            httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
//            httpServletResponse.setHeader("Access-Control-Allow-Origin", httpServletRequest.getHeader("Origin"));
//            String json = JSON.toJSONString(R.fail(ResultCode.NO_AUTH, throwable.getMessage()));
//            httpServletResponse.getWriter().print(json);
        JwtUtils.responseError(response, ResultCode.NO_AUTH, throwable.getMessage() );

        return false;
    }

    /**
     * 获取请求的token
     */
    private String getRequestToken(HttpServletRequest httpRequest) {
        //从header中获取token
        String token = httpRequest.getHeader(CommonConstant.S_ACCESS_TOKEN);

        //如果header中不存在token，则从参数中获取token
        if (StringUtils.isBlank(token)) {
            token = httpRequest.getParameter(CommonConstant.S_ACCESS_TOKEN);
        }

        return token;
    }


}
