package com.example.demo.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.example.demo.model.Pet;
import com.example.demo.repository.PetRepository;

class PetServiceTest {

    @Mock
    private PetRepository petRepository;

    @InjectMocks
    private PetService petService;

    private Pet pet;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        pet = new Pet(1L, "Buddy", null, null, "available");
    }

    @Test
    void testGetAllPets() {
        when(petRepository.findAll()).thenReturn(Arrays.asList(pet));
        List<Pet> pets = petService.getAllPets();
        assertFalse(pets.isEmpty());
        assertEquals(1, pets.size());
        assertEquals("Buddy", pets.get(0).getName());
    }

    @Test
    void testGetPetById() {
        when(petRepository.findById(1L)).thenReturn(Optional.of(pet));
        Optional<Pet> foundPet = petService.getPetById(1L);
        assertTrue(foundPet.isPresent());
        assertEquals("Buddy", foundPet.get().getName());
    }

    @Test
    void testCreatePet() {
        when(petRepository.save(pet)).thenReturn(pet);
        Pet createdPet = petService.createPet(pet);
        assertNotNull(createdPet);
        assertEquals("Buddy", createdPet.getName());
    }

    @Test
    void testUpdatePet() {
        when(petRepository.save(pet)).thenReturn(pet);
        Pet updatedPet = petService.updatePet(pet);
        assertNotNull(updatedPet);
        assertEquals("Buddy", updatedPet.getName());
    }

    @Test
    void testDeletePet() {
        doNothing().when(petRepository).deleteById(1L);
        assertDoesNotThrow(() -> petService.deletePet(1L));
        verify(petRepository, times(1)).deleteById(1L);
    }
}
