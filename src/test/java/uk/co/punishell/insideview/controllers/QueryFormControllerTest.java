package uk.co.punishell.insideview.controllers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import uk.co.punishell.insideview.model.database.services.RaceSearchEngine;
import uk.co.punishell.insideview.model.database.services.RunnerSearchEngine;

public class QueryFormControllerTest {

    QueryFormController queryFormController;

    @Mock
    RaceSearchEngine raceSearchEngine;

    @Mock
    RunnerSearchEngine runnerSearchEngine;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        queryFormController = new QueryFormController(raceSearchEngine, runnerSearchEngine);
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