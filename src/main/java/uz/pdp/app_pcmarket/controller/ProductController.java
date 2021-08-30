package uz.pdp.app_pcmarket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.app_pcmarket.entity.product.Product;
import uz.pdp.app_pcmarket.payload.ProductDto;
import uz.pdp.app_pcmarket.payload.ApiResponse;
import uz.pdp.app_pcmarket.service.ProductService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/product")
public class ProductController {
    @Autowired
    ProductService productService;

    /**
     * @param productDto
     * @return ResponseEntity<ApiResponse>
     */
    @PostMapping
    public ResponseEntity<ApiResponse> add(@Valid @RequestBody ProductDto productDto) {
        ApiResponse apiResponse = productService.add(productDto);

        return ResponseEntity.status(apiResponse.isStatus() ? 200 : 409).body(apiResponse);
    }

    /**
     * @return ResponseEntity<List < Commentary>>
     */

    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN','MODERATOR','OPERATOR')")
    @GetMapping
    public ResponseEntity<List<Product>> getAll() {
        List<Product> all = productService.getAll();

        return ResponseEntity.ok(all);
    }

    /**
     * @param id
     * @return ResponseEntity<Product>
     */

    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN','MODERATOR','OPERATOR')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<Product> getById(@PathVariable Integer id) {
        Product product = productService.getById(id);

        return ResponseEntity.ok(product);
    }

    /**
     * @param productDto
     * @param id
     * @return ResponseEntity<ApiResponse>
     */

    @PutMapping(value = "/{id}")
    public ResponseEntity<ApiResponse> edit(@Valid @RequestBody ProductDto productDto, @PathVariable Integer id) {
        ApiResponse apiResponse = productService.edit(productDto, id);

        return ResponseEntity.status(apiResponse.isStatus() ? 201 : 409).body(apiResponse);
    }

    /**
     * @param id
     * @return ResponseEntity<ApiResponse>
     */
    @PreAuthorize(value = "hasRole('SUPER_ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Integer id) {
        ApiResponse apiResponse = productService.delete(id);

        return ResponseEntity.status(apiResponse.isStatus() ? 200 : 409).body(apiResponse);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
