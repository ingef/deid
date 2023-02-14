//package de.ingef.deid.config;
//
//import java.io.File;
//import java.util.TimeZone;
//
//import freemarker.template.Configuration;
//import freemarker.template.TemplateExceptionHandler;
//import freemarker.template.Version;
//import org.springframework.context.annotation.Bean;
//
//@org.springframework.context.annotation.Configuration
//public class FreemarkerConfiguration {
//
//	public static final Version VERSION = Configuration.VERSION_2_3_27;
//
//	@Bean
//	public Configuration freemarker() {
//		final Configuration cfg = new Configuration(VERSION);
//		cfg.setClassLoaderForTemplateLoading(this.getClass().getClassLoader(), "/templates");
//
//		cfg.setDefaultEncoding("UTF-8");
//		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
//		cfg.setLogTemplateExceptions(false);
//		cfg.setWrapUncheckedExceptions(true);
//		cfg.setFallbackOnNullLoopVariable(false);
//		cfg.setSQLDateAndTimeTimeZone(TimeZone.getDefault());
//
//		return cfg;
//	}
//}
