package com.qianbing.blog.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.qianbing.blog.exception.BizCodeExcetionEnum;
import com.qianbing.blog.utils.R;
import com.qianbing.blog.utils.jwt.JWTUtils;
import io.jsonwebtoken.Claims;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String token = request.getHeader("token");
        if(token == null){
            token = request.getParameter("token");
        }
        if(!StringUtils.isEmpty(token)){
            Claims claims = JWTUtils.checkJWT(token);
            if(claims == null){
                sendMessage(response);
                return false;
            }
            Integer id = (Integer) claims.get("id");
            String name = (String) claims.get("name");
            request.setAttribute("id",id);
            request.setAttribute("name",name);
            return true;
        }
        sendMessage(response);
        return false;
    }

    /**
     * 发送错误消息
     * @param response
     * @throws IOException
     */
    private void sendMessage(HttpServletResponse response) throws IOException {
        //发送错误信息
        ObjectMapper objectMapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.print(objectMapper.writeValueAsString(R.error(BizCodeExcetionEnum.LOGIN_TIME_ERROR.getCode(),BizCodeExcetionEnum.LOGIN_TIME_ERROR.getMsg())));
        writer.close();
        response.flushBuffer();
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
