package uz.pdp.pcmarket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.pcmarket.payload.CustomerDTO;
import uz.pdp.pcmarket.service.CustomerService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    private final CustomerService service;

    @Autowired
    public CustomerController(CustomerService service) {
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
    public ResponseEntity<?> customer(@Valid @RequestBody CustomerDTO dto) {
        return service.addCustomer(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @Valid @RequestBody CustomerDTO dto) {
        return service.editCustomer(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        return service.deleteCustomer(id);
    }
}
