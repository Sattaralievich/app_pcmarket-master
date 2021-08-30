package uz.pdp.app_pcmarket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.app_pcmarket.entity.commentary.Commentary;
import uz.pdp.app_pcmarket.entity.user.User;
import uz.pdp.app_pcmarket.payload.ApiResponse;
import uz.pdp.app_pcmarket.payload.CommentaryDto;
import uz.pdp.app_pcmarket.payload.UserDto;
import uz.pdp.app_pcmarket.service.CommentaryService;
import uz.pdp.app_pcmarket.service.UserService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/user")
public class UserController {
    @Autowired
    UserService userService;

    /**
     * @param userDto
     * @return ResponseEntity<ApiResponse>
     */
    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN','MODERATOR')")
    @PostMapping
    public ResponseEntity<ApiResponse> add(@Valid @RequestBody UserDto userDto) {
        ApiResponse apiResponse = userService.add(userDto);

        return ResponseEntity.status(apiResponse.isStatus() ? 200 : 409).body(apiResponse);
    }

    /**
     * @return ResponseEntity<List < Commentary>>
     */

    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN','MODERATOR','OPERATOR')")
    @GetMapping
    public ResponseEntity<List<User>> getAll() {
        List<User> all = userService.getAll();

        return ResponseEntity.ok(all);
    }

    /**
     * @param id
     * @return ResponseEntity<Commentary>
     */

    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN','MODERATOR','OPERATOR')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<User> getById(@PathVariable Integer id) {
        User user = userService.getById(id);

        return ResponseEntity.ok(user);
    }

    /**
     * @param userDto
     * @param id
     * @return ResponseEntity<ApiResponse>
     */

    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN','MODERATOR')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<ApiResponse> edit(@Valid @RequestBody UserDto userDto, @PathVariable Integer id) {
        ApiResponse apiResponse = userService.edit(userDto, id);

        return ResponseEntity.status(apiResponse.isStatus() ? 201 : 409).body(apiResponse);
    }

    /**
     * @param id
     * @return ResponseEntity<ApiResponse>
     */
    @PreAuthorize(value = "hasRole('SUPER_ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Integer id) {
        ApiResponse apiResponse = userService.delete(id);

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
