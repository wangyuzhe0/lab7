package se331.lab.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import se331.lab.entity.Event;
import se331.lab.entity.Organizer;
import se331.lab.repository.EventRepository;
import se331.lab.repository.OrganizerRepository;

@Component
@RequiredArgsConstructor
public class InitApp implements ApplicationListener<ApplicationReadyEvent> {

    final EventRepository eventRepository;
    final OrganizerRepository organizerRepository;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        // 清空现有数据
        eventRepository.deleteAll();
        organizerRepository.deleteAll();

        // 先添加Organizer数据
        Organizer camtOrganizer = organizerRepository.save(Organizer.builder()
                .organizationName("CAMT")
                .address("Chiang Mai University")
                .build());

        Organizer cmuOrganizer = organizerRepository.save(Organizer.builder()
                .organizationName("CMU")
                .address("Chiang Mai")
                .build());

        Organizer chiangMaiOrganizer = organizerRepository.save(Organizer.builder()
                .organizationName("Chiang Mai")
                .address("City Center")
                .build());

        Organizer municipalityOrganizer = organizerRepository.save(Organizer.builder()
                .organizationName("Chiang Mai Municipality")
                .address("City Hall")
                .build());

        // 添加Event数据，关联Organizer
        eventRepository.save(Event.builder()
                .category("Academic")
                .title("Midterm Exam")
                .description("A time for taking the exam")
                .location("CAMT Building")
                .date("3rd Sept")
                .time("3.00-4.00 pm.")
                .petAllowed(false)
                .organizer("CAMT")
                .build());

        eventRepository.save(Event.builder()
                .category("Academic")
                .title("Commencement Day")
                .description("A time for celebration")
                .location("CMU Convention hall")
                .date("21th Jan")
                .time("8.00am-4.00 pm.")
                .petAllowed(false)
                .organizer("CMU")
                .build());

        eventRepository.save(Event.builder()
                .category("Cultural")
                .title("Loy Krathong")
                .description("A time for Krathong")
                .location("Ping River")
                .date("21th Nov")
                .time("8.00-10.00 pm.")
                .petAllowed(false)
                .organizer("Chiang Mai")
                .build());

        eventRepository.save(Event.builder()
                .category("Cultural")
                .title("Songkran")
                .description("Let's Play Water")
                .location("Chiang Mai Moat")
                .date("13th April")
                .time("10.00am - 6.00 pm.")
                .petAllowed(true)
                .organizer("Chiang Mai Municipality")
                .build());

        System.out.println("Event和Organizer初始化数据已添加到数据库");
        System.out.println("创建了 " + organizerRepository.count() + " 个Organizer");
        System.out.println("创建了 " + eventRepository.count() + " 个Event");
    }
}