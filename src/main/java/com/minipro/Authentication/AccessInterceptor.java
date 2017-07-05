package com.minipro.Authentication;

import com.minipro.conf.ErrorConfig;
import com.minipro.entity.JSONResult;
import com.minipro.util.HttpsClient;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.lang.reflect.Method;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

/**
 * Created by chuckulu on 2017/7/4.
 */
public class AccessInterceptor extends HandlerInterceptorAdapter{

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        AccessRequired annotation = method.getAnnotation(AccessRequired.class);
        if(annotation == null) {
            return true;
        }
        if (annotation != null) {
            Cookie[] cookies = request.getCookies();
            Optional<String> accessTokenOp = Arrays.asList(cookies).stream()
                    .filter(cookie -> "access_token".equals(cookie.getName()))
                    .map(cookie -> cookie.getValue()).findAny();
            if(!accessTokenOp.isPresent()) {
                JSONResult rst = new JSONResult();
                rst.fail(ErrorConfig.INVALIDPARAM, "invalid operation", "called method need authentication, " +
                        "no access_token found in cookie");
                response.getWriter().write(rst.toJson());
                return false;
            }

            String accessToken = accessTokenOp.get();
//            String token_acess = "6626EA4D059440FAF595C205226726BE";
            String url = String.format("https://graph.qq.com/oauth2.0/me?access_token=%s",accessToken);
            try {
                HttpsClient client = new HttpsClient();
               if(client.get(url).contains("openid")) {
                   return true;
               }
            JSONResult rst = new JSONResult();
            rst.fail(ErrorConfig.NOTAUTHORIZATION, "未登录", "invalid access_token");
            response.getWriter().write(rst.toJson());
            } catch (KeyStoreException | NoSuchAlgorithmException | KeyManagementException ex) {
                JSONResult rst = new JSONResult();
                rst.fail(ErrorConfig.INVALIDPARAM, "服务器错误", "can not initialize https connect to " +
                        "https://graph.qq.com/oauth2.0/me");
                response.getWriter().write(rst.toJson());
            }
            //

        }
        return false;
    }
}
