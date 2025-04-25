package nashtech.rookie.uniform.wishlist.internal.mappers;

import nashtech.rookie.uniform.product.internal.mappers.ProductMapper;
import nashtech.rookie.uniform.wishlist.internal.dtos.WishListResponse;
import nashtech.rookie.uniform.wishlist.internal.entities.WishList;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public interface WishListMapper {
    WishListResponse wishListToWishListResponse(WishList wishList);
}
