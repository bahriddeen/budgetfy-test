package com.budgetfy.app.mapstruct;

import com.budgetfy.app.mapstruct.base.ObjectMapper;
import com.budgetfy.app.model.Account;
import com.budgetfy.app.model.User;
import com.budgetfy.app.payload.dto.AccountDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountMapper extends ObjectMapper<AccountDTO, Account> {

    @Override
    @Mapping(source = "userId", target = "user")
    Account mapDTOToEntity(AccountDTO accountDTO);

    @Override
    @Mapping(source = "user.id", target = "userId")
    AccountDTO mapEntityToDTO(Account account);

    @Override
    List<Account> listMapDTOToEntity(List<AccountDTO> accountDTOS);

    @Override
    List<AccountDTO> listMapEntityToDTO(List<Account> entityList);



}


