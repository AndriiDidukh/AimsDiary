package ua.com.controller;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.com.domain.Note;
import ua.com.repos.NoteRepo;
import ua.com.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.Date;

@Controller
public class MainController {

    @Autowired
    private UserRepo userRepo;


    @GetMapping("/")
    public String greeting(Model model, Principal principal) {
        model.addAttribute("user", userRepo.findByUsername(principal.getName()));
        return "index";
    }




}
