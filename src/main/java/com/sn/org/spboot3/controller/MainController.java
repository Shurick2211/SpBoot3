package com.sn.org.spboot3.controller;

import com.sn.org.spboot3.model.Person;
import com.sn.org.spboot3.model.Post;
import com.sn.org.spboot3.model.Role;
import com.sn.org.spboot3.repo.PersonRepo;
import com.sn.org.spboot3.repo.PostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URI;
import java.net.http.HttpRequest;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
public class MainController {
    @Autowired
    PersonRepo personRepo;
    @Autowired
    PostRepo postRepo;

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

            List<Post> posts=  postRepo.findAllByAuthor(person);
            model.addAttribute("posts",posts);

       // System.out.println(person.getPosts().size());
        return "/enterUser";
    }
    @GetMapping("/enterModer")
    public String enterModer(

            @AuthenticationPrincipal Person person,
            Model model){
        model.addAttribute("person",person);
        List<Post> posts=postRepo.findAll();

        model.addAttribute("posts",posts);

        return "/enterModer";
    }
    @GetMapping("/enterAdmin")
    public String enterAdmin(Model model){
        model.addAttribute("persons",personRepo.findAll());
        return "/enterAdmin";
    }


}
