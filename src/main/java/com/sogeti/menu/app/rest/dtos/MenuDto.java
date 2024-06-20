package com.sogeti.menu.app.rest.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class MenuDto {

    private long id;
    private Date menuDate;
    private List<RecipeDto> recipeDtoList;

}