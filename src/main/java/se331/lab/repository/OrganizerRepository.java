package se331.lab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se331.lab.entity.Organizer;

public interface OrganizerRepository extends JpaRepository<Organizer, Long> {
    // 可以使用JpaRepository提供的默认方法
}