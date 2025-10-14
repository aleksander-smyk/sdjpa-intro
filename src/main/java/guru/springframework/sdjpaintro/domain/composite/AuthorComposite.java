package guru.springframework.sdjpaintro.domain.composite;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "author_composite")
public class AuthorComposite {

    @EmbeddedId
    private AuthorCompositeKey id;

    private String country;

    public AuthorComposite() {
    }

    public AuthorComposite(AuthorCompositeKey id, String country) {
        this.id = id;
        this.country = country;
    }

    public AuthorCompositeKey getId() {
        return id;
    }

    public void setId(AuthorCompositeKey id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthorComposite that = (AuthorComposite) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
