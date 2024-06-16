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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(exclude = "regioid")
@ToString(exclude = "regioid")
@Table(name = "regio")
public class Regio implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int regioid;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "regio")
    private Set<Festival> festivals = new HashSet<>();

}
