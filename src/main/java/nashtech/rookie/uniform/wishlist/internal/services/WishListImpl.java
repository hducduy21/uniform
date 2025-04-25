package nashtech.rookie.uniform.wishlist.internal.services;

import lombok.RequiredArgsConstructor;
import nashtech.rookie.uniform.application.utils.SecurityUtil;
import nashtech.rookie.uniform.shared.exceptions.BadRequestException;
import nashtech.rookie.uniform.shared.exceptions.ResourceNotFoundException;
import nashtech.rookie.uniform.wishlist.internal.dtos.WishListRequest;
import nashtech.rookie.uniform.wishlist.internal.dtos.WishListResponse;
import nashtech.rookie.uniform.wishlist.internal.entities.WishList;
import nashtech.rookie.uniform.wishlist.internal.mappers.WishListMapper;
import nashtech.rookie.uniform.wishlist.internal.repositories.WishListRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WishListImpl implements WishListService {
    private final WishListRepository wishListRepository;
    private final WishListMapper wishListMapper;

    @Transactional
    @Override
    public void addProductToWishList(WishListRequest wishListRequest) {
        UUID userId = getCurrentUserId();

        // client: fetch to get products

        if (wishListRepository.existsByUserIdAndProductId(userId, wishListRequest.getProductId())) {
            throw new BadRequestException("Product already in wish list");
        }

        WishList wishList = WishList.builder().userId(userId).productId(wishListRequest.getProductId()).build();
        saveWishList(wishList);
    }

    @Transactional
    @Override
    public void removeProductFromWishList(UUID productId) {
        UUID userId = getCurrentUserId();

        WishList wishList = wishListRepository.findByUserIdAndProductId(userId, productId).orElseThrow(() -> new ResourceNotFoundException("Product not found in wish list"));

        wishListRepository.delete(wishList);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<WishListResponse> getAllWishList(Pageable pageable) {
        UUID user = getCurrentUserId();

        Page<WishList> wishLists = wishListRepository.findAllByUserId(user,pageable);

        return wishLists.map(wishListMapper::wishListToWishListResponse);
    }

    private void saveWishList(WishList wishList) {
        wishListRepository.save(wishList);
    }

    private UUID getCurrentUserId() {
        return SecurityUtil.getCurrentUserId();
    }
}
