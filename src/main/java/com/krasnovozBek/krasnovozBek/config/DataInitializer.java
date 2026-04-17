package com.krasnovozBek.krasnovozBek.config;
import com.krasnovozBek.krasnovozBek.domain.User;
import com.krasnovozBek.krasnovozBek.dao.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {

    private final UserRepository repo;
    private final PasswordEncoder encoder;

    public DataInitializer(UserRepository repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    @Bean
    CommandLineRunner init() {
        return args -> {

            if (repo.findByUsername("manager").isEmpty()) {
                User manager = new User();
                manager.setUsername("manager");
                manager.setPassword(encoder.encode("1234"));
                manager.setRole("MANAGER");
                repo.save(manager);
            }
            if (repo.findByUsername("cashier").isEmpty()) {
                User cashier = new User();
                cashier.setUsername("cashier");
                cashier.setPassword(encoder.encode("1234"));
                cashier.setRole("CASHIER");
                repo.save(cashier);
            }
        };
    }
}