package org.motoc.gamelibrary.controller.handler;

import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

//@ControllerAdvice
public class ExceptionController {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionController.class);

    @ResponseBody
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    String handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        if (ex.contains(ConstraintViolationException.class)) {
            return "CONSTRAINT_ERROR";
        }
        logger.debug("Je sert a rien");
        return ex.getMessage();
    }
}
