package uz.pdp.app_pcmarket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.app_pcmarket.entity.attachment.Attachment;
import uz.pdp.app_pcmarket.entity.category.Category;
import uz.pdp.app_pcmarket.entity.measurement.Measurement;
import uz.pdp.app_pcmarket.entity.product.Product;
import uz.pdp.app_pcmarket.payload.ProductDto;
import uz.pdp.app_pcmarket.payload.ApiResponse;
import uz.pdp.app_pcmarket.repository.*;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    AttachmentRepository attachmentRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    MeasurementRepository measurementRepository;

    public ApiResponse add(ProductDto productDto) {
        boolean existsByCode = productRepository.existsByCode(productDto.getCode());
        if (existsByCode)
            return new ApiResponse("This product already added", false);

        Product product = new Product();

        Optional<Category> optionalCategory = categoryRepository.findById(productDto.getCategoryId());
        if (optionalCategory.isEmpty())
            return new ApiResponse("Category not found", false);

        Optional<Measurement> optionalMeasurement = measurementRepository.findById(productDto.getMeasurementId());
        if (optionalMeasurement.isEmpty())
            return new ApiResponse("Measurement not found", false);

        Optional<Attachment> optionalAttachment = attachmentRepository.findById(productDto.getPhotoId());

        if (optionalAttachment.isEmpty())
            return new ApiResponse("Photo not found", false);


        product.setName(productDto.getName());
        product.setCode(productDto.getCode());
        product.setCategory(optionalCategory.get());
        product.setMeasurement(optionalMeasurement.get());
        product.setPhoto(optionalAttachment.get());
        product.setPrice(productDto.getPrice());
        product.setWarrantyYear(productDto.getWarrantyYear());
        product.setOthers(productDto.getOthers());

        productRepository.save(product);

        return new ApiResponse("Product added", true);
    }

    public List<Product> getAll() {
        List<Product> all = productRepository.findAll();

        return all;
    }

    public Product getById(Integer id) {
        Optional<Product> optionalProduct = productRepository.findById(id);

        if (optionalProduct.isPresent()) {
            return optionalProduct.get();
        }
        return new Product();
    }

    public ApiResponse edit(ProductDto productDto, Integer id) {

        Optional<Product> optionalProduct = productRepository.findById(id);

        if (optionalProduct.isEmpty())
            return new ApiResponse("The product not found", false);

        Product product = optionalProduct.get();
        Optional<Category> optionalCategory = categoryRepository.findById(productDto.getCategoryId());
        if (optionalCategory.isEmpty())
            return new ApiResponse("Category not found", false);

        Optional<Measurement> optionalMeasurement = measurementRepository.findById(productDto.getMeasurementId());
        if (optionalMeasurement.isEmpty())
            return new ApiResponse("Measurement not found", false);

        Optional<Attachment> optionalAttachment = attachmentRepository.findById(productDto.getPhotoId());

        if (optionalAttachment.isEmpty())
            return new ApiResponse("Photo not found", false);


        product.setName(productDto.getName());
        product.setCode(productDto.getCode());
        product.setCategory(optionalCategory.get());
        product.setMeasurement(optionalMeasurement.get());
        product.setPhoto(optionalAttachment.get());
        product.setPrice(productDto.getPrice());
        product.setWarrantyYear(productDto.getWarrantyYear());
        product.setOthers(productDto.getOthers());

        productRepository.save(product);

        return new ApiResponse("The product edited", true);


    }

    public ApiResponse delete(Integer id) {
        Optional<Product> optionalProduct = productRepository.findById(id);

        if (optionalProduct.isEmpty())
            return new ApiResponse("The product not found", false);

        productRepository.deleteById(id);
        return new ApiResponse("The product deleted", true);
    }
}
