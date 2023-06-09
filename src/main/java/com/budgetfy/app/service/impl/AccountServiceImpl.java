package com.budgetfy.app.service.impl;

import com.budgetfy.app.mapstruct.AccountMapper;
import com.budgetfy.app.model.Account;
import com.budgetfy.app.payload.dto.AccountDTO;
import com.budgetfy.app.payload.response.ApiResponse;
import com.budgetfy.app.repository.AccountRepository;
import com.budgetfy.app.repository.TemplateRepository;
import com.budgetfy.app.repository.TransactionRepository;
import com.budgetfy.app.service.AccountService;
import com.budgetfy.app.service.functionality.Create;
import com.budgetfy.app.service.functionality.Delete;
import com.budgetfy.app.service.functionality.Read;
import com.budgetfy.app.service.functionality.Update;
import com.budgetfy.app.utils.TransactionUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static com.budgetfy.app.enums.Message.*;
import static com.budgetfy.app.utils.Utils.getCurrentUser;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl
        implements
        AccountService,
        Create<AccountDTO>,
        Update<AccountDTO>,
        Delete<Integer>,
        Read<Integer> {

    private final AccountMapper accountMapper;
    private final AccountRepository accountRepository;
    private final TemplateRepository templateRepository;
    private final TransactionRepository transactionRepository;

    public ApiResponse create(AccountDTO accountDTO) {

        boolean accountExists = accountRepository.existsByNameAndUserId(accountDTO.name(), accountDTO.userId());

        if (accountExists)
            return ApiResponse.error(
                    ACCOUNT_ALREADY_EXISTS.message(),
                    HttpStatus.CONFLICT.value()
            );


        int userAccountCount = accountRepository.countAccountsByUserId(accountDTO.userId());

        if (userAccountCount >= 5)
            return ApiResponse.error(
                    TOO_MANY_ACCOUNTS.message(),
                    HttpStatus.CONFLICT.value()
            );


        Account account = accountMapper.mapDTOToEntity(accountDTO);
        accountRepository.save(account);

        if (account.getId() == null)
            return ApiResponse.error(
                    CONFLICT.message(),
                    HttpStatus.INTERNAL_SERVER_ERROR.value()
            );

        return ApiResponse.success(
                ACCOUNT_CREATED.message(),
                HttpStatus.CREATED.value()
        );

    }

    @Override
    public ApiResponse getDataById(Integer accountId) {
        return accountRepository.findById(accountId)
                .map(accountMapper::mapEntityToDTO)
                .map(
                        account ->
                                ApiResponse.success(
                                        account,
                                        HttpStatus.OK.value()
                                )
                )
                .orElse(
                        ApiResponse.error(
                                DATA_NOT_FOUND.message(),
                                HttpStatus.NOT_FOUND.value()
                        )
                );
    }


    @Override
    public ApiResponse update(Integer accountId, AccountDTO accountDTO) {
        return accountRepository.findById(accountId)
                .map(
                        account -> {

                            account = accountMapper.mapDTOToEntity(accountDTO);

                            accountRepository.save(account);

                            return ApiResponse.success(
                                    ACCOUNT_UPDATED.message(),
                                    HttpStatus.OK.value()
                            );
                        }

                ).orElse(

                        ApiResponse.error(
                                ACCOUNT_NOT_FOUND.message(),
                                HttpStatus.CONFLICT.value()
                        )

                );
    }

    @Override
    public ApiResponse delete(Integer id) {

        if (accountRepository.existsById(id)) {
            templateRepository.deleteAllByAccountId(id);
            accountRepository.deleteById(id);
            TransactionUtils.deleteTransactionsInBatch(transactionRepository, id);

            return ApiResponse.success(
                    ACCOUNT_DELETED.message(),
                    HttpStatus.OK.value()
            );
        }

        return ApiResponse.error(
                ACCOUNT_NOT_FOUND.message(),
                HttpStatus.NOT_FOUND.value()
        );
    }


    @Override
    public ApiResponse getUserAccounts(Integer userId) {

        List<AccountDTO> accounts = accountMapper.listMapEntityToDTO(
                accountRepository.findAccountsByUserId(userId, Sort.by(Sort.Direction.ASC, "balance"))
        );

        return ApiResponse.success(
                accounts,
                HttpStatus.OK.value()
        );

    }

    @Override
    public ApiResponse searchByName(String accountName) {

        Integer userId = Objects.requireNonNull(getCurrentUser()).getId();
        List<AccountDTO> accounts = accountMapper.listMapEntityToDTO(
                accountRepository.searchAccountByNameContainingIgnoreCaseAndUserId(accountName, userId)
        );

        return ApiResponse.success(
                accounts,
                HttpStatus.OK.value()
        );

    }

}
