package org.motoc.gamelibrary.service;

import org.motoc.gamelibrary.domain.dto.CategoryDto;
import org.motoc.gamelibrary.mapper.CategoryMapper;
import org.motoc.gamelibrary.repository.jpa.CategoryRepository;
import org.motoc.gamelibrary.technical.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import java.util.List;

/**
 * Perform service logic on the entity Category
 */
@Service
@Transactional
public class CategoryService {

    private static final Logger logger = LoggerFactory.getLogger(CategoryService.class);

    private final CategoryRepository repository;

    private final CategoryMapper mapper;

    @Autowired
    public CategoryService(CategoryRepository repository) {
        this.mapper = CategoryMapper.INSTANCE;
        this.repository = repository;
    }

    public CategoryDto save(@Valid CategoryDto gc) {
        return mapper.categoryToDto(repository.save(mapper.dtoToCategory(gc)));
    }


    public long count() {
        return repository.count();
    }


    public CategoryDto findById(long id) {
        return mapper.categoryToDto(repository.findById(id)
                .orElseThrow(() -> {
                    throw new NotFoundException("No category of id=" + id + " found.");
                }));
    }


    public Page<CategoryDto> findPage(Pageable pageable) {
        return mapper.pageToPageDto(repository.findAll(pageable));
    }


    /**
     * Edits a category by id
     */
    public CategoryDto edit(@Valid CategoryDto category, Long id) {
        return mapper.categoryToDto(repository.findById(id)
                .map(categoryFromPersistence -> {
                    categoryFromPersistence.setTitle(category.getTitle());
                    logger.debug("Found category of id={} : {}", id, categoryFromPersistence);
                    return repository.save(categoryFromPersistence);
                })
                .orElseGet(() -> {
                    category.setId(id);
                    logger.debug("No category of id={} found. Set category : {}", id, category);
                    return repository.save(mapper.dtoToCategory(category));
                }));
    }

    /**
     * Calls the DAO to delete a category by id
     */
    public void remove(Long id) {
        logger.debug("deleting (if exist) category of id=" + id);
        repository.remove(id);
    }


    /**
     * Find all categories' names
     */
    public List<String> findTitles() {
        return repository.findTitles();
    }

    /**
     * Find all categories
     */
    public List<CategoryDto> findAll() {
        return mapper.categoriesToDto(repository.findAll(Sort.by(Sort.Direction.ASC, "title")));
    }
}
