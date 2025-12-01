package edu.csula.cs3220stu12.earthconvo;

import jakarta.servlet.http.HttpSession;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    private final ChatClient chatClient;
    private final SavedLessons savedLessons;
    private final SavedSentences savedSentences;
    private final SavedVocab savedVocab;

    public HomeController(ChatClient.Builder chatClientBuilder, SavedLessons savedLessons,
                          SavedSentences savedSentences, SavedVocab savedVocab) {
        this.chatClient = chatClientBuilder.build();
        this.savedLessons = savedLessons;
        this.savedSentences = savedSentences;
        this.savedVocab = savedVocab;
    }

    // ------------------ HOME ------------------
    @GetMapping("/")
    public String home(HttpSession session, Model model) {
        String email = String.valueOf(session.getAttribute("userEmail"));
        if (email == null || email.isEmpty()) {
            return "redirect:/login";
        }

        String language = (String) session.getAttribute("language");
        if (language == null || language.isEmpty()) {
            language = "English";
            session.setAttribute("language", language);
        }

        // ---------- THEME DEFAULT ----------
        String theme = (String) session.getAttribute("theme");
        if (theme == null) theme = "default";

        model.addAttribute("theme", theme);


        // 🔹 history = all saved lessons (AI replies) for this user
        List<ChatMessage> history = (List<ChatMessage>) session.getAttribute("history");
        if (history == null) {
            history = new ArrayList<>();
            session.setAttribute("history", history);
        }

        model.addAttribute("userEmail", email);
        model.addAttribute("question", null);
        model.addAttribute("answer", null);
        model.addAttribute("history", history);
        model.addAttribute("theme", theme);

        return "homepage";
    }

    // ------------------ ASK ------------------
    @PostMapping("/ask")
    public String ask(@RequestParam("prompt") String prompt,
                      HttpSession session,
                      RedirectAttributes redirectAttributes) {

        String email = String.valueOf(session.getAttribute("userEmail"));
        if (email == null || email.isEmpty()) {
            return "redirect:/login";
        }

        String language = (String) session.getAttribute("language");
        if (language == null || language.isEmpty()) {
            language = "English";
        }

        String reply;
        String summary;

        boolean wantsSentences =
                prompt.toLowerCase().contains("sentence") ||
                        prompt.toLowerCase().contains("example")||
                        prompt.toLowerCase().contains("oracion");

        boolean wantsVocab =
                prompt.toLowerCase().contains("vocab") ||
                        prompt.toLowerCase().contains("vocabulary") ||
                        prompt.toLowerCase().contains("words");

        String type = "lesson";

        if (wantsSentences) type = "sentence";
        if (wantsVocab) type = "vocab";


        try {
            reply = chatClient
                    .prompt()
                    .user("""
                            You are an English tutor named EarthConvo.

                            Selected translation language: %s
                            
                             Your behavior rules:
                             - If the selected language is English: reply in simple English.
                             - If the selected language is Spanish: reply in Spanish.
                             - If the selected language is Japanese: reply in Japanese.

                            Your reply rules:
                            - Keep answers short.
                            - Use bullet points only.
                            - Do NOT use bold, italics, Markdown syntax, or numbering.
                            - Every line must start with "- " (dash + space).
                            
                            If the user asks for sentences:
                            - Output 5 separate example sentences.
                            
                            If the user asks for vocabulary:
                            - Output 5 vocabulary words.
                            
                            Each item must start with "- "
                            
                            Translation rule:
                            - If the user asks to translate, asks "how do I say", or clearly wants a translation,
                              your reply must include these bullets IN THIS EXACT ORDER:

                              - Translation (in %s): <translated text in %s> Pronunciation: <pronunciation written in English letters> Explanation: <simple explanation in English> Example: <1 short example sentence in English>
                            
                            
                            

                        

                            Keep everything short and clear.

                            User: %s
                            Question: %s
                            """.formatted(language, language, language, email, prompt))
                    .call()
                    .content();


            summary = chatClient
                    .prompt()
                    .user("""
                        Summarize the following response in 1 sentence, so it can be shown in a chat sidebar:
                        %s
                        """.formatted(reply))
                    .call()
                    .content();

        } catch (Exception e) {
            reply = "Sorry, I couldn't reach the AI service right now. Please try again later.";
            summary = reply;
        }

        // Save AI response as lesson
        if (reply != null && !reply.trim().isEmpty()) {
            savedLessons.savedLessons(email, reply);
        }

        // Add to session history
        List<ChatMessage> history = (List<ChatMessage>) session.getAttribute("history");
        if (history == null) {
            history = new ArrayList<>();
        }
        history.add(new ChatMessage(prompt, reply, summary, type));
        session.setAttribute("history", history);

        // Flash attributes for main area (Post/Redirect/Get)
        redirectAttributes.addFlashAttribute("question", prompt);
        redirectAttributes.addFlashAttribute("answer", reply);

        // Redirect to home page to prevent double submission
        return "redirect:/";
    }
    @GetMapping("/new-chat")
    public String newChat(HttpSession session) {
        session.removeAttribute("question");
        session.removeAttribute("answer");
        return "redirect:/";
    }

    // ------------------ SAVED LESSONS ------------------
    @GetMapping("/saved-lessons")
    public String savedLessonsPage(HttpSession session, Model model) {
        String email = String.valueOf(session.getAttribute("userEmail"));
        if (email == null || email.isEmpty()) {
            return "redirect:/login";
        }

        List<String> lessons = savedLessons.getLessonsForUser(email);
        model.addAttribute("lessons", lessons);
        model.addAttribute("userEmail", email);
        model.addAttribute("theme", session.getAttribute("theme"));
        return "saved-lessons";
    }

    @PostMapping("/save-lesson")
    public String saveLesson(@RequestParam("lesson") String lesson,
                             HttpSession session,
                             RedirectAttributes redirectAttributes) {

        String email = (String) session.getAttribute("userEmail");
        if (email == null || email.isEmpty()) {
            return "redirect:/login";
        }

        savedLessons.savedLessons(email, lesson);

        redirectAttributes.addFlashAttribute("saved", true);
        return "redirect:/saved-lessons";
    }


    // ------------------ SAVED SENTENCES ------------------
    @GetMapping("/saved-sentences")
    public String savedSentencesPage(HttpSession session, Model model) {
        String email = String.valueOf(session.getAttribute("userEmail"));
        if (email == null || email.isEmpty()) {
            return "redirect:/login";
        }

        List<String> sentences = savedSentences.getSentencesForUser(email);
        model.addAttribute("sentences", sentences);
        model.addAttribute("userEmail", email);
        model.addAttribute("theme", session.getAttribute("theme"));
        return "saved-sentences";
    }

    @PostMapping("/save-sentence")
    public String saveSentence(@RequestParam("sentence") String sentence,
                               HttpSession session) {

        String email = (String) session.getAttribute("userEmail");
        if (email == null || email.isEmpty()) {
            return "redirect:/login";
        }

        savedSentences.savedSentences(email, sentence);
        return "redirect:/saved-sentences";
    }


    // ------------------ SAVED VOCAB ------------------
    @GetMapping("/saved-vocab")
    public String savedVocabPage(HttpSession session, Model model) {
        String email = String.valueOf(session.getAttribute("userEmail"));
        if (email == null || email.isEmpty()) {
            return "redirect:/login";
        }

        List<String> vocab = savedVocab.getVocabForUser(email);
        model.addAttribute("vocab", vocab);
        model.addAttribute("userEmail", email);
        model.addAttribute("theme", session.getAttribute("theme"));
        return "saved-vocab";
    }

    @PostMapping("/save-vocab")
    public String saveVocab(@RequestParam("vocab") String vocab,
                            HttpSession session) {

        String email = (String) session.getAttribute("userEmail");
        if (email == null || email.isEmpty()) {
            return "redirect:/login";
        }

        savedVocab.savedVocab(email, vocab);
        return "redirect:/saved-vocab";
    }


    @GetMapping("/settings")
    public String settings(Model model, HttpSession session) {
        String userEmail = (String) session.getAttribute("userEmail");
        model.addAttribute("userEmail", userEmail);
        model.addAttribute("theme", session.getAttribute("theme"));
        return "settings"; // maps to settings.jte
    }
}
