package nashtech.rookie.uniform.mappers;

import nashtech.rookie.uniform.dtos.response.WishListResponse;
import nashtech.rookie.uniform.entities.WishList;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public interface WishListMapper {
    WishListResponse wishListToWishListResponse(WishList wishList);
}
