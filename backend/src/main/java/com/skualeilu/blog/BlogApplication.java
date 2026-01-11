package com.skualeilu.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import com.skualeilu.blog.config.JsonPropertySourceFactory;

@SpringBootApplication
@PropertySource(value = "classpath:secrets.json", factory = JsonPropertySourceFactory.class, ignoreResourceNotFound = true)
public class BlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogApplication.class, args);
    }

}