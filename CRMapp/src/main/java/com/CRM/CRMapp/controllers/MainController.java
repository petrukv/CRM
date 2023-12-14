package com.CRM.CRMapp.controllers;

import com.CRM.CRMapp.model.Info;
import com.CRM.CRMapp.repo.InfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Optional;

@Controller
public class MainController {
    @Autowired
    private InfoRepository infoRepository;
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Main Page");
        Iterable<Info> info = infoRepository.findAll();
        model.addAttribute("info", info);
        return "home";
    }

    @GetMapping("/add_record")
    public String add(Model model) {
        return "add_person";
    }

    @PostMapping("/add_record")
    public String addPerson(@RequestParam String first_name, @RequestParam String second_name, @RequestParam String email, @RequestParam String phone, @RequestParam String address, @RequestParam String city, @RequestParam String state, Model model) {
        Info info = new Info(first_name, second_name, email, phone, address, city, state );
        infoRepository.save(info);
        return "redirect:/";
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable(value = "id") long id, Model model) {
        if(!infoRepository.existsById(id)) {
            return "redirect:/";
        }
        Optional<Info> post = infoRepository.findById(id);
        ArrayList<Info> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("info", res);
        return "detail";
    }

    @GetMapping("/about/{id}/edit")
    public String blogEdit (@PathVariable(value = "id") long id, Model model) {
        if(!infoRepository.existsById(id)) {
            return "redirect:/";
        }
        Optional<Info> post = infoRepository.findById(id);
        ArrayList<Info> res = new ArrayList<>();
        post.ifPresent(res::add);
        model.addAttribute("info", res);
        return "edit";
    }

    // В MainController
    @PostMapping("/about/{id}/edit")
    public String update(@ModelAttribute Info updatedInfo, Model model) {
        Info info = infoRepository.findById(updatedInfo.getId()).orElseThrow();
        info.setFirst_name(updatedInfo.getFirst_name());
        info.setSecond_name(updatedInfo.getSecond_name());
        info.setEmail(updatedInfo.getEmail());
        info.setPhone(updatedInfo.getPhone());
        info.setAddress(updatedInfo.getAddress());
        info.setCity(updatedInfo.getCity());
        info.setState(updatedInfo.getState());
        infoRepository.save(info);
        return "redirect:/";
    }



    @PostMapping("/about/{id}/delete")
    public String delete ( @PathVariable(value = "id") long id, Model model) {
        Info post = infoRepository.findById(id).orElseThrow();
        infoRepository.delete(post);
        return "redirect:/";
    }
}
