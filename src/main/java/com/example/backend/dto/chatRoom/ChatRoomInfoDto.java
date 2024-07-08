package com.example.backend.dto.chatRoom;

import com.example.backend.dto.userDtos.UserDto;

import java.util.Date;

public class ChatRoomInfoDto {

    private UserDto user;
    private Date lastMessage;
    private Integer unReadMessages;

}
