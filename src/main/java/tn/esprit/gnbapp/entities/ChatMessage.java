package tn.esprit.gnbapp.entities;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.awt.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString


public class ChatMessage {

    @Id
    @GeneratedValue
    @Column
    private Long idM;
    private String content;
    private String sender;
    private MessageType type;
    private String recipient;

    public ChatMessage(String sender, String s, String content, MessageType chat) {
    }
}