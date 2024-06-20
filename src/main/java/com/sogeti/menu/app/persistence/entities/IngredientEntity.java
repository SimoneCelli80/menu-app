package com.sogeti.menu.app.persistence.entities;

import com.sogeti.menu.app.persistence.enums.UnitsOfMeasureEnum;
import jakarta.persistence.*;
import lombok.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@Builder
public class IngredientEntity {

    private String name;
    private double quantity;
    private UnitsOfMeasureEnum unit;

    public IngredientEntity(String name, double quantity, UnitsOfMeasureEnum unit) {
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
    }

}
