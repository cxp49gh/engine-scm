//package com.kuandeng.data.config;
//
//import io.swagger.annotations.ApiOperation;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.context.request.async.DeferredResult;
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.service.Contact;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
//
///**
// * sawgger配置
// * @author jiang
// */
//@Configuration
//@EnableSwagger2
//public class SwaggerConfig {
//    @Autowired
//    private BuildInfo buildInfo;
//
//    @Bean
//    public Docket docket() {
//        return new Docket(DocumentationType.SWAGGER_2).groupName("data").genericModelSubstitutes(DeferredResult.class)
//                .useDefaultResponseMessages(false).forCodeGeneration(true).apiInfo(apiInfo()) // 用于定义api文档汇总信息
//                .select().apis(RequestHandlerSelectors.basePackage("com.kuandeng")) // 指定controller包
//                .paths(PathSelectors.any()) // 所有controller
//                .build();
//    }
//
//    private ApiInfo apiInfo() {
//        return new ApiInfoBuilder().title(buildInfo.getName() + " API").description(buildInfo.getName() + " API 在线文档")
//                .contact(new Contact("cxp", null, "chenxiaopeng@kuandeng.com")).license("宽凳（北京）科技有限公司")
//                .licenseUrl("http://www.kuandeng.com/").version(buildInfo.getVersion()).build();
//    }
//}
