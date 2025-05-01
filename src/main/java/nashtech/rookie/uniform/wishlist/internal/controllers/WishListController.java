package nashtech.rookie.uniform.wishlist.internal.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nashtech.rookie.uniform.product.dto.ProductGeneralResponse;
import nashtech.rookie.uniform.wishlist.internal.dtos.WishListRequest;
import nashtech.rookie.uniform.wishlist.internal.services.WishListService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/wishlists")
@RequiredArgsConstructor
public class WishListController {
    private final WishListService wishListService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addToWishList(@RequestBody @Valid WishListRequest wishListRequest) {
        wishListService.addProductToWishList(wishListRequest);
    }

    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeFromWishList(@PathVariable UUID productId) {
        wishListService.removeProductFromWishList(productId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<ProductGeneralResponse> getAllWishList(@PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.ASC) Pageable pageable) {
        return wishListService.getAllWishList(pageable);
    }
}
