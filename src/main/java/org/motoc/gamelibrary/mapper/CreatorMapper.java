package org.motoc.gamelibrary.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.motoc.gamelibrary.dto.CreatorDto;
import org.motoc.gamelibrary.dto.CreatorWithoutContactDto;
import org.motoc.gamelibrary.model.Creator;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface CreatorMapper {
    CreatorMapper INSTANCE = Mappers.getMapper(CreatorMapper.class);

    CreatorDto creatorToDto(Creator creator);

    default Page<CreatorDto> pageToPageDto(Page<Creator> page) {
        return page.map(this::creatorToDto);
    }

    @Mapping(target = "games", ignore = true)
//    @Mapping(target = "contact.creator", ignore = true)
//    @Mapping(target = "contact.publisher", ignore = true)
//    @Mapping(target = "contact.seller", ignore = true)
//    @Mapping(target = "contact.account", ignore = true)
    Creator dtoToCreator(CreatorDto creatorDto);


    CreatorWithoutContactDto creatorToCreatorWithoutContactDto(Creator creator);

}
