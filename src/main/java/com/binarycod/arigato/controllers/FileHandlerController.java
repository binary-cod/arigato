package com.binarycod.arigato.controllers;

import com.binarycod.arigato.services.FileStorageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/files")
public class FileHandlerController {

    @Autowired
    FileStorageServiceImpl fileStorageService;

    @GetMapping
    public String showFilesAndUpload(Model model){

        model.addAttribute("files", fileStorageService.loadAll());

        return "file_list_upload";
    }


    @PostMapping
    public String uploadFiles(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes, Model model){
      //  System.out.println("content type "+file.getContentType());
        if (!file.getContentType().contains("image")){
            redirectAttributes.addAttribute("message", "I accept only Images.");
            return "redirect:/admin/files";
        }
        fileStorageService.store(file);
        redirectAttributes.addAttribute("message", "Uploaded successfully");
        return "redirect:/admin/files";
    }
}
