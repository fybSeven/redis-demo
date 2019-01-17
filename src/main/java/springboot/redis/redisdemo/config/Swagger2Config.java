package springboot.redis.redisdemo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Author: fengyibo
 * @Date: 2019/1/15 10:31
 * @Description: swagger2配置
 * @EnableSwagger2 启用swagger功能
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {

    /**
     * 是否开启swagger标识，开发环境一般关闭
     */
    @Value("${swagger.enabled}")
    private Boolean swaggerEnabled;

    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(createApiInfo())
                .enable(swaggerEnabled).select()
                //设置包扫描路径
                .apis(RequestHandlerSelectors.basePackage("springboot.redis.redisdemo.controller"))
                //任何路径都生成，总共支持四种any，none（所有都不支持），regex（正则匹配请求路径），ant
                .paths(PathSelectors.any()).build();

    }

    private ApiInfo createApiInfo() {
        return new ApiInfoBuilder().title("swagger API").description("springboot整合swagger项目").version("1.0")
                .termsOfServiceUrl("http://127.0.0.1:8080/").contact(new Contact("fengyibo", "www.baidu.com", "18298459311@163.com"))
                .build();
    }
}
