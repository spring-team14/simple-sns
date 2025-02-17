package com.example.simplesns.common.filter;

import com.example.simplesns.common.consts.Const;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
public class LoginFilter implements Filter {

    private static final String[] WHITE_LIST = {"/", "/api/members/sign-up", "/api/auth/login", "/api/auth/logout"};

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String requestURI = httpServletRequest.getRequestURI();
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        log.info("로그인 필터 로직 실행");

        if (!isWhiteList(requestURI)) {
            HttpSession session = httpServletRequest.getSession(false);
            if (session == null || session.getAttribute(Const.LOGIN_USER) == null) {
                log.warn("로그인하지 않은 사용자 접근: {}", requestURI);

                Map<String, Object> response = new LinkedHashMap<>();
                response.put("status", HttpStatus.UNAUTHORIZED.value());
                response.put("message", "로그인이 필요합니다.");
                response.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));

                ObjectMapper objectMapper = new ObjectMapper();
                String jsonResponse = objectMapper.writeValueAsString(response);

                httpServletResponse.setContentType("application/json");
                httpServletResponse.setCharacterEncoding("UTF-8");
                httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                httpServletResponse.getWriter().write(jsonResponse);
                return;
            }

            log.info("로그인 성공: {}", session.getAttribute(Const.LOGIN_USER));
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    private boolean isWhiteList(String requestURI) {
        //return PatternMatchUtils.simpleMatch(WHITE_LIST, requestURI);
        return true;
        // TODO: 현재는 모든 요청을 화이트리스트로 처리하여 로그인 필터 무력화 (개발/테스트용)
        // 추후 특정 URL만 허용하도록 변경 필요
    }
}
