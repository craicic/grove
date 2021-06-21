package org.motoc.gamelibrary.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.motoc.gamelibrary.dto.LoanDto;
import org.motoc.gamelibrary.model.Loan;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LoanMapper {

    LoanMapper INSTANCE = Mappers.getMapper(LoanMapper.class);

    default Page<LoanDto> pageToPageDto(Page<Loan> page) {
        return page.map(this::loanToDto);
    }

    List<LoanDto> loansToDto(List<Loan> loans);

    Loan dtoToLoan(LoanDto loanDto);

    LoanDto loanToDto(Loan loan);
}
