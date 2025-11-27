package edu.csula.cs3220stu12.earthconvo;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ThemeController {

    @PostMapping("/theme")
    public String setTheme(@RequestParam("theme") String theme,
                           HttpSession session) {

        String email = (String) session.getAttribute("userEmail");
        if (email == null || email.isEmpty()) {
            return "redirect:/login";
        }

        session.setAttribute("theme", theme);
        return "redirect:/";
    }
}
