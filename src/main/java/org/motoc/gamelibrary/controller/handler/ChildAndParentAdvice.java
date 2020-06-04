package org.motoc.gamelibrary.controller.handler;

import org.motoc.gamelibrary.technical.exception.ChildAndParentException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ChildAndParentAdvice {

    @ResponseBody
    @ExceptionHandler(ChildAndParentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String childAndParentHandler(ChildAndParentException ex) {
        return ex.getMessage();
    }
}
