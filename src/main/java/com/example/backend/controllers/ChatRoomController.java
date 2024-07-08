package com.example.backend.controllers;

import com.example.backend.dto.chatRoom.ChatRoomDto;
import com.example.backend.entity.User;
import com.example.backend.sercives.UserService;
import com.example.backend.sercives.chatRoom.ChatRoomService;
import com.example.backend.sercives.chatRoom.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin(origins = {"http://localhost:3000/"})
@RequestMapping("/api/v1/chats")
public class ChatRoomController {

    private final UserService userService;
    private final ChatRoomService chatRoomService;
    private final MessageService messageService;

    @Autowired
    public ChatRoomController(UserService userService, ChatRoomService chatRoomService, MessageService messageService) {
        this.userService = userService;
        this.chatRoomService = chatRoomService;
        this.messageService = messageService;
    }

    @GetMapping("/{user_id}/{friend_id}")
    public ResponseEntity<ChatRoomDto> getChatRoomByUser(@PathVariable("user_id") Long user_id, @PathVariable("friend_id") Long friend_id){
        User user = userService.findUserById(user_id);
        User friend = userService.findUserById(friend_id);
        ChatRoomDto chatFromDb = chatRoomService.getChatRoomByUsers(user, friend);
        return ResponseEntity.ok(chatFromDb);
    }

}
