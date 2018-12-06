package uk.co.punishell.insideview.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import uk.co.punishell.insideview.model.services.FileUploader;


@Controller
public class DatabaseManagementController {

    FileUploader fileUploader;

    public DatabaseManagementController(FileUploader fileUploader) {
        this.fileUploader = fileUploader;
    }

    @GetMapping(value={"/database_management", "/database_management.html"})
    public String getDatabaseManagementWebsite(Model model) {

        model.addAttribute("fileUploader", fileUploader);

        return "database_management";
    }

}
