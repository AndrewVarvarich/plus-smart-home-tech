package ru.yandex.practicum.commerce.shoppingcart.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.commerce.shoppingcart.exception.BookingNotPossibleException;
import ru.yandex.practicum.commerce.shoppingcart.mapper.ShoppingCartMapper;
import ru.yandex.practicum.commerce.shoppingcart.model.ShoppingCart;
import ru.yandex.practicum.commerce.shoppingcart.model.ShoppingCartState;
import ru.yandex.practicum.commerce.shoppingcart.repo.ShoppingCartRepository;
import ru.yandex.practicum.commerce.dto.warehouse.BookedProductsDto;
import ru.yandex.practicum.commerce.dto.warehouse.ChangeProductQuantityRequest;
import ru.yandex.practicum.commerce.dto.cart.ShoppingCartDto;
import ru.yandex.practicum.commerce.exception.NoProductsInShoppingCartException;
import ru.yandex.practicum.commerce.exception.NotAuthorizedUserException;
import ru.yandex.practicum.commerce.exception.ShoppingCartDeactivatedException;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final WarehouseService warehouseService;
    private final ShoppingCartMapper shoppingCartMapper;
    private final ShoppingCartRepository repository;
    private final UUIDGenerator uuidGenerator;

    @Override
    public ShoppingCart getShoppingCartByUsername(final String username) {
        if (username.isBlank()) {
            throw new NotAuthorizedUserException("User not unauthenticated");
        }
        return repository.findByUsername(username).orElseGet(() -> createShoppingCart(username));
    }

    @Override
    public ShoppingCart addProductsToShoppingCart(final String username, final Map<UUID, Long> products) {
        ShoppingCart shoppingCart = getShoppingCartByUsername(username);
        requireShoppingCartNotDeactivated(shoppingCart);
        shoppingCart.getProducts().putAll(products);
        shoppingCart = repository.save(shoppingCart);
        log.debug("Products added to cart ID: {}", shoppingCart.getShoppingCartId());
        return shoppingCart;
    }

    @Override
    public void deactivateShoppingCart(final String username) {
        ShoppingCart shoppingCart = getShoppingCartByUsername(username);
        shoppingCart.setShoppingCartState(ShoppingCartState.DEACTIVATED);
        shoppingCart = repository.save(shoppingCart);
        log.debug("Cart deactivated: {}", shoppingCart.getShoppingCartId());
    }

    @Override
    public ShoppingCart deleteProductsFromShoppingCart(final String username, final Set<UUID> products) {
        final ShoppingCart shoppingCart = getShoppingCartByUsername(username);
        final Set<UUID> productsNotInShoppingCart = products.stream()
                .filter(productId -> !shoppingCart.getProducts().containsKey(productId))
                .collect(Collectors.toSet());
        if (!productsNotInShoppingCart.isEmpty()) {
            final String productsNotInShoppingCartStr = productsNotInShoppingCart.stream()
                    .map(Object::toString)
                    .sorted()
                    .collect(Collectors.joining(", "));
            throw new NoProductsInShoppingCartException("Shopping cart contains no product(s): "
                    + productsNotInShoppingCartStr);
        }
        requireShoppingCartNotDeactivated(shoppingCart);
        products.forEach(shoppingCart.getProducts()::remove);
        final ShoppingCart savedShoppingCart = repository.save(shoppingCart);
        log.debug("Products removed from cart ID: {}", savedShoppingCart.getShoppingCartId());
        return savedShoppingCart;
    }

    @Override
    public ShoppingCart changeProductQuantity(final String username, final ChangeProductQuantityRequest request) {
        ShoppingCart shoppingCart = getShoppingCartByUsername(username);
        if (!shoppingCart.getProducts().containsKey(request.getProductId())) {
            throw new NoProductsInShoppingCartException("Shopping cart does not contain product "
                    + request.getProductId());
        }
        requireShoppingCartNotDeactivated(shoppingCart);
        shoppingCart.getProducts().put(request.getProductId(), request.getNewQuantity());
        shoppingCart = repository.save(shoppingCart);
        log.debug("Quantity updated for product {} in cart {}",
                request.getProductId(), shoppingCart.getShoppingCartId());
        return shoppingCart;
    }

    @Override
    public BookedProductsDto bookProductsInWarehouse(final String username) {
        final ShoppingCart shoppingCart = getShoppingCartByUsername(username);
        if (shoppingCart.getProducts().isEmpty()) {
            throw new NoProductsInShoppingCartException("Shopping cart is empty");
        }
        requireShoppingCartNotDeactivated(shoppingCart);
        final ShoppingCartDto shoppingCartDto = shoppingCartMapper.mapToDto(shoppingCart);

        try {
            final BookedProductsDto bookedProductsDto = warehouseService.bookProducts(shoppingCartDto);
            log.debug("Products booked from cart {}", shoppingCart.getShoppingCartId());
            return bookedProductsDto;
        } catch (Exception e) {
            throw new BookingNotPossibleException("Cannot book products in warehouse now");
        }
    }

    private ShoppingCart createShoppingCart(final String username) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setShoppingCartId(uuidGenerator.getNewUUID());
        shoppingCart.setUsername(username);
        shoppingCart.setShoppingCartState(ShoppingCartState.ACTIVE);
        shoppingCart = repository.save(shoppingCart);
        log.debug("New cart created for user {}", username);
        return shoppingCart;
    }

    private void requireShoppingCartNotDeactivated(final ShoppingCart shoppingCart) {
        if (ShoppingCartState.DEACTIVATED.equals(shoppingCart.getShoppingCartState())) {
            throw new ShoppingCartDeactivatedException("User not authorized to modify shopping cart");
        }
    }
}