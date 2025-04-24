package nashtech.rookie.uniform.wishlist.internal.services;

import lombok.RequiredArgsConstructor;
import nashtech.rookie.uniform.product.entities.Product;
import nashtech.rookie.uniform.user.internal.entities.User;
import nashtech.rookie.uniform.shared.exceptions.BadRequestException;
import nashtech.rookie.uniform.shared.exceptions.ResourceNotFoundException;
import nashtech.rookie.uniform.product.repositories.ProductRepository;
import nashtech.rookie.uniform.shared.utils.SecurityUtil;
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
    private final ProductRepository productRepository;
    private final WishListRepository wishListRepository;
    private final WishListMapper wishListMapper;

    @Transactional
    @Override
    public void addProductToWishList(WishListRequest wishListRequest) {
        User user = getCurrentUser();
        Product product = getProductById(wishListRequest.getProductId());

        if (wishListRepository.existsByUserIdAndProductId(user.getId(), product.getId())) {
            throw new BadRequestException("Product already in wish list");
        }

        WishList wishList = WishList.builder().user(user).product(product).build();
        saveWishList(wishList);
    }

    @Transactional
    @Override
    public void removeProductFromWishList(UUID productId) {
        User user = getCurrentUser();
        Product product = getProductById(productId);

        WishList wishList = wishListRepository.findByUserAndProduct(user, product).orElseThrow(() -> new ResourceNotFoundException("Product not found in wish list"));

        wishListRepository.delete(wishList);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<WishListResponse> getAllWishList(Pageable pageable) {
        User user = getCurrentUser();

        Page<WishList> wishLists = wishListRepository.findAllByUser(user,pageable);

        return wishLists.map(wishListMapper::wishListToWishListResponse);
    }

    private void saveWishList(WishList wishList) {
        wishListRepository.save(wishList);
    }

    private User getCurrentUser() {
        return SecurityUtil.getCurrentUser();
    }

    private Product getProductById(UUID productId) {
        return productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }
}
