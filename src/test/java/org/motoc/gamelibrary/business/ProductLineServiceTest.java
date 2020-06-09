package org.motoc.gamelibrary.business;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.motoc.gamelibrary.model.ProductLine;
import org.motoc.gamelibrary.repository.jpa.ProductLineRepository;
import org.springframework.data.domain.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class ProductLineServiceTest {

    @Mock
    ProductLineRepository repository;

    @InjectMocks
    ProductLineService service;

    @Test
    void count() {
        when(repository.count()).thenReturn(5L);

        assertThat(repository.count()).isEqualTo(5L);
    }

    @Test
    void save() {
        final String name = "Carcassonne";
        ProductLine toPersist = new ProductLine();
        toPersist.setName(name);

        ProductLine toReturn = new ProductLine();
        toReturn.setId(5L);
        toReturn.setName(name);

        when(repository.saveAndFlush(toPersist)).thenReturn(toReturn);

        assertThat(service.save(toPersist)).isSameAs(toReturn);
    }

    @Test
    void findById() {

        final long id = 4L;
        ProductLine productLine = new ProductLine();
        productLine.setId(id);
        productLine.setName("Carcassonne");


        when(repository.findById(4L)).thenReturn(Optional.of(productLine));

        assertThat(service.findById(id)).isSameAs(productLine);
    }

    @Test
    void findById_NotFoundException() {
        final long id = 4L;

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> {
            service.findById(id);
        }).hasMessageContaining("Could not find " + id);
    }

    @Test
    void findPage() {
        final Pageable pageable = PageRequest.of(0, 2, Sort.by(Sort.Order.asc("name")));

        ProductLine productLineA = new ProductLine();
        productLineA.setId(5L);
        productLineA.setName("Aventure");

        ProductLine productLineB = new ProductLine();
        productLineB.setId(7L);
        productLineB.setName("Médiéval");

        List<ProductLine> productLines = List.of(productLineA, productLineB);
        Page<ProductLine> pageToReturn = new PageImpl<>(productLines, pageable, productLines.size());

        when(repository.findAll(pageable)).thenReturn(pageToReturn);

        assertThat(service.findPage(pageable)).isSameAs(pageToReturn);
    }

    @Test
    void edit_ShouldReplace() {
        final long id = 1L;
        final String name = "Carcassonne";

        ProductLine pLToReplace = new ProductLine();
        pLToReplace.setId(id);
        pLToReplace.setName("Chtulu");

        ProductLine productLine = new ProductLine();
        productLine.setId(id);
        productLine.setName(name);


        when(repository.findById(id)).thenReturn(Optional.of(pLToReplace));
        when(repository.save(pLToReplace)).thenReturn(productLine);

        assertThat(service.edit(productLine, id)).isEqualTo(productLine);
    }

    @Test
    void edit_ShouldCreate() {
        final long id = 1L;
        final String name = "Chtulu";

        ProductLine productLine = new ProductLine();
        productLine.setId(id);
        productLine.setName(name);

        when(repository.findById(id)).thenReturn(Optional.empty());
        when(repository.save(productLine)).thenReturn(productLine);

        assertThat(service.edit(productLine, id)).isEqualTo(productLine);
    }
}