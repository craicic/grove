package org.motoc.gamelibrary.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.motoc.gamelibrary.dto.CategoryDto;
import org.motoc.gamelibrary.model.Category;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Maps model to dto and and dto to model
 *
 * @author RouzicJ
 */
@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    default Page<CategoryDto> pageToPageDto(Page<Category> page) {
        return page.map(this::categoryToDto);
    }

    List<CategoryDto> categoriesToDto(List<Category> categories);

    Category dtoToCategory(CategoryDto categoryDto);

    CategoryDto categoryToDto(Category category);


}
