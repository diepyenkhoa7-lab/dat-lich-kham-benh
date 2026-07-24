package com.datlichkhambenh.backend.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

/**
 * Generic API response wrapper.
 * Moi endpoint deu tra ve dang chuan nay de frontend de xu ly.
 *
 * Thanh cong:  { "success": true,  "data": {...}, "message": null }
 * That bai:    { "success": false, "data": null,  "message": "Loi..." }
 *
 * Su dung static factory thay vi @Builder de tranh van de Lombok generic type.
 */
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponseDto<T> {

    private final boolean success;
    private final T data;
    private final String message;

    private ApiResponseDto(boolean success, T data, String message) {
        this.success = success;
        this.data = data;
        this.message = message;
    }

    public static <T> ApiResponseDto<T> ok(T data) {
        return new ApiResponseDto<>(true, data, null);
    }

    public static <T> ApiResponseDto<T> ok(T data, String message) {
        return new ApiResponseDto<>(true, data, message);
    }

    public static <T> ApiResponseDto<T> error(String message) {
        return new ApiResponseDto<>(false, null, message);
    }
}
