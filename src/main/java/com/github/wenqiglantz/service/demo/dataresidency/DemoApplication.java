package com.github.wenqiglantz.service.demo.dataresidency;

import com.github.wenqiglantz.service.demo.dataresidency.democlient.DemoClient;
import com.github.wenqiglantz.service.demo.dataresidency.democlient.DemoClientConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.nativex.hint.NativeHint;
import org.springframework.nativex.hint.TypeAccess;
import org.springframework.nativex.hint.TypeHint;
import reactivefeign.spring.config.EnableReactiveFeignClients;
import reactivefeign.spring.config.ReactiveFeignAutoConfiguration;
import reactivefeign.spring.config.ReactiveFeignClient;

@SpringBootApplication
@EnableReactiveFeignClients(clients = {DemoClient.class})
//@ConfigurationPropertiesScan
@EnableConfigurationProperties({ DemoClientConfig.class })
@NativeHint(
		trigger = ReactiveFeignAutoConfiguration.class,
		options = {"--enable-http", "--enable-https"},
		types = {
				@TypeHint(types = {
						ReactiveFeignAutoConfiguration.class,
						DemoClient.class},
						access = { TypeAccess.DECLARED_METHODS, TypeAccess.DECLARED_FIELDS,
								TypeAccess.DECLARED_CLASSES, TypeAccess.DECLARED_CONSTRUCTORS,
								TypeAccess.PUBLIC_CLASSES, TypeAccess.PUBLIC_CONSTRUCTORS,
								TypeAccess.PUBLIC_FIELDS, TypeAccess.PUBLIC_METHODS
				})
		}
)
public class DemoApplication {
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}
