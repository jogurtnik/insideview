package uk.co.punishell.insideview.controllers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import uk.co.punishell.insideview.model.managers.DBPopulatingManager;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class DatabaseManagementControllerTest {

    MockMultipartFile multipartFile;

    DatabaseManagementController databaseManagementController;

    @Mock
    DBPopulatingManager dbPopulatingManager;

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);

        multipartFile = new MockMultipartFile("data","filename.txt","text/plain","some xml".getBytes());

        databaseManagementController = new DatabaseManagementController(dbPopulatingManager);

    }


    @Test
    public void getDatabaseManagementWebsite() throws Exception {
    }

    @Test
    public void uploadFileHandler() {
    }
}