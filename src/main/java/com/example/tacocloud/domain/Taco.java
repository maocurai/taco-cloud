package com.example.tacocloud.domain;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Data
@ToString
public class Taco {

    private Long id;

    private Date createdAt;

    @NotNull
    @Size(min=5, message="Name must be at least 3 characters long")
    private String name;

    @Size(min=1, message="You must choose at least 1 ingredient")
    private List<Ingredient> ingredients;

    public Taco() {}
}
