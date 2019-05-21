package com.tensquare.user.filter;

import com.tensquare.user.service.UserService;
import entity.Result;
import entity.StatusCode;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends HandlerInterceptorAdapter {

    @Autowired
    private JwtUtil jwtUtil;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //1. 使用jjwt解析token 字符串，是否过期（合法）
        //1.1 获取Authorization信息
        String auth = request.getHeader("Authorization");
        if (auth != null) {
            //1.2判断已Bearer开头
            if (auth.startsWith("Bearer")) {
                //1.3 去除token字符串
                String token = auth.substring(7);
                //1.4解析token字符串
                Claims claims = jwtUtil.parseJWT(token);
                //1.5 判断是否是管理员  载荷中
                if ("admin".equals(claims.get("roles"))){
                    //标记  管理员
                    request.setAttribute("admin_claims",claims);
                }
            }
        }
        return true;
    }
}
