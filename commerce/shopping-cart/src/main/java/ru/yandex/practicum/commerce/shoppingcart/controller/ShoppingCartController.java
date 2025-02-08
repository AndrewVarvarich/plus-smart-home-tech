package ru.yandex.practicum.commerce.shoppingcart.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.commerce.shoppingcart.mapper.ShoppingCartMapper;
import ru.yandex.practicum.commerce.shoppingcart.model.ShoppingCart;
import ru.yandex.practicum.commerce.shoppingcart.service.ShoppingCartService;
import ru.yandex.practicum.commerce.dto.warehouse.BookedProductsDto;
import ru.yandex.practicum.commerce.dto.warehouse.ChangeProductQuantityRequest;
import ru.yandex.practicum.commerce.dto.cart.ShoppingCartDto;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/shopping-cart")
@RequiredArgsConstructor
@Slf4j
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;
    private final ShoppingCartMapper shoppingCartMapper;

    @GetMapping
    public ShoppingCartDto getShoppingCartByUsername(@RequestParam final String username) {
        log.info("Received request for shopping cart by username: {}", username);

        final ShoppingCart shoppingCart = shoppingCartService.getShoppingCartByUsername(username);
        final ShoppingCartDto dto = shoppingCartMapper.mapToDto(shoppingCart);

        log.debug("Mapped shopping cart response: {}", dto);
        return dto;
    }

    @PutMapping
    public ShoppingCartDto addProductsToShoppingCart(
            @RequestParam final String username,
            @RequestBody final Map<UUID, Long> products) {
        log.info("Adding products to shopping cart: {}", username);

        final ShoppingCart shoppingCart = shoppingCartService.addProductsToShoppingCart(username, products);
        final ShoppingCartDto dto = shoppingCartMapper.mapToDto(shoppingCart);

        log.debug("Updated shopping cart after adding products: {}", dto);
        return dto;
    }

    @DeleteMapping
    public void deactivateShoppingCartByUsername(@RequestParam final String username) {
        log.info("Received request to deactivate shopping cart: username = {}", username);
        shoppingCartService.deactivateShoppingCart(username);
        log.info("Responded with 200 OK to deactivate shopping cart request: username = {}", username);
    }

    @PostMapping("/remove")
    public ShoppingCartDto deleteProductsFromShoppingCart(
            @RequestParam final String username,
            @RequestBody final Set<UUID> products) {
        log.info("Removing products from shopping cart: {}", username);

        final ShoppingCart shoppingCart = shoppingCartService.deleteProductsFromShoppingCart(username, products);
        final ShoppingCartDto dto = shoppingCartMapper.mapToDto(shoppingCart);

        log.debug("Updated shopping cart after removal: {}", dto);
        return dto;
    }

    @PostMapping("/change-quantity")
    public ShoppingCartDto changeProductQuantity(
            @RequestParam final String username,
            @RequestBody @Valid final ChangeProductQuantityRequest request) {
        log.info("Changing product quantity: {} | product: {}", username, request.getProductId());
        log.debug("Quantity change details: {}", request);

        final ShoppingCart shoppingCart = shoppingCartService.changeProductQuantity(username, request);
        final ShoppingCartDto dto = shoppingCartMapper.mapToDto(shoppingCart);

        log.debug("Updated shopping cart after quantity change: {}", dto);
        return dto;
    }

    @PostMapping("/booking")
    public BookedProductsDto bookProductsInWarehouse(@RequestParam final String username) {
        log.info("Booking products for: {}", username);

        final BookedProductsDto dto = shoppingCartService.bookProductsInWarehouse(username);

        log.debug("Booking details - volume: {}, weight: {}, fragile: {}",
                dto.getDeliveryVolume(), dto.getDeliveryWeight(), dto.getFragile());
        return dto;
    }
}