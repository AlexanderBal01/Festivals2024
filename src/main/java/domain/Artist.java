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
@EqualsAndHashCode(exclude = "artistid")
@ToString(exclude = "artistid")
@Table(name = "artist")
public class Artist implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int artistid;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToMany
    private List<Festival> bookings;
}
