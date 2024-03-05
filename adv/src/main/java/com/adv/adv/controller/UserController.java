package com.adv.adv.controller;

import com.adv.adv.model.User;
import com.adv.adv.repository.userRepository;

import jakarta.servlet.http.HttpSession;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;


import java.util.Optional;

@RestController
@RequestMapping("/")
public class UserController {

    @Autowired
    private userRepository userRepository;

@GetMapping("/signup")
    public  ModelAndView showsignuppage (){
        User newuser= new User();
        ModelAndView mav = new ModelAndView("signup.html");
        mav.addObject("user",newuser);
        return mav;
    }
@PostMapping("/signup")
public RedirectView saveUser(@ModelAttribute("user") User user, Model model) {
    // Check if the email already exists in the database
    if (userRepository.existsByEmail(user.getEmail())) {
        model.addAttribute("error", "Email already registered");
        return new RedirectView("/signup-error"); // Redirect to a signup error page or handle as needed
    }

    // Hash the password before saving
    String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(12));
    user.setPassword(hashedPassword);

    // Save the user to the database
    userRepository.save(user);

    // Redirect to the login page after successful signup
    return new RedirectView("/login");
}


    @GetMapping("/login")
    public ModelAndView getLoginPage() {
        User newuser= new User();
        ModelAndView mv = new ModelAndView("login.html");
        mv.addObject("user",newuser);
        return mv;
    }
    


    @PostMapping("/login")
public RedirectView login(@ModelAttribute("user") User loginUser, Model model, HttpSession session) {
    Optional<User> userOptional = userRepository.findByEmail(loginUser.getEmail());

    if (userOptional.isPresent()) {
        User user = userOptional.get();

        if (BCrypt.checkpw(loginUser.getPassword(), user.getPassword())) {
            // Save user ID in the session
            session.setAttribute("userId", user.getId());

            return new RedirectView("/home");
        }
    }

    // If login fails, redirect to login page with an error message
    model.addAttribute("error", "Invalid email or password");
    return new RedirectView("/login-error");
}
}
    
