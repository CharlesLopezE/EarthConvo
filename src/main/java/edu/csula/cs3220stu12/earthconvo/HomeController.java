package edu.csula.cs3220stu12.earthconvo;

import jakarta.servlet.http.HttpSession;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    private final ChatClient chatClient;
    private final SavedLessons savedLessons;

    // Inject both ChatClient.Builder and SavedJokesStore
    public HomeController(ChatClient.Builder chatClientBuilder, SavedLessons savedLessons) {
        this.chatClient = chatClientBuilder.build();
        this.savedLessons = savedLessons;
    }

    // Home page
    @GetMapping("/")
    public String index(HttpSession session, Model model) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
        String username = (String) session.getAttribute("user");
        model.addAttribute("username", username);
        return "homepage";
    }

    // Generate joke with chat history
    @PostMapping("/joke")
    public String generateJoke(@RequestParam("topic") String topic, HttpSession session, Model model) {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }
        String username = (String) session.getAttribute("user");

        // --- Chat history in session ---
        @SuppressWarnings("unchecked")
        List<String> history = (List<String>) session.getAttribute("history");
        if (history == null) {
            history = new java.util.ArrayList<>();
        }

        StringBuilder promptBuilder = new StringBuilder();
        if (!history.isEmpty()) {
            promptBuilder.append("Chat history:\n");
            for (String entry : history) {
                promptBuilder.append(entry).append("\n");
            }
        }
        promptBuilder.append("Now tell me a new joke about ").append(topic)
                .append(" that hasn’t been told before.");

        String aiJoke = chatClient.prompt(promptBuilder.toString()).call().content();

        // Save this round to chat history
        history.add("User requested joke about: " + topic);
        history.add("AI: " + aiJoke);
        session.setAttribute("history", history);
        // --- End chat history ---

        model.addAttribute("joke", aiJoke);
        model.addAttribute("topic", topic);
        model.addAttribute("username", username);
        return "joke";
    }

    // Save a joke permanently
    @PostMapping("/saved-lessons")
    public String saveJoke(@RequestParam String joke, HttpSession session) {
        String username = (String) session.getAttribute("user");
        if (username == null) return "redirect:/login";

        savedLessons.savedLessons(username, joke);
        return "redirect:/saved-lessons";
    }

    @GetMapping("/saved-lessons")
    public String viewSavedJokes(HttpSession session, Model model) {
        String username = (String) session.getAttribute("user");
        if (username == null) return "redirect:/login";

        List<String> jokes = savedLessons.getLessonsForUser(username);
        model.addAttribute("username", username);
        model.addAttribute("jokes", jokes);
        return "saved-lessons";
    }

    @GetMapping("/chat-history")
    public String viewChatHistory(HttpSession session, Model model) {
        String username = (String) session.getAttribute("user");
        if (username == null) return "redirect:/login";

        @SuppressWarnings("unchecked")
        List<String> history = (List<String>) session.getAttribute("history");
        if (history == null) history = new ArrayList<>();

        model.addAttribute("username", username);
        model.addAttribute("history", history);
        return "chat-history";
    }
}
