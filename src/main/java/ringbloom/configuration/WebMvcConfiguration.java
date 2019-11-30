/* WebMvcConfiguration.java
 * Description : Web MVC 관련 설정
 * ver 0.1 : 초기 구성 - 이 창 재
 * ver 0.2 : 파일 처리 설정 - 이 창 재
 */
package ringbloom.configuration;

import java.util.Arrays;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.reactive.HiddenHttpMethodFilter;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import ringbloom.interceptor.UserInterceptor;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
	// 인터셉터 등록
	@Override
	public void addInterceptors(InterceptorRegistry registry){
		registry.addInterceptor(new UserInterceptor());
	}
	
	// 파일 처리를 위한 빈 설정
	@Bean
	public CommonsMultipartResolver multipartResolver() {
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
		commonsMultipartResolver.setDefaultEncoding("UTF-8");
		commonsMultipartResolver.setMaxUploadSizePerFile(5 * 1024 * 1024);
		return commonsMultipartResolver;
	}
	
	// HiddenHttpMethodFilter 사용을 위한 빈 설정
	@Bean
	public FilterRegistrationBean hiddenHttpMethodFilter() {
		FilterRegistrationBean filterRegBean = new FilterRegistrationBean(new org.springframework.web.filter.HiddenHttpMethodFilter());
		filterRegBean.setUrlPatterns(Arrays.asList("/*"));
		return filterRegBean;
	}
}
