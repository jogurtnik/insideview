package uk.co.punishell.insideview.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import uk.co.punishell.insideview.model.exceptions.FileUploadException;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {

    /*@ExceptionHandler(Exception.class)
    public ModelAndView handleBindingError(Exception exception) {

        log.error("QUERY FORM OBJECT BINDING ERROR!");
        log.error(exception.getMessage());

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("bindingError");
        modelAndView.addObject("exception", exception);

        return modelAndView;
    }*/

    @ExceptionHandler(FileUploadException.class)
    public ModelAndView handleFileUploadError(Exception exception) {

        log.error("FILE UPLOAD ERROR! " + exception.getMessage());

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("FileUploadError");
        modelAndView.addObject("exception", exception);

        return modelAndView;
    }
}
