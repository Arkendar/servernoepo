package com.example.demo.controller;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.demo.model.Pet;
import com.example.demo.service.PetService;
import com.fasterxml.jackson.databind.ObjectMapper;

class PetControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PetService petService;

    @InjectMocks
    private PetController petController;

    private Pet pet;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(petController).build();
        pet = new Pet(1L, "Buddy", null, null, "available");
    }

    @Test
    void testGetAllPets() throws Exception {
        when(petService.getAllPets()).thenReturn(Arrays.asList(pet));

        mockMvc.perform(get("/api/v3/pet"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].name").value("Buddy"));
    }

    @Test
    void testGetPetById_Found() throws Exception {
        when(petService.getPetById(1L)).thenReturn(Optional.of(pet));

        mockMvc.perform(get("/api/v3/pet/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Buddy"));
    }

    @Test
    void testGetPetById_NotFound() throws Exception {
        when(petService.getPetById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v3/pet/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testAddPet() throws Exception {
        when(petService.createPet(any(Pet.class))).thenReturn(pet);

        mockMvc.perform(post("/api/v3/pet")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pet)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Buddy"));
    }

    @Test
    void testUpdatePet() throws Exception {
        when(petService.updatePet(any(Pet.class))).thenReturn(pet);

        mockMvc.perform(put("/api/v3/pet")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pet)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Buddy"));
    }

    @Test
void testDeletePet() throws Exception {
    when(petService.getPetById(1L)).thenReturn(Optional.of(pet));
    doNothing().when(petService).deletePet(1L);

    mockMvc.perform(delete("/api/v3/pet/1"))
            .andExpect(status().isNoContent());

    verify(petService, times(1)).deletePet(1L);
}

}
