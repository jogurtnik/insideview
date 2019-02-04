package uk.co.punishell.insideview.model.managers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import uk.co.punishell.insideview.view.web.forms.QueryFormData;
import uk.co.punishell.insideview.view.web.forms.QueryFormResult;

@Slf4j
@Component
public class QueryFormProcessingManager {

    QueryFormData queryFormData;
    QueryFormResult queryFormResult;

    public QueryFormResult processQueryForm (QueryFormData queryFormData) {

        this.queryFormData = queryFormData;

        // TODO

        return queryFormResult;
    }
}
