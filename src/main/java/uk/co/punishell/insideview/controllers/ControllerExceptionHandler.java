package uk.co.punishell.insideview.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import uk.co.punishell.insideview.model.exceptions.BindingFormException;
import uk.co.punishell.insideview.model.exceptions.FileUploadException;
import uk.co.punishell.insideview.model.exceptions.VendorsConnectionException;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ModelAndView handleUnknownException(Exception exception) {
        log.error(exception.getMessage());
        exception.printStackTrace();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error");
        modelAndView.addObject("exception", exception);

        return modelAndView;
    }

    @ExceptionHandler(VendorsConnectionException.class)
    public ModelAndView handleJshConnectionTimeoutException (Exception exception) {
        log.error(exception.getMessage());
        exception.printStackTrace();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("vendorsErrorRedirect");
        modelAndView.addObject("exception", exception);

        return modelAndView;
    }

    @ExceptionHandler(BindingFormException.class)
    public ModelAndView handleBindingError(Exception exception) {

        log.error("BINDING FORM EXCEPTION");
        log.error(exception.getMessage());
        exception.printStackTrace();

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("formProcessingError");
        modelAndView.addObject("exception", exception);

        return modelAndView;
    }

    @ExceptionHandler(FileUploadException.class)
    public ModelAndView handleFileUploadError(Exception exception) {

        log.error("FILE UPLOAD ERROR! ");
        log.error(exception.getMessage());

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("FileUploadError");
        modelAndView.addObject("exception", exception);

        return modelAndView;
    }
}
