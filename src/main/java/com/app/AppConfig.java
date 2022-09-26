package com.app;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.app.web.interceptor.BasicInterceptor;
import com.app.web.interceptor.CORSInterceptor;
import com.app.web.utils.CommonConstant;
import com.app.web.utils.MappingConstant;

/* 
* Configuration Class
*
*/

@Configuration
@PropertySource({ CommonConstant.SUCCESS_PROPERTIES })
public class AppConfig implements WebMvcConfigurer {

	@Autowired
	private BasicInterceptor basicInterceptor;
	@Autowired
	private CORSInterceptor consInterceptor;

	/**
	 * Creating Beans @Scope as Request
	 * 
	 * @return
	 */
	@Bean(name = CommonConstant.COMMON_MAP_OBJECT)
	@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
	public Map<String, Object> getMapObject() {
		return new HashMap<>();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(consInterceptor).addPathPatterns(CommonConstant.SLASH_STAR_STAR);
		registry.addInterceptor(basicInterceptor).addPathPatterns(CommonConstant.SLASH_STAR_STAR)
				.excludePathPatterns(MappingConstant.getExcludedURL());
	}
}