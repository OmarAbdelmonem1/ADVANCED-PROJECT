package com.adv.adv.controller;

import com.adv.adv.model.User;
import com.adv.adv.repository.userRepository;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;


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
public ModelAndView saveFruit( @ModelAttribute User user) {
   
  String hashedPassword=BCrypt.hashpw(user.getPassword(),BCrypt.gensalt(12));
  user.setPassword(hashedPassword);
    this.userRepository.save(user);

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
