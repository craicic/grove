package org.motoc.gamelibrary.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.motoc.gamelibrary.dto.ThemeDto;
import org.motoc.gamelibrary.model.Theme;
import org.springframework.data.domain.Page;

/**
 * Maps model to dto and and dto to model
 *
 * @author RouzicJ
 */
@Mapper(componentModel = "spring")
public interface ThemeMapper {

    ThemeMapper INSTANCE = Mappers.getMapper(ThemeMapper.class);

    default Page<ThemeDto> themePageToThemePageDto(Page<Theme> themePage) {
        return themePage.map(this::themeToThemeDto);
    }

    ThemeDto themeToThemeDto(Theme theme);

    @Mapping(target = "games", ignore = true)
    Theme themeDtoToTheme(ThemeDto theme);
}
