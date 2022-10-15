package sandro.elib.elib;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import sandro.elib.elib.domain.Book;
import sandro.elib.elib.domain.BookLibraryServiceMap;
import sandro.elib.elib.domain.Library;
import sandro.elib.elib.domain.Service;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;

        public void dbInit() {
            Book book1 = new Book("사피엔스", "하라리", "믿음사");
            Book book2 = new Book("토지", "박경리", "김영사", LocalDateTime.of(2020,1,15,0,0,0));
            Service service1 = new Service("교보");
            Service service2 = new Service("예스24");
            Library library1 = new Library("중랑구립도서관");
            Library library2 = new Library("강남구립도서관");
            Library library3 = new Library("서초구립도서관");
            em.persist(book1);
            em.persist(book2);
            em.persist(service1);
            em.persist(service2);
            em.persist(library1);
            em.persist(library2);
            em.persist(library3);

            BookLibraryServiceMap bookLibraryServiceMap1 = new BookLibraryServiceMap(book1, library1, service1);
            BookLibraryServiceMap bookLibraryServiceMap2 = new BookLibraryServiceMap(book1, library1, service2);
            BookLibraryServiceMap bookLibraryServiceMap3 = new BookLibraryServiceMap(book1, library2, service1);
            BookLibraryServiceMap bookLibraryServiceMap4 = new BookLibraryServiceMap(book2, library2, service2);
            BookLibraryServiceMap bookLibraryServiceMap5 = new BookLibraryServiceMap(book2, library3, service2);
            em.persist(bookLibraryServiceMap1);
            em.persist(bookLibraryServiceMap2);
            em.persist(bookLibraryServiceMap3);
            em.persist(bookLibraryServiceMap4);
            em.persist(bookLibraryServiceMap5);
        }
    }
}
