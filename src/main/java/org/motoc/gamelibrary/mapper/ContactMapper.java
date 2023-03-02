package org.motoc.gamelibrary.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.motoc.gamelibrary.domain.dto.ContactDto;
import org.motoc.gamelibrary.domain.model.Contact;

@Mapper(componentModel = "spring")
public interface ContactMapper {

    ContactMapper INSTANCE = Mappers.getMapper(ContactMapper.class);

    Contact map(ContactDto value);
}
