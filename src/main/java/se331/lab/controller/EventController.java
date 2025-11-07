package se331.lab.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import se331.lab.entity.Event;
import se331.lab.service.EventService;

@RestController
@RequiredArgsConstructor
public class EventController {
    final EventService eventService;

    @GetMapping("events")
    public ResponseEntity<?> getEventList(
            @RequestParam(value = "_limit", required = false) Integer perPage,
            @RequestParam(value = "_page", required = false) Integer page) {

        org.springframework.data.domain.Page<Event> pageOutput = eventService.getEvents(perPage, page);
        HttpHeaders responseHeader = new HttpHeaders();

        responseHeader.set("x-total-count", String.valueOf(pageOutput.getTotalElements()));

        return new ResponseEntity<>(pageOutput.getContent(), responseHeader, HttpStatus.OK);
    }

    @GetMapping("events/{id}")
    public ResponseEntity<?> getEvent(@PathVariable("id") Long id) {
        Event output = eventService.getEvent(id);
        if (output != null) {
            return ResponseEntity.ok(output);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The given id is not found");
        }
    }

    @PostMapping("/events")
    public ResponseEntity<?> addEvent(@RequestBody Event event) {
        try {
            System.out.println("Received event: " + event);

            // 额外保护：确保新事件的 ID 为 null
            if (event.getId() != null) {
                event.setId(null);
                System.out.println("Reset ID to null for new event");
            }

            Event output = eventService.save(event);
            System.out.println("Saved event: " + output);
            return ResponseEntity.ok(output);
        } catch (Exception e) {
            System.out.println("Error saving event: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}