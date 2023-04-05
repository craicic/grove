package org.motoc.gamelibrary.controller;

import org.motoc.gamelibrary.domain.dto.PublisherDto;
import org.motoc.gamelibrary.domain.dto.PublisherNameDto;
import org.motoc.gamelibrary.domain.dto.PublisherNoIdDto;
import org.motoc.gamelibrary.service.PublisherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class PublisherController {

    private static final Logger logger = LoggerFactory.getLogger(PublisherController.class);

    private final PublisherService service;

    public PublisherController(PublisherService service) {
        this.service = service;
    }

    @GetMapping("/admin/publishers/count")
    Long count() {
        logger.trace("count() called");
        return service.count();
    }

    @GetMapping("/admin/publishers/names")
    List<PublisherNameDto> findNames() {
        logger.trace("findNames called");
        return service.findNames();
    }

    @GetMapping("/admin/publishers/{id}")
    PublisherDto findById(@PathVariable Long id) {
        logger.trace("findById(id) called");
        return service.findById(id);
    }

    @GetMapping("/admin/publishers/page")
    Page<PublisherDto> findPage(Pageable pageable,
                                @RequestParam(name = "search", required = false) String keyword) {
        logger.trace("findPage(pageable) called");
        if (keyword == null) {
            return service.findPage(pageable);
        } else {
            logger.trace("findPage(" + keyword + ", pageable) called");
            return service.quickSearch(keyword, pageable);
        }
    }

    @PostMapping("/admin/publishers")
    PublisherDto save(@RequestBody @Valid PublisherNoIdDto dto) {
        logger.trace("save(publisher) called");
        return service.save(dto);
    }

    @PutMapping("/admin/publishers/{id}")
    PublisherDto edit(@RequestBody @Valid PublisherDto publisherDto,
                      @PathVariable Long id) {
        logger.trace("edit(publisher, id) called");
        return service.edit(publisherDto, id);
    }

    @DeleteMapping("/admin/publishers/{id}")
    void deleteById(@PathVariable Long id) {
        logger.trace("deleteById(id) called");
        service.remove(id);
    }

    @DeleteMapping("admin/publishers/{publisherId}/contact/")
    void deleteContact(@PathVariable Long publisherId) {
        logger.trace("deleteContact(publisherId, contactId) called");
        service.removeContact(publisherId);
    }
}
