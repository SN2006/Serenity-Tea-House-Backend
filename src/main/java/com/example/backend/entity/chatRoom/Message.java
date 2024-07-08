package com.example.backend.entity.chatRoom;

import com.example.backend.entity.User;
import com.example.backend.enums.MessageType;
import jakarta.persistence.*;
import jdk.jfr.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "chat_message")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "content")
    private String content;
    @Timestamp
    @Column(name = "created_at")
    private Date createdAt;
    @Column(name = "message_type")
    @Enumerated(EnumType.STRING)
    private MessageType type;
    @Column(name = "is_reading")
    private Boolean isRead;
    @ManyToOne
    @JoinColumn(name = "chat_id", referencedColumnName = "id")
    private ChatRoom chatRoom;
    @ManyToOne
    @JoinColumn(name = "by_user_id", referencedColumnName = "id")
    private User from;

}
