package ru.yandex.practicum.commerce.shoppingcart.mapper;

import org.mapstruct.Mapper;
import ru.yandex.practicum.commerce.shoppingcart.model.ShoppingCart;
import ru.yandex.practicum.commerce.dto.cart.ShoppingCartDto;

@Mapper
public interface ShoppingCartMapper {

    ShoppingCartDto mapToDto(ShoppingCart shoppingCart);
}