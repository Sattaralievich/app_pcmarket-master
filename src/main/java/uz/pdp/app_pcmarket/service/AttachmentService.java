package uz.pdp.app_pcmarket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.pdp.app_pcmarket.entity.attachment.Attachment;
import uz.pdp.app_pcmarket.entity.attachment.AttachmentContent;
import uz.pdp.app_pcmarket.payload.ApiResponse;
import uz.pdp.app_pcmarket.repository.AttachmentContentRepository;
import uz.pdp.app_pcmarket.repository.AttachmentRepository;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class AttachmentService {
    @Autowired
    AttachmentRepository attachmentRepository;
    @Autowired
    AttachmentContentRepository attachmentContentRepository;

    public ApiResponse uploadFile(MultipartHttpServletRequest request) throws IOException {

        Iterator<String> fileNames = request.getFileNames();
        MultipartFile file = request.getFile(fileNames.next());

        if (file != null) {
            String originalFilename = file.getOriginalFilename();
            long size = file.getSize();
            String contentType = file.getContentType();


            Attachment attachment = new Attachment();
            attachment.setOriginalFileName(originalFilename);
            attachment.setSize(size);
            attachment.setContentType(contentType);

            Attachment savedAttachment = attachmentRepository.save(attachment);

            AttachmentContent attachmentContent = new AttachmentContent();
            attachmentContent.setBytes(file.getBytes());
            attachmentContent.setAttachment(savedAttachment);

            attachmentContentRepository.save(attachmentContent);

            return new ApiResponse("Attachment added", true, "File ID : " + attachment.getId());

        }
        return new ApiResponse("ERROR", false, null);
    }

    public void downloadFile(Integer id, HttpServletResponse response) throws IOException {
        Optional<Attachment> optionalAttachment = attachmentRepository.findById(id);

        if (optionalAttachment.isPresent()) {
            Attachment attachment = optionalAttachment.get();
            Optional<AttachmentContent> contentOptional = attachmentContentRepository.findByAttachmentId(id);

            if (contentOptional.isPresent()) {
                AttachmentContent attachmentContent = contentOptional.get();

                response.setHeader(
                        "Content-Disposition",
                        "attachment; filename=\"" + attachment.getOriginalFileName() + "\"");

                response.setContentType(attachment.getContentType());

                FileCopyUtils.copy(attachmentContent.getBytes(), response.getOutputStream());

            }
        }
    }

    public List<Attachment> getAll() {
        List<Attachment> all = attachmentRepository.findAll();

        return all;
    }

    public Attachment getById(Integer id) {
        Optional<Attachment> optionalAttachment = attachmentRepository.findById(id);

        if (optionalAttachment.isPresent()) {
            Attachment attachment = optionalAttachment.get();

            return attachment;
        }

        return new Attachment();
    }

    public ApiResponse deleteById(Integer id) {
        Optional<Attachment> optionalAttachment = attachmentRepository.findById(id);
        if (optionalAttachment.isEmpty())
            return new ApiResponse("This photo not found", false);

        attachmentRepository.deleteById(id);

        return new ApiResponse("Photo deleted", true);
    }

}
