package com.adv.adv.controller;

import com.adv.adv.model.User;
import com.adv.adv.repository.userRepository;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private userRepository userRepository;

  @GetMapping("/signup")
    public ModelAndView getsignupPage() {
        User newuser= new User();
        ModelAndView mav = new ModelAndView ("signup.html");
        mav.addObject("user",newuser);
        return mav;
    }
 @PostMapping("/signup")
public ModelAndView saveFruit(@Valid @ModelAttribute User user, BindingResult result) {
   
    ModelAndView mav = new ModelAndView("redirect:login");
    mav.addObject("user", user); // Optionally, you can pass the user object to the success page
    return mav;

}





    @GetMapping("/login")
    public ModelAndView getLoginPage() {

        
        User newuser= new User();
        ModelAndView mav = new ModelAndView ("login.html");
        mav.addObject(newuser);
        return mav;
    }

@PostMapping("/login")
public RedirectView login(@ModelAttribute("newuser") User loginUser) {
    Optional<User> user = userRepository.findByUsername(loginUser.getUsername());

    if (user.isPresent() && user.get().getPassword().equals(loginUser.getPassword())) {
        
        return new RedirectView("user/signup");
    } else {
       
        return new RedirectView("/login-error");
    }
}
  
}
