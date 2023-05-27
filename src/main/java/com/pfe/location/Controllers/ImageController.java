package com.pfe.location.Controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.pfe.location.Models.Apartment;
import com.pfe.location.Models.Category;
import com.pfe.location.Models.Image;
import com.pfe.location.Models.Person;
import com.pfe.location.Repositories.ApartmentRepo;
import com.pfe.location.Repositories.ImageRepo;
import com.pfe.location.Repositories.PersonRepo;
import com.pfe.location.Utils.MyResponse;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "/api/v1/image")
@RequiredArgsConstructor
@Transactional
public class ImageController {

    final ImageRepo imageRepo;
    final PersonRepo personRepo;
    final ApartmentRepo apartmentRepo;

    @PostMapping("/person/{id_person}/image/{id_image}")
    public ResponseEntity<MyResponse> aadImageToPerson(@PathVariable("id_image") long id_image,
            @PathVariable("id_person") long id_person) {
        Optional<Image> image = imageRepo.findById(id_image);
        Optional<Person> person = personRepo.findById(id_person);
        if (image.isPresent() && person.isPresent()) {
            person.get().setImage(image.get());
            return ResponseEntity.ok(new MyResponse(200, "Image added to person!"));
        }
        return ResponseEntity.status(404).body(new MyResponse(404, "Image or person not found !"));
    }

    @PostMapping
    public String addPrfileImage(@RequestParam("image") MultipartFile file) throws IOException {
        var image = Image.builder()
                .name(file.getOriginalFilename())
                .category(Category.ProfilePhoto)
                .type(file.getContentType())
                .image(file.getBytes())
                .src("data:" + file.getContentType() + ";base64," + Base64.getEncoder().encodeToString(file.getBytes()))
                .build();

        imageRepo.save(image);
        return "added successfully!";
    }

    @GetMapping("/profile_images")
    public List<Image> getPrfileImages() {
        List<Image> profileImages = new ArrayList<Image>();
        imageRepo.findAll().forEach(elem -> {
            if (elem.getCategory() != null && elem.getCategory().equals(Category.ProfilePhoto)) {
                profileImages.add(elem);
            }
        });
        return profileImages;
    }

    @PostMapping("/upload/apartment/{id_apartment}")
    public ResponseEntity<MyResponse> uploadImageToApartment(@RequestParam(value = "files") List<MultipartFile> files,
            @PathVariable("id_apartment") long id_apartment)
            throws IOException {
        Collection<Image> images = new ArrayList<Image>();
        for (int i = 0; i < files.size(); i++) {
            var image = Image.builder()
                    .name(files.get(i).getOriginalFilename())
                    .category(Category.ApartmentPhoto)
                    .type(files.get(i).getContentType())
                    .image(files.get(i).getBytes())
                    .src("data:" + files.get(i).getContentType() + ";base64,"
                            + Base64.getEncoder().encodeToString(files.get(i).getBytes()))
                    .build();
            images.add(image);
        }

        Optional<Apartment> apartment = apartmentRepo.findById(id_apartment);
        if (apartment.isPresent()) {
            Apartment apart = apartment.get();
            Collection<Image> thisApartImages = apart.getImages();
            if (thisApartImages.size() == 0) {
                thisApartImages = images;
            } else {
                thisApartImages.addAll(images);
            }
            images.forEach(child -> child.setApartment(apart));
            apart.setImages(images);
            imageRepo.saveAll(images);
            return ResponseEntity.ok(new MyResponse(200, "All images are uploaded successfully!"));
        }
        return ResponseEntity.status(404).body(new MyResponse(404, "Apartment not found!"));
    }

    @PutMapping("update")
    public String updateSrc() {
        imageRepo.findAll().forEach(elem -> {
            byte[] imageBytes = elem.getImage(); // assuming elem.getImage() returns a byte array
            String base64String = Base64.getEncoder().encodeToString(imageBytes);
            String srcValue = "data:" + elem.getType() + ";base64," + base64String;
            elem.setSrc(srcValue);
        });

        return "success";
    }

}
