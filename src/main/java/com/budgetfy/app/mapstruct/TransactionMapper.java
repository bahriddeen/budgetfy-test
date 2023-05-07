package com.budgetfy.app.mapstruct;

import com.budgetfy.app.mapstruct.base.ObjectMapper;
import com.budgetfy.app.model.Transaction;
import com.budgetfy.app.payload.dto.TransactionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TransactionMapper extends ObjectMapper<TransactionDTO, Transaction> {

    @Override
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(source = "accountId", target = "account")
    @Mapping(source = "categoryId", target = "category")
    Transaction mapDTOToEntity(TransactionDTO transactionDTO);

    @Override
    @Mapping(source = "account.id", target = "accountId")
    @Mapping(source = "category.id", target = "categoryId")
    TransactionDTO mapEntityToDTO(Transaction transaction);

    @Override
    List<Transaction> listMapDTOToEntity(List<TransactionDTO> transactionDTOS);

    @Override
    List<TransactionDTO> listMapEntityToDTO(List<Transaction> transactions);
}
