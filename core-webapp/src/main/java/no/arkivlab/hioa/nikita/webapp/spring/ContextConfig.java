package no.arkivlab.hioa.nikita.webapp.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@ImportResource("classpath*:n5CoreContextConfig.xml")
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class ContextConfig {


	public ContextConfig() {
		super();
	}

	    // beans

	    @Bean
	    public static PropertySourcesPlaceholderConfigurer properties() {
	        final PropertySourcesPlaceholderConfigurer pspc = new PropertySourcesPlaceholderConfigurer();
	        return pspc;
	    }

}
