package com.sogeti.menu.app.rest.dtos;

import com.sogeti.menu.app.persistence.enums.UnitsOfMeasureEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class IngredientDto {

    private String name;
    private double quantity;
    private UnitsOfMeasureEnum unit;

}
