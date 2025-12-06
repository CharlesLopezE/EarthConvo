package edu.csula.cs3220stu12.earthconvo.control;

import edu.csula.cs3220stu12.earthconvo.model.User;
import edu.csula.cs3220stu12.earthconvo.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class SignupController {

    @Autowired
    private UserRepository userRepo;

    @GetMapping("/signup")
    public String signupForm(@RequestParam(value = "error", required = false) String error, Model model) {
        model.addAttribute("error", error);
        return "signup";
    }

    @PostMapping("/signup")
    public String signupSubmit(@RequestParam String email,
                               @RequestParam String password,
                               HttpSession session,
                               Model model) {

        if (userRepo.existsById(email)) {
            model.addAttribute("error", "Email already exists!");
            return "signup";
        }

        // TODO: For production, store hashed password instead of plain text
        User user = new User(email, password);
        userRepo.save(user);

        session.setAttribute("user", email);
        session.setAttribute("user", user);

        return "redirect:/";
    }
}
