package edu.csula.cs3220stu12.earthconvo.control;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfileController {

    @GetMapping("/profile")
    public String profile(HttpSession session, Model model) {
        String email = (String) session.getAttribute("userEmail");
        String username = (String) session.getAttribute("user");

        if (email == null || email.isEmpty()) {
            return "redirect:/login";
        }

        model.addAttribute("email", email);
        model.addAttribute("username", username);

        return "profile";
    }
}
