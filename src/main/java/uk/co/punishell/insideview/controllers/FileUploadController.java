package uk.co.punishell.insideview.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import uk.co.punishell.insideview.model.services.FileUploader;


@Controller
public class FileUploadController {

    FileUploader fileUploader;

    public FileUploadController(FileUploader fileUploader) {
        this.fileUploader = fileUploader;
    }

    @GetMapping("/database_management")
    public String getDatabaseManagementWebsite(Model model) {

        model.addAttribute("fileUploader", fileUploader);

        return "database_management";
    }

}
