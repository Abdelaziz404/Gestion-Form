package com.example.GestionFormationsBackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching

@SpringBootApplication(scanBasePackages = {
		"com.example.GestionFormationsBackend",
		"Security",
		"Config",
		"Service",
		"Controller",
		"Repository",
		"Mapper",
		"Exception",
		"Util"
})
@EnableJpaRepositories(basePackages = "Repository")
@EntityScan(basePackages = "Entity")
public class GestionFormationsBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestionFormationsBackendApplication.class, args);
	}

}
