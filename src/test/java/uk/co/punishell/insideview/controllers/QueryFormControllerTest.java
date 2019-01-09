package uk.co.punishell.insideview.controllers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import uk.co.punishell.insideview.model.database.services.QueryFormConversionService;

public class QueryFormControllerTest {

    @Mock
    QueryFormConversionService queryFormConversionService;

    QueryFormController queryFormController;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        queryFormController = new QueryFormController(queryFormConversionService);
    }

    @Test
    public void getQueryPage() throws Exception {
    }

    @Test
    public void performQuery() {
    }

    @Test
    public void getQueryFormData() {
    }
}