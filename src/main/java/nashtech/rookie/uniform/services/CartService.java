package nashtech.rookie.uniform.services;

import nashtech.rookie.uniform.dtos.request.CartRequest;
import nashtech.rookie.uniform.dtos.response.CartResponse;

import java.util.Collection;


public interface CartService {
    void addToCart(CartRequest cartRequest);

    void removeFromCart(Long productVariantsId);

    void updateCart(Long productVariantsId, Integer quantity);

    Collection<CartResponse> getAllCarts();
}
