package com.example.backend.sercives.chatRoom;

import com.example.backend.dto.chatRoom.MessageDto;
import com.example.backend.entity.User;
import com.example.backend.entity.chatRoom.ChatRoom;
import com.example.backend.entity.chatRoom.Message;
import com.example.backend.enums.MessageType;
import com.example.backend.repositories.UserRepository;
import com.example.backend.repositories.chatRoom.ChatRoomRepository;
import com.example.backend.repositories.chatRoom.MessageRepository;
import com.example.backend.util.AppConvector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;
    private final AppConvector convector;

    @Autowired
    public MessageService(MessageRepository messageRepository, ChatRoomRepository chatRoomRepository, UserRepository userRepository, AppConvector convector) {
        this.messageRepository = messageRepository;
        this.chatRoomRepository = chatRoomRepository;
        this.userRepository = userRepository;
        this.convector = convector;
    }

    @Transactional
    public MessageDto saveMessage(Message message) {
        User user = userRepository.findById(message.getFrom().getId()).orElse(null);
        ChatRoom chatRoom = chatRoomRepository.findById(message.getChatRoom().getId()).orElse(null);
        if (user != null) {
            user.setLastVisit(new Date());
            userRepository.save(user);
        }
        message.setCreatedAt(new Date());
        message.setIsRead(false);
        message.setChatRoom(chatRoom);
        message.setFrom(user);
        if (message.getContent() != null){
            message.setType(MessageType.TEXT);
        }
        messageRepository.updateIsReadingWithoutFriend(true, message.getChatRoom(), user);
        return convector.convertToMessageDto(
                messageRepository.save(message)
        );
    }

    public void readMessagesByUser(User user, List<MessageDto> messages) {
        for (MessageDto messageDto : messages) {
            if (!messageDto.getFrom().getId().equals(user.getId()) && !messageDto.getIsRead()) {
                setReadStatus(messageDto.getId());
            }
        }
    }

    public void setReadStatus(Long messageId){
        Message message = messageRepository.findById(messageId).orElse(null);
        if (message != null) {
            message.setIsRead(true);
            messageRepository.save(message);
        }
    }
}
