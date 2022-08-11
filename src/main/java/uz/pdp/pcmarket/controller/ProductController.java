package uz.pdp.pcmarket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.pcmarket.payload.ProductDTO;
import uz.pdp.pcmarket.service.ProductService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    private final ProductService service;

    @Autowired
    public ProductController(ProductService service) {
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
    public ResponseEntity<?> customer(@Valid @RequestBody ProductDTO dto) {
        return service.addProduct(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @Valid @RequestBody ProductDTO dto) {
        return service.editProduct(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        return service.delete(id);
    }
}
