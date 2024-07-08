package com.example.backend.sercives.chatRoom;

import com.example.backend.dto.chatRoom.ChatRoomDto;
import com.example.backend.entity.User;
import com.example.backend.entity.chatRoom.ChatRoom;
import com.example.backend.entity.chatRoom.Message;
import com.example.backend.enums.MessageType;
import com.example.backend.enums.Role;
import com.example.backend.exceptions.AuthException;
import com.example.backend.repositories.UserRepository;
import com.example.backend.repositories.chatRoom.ChatRoomRepository;
import com.example.backend.repositories.chatRoom.MessageRepository;
import com.example.backend.util.AppConvector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final AppConvector convector;

    @Autowired
    public ChatRoomService(ChatRoomRepository chatRoomRepository, MessageRepository messageRepository, UserRepository userRepository, AppConvector convector) {
        this.chatRoomRepository = chatRoomRepository;
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.convector = convector;
    }

    @Transactional
    public ChatRoomDto getChatRoomByUsers(User user, User friend){
        List<ChatRoom> chats = chatRoomRepository.findByUsersContains(user);
        ChatRoom chatRoomFromDb = chats.stream()
                .filter(chat -> chat.getUsers().contains(friend))
                .findFirst()
                .orElseGet(() -> {
                    ChatRoom newChatRoom = new ChatRoom();
                    newChatRoom.addUser(user);
                    newChatRoom.addUser(friend);
                    ChatRoom presavedChatRoom = chatRoomRepository.save(newChatRoom);
                    if (friend.getRole().equals(Role.ADMIN) && user.getRole().equals(Role.USER)){
                        Message message = new Message();
                        message.setContent("Hello, how can I help you?");
                        message.setFrom(friend);
                        message.setType(MessageType.TEXT);
                        message.setCreatedAt(new Date());
                        message.setIsRead(true);
                        message.setChatRoom(presavedChatRoom);
                        Message savedMessage = messageRepository.save(message);
                        presavedChatRoom.addMessage(savedMessage);
                        return presavedChatRoom;
                    }
                    return presavedChatRoom;
                });
        messageRepository.updateIsReading(true, chatRoomFromDb, friend);
        chatRoomFromDb.getMessages().sort(Comparator.comparing(Message::getCreatedAt));
        return convector.convertToChatRoomDto(
            chatRoomFromDb
        );
    }

}
