package uk.co.punishell.insideview.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import uk.co.punishell.insideview.model.exceptions.FileUploadException;
import uk.co.punishell.insideview.model.exceptions.VendorsException;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ModelAndView handleBindingError(Exception exception) {

        log.error("External Error");
        log.error(exception.getMessage());
        exception.printStackTrace();

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("bindingError");
        modelAndView.addObject("exception", exception);

        return modelAndView;
    }

    @ExceptionHandler(VendorsException.class)
    public ModelAndView handleVendorsException(Exception exception) {
        ModelAndView modelAndView = new ModelAndView();

        log.error("VENDORS ERROR ");
        exception.printStackTrace();

        modelAndView.setViewName("vendorsErrorRedirect");
        modelAndView.addObject("exception", exception);

        return modelAndView;
    }

    @ExceptionHandler(FileUploadException.class)
    public ModelAndView handleFileUploadError(Exception exception) {

        log.error("FILE UPLOAD ERROR! " + exception.getMessage());

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("FileUploadError");
        modelAndView.addObject("exception", exception);

        return modelAndView;
    }
}
