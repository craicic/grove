package org.motoc.gamelibrary.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.motoc.gamelibrary.dto.AccountDto;
import org.motoc.gamelibrary.model.Account;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Maps model to dto and and dto to model
 */
@Mapper(componentModel = "spring")
public interface AccountMapper {

    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

    default Page<AccountDto> pageToPageDto(Page<Account> page) {
        return page.map(this::accountToDto);
    }

    List<AccountDto> accountsToDto(List<Account> accounts);

    Account dtoToAccount(AccountDto accountDto);

    AccountDto accountToDto(Account account);

}
