package uz.pdp.app_pcmarket.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.app_pcmarket.entity.category.Category;
import uz.pdp.app_pcmarket.payload.CategoryDto;
import uz.pdp.app_pcmarket.payload.ApiResponse;
import uz.pdp.app_pcmarket.repository.CategoryRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    public ApiResponse add(CategoryDto categoryDto) {
        boolean existsByName = categoryRepository.existsByName(categoryDto.getName());

        if (existsByName){
            return new ApiResponse("This category already added", false);
        }

        Category category = new Category();
        category.setName(categoryDto.getName());

        if(categoryDto.getParentCategoryId() != null){
            Optional<Category> optionalCategory = categoryRepository.findById(categoryDto.getParentCategoryId());
            if(optionalCategory.isEmpty())
                return new ApiResponse("This category not found",false);

            category.setParentCategory(categoryRepository.getById(categoryDto.getParentCategoryId()));
        }

        categoryRepository.save(category);

        return new ApiResponse("The category added", true);
    }

    public List<Category> getAll() {
        List<Category> all = categoryRepository.findAll();

        return all;
    }

    public Category getById(Integer id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);

        if (optionalCategory.isPresent()) {
            return optionalCategory.get();
        }
        return new Category();
    }

    public ApiResponse edit(CategoryDto categoryDto, Integer id) {

        Optional<Category> optionalCategory = categoryRepository.findById(id);

        if (optionalCategory.isEmpty())
            return new ApiResponse("The category not found", false);

        Category category = optionalCategory.get();
        category.setName(categoryDto.getName());

        if(categoryDto.getParentCategoryId() != null){
            Optional<Category> optional = categoryRepository.findById(categoryDto.getParentCategoryId());
            if(optional.isEmpty())
                return new ApiResponse("This category not found",false);

            category.setParentCategory(categoryRepository.getById(categoryDto.getParentCategoryId()));
        }

        categoryRepository.save(category);

        return new ApiResponse("The category edited", true);


    }

    public ApiResponse delete(Integer id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);

        if (optionalCategory.isEmpty())
            return new ApiResponse("The category not found", false);

        categoryRepository.deleteById(id);
        return new ApiResponse("The category deleted", true);
    }
}
