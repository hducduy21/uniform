package nashtech.rookie.uniform.wishlist.internal.services;

import lombok.RequiredArgsConstructor;
import nashtech.rookie.uniform.application.utils.SecurityUtil;
import nashtech.rookie.uniform.product.api.ProductServiceProvider;
import nashtech.rookie.uniform.product.dto.ProductGeneralResponse;
import nashtech.rookie.uniform.shared.exceptions.BadRequestException;
import nashtech.rookie.uniform.shared.exceptions.ResourceNotFoundException;
import nashtech.rookie.uniform.wishlist.internal.dtos.WishListRequest;
import nashtech.rookie.uniform.wishlist.internal.entities.WishList;
import nashtech.rookie.uniform.wishlist.internal.mappers.WishListMapper;
import nashtech.rookie.uniform.wishlist.internal.repositories.WishListRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WishListImpl implements WishListService {
    private final WishListRepository wishListRepository;
    private final WishListMapper wishListMapper;
    private final ProductServiceProvider productServiceProvider;

    @Transactional
    @Override
    public void addProductToWishList(WishListRequest wishListRequest) {
        UUID userId = getCurrentUserId();

        if(!productServiceProvider.productExists(wishListRequest.getProductId())){
            throw new ResourceNotFoundException("Product not found");
        }

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
    public Page<ProductGeneralResponse> getAllWishList(Pageable pageable) {
        UUID user = getCurrentUserId();

        Collection<WishList> wishLists = wishListRepository.findAllByUserId(user);
        Set<UUID> productIds = wishLists.stream().map(WishList::getProductId).collect(Collectors.toSet());

        return productServiceProvider.getProductsByIds(productIds, pageable);
    }

    private void saveWishList(WishList wishList) {
        wishListRepository.save(wishList);
    }

    private UUID getCurrentUserId() {
        return SecurityUtil.getCurrentUserId();
    }
}
