package nashtech.rookie.uniform.mappers;

import nashtech.rookie.uniform.dtos.response.CartResponse;
import nashtech.rookie.uniform.entities.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {ProductVariantsMapper.class})
public interface CartMapper {
    CartMapper INSTANCE = Mappers.getMapper(CartMapper.class);

    CartResponse cartToCartResponse(Cart cart);
}
