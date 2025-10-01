package guru.springframework.sdjpaintro.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "author")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "author_seq")
    Long id;

    @Column(name = "firstName")
    String firstName;

    @Column(name = "lastName")
    String lastName;
}
