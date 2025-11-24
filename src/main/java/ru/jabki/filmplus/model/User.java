package ru.jabki.filmplus.model;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;


@Data
@Builder
public class User {
    private long id;
    private String login;
    private String name;
    private String email;
    private LocalDate birthday;
}
