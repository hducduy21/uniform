package nashtech.rookie.uniform.services;

import nashtech.rookie.uniform.dtos.request.WishListRequest;
import nashtech.rookie.uniform.dtos.response.WishListResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface WishListService {

    void addProductToWishList(WishListRequest wishListRequest);

    void removeProductFromWishList(UUID productId);

    Page<WishListResponse> getAllWishList(Pageable pageable);
}
