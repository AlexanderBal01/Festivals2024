package domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor()
@EqualsAndHashCode(exclude = "id")
@ToString(exclude = "id")
@Table(name = "user")
public class MyUser implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userid")
    @JsonIgnore
    private int id;

    @Column(nullable = false, unique = true)
    @JsonIgnore
    private String username;

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    @Column(nullable = false)
    @JsonProperty("fullname")
    private String fullname;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    @JsonIgnore
    private Role role;

    @ManyToMany
    @JsonIgnore
    private List<Festival> tickets;
}
