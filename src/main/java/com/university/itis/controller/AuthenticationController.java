package com.university.itis.controller;

import com.university.itis.model.Role;
import com.university.itis.model.User;
import com.university.itis.repository.UserRepository;
import com.university.itis.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

@Controller
public class AuthenticationController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(HttpServletRequest request, ModelMap modelMap) {

        modelMap.put("content", "registration");

        if(Utils.isAjax(request)) {
            return "/views/ftl/site/registration.ftl";
        } else {
            return "site/index";
        }
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String saveUser(User user, ModelMap modelMap) {
        User existingUser = userRepository.findByUsername(user.getUsername());

        if (existingUser != null) {
            modelMap.put("error", "Пользователь с таким именем существует");
            modelMap.put("content", "registration");
            return "site/index";
        }

        user.setRoles(Collections.singleton(Role.USER));
        user.setActive(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);
        return "redirect:/admin/login";
    }

    @RequestMapping(value = "/admin/login", method = RequestMethod.GET)
    @PreAuthorize("isAnonymous()")
    public String login() {
        return "admin/login";
    }
}
