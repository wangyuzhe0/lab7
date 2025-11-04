package se331.lab.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Organizer {
    private Long id;
    private String organizationName;
    private String address;
}