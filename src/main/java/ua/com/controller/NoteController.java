package ua.com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ua.com.domain.Note;
import ua.com.service.NoteService;

import java.security.Principal;

@Controller
public class NoteController {


    @Autowired
    private NoteService noteService;

    @GetMapping("/notes")
    public String main(Model model, Principal principal){
        model.addAttribute("note", noteService.findTodaysNoteForCurrentUser(principal));
        return "notes";
    }

    @PostMapping("/notes")
    public String add(@ModelAttribute Note note, Principal principal){
        noteService.addTodaysNote(note, principal);
        return "redirect:/notes";
    }
}
