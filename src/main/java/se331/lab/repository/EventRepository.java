package se331.lab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se331.lab.entity.Event;

public interface EventRepository extends JpaRepository<Event, Long> {
    // 移除findAll()方法，使用JpaRepository自带的方法
}