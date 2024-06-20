package com.sogeti.menu.app.rest.requests;

import com.sogeti.menu.app.persistence.enums.UnitsOfMeasureEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record IngredientRequest(
        @NotBlank(message = "Please enter the name of the ingredient.")
        String name,
        @NotNull(message = "Please enter the quantity of the ingredient.")
        double quantity,
        @NotNull(message = "Please select the unit of measure of the ingredient")
        UnitsOfMeasureEnum unit
) {
}
