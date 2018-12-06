package uk.co.punishell.insideview.model.services;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import uk.co.punishell.insideview.model.services.messages.FileUploadMessage;

import java.io.File;

@Service
public class FileUploader {

    DBPopulatingManager dbPopulatingManager;
    private File file;

    public FileUploader(DBPopulatingManager dbPopulatingManager) {
        this.dbPopulatingManager = dbPopulatingManager;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    @PostMapping("/uploadFile")
    public String handleFileUpload(@ModelAttribute FileUploadMessage fileUploadMessage, Model model) {

        /*try {
            dbPopulatingManager.populateDB(file);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }*/

        model.addAttribute("uploadMessage", "Your upload was successful!");

        return "redirect:/upload_result";
    }


}
