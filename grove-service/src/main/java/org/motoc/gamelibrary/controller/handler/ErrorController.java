package org.motoc.gamelibrary.controller.handler;

import org.motoc.gamelibrary.technical.error.ErrorDetails;
import org.motoc.gamelibrary.technical.exception.ChildAndParentException;
import org.motoc.gamelibrary.technical.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Handles most of the errors and formats the output
 */
@ControllerAdvice
public class ErrorController {

    private static final Logger logger = LoggerFactory.getLogger(ErrorController.class);

    @ResponseBody
    @ExceptionHandler(ChildAndParentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorDetails childAndParentHandler(ChildAndParentException ex, WebRequest request) {
        ErrorDetails error = new ErrorDetails(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false),
                HttpStatus.BAD_REQUEST);
        logger.warn("In childAndParentHandler, new error treated : " + error);
        return error;
    }

    @ResponseBody
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorDetails illegalArgumentHandler(IllegalArgumentException ex, WebRequest request) {
        ErrorDetails error = new ErrorDetails(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false),
                HttpStatus.BAD_REQUEST);
        logger.warn("In illegalArgumentHandler, new error treated : " + error);
        return error;
    }

    @ResponseBody
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ErrorDetails notFoundHandler(NotFoundException ex, WebRequest request) {
        ErrorDetails error = new ErrorDetails(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false),
                HttpStatus.BAD_REQUEST);
        logger.info("In notFoundHandler, new error treated : " + error);
        return error;
    }

    @ResponseBody
    @ExceptionHandler(IOException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    ErrorDetails iOHandler(IOException ex, WebRequest request) {
        ErrorDetails error = new ErrorDetails(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false),
                HttpStatus.INTERNAL_SERVER_ERROR);
        logger.warn("In iOHandler, new error treated : " + error);
        return error;
    }

    @ResponseBody
    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorDetails illegalStateHandler(IllegalStateException ex, WebRequest request) {
        ErrorDetails error = new ErrorDetails(
                LocalDateTime.now(),
                ex.getMessage(),
                request.getDescription(false),
                HttpStatus.BAD_REQUEST);
        logger.warn("In illegalStateHandler, new error treated : " + error);
        return error;
    }
}
