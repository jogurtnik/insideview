package uk.co.punishell.insideview.model.services;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;

@Service
public class FileUploader {

    private DBPopulatingManager dbPopulatingManager;
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

    @RequestMapping(value = "/uploadFile", params = {"save"})
    public String uploadFile( Model model) {

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

    @RequestMapping("uploadResult")
    public String uploadResult () {

        return "upload_result";
    }


}
