package nashtech.rookie.uniform.cart.internal.services;

import lombok.RequiredArgsConstructor;
import nashtech.rookie.uniform.cart.internal.dtos.CartRequest;
import nashtech.rookie.uniform.cart.internal.dtos.CartResponse;
import nashtech.rookie.uniform.cart.internal.entities.Cart;
import nashtech.rookie.uniform.cart.internal.mappers.CartMapper;
import nashtech.rookie.uniform.cart.internal.repositories.CartRepository;
import nashtech.rookie.uniform.product.entities.ProductVariants;
import nashtech.rookie.uniform.product.repositories.ProductVariantsRepository;
import nashtech.rookie.uniform.shared.exceptions.BadRequestException;
import nashtech.rookie.uniform.shared.exceptions.ForbiddenException;
import nashtech.rookie.uniform.shared.exceptions.ResourceNotFoundException;
import nashtech.rookie.uniform.shared.utils.SecurityUtil;
import nashtech.rookie.uniform.user.internal.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Page<CartResponse> getAllCarts(Pageable pageable) {
        return cartRepository.findByUserId(getCurrentUserId(), pageable).map(cartMapper::cartToCartResponse);
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
