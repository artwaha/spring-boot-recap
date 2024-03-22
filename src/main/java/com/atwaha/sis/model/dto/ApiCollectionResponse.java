package com.atwaha.sis.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiCollectionResponse<T> {
    private int status;
    private int pageNumber;
    private int pageSize;
    private int totalPages;
    private long totalElements;
    private List<T> data;
}
