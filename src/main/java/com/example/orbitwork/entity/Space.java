package com.example.orbitwork.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "work_space")
public class Space {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(columnDefinition = "text")
    private String description;

    @Column(name = "map_link", nullable = false, columnDefinition = "text")
    private String googleMapLocation;

    @Column(nullable = false, columnDefinition = "text")
    private String city;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id", nullable = false)
    private User vendor;

    @Column(name = "price_per_hour", nullable = false)
    private Integer price;

    @Column(name = "created_at", columnDefinition = "timestamp default current_timestamp")
    private LocalDateTime createdAt;

    @Column(name = "open_time", nullable = false)
    private LocalTime openTime;

    @Column(name = "close_time", nullable = false)
    private LocalTime closeTime;

    @OneToMany(mappedBy = "space", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Room> rooms = new ArrayList<>();

    public void addRoom(Room room) {
        rooms.add(room);
        room.setSpace(this);
    }

    public Space(
            String name, String description, String googleMapLocation, String city,
            User vendor, Integer price, LocalDateTime createdAt, LocalTime openTime, LocalTime closeTime
    ) {
        this.name = name;
        this.description = description;
        this.googleMapLocation = googleMapLocation;
        this.city = city;
        this.vendor = vendor;
        this.price = price;
        this.createdAt = createdAt;
        this.openTime = openTime;
        this.closeTime = closeTime;
    }
}