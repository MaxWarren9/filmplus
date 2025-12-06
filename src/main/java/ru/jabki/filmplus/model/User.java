package ru.jabki.filmplus.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class User {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long id;
    private String login;
    private String name;
    private String email;
    private LocalDate birthday;
}
