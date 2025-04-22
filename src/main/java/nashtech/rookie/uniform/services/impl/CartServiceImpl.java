package nashtech.rookie.uniform.services.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import nashtech.rookie.uniform.dtos.request.CartRequest;
import nashtech.rookie.uniform.dtos.response.CartResponse;
import nashtech.rookie.uniform.entities.Cart;
import nashtech.rookie.uniform.entities.ProductVariants;
import nashtech.rookie.uniform.entities.User;
import nashtech.rookie.uniform.exceptions.BadRequestException;
import nashtech.rookie.uniform.exceptions.ForbiddenException;
import nashtech.rookie.uniform.exceptions.ResourceNotFoundException;
import nashtech.rookie.uniform.mappers.CartMapper;
import nashtech.rookie.uniform.repositories.CartRepository;
import nashtech.rookie.uniform.repositories.ProductVariantsRepository;
import nashtech.rookie.uniform.services.CartService;
import nashtech.rookie.uniform.utils.SecurityUtil;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final ProductVariantsRepository productVariantsRepository;
    private final CartMapper cartMapper;

    @Transactional
    @Override
    public void addToCart(CartRequest cartRequest) {
        User user = getCurrentUser();
        ProductVariants productVariants = getProductVariantsById(cartRequest.getProductVariantsId());

        if (cartRepository.existsByUserIdAndProductVariantsId(user.getId(), cartRequest.getProductVariantsId())) {
            throw new BadRequestException("Product already in cart");
        }

        Cart cart = Cart.builder()
                .user(user)
                .productVariants(productVariants)
                .quantity(cartRequest.getQuantity())
                .build();
        saveCart(cart);
    }

    @Transactional
    @Override
    public void removeFromCart(Long productVariantsId) {
        User user = getCurrentUser();

        Cart cart = findCart(user.getId(), productVariantsId);

        cartRepository.delete(cart);
    }

    @Transactional
    @Override
    public void updateCart(Long cartId, Integer quantity) {
        Cart cart = findCart(cartId);
        User user = getCurrentUser();

        if(!cart.getUser().equals(user)) {
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
    public Collection<CartResponse> getAllCarts() {
        return cartRepository.findByUserId(getCurrentUserId()).stream().map(cartMapper::cartToCartResponse).toList();
    }

    private void saveCart(Cart cart) {
        cartRepository.save(cart);
    }

    private User getCurrentUser() {
        return SecurityUtil.getCurrentUser();
    }

    private UUID getCurrentUserId() {
        return SecurityUtil.getCurrentUserId();
    }

    private ProductVariants getProductVariantsById(Long productVariantsId) {
        return productVariantsRepository.findById(productVariantsId).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
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
