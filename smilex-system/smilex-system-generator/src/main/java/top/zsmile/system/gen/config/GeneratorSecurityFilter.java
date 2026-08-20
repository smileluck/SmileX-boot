package top.zsmile.system.gen.config;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 代码生成器 Token 门禁
 * <p>
 * 配置 smilex.generator.security.token 后，/generator/** 与 /druid/** 请求
 * 必须携带匹配的 X-Generator-Token 请求头，否则返回 401；
 * 未配置 token 时放行并在启动时输出 WARN 提示（本地开发场景保留易用性）。
 */
@Slf4j
public class GeneratorSecurityFilter implements Filter {

    public static final String TOKEN_HEADER = "X-Generator-Token";

    private final String token;

    public GeneratorSecurityFilter(String token) {
        this.token = token;
        if (token == null || token.isEmpty()) {
            log.warn("====================================================================");
            log.warn("[smilex-generator] 未配置访问令牌(smilex.generator.security.token)，"
                    + "生成器接口处于无防护状态！仅建议本地开发使用。");
            log.warn("====================================================================");
        } else {
            log.info("[smilex-generator] 访问令牌已启用，请求需携带请求头 {}", TOKEN_HEADER);
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if (token == null || token.isEmpty()) {
            chain.doFilter(request, response);
            return;
        }
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String path = httpRequest.getRequestURI();
        // 页面与静态资源放行，其余（接口/druid 监控）校验 token
        boolean isPage = path.equals(httpRequest.getContextPath() + "/")
                || path.startsWith(httpRequest.getContextPath() + "/static")
                || path.startsWith(httpRequest.getContextPath() + "/webjars")
                || path.startsWith(httpRequest.getContextPath() + "/swagger")
                || path.startsWith(httpRequest.getContextPath() + "/v3/api-docs");
        if (!isPage) {
            String provided = httpRequest.getHeader(TOKEN_HEADER);
            if (provided == null) {
                provided = httpRequest.getParameter("generatorToken");
            }
            if (!token.equals(provided)) {
                httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                httpResponse.setContentType("application/json;charset=UTF-8");
                httpResponse.getWriter().write("{\"code\":401,\"msg\":\"未授权访问生成器，请携带请求头 " + TOKEN_HEADER + "\",\"success\":false}");
                return;
            }
        }
        chain.doFilter(request, response);
    }
}
