package cn.wolfcode.wolf2w;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("cn.wolfcode.wolf2w.mapper")
public class MemberServer {
    public static void main(String[] args) {
        SpringApplication.run(MemberServer.class,args);
    }
}
