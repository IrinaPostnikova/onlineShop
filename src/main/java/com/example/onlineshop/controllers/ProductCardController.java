package com.example.onlineshop.controllers;

import com.example.onlineshop.entity.Cart.ProductCart;
import com.example.onlineshop.entity.product.Product;
import com.example.onlineshop.entity.user.User;
import com.example.onlineshop.repository.CityRepository;
import com.example.onlineshop.repository.ProductRepository;
import com.example.onlineshop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/productCard")
public class ProductCardController {

    private final ProductRepository productRepository;

    @Autowired
    public ProductCardController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/{id}")
    public String showProductCard(Model model, @AuthenticationPrincipal User user, @PathVariable("id") Long id) {
        model.addAttribute("user", user);
        model.addAttribute("product", productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + id)));
        return "productCard";
    }

}
