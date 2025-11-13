package edu.csula.cs3220stu12.earthconvo;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LanguageController {

    @GetMapping("/language")
    public String showLanguage(HttpSession session, Model model) {
        String email = (String) session.getAttribute("userEmail");
        if (email == null || email.isEmpty()) {
            return "redirect:/login";
        }

        String current = (String) session.getAttribute("language");
        if (current == null || current.isEmpty()) {
            current = "English";
        }

        model.addAttribute("currentLanguage", current);
        return "language";   // language.jte
    }

    @PostMapping("/language")
    public String setLanguage(
            @RequestParam("language") String language,
            HttpSession session
    ) {
        String email = (String) session.getAttribute("userEmail");
        if (email == null || email.isEmpty()) {
            return "redirect:/login";
        }

        session.setAttribute("language", language);
        return "redirect:/";
    }
}

