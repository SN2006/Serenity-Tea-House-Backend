package com.example.backend.repositories.chatRoom;

import com.example.backend.entity.User;
import com.example.backend.entity.chatRoom.ChatRoom;
import com.example.backend.entity.chatRoom.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findByChatRoomOrderByCreatedAtAsc(ChatRoom chatRoom);

    @Modifying
    @Query("UPDATE Message m SET m.isRead = :isRead WHERE m.chatRoom = :chatRoom AND m.from = :user")
    void updateIsReading(@Param(value = "isRead") Boolean isRead, @Param(value = "chatRoom") ChatRoom chatRoom, @Param(value = "user") User user);

    @Modifying
    @Query("UPDATE Message m SET m.isRead = :isRead WHERE m.chatRoom = :chatRoom AND NOT(m.from = :user)")
    void updateIsReadingWithoutFriend(@Param(value = "isRead") Boolean isRead, @Param(value = "chatRoom") ChatRoom chatRoom, @Param(value = "user") User user);

}
