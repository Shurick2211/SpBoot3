package com.sn.org.spboot3.controller;

import com.sn.org.spboot3.model.Person;
import com.sn.org.spboot3.model.Role;
import com.sn.org.spboot3.repo.PersonRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainController {
    @Autowired
    PersonRepo personRepo;

    @GetMapping("/")
    public String index(
            @AuthenticationPrincipal Person person,
            Model model){
            if(person!=null) {
                if (person.getRole() == Role.ADMIN)
                    model.addAttribute("isAdmin", true);
                if (person.getRole() == Role.MODER)
                    model.addAttribute("isModer", true);
            }
            model.addAttribute("person",person);
        return "/main";
    }

    @GetMapping("/enterUser")
    public String enterUser(
            @AuthenticationPrincipal Person person,
            Model model){
            model.addAttribute("person",person);
        return "/enterUser";
    }
    @GetMapping("/enterModer")
    public String enterModer(
            @AuthenticationPrincipal Person person,
            Model model){
        model.addAttribute("person",person);
        return "/enterModer";
    }
    @GetMapping("/enterAdmin")
    public String enterAdmin(Model model){
        model.addAttribute("persons",personRepo.findAll());
        return "/enterAdmin";
    }
}
