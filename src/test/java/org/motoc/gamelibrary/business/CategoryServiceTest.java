package org.motoc.gamelibrary.business;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.motoc.gamelibrary.model.Category;
import org.motoc.gamelibrary.repository.CategoryRepository;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class CategoryServiceTest {

    @Mock
    CategoryRepository repository;

    @InjectMocks
    CategoryService service;

    @Test
    void edit_ShouldCreate() {
        final long parentId = 5L;
        final String parentName = "Réfléxion";
        final long id = 1L;
        final String name = "Stratégie";

        Category parent = new Category();
        parent.setId(parentId);
        parent.setName(parentName);

        Category category = new Category();
        category.setId(id);
        category.setName(name);
        category.setParent(parent);

        parent.getChildren().add(category);

        when(repository.findById(id)).thenReturn(Optional.empty());
        when(repository.save(category)).thenReturn(category);

        assertThat(service.edit(category, id)).isEqualTo(category);

    }

    @Test
    void edit_ShouldReplace() {
        final long parentId = 5L;
        final String parentName = "Réfléxion";
        final long id = 1L;
        final String name = "Stratégie";
        final String nameToBeReplaced = "Straéigte";

        Category toBeReplaced = new Category();
        toBeReplaced.setId(id);
        toBeReplaced.setName(nameToBeReplaced);

        Category parent = new Category();
        parent.setId(parentId);
        parent.setName(parentName);

        Category category = new Category();
        category.setId(id);
        category.setName(name);
        category.setParent(parent);

        parent.getChildren().add(category);

        when(repository.findById(id)).thenReturn(Optional.of(toBeReplaced));
        when(repository.save(toBeReplaced)).thenReturn(category);

        assertThat(service.edit(category, id)).isEqualTo(category);
    }

}