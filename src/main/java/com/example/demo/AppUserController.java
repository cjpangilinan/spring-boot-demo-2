package com.example.demo;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AppUserController {

	private AppUserRepository repo;
	
	public AppUserController(AppUserRepository repo) {
		this.repo = repo;
	}
	
	@GetMapping("/users")
	public String listUsers(Model model) {
		model.addAttribute("users", repo.findAll());
		return "users-list";
	}
	
	@GetMapping("/users/{id}")
	public String viewUser(@PathVariable long id, Model model) {
		Optional<AppUser> oUser = repo.findById(id);
		AppUser user = oUser.orElseGet(() -> new AppUser());
		model.addAttribute("user", user);
		return "users-view";
	}
	
	@GetMapping("/users/search")
	public String searchUser(@RequestParam String name, Model model) {
		List<AppUser> users = repo.findByName(name);
		model.addAttribute("users", users);
		return "users-list";
	}
	
	@GetMapping("/users/add")
	public String addUserForm(Model model) {
		model.addAttribute("user", new AppUser());
		return "users-add";
	}
	
	@PostMapping("/users")
	public String saveUser(@Validated @ModelAttribute("user") AppUser user, Errors errors, final RedirectAttributes redirect) {
		if (errors.hasErrors()) {
			return "users-add";
		}
		repo.save(user);
		redirect.addFlashAttribute("success", "User is added successfully.");
		return "redirect:/users";
	}
}
