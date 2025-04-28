package nashtech.rookie.uniform.cart.internal.mappers;

import nashtech.rookie.uniform.cart.internal.dtos.CartResponse;
import nashtech.rookie.uniform.cart.internal.entities.Cart;
import nashtech.rookie.uniform.product.dto.ProductVariantsResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.Map;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CartMapper {
    CartResponse cartToCartResponse(Cart cart);

    default CartResponse cardToCartResponse(Cart cart, ProductVariantsResponse productVariantsResponse) {
        return CartResponse.builder()
                .id(cart.getId())
                .productVariants(productVariantsResponse)
                .quantity(cart.getQuantity())
                .createdAt(cart.getCreatedAt())
                .build();
    }

    default Page<CartResponse> toCartResponsePage(Page<Cart> carts, Map<Long, ProductVariantsResponse> variantsMap) {
        List<CartResponse> responses = carts.getContent().stream()
                .map(cart -> {
                    ProductVariantsResponse variant = variantsMap.get(cart.getProductVariantsId());
                    return cardToCartResponse(cart, variant);
                }).toList();
        return new PageImpl<>(responses, carts.getPageable(), carts.getTotalElements());
    }
}
