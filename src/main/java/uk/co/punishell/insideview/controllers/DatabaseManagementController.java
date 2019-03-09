package uk.co.punishell.insideview.controllers;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import uk.co.punishell.insideview.model.ResourceData.FileValidator;
import uk.co.punishell.insideview.model.exceptions.FileUploadException;
import uk.co.punishell.insideview.model.managers.DBPopulatingManager;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Controller
public class DatabaseManagementController {

    DBPopulatingManager dbPopulatingManager;
    private FileValidator validator;

    @Autowired
    public DatabaseManagementController(DBPopulatingManager dbPopulatingManager, FileValidator validator) {
        this.dbPopulatingManager = dbPopulatingManager;
        this.validator = validator;
    }

    @GetMapping(value={"databaseManagement"})
    public String getDatabaseManagementWebsite() {

        return "databaseManagement";
    }

    @PostMapping("uploadFile")
    public String uploadFileHandler(@RequestParam("files") MultipartFile[] files) throws FileUploadException {

        Set<File> savedFiles = this.saveFilesTemp(files);

        Set<File> validatedFiles = new HashSet<>();

        for (File f : savedFiles) {
            try {
                if (validator.isValidFile(f)) {
                    validatedFiles.add(f);
                }
            } catch (IOException e) {
                throw new FileUploadException("File is not in compatible MS Excel format.");
            }

        }

        log.info("FILE VALIDATION: " + validatedFiles.size() + " files valid.");

        dbPopulatingManager.populateDB(validatedFiles);

        return "redirect:/databaseManagement";
    }

    private Set<File> saveFilesTemp(@NotNull MultipartFile[] files) {

        Set<File> savedFiles = new HashSet<>();

        log.info("FILE UPLOAD: " + files.length + " files");

        for (MultipartFile file : files) {

            if (!file.isEmpty()) {
                try {
                    byte[] bytes = file.getBytes();

                    // Creating the directory to store file
                    String rootPath = System.getProperty("catalina.home");
                    File dir = new File(rootPath + File.separator + "tmpFiles");
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }

                    // Create the file on server
                    File serverFile = new File(dir.getAbsolutePath()
                            + File.separator + file.getOriginalFilename());
                    BufferedOutputStream stream = new BufferedOutputStream(
                            new FileOutputStream(serverFile));
                    stream.write(bytes);
                    stream.close();
                    savedFiles.add(serverFile);

                    log.info("Server File Location="
                            + serverFile.getAbsolutePath());

                } catch (Exception e) {
                    throw new FileUploadException("File is empty or the path to the file is invalid.");
                }
            } else {
                throw new FileUploadException("You failed to upload because the file was empty.");
            }

        }

        return savedFiles;
    }
}
