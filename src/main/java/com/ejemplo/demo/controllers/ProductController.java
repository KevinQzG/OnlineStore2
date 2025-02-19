package com.ejemplo.demo.controllers;

import com.ejemplo.demo.forms.ProductForm;
import com.ejemplo.demo.models.Comment;
import com.ejemplo.demo.models.Product;
import com.ejemplo.demo.repositories.CommentRepository;
import com.ejemplo.demo.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

     @Autowired
    private CommentRepository commentRepository;

    // ✅ URL de imagen predeterminada
    private static final String DEFAULT_IMAGE_URL = "https://www.fedex.com/content/dam/fedex/us-united-states/shipping/images/2022/FedEx-boxes.jpg";

    // ✅ Productos estáticos (hardcoded)
    private static final List<Map<String, String>> staticProducts = new ArrayList<>(List.of(
            Map.of("id", "1", "name", "TV", "description", "Best TV", "price", "500",
                    "image", "https://www.apple.com/newsroom/images/2023/12/redesigned-apple-tv-app-simplifies-the-viewing-experience/tile/Apple-TV-app-home-screen-lp.jpg.og.jpg?202501141956"),
            Map.of("id", "2", "name", "iPhone", "description", "Best iPhone", "price", "999",
                    "image", "https://www.telstra.com.au/content/dam/tcom/devices/mobile/mhdwhst-16pm/deserttitanium/landscape-apple-iphone16promax-deserttitianium-02-900x1200.jpg"),
            Map.of("id", "3", "name", "Chromecast", "description", "Best Chromecast", "price", "35",
                    "image", "https://s1.elespanol.com/2017/06/27/actualidad/actualidad_226990894_129938048_1200x630.jpg"),
            Map.of("id", "4", "name", "Glasses", "description", "Best Glasses", "price", "150",
                    "image", "https://www.apple.com/newsroom/images/media/introducing-apple-vision-pro/Apple-WWDC23-Vision-Pro-glass-230605_big.jpg.large.jpg")
    ));

    // ✅ Mostrar lista combinada de productos
    @GetMapping
    public String index(Model model) {
        List<Product> dbProducts = productRepository.findAll();

        model.addAttribute("title", "Products - Online Store");
        model.addAttribute("subtitle", "List of products");
        model.addAttribute("products", dbProducts);      // Productos de la base de datos
        model.addAttribute("staticProducts", staticProducts);  // Productos estáticos

        return "product/index";
    }

    // ✅ Mostrar formulario para crear producto
    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("title", "Create Product");
        model.addAttribute("productForm", new ProductForm());
        return "product/create";
    }

    @PostMapping("/save")
public String saveProduct(@ModelAttribute ProductForm productForm, RedirectAttributes redirectAttributes) {
    if (productForm.getPrice() == null || productForm.getPrice() <= 0) {
        redirectAttributes.addFlashAttribute("errorMessage", "El precio debe ser mayor a cero.");
        return "redirect:/products/create";
    }

    // ✅ Se asigna imagen predeterminada y descripción
    Product newProduct = new Product(
            productForm.getName(),
            productForm.getPrice(),
            DEFAULT_IMAGE_URL,
            ""  // ✅ Descripción por defecto
    );

    productRepository.save(newProduct);

    redirectAttributes.addFlashAttribute("successMessage", "¡Producto creado exitosamente!");
    return "redirect:/products";
}

@GetMapping("/{id}")
public String show(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
    Product product = productRepository.findById(id).orElse(null);

    if (product != null) {
        model.addAttribute("title", product.getName() + " - Online Store");
        model.addAttribute("subtitle", product.getName() + " - Product Information");
        model.addAttribute("product", product);
        model.addAttribute("comments", product.getComments());
        return "product/show";
    }

    redirectAttributes.addFlashAttribute("errorMessage", "Producto no encontrado.");
    return "redirect:/products";
}

@PostMapping("/{id}/comments")
public String addComment(@PathVariable Long id, @RequestParam("description") String description, RedirectAttributes redirectAttributes) {
    Product product = productRepository.findById(id).orElse(null);

    if (product == null) {
        redirectAttributes.addFlashAttribute("errorMessage", "Producto no encontrado.");
        return "redirect:/products";
    }

    if (description != null && !description.trim().isEmpty()) {
        Comment newComment = new Comment(description, product);
        commentRepository.save(newComment);
        redirectAttributes.addFlashAttribute("successMessage", "¡Comentario agregado exitosamente!");
    } else {
        redirectAttributes.addFlashAttribute("errorMessage", "El comentario no puede estar vacío.");
    }

    return "redirect:/products/" + id;
}
}