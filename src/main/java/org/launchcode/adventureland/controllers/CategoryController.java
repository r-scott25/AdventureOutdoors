package org.launchcode.adventureland.controllers;

import org.launchcode.adventureland.models.CatData;
import org.launchcode.adventureland.models.Equipment;
import org.launchcode.adventureland.models.data.CategoryRepository;
import org.launchcode.adventureland.models.Category;
import org.launchcode.adventureland.models.data.EquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping(value = "categories")
public class CategoryController{

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private EquipmentRepository equipmentRepository;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("title", "All Categories");
        model.addAttribute("categories", categoryRepository.findAll());

        return "categories/index";
    }

    // this will need items displayed below
    @GetMapping("/{categoryId}")
    public String displayViewCategory(Model model, @PathVariable  Integer categoryId) {

        Optional optCategory = categoryRepository.findById(categoryId);
        if (optCategory.isPresent()) {
            Category category = (Category) optCategory.get();
            Iterable<Equipment> equipmentInCategory;

            String value = category.toString();
            equipmentInCategory = CatData.findByValue(value, equipmentRepository.findAll());
            model.addAttribute("category", category);
            model.addAttribute("title", "Equipment in " + value);
            model.addAttribute("equipments", equipmentInCategory);

            return "catView";
        } else {
            return "redirect:../";
        }
    }

}