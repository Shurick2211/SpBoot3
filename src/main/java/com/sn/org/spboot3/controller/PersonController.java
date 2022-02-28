package com.sn.org.spboot3.controller;

import com.sn.org.spboot3.bot.Bot;
import com.sn.org.spboot3.model.Person;
import com.sn.org.spboot3.model.Post;
import com.sn.org.spboot3.model.Role;
import com.sn.org.spboot3.repo.PersonRepo;
import com.sn.org.spboot3.repo.PostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Controller
@RequestMapping("/person")
public class PersonController {
    @Autowired
    PersonRepo personRepo;
    @Autowired
    PostRepo postRepo;
    @Autowired
    Bot bot;


    @GetMapping("/{id}")
    public String index(@PathVariable long id, Model model){
        Person person=personRepo.getById(id);
        model.addAttribute("person",person);
        return "/person/show";
    }

    @GetMapping("/add")
    public String add(Model model){
        model.addAttribute("person",new Person());
        return "/person/add";
    }
    @PostMapping("/add")
    public String addPerson(@ModelAttribute Person person){

        person.setRole(Role.USER);
        person.setActive(true);
        personRepo.save(person);
        return "redirect:/person/"+person.getId();
    }
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable long id, Model model){
        model.addAttribute("person",personRepo.getById(id));
        model.addAttribute("roles", Role.values());
        return "/person/edit";
    }
    @PostMapping("/edit/{id}")
    public String editPerson(@PathVariable long id,@ModelAttribute Person person,@ModelAttribute("isActive")String ch){
        person.setLogin(""+id);

       if(ch.equals("on")) person.setActive(true);
        personRepo.save(person);
        return "redirect:/enterAdmin";
    }
    @GetMapping("/delete/{id}")
    public String deletePerson(@PathVariable long id){

        personRepo.deleteById(id);
        return "redirect:/enterAdmin";
    }
    @GetMapping("/send")
    public String sendMsgGet(@ModelAttribute("postId") long postId,
                             @AuthenticationPrincipal Person person,
                             Model model
    )  {
        model.addAttribute("person",person);
        model.addAttribute("post", postRepo.getById(postId));

        return "/sendMessage";
    }

    @PostMapping("/send/{id}")
    public String sendMsg(@PathVariable long id,
                            @RequestParam String msg
    )  {

        bot.sendMessage(""+id,msg);

        return "redirect:/enterModer";
    }

    @GetMapping("/ban/{id}")
    public String ban(@PathVariable long id)  {

            Person person=personRepo.getById(id);
            person.setActive(false);
            personRepo.save(person);
        return "redirect:/enterModer";
    }

}
