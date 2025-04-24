package nashtech.rookie.uniform.services;

import nashtech.rookie.uniform.dtos.request.CartRequest;
import nashtech.rookie.uniform.dtos.response.CartResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface CartService {
    void addToCart(CartRequest cartRequest);

    void removeFromCart(Long productVariantsId);

    void updateCart(Long productVariantsId, Integer quantity);

    Page<CartResponse> getAllCarts(Pageable pageable);
}
