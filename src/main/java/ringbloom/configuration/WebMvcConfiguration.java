package ringbloom.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import ringbloom.interceptor.LoggerInterceptor;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
	// 인터셉터 등록
	@Override
	public void addInterceptors(InterceptorRegistry registry){
		registry.addInterceptor(new LoggerInterceptor());
	}
	
	// 파일 처리를 위한 빈 설정
	@Bean
	public CommonsMultipartResolver multipartResolver() {
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
		commonsMultipartResolver.setDefaultEncoding("UTF-8");
		commonsMultipartResolver.setMaxUploadSizePerFile(5 * 1024 * 1024);
		return commonsMultipartResolver;
	}
}
