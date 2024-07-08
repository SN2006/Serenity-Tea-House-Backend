package com.example.backend.repositories.chatRoom;

import com.example.backend.entity.User;
import com.example.backend.entity.chatRoom.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    List<ChatRoom> findByUsersContains(User user);
}
