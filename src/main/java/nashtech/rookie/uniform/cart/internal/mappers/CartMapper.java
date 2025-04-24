package nashtech.rookie.uniform.cart.internal.mappers;

import nashtech.rookie.uniform.cart.internal.dtos.CartResponse;
import nashtech.rookie.uniform.cart.internal.entities.Cart;
import nashtech.rookie.uniform.product.mappers.ProductVariantsMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ProductVariantsMapper.class})
public interface CartMapper {
    CartResponse cartToCartResponse(Cart cart);
}
