package com.example.orbitwork.repository;

import com.example.orbitwork.entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {

    Optional<Room> findByIdAndSpace_IdAndSpace_Vendor_Email(
            Long roomId,
            Long spaceId,
            String vendorEmail
    );

    Page<Room> findBySpace_Id(Long spaceId, Pageable pageable);
}