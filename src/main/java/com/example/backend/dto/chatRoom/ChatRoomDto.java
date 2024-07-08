package com.example.backend.dto.chatRoom;


import com.example.backend.dto.userDtos.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomDto {

    private Long id;
    private List<UserDto> users;
    private List<MessageDto> messages;

}
