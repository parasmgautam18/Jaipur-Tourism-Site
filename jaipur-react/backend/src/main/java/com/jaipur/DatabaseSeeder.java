package com.jaipur;

import com.jaipur.model.TourPackage;
import com.jaipur.repository.TourPackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    @Autowired
    private TourPackageRepository tourPackageRepository;

    @Override
    public void run(String... args) throws Exception {
        if (tourPackageRepository.count() == 0) {
            System.out.println("Seeding TourPackage database...");

            // EXCLUSIVE Packages
            tourPackageRepository.save(new TourPackage(
                "Amber Fort Heritage Tour", 
                "Explore the majestic Amber Fort, including a guided tour of the Diwan-e-Aam, Sheesh Mahal, and the royal courtyards. Enjoy a magnificent sunset view.",
                "Full Day", 1500, "/packages/amber_fort.png", "EXCLUSIVE"
            ));
            tourPackageRepository.save(new TourPackage(
                "Royal Palace Tour", 
                "A deep dive into the City Palace of Jaipur showcasing its intricate royal architecture, vibrant pink and peach colors, and elegant courtyards.",
                "Half Day", 1200, "/packages/city_palace.png", "EXCLUSIVE"
            ));
            tourPackageRepository.save(new TourPackage(
                "Cultural Night Safari", 
                "Experience Nahargarh fort at night overlooking the glittering city of Jaipur. Includes dinner under the starry sky and a magical atmosphere.",
                "Evening", 2000, "/packages/night_safari.png", "EXCLUSIVE"
            ));
            tourPackageRepository.save(new TourPackage(
                "Spiritual Pushkar Journey", 
                "A spiritual trip to the holy Pushkar lake surrounded by traditional temples and ghats. Includes a visit to the famous Brahma Temple.",
                "Full Day", 2500, "/packages/pushkar.png", "EXCLUSIVE"
            ));
            tourPackageRepository.save(new TourPackage(
                "Rajasthani Culinary Walk", 
                "A vibrant and rich traditional Rajasthani food tour. Taste various local curries, breads, and sweets in authentic settings.",
                "3 Hours", 1800, "/packages/food.png", "EXCLUSIVE"
            ));
            tourPackageRepository.save(new TourPackage(
                "Pink City Shopping Spree", 
                "A guided shopping tour through the vibrant bustling markets of Jaipur. Buy traditional textiles, umbrellas, and exquisite handicrafts.",
                "Half Day", 1000, "/packages/shopping.png", "EXCLUSIVE"
            ));

            // CUSTOM Packages (Places)
            tourPackageRepository.save(new TourPackage("Amber Fort", null, null, 500, "/packages/amber_fort.png", "CUSTOM"));
            tourPackageRepository.save(new TourPackage("City Palace", null, null, 400, "/packages/city_palace.png", "CUSTOM"));
            tourPackageRepository.save(new TourPackage("Nahargarh Fort", null, null, 600, "/packages/night_safari.png", "CUSTOM"));
            tourPackageRepository.save(new TourPackage("Jal Mahal", null, null, 300, "/packages/jal_mahal.png", "CUSTOM"));
            tourPackageRepository.save(new TourPackage("Hawa Mahal", null, null, 200, "/packages/hawa_mahal.png", "CUSTOM"));
            tourPackageRepository.save(new TourPackage("Jantar Mantar", null, null, 200, "/packages/jantar_mantar.png", "CUSTOM"));
            tourPackageRepository.save(new TourPackage("Albert Hall Museum", null, null, 300, "/packages/albert_hall.png", "CUSTOM"));
            tourPackageRepository.save(new TourPackage("Chokhi Dhani (Dinner)", null, null, 1000, "/packages/food.png", "CUSTOM"));

            System.out.println("Seeding completed.");
        }
    }
}
