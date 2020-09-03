package br.pucrs.ages.townsq.controller;

import br.pucrs.ages.townsq.model.User;
import br.pucrs.ages.townsq.service.UserService;
import br.pucrs.ages.townsq.utils.Slugify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UserController {

    private final UserService service;

    @Autowired
    public UserController(UserService service){
        this.service = service;
    }

    @GetMapping("/users/signup")
    public String getUserSignupPage(){
        return "signup";
    }

    @PostMapping("/users/signup")
    public String postUsersSignup(@ModelAttribute User user){
        service.save(user);
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String getAllUsers(Model model){
        model.addAttribute("users", service.findAll());
        return "users";
    }

    @GetMapping(value = {"/user/{id}", "user/{id}/{slug}"})
    public String getUserById(HttpServletRequest request,
                              @PathVariable long id,
                              @PathVariable(required = false) String slug,
                              Model model){
        User user = service.findById(id).orElse(null);
        if(user != null){
            String userSlug = Slugify.toSlug(user.getName());
            if(slug == null || !slug.equals(userSlug)){
                request.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.MOVED_PERMANENTLY);
                return "redirect:/user/" + id + "/" + userSlug;
            }
            model.addAttribute("user", user);
            return "user";
        }
        return "user";
    }

}