package cn.wolfcode.wolf2w.common.security.interceptor;

import cn.wolfcode.wolf2w.common.redis.service.RedisService;
import cn.wolfcode.wolf2w.common.redis.util.RedisKeys;
import cn.wolfcode.wolf2w.common.security.annotation.RequireLogin;
import cn.wolfcode.wolf2w.common.util.Consts;
import cn.wolfcode.wolf2w.common.web.response.JsonResult;
import com.alibaba.fastjson2.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

public class CheckLoginInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisService redisService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println(request.getRequestURL());
        System.out.println(request.getMethod());
        System.out.println(handler.getClass());
        System.out.println(handler);
        System.out.println("-------------------------------");

        // 解决跨域
        if (!(handler instanceof HandlerMethod)) {
            // 跨域预请求放行
            return true;
        }

        String header = request.getHeader(Consts.REQUEST_ORIGIN_KEY);
        if (!Consts.REQUEST_ORIGIN_GATEWAY.equals(header) && !Consts.REQUEST_ORIGIN_FEIGN.equals(header)) {
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(JSON.toJSONString(JsonResult.noPermission()));
            return false;
        }

        // 自定义注解拦截
        //判断当前请求url对应接口方法是否贴有@RequireLogin注解
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        String token = request.getHeader("token");
        String key = RedisKeys.USER_LOGIN_TOKEN.join(token);
        //重置登录key时间
        if (redisService.hasKey(key)) {
            redisService.expire(key, RedisKeys.USER_LOGIN_TOKEN.getTime(), TimeUnit.SECONDS);
        }
        // 贴了进行判断
        if (handlerMethod.hasMethodAnnotation(RequireLogin.class)) {
            if (!redisService.hasKey(key)) {
                //没有登录--json格式提示
                response.setContentType("application/json;charset=utf-8");
                response.getWriter().write(JSON.toJSONString(JsonResult.noLogin()));
                return false;
            }
        }
        // 不贴注解放行
        return true;
    }
}
