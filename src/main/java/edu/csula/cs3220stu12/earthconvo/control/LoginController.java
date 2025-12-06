package edu.csula.cs3220stu12.earthconvo.control;

import edu.csula.cs3220stu12.earthconvo.model.User;
import edu.csula.cs3220stu12.earthconvo.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    @Autowired
    private UserRepository userRepo;

    @GetMapping("/login")
    public String loginForm(@RequestParam(value = "error", required = false) String error, Model model) {
        model.addAttribute("error", error);
        return "login";
    }

    @PostMapping("/login")
    public String loginSubmit(@RequestParam String email,
                              @RequestParam String password,
                              HttpSession session,
                              Model model) {

        User user = userRepo.findById(email).orElse(null);

        if (user == null || !user.getPassword().equals(password)) {
            model.addAttribute("error", "Invalid email or password!");
            return "login";
        }

        session.setAttribute("userEmail", email);
        session.setAttribute("user", user);

        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
