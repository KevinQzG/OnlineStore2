package com.ejemplo.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ContactController {

    @GetMapping("/contact")
    public String showContactForm(Model model) {
        model.addAttribute("title", "Contáctenos - Online Store");
        return "home/contact";
    }

    @PostMapping("/contact/submit")
    public String submitContactForm(
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("message") String message,
            Model model) {

        model.addAttribute("title", "Mensaje Enviado");
        model.addAttribute("confirmation", "Gracias " + name + ", hemos recibido tu mensaje.");
        return "home/contact";
    }
}
