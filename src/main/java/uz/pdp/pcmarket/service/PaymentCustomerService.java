package uz.pdp.pcmarket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.pcmarket.entity.Customer;
import uz.pdp.pcmarket.entity.PaymentCustomer;
import uz.pdp.pcmarket.entity.Product;
import uz.pdp.pcmarket.exception.NotFoundException;
import uz.pdp.pcmarket.payload.PayCusDTO;
import uz.pdp.pcmarket.payload.response.ApiError;
import uz.pdp.pcmarket.repository.CustomerRepository;
import uz.pdp.pcmarket.repository.PaymentCustomerRepository;
import uz.pdp.pcmarket.repository.ProductRepository;

import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.springframework.http.ResponseEntity.*;

@Service
public class PaymentCustomerService {
    private final PaymentCustomerRepository repository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    @Autowired
    public PaymentCustomerService(PaymentCustomerRepository repository, CustomerRepository customerRepository, ProductRepository productRepository) {
        this.repository = repository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
    }

    public ResponseEntity<?> getAll(Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page > 0 ? page - 1 : 0, size > 0 ? size : 10);
        return ok(repository.findAll(pageRequest));
    }

    public ResponseEntity<?> getOne(Integer id) {
        Optional<PaymentCustomer> optionalPaymentCustomer = repository.findById(id);
        if (!optionalPaymentCustomer.isPresent()) throw new NotFoundException("Payment customer not found");
        return ok(optionalPaymentCustomer.get());
    }

    public ResponseEntity<?> addPayment(PayCusDTO dto) {
        Optional<Customer> optionalCustomer = customerRepository.findById(dto.getCustomerId());
        if (!optionalCustomer.isPresent()) throw new NotFoundException("Customer not found");
        Set<Product> products = checkProduct(dto.getProductsId());
        if (products.size() == 0) throw new NotFoundException("Products not found");
        if (dto.getPrice() < 0)
            badRequest().body(new ApiError(HttpStatus.BAD_REQUEST, "Invalid price entered.", "The payment amount was entered incorrectly"));
        PaymentCustomer save = repository.save(new PaymentCustomer(null, optionalCustomer.get(), products, new Date(), dto.getPrice()));
        return status(HttpStatus.CREATED).body(save);
    }


//     TO'LOV AMALGA OSHIRILGANDAN SO'NG TO'LOV TARIXINI TAXRIRLASH VA O'CHIRISH XATO ISH => BUNI FAQAT VAZIFA UCHUN QILDIM
    public ResponseEntity<?> editPayment(Integer id, PayCusDTO dto) {
        Optional<PaymentCustomer> opc = repository.findById(id);
        if (!opc.isPresent()) throw new NotFoundException("Payment Customer not found");
        Optional<Customer> optionalCustomer = customerRepository.findById(dto.getCustomerId());
        if (!optionalCustomer.isPresent()) throw new NotFoundException("Customer not found");
        Set<Product> products = checkProduct(dto.getProductsId());
        if (products.size() == 0) throw new NotFoundException("Products not found");
        if (dto.getPrice() < 0)
            badRequest().body(new ApiError(HttpStatus.BAD_REQUEST, "Invalid price entered.", "The payment amount was entered incorrectly"));
        PaymentCustomer save = repository.save(new PaymentCustomer(id, optionalCustomer.get(), products, opc.get().getCreatedDate(), dto.getPrice()));
        return status(HttpStatus.CREATED).body(save);
    }

    public ResponseEntity<?> deletePayment(Integer id) {
        Optional<PaymentCustomer> opc = repository.findById(id);
        if (!opc.isPresent()) throw new NotFoundException("Payment customer not found");
        try {
            repository.delete(opc.get());
            return noContent().build();
        } catch (Exception e) {
            return badRequest().body(new ApiError(HttpStatus.BAD_REQUEST, "Payment customer could not be deleted", e.getLocalizedMessage()));
        }
    }

    //    ACTION
    public Set<Product> checkProduct(Set<Integer> ids) {
        Set<Product> products = new HashSet<>();
        for (Integer id : ids) {
            Optional<Product> optionalProduct = productRepository.findById(id);
            optionalProduct.ifPresent(products::add);
        }
        return products;
    }
}
