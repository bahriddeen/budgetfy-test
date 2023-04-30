package com.budgetfy.app.component;

import com.budgetfy.app.enums.AccountType;
import com.budgetfy.app.payload.RegisterRequest;
import com.budgetfy.app.payload.dto.AccountDTO;
import com.budgetfy.app.repository.UserRepository;
import com.budgetfy.app.service.impl.AccountServiceImpl;
import com.budgetfy.app.service.impl.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final AccountServiceImpl accountService;
    private final AuthenticationService authService;

    @Value("${spring.sql.init.mode}")
    private String initialMode;


    @Override
    public void run(String... args) {

        if (initialMode.equals("always")) {

            RegisterRequest request = new RegisterRequest(
                    "Bahriddin",
                    "admin@gmail.com",
                    "123"
            );

            authService.signup(request);

            userRepository.findByEmail("admin@gmail.com").ifPresent(user -> {

                Integer userId = user.getId();

                AccountDTO account = new AccountDTO(
                        null, userId, "Cash", "#6fd6f2",
                        "USD", AccountType.CASH, 0, true
                );

                accountService.create(account);

            });

        }
    }
}
