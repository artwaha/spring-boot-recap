package com.atwaha.sis.components;

import com.atwaha.sis.model.dto.ApiCollectionResponse;
import com.atwaha.sis.model.dto.ApiResponse;
import com.atwaha.sis.model.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class UtilityMethods {
    public ErrorResponse generateErrorResponse(HttpStatus status, String path, String message, Map<String, String> details) {
        return ErrorResponse
                .builder()
                .path(path)
                .status(status.value())
                .message(message)
                .details(details)
                .build();
    }

    public <T> ApiResponse<T> generateGenericApiResponse(int status, T data) {
        return ApiResponse
                .<T>builder()
                .status(status)
                .data(data)
                .build();
    }

    public <T> ApiCollectionResponse<T> generateGenericApiCollectionResponse(int status, int pageNumber, int pageSize, int totalPages, long totalElements, List<T> data) {
        return ApiCollectionResponse
                .<T>builder()
                .status(status)
                .pageNumber(pageNumber)
                .pageSize(pageSize)
                .totalPages(totalPages)
                .totalElements(totalElements)
                .data(data)
                .build();
    }
}
