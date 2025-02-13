package com.example.demo.controllers;

import com.example.demo.models.Persona;
import com.example.demo.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class Controller {

    // Inyección de dependencias
    @Autowired
    private Repository repo;


    // Endpoints
    @GetMapping()
    public String index() {
        return "Connected";
    }

    @GetMapping("personas")
    public ResponseEntity<List<Persona>> listarPersonas() {
        List<Persona> personas = repo.findAll();
        if (personas.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 No Content if no personas are found
        }
        return ResponseEntity.ok(personas); // 200 OK with the list of personas
    }

    @PostMapping("addPersona")
    public ResponseEntity<String> post(@RequestBody Persona persona) {
        // Save the Persona to the database
        Persona savedPersona = repo.save(persona);

        // Return a response with a success message and a 201 Created status
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Persona saved successfully with ID: " + savedPersona.getId());
    }

    @GetMapping("getPersonaByID/{id}")
    public ResponseEntity<Persona> getPersonaByID(@PathVariable Long id) {
        Persona persona = repo.findById(id).orElse(null);

        if (persona == null) {
            return ResponseEntity.notFound().build(); // 404 Not Found if Persona is not found
        }

        return ResponseEntity.ok(persona); // 200 OK with the Persona data
    }

    @PutMapping("editarPersona/{id}")
    public ResponseEntity<Persona> update(@PathVariable Long id, @RequestBody Persona updatedPersona) {
        Persona persona = repo.findById(id).orElse(null);

        if (persona == null) {
            return ResponseEntity.notFound().build(); // 404 Not Found if Persona is not found
        }

        // Update fields in the existing persona with values from updatedPersona
        persona.setNombre(updatedPersona.getNombre());
        persona.setTelefono(updatedPersona.getTelefono());
        // Add other fields as necessary

        // Save the updated persona
        Persona savedPersona = repo.save(persona);

        return ResponseEntity.ok(savedPersona); // Return the updated persona with 200 OK
    }

    @DeleteMapping("deletePersona/{id}")
    public ResponseEntity<Void> deletePersona(@PathVariable Long id) {
        Persona persona = repo.findById(id).orElse(null);

        if (persona == null) {
            return ResponseEntity.notFound().build(); // 404 Not Found if Persona is not found
        }

        repo.deleteById(id); // Delete the persona by its ID

        return ResponseEntity.noContent().build(); // 204 No Content after successful deletion
    }
}
