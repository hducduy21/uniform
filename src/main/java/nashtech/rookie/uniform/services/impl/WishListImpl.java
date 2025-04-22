package nashtech.rookie.uniform.services.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import nashtech.rookie.uniform.dtos.request.WishListRequest;
import nashtech.rookie.uniform.dtos.response.ProductGeneralResponse;
import nashtech.rookie.uniform.dtos.response.WishListResponse;
import nashtech.rookie.uniform.entities.Product;
import nashtech.rookie.uniform.entities.User;
import nashtech.rookie.uniform.entities.WishList;
import nashtech.rookie.uniform.exceptions.BadRequestException;
import nashtech.rookie.uniform.mappers.ProductMapper;
import nashtech.rookie.uniform.repositories.ProductRepository;
import nashtech.rookie.uniform.repositories.WishListRepository;
import nashtech.rookie.uniform.services.WishListService;
import nashtech.rookie.uniform.utils.SecurityUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WishListImpl implements WishListService {
    private final ProductRepository productRepository;
    private final WishListRepository wishListRepository;
    private final ProductMapper productMapper;

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

        WishList wishList = wishListRepository.findByUserAndProduct(user, product).orElseThrow(() -> new BadRequestException("Product not found in wish list"));

        wishListRepository.delete(wishList);
    }

    @Transactional
    @Override
    public WishListResponse getWishList() {
        User user = getCurrentUser();

        List<WishList> wishLists = wishListRepository.findAllByUser(user);

        List<ProductGeneralResponse> productGeneralResponses = wishLists.stream()
                .map(WishList::getProduct)
                .map(productMapper::productToProductGeneralResponse).toList();

        return WishListResponse.builder().products(productGeneralResponses).build();
    }

    private void saveWishList(WishList wishList) {
        wishListRepository.save(wishList);
    }

    private User getCurrentUser() {
        return SecurityUtil.getCurrentUser();
    }

    private Product getProductById(UUID productId) {
        return productRepository.findById(productId).orElseThrow(() -> new BadRequestException("Product not found"));
    }
}
