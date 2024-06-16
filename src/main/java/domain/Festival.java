package domain;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(exclude = "festivalid")
@ToString(exclude = "festivalid")
@Table(name = "festival")
public class Festival implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int festivalid;

    @Column(nullable = false, unique = true)
    private String name;

    private String logoLocation;

    private String logoDescription;

    @ManyToOne
    @JoinColumn(name = "musicgenreid")
    private MusicGenre musicGenre;

    @ManyToOne
    @JoinColumn(name = "regioid")
    private Regio regio;

    @ManyToMany
    private List<Artist> artists;
}
