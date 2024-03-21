package com.atwaha.sis.service;

import com.atwaha.sis.components.DTOmapper;
import com.atwaha.sis.components.UtilityMethods;
import com.atwaha.sis.model.dto.ApiResponse;
import com.atwaha.sis.model.dto.SchoolRequest;
import com.atwaha.sis.model.dto.SchoolResponse;
import com.atwaha.sis.model.entities.School;
import com.atwaha.sis.repository.SchoolRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SchoolService {
    private final SchoolRepository schoolRepository;
    private final DTOmapper dtOmapper;
    private final UtilityMethods util;

    public ResponseEntity<ApiResponse<SchoolResponse>> addSchool(SchoolRequest school) {
        School savedSchool = schoolRepository.save(dtOmapper.schoolRequestDTOtoSchoolEntity(school));
        SchoolResponse schoolResponse = dtOmapper.schoolEntityToSchoolResponseDTO(savedSchool);
        ApiResponse<SchoolResponse> response = ApiResponse
                .<SchoolResponse>builder()
                .status(HttpStatus.CREATED.value())
                .data(schoolResponse)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    public ResponseEntity<ApiResponse<List<SchoolResponse>>> getAllSchools() {
        List<School> schoolList = schoolRepository.findAll();
        List<SchoolResponse> schoolResponseList = schoolList
                .stream()
                .map(dtOmapper::schoolEntityToSchoolResponseDTO)
                .toList();

        ApiResponse<List<SchoolResponse>> response = ApiResponse
                .<List<SchoolResponse>>builder()
                .status(HttpStatus.OK.value())
                .data(schoolResponseList)
                .build();

        return ResponseEntity.ok(response);
    }


    public ResponseEntity<ApiResponse<SchoolResponse>> updateSchool(Long schoolId, SchoolRequest schoolRequest) {
        School school = schoolRepository.findById(schoolId).orElseThrow(() -> new EntityNotFoundException("Invalid School Id"));

        school.setName(schoolRequest.getName());

        SchoolResponse schoolResponse = dtOmapper.schoolEntityToSchoolResponseDTO(schoolRepository.save(school));

        ApiResponse<SchoolResponse> response = ApiResponse
                .<SchoolResponse>builder()
                .status(HttpStatus.CREATED.value())
                .data(schoolResponse)
                .build();

        return ResponseEntity.ok(response);
    }
}
