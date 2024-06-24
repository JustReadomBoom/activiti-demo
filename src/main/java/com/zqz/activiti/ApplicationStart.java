package com.zqz.activiti;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author: zqz
 * @CreateTime: 2024/06/24
 * @Description: TODO
 * @Version: 1.0
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.zqz.activiti"})
public class ApplicationStart {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationStart.class, args);
    }
}
