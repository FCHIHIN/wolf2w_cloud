package cn.wolfcode.wolf2w.article;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@MapperScan("cn.wolfcode.wolf2w.article.mapper")
@EnableDiscoveryClient
public class ArticleServe {
    public static void main(String[] args) {
        SpringApplication.run(ArticleServe.class,args);
    }
}
