package org.motoc.gamelibrary.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.motoc.gamelibrary.dto.ThemeDto;
import org.motoc.gamelibrary.model.Theme;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface ThemeMapper {

    ThemeMapper INSTANCE = Mappers.getMapper(ThemeMapper.class);

    default Page<ThemeDto> themePageToThemePageDto(Page<Theme> themePage) {
        return themePage.map(this::themeToThemeDto);
    }

    ThemeDto themeToThemeDto(Theme theme);

    Theme themeDtoToTheme(ThemeDto theme);
}
