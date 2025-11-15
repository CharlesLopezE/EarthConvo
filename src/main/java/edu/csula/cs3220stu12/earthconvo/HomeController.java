package edu.csula.cs3220stu12.earthconvo;

import jakarta.servlet.http.HttpSession;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    private final ChatClient chatClient;
    private final SavedLessons savedLessons;

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

        String language = (String) session.getAttribute("language");
        if (language == null || language.isEmpty()) {
            language = "English";
            session.setAttribute("language", language);
        }

        // Retrieve chat history from session
        List<ChatMessage> history = (List<ChatMessage>) session.getAttribute("history");
        if (history == null) {
            history = new ArrayList<>();
            session.setAttribute("history", history);
        }

        model.addAttribute("username", email);
        model.addAttribute("question", null);
        model.addAttribute("answer", null);
        model.addAttribute("history", history);

        return "homepage";
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
        String summary;
        try {
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

            // Generate a short summary for history
            summary = chatClient
                    .prompt()
                    .user("""
                        Summarize the following response in 1 sentence, so it can be shown in a chat sidebar:
                        %s
                        """.formatted(reply))
                    .call()
                    .content();

            // Save lesson in your SavedLessons system
            savedLessons.savedLessons(email, reply);

        } catch (Exception e) {
            reply = "Sorry, I couldn't reach the AI service right now. Please try again later.";
            summary = reply;
        }

        // Store chat history in session
        List<ChatMessage> history = (List<ChatMessage>) session.getAttribute("history");
        if (history == null) {
            history = new ArrayList<>();
        }
        history.add(new ChatMessage(prompt, reply, summary));
        session.setAttribute("history", history);

        // Add data for JTE template
        model.addAttribute("username", email);
        model.addAttribute("question", prompt);
        model.addAttribute("answer", reply);
        model.addAttribute("history", history);

        return "homepage";
    }
}
