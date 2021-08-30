package uz.pdp.app_pcmarket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.app_pcmarket.entity.commentary.Commentary;
import uz.pdp.app_pcmarket.payload.ApiResponse;
import uz.pdp.app_pcmarket.payload.CommentaryDto;
import uz.pdp.app_pcmarket.service.CommentaryService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/commentary")
public class CommentaryController {
    @Autowired
    CommentaryService commentaryService;

    /**
     * @param commentaryDto
     * @return ResponseEntity<ApiResponse>
     */
    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN','MODERATOR')")
    @PostMapping
    public ResponseEntity<ApiResponse> add(@Valid @RequestBody CommentaryDto commentaryDto) {
        ApiResponse apiResponse = commentaryService.add(commentaryDto);

        return ResponseEntity.status(apiResponse.isStatus() ? 200 : 409).body(apiResponse);
    }

    /**
     * @return ResponseEntity<List < Commentary>>
     */

    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN','MODERATOR','OPERATOR')")
    @GetMapping
    public ResponseEntity<List<Commentary>> getAll() {
        List<Commentary> all = commentaryService.getAll();

        return ResponseEntity.ok(all);
    }

    /**
     * @param id
     * @return ResponseEntity<Commentary>
     */

    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN','MODERATOR','OPERATOR')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<Commentary> getById(@PathVariable Integer id) {
        Commentary commentary = commentaryService.getById(id);

        return ResponseEntity.ok(commentary);
    }

    /**
     * @param commentaryDto
     * @param id
     * @return ResponseEntity<ApiResponse>
     */

    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN','MODERATOR')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<ApiResponse> edit(@Valid @RequestBody CommentaryDto commentaryDto, @PathVariable Integer id) {
        ApiResponse apiResponse = commentaryService.edit(commentaryDto, id);

        return ResponseEntity.status(apiResponse.isStatus() ? 201 : 409).body(apiResponse);
    }

    /**
     * @param id
     * @return ResponseEntity<ApiResponse>
     */
    @PreAuthorize(value = "hasRole('SUPER_ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Integer id) {
        ApiResponse apiResponse = commentaryService.delete(id);

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
