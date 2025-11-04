package se331.lab.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import se331.lab.entity.Event;
import se331.lab.service.EventService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class EventController {

    final EventService eventService;

    @GetMapping("events")
    public ResponseEntity<?> getEventList(
            @RequestParam(value = "_limit", required = false) Integer perPage,
            @RequestParam(value = "_page", required = false) Integer page) {

        List<Event> output;
        HttpHeaders responseHeader = new HttpHeaders();

        // 获取事件总数
        Integer eventSize = eventService.getEventSize();
        responseHeader.set("x-total-count", String.valueOf(eventSize));

        if (perPage == null && page == null) {
            // 如果没有分页参数，返回所有事件
            output = eventService.getEvents(null, null);
        } else {
            // 处理分页
            perPage = perPage == null ? eventSize : perPage;
            page = page == null ? 1 : page;
            output = eventService.getEvents(perPage, page);
        }

        return new ResponseEntity<>(output, responseHeader, HttpStatus.OK);
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
}