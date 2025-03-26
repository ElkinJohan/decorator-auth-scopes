package com.daicode.authscopes.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiResponseDTO {
    private String message;
    private String executorScope;
    private String decoratorAt;
}
