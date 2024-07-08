package com.example.backend.util;

import com.example.backend.dto.chatRoom.ChatRoomDto;
import com.example.backend.dto.chatRoom.MessageDto;
import com.example.backend.dto.orderDtos.*;
import com.example.backend.dto.productDtos.ProductDto;
import com.example.backend.dto.userDtos.AddressDto;
import com.example.backend.dto.userDtos.EditUserDto;
import com.example.backend.dto.userDtos.SignUpDto;
import com.example.backend.dto.userDtos.UserDto;
import com.example.backend.entity.Address;
import com.example.backend.entity.Product;
import com.example.backend.entity.User;
import com.example.backend.entity.chatRoom.ChatRoom;
import com.example.backend.entity.chatRoom.Message;
import com.example.backend.entity.order.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AppConvector {

    private final ModelMapper modelMapper;

    @Autowired
    public AppConvector(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Order toOrder(CreateOrderDto orderDto) {
        Order order = new Order();
        order.setAddress(modelMapper.map(orderDto.getAddress(), OrderAddress.class));
        order.setBuyerInfo(modelMapper.map(orderDto.getBuyerInfo(), BuyerInfo.class));
        order.setCardInfo(modelMapper.map(orderDto.getCardInfo(), CardInfo.class));
        return order;
    }

    public OrderDto convertToOrderDto(Order order) {
        OrderDto orderDto = modelMapper.map(order, OrderDto.class);
        orderDto.setAddress(modelMapper.map(order.getAddress(), OrderAddressDto.class));
        orderDto.setBuyerInfo(modelMapper.map(order.getBuyerInfo(), OrderBuyerInfoDto.class));
        orderDto.setCardInfo(modelMapper.map(order.getCardInfo(), CardInfoDto.class));
        if (order.getUser() != null){
            orderDto.setUser(convertToUserDto(order.getUser()));
        }
        orderDto.setProducts(null);
        order.getProducts().forEach(productInOrder -> {
            orderDto.addProduct(convertToProductInOrderDto(productInOrder));
        });
        return orderDto;
    }

    public ProductInOrderDto convertToProductInOrderDto(ProductInOrder productInOrder) {
        ProductInOrderDto productInOrderDto =
                modelMapper.map(productInOrder, ProductInOrderDto.class);
        productInOrderDto.setProduct(convertToProductDto(productInOrder.getProduct()));
        return productInOrderDto;
    }

    public UserDto convertToUserDto(User user){
        UserDto userDto = modelMapper.map(user, UserDto.class);
        userDto.setRole(user.getRole());
        userDto.setAddress(modelMapper.map(user.getAddress(), AddressDto.class));
        return userDto;
    }

    public User convertToUser(SignUpDto signUpDto){
        modelMapper.typeMap(SignUpDto.class, User.class).addMappings(
                mapper -> mapper.skip(User::setPassword)
        );
        return modelMapper.map(signUpDto, User.class);
    }

    public User convertToUser(EditUserDto editUserDto){
        User user = modelMapper.map(editUserDto, User.class);
        user.setAddress(modelMapper.map(editUserDto.getAddress(), Address.class));
        return user;
    }

    public ProductDto convertToProductDto(Product product){
        ProductDto productDto = modelMapper.map(product, ProductDto.class);
        product.getImages().forEach(productImage -> productDto.addImagesId(productImage.getId()));
        return productDto;
    }

    public ChatRoomDto convertToChatRoomDto(ChatRoom chatRoom){
        ChatRoomDto chatRoomDto = modelMapper.map(chatRoom, ChatRoomDto.class);
        chatRoomDto.setUsers(chatRoom.getUsers().stream().map(this::convertToUserDto).toList());
        chatRoomDto.setMessages(chatRoom.getMessages().stream().map(this::convertToMessageDto).toList());
        return chatRoomDto;
    }

    public MessageDto convertToMessageDto(Message message){
        MessageDto messageDto = modelMapper.map(message, MessageDto.class);
        messageDto.setType(message.getType());
        messageDto.setFrom(convertToUserDto(message.getFrom()));
        return messageDto;
    }

}
