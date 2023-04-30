package com.budgetfy.app.service.impl;

import com.budgetfy.app.enums.TransactionType;
import com.budgetfy.app.mapstruct.TransactionMapper;
import com.budgetfy.app.model.Account;
import com.budgetfy.app.model.Transaction;
import com.budgetfy.app.payload.ItemList;
import com.budgetfy.app.payload.dto.TransactionDTO;
import com.budgetfy.app.payload.response.ApiResponse;
import com.budgetfy.app.repository.AccountRepository;
import com.budgetfy.app.repository.TransactionRepository;
import com.budgetfy.app.service.TransactionService;
import com.budgetfy.app.service.functionality.Create;
import com.budgetfy.app.service.functionality.Delete;
import com.budgetfy.app.service.functionality.Read;
import com.budgetfy.app.service.functionality.Update;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.budgetfy.app.enums.Message.*;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl
        implements
        TransactionService,
        Create<TransactionDTO>,
        Update<TransactionDTO>,
        Delete<Integer>,
        Read<Integer> {

    private final TransactionMapper transactionMapper;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    @Override
    @Transactional
    public ApiResponse create(TransactionDTO transactionDTO) {

        Integer accountId = transactionDTO.accountId();
        TransactionType transactionType = transactionDTO.transactionType();
        Optional<Account> accountOptional = accountRepository.findById(accountId);

        if (accountOptional.isEmpty())
            return ApiResponse.error(
                    TRANSACTION_NOT_FOUND.message(),
                    HttpStatus.NOT_FOUND.value()
            );

        Account account = accountOptional.get();

        Transaction transaction = transactionMapper.mapDTOToEntity(transactionDTO);

        switch (transactionType) {

            case INCOME -> account.setBalance(account.getBalance() + transaction.getAmount());

            case EXPENSE -> account.setBalance(account.getBalance() - transaction.getAmount());

            case TRANSFER -> {
                return validAndTransfer(transactionDTO, true);
            }

            default -> throw new IllegalArgumentException("Invalid transaction type: " + transactionType);

        }

        accountRepository.save(account);
        transactionRepository.save(transaction);

        return ApiResponse.success(
                TRANSACTION_CREATED.message(),
                HttpStatus.CREATED.value()
        );

    }


    @Override
    @Transactional
    public ApiResponse delete(Integer transactionId) {

        Optional<Transaction> transaction = transactionRepository.findById(transactionId);

        if (transaction.isPresent()) {

            Transaction _transaction = transaction.get();
            Account account = _transaction.getAccount();
            TransactionType transactionType = _transaction.getTransactionType();

            switch (transactionType) {

                case INCOME -> account.setBalance(account.getBalance() - _transaction.getAmount());

                case EXPENSE -> account.setBalance(account.getBalance() + _transaction.getAmount());

                case TRANSFER -> {
                    TransactionDTO transactionDTO = transactionMapper.mapEntityToDTO(_transaction);
                    return validAndTransfer(transactionDTO, false);
                }

            }

            accountRepository.save(account);
            transactionRepository.deleteById(transactionId);

        }

        return ApiResponse.success(
                TRANSACTION_DELETED.message(),
                HttpStatus.NO_CONTENT.value()
        );

    }

    @Override
    public ApiResponse deleteAllById(ItemList itemList) {


        itemList.integerList().forEach(this::delete);

        return ApiResponse.success(
                TRANSACTION_DELETED.message(),
                HttpStatus.NO_CONTENT.value()
        );

    }

    @Override
    public ApiResponse getDataById(Integer transactionId) {
        return transactionRepository.findById(transactionId)
                .map(
                        transaction ->
                                ApiResponse.success(
                                        transactionMapper.mapEntityToDTO(transaction),
                                        HttpStatus.OK.value()
                                )
                ).orElse(
                        ApiResponse.error(
                                TRANSACTION_NOT_FOUND.message(),
                                HttpStatus.NOT_FOUND.value()
                        )
                );
    }


    /*
     * 1. */
    @Override
    @Transactional
    public ApiResponse update(Integer transactionId, TransactionDTO transactionDTO) {
        Optional<Transaction> transaction = transactionRepository.findById(transactionId);

        if (transaction.isPresent()) {

            Transaction _transaction = transactionMapper.mapDTOToEntity(transactionDTO);
            TransactionType transactionType = _transaction.getTransactionType();
            Account account = _transaction.getAccount();

            switch (transactionType) {

                case INCOME -> account.setBalance(account.getBalance() + _transaction.getAmount());

                case EXPENSE -> account.setBalance(account.getBalance() - _transaction.getAmount());

                case TRANSFER -> {
                    return validAndTransfer(transactionDTO, false);
                }

            }

            accountRepository.save(account);
            transactionRepository.save(_transaction);
        }
        return ApiResponse.success(TRANSACTION_UPDATED, HttpStatus.OK.value());
    }


    @Override
    public ApiResponse loadTransactionsByAccountId(Integer accountId, Integer pageNo, Integer pageSize, String sortBy) {

        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        Slice<Transaction> transactions = transactionRepository.findTransactionByAccountId(accountId, paging);

        if (transactions.hasContent()) {
            List<TransactionDTO> list = transactionMapper.listMapEntityToDTO(transactions.stream().toList());
            return ApiResponse.success(list, HttpStatus.OK.value());
        }

        return ApiResponse.success(
                NO_CONTENT.message(),
                HttpStatus.NO_CONTENT.value()
        );

    }

    @Override
    public ApiResponse loadTransactions(Integer pageNo, Integer pageSize, String sortBy) {

        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        Page<Transaction> transactions = transactionRepository.findAll(paging);

        if (transactions.hasContent()) {
            List<TransactionDTO> list = transactionMapper.listMapEntityToDTO(transactions.stream().toList());
            return ApiResponse.success(list, HttpStatus.OK.value());
        }

        return ApiResponse.success(
                NO_CONTENT,
                HttpStatus.NO_CONTENT.value()
        );

    }


    private ApiResponse validAndTransfer(TransactionDTO transactionDTO, boolean newTransfer) {

        Optional<Account> senderAccount = accountRepository.findById(transactionDTO.accountId());
        Optional<Account> recipientAccount = accountRepository.findById(transactionDTO.t_accountId());

        if (senderAccount.isEmpty() || recipientAccount.isEmpty())
            return ApiResponse.error(ACCOUNT_NOT_FOUND.message(), HttpStatus.NOT_FOUND.value());


        Account _senderAccount = senderAccount.get();
        Account _recipientAccount = recipientAccount.get();

        double amount = transactionDTO.amount();

        if (newTransfer && (_senderAccount.getBalance() < amount || amount <= 0 || transactionDTO.date().after(new Date())))
            return ApiResponse.error(
                    CONFLICT.message(),
                    HttpStatus.CONFLICT.value()
            );

        if (!newTransfer) {
            _senderAccount.setBalance(_senderAccount.getBalance() + amount);
            _recipientAccount.setBalance(_recipientAccount.getBalance() - amount);
        }

        _senderAccount.setBalance(_senderAccount.getBalance() - amount);
        _recipientAccount.setBalance(_recipientAccount.getBalance() + amount);

        accountRepository.saveAll(List.of(_senderAccount, _recipientAccount));

        Transaction transaction = transactionMapper.mapDTOToEntity(transactionDTO);
        transactionRepository.save(transaction);

        return ApiResponse.success(
                TRANSACTION_CREATED.message(),
                HttpStatus.CREATED.value()
        );

    }

}
