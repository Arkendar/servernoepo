package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Pet;
import com.example.demo.service.PetService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v3/pet")
@RequiredArgsConstructor
public class PetController {
    private final PetService petService;

    @GetMapping
    public ResponseEntity<List<Pet>> getAllPets() {
        List<Pet> pets = petService.getAllPets();
        if (pets.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // 204 No Content
        }
        return ResponseEntity.ok(pets); // 200 Successful operation
    }

    @GetMapping("/{petId}")
    public ResponseEntity<Pet> getPetById(@PathVariable Long petId) {
        return petService.getPetById(petId)
                .map(ResponseEntity::ok) // 200 Successful operation
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build()); // 404 Pet not found
    }

    @PostMapping
    public ResponseEntity<Object> addPet(@RequestBody Pet pet) {
        if (pet.getName() == null || pet.getName().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Invalid input"); // 400 Invalid input
        }

        Pet createdPet = petService.createPet(pet);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPet); // 201 Successful operation
    }

    @PutMapping
    public ResponseEntity<Object> updatePet(@RequestBody Pet pet) {
        if (pet.getId() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Invalid ID supplied"); // 400 Invalid ID supplied
        }

        Pet updatedPet = petService.updatePet(pet);
        return ResponseEntity.ok(updatedPet); // 200 Successful operation
    }

    @DeleteMapping("/{petId}")
    public ResponseEntity<Object> deletePet(@PathVariable Long petId) {
        if (!petService.getPetById(petId).isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Pet not found"); // 404 Pet not found
        }

        petService.deletePet(petId);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}
