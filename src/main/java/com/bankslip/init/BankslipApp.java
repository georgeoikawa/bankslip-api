package com.bankslip.init;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories("com.bankslip.repository")
@EntityScan("com.bankslip.entity")
@SpringBootApplication(scanBasePackages = {"com.bankslip"})
public class BankslipApp {

    public static void main(String[] args) {
        SpringApplication.run(BankslipApp.class, args);
    }
}