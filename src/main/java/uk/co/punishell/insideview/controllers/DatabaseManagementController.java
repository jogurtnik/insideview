package uk.co.punishell.insideview.controllers;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import uk.co.punishell.insideview.model.ResourceData.FileValidator;
import uk.co.punishell.insideview.model.database.entities.Race;
import uk.co.punishell.insideview.model.database.services.RaceService;
import uk.co.punishell.insideview.model.exceptions.FileUploadException;
import uk.co.punishell.insideview.model.managers.DBPopulatingManager;
import uk.co.punishell.insideview.view.commands.guiCommands.DatabaseStatusCheck;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

@Slf4j
@Controller
public class DatabaseManagementController {

    private RaceService raceService;
    private DBPopulatingManager dbPopulatingManager;
    private FileValidator validator;

    @Autowired
    public DatabaseManagementController(DBPopulatingManager dbPopulatingManager,
                                        FileValidator validator,
                                        RaceService raceService) {
        this.dbPopulatingManager = dbPopulatingManager;
        this.validator = validator;
        this.raceService = raceService;
    }

    @GetMapping(value={"databaseManagement"})
    public String getDatabaseManagementWebsite(Model model) {

        model.addAttribute("databaseStatusCheck", new DatabaseStatusCheck());

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
                throw new FileUploadException("File is not in compatible with MS Excel format.");
            }

        }

        log.info("FILE VALIDATION: " + validatedFiles.size() + " files valid.");

        dbPopulatingManager.populateDB(validatedFiles);

        return "redirect:/databaseManagement";
    }

    @GetMapping("refreshDatabaseStatusCheck")
    public String refreshDatabaseStatusCheck(Model model) {

        Set<LocalDate> existingDatesSet = new HashSet<>();

        Set<Race> races = raceService.getRaces();

        DatabaseStatusCheck databaseStatusCheck = new DatabaseStatusCheck();

        if (races.isEmpty()) {
            databaseStatusCheck.setSummaryMessage("Database is empty.");
            model.addAttribute("databaseStatusCheck", databaseStatusCheck);

            return "databaseManagement";
        } else {

            races.stream().forEach(race -> existingDatesSet.add(race.getLocalDate()));
            List<LocalDate> existingDates = new ArrayList<>();
            existingDates.addAll(existingDatesSet);
            Collections.sort(existingDates);

            List<LocalDate> missingDates = new ArrayList<>();

            for (LocalDate date = existingDates.get(0); date.isBefore(LocalDate.now().plusDays(1)); date = date.plusDays(1)) {

                if (!existingDates.contains(date)) {
                    missingDates.add(date);
                }
            }

            if (missingDates.isEmpty()) {
                databaseStatusCheck.setSummaryMessage("Data is complete, there are no missing dates.");
            } else {
                databaseStatusCheck.setSummaryMessage("Data is incomplete. See the missing dates in the table below.");
                databaseStatusCheck.setMissingDates(missingDates);
            }

            model.addAttribute("databaseStatusCheck", databaseStatusCheck);

            return "databaseManagement";
        }
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
                throw new FileUploadException("You failed to upload the data because the file was empty.");
            }

        }

        return savedFiles;
    }
}
