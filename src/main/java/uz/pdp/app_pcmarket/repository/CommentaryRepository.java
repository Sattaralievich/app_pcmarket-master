package uz.pdp.app_pcmarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.app_pcmarket.entity.commentary.Commentary;

@Repository
public interface CommentaryRepository extends JpaRepository<Commentary, Integer> {

}
