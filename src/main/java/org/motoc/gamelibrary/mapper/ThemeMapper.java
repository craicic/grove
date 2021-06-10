package org.motoc.gamelibrary.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.motoc.gamelibrary.dto.ThemeDto;
import org.motoc.gamelibrary.model.Theme;
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

    @Mapping(target = "games", ignore = true)
    Theme dtoToTheme(ThemeDto theme);
}
