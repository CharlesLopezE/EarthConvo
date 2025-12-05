package edu.csula.cs3220stu12.earthconvo;

import jakarta.servlet.http.HttpSession;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    private final ChatClient chatClient;
    private final SavedLessons savedLessons;
    private final SavedSentences savedSentences;
    private final SavedVocab savedVocab;

    public HomeController(ChatClient.Builder chatClientBuilder,
                          SavedLessons savedLessons,
                          SavedSentences savedSentences,
                          SavedVocab savedVocab) {
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

        // language default
        String language = (String) session.getAttribute("language");
        if (language == null || language.isEmpty()) {
            language = "English";
            session.setAttribute("language", language);
        }

        // theme default
        String theme = (String) session.getAttribute("theme");
        if (theme == null) theme = "default";
        model.addAttribute("theme", theme);

        // history = chat history in session
        List<ChatMessage> history = (List<ChatMessage>) session.getAttribute("history");
        if (history == null) {
            history = new ArrayList<>();
            session.setAttribute("history", history);
        }

        model.addAttribute("userEmail", email);
        model.addAttribute("question", null);
        model.addAttribute("answer", null);
        model.addAttribute("history", history);

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
            session.setAttribute("language", language);
        }

        // detect what user is asking for
        boolean wantsSentences =
                prompt.toLowerCase().contains("sentence") ||
                        prompt.toLowerCase().contains("sentences") ||
                        prompt.toLowerCase().contains("oración");

        boolean wantsVocab =
                prompt.toLowerCase().contains("vocab") ||
                        prompt.toLowerCase().contains("vocabulary") ||
                        prompt.toLowerCase().contains("words");

        String type = "lesson";
        if (wantsSentences) type = "sentence";
        if (wantsVocab)    type = "vocab";

        String reply;
        String summary;

        try {
            // Main tutor response
            String systemPrompt = """
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
                            """.formatted(language, language, language, email, prompt);

            reply = chatClient
                    .prompt()
                    .system(systemPrompt)
                    .user(prompt)
                    .call()
                    .content();

            // Short sidebar summary
            summary = chatClient
                    .prompt()
                    .user("""
                            Summarize the following response in 3 to 5 words, so it can be shown in a chat sidebar:
                            %s
                            """.formatted(reply))
                    .call()
                    .content();

        } catch (Exception e) {
            reply = "Sorry, I couldn't reach the AI service right now. Please try again later.";
            summary = reply;
        }

        // Add to session history
        List<ChatMessage> history = (List<ChatMessage>) session.getAttribute("history");
        if (history == null) {
            history = new ArrayList<>();
        }
        history.add(new ChatMessage(prompt, reply, summary, type));
        session.setAttribute("history", history);

        // Flash attributes for main chat display
        redirectAttributes.addFlashAttribute("question", prompt);
        redirectAttributes.addFlashAttribute("answer", reply);

        // Avoid double-submit
        return "redirect:/";
    }

    // ------------------ NEW CHAT (CLEAR HISTORY) ------------------
    @GetMapping("/new-chat")
    public String newChat(HttpSession session) {
        session.removeAttribute("question");
        session.removeAttribute("answer");
        session.removeAttribute("history");
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
    public String saveLesson(@RequestParam("lesson") String lesson, HttpSession session) {
        String email = (String) session.getAttribute("userEmail");
        if (email != null && !email.isEmpty()) {
            savedLessons.savedLessons(email, lesson);
        }
        return "redirect:/saved-lessons";
    }

    @PostMapping("/delete-lesson")
    public String deleteLesson(@RequestParam String lesson, HttpSession session) {
        String email = (String) session.getAttribute("userEmail");
        if (email != null) {
            savedLessons.getLessonsForUser(email).remove(lesson);
        }
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

    @PostMapping("/delete-sentence")
    public String deleteSentence(
            @RequestParam String userEmail,
            @RequestParam String sentence
    ) {
        savedSentences.deleteSentence(userEmail, sentence);
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

    // 🔹 NEW: delete a vocab row (for your delete button in saved-vocab.jte)
    @PostMapping("/delete-vocab")
    public String deleteVocab(
            @RequestParam String userEmail,
            @RequestParam String vocab
    ) {
        savedVocab.deleteVocab(userEmail, vocab);
        return "redirect:/saved-vocab";
    }

    // ------------------ SETTINGS ------------------
    @GetMapping("/settings")
    public String settings(Model model, HttpSession session) {
        String userEmail = (String) session.getAttribute("userEmail");
        model.addAttribute("userEmail", userEmail);
        model.addAttribute("theme", session.getAttribute("theme"));
        return "settings"; // maps to settings.jte
    }
}
