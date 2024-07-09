package domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.NumberFormat;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor()
@EqualsAndHashCode(exclude = "festivalid")
@ToString(exclude = "festivalid")
@Table(name = "festival")
@JsonPropertyOrder({"festival_id", "festival_name"})
public class Festival implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("festival_id")
    private int festivalid;

    @Column(nullable = false, unique = true)
    @JsonProperty("festival_name")
    private String name;

    @JsonIgnore
    private String logoLocation;

    @JsonIgnore
    private String logoDescription;

    @Column(nullable = false)
    @JsonIgnore
    private LocalDateTime datum;

    @ManyToOne
    @JoinColumn(name = "musicgenreid")
    @JsonIgnore
    private MusicGenre musicGenre;

    @ManyToOne
    @JoinColumn(name = "regioid")
    @JsonIgnore
    private Regio regio;

    @OneToMany(mappedBy = "booking")
    @JsonIgnore
    private List<Artist> artists;

    @JsonIgnore
    private Integer ticket;

    @NumberFormat(style = NumberFormat.Style.CURRENCY)
    @JsonIgnore
    private Double ticketPrijs;

    @ManyToMany
    @JsonIgnore
    private List<MyUser> festivalVisitors;
}
