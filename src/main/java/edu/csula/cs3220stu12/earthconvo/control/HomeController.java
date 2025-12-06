package edu.csula.cs3220stu12.earthconvo.control;

import edu.csula.cs3220stu12.earthconvo.ChatMessage;
import edu.csula.cs3220stu12.earthconvo.model.*;
import edu.csula.cs3220stu12.earthconvo.repository.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    private final ChatClient chatClient;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private LessonRepository lessonRepo;

    @Autowired
    private SentenceRepository sentenceRepo;

    @Autowired
    private VocabRepository vocabRepo;

    public HomeController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    // ------------------ HOME ------------------
    @GetMapping("/")
    public String home(HttpSession session, Model model) {
        String email = (String) session.getAttribute("userEmail");
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

        String email = (String) session.getAttribute("userEmail");
        if (email == null || email.isEmpty()) {
            return "redirect:/login";
        }

        String language = (String) session.getAttribute("language");
        if (language == null || language.isEmpty()) {
            language = "English";
            session.setAttribute("language", language);
        }

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
        if (wantsVocab) type = "vocab";

        String reply;
        String summary;

        try {
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
                    - Every line must start with "- "
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

            summary = chatClient
                    .prompt()
                    .user("Summarize the following response in 3 to 5 words, so it can be shown in a chat sidebar:\n" + reply)
                    .call()
                    .content();

        } catch (Exception e) {
            reply = "Sorry, I couldn't reach the AI service right now. Please try again later.";
            summary = reply;
        }

        List<ChatMessage> history = (List<ChatMessage>) session.getAttribute("history");
        if (history == null) history = new ArrayList<>();
        history.add(new ChatMessage(prompt, reply, summary, type));
        session.setAttribute("history", history);

        redirectAttributes.addFlashAttribute("question", prompt);
        redirectAttributes.addFlashAttribute("answer", reply);

        return "redirect:/";
    }

    // ------------------ NEW CHAT ------------------
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
        String email = (String) session.getAttribute("userEmail");
        if (email == null || email.isEmpty()) return "redirect:/login";

        User user = userRepo.findById(email).orElseThrow();
        List<String> lessons = lessonRepo.findByUser(user).stream()
                .map(Lesson::getContent)
                .toList();

        model.addAttribute("lessons", lessons);
        model.addAttribute("userEmail", email);
        model.addAttribute("theme", session.getAttribute("theme"));
        return "saved-lessons";
    }

    @PostMapping("/save-lesson")
    public String saveLesson(@RequestParam("lesson") String lesson, HttpSession session) {
        String email = (String) session.getAttribute("userEmail");
        if (email != null && !lesson.isBlank()) {
            User user = userRepo.findById(email).orElseThrow();
            lessonRepo.save(new Lesson(user, lesson));
        }
        return "redirect:/saved-lessons";
    }

    @PostMapping("/delete-lesson")
    @Transactional
    public String deleteLesson(@RequestParam String lesson, HttpSession session) {
        String email = (String) session.getAttribute("userEmail");
        if (email != null) {
            User user = userRepo.findById(email).orElseThrow();
            lessonRepo.deleteByUserAndContent(user, lesson);
        }
        return "redirect:/saved-lessons";
    }

    // ------------------ SAVED SENTENCES ------------------
    @GetMapping("/saved-sentences")
    public String savedSentencesPage(HttpSession session, Model model) {
        String email = (String) session.getAttribute("userEmail");
        if (email == null || email.isEmpty()) return "redirect:/login";

        User user = userRepo.findById(email).orElseThrow();
        List<String> sentences = sentenceRepo.findByUser(user).stream()
                .map(Sentence::getSentence)
                .toList();

        model.addAttribute("sentences", sentences);
        model.addAttribute("userEmail", email);
        model.addAttribute("theme", session.getAttribute("theme"));
        return "saved-sentences";
    }

    @PostMapping("/save-sentence")
    public String saveSentence(@RequestParam("sentence") String sentence, HttpSession session) {
        String email = (String) session.getAttribute("userEmail");
        if (email != null && !sentence.isBlank()) {
            User user = userRepo.findById(email).orElseThrow();
            sentenceRepo.save(new Sentence(user, sentence));
        }
        return "redirect:/saved-sentences";
    }

    @PostMapping("/delete-sentence")
    public String deleteSentence(@RequestParam String sentence, HttpSession session) {
        String email = (String) session.getAttribute("userEmail");
        if (email != null) {
            User user = userRepo.findById(email).orElseThrow();
            sentenceRepo.deleteByUserAndSentence(user, sentence);
        }
        return "redirect:/saved-sentences";
    }

    // ------------------ SAVED VOCAB ------------------
    @GetMapping("/saved-vocab")
    public String savedVocabPage(HttpSession session, Model model) {
        String email = (String) session.getAttribute("userEmail");
        if (email == null || email.isEmpty()) return "redirect:/login";

        User user = userRepo.findById(email).orElseThrow();
        List<String> vocab = vocabRepo.findByUser(user).stream()
                .map(Vocab::getEntry)
                .toList();

        model.addAttribute("vocab", vocab);
        model.addAttribute("userEmail", email);
        model.addAttribute("theme", session.getAttribute("theme"));
        return "saved-vocab";
    }

    @PostMapping("/save-vocab")
    public String saveVocab(@RequestParam("vocab") String vocabEntry, HttpSession session) {
        String email = (String) session.getAttribute("userEmail");
        if (email != null && !vocabEntry.isBlank()) {
            User user = userRepo.findById(email).orElseThrow();
            vocabRepo.save(new Vocab(user, vocabEntry));
        }
        return "redirect:/saved-vocab";
    }

    @PostMapping("/delete-vocab")
    @Transactional
    public String deleteVocab(@RequestParam String vocab, HttpSession session) {
        String email = (String) session.getAttribute("userEmail");
        if (email != null) {
            User user = userRepo.findById(email).orElseThrow();
            vocabRepo.deleteByUserAndEntry(user, vocab);
        }
        return "redirect:/saved-vocab";
    }

    // ------------------ SETTINGS ------------------
    @GetMapping("/settings")
    public String settings(Model model, HttpSession session) {
        String userEmail = (String) session.getAttribute("userEmail");
        model.addAttribute("userEmail", userEmail);
        model.addAttribute("theme", session.getAttribute("theme"));
        return "settings";
    }

    // ------------------ HISTORY ------------------
    @PostMapping("/clear-history")
    public String clearHistory(HttpSession session) {
        session.removeAttribute("history");
        return "redirect:/";
    }

    @GetMapping("/delete-history")
    public String deleteHistory(@RequestParam("index") int index, HttpSession session) {
        List<ChatMessage> history = (List<ChatMessage>) session.getAttribute("history");
        if (history != null && index >= 0 && index < history.size()) {
            history.remove(index);
            session.setAttribute("history", history);
        }
        return "redirect:/";
    }

}
