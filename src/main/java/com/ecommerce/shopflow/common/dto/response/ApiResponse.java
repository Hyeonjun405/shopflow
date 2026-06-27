package com.ecommerce.shopflow.common.dto.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ApiResponse<T> {

    String errorMessage;
    T data;

    public static <T> ResponseEntity<ApiResponse<T>> success(HttpStatus httpStatus,T data) {
        return ResponseEntity.status(httpStatus)
                .body(ApiResponse.<T>builder()
                    .data(data)
                    .build());
    }

    public static <T> ResponseEntity<ApiResponse<T>> fail(HttpStatus httpStatus, String errorMessage) {
        return ResponseEntity.status(httpStatus)
                .body(ApiResponse.<T>builder()
                        .errorMessage(errorMessage)
                        .build());
    }


}
