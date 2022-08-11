package uz.pdp.pcmarket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.pcmarket.repository.CategoryRepository;
import uz.pdp.pcmarket.repository.ProductRepository;

import static org.springframework.http.ResponseEntity.ok;

@Service
public class CustomerService {
    private final CategoryRepository repository;
    private final ProductRepository productRepository;

    @Autowired
    public CustomerService(CategoryRepository repository, ProductRepository productRepository) {
        this.repository = repository;
        this.productRepository = productRepository;
    }

    public ResponseEntity<?> getAll(Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page > 0 ? page - 1 : 0, size > 0 ? size : 10);
        return ok(repository.findAll(pageRequest));
    }

    public ResponseEntity<?> getInteger() {
        return null;
    }
}
