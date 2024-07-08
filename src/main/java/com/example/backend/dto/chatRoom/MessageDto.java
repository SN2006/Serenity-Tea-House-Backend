package com.example.backend.dto.chatRoom;

import com.example.backend.dto.userDtos.UserDto;
import com.example.backend.entity.User;
import com.example.backend.enums.MessageType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageDto {

    private Long id;
    private String content;
    private Date createdAt;
    private MessageType type;
    private Long chatRoomId;
    private UserDto from;
    private Boolean isRead;

}
