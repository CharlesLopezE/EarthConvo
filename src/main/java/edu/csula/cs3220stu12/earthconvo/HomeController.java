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

        model.addAttribute("username", email);
        model.addAttribute("question", null);
        model.addAttribute("answer", null);

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

        try {
            // English tutor + translations + pronunciation + bullets + short
            reply = chatClient
                    .prompt()
                    .user("""
                            You are an English tutor named EarthConvo.

                            Selected translation language: %s

                            Your reply rules:
                            - Always explain in simple English.
                            - Keep answers short.
                            - Use bullet points only.
                            - Do NOT use bold, italics, Markdown syntax, or numbering.
                            - Every line must start with "- " (dash + space).

                            Translation rule:
                            - If the user asks to translate, asks "how do I say", or clearly wants a translation,
                              your reply must include these bullets IN THIS EXACT ORDER:

                              - Translation (in %s): <translated text in %s>
                              - Pronunciation: <pronunciation written in English letters>
                              - Explanation: <simple explanation in English>
                              - Example: <1 short example sentence in English>

                            If the user is NOT asking for translation:
                            - Just answer using English bullet points.

                            Keep everything short and clear.
                            
                            User: %s
                            Question: %s
                            """.formatted(language, language, language, email, prompt))
                    .call()
                    .content();

            savedLessons.savedLessons(email, reply);

        } catch (Exception e) {
            reply = "Sorry, I couldn't reach the AI service right now. Please try again later.";
        }

        model.addAttribute("username", email);
        model.addAttribute("question", prompt);
        model.addAttribute("answer", reply);

        return "homepage";
    }
}
