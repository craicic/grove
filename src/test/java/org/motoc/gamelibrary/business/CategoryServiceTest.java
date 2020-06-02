package org.motoc.gamelibrary.business;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.motoc.gamelibrary.model.Category;
import org.motoc.gamelibrary.repository.CategoryRepository;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

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

        Set<Category> children = new HashSet<>();
        children.add(category);
        parent.setChildren(children);

        when(repository.findById(id)).thenReturn(Optional.empty());
        when(repository.save(category)).thenReturn(category);

        assertThat(service.edit(category, id)).isEqualTo(category);

    }

}