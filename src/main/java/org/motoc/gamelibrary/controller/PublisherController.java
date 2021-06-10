package org.motoc.gamelibrary.controller;

import org.motoc.gamelibrary.business.PublisherService;
import org.motoc.gamelibrary.dto.PublisherDto;
import org.motoc.gamelibrary.dto.PublisherNameDto;
import org.motoc.gamelibrary.mapper.PublisherMapper;
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

    private final PublisherMapper mapper;

    public PublisherController(PublisherService service) {
        this.service = service;
        this.mapper = PublisherMapper.INSTANCE;
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
        return mapper.publisherToDto(service.findById(id));
    }

    @GetMapping("/admin/publishers/page")
    Page<PublisherDto> findPage(Pageable pageable,
                                @RequestParam(name = "search", required = false) String keyword) {
        logger.trace("findPage(pageable) called");
        if (keyword == null) {
            return mapper.pageToPageDto(service.findPage(pageable));
        } else {
            logger.trace("findPage(" + keyword + ", pageable) called");
            return mapper.pageToPageDto(service.quickSearch(keyword, pageable));
        }
    }

    @PostMapping("/admin/publishers")
    PublisherDto save(@RequestBody @Valid PublisherDto publisherDto,
                      @RequestParam(value = "has-contact", required = false) boolean hasContact) {
        logger.trace("save(publisher) called");
        return mapper.publisherToDto(service.save(mapper.dtoToPublisher(publisherDto), hasContact));
    }

    @PutMapping("/admin/publishers/{id}")
    PublisherDto edit(@RequestBody @Valid PublisherDto publisherDto,
                      @PathVariable Long id) {
        logger.trace("edit(publisher, id) called");
        return mapper.publisherToDto(service.edit(mapper.dtoToPublisher(publisherDto), id));
    }

    @DeleteMapping("/admin/publishers/{id}")
    void deleteById(@PathVariable Long id) {
        logger.trace("deleteById(id) called");
        service.remove(id);
    }

    @DeleteMapping("admin/publishers/{publisherId}/contact/{contactId}")
    void deleteContact(@PathVariable Long publisherId,
                       @PathVariable Long contactId) {
        logger.trace("deleteContact(publisherId, contactId) called");
        service.removeContact(publisherId, contactId);
    }
}
