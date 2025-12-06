package ru.jabki.filmplus.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import ru.jabki.filmplus.enums.FriendshipStatus;

import java.time.LocalDate;

@Data
@Builder(toBuilder = true)
public class Friend {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long id;
    private long userId;
    private long friendId;
    private FriendshipStatus status;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDate created;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDate updated;
}
