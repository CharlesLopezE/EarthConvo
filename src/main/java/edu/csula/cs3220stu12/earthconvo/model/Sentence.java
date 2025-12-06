package edu.csula.cs3220stu12.earthconvo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "sentences")
public class Sentence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 1000)
    private String sentence; // stored as "original||translation"

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_email")
    private User user;

    public Sentence() {}
    public Sentence(User user, String sentence) {
        this.user = user;
        this.sentence = sentence;
    }

    public Long getId() { return id; }
    public String getSentence() { return sentence; }
    public void setSentence(String sentence) { this.sentence = sentence; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}
