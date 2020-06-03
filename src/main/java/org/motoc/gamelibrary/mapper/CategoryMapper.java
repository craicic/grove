package org.motoc.gamelibrary.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.motoc.gamelibrary.dto.CategoryDto;
import org.motoc.gamelibrary.model.Category;
import org.springframework.data.domain.Page;

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

    default Category dtoToCategoryIgnoringRelations(CategoryDto categoryDto) {
        if (categoryDto == null)
            return null;

        Category category = new Category();

        category.setId(categoryDto.getId());
        category.setName(categoryDto.getName());

        return category;
    }

    default CategoryDto categoryToDtoIgnoringRelations(Category category) {
        if (category == null) {
            return null;
        }

        CategoryDto categoryDto = new CategoryDto();

        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());

        return categoryDto;
    }

    default Category dtoToCategory(CategoryDto categoryDto) {
        if (categoryDto == null) {
            return null;
        }

        Category category = new Category();

        category.setId(categoryDto.getId());
        category.setName(categoryDto.getName());


        category.setParent(dtoToCategoryIgnoringRelations(categoryDto.getParent()));
        for (CategoryDto child : categoryDto.getChildren()) {
            category.getChildren().add(dtoToCategoryIgnoringRelations(child));
        }

        return category;
    }


    default CategoryDto categoryToDto(Category category) {
        if (category == null) {
            return null;
        }

        CategoryDto categoryDto = new CategoryDto();

        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());
        for (Category child : category.getChildren()) {
            categoryDto.getChildren().add(categoryToDtoIgnoringRelations(child));
        }
        categoryDto.setParent(categoryToDtoIgnoringRelations(category.getParent()));

        return categoryDto;
    }
}
