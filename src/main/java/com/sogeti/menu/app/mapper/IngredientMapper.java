package com.sogeti.menu.app.mapper;

import com.sogeti.menu.app.persistence.entities.IngredientEntity;
import com.sogeti.menu.app.rest.dtos.IngredientDto;
import com.sogeti.menu.app.rest.requests.IngredientRequest;
import com.sogeti.menu.app.rest.responses.IngredientResponse;

public class IngredientMapper {

    public static IngredientDto fromRequestToDto(IngredientRequest ingredientRequest) {
        if (ingredientRequest == null) {
            return null;
        }

        return IngredientDto.builder()
                .name(ingredientRequest.name())
                .quantity(ingredientRequest.quantity())
                .unit(ingredientRequest.unit())
                .build();
    }

    public static IngredientEntity fromDtoToEntity(IngredientDto ingredientDto) {
        if (ingredientDto == null) {
            return null;
        }

        return IngredientEntity.builder()
                .name(ingredientDto.getName())
                .quantity(ingredientDto.getQuantity())
                .unit(ingredientDto.getUnit())
                .build();
    }

    public static IngredientDto fromEntityToDto(IngredientEntity ingredientEntity) {
        if (ingredientEntity == null) {
            return null;
        }

        return IngredientDto.builder()
                .name(ingredientEntity.getName())
                .quantity(ingredientEntity.getQuantity())
                .unit(ingredientEntity.getUnit())
                .build();
    }

    public static IngredientResponse fromDtoToResponse(IngredientDto ingredientDto) {
        if (ingredientDto == null) {
            return null;
        }

        return IngredientResponse.builder()
                .name(ingredientDto.getName())
                .quantity(ingredientDto.getQuantity())
                .unit(ingredientDto.getUnit())
                .build();
    }



}
