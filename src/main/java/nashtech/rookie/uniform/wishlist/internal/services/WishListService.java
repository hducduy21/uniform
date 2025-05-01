package nashtech.rookie.uniform.wishlist.internal.services;

import nashtech.rookie.uniform.product.dto.ProductGeneralResponse;
import nashtech.rookie.uniform.wishlist.internal.dtos.WishListRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface WishListService {

    void addProductToWishList(WishListRequest wishListRequest);

    void removeProductFromWishList(UUID productId);

    Page<ProductGeneralResponse> getAllWishList(Pageable pageable);
}
