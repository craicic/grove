package org.motoc.gamelibrary.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.motoc.gamelibrary.dto.PublisherDto;
import org.motoc.gamelibrary.model.Publisher;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface PublisherMapper {

    PublisherMapper INSTANCE = Mappers.getMapper(PublisherMapper.class);

    PublisherDto publisherToDto(Publisher id);

    PublisherDto publisherNameToDto(Publisher id);

    @Mapping(target = "copies", ignore = true)
    @Mapping(target = "lowerCaseName", ignore = true)
    Publisher dtoToPublisher(PublisherDto publisherDto);

    default Page<PublisherDto> pageToPageDto(Page<Publisher> page) {
        return page.map(this::publisherToDto);
    }
}
