package guru.springframework.sdjpaintro.bootstrap;

import guru.springframework.sdjpaintro.domain.AuthorUuid;
import guru.springframework.sdjpaintro.domain.Book;
import guru.springframework.sdjpaintro.domain.BookUuid;
import guru.springframework.sdjpaintro.domain.composite.AuthorComposite;
import guru.springframework.sdjpaintro.domain.composite.AuthorCompositeKey;
import guru.springframework.sdjpaintro.repositories.AuthorCompositeRepository;
import guru.springframework.sdjpaintro.repositories.AuthorUuidRepository;
import guru.springframework.sdjpaintro.repositories.BookRepository;
import guru.springframework.sdjpaintro.repositories.BookUuidRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * Created by jt on 6/12/21.
 */
@Profile({"local", "default"})
@Component
public class DataInitializer implements CommandLineRunner {

    private final BookRepository bookRepository;
    private final AuthorUuidRepository authorUuidRepository;
    private final BookUuidRepository bookUuidRepository;
    private final AuthorCompositeRepository authorCompositeRepository;

    public DataInitializer(BookRepository bookRepository,
                           AuthorUuidRepository authorUuidRepository,
                           BookUuidRepository bookUuidRepository,
                           AuthorCompositeRepository authorCompositeRepository) {
        this.bookRepository = bookRepository;
        this.authorUuidRepository = authorUuidRepository;
        this.bookUuidRepository = bookUuidRepository;
        this.authorCompositeRepository = authorCompositeRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        bookRepository.deleteAll();
        authorUuidRepository.deleteAll();
        bookUuidRepository.deleteAll();
        authorCompositeRepository.deleteAll();

        Book bookDDD = new Book("Domain Driven Design", "123", "RandomHouse", null);
        Book savedDDD = bookRepository.save(bookDDD);

        Book bookSIA = new Book("Spring In Action", "234234", "Oriely", null);
        Book savedSIA = bookRepository.save(bookSIA);

        bookRepository.findAll().forEach(book -> {
            System.out.println("Book Id: " + book.getId());
            System.out.println("Book Title: " + book.getTitle());
        });

        AuthorUuid authorUuid = new AuthorUuid();

        authorUuid.setFirstName("Joe");
        authorUuid.setLastName("Buck");

        AuthorUuid savedAuthor = authorUuidRepository.save(authorUuid);
        System.out.println("Saved Author UUID: " + savedAuthor.getId());

        BookUuid bookUuid = new BookUuid();

        bookUuid.setTitle("Endymodion");

        BookUuid savedBook = bookUuidRepository.save(bookUuid);
        System.out.println("Saved Book UUID: " + savedBook.getId());

        // Inicjalizacja AuthorComposite z kluczem złożonym
        AuthorCompositeKey key1 = new AuthorCompositeKey("John", "Smith");
        AuthorComposite author1 = new AuthorComposite(key1, "USA");
        AuthorComposite savedAuthor1 = authorCompositeRepository.save(author1);
        System.out.println("Saved Author Composite: " + savedAuthor1.getId().getFirstName() + " " 
                + savedAuthor1.getId().getLastName() + " from " + savedAuthor1.getCountry());

        AuthorCompositeKey key2 = new AuthorCompositeKey("Maria", "Kowalska");
        AuthorComposite author2 = new AuthorComposite(key2, "Poland");
        AuthorComposite savedAuthor2 = authorCompositeRepository.save(author2);
        System.out.println("Saved Author Composite: " + savedAuthor2.getId().getFirstName() + " " 
                + savedAuthor2.getId().getLastName() + " from " + savedAuthor2.getCountry());

        AuthorCompositeKey key3 = new AuthorCompositeKey("Robert", "Martin");
        AuthorComposite author3 = new AuthorComposite(key3, "USA");
        AuthorComposite savedAuthor3 = authorCompositeRepository.save(author3);
        System.out.println("Saved Author Composite: " + savedAuthor3.getId().getFirstName() + " " 
                + savedAuthor3.getId().getLastName() + " from " + savedAuthor3.getCountry());

        System.out.println("Total Author Composite records: " + authorCompositeRepository.count());

    }
}
