package uz.pdp.pcmarket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.pcmarket.entity.Category;
import uz.pdp.pcmarket.exception.NotFoundException;
import uz.pdp.pcmarket.payload.CategoryDTO;
import uz.pdp.pcmarket.payload.response.ApiError;
import uz.pdp.pcmarket.repository.CategoryRepository;

import java.util.Optional;

import static org.springframework.http.ResponseEntity.*;

@Service
public class CategoryService {
    private final CategoryRepository repository;

    @Autowired
    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    public ResponseEntity<?> getAll(Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page > 0 ? page - 1 : 0, size > 0 ? size : 10);
        return ok(repository.findAll(pageRequest));
    }

    public ResponseEntity<?> getOne(Integer id) {
        Optional<Category> optionalCategory = repository.findById(id);
        if (!optionalCategory.isPresent()) throw new NotFoundException("Category not found");
        return ok(optionalCategory.get());
    }

    public ResponseEntity<?> addCategory(CategoryDTO dto) {
        Category category = new Category();
        category.setName(dto.getName());
        if (dto.getCategoryId() != null) {
            Optional<Category> optionalCategory = repository.findById(dto.getCategoryId());
            optionalCategory.ifPresent(category::setCategory);
        }
        return status(HttpStatus.CREATED).body(repository.save(category));
    }

    public ResponseEntity<?> editCategory(Integer id, CategoryDTO dto) {
        Optional<Category> optionalCategory = repository.findById(id);
        if (!optionalCategory.isPresent()) throw new NotFoundException("Category not found");
        Category category = optionalCategory.get();
        if (dto.getCategoryId() != null && repository.existsById(dto.getCategoryId()))
            category.setCategory(repository.getReferenceById(dto.getCategoryId()));
        category.setActive(dto.isActive());
        category.setName(dto.getName());
        return ok(repository.save(category));
    }

    public ResponseEntity<?> deleteCategory(Integer id) {
        Optional<Category> op = repository.findById(id);
        if (!op.isPresent()) throw new NotFoundException("Category not found");
        try {
            repository.delete(op.get());
        } catch (Exception e) {
            return badRequest().body(new ApiError(HttpStatus.BAD_REQUEST, "Category could not be deleted", e.getLocalizedMessage()));
        }
        return noContent().build();
    }
}
