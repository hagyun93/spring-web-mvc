package me.whiteship;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;


@Configuration
@ComponentScan
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    // preHandle 1
    // preHandle 2
    // 요청 처리
    // postHandler 2
    // postHandler 1
    // 뷰 랜더링
    // afterCompletion 2
    // afterCompletion 1
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // order 설정 안하면 add 한 순서대로
        registry.addInterceptor(new GreetingInterceptor()).order(0);
        registry.addInterceptor(new AnotherInterceptor())
                .addPathPatterns("/hello")
                .order(1);
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(new PersonFormatter());
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.jsp("/WEB-INF/", ".jsp");
    }
}
