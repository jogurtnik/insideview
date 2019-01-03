package uk.co.punishell.insideview.model.database.services;

import org.springframework.stereotype.Service;
import uk.co.punishell.insideview.model.services.web.forms.QueryFormData;
import uk.co.punishell.insideview.model.services.web.forms.QueryFormResult;

@Service
public class QueryFormConversionService {

    QueryFormData queryFormData;
    QueryFormResult queryFormResult;

    public QueryFormResult convert(QueryFormData queryFormData){
        this.queryFormData = queryFormData;

        // TODO

        return queryFormResult;
    }
}
