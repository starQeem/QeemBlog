package com.starQeem.qeemblog;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableAsync;

@Slf4j
@EnableAsync//开启异步
@SpringBootApplication
@ServletComponentScan
public class QeemBlogApplication extends SpringBootServletInitializer {

@Override
protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(QeemBlogApplication.class);
}

    public static void main(String[] args) {
        SpringApplication.run(QeemBlogApplication.class, args);
    }

}
