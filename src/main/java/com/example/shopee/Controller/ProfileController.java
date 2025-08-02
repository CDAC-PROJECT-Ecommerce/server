package com.example.shopee.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.ui.Model;

import com.example.shopee.Model.User;
import com.example.shopee.Service.ProfileService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ProfileController {
	
	private final ProfileService profileService;
	
	@GetMapping("profile/{id}")
	public ResponseEntity<?> fetchProfileById(@PathVariable long id) {
	    User profile = profileService.getProfileById(id);
	    if (profile != null) {
	        return ResponseEntity.ok(profile);
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	}
	
//	@GetMapping("edit/{id}")
//    public String showEditForm(@PathVariable Long id, Model model) {
//        model.addAttribute("profile", profileService.getProfileById(id));
//        return "profiles/form";
//    }
	
//	@GetMapping("/delete/{id}")
//    public String deleteProfile(@PathVariable Long id) {
//        profileService.deleteProfile(id);
//        return "redirect:/";
//    }
	
	
}
