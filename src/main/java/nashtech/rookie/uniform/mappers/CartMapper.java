package nashtech.rookie.uniform.mappers;

import nashtech.rookie.uniform.dtos.response.CartResponse;
import nashtech.rookie.uniform.entities.Cart;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ProductVariantsMapper.class})
public interface CartMapper {
    CartResponse cartToCartResponse(Cart cart);
}
