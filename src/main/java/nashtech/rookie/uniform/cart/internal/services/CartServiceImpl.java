package nashtech.rookie.uniform.cart.internal.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nashtech.rookie.uniform.application.utils.SecurityUtil;
import nashtech.rookie.uniform.cart.internal.dtos.CartRequest;
import nashtech.rookie.uniform.cart.internal.dtos.CartResponse;
import nashtech.rookie.uniform.cart.internal.entities.Cart;
import nashtech.rookie.uniform.cart.internal.mappers.CartMapper;
import nashtech.rookie.uniform.cart.internal.repositories.CartRepository;
import nashtech.rookie.uniform.product.api.ProductServiceProvider;
import nashtech.rookie.uniform.product.dto.ProductVariantsResponse;
import nashtech.rookie.uniform.shared.exceptions.BadRequestException;
import nashtech.rookie.uniform.shared.exceptions.ForbiddenException;
import nashtech.rookie.uniform.shared.exceptions.ResourceNotFoundException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final CartMapper cartMapper;

    private final ProductServiceProvider productServiceProvider;

    @Transactional
    @Override
    public void addToCart(CartRequest cartRequest) {
        UUID userId = SecurityUtil.getCurrentUserId();
        Long productVariantsId = cartRequest.getProductVariantsId();

        if(!productServiceProvider.productVariantsExists(productVariantsId)) {
            log.info("Product variants does not exist: " + productVariantsId);
            throw new ResourceNotFoundException("Product variants not found");
        }

        if (cartRepository.existsByUserIdAndProductVariantsId(userId, cartRequest.getProductVariantsId())) {
            throw new BadRequestException("Product already in cart");
        }

        Cart cart = Cart.builder()
                .userId(userId)
                .productVariantsId(productVariantsId)
                .quantity(cartRequest.getQuantity())
                .build();
        cartRepository.save(cart);
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
            log.warn("User does not have permission to update the cart");
            throw new ForbiddenException("You do not have permission to update this cart");
        }

        if(quantity == 0) {
            cartRepository.delete(cart);
            return;
        }

        cart.setQuantity(quantity);
        cartRepository.save(cart);
    }

    @Transactional
    @Override
    public Page<CartResponse> getAllCarts(Pageable pageable) {
        UUID userId = getCurrentUserId();
        Page<Cart> carts = findAllCart(userId, pageable);

        Set<Long> productVariantsIds = carts.getContent().stream()
                .map(Cart::getProductVariantsId)
                .collect(Collectors.toSet());

        Collection<ProductVariantsResponse> variants = productServiceProvider.getProductVariantsByIds(productVariantsIds);

        Map<Long, ProductVariantsResponse> variantsMap = convertVariantsToMap(variants);

        return cartMapper.toCartResponsePage(carts, variantsMap);
    }

    private UUID getCurrentUserId() {
        return SecurityUtil.getCurrentUserId();
    }

    private Cart findCart(Long cartId) {
        return cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
    }

    private Page<Cart> findAllCart(UUID userId, Pageable pageable) {
        return cartRepository.findByUserId(userId, pageable);
    }

    private Cart findCart(UUID userId, Long productVariantsId) {
        return cartRepository.findByUserIdAndProductVariantsId(userId, productVariantsId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found in cart"));
    }

    private Map<Long, ProductVariantsResponse> convertVariantsToMap(Collection<ProductVariantsResponse> variants) {
        return variants.stream()
                .collect(Collectors.toMap(ProductVariantsResponse::getId, Function.identity()));
    }
}
