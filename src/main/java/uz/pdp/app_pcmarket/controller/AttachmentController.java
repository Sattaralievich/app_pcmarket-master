package uz.pdp.app_pcmarket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.pdp.app_pcmarket.entity.attachment.Attachment;
import uz.pdp.app_pcmarket.payload.ApiResponse;
import uz.pdp.app_pcmarket.service.AttachmentService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/api/attachment")
public class AttachmentController {

    @Autowired
    AttachmentService attachmentService;

    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN','MODERATOR')")
    @PostMapping(value = "/upload")
    public ResponseEntity<ApiResponse> uploadFile(MultipartHttpServletRequest request) throws IOException {
        ApiResponse apiResponse = attachmentService.uploadFile(request);

        return ResponseEntity.status(apiResponse.isStatus() ? 200 : 409).body(apiResponse);
    }


    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN','MODERATOR')")
    @GetMapping(value = "/download/{id}")
    public void downloadFile(@PathVariable Integer id, HttpServletResponse response) throws IOException {
        attachmentService.downloadFile(id, response);
    }

    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN','MODERATOR','OPERATOR')")
    @GetMapping(value = "/info")
    public ResponseEntity<List<Attachment>> getAll() {
        List<Attachment> all = attachmentService.getAll();

        return ResponseEntity.ok(all);
    }

    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN','MODERATOR','OPERATOR')")
    @GetMapping(value = "/info/{id}")
    public Attachment getById(@PathVariable Integer id) {
        Attachment attachment = attachmentService.getById(id);

        return attachment;
    }

    @PreAuthorize(value = "hasRole('SUPER_ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Integer id) {
        ApiResponse apiResponse = attachmentService.deleteById(id);

        return ResponseEntity.status(apiResponse.isStatus() ? 200 : 409).body(apiResponse);
    }

}
