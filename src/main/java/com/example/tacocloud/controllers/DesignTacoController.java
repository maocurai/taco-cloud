package com.example.tacocloud.controllers;

import com.example.tacocloud.domain.Ingredient;
import com.example.tacocloud.domain.Order;
import com.example.tacocloud.domain.Taco;
import com.example.tacocloud.repo.IngredientRepository;
import com.example.tacocloud.repo.TacoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import com.example.tacocloud.domain.Ingredient.Type;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("order")
public class DesignTacoController {

    private final IngredientRepository ingredientRepository;

    private TacoRepository designRepo;

    @Autowired
    public DesignTacoController(IngredientRepository ingredientRepository, TacoRepository tacoRepository) {
        this.ingredientRepository = ingredientRepository;
        this.designRepo = tacoRepository;
    }

    @GetMapping
    public String showDesignForm(Model model) {
        List<Ingredient> ingredients = new ArrayList<>();
        ingredientRepository.findAll().forEach(ingredients::add);

        Type[] types = Type.values();
        for(Type type : types) {
            model.addAttribute(type.toString().toLowerCase(),
                    filterByType(ingredients, type));
        }

        model.addAttribute("design", new Taco());
        return "design";
    }

    private List<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
        return ingredients.stream().filter(x -> x.getType().equals(type)).collect(Collectors.toList());
    }

    @ModelAttribute(name = "order")
    public Order order() {
        return new Order();
    }

    @ModelAttribute(name = "taco")
    public Taco taco() {
        return new Taco();
    }

    @PostMapping()
    public String processDesign(@Valid Taco design, Errors errors, Order order) {
        if(errors.hasErrors()) {
            return "redirect:/design";
        }

        Taco saved = designRepo.save(design);
        order.addDesign(saved);
        log.info("Processing design: " + design);
        return "redirect:/orders/current";
    }
}
