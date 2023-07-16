package org.motoc.gamelibrary.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.motoc.gamelibrary.domain.dto.CategoryDto;
import org.motoc.gamelibrary.domain.model.Category;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Maps model to dto and dto to model
 */
@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    default Page<CategoryDto> pageToPageDto(Page<Category> page) {
        return page.map(this::categoryToDto);
    }

    List<CategoryDto> categoriesToDto(List<Category> categories);

    @Mapping(target = "lowerCaseTitle", ignore = true)
    @Mapping(target = "games", ignore = true)
    Category dtoToCategory(CategoryDto categoryDto);

    CategoryDto categoryToDto(Category category);


}
