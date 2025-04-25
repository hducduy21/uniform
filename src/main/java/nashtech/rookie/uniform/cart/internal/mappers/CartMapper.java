package nashtech.rookie.uniform.cart.internal.mappers;

import nashtech.rookie.uniform.cart.internal.dtos.CartResponse;
import nashtech.rookie.uniform.cart.internal.entities.Cart;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartMapper {
    CartResponse cartToCartResponse(Cart cart);
}
