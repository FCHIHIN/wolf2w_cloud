package cn.wolfcode.wolf2w.gateway.filter;

import cn.wolfcode.wolf2w.common.redis.service.RedisService;
import cn.wolfcode.wolf2w.common.redis.util.RedisKeys;
import cn.wolfcode.wolf2w.common.util.Consts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;

@Component
public class CommonFilter implements GlobalFilter {

    @Autowired
    private RedisService redisService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        /**
         * pre拦截逻辑
         * 在请求去到微服务之前，做了一个处理
         *  在请求头中添加FEIGN_REQUEST的请求头，值为0，标记请求不是Feign调用，而是客户端调用
         *
         *  mutate --转换--默认请求对象时无法改变，重新赋值再转换
         *  request("request_origin", "gateway")
         */
        ServerHttpRequest httpRequest = exchange.getRequest().mutate()
                .header(Consts.REQUEST_ORIGIN_KEY, Consts.REQUEST_ORIGIN_GATEWAY)
                .build();
        return chain.filter(exchange.mutate().request(httpRequest).build()).then(Mono.fromRunnable(()->{
            // 网关filter可以全局配置 进到微服务再细节配置控制接口 重置token时间
            String token = exchange.getRequest().getHeaders().getFirst(Consts.TOKEN_NAME);

            if (StringUtils.hasText(token)) {
                redisService.expire(RedisKeys.USER_LOGIN_TOKEN.join(token),RedisKeys.USER_LOGIN_TOKEN.getTime(), TimeUnit.SECONDS);
            }

        }));

    }
}
