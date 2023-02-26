package cn.wolfcode.wolf2w;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableDiscoveryClient
@EnableScheduling  //启动定时任务
@EnableFeignClients  //支持feign远程调用
public class DataServer {
    public static void main(String[] args) {
        SpringApplication.run(DataServer.class,args).start();
    }
}
