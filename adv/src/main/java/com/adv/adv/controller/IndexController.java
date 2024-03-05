package com.adv.adv.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.adv.adv.model.Product;
import com.adv.adv.repository.ProductRepository;


@Controller
public class IndexController {
    // @GetMapping("/home")
    // public String home() {
    //     return "index";
    // }
    @Autowired
    private ProductRepository productRepository;
    
    @GetMapping("/home")
    public ModelAndView getAll() {
        ModelAndView mav =new ModelAndView("index.html");
        List<Product>products=this.productRepository.findAll();
        mav.addObject("products", products);
        return mav;
    }
    
}
