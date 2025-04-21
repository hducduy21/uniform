package nashtech.rookie.uniform.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nashtech.rookie.uniform.dtos.request.WishListRequest;
import nashtech.rookie.uniform.dtos.response.ApiResponse;
import nashtech.rookie.uniform.dtos.response.WishListResponse;
import nashtech.rookie.uniform.services.WishListService;
import nashtech.rookie.uniform.utils.ResponseUtil;
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
    public ApiResponse<WishListResponse> getWishList() {
        return ResponseUtil.successResponse(wishListService.getWishList());
    }
}
