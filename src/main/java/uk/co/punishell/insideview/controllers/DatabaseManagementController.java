package uk.co.punishell.insideview.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import uk.co.punishell.insideview.model.managers.DBPopulatingManager;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;


@Controller
public class DatabaseManagementController {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseManagementController.class);

    DBPopulatingManager dbPopulatingManager;

    @Autowired
    public DatabaseManagementController(DBPopulatingManager dbPopulatingManager) {
        this.dbPopulatingManager = dbPopulatingManager;
    }

    @GetMapping(value={"/databaseManagement"})
    public String getDatabaseManagementWebsite() {

        return "databaseManagement";
    }

    @PostMapping({"/uploadFile", "uploadFile"})
    public String uploadFileHandler(@RequestParam("file") MultipartFile file) {

        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();

                // Creating the directory to store file
                String rootPath = System.getProperty("catalina.home");
                File dir = new File(rootPath + File.separator + "tmpFiles");
                if (!dir.exists())
                    dir.mkdirs();

                // Create the file on server
                File serverFile = new File(dir.getAbsolutePath()
                        + File.separator + file.getOriginalFilename());
                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();

                logger.info("Server File Location="
                        + serverFile.getAbsolutePath());

                dbPopulatingManager.populateDB(serverFile);

                return "redirect:/databaseManagement";

            } catch (Exception e) {
                e.printStackTrace();
                return "redirect:/";
            }
        } else {
            return "You failed to upload because the file was empty.";
        }
    }
}
