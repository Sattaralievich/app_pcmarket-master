package uz.pdp.app_pcmarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.app_pcmarket.entity.product.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    boolean existsByCode(String code);
}
