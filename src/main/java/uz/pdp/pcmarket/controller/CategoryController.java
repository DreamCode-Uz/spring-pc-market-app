package uz.pdp.pcmarket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.pcmarket.payload.CategoryDTO;
import uz.pdp.pcmarket.service.CategoryService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    private final CategoryService service;

    @Autowired
    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> customers(@RequestParam(name = "page", defaultValue = "1") Integer page,
                                       @RequestParam(name = "size", defaultValue = "10") Integer size) {
        return service.getAll(page, size);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> customer(@PathVariable Integer id) {
        return service.getOne(id);
    }

    @PostMapping
    public ResponseEntity<?> customer(@Valid @RequestBody CategoryDTO dto) {
        return service.addCategory(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @Valid @RequestBody CategoryDTO dto) {
        return service.editCategory(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        return service.deleteCategory(id);
    }
}
