package guru.springframework.sdjpaintro;

import guru.springframework.sdjpaintro.domain.composite.AuthorComposite;
import guru.springframework.sdjpaintro.domain.composite.AuthorCompositeKey;
import guru.springframework.sdjpaintro.repositories.AuthorCompositeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Testy jednostkowe dla AuthorComposite z kluczem złożonym
 */
@DataJpaTest
public class AuthorCompositeTest {

    @Autowired
    AuthorCompositeRepository authorCompositeRepository;

    @Test
    void testSaveAuthorComposite() {
        // Given
        AuthorCompositeKey key = new AuthorCompositeKey("Adam", "Nowak");
        AuthorComposite author = new AuthorComposite(key, "Poland");

        // When
        AuthorComposite savedAuthor = authorCompositeRepository.save(author);

        // Then
        assertThat(savedAuthor).isNotNull();
        assertThat(savedAuthor.getId()).isNotNull();
        assertThat(savedAuthor.getId().getFirstName()).isEqualTo("Adam");
        assertThat(savedAuthor.getId().getLastName()).isEqualTo("Nowak");
        assertThat(savedAuthor.getCountry()).isEqualTo("Poland");
    }

    @Test
    void testFindByCompositeKey() {
        // Given
        AuthorCompositeKey key = new AuthorCompositeKey("Jan", "Kowalski");
        AuthorComposite author = new AuthorComposite(key, "Poland");
        authorCompositeRepository.save(author);

        // When
        Optional<AuthorComposite> foundAuthor = authorCompositeRepository.findById(key);

        // Then
        assertThat(foundAuthor).isPresent();
        assertThat(foundAuthor.get().getId().getFirstName()).isEqualTo("Jan");
        assertThat(foundAuthor.get().getId().getLastName()).isEqualTo("Kowalski");
        assertThat(foundAuthor.get().getCountry()).isEqualTo("Poland");
    }

    @Test
    void testUpdateAuthorComposite() {
        // Given
        AuthorCompositeKey key = new AuthorCompositeKey("Piotr", "Wiśniewski");
        AuthorComposite author = new AuthorComposite(key, "Poland");
        authorCompositeRepository.save(author);

        // When
        author.setCountry("Germany");
        AuthorComposite updatedAuthor = authorCompositeRepository.save(author);

        // Then
        assertThat(updatedAuthor.getCountry()).isEqualTo("Germany");
        assertThat(updatedAuthor.getId().getFirstName()).isEqualTo("Piotr");
        assertThat(updatedAuthor.getId().getLastName()).isEqualTo("Wiśniewski");
    }

    @Test
    void testDeleteAuthorComposite() {
        // Given
        AuthorCompositeKey key = new AuthorCompositeKey("Anna", "Lewandowska");
        AuthorComposite author = new AuthorComposite(key, "Poland");
        authorCompositeRepository.save(author);

        // When
        authorCompositeRepository.deleteById(key);

        // Then
        Optional<AuthorComposite> deletedAuthor = authorCompositeRepository.findById(key);
        assertThat(deletedAuthor).isNotPresent();
    }

    @Test
    void testCompositeKeyUniqueness() {
        // Given
        AuthorCompositeKey key = new AuthorCompositeKey("Marek", "Zieliński");
        AuthorComposite author1 = new AuthorComposite(key, "Poland");
        authorCompositeRepository.save(author1);

        // When - próba zapisu z tym samym kluczem (powinno zaktualizować)
        AuthorComposite author2 = new AuthorComposite(key, "Czech Republic");
        AuthorComposite savedAuthor = authorCompositeRepository.save(author2);

        // Then
        assertThat(authorCompositeRepository.count()).isEqualTo(4); // 3 z DataInitializer + 1 z tego testu
        assertThat(savedAuthor.getCountry()).isEqualTo("Czech Republic");
    }

    @Test
    void testCompositeKeyEqualsAndHashCode() {
        // Given
        AuthorCompositeKey key1 = new AuthorCompositeKey("Tomasz", "Nowicki");
        AuthorCompositeKey key2 = new AuthorCompositeKey("Tomasz", "Nowicki");
        AuthorCompositeKey key3 = new AuthorCompositeKey("Tomasz", "Kowalczyk");

        // Then
        assertThat(key1).isEqualTo(key2);
        assertThat(key1.hashCode()).isEqualTo(key2.hashCode());
        assertThat(key1).isNotEqualTo(key3);
    }

    @Test
    void testFindAllAuthors() {
        // Given - DataInitializer dodaje 3 autorów
        long initialCount = authorCompositeRepository.count();
        //assertThat(initialCount).isEqualTo(3);

        // When
        AuthorCompositeKey key = new AuthorCompositeKey("Wojciech", "Szczęsny");
        AuthorComposite newAuthor = new AuthorComposite(key, "Italy");
        authorCompositeRepository.save(newAuthor);

        // Then
        long finalCount = authorCompositeRepository.count();
        assertThat(finalCount).isEqualTo(initialCount + 1);
        assertThat(authorCompositeRepository.findAll()).hasSize(4);
    }

    @Test
    void testCompositeKeyWithNullValues() {
        // Given
        AuthorCompositeKey key = new AuthorCompositeKey("Katarzyna", "Wiśniewska");
        AuthorComposite author = new AuthorComposite(key, null);

        // When
        AuthorComposite savedAuthor = authorCompositeRepository.save(author);

        // Then
        assertThat(savedAuthor.getCountry()).isNull();
        assertThat(savedAuthor.getId()).isNotNull();
    }

    @Test
    void testDifferentAuthorsWithSameFirstName() {
        // Given
        AuthorCompositeKey key1 = new AuthorCompositeKey("Andrzej", "Duda");
        AuthorCompositeKey key2 = new AuthorCompositeKey("Andrzej", "Wajda");

        AuthorComposite author1 = new AuthorComposite(key1, "Poland");
        AuthorComposite author2 = new AuthorComposite(key2, "Poland");

        // When
        authorCompositeRepository.save(author1);
        authorCompositeRepository.save(author2);

        // Then
        Optional<AuthorComposite> foundAuthor1 = authorCompositeRepository.findById(key1);
        Optional<AuthorComposite> foundAuthor2 = authorCompositeRepository.findById(key2);

        assertThat(foundAuthor1).isPresent();
        assertThat(foundAuthor2).isPresent();
        assertThat(foundAuthor1.get().getId()).isNotEqualTo(foundAuthor2.get().getId());
    }

    @Test
    @Rollback(false)
    void testPersistenceAcrossTransactions() {
        // Given
        AuthorCompositeKey key = new AuthorCompositeKey("Robert", "Lewandowski");
        AuthorComposite author = new AuthorComposite(key, "Spain");

        // When
        authorCompositeRepository.save(author);
        authorCompositeRepository.flush();

        // Then
        Optional<AuthorComposite> foundAuthor = authorCompositeRepository.findById(key);
        assertThat(foundAuthor).isPresent();
        assertThat(foundAuthor.get().getCountry()).isEqualTo("Spain");
    }
}
