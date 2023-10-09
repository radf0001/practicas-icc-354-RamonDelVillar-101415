//package edu.pucmm.eict.practica2.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class MvcConfig implements WebMvcConfigurer {
//
//
//
//
//    /**
//     * Permite registrar una vista del template asociado a una URL.
//     * @param registry
//     */
//    @Override
//    public void addViewControllers(ViewControllerRegistry registry) {
//        WebMvcConfigurer.super.addViewControllers(registry);
//        registry.addViewController("/").setViewName("index");
//    }
//
//
//    /**
//     *
//     * En el caso que quiera pasarlo por parametro es una opción.
//     * Ver: https://www.baeldung.com/spring-boot-internationalization
//     * y
//     * https://lokalise.com/blog/spring-boot-internationalization/
//     */
//
//    /*@Bean
//    public LocaleResolver localeResolver() {
//        SessionLocaleResolver slr = new SessionLocaleResolver();
//        slr.setDefaultLocale(Locale.US);
//        return slr;
//    }
//    @Bean
//    public LocaleChangeInterceptor localeChangeInterceptor() {
//        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
//        lci.setParamName("lang");
//        return lci;
//    }
//
//    *//**
//     *
//     * @param registry
//     *//*
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(localeChangeInterceptor());
//    }
//*/
//
//}
