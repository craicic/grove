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

//    @Mapping(target ="contact.street", source = "street")
//    @Mapping(target ="contact.city", source = "city")
//    @Mapping(target ="contact.postCode", source = "postCode")
//    @Mapping(target ="contact.country", source = "country")
//    @Mapping(target ="contact.houseNumber", source = "houseNumber")
//    @Mapping(target ="contact.phoneNumber", source = "phoneNumber")
//    @Mapping(target ="contact.website", source = "website")
//    @Mapping(target ="contact.mailAddress", source = "mailAddress")
CreatorDto creatorToDto(Creator creator);

    @Mapping(target = "games", ignore = true)
//    @Mapping(target = "street", source="contact.street")
//    @Mapping(target = "city", source="contact.city")
//    @Mapping(target = "postCode", source="contact.postCode")
//    @Mapping(target = "country", source="contact.country")
//    @Mapping(target = "houseNumber", source="contact.houseNumber")
//    @Mapping(target = "phoneNumber", source="contact.phoneNumber")
//    @Mapping(target = "website", source="contact.website")
//    @Mapping(target = "mailAddress", source="contact.mailAddress")
    @Mapping(target = "lowerCaseFirstName", ignore = true)
    @Mapping(target = "lowerCaseLastName", ignore = true)
    Creator dtoToCreator(CreatorDto creatorDto);

    default Page<CreatorDto> pageToPageDto(Page<Creator> page) {
        return page.map(this::creatorToDto);
    }

    CreatorWithoutContactDto creatorToCreatorWithoutContactDto(Creator creator);

}
