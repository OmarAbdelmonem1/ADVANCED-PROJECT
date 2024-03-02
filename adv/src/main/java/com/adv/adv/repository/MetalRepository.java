package com.adv.adv.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.adv.adv.model.*;


@Repository
public interface MetalRepository extends JpaRepository<Metal, Long> {
  
}