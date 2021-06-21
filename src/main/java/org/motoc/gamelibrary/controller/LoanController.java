package org.motoc.gamelibrary.controller;

import org.motoc.gamelibrary.business.LoanService;
import org.motoc.gamelibrary.dto.LoanDto;
import org.motoc.gamelibrary.mapper.LoanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Loans endpoints
 */
@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class LoanController {

    private static final Logger logger = LoggerFactory.getLogger(LoanController.class);

    private final LoanService service;

    private final LoanMapper mapper;

    @Autowired
    public LoanController(LoanService service) {
        this.service = service;
        this.mapper = LoanMapper.INSTANCE;
    }

    @GetMapping("/admin/loans/count")
    Long count() {
        logger.trace("count called");
        return service.count();
    }

    @GetMapping("/admin/loans")
    List<LoanDto> findAll() {
        logger.trace("findAll() called");
        return mapper.loansToDto(service.findAll());
    }

    @GetMapping("/admin/loans/{id}")
    LoanDto findById(@PathVariable Long id) {
        logger.trace("findById(id) called");
        return mapper.loanToDto(service.findById(id));
    }

    @GetMapping("/admin/loans/page")
    Page<LoanDto> findPage(Pageable pageable) {
        logger.trace("findPage(pageable) called");
        return mapper.pageToPageDto(service.findPage(pageable));
    }

    @PostMapping("/admin/loans")
    LoanDto save(@RequestBody @Valid LoanDto loan) {
        logger.trace("save(loan) called");
        return mapper.loanToDto(
                service.save(mapper.dtoToLoan(loan))
        );
    }
}
