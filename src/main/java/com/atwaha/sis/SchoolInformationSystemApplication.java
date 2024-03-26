package com.atwaha.sis;

import com.atwaha.sis.model.dto.RegisterRequest;
import com.atwaha.sis.service.AuthService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import static com.atwaha.sis.model.enums.Role.ADMIN;
import static com.atwaha.sis.model.enums.Role.MANAGER;

@SpringBootApplication
@EnableJpaAuditing
public class SchoolInformationSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(SchoolInformationSystemApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(AuthService authService) {
        return args -> {
            RegisterRequest admin = RegisterRequest
                    .builder()
                    .firstName("Admin")
                    .lastName("Admin")
                    .email("admin@domain.com")
                    .password("123")
                    .role(ADMIN)
                    .build();

            System.out.println("Admin Token:: " + authService.register(admin).getToken());

            RegisterRequest manager = RegisterRequest
                    .builder()
                    .firstName("Manager")
                    .lastName("Manager")
                    .email("manager@domain.com")
                    .password("123")
                    .role(MANAGER)
                    .build();

            System.out.println("Manager Token:: " + authService.register(manager).getToken());
        };
    }

}
