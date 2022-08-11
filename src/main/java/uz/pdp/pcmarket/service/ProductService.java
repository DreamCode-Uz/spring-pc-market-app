package uz.pdp.pcmarket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.pcmarket.entity.Currency;
import uz.pdp.pcmarket.entity.Measurement;
import uz.pdp.pcmarket.entity.Product;
import uz.pdp.pcmarket.exception.NotFoundException;
import uz.pdp.pcmarket.payload.ProductDTO;
import uz.pdp.pcmarket.payload.response.ApiError;
import uz.pdp.pcmarket.repository.CurrencyRepository;
import uz.pdp.pcmarket.repository.MeasurementRepository;
import uz.pdp.pcmarket.repository.ProductRepository;

import java.util.Optional;

import static org.springframework.http.ResponseEntity.*;

@Service
public class ProductService {
    private final ProductRepository repository;
    private final CurrencyRepository currencyRepository;
    private final MeasurementRepository measurementRepository;

    @Autowired
    public ProductService(ProductRepository repository, CurrencyRepository currencyRepository, MeasurementRepository measurementRepository) {
        this.repository = repository;
        this.currencyRepository = currencyRepository;
        this.measurementRepository = measurementRepository;
    }

    public ResponseEntity<?> getAll(Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page > 0 ? page - 1 : 0, size > 0 ? size : 10);
        return ok(repository.findAll(pageRequest));
    }

    public ResponseEntity<?> getOne(Integer id) {
        Optional<Product> optionalProduct = repository.findById(id);
        if (!optionalProduct.isPresent()) throw new NotFoundException("Product not found");
        return ok(optionalProduct.get());
    }

    public ResponseEntity<?> addProduct(ProductDTO dto) {
        Optional<Currency> optionalCurrency = currencyRepository.findById(dto.getCurrencyId());
        if (!optionalCurrency.isPresent()) throw new NotFoundException("Currency not found");
        Optional<Measurement> optionalMeasurement = measurementRepository.findById(dto.getMeasurementId());
        if (!optionalMeasurement.isPresent()) throw new NotFoundException("Measurement not found");
        Product product = new Product(dto.getName(), dto.isActive(), dto.getPrice(), dto.getAmount(), optionalCurrency.get(), optionalMeasurement.get());
        return status(HttpStatus.CREATED).body(repository.save(product));
    }

    public ResponseEntity<?> editProduct(Integer id, ProductDTO dto) {
        Optional<Product> optionalProduct = repository.findById(id);
        if (!optionalProduct.isPresent()) throw new NotFoundException("Product not found");
        Optional<Currency> optionalCurrency = currencyRepository.findById(dto.getCurrencyId());
        if (!optionalCurrency.isPresent()) throw new NotFoundException("Currency not found");
        Optional<Measurement> optionalMeasurement = measurementRepository.findById(dto.getMeasurementId());
        if (!optionalMeasurement.isPresent()) throw new NotFoundException("Measurement not found");
        Product product = new Product(id, dto.getName(), dto.isActive(), dto.getPrice(), dto.getAmount(), optionalCurrency.get(), optionalMeasurement.get());
        return ok(repository.save(product));
    }

    public ResponseEntity<?> delete(Integer id) {
        Optional<Product> optionalProduct = repository.findById(id);
        if (!optionalProduct.isPresent()) throw new NotFoundException("Product not found");
        try {
            repository.delete(optionalProduct.get());
        } catch (Exception e) {
            return badRequest().body(new ApiError(HttpStatus.BAD_REQUEST, "Product could not be deleted", e.getLocalizedMessage()));
        }
        return noContent().build();
    }
}
