package nashtech.rookie.uniform.cart.internal.services;

import nashtech.rookie.uniform.cart.internal.dtos.CartRequest;
import nashtech.rookie.uniform.cart.internal.dtos.CartResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface CartService {
    void addToCart(CartRequest cartRequest);

    void removeFromCart(Long productVariantsId);

    void updateCart(Long productVariantsId, Integer quantity);

    Page<CartResponse> getAllCarts(Pageable pageable);
}
