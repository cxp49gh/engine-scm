//package com.engine.scm.util;
//
//import freemarker.core.TemplateClassResolver;
//import freemarker.template.TemplateExceptionHandler;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class FreemarkerConfig {
//
//    @Bean
//    public freemarker.template.Configuration freemarkerConfiguration() {
//
//        freemarker.template.Configuration cfg =
//                new freemarker.template.Configuration(freemarker.template.Configuration.VERSION_2_3_32);
//
//        cfg.setDefaultEncoding("UTF-8");
//
//        /** 严格模式：变量不存在直接失败 */
//        cfg.setTemplateExceptionHandler(
//                TemplateExceptionHandler.RETHROW_HANDLER
//        );
//
//        /** 禁止 classic null 容忍 */
//        cfg.setClassicCompatible(false);
//
//        /** 安全：禁止任意类访问 */
//        cfg.setNewBuiltinClassResolver(
//                TemplateClassResolver.SAFER_RESOLVER
//        );
//
//        /** 数字格式 */
//        cfg.setNumberFormat("0.######");
//
//        return cfg;
//    }
//}
