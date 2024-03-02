package com.adv.adv.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.adv.adv.model.*;


@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Product deleteById(int id);
     Product findById(int id);
}
