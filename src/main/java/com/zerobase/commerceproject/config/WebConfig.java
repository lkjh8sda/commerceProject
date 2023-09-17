package com.zerobase.commerceproject.config;

import com.zerobase.commerceproject.config.Interceptor.CustomerInterceptor;
import com.zerobase.commerceproject.config.Interceptor.SellerInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final JwtAuthenticationProvider jwtAuthenticationProvider;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new CustomerInterceptor(jwtAuthenticationProvider))
                .order(1)
                .addPathPatterns("/customer/**")
                .excludePathPatterns("/seller/**","/signup","/signin");

        registry.addInterceptor(new SellerInterceptor(jwtAuthenticationProvider))
                .order(2)
                .addPathPatterns("/seller/**")
                .excludePathPatterns("/customer/**","/signup","/signin");
    }
}
