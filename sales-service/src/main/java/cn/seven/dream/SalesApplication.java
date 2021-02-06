package cn.seven.dream;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @description
 * @AUTHOR zhangxue9
 * @DATE 2021/1/27 14:47
 */
@SpringBootApplication
@MapperScan("cn.seven.dream.dao")
@EnableDiscoveryClient
public class SalesApplication {
    public static void main(String[] args) {
        SpringApplication.run(SalesApplication.class,args);
    }
}
