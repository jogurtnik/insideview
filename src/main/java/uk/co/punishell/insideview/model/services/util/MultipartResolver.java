package uk.co.punishell.insideview.model.services.util;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Service
public class MultipartResolver {

    private CommonsMultipartResolver multipartResolver;

    public MultipartResolver() {
        multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(20848820);
    }

    public CommonsMultipartResolver getMultipartResolver() {
        return multipartResolver;
    }

}
