package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.data.Credential;
import com.udacity.jwdnd.course1.cloudstorage.data.File;
import com.udacity.jwdnd.course1.cloudstorage.data.Note;
import com.udacity.jwdnd.course1.cloudstorage.data.User;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@Controller
@RequestMapping("/home")
public class HomeController {
    private UserService userService;
    private FileService fileService;
    private NoteService noteService;
    private CredentialService credentialService;
    private EncryptionService encryptionService;

    public HomeController(UserService userService, FileService fileService, NoteService noteService, CredentialService credentialService, EncryptionService encryptionService) {
        this.userService = userService;
        this.fileService = fileService;
        this.noteService = noteService;
        this.credentialService = credentialService;
        this.encryptionService = encryptionService;
    }

    private void refreshPageContents(Integer userId, Model model, String tab) {
        model.addAttribute("userFiles", fileService.getUserFiles(userId));
        model.addAttribute("userNotes", noteService.getUserNotes(userId));
        model.addAttribute("userCredentials", credentialService.getUserCredentials(userId));
        model.addAttribute("activeTab", tab);
    }

    @GetMapping()
    public String requestHome(@ModelAttribute("credentialForm") Credential credential, @ModelAttribute("noteForm") Note note, Principal principal, Model model) {
        User user = userService.getUser(principal.getName());
        refreshPageContents(user.getUserid(), model, "nav-files-tab");

        return "home";
    }

    @PostMapping("/upload")
    public String postUpload (@ModelAttribute("credentialForm") Credential credential, @ModelAttribute("noteForm") Note note,  @RequestParam("fileUpload") MultipartFile fileUpload, Principal principal, Model model) {
        model.addAttribute("success", false);
        if (fileUpload.isEmpty()) {
            model.addAttribute("message", "No selected file to upload");
            return "home";
        }
        try {
            File file = new File();
            User user = userService.getUser(principal.getName());
            file.setFilename(fileUpload.getOriginalFilename());
            file.setContenttype(fileUpload.getContentType());
            file.setFilesize(String.valueOf(fileUpload.getSize()));
            file.setUserid(user.getUserid());
            file.setFiledata(fileUpload.getBytes());

            fileService.insertFile(file);

            refreshPageContents(user.getUserid(), model, "nav-files-tab");

            model.addAttribute("success",true);
            model.addAttribute("message","New File added successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message","There has been an error!");
        }
        return "home";
    }

    @GetMapping("/viewfile/{fileId}")
    public ResponseEntity<Resource> getFile(@PathVariable Integer fileId) {
        File file = fileService.getFile(fileId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getContenttype()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""
                + file.getFilename() + "\"").body(new ByteArrayResource(file.getFiledata()));
    }

    @GetMapping("/deletefile/{fileId}")
    public String deleteFile(@ModelAttribute("credentialForm") Credential credential, @ModelAttribute("noteForm") Note note,  @PathVariable Integer fileId, Principal principal, Model model) {
        if (fileService.deleteFile(fileId) > 0) {
            User user = userService.getUser(principal.getName());
            refreshPageContents(user.getUserid(), model, "nav-files-tab");
        }
        return "home";
    }

    @GetMapping("/deletenote/{noteId}")
    public String deleteNote(@ModelAttribute("credentialForm") Credential credential, @ModelAttribute("noteForm") Note note, @PathVariable Integer noteId, Principal principal, Model model) {
        if (noteService.deleteNote(noteId) > 0) {
            User user = userService.getUser(principal.getName());
            refreshPageContents(user.getUserid(), model, "nav-notes-tab");
        }
        return "home";
    }

    @PostMapping("/insertupdatenote")
    public String insertUpdateNote(@ModelAttribute("credentialForm") Credential credential, @ModelAttribute("noteForm") Note note, Principal principal, Model model) {
        User user = userService.getUser(principal.getName());
        note.setUserid(user.getUserid());
        if (noteService.getNote(note.getNoteid()) != null) {
            noteService.updateNote(note);
        } else {
            noteService.insertNote(note);
        }
        refreshPageContents(user.getUserid(), model, "nav-notes-tab");
        return "home";
    }

    @GetMapping("/deletecredential/{credentialId}")
    public String deleteCredential(@ModelAttribute("credentialForm") Credential credential, @ModelAttribute("noteForm") Note note, @PathVariable Integer credentialId, Principal principal, Model model) {
        if (credentialService.deleteCredential(credentialId) > 0) {
            User user = userService.getUser(principal.getName());
            refreshPageContents(user.getUserid(), model, "nav-credentials-tab");
        }
        return "home";
    }

    @PostMapping("/insertupdatecredential")
    public String insertUpdateCredential(@ModelAttribute("credentialForm") Credential credential, @ModelAttribute("noteForm") Note note, Principal principal, Model model) {
        User user = userService.getUser(principal.getName());
        credential.setUserid(user.getUserid());

        if (credential.getCredentialid() != null) {
            credentialService.updateCredential(credential);
        } else {
            credentialService.insertCredential(credential);
        }
        refreshPageContents(user.getUserid(), model, "nav-credentials-tab");
        return "home";
    }

}
