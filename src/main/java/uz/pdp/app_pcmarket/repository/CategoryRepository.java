package uz.pdp.app_pcmarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.app_pcmarket.entity.category.Category;

public interface CategoryRepository extends JpaRepository<Category,Integer> {
    boolean existsByName(String name);
}
