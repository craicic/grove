package org.motoc.gamelibrary.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.motoc.gamelibrary.dto.AccountDto;
import org.motoc.gamelibrary.dto.ContactDto;
import org.motoc.gamelibrary.dto.LoanDto;
import org.motoc.gamelibrary.model.Account;
import org.motoc.gamelibrary.model.Contact;
import org.motoc.gamelibrary.model.Loan;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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


    default AccountDto accountToDto(Account account) {
        if (account == null) {
            return null;
        }


        return new AccountDto(
                account.getId(),
                account.getFirstName(),
                account.getLastName(),
                account.getUsername(),
                account.getMembershipNumber(),
                account.getRenewalDate(),
                loanSetToLoanDtoList(account.getLoans()),
                contactToContactDto(account.getContact()));
    }

    default ContactDto contactToContactDto(Contact contact) {
        if (contact == null) {
            return null;
        }

        return new ContactDto(
                contact.getPostCode(),
                contact.getStreet(),
                contact.getCity(),
                contact.getHouseNumber(),
                contact.getCountry(),
                contact.getPhoneNumber(),
                contact.getWebsite(),
                contact.getMailAddress()
        );

    }


    default List<LoanDto> loanSetToLoanDtoList(Set<Loan> set) {
        if (set == null) {
            return null;
        }

        List<LoanDto> list = new ArrayList<>();
        for (Loan loan : set) {
            list.add(loanToLoanDto(loan));
        }

        return list;
    }

    default LoanDto loanToLoanDto(Loan loan) {
        if (loan == null) {
            return null;
        }

        return new LoanDto(
                loan.getId(),
                loan.getLoanStartTime(),
                loan.getLoanEndTime(),
                loan.getGameCopy().getId(),
                loan.isClosed()
        );
    }
}
