package uk.co.punishell.insideview.controllers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import uk.co.punishell.insideview.model.ResourceData.FileValidator;
import uk.co.punishell.insideview.model.database.services.RaceService;
import uk.co.punishell.insideview.model.managers.DBPopulatingManager;

public class DatabaseManagementControllerTest {

    MockMultipartFile multipartFile;

    DatabaseManagementController databaseManagementController;

    @Mock
    DBPopulatingManager dbPopulatingManager;

    @Mock
    RaceService raceService;

    @Mock
    FileValidator validator;

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);

        multipartFile = new MockMultipartFile("data","filename.txt","text/plain","some xml".getBytes());

        databaseManagementController = new DatabaseManagementController(dbPopulatingManager, validator, raceService);

    }


    @Test
    public void getDatabaseManagementWebsite() throws Exception {
    }

    @Test
    public void uploadFileHandler() {
    }
}