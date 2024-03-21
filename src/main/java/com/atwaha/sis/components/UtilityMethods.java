package com.atwaha.sis.components;

import com.atwaha.sis.model.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

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

}
