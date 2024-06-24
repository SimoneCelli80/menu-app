package com.sogeti.menu.app.persistence.entities;

import com.sogeti.menu.app.persistence.enums.UnitsOfMeasureEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@Builder
public class IngredientEntity {
    @NotNull(message = "Please enter the name of the ingredient.")
    @NotBlank(message = "Please enter the name of the ingredient.")
    private String name;
    @NotNull(message = "Please enter the quantity of the ingredient.")
    private double quantity;
    @NotNull(message = "Please select the unit of measure of the ingredient")
    private UnitsOfMeasureEnum unit;

    public IngredientEntity(String name, double quantity, UnitsOfMeasureEnum unit) {
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
    }

}
