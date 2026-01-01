package com.example.orbitwork.repository;

import com.example.orbitwork.entity.Space;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpaceRepository extends JpaRepository<Space, Long> {

    Page<Space> findByCity(String city, Pageable pageable);

    Page<Space> findByVendor_Id(Long vendorId, Pageable pageable);

    Optional<Space> findByIdAndVendor_Id(Long id, Long vendorId);
}