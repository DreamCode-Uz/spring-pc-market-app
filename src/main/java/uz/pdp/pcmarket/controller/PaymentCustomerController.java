package uz.pdp.pcmarket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.pcmarket.payload.PayCusDTO;
import uz.pdp.pcmarket.service.PaymentCustomerService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/payment/customer")
public class PaymentCustomerController {
    private final PaymentCustomerService service;

    @Autowired
    public PaymentCustomerController(PaymentCustomerService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam(name = "page", defaultValue = "1") Integer page,
                                    @RequestParam(name = "size", defaultValue = "10") Integer size) {
        return service.getAll(page, size);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Integer id) {
        return service.getOne(id);
    }

    @PostMapping
    public ResponseEntity<?> getOne(@Valid @RequestBody PayCusDTO payCusDTO) {
        return service.addPayment(payCusDTO);
    }

    //     TO'LOV AMALGA OSHIRILGANDAN SO'NG TO'LOV TARIXINI TAXRIRLASH VA O'CHIRISH XATO ISH => BUNI FAQAT VAZIFA UCHUN QILDIM
    /*
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @Valid @RequestBody PayCusDTO payCusDTO) {
        return service.editPayment(id, payCusDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        return service.getOne(id);
    }
    */
}
