package org.motoc.gamelibrary.business;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.motoc.gamelibrary.model.Category;
import org.motoc.gamelibrary.repository.CategoryRepository;
import org.motoc.gamelibrary.repository.CategoryRepositoryCustom;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class CategoryServiceTest {

    @Mock
    CategoryRepository repository;

    @Mock
    CategoryRepositoryCustom repositoryCustom;

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

    @Test
    void addChildren_shouldThrow_NotFoundException() {
        final long catId = 5L;
        List<Long> childrenIds = Arrays.asList(1L, 2L, 3L);

        when(repository.findById(catId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.addChildren(childrenIds, catId)).hasMessageContaining("Could not find " + catId);

    }

    @Test
    void addChildren_shouldThrow_ChildAndParentException() {
        final long catId = 5L;
        List<Long> childrenIds = Arrays.asList(1L);

        Category childButAlsoParent = new Category();
        childButAlsoParent.setId(1L);
        childButAlsoParent.setName("Refléxion");

        Category category = new Category();
        category.setId(catId);
        category.setName("Stratégie");
        category.setParent(childButAlsoParent);

        childButAlsoParent.getChildren().add(category);
        List<Category> children = Arrays.asList(childButAlsoParent);

        when(repository.findById(catId)).thenReturn(Optional.of(category));
        when(repository.findAllById(childrenIds)).thenReturn(children);

        assertThatThrownBy(() -> service.addChildren(childrenIds, catId)).hasMessageContaining("The child : " + childButAlsoParent + " is also the parent of the given : " + category);

    }

    @Test
    void addChildren() {
        final long catId = 5L;
        List<Long> childrenIds = Arrays.asList(1L);

        Category child = new Category();
        child.setId(1L);
        child.setName("Refléxion");

        Category category = new Category();
        category.setId(catId);
        category.setName("Stratégie");

        List<Category> children = Arrays.asList(child);

        when(repository.findById(catId)).thenReturn(Optional.of(category));
        when(repository.findAllById(childrenIds)).thenReturn(children);

        Category toReturn = new Category();
        toReturn.setId(category.getId());
        toReturn.setName(category.getName());
        toReturn.getChildren().add(child);

        when(repositoryCustom.saveWithChildren(children, category)).thenReturn(toReturn);
        assertThat(service.addChildren(childrenIds, catId)).isEqualTo(toReturn);
    }

    @Test
    void addParent_shouldThrow_ChildAndParentException_1() {
        final long catId = 5L;

        Category parent = new Category();
        parent.setId(1L);
        parent.setName("Refléxion");

        Category category = new Category();
        category.setId(catId);
        category.setName("Stratégie");

        when(repository.findById(catId)).thenReturn(Optional.of(category));

        parent.getChildren().add(category);

        assertThatThrownBy(() -> service.addParent(parent, catId)).hasMessageContaining("Category : " + parent.getName() + " is already the parent of " + category.getName());

    }

    @Test
    void addParent_shouldThrow_ChildAndParentException_2() {
        final long catId = 5L;

        Category parent = new Category();
        parent.setId(1L);
        parent.setName("Refléxion");

        Category category = new Category();
        category.setId(catId);
        category.setName("Stratégie");

        when(repository.findById(catId)).thenReturn(Optional.of(category));

        category.setParent(new Category());

        assertThatThrownBy(() -> service.addParent(parent, catId)).hasMessageContaining("The category of id=" + catId + " already has a parent");

    }

    @Test
    void addParent_shouldThrow_ChildAndParentException_3() {
        final long catId = 5L;

        Category parent = new Category();
        parent.setId(1L);
        parent.setName("Refléxion");

        Category category = new Category();
        category.setId(catId);
        category.setName("Stratégie");

        when(repository.findById(catId)).thenReturn(Optional.of(category));

        category.getChildren().add(parent);

        assertThatThrownBy(() -> service.addParent(parent, catId)).hasMessageContaining("Category " + parent.getName() + " is one of the children of " + category.getName());
    }

    @Test
    void addParent() {
        final long catId = 5L;

        Category parent = new Category();
        parent.setId(1L);
        parent.setName("Refléxion");

        Category category = new Category();
        category.setId(catId);
        category.setName("Stratégie");

        when(repository.findById(catId)).thenReturn(Optional.of(category));

        Category toReturn = new Category();
        toReturn.setId(category.getId());
        toReturn.setName(category.getName());
        toReturn.setParent(parent);

        when(repositoryCustom.saveWithParent(parent, category)).thenReturn(toReturn);

        assertThat(service.addParent(parent, catId)).isEqualTo(toReturn);
    }
}