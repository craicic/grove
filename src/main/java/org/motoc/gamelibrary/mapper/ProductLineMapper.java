package org.motoc.gamelibrary.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.motoc.gamelibrary.dto.ProductLineDto;
import org.motoc.gamelibrary.model.ProductLine;
import org.springframework.data.domain.Page;

/**
 * Maps model to dto and and dto to model
 *
 * @author RouzicJ
 */
@Mapper(componentModel = "spring")
public interface ProductLineMapper {

    ProductLineMapper INSTANCE = Mappers.getMapper(ProductLineMapper.class);

    default Page<ProductLineDto> pageToPageDto(Page<ProductLine> page) {
        return page.map(this::productLineToDto);
    }

    ProductLineDto productLineToDto(ProductLine productLine);

    @Mapping(target = "games", ignore = true)
    ProductLine dtoToProductLine(ProductLineDto productLineDto);
}
