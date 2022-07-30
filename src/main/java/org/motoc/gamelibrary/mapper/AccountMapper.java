package org.motoc.gamelibrary.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.motoc.gamelibrary.domain.dto.AccountDto;
import org.motoc.gamelibrary.domain.dto.ContactDto;
import org.motoc.gamelibrary.domain.dto.LoanDto;
import org.motoc.gamelibrary.domain.model.Account;
import org.motoc.gamelibrary.domain.model.Contact;
import org.motoc.gamelibrary.domain.model.Loan;
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

    AccountDto accountToDto(Account account);

    ContactDto contactToDto(Contact contact);

    @Mapping(target = "gameCopyId", source = "gameCopy.id")
    LoanDto loanToDto(Loan loan);


}
