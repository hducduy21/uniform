package nashtech.rookie.uniform.services;

import nashtech.rookie.uniform.dtos.request.WishListRequest;
import nashtech.rookie.uniform.dtos.response.WishListResponse;

import java.util.UUID;

public interface WishListService {

    void addProductToWishList(WishListRequest wishListRequest);

    void removeProductFromWishList(UUID productId);

    WishListResponse getWishList();
}
