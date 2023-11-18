package com.flexidorm.artsch;

import com.flexidorm.artsch.security_management.domain.enums.ERole;
import com.flexidorm.artsch.security_management.infrastructure.repositories.IRoleRepository;
import com.flexidorm.artsch.shared.util.Utilities;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ArtschApplication {

	public static void main(String[] args) {
		SpringApplication.run(ArtschApplication.class, args);
	}

	/**
	 * Bean que se encarga de insertar los roles por defecto
	 * @param roleRepository Repositorio de roles
	 */
	@Bean
	CommandLineRunner initDatabase(IRoleRepository roleRepository) {
		return args -> {
			Utilities.insertRoleIfNotFound(roleRepository, ERole.ROLE_USER);
			Utilities.insertRoleIfNotFound(roleRepository, ERole.ROLE_ADMIN);
		};
	}
}
