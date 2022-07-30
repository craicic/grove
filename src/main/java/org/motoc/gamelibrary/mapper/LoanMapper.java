package org.motoc.gamelibrary.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.motoc.gamelibrary.dto.GameCopyDto;
import org.motoc.gamelibrary.dto.LoanDto;
import org.motoc.gamelibrary.model.GameCopy;
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

    @Mapping(target = "gameCopy", ignore = true)
    @Mapping(target = "account", ignore = true)
    Loan dtoToLoan(LoanDto loanDto);


    @Mapping(source = "game.id", target = "gameId")
    @Mapping(source = "game.name", target = "gameName")
    GameCopyDto copyToDto(GameCopy copy);

    @Mapping(target = "gameCopyId", source = "gameCopy.id")
    LoanDto loanToDto(Loan loan);
}
