package org.motoc.gamelibrary.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.motoc.gamelibrary.domain.model.Publisher;
import org.motoc.gamelibrary.repository.jpa.PublisherRepository;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class PublisherServiceTest {

    @Mock
    PublisherRepository repository;

    @InjectMocks
    PublisherService service;

    @Test
    void edit_ShouldReplace() {
        final long id = 1L;
        final String name = "Asmodee";

        Publisher toReplace = new Publisher();
        toReplace.setId(id);
        toReplace.setName("Momo tuning");

        Publisher publisher = new Publisher();
        publisher.setId(id);
        publisher.setName(name);

        when(repository.findById(id)).thenReturn(Optional.of(toReplace));
        when(repository.save(toReplace)).thenReturn(publisher);

        assertThat(service.edit(publisher, id)).isEqualTo(publisher);
    }

    @Test
    void edit_ShouldCreate() {
        final long id = 1L;
        final String name = "Asmodee";


        Publisher publisher = new Publisher();
        publisher.setId(id);
        publisher.setName(name);

        when(repository.findById(id)).thenReturn(Optional.empty());
        when(repository.save(publisher)).thenReturn(publisher);

        assertThat(service.edit(publisher, id)).isEqualTo(publisher);
    }
}