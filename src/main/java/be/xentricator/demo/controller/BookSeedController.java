package be.xentricator.demo.controller;

import be.xentricator.demo.entity.Book;
import be.xentricator.demo.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BookSeedController {
    private final BookRepository bookRepository;

    @PostMapping("api/book/seed")
    public ResponseEntity<?> seed() {
        Book book1 = createBook("test");
        Book book2 = createBook("test2");
        bookRepository.save(book1);
        bookRepository.save(book2);
        return ResponseEntity.ok().build();
    }

    private Book createBook(String title) {
        Book book1 = new Book();
        book1.setTitle(title);
        return book1;
    }
}
