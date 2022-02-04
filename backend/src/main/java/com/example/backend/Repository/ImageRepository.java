package com.example.backend.Repository;

import com.example.backend.Beans.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
