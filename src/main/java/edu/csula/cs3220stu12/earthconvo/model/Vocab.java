package edu.csula.cs3220stu12.earthconvo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "vocab")
public class Vocab {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500)
    private String entry; // stored as "English - Spanish"

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_email")
    private User user;

    public Vocab() {}
    public Vocab(User user, String entry) {
        this.user = user;
        this.entry = entry;
    }

    public Long getId() { return id; }
    public String getEntry() { return entry; }
    public void setEntry(String entry) { this.entry = entry; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}
