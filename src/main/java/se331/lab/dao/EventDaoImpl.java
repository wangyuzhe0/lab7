package se331.lab.dao;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import se331.lab.entity.Event;

import java.util.ArrayList;
import java.util.List;

@Repository
@Profile("manual")
public class EventDaoImpl implements EventDao {
    List<Event> eventList;

    @PostConstruct
    public void init() {
        eventList = new ArrayList<>();
        // ... 现有的初始化数据保持不变 ...
    }

    @Override
    public Integer getEventSize() {
        return eventList.size();
    }

    @Override
    public Page<Event> getEvents(Integer pageSize, Integer page) {
        pageSize = pageSize == null ? eventList.size() : pageSize;
        page = page == null ? 1 : page;
        int firstIndex = (page - 1) * pageSize;

        List<Event> pageContent = eventList.subList(firstIndex,
                Math.min(firstIndex + pageSize, eventList.size()));

        return new PageImpl<>(
                pageContent,
                PageRequest.of(page - 1, pageSize),
                eventList.size()
        );
    }

    @Override
    public Event getEvent(Long id) {
        return eventList.stream()
                .filter(event -> event.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Event save(Event event) {
        Long newId = eventList.stream()
                .mapToLong(Event::getId)
                .max()
                .orElse(0L) + 1;
        event.setId(newId);
        eventList.add(event);
        return event;
    }
}