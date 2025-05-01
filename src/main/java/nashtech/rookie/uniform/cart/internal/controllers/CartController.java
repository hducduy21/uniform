package nashtech.rookie.uniform.cart.internal.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nashtech.rookie.uniform.cart.internal.dtos.CartRequest;
import nashtech.rookie.uniform.cart.internal.dtos.CartResponse;
import nashtech.rookie.uniform.cart.internal.services.CartService;
import nashtech.rookie.uniform.shared.dtos.ApiResponse;
import nashtech.rookie.uniform.shared.utils.ResponseUtil;
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
    public ApiResponse<Page<CartResponse>> getAllCarts(@PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseUtil.successResponse(cartService.getAllCarts(pageable));
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
