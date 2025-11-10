package edu.csula.cs3220stu12.earthconvo;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam String email,
            @RequestParam String password,
            HttpSession session,
            Model model) {

        if ("alice@example.com".equals(email) && "ali123".equals(password)) {
            session.setAttribute("user", "Alice Johnson");
            session.setAttribute("userEmail", email);
            return "redirect:/";
        } else if ("bob@example.com".equals(email) && "abcd".equals(password)) {
            session.setAttribute("user", "Bob Smith");
            session.setAttribute("userEmail", email);
            return "redirect:/";
        } else {
            model.addAttribute("error", "Invalid email or password");
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
