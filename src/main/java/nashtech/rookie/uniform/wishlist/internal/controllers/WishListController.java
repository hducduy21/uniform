package nashtech.rookie.uniform.wishlist.internal.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nashtech.rookie.uniform.shared.dtos.ApiResponse;
import nashtech.rookie.uniform.shared.utils.ResponseUtil;
import nashtech.rookie.uniform.wishlist.internal.dtos.WishListRequest;
import nashtech.rookie.uniform.wishlist.internal.dtos.WishListResponse;
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
    public ApiResponse<Void> addToWishList(@RequestBody @Valid WishListRequest wishListRequest) {
        wishListService.addProductToWishList(wishListRequest);
        return ResponseUtil.successResponse("Product added to wishlist successfully");
    }

    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ApiResponse<Void> removeFromWishList(@PathVariable UUID productId) {
        wishListService.removeProductFromWishList(productId);
        return ResponseUtil.successResponse("Product removed from wishlist successfully");
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<Page<WishListResponse>> getAllWishList(@PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseUtil.successResponse(wishListService.getAllWishList(pageable));
    }
}
