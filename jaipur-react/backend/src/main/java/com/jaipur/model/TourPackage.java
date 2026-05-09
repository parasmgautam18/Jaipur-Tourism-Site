package com.jaipur.model;

import jakarta.persistence.*;

@Entity
public class TourPackage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String title;
    
    @Column(columnDefinition="TEXT")
    private String description;
    
    private String duration;
    
    private Integer price;
    
    private String imageUrl;
    
    // "EXCLUSIVE" or "CUSTOM"
    private String packageType;

    public TourPackage() {
    }

    public TourPackage(String title, String description, String duration, Integer price, String imageUrl, String packageType) {
        this.title = title;
        this.description = description;
        this.duration = duration;
        this.price = price;
        this.imageUrl = imageUrl;
        this.packageType = packageType;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getDuration() { return duration; }
    public void setDuration(String duration) { this.duration = duration; }

    public Integer getPrice() { return price; }
    public void setPrice(Integer price) { this.price = price; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getPackageType() { return packageType; }
    public void setPackageType(String packageType) { this.packageType = packageType; }
}
