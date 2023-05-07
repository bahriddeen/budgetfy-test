package com.budgetfy.app.service.impl;

import com.budgetfy.app.mapstruct.UserMapper;
import com.budgetfy.app.model.User;
import com.budgetfy.app.payload.dto.UserDTO;
import com.budgetfy.app.payload.response.ApiResponse;
import com.budgetfy.app.repository.*;
import com.budgetfy.app.service.UserService;
import com.budgetfy.app.service.functionality.Delete;
import com.budgetfy.app.service.functionality.Read;
import com.budgetfy.app.utils.TransactionUtils;
import com.budgetfy.app.utils.Utils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import static com.budgetfy.app.enums.Message.USER_DELETED;
import static com.budgetfy.app.enums.Message.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class UserServiceImpl
        implements
        UserService,
        Delete<Integer>,
        Read<Integer> {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;
    private final TemplateRepository templateRepository;
    private final TransactionRepository transactionRepository;
    private final Logger log = LoggerFactory.getLogger(LogoutService.class);

    /**
     * Retrieves a user with the given ID.
     *
     * @param userId the ID of the user to retrieve
     * @return an ApiResponse with the user data, or an error message if the user is not found
     */
    @Override
    public ApiResponse getDataById(Integer userId) {
        return userRepository.findById(userId)
                .map(
                        user -> ApiResponse.success(
                                userMapper.mapEntityToDTO(user),
                                HttpStatus.OK.value()
                        )
                )
                .orElse(
                        ApiResponse.error(
                                USER_NOT_FOUND.message(),
                                HttpStatus.NOT_FOUND.value()
                        )
                );
    }

    /**
     * Deletes a user with the given ID.
     *
     * @param userId the ID of the user to delete
     * @return an ApiResponse with the HTTP status
     */
    @Override
    @Transactional
    public ApiResponse delete(Integer userId) {

        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isEmpty())
            return ApiResponse.error(
                    USER_NOT_FOUND.message(),
                    HttpStatus.NOT_FOUND.value()
            );

        List<Integer> accountIds = accountRepository.getUserAccountIds(userId);

        templateRepository.deleteAllByUserId(userId);

        accountIds.forEach(accountId -> TransactionUtils.deleteTransactionsInBatch(transactionRepository, accountId));

        accountRepository.deleteAllByUserId(userId);
        tokenRepository.deleteAllByUserId(userId);
        userRepository.deleteById(userId);

        return ApiResponse.success(
                USER_DELETED.message(),
                HttpStatus.NO_CONTENT.value()
        );


    }


    public void changePassword(String password) {

        userRepository.findByEmail(Utils.getCurrentUserName())
                .ifPresent(
                        user -> {
                            String encryptedPassword = passwordEncoder.encode(password);
                            user.setPassword(encryptedPassword);
                            userRepository.save(user);
                            log.debug("Changed password for User: {}", user);
                        }
                );

    }

    /**
     * Retrieves all users.
     *
     * @return an ApiResponse with the list of users and an HTTP status code
     */
    @Override
    public ApiResponse getUsers(Integer pageNo, Integer pageSize, String sortBy) {

        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        List<UserDTO> users = userMapper.listMapEntityToDTO(userRepository.findAllByActiveIsTrue(paging));

        return ApiResponse.success(
                users,
                HttpStatus.OK.value()
        );

    }

    /**
     * Not activated users should be automatically deleted after 3 days.
     * <p>
     * This is scheduled to get fired everyday, at 01:00 (am).
     * </p>
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void removeNotActivatedUsers() {

        List<User> users = userRepository.findAllByActiveIsFalseAndCreatedAtBefore(
                Instant.now().minus(3, ChronoUnit.DAYS)
        );

        for (User user : users) {
            log.debug("Deleting not activated user {}", user.getEmail());
            userRepository.delete(user);
        }

    }

}
