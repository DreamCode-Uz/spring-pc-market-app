package uz.pdp.pcmarket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.pcmarket.entity.Customer;
import uz.pdp.pcmarket.entity.Product;
import uz.pdp.pcmarket.exception.ConflictException;
import uz.pdp.pcmarket.exception.NotFoundException;
import uz.pdp.pcmarket.payload.CustomerDTO;
import uz.pdp.pcmarket.payload.response.ApiError;
import uz.pdp.pcmarket.repository.CustomerRepository;
import uz.pdp.pcmarket.repository.ProductRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.springframework.http.ResponseEntity.*;

@Service
public class CustomerService {
    private final CustomerRepository repository;
    private final ProductRepository productRepository;

    @Autowired
    public CustomerService(CustomerRepository repository, ProductRepository productRepository) {
        this.repository = repository;
        this.productRepository = productRepository;
    }

    public ResponseEntity<?> getAll(Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page > 0 ? page - 1 : 0, size > 0 ? size : 10);
        return ok(repository.findAll(pageRequest));
    }

    public ResponseEntity<?> getOne(Integer id) {
        Optional<Customer> optionalCustomer = repository.findById(id);
        if (!optionalCustomer.isPresent()) throw new NotFoundException("Customer not found");
        return ok(optionalCustomer.get());
    }

    public ResponseEntity<?> addCustomer(CustomerDTO dto) {
        if (repository.existsByEmail(dto.getEmail())) throw new ConflictException("This email has been registered");
        if (repository.existsByPhoneNumber(dto.getPhoneNumber()))
            throw new ConflictException("This phoneNumber has been registered");
        Set<Product> products = checkProducts(dto.getProductsId());
        if (products.size() == 0) throw new NotFoundException("Products not found");
        return status(HttpStatus.CREATED).body(
                repository.save(new Customer(null, dto.getFullName(), dto.getPhoneNumber(), dto.getEmail(), products)));
    }

    public ResponseEntity<?> editCustomer(Integer id, CustomerDTO dto) {
        Optional<Customer> optionalCustomer = repository.findById(id);
        if (!optionalCustomer.isPresent()) throw new NotFoundException("Customer not found");
        if (repository.existsByEmailAndIdNot(dto.getEmail(), id))
            throw new ConflictException("This email has been registered");
        if (repository.existsByPhoneNumberAndIdNot(dto.getPhoneNumber(), id))
            throw new ConflictException("This phoneNumber has been registered");
        Set<Product> products = checkProducts(dto.getProductsId());
        if (products.size() == 0) throw new NotFoundException("Products not found");
        return status(HttpStatus.CREATED).body(
                repository.save(new Customer(id, dto.getFullName(), dto.getPhoneNumber(), dto.getEmail(), products)));
    }

    public ResponseEntity<?> deleteCustomer(Integer id) {
        Optional<Customer> optionalCustomer = repository.findById(id);
        if (!optionalCustomer.isPresent()) throw new NotFoundException("Cutomer not found");
        try {
            repository.delete(optionalCustomer.get());
        } catch (Exception e) {
            return badRequest().body(new ApiError(HttpStatus.BAD_REQUEST, "Customer could not be deleted", e.getLocalizedMessage()));
        }
        return noContent().build();
    }

    //    ACTION
    private Set<Product> checkProducts(Set<Integer> ids) {
        Set<Product> products = new HashSet<>();
        for (Integer id : ids) {
            Optional<Product> optionalProduct = productRepository.findById(id);
            optionalProduct.ifPresent(products::add);
        }
        return products;
    }
}
