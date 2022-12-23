package org.motoc.gamelibrary.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.motoc.gamelibrary.domain.dto.ThemeDto;
import org.motoc.gamelibrary.domain.dto.ThemeNameDto;
import org.motoc.gamelibrary.domain.model.Theme;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Maps model to dto and and dto to model
 */
@Mapper(componentModel = "spring")
public interface ThemeMapper {

    ThemeMapper INSTANCE = Mappers.getMapper(ThemeMapper.class);

    default Page<ThemeDto> pageToPageDto(Page<Theme> themePage) {
        return themePage.map(this::themeToDto);
    }

    List<ThemeDto> themesToDto(List<Theme> themes);

    ThemeDto themeToDto(Theme theme);

    Theme themeNameDtoToTheme(ThemeNameDto themeNameDto);

    @Mapping(target = "games", ignore = true)
    @Mapping(target = "lowerCaseName", ignore = true)
    Theme dtoToTheme(ThemeDto theme);
}
