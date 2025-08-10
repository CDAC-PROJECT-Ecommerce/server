package com.example.shopee.Mapper;

import com.example.shopee.DTO.Cart.CartDto;
import com.example.shopee.DTO.Cart.CartItemDto;
import com.example.shopee.Model.Cart;

import java.util.List;

public class CartMapper {
    // Dto to send cart details of user
    public static CartDto convertToDto(Cart cart){
        CartDto cartDto=new CartDto();
        cartDto.setCartId(cart.getId());
        if(cart.getUser()!=null){
            cartDto.setUsername(cart.getUser().getUsername());

        }

        List<CartItemDto> itemDtoList = cart.getCartItems().stream().map(
                item-> {
                    CartItemDto itemDto = new CartItemDto();
                    itemDto.setProductId(item.getProduct().getId());
                    itemDto.setProductName(item.getProduct().getName());
                    itemDto.setPrice(item.getProduct().getPrice());
                    itemDto.setQuantity(item.getQuantity());
                    itemDto.setImageUrl(item.getImageUrl());
                    return itemDto;
                }
        ).toList();

        cartDto.setItems(itemDtoList);
        return cartDto;
    }

}
