package domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import validator.ValidStartingHour;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor()
@EqualsAndHashCode(exclude = "artistid")
@ToString(exclude = "artistid")
@Table(name = "artist")
@JsonPropertyOrder({"artist_id", "artist_name"})
public class Artist implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("artist_id")
    private int artistid;

    @Pattern(regexp = "^[^\\s]+[-a-zA-Z\\s]+([-a-zA-Z]+)*$+", message = "{validator.artist.name}")
    @NotBlank
    @Column(nullable = false)
    @JsonProperty("artist_name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "festivalid")
    @JsonIgnore
    private Festival booking;

    @ValidStartingHour
    @Column(nullable = false, name = "startuur")
    @JsonIgnore
    private LocalDateTime startingHour;

    @JsonIgnore
    private String subgenre1;

    @JsonIgnore
    private String subgenre2;

    @Column(nullable = false)
    @NotBlank
    @JsonIgnore
    private String festivalnummer1;

    @Column(nullable = false)
    @NotBlank
    @JsonIgnore
    private String festivalnummer2;
}
