package com.ejemplo.demo.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
   @GetMapping("/")
   public String index(Model model) {
      model.addAttribute("title", "Welcome to Spring Boot");
      model.addAttribute("subtitle", "A Spring Boot EAFIT App");

      return "home/index";
   }

   @GetMapping("/about")
public String about(Model model) {
    model.addAttribute("title", "Sobre Nosotros - Online Store");
    model.addAttribute("subtitle", "Sobre Nosotros");
    model.addAttribute("description", "Somos una tienda en línea dedicada a ofrecer los mejores productos de tecnología, calidad y precio.");
    model.addAttribute("author", "Desarrollado por: Kevin Quiroz");

    model.addAttribute("mission", "Brindar a nuestros clientes productos de alta calidad a precios competitivos, con un excelente servicio al cliente.");
    model.addAttribute("vision", "Ser la tienda en línea número uno en tecnología y electrónica, destacándonos por nuestra innovación y atención al cliente.");
    model.addAttribute("values", List.of(
"Compromiso con la calidad",
        "Atención al cliente excepcional",
        "Innovación y mejora continua",
        "Responsabilidad y transparencia"
    ));

    return "home/about";
}



}
