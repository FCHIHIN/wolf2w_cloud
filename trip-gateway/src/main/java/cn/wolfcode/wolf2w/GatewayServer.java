package cn.wolfcode.wolf2w;

import cn.wolfcode.wolf2w.common.swagger2.SwaggerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan( excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = SwaggerConfig.class)})
public class GatewayServer {
    public static void main(String[] args) {
        SpringApplication.run(GatewayServer.class,args);
    }
}
