package cn.wolfcode.wolf2w.common.security.interceptor.feign;

import cn.wolfcode.wolf2w.common.util.Consts;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
public class FeignRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header(Consts.REQUEST_ORIGIN_KEY,Consts.REQUEST_ORIGIN_FEIGN);
    }
}
