package com.clinicManagement.ClinicManagement.CORS;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Cho phép tất cả các đường dẫn
                .allowedOrigins("http://127.0.0.1:8081") // Chỉ định nguồn (origin) được phép truy cập
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Các phương thức được phép
                .allowedHeaders("*") // Các header được phép
                .allowCredentials(true); // Cho phép gửi cookie
    }

}

