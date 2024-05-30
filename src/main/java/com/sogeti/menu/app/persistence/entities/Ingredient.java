package com.sogeti.menu.app.persistence.entities;

import com.sogeti.menu.app.persistence.enums.UnitsOfMeasureEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private String name;
    private double quantity;
    private UnitsOfMeasureEnum unit;

    public Ingredient(String name, double quantity, UnitsOfMeasureEnum unit) {
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
    }

}
