package com.syoka.tutorial.logback.plugin.b;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import com.syoka.tutorial.logback.plugin.b.utils.LogUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author syoka
 */
public class BInterceptor implements HandlerInterceptor {

    private static final String PLUGIN_DIGEST = "PLUGIN-B-DIGEST";

    private static final String START_TIME = "PluginStartTime";
    /**
     * Digest Logger
     */

    private static final Logger DIGEST_LOGGER = LogUtils.getLogger(PLUGIN_DIGEST);
    /**
     * Detail Logger
     */
    private static final Logger DETAIL_LOGGER = LogUtils.getLogger(BInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        long startTime = System.currentTimeMillis();
        request.setAttribute(START_TIME, startTime);
        DETAIL_LOGGER.info("detail start plugin !!!");
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String matchPath = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        String host = request.getHeader(HttpHeaders.HOST);

        Long startTime = (Long) request.getAttribute(START_TIME);

        long elapseTime = System.currentTimeMillis() - startTime;
        if (ex == null) {
            DIGEST_LOGGER.info("[{},Y,{}ms,{}].", host, elapseTime, matchPath);
        } else {
            DIGEST_LOGGER.info("[{},N,{}ms,{}].", host, elapseTime, matchPath);
        }
        DETAIL_LOGGER.info("detail end plugin !!!");
    }
}
