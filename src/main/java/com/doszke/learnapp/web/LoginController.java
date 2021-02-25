package com.doszke.learnapp.web;

import com.doszke.learnapp.data.dao.CardCategoryDAO;
import com.doszke.learnapp.data.dao.CardDAO;
import com.doszke.learnapp.data.dao.SubjectDAO;
import com.doszke.learnapp.data.dao.UserDAO;
import com.doszke.learnapp.repositories.CardCategoryRepository;
import com.doszke.learnapp.repositories.CardRepository;
import com.doszke.learnapp.repositories.SubjectRepository;
import com.doszke.learnapp.repositories.UserRepository;
import com.doszke.learnapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.web.servlet.oauth2.client.OAuth2ClientSecurityMarker;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

import javax.smartcardio.Card;
import javax.validation.Valid;
import java.util.List;

@Controller
public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping(value = {"/", "/login"})
    public ModelAndView login(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @GetMapping(value = "/registration")
    public ModelAndView registration(){
        ModelAndView modelAndView = new ModelAndView();
        UserDAO userDAO = new UserDAO();
        modelAndView.addObject("userDAO", userDAO);
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @PostMapping(value = "/registration")
    public ModelAndView createUser(@Valid UserDAO userDAO, final BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        System.out.println(userDAO);
        UserDAO userExists = userService.findUserByUserName(userDAO.getUserName());
        if (userExists != null) {
            bindingResult.rejectValue("userName", "error.user", "There is already a user with the user name provided");
        }
        System.out.println(bindingResult.hasErrors());
        bindingResult.getAllErrors().forEach(System.out::println);
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("registration");
        } else {
            userService.saveUser(userDAO);
            modelAndView.addObject("successMessage", "User has been registered successfully");
            modelAndView.addObject("userDAO", new UserDAO());
            modelAndView.setViewName("registration");
        }
        return modelAndView;
    }

    @GetMapping(value = "/home")
    public ModelAndView adminHome(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDAO user = userService.findUserByUserName(auth.getName());
        modelAndView.addObject("userName", "Welcome " + user.getUserName() + "/" + user.getName() + " (" + user.getEmail() + ")");
        modelAndView.addObject("adminMessage","Content Available Only for Users with Admin Role");
        modelAndView.setViewName("home");
        return modelAndView;
    }


}
