package edu.csula.cs3220stu12.earthconvo;

import jakarta.servlet.http.HttpSession;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    private final ChatClient chatClient;
    private final SavedLessons savedLessons;

    // Spring will auto-inject ChatClient.Builder from spring-ai starter
    public HomeController(ChatClient.Builder chatClientBuilder, SavedLessons savedLessons) {
        this.chatClient = chatClientBuilder.build();
        this.savedLessons = savedLessons;
    }

    @GetMapping("/")
    public String home(HttpSession session, Model model) {
        String email = (String) session.getAttribute("userEmail");
        if (email == null || email.isEmpty()) {
            return "redirect:/login";
        }

        // default language if none chosen yet
        String language = (String) session.getAttribute("language");
        if (language == null || language.isEmpty()) {
            language = "English";
            session.setAttribute("language", language);
        }

        model.addAttribute("username", email);
        model.addAttribute("question", null);
        model.addAttribute("answer", null);

        return "homepage"; // homepage.jte
    }

    @PostMapping("/ask")
    public String ask(
            @RequestParam("prompt") String prompt,
            HttpSession session,
            Model model
    ) {
        String email = (String) session.getAttribute("userEmail");
        if (email == null || email.isEmpty()) {
            return "redirect:/login";
        }

        String language = (String) session.getAttribute("language");
        if (language == null || language.isEmpty()) {
            language = "English";
        }

        String reply;

        try {
            // Try to call the AI model
            reply = chatClient
                    .prompt()
                    .user("""
                            You are a language tutor called EarthConvo.
                            The user selected this language for explanations and examples: %s.
                            Answer primarily in that language, but you can include English as needed to help learning.
                            User email: %s
                            User message: %s
                            """.formatted(language, email, prompt))
                    .call()
                    .content();

            // Only save if the AI call succeeded
            savedLessons.savedLessons(email, reply);

        } catch (Exception e) {
            // If the AI call fails (like HTTP 401 User not found),
            // we stay on the homepage and show a friendly error message.
            reply = "Sorry, I couldn't reach the AI service right now. "
                    + "Please try again later.";
        }

        model.addAttribute("username", email);
        model.addAttribute("question", prompt);
        model.addAttribute("answer", reply);

        return "homepage";
    }
}

