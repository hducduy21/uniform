package nashtech.rookie.uniform.shared.utils;

import nashtech.rookie.uniform.shared.dtos.ApiResponse;

public class ResponseUtil {
    public static <T> ApiResponse<T> successResponse(String message, T data) {
        return ApiResponse.<T>builder()
                .message(message)
                .success(true)
                .data(data)
                .build();
    }

    public static <T> ApiResponse<T> successResponse(T data) {
        return ApiResponse.<T>builder()
                .success(true)
                .data(data)
                .build();
    }

    public static ApiResponse<Void> successResponse(String message) {
        return ApiResponse.<Void>builder()
                .message(message)
                .success(true)
                .data(null)
                .build();
    }

    public static ApiResponse<Void> errorResponse(int code, String message) {
        return ApiResponse.<Void>builder()
                .message(message)
                .errorCode(code)
                .success(false)
                .data(null)
                .build();
    }

    public static <T> ApiResponse<T> errorResponse(String message, T data) {
        return ApiResponse.<T>builder()
                .message(message)
                .success(true)
                .data(data)
                .build();
    }
}
