package io.tasw.app.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

public interface BusinessForms {

    @Data
    @Builder
    public static final class BusinessForm {

        @NotBlank
        @ApiModelProperty(required = true)
        private final String name;

        @Positive
        @ApiModelProperty(required = true)
        private final int totalEmployees;

    }
    
}
