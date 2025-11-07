package se331.lab.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    private Long id;

    private String category;
    private String title;
    private String description;
    private String location;
    private String date;
    private String time;
    private Boolean petAllowed;
    private String organizer;
}