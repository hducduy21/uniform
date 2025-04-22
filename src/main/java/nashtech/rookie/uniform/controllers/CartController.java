package nashtech.rookie.uniform.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nashtech.rookie.uniform.dtos.request.CartRequest;
import nashtech.rookie.uniform.dtos.response.ApiResponse;
import nashtech.rookie.uniform.dtos.response.CartResponse;
import nashtech.rookie.uniform.services.CartService;
import nashtech.rookie.uniform.utils.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/carts")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<Collection<CartResponse>> getAllCarts() {
        return ResponseUtil.successResponse(cartService.getAllCarts());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<Void> createCart(@RequestBody @Valid CartRequest cart) {
        cartService.addToCart(cart);
        return ResponseUtil.successResponse("Cart created successfully");
    }

    @PutMapping("/{cartId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<Void> updateCart(@PathVariable Long cartId, @RequestBody @Valid CartRequest cart) {
        cartService.updateCart(cartId, cart.getQuantity());
        return ResponseUtil.successResponse("Cart updated successfully");
    }

    @DeleteMapping("/{cartId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ApiResponse<Void> deleteCart(@PathVariable Long cartId) {
        cartService.removeFromCart(cartId);
        return ResponseUtil.successResponse("Cart deleted successfully");
    }
}
