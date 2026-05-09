package com.jaipur;

import com.jaipur.model.TourPackage;
import com.jaipur.repository.TourPackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Map;

@RestController
public class TourPackageController {

    @Autowired
    private TourPackageRepository tourPackageRepository;

    private boolean isAdmin(Object principal) {
        if (principal == null) return false;
        String email = null;
        if (principal instanceof OAuth2User) {
            email = ((OAuth2User) principal).getAttribute("email");
        } else if (principal instanceof org.springframework.security.core.userdetails.UserDetails) {
            email = ((org.springframework.security.core.userdetails.UserDetails) principal).getUsername();
        } else if (principal instanceof String) {
            email = (String) principal;
        }
        return email != null && "jaipur.tourism.official@gmail.com".equalsIgnoreCase(email.trim());
    }

    @GetMapping("/api/packages")
    public List<TourPackage> getAllPackages() {
        return tourPackageRepository.findAll();
    }

    @PostMapping("/api/admin/packages")
    public ResponseEntity<?> createPackage(@RequestBody TourPackage tourPackage, @AuthenticationPrincipal Object principal) {
        if (!isAdmin(principal)) return ResponseEntity.status(403).body(Map.of("error", "Forbidden: Admins only"));
        TourPackage saved = tourPackageRepository.save(tourPackage);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/api/admin/packages/{id}")
    public ResponseEntity<?> updatePackage(@PathVariable Long id, @RequestBody TourPackage packageDetails, @AuthenticationPrincipal Object principal) {
        if (!isAdmin(principal)) return ResponseEntity.status(403).body(Map.of("error", "Forbidden: Admins only"));
        Optional<TourPackage> optionalPackage = tourPackageRepository.findById(id);
        if (optionalPackage.isPresent()) {
            TourPackage existing = optionalPackage.get();
            existing.setTitle(packageDetails.getTitle());
            existing.setDescription(packageDetails.getDescription());
            existing.setDuration(packageDetails.getDuration());
            existing.setPrice(packageDetails.getPrice());
            existing.setImageUrl(packageDetails.getImageUrl());
            existing.setPackageType(packageDetails.getPackageType());
            tourPackageRepository.save(existing);
            return ResponseEntity.ok(existing);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/api/admin/packages/{id}")
    public ResponseEntity<?> deletePackage(@PathVariable Long id, @AuthenticationPrincipal Object principal) {
        if (!isAdmin(principal)) return ResponseEntity.status(403).body(Map.of("error", "Forbidden: Admins only"));
        Optional<TourPackage> optionalPackage = tourPackageRepository.findById(id);
        if (optionalPackage.isPresent()) {
            tourPackageRepository.delete(optionalPackage.get());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
