package com.skualeilu.blog.config;

import com.skualeilu.blog.entity.User;
import com.skualeilu.blog.repository.UserRepository;
import com.skualeilu.blog.security.AdminConfig;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class UserInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(UserInitializer.class);

    private final UserRepository userRepository;
    private final AdminConfig adminConfig;

    public UserInitializer(UserRepository userRepository, AdminConfig adminConfig) {
        this.userRepository = userRepository;
        this.adminConfig = adminConfig;
    }

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            logger.info("No users found. Creating default admin user from configuration.");
            User admin = new User();
            admin.setUsername(adminConfig.getUsername());
            admin.setPassword(adminConfig.getPassword()); // Password in config is already hashed

            userRepository.save(admin);
            logger.info("Default admin user created successfully.");
        } else {
            logger.info("Users found in database. Skipping initialization.");
        }
    }
}
