package nashtech.rookie.uniform.cart.internal.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nashtech.rookie.uniform.cart.internal.dtos.CartRequest;
import nashtech.rookie.uniform.cart.internal.dtos.CartResponse;
import nashtech.rookie.uniform.cart.internal.services.CartService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carts")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<CartResponse> getAllCarts(@PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.ASC) Pageable pageable) {
        return cartService.getAllCarts(pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createCart(@RequestBody @Valid CartRequest cart) {
        cartService.addToCart(cart);
    }

    @PutMapping("/{cartId}")
    @ResponseStatus(HttpStatus.OK)
    public void updateCart(@PathVariable Long cartId, @RequestBody @Valid CartRequest cart) {
        cartService.updateCart(cartId, cart.getQuantity());
    }

    @DeleteMapping("/{cartId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCart(@PathVariable Long cartId) {
        cartService.removeFromCart(cartId);
    }
}
