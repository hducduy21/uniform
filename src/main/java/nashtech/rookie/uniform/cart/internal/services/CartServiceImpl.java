package nashtech.rookie.uniform.cart.internal.services;

import lombok.RequiredArgsConstructor;
import nashtech.rookie.uniform.cart.internal.dtos.CartRequest;
import nashtech.rookie.uniform.cart.internal.dtos.CartResponse;
import nashtech.rookie.uniform.cart.internal.entities.Cart;
import nashtech.rookie.uniform.cart.internal.mappers.CartMapper;
import nashtech.rookie.uniform.cart.internal.repositories.CartRepository;
import nashtech.rookie.uniform.shared.exceptions.BadRequestException;
import nashtech.rookie.uniform.shared.exceptions.ForbiddenException;
import nashtech.rookie.uniform.shared.exceptions.ResourceNotFoundException;
import nashtech.rookie.uniform.shared.utils.SecurityUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final CartMapper cartMapper;

    @Transactional
    @Override
    public void addToCart(CartRequest cartRequest) {
        UUID userId = SecurityUtil.getCurrentUserId();
        Long productVariantsId = cartRequest.getProductVariantsId();

        //clients: check if the product exists

        if (cartRepository.existsByUserIdAndProductVariantsId(userId, cartRequest.getProductVariantsId())) {
            throw new BadRequestException("Product already in cart");
        }

        Cart cart = Cart.builder()
                .userId(userId)
                .productVariantsId(productVariantsId)
                .quantity(cartRequest.getQuantity())
                .build();
        saveCart(cart);
    }

    @Transactional
    @Override
    public void removeFromCart(Long productVariantsId) {
        UUID userId = getCurrentUserId();

        Cart cart = findCart(userId, productVariantsId);

        cartRepository.delete(cart);
    }

    @Transactional
    @Override
    public void updateCart(Long cartId, Integer quantity) {
        Cart cart = findCart(cartId);
        UUID userId = getCurrentUserId();

        if(!StringUtils.equals(cart.getUserId().toString(), userId.toString())) {
            throw new ForbiddenException("You do not have permission to update this cart");
        }

        if(quantity == 0) {
            cartRepository.delete(cart);
            return;
        }

        cart.setQuantity(quantity);
        saveCart(cart);
    }

    @Transactional
    @Override
    public Page<CartResponse> getAllCarts(Pageable pageable) {
        return cartRepository.findByUserId(getCurrentUserId(), pageable).map(cartMapper::cartToCartResponse);
    }

    private void saveCart(Cart cart) {
        cartRepository.save(cart);
    }

    private UUID getCurrentUserId() {
        return SecurityUtil.getCurrentUserId();
    }

    private Cart findCart(Long cartId) {
        return cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
    }

    private Cart findCart(UUID userId, Long productVariantsId) {
        return cartRepository.findByUserIdAndProductVariantsId(userId, productVariantsId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found in cart"));
    }
}
