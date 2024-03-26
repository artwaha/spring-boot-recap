package com.atwaha.sis.components;

import com.atwaha.sis.model.dto.ApiCollectionResponse;
import com.atwaha.sis.model.dto.ApiResponse;
import com.atwaha.sis.model.dto.ErrorResponse;
import com.atwaha.sis.model.entities.Token;
import com.atwaha.sis.model.entities.User;
import com.atwaha.sis.model.enums.TokenType;
import com.atwaha.sis.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class Utils {
    private final TokenRepository tokenRepository;
    Logger logger = LoggerFactory.getLogger(getClass());

    public void saveUserToken(String token, User user) {
        Token newToken = Token
                .builder()
                .token(token)
                .user(user).
                tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(newToken);
    }

    public void revokeUserTokens(User user) {
        List<Token> validTokens = tokenRepository.findByUserAndExpiredFalseOrRevokedFalse(user);

        if (validTokens.isEmpty()) {
            return;
        }

        validTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });

        tokenRepository.saveAll(validTokens);
    }

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
