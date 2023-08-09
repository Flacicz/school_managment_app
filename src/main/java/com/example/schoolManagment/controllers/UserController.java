package com.example.schoolManagment.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.schoolManagment.entity.User;
import com.example.schoolManagment.repo.UserRepository;
import com.example.schoolManagment.serivce.UserService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class UserController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
    
    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            request.getSession().invalidate();
        }
        return "redirect:/login";
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String createUser(User user) {
    	userService.createUser(user);
        return "redirect:/login";
    }

    @GetMapping("/students")
    public String userList(Model model) {
    	model.addAttribute("students", userService.userList());
        return "studentList";
    }
    
    @GetMapping("/students/{id}")
    public String userInfo(@PathVariable(value = "id") Long id, Model model) {
    	if(!userRepository.existsById(id)) {
    		return "redirect:/students";
    	}
    	
    	model.addAttribute("student", userService.findUserById(id));
    	
        return "studentInfo";
    }
    
    @GetMapping("/add_student")
    public String addUserPage() {
        return "addStudent";
    }
    
    @PostMapping("/add_student")
    public String addUser(User user) {
    	userService.createUser(user);
        return "addStudent";
    }
    
    @GetMapping("/edit_student/{id}")
    public String editStudentPage(@PathVariable(value = "id") Long id,Model model) {
    	List<User> result = userService.findUserById(id);
    	
    	if(result == null) {
    		return "redirect:/students";
    	}
    	
    	model.addAttribute("student", result);
    	
        return "studentEdit";
    }
    
    @PostMapping("/edit_student/{id}")
    public String editStudent(User user) {
    	userService.editUser(user);
        return "redirect:/students";
    }
    
    @GetMapping("/delete_user/{id}")
    public String deleteUser(@PathVariable(value = "id") Long id) {
    	userService.deleteUser(id);
        return "redirect:/students";
    }
}
