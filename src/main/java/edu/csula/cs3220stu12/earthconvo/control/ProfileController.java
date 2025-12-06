package edu.csula.cs3220stu12.earthconvo.control;

import edu.csula.cs3220stu12.earthconvo.model.User;
import edu.csula.cs3220stu12.earthconvo.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfileController {

    private final UserRepository userRepository;

    public ProfileController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/profile")
    public String profile(HttpSession session, Model model) {
        String userEmail = (String) session.getAttribute("userEmail");

        if (userEmail == null || userEmail.isEmpty()) {
            return "redirect:/login"; // not logged in
        }

        User user = userRepository.findById(userEmail).orElse(null);

        if (user == null) {
            session.invalidate(); // remove stale session
            return "redirect:/login";
        }

        model.addAttribute("email", user.getEmail());

        return "profile"; // maps to profile.jte
    }
}
