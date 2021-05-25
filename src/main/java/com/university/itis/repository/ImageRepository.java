package com.university.itis.repository;

import com.university.itis.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    @Override
    void deleteById(Long aLong);
}
