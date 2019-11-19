package io.tasw.app.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

public interface BusinessForms {

    @Data
    public final class CreateBusiness {

        @NotBlank
        @ApiModelProperty(required = true)
        private final String name;

        @Positive
        @ApiModelProperty(required = true)
        private final int totalEmployees;

    }
    
}
