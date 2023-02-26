package cn.wolfcode.wolf2w;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@MapperScan("cn.wolfcode.wolf2w.article.mapper")
@EnableDiscoveryClient
@EnableFeignClients  //支持feign远程调用
public class ArticleServe {
    public static void main(String[] args) {
        SpringApplication.run(ArticleServe.class,args);
    }
}
