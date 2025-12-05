package edu.csula.cs3220stu12.earthconvo.control;

import edu.csula.cs3220stu12.earthconvo.model.UserStore;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class SignupController {

    @Autowired
    private UserStore userStore;

    @GetMapping("/signup")
    public String signupPage() {
        return "signup";
    }

    @PostMapping("/signup")
    public String createUser(
            @RequestParam String email,
            @RequestParam String password,
            Model model,
            HttpSession session) {

        if (userStore.emailExists(email)) {
            model.addAttribute("error", "Email already exists.");
            return "signup";
        }

        userStore.addUser(email, password);
        session.setAttribute("userEmail", email);
        session.setAttribute("user", email);

        return "redirect:/";
    }
}
