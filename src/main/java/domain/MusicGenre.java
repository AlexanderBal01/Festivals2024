package domain;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = "musicgenreid")
@ToString(exclude = "musicgenreid")
@Table(name = "musicgenre")
public class MusicGenre implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int musicgenreid;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "musicGenre")
    private Set<Festival> festivals = new HashSet<>();
}
