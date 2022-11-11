package sandro.elib.elib.crwaler;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import sandro.elib.elib.domain.Library;
import sandro.elib.elib.repository.BookRepository;
import sandro.elib.elib.repository.LibraryRepository;

import java.util.List;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

@Transactional
@SpringBootTest(webEnvironment = NONE)
class CrawlerServiceTest {

    @Autowired CrawlerService crawlerService;
    @Autowired LibraryRepository libraryRepository;
    @Autowired BookRepository bookRepository;
    @Autowired Crawler crawler;

//    @Commit
//    @DisplayName("referrer 생성 및 저장 - 도서관 테이블의 url을 이용해서 referrer을 생성해서 저장하는 기능 (DB에 적용하려면 @Commit을 달아야 함)")
//    @Test
//    void updateApiUrl() throws Exception {
//        List<Library> libraries = libraryRepository.findAll();
//        libraries.forEach(library -> {
//            String referrer = crawler.getReferrer(library);
//            library.setReferrer(referrer);
//        });
//    }

//    @Commit
//    @Test
//    void multiCrawl() throws Exception {
//        List<Library> libraries = libraryRepository.findByTotalBooksGreaterThanAndTotalBooksLessThan(500, 1000);
//        libraries.forEach(library -> crawlerService.crawl(library));
//    }
//
//    @Commit
//    @Test
//    void crawl() throws Exception {
//        Library library = libraryRepository.findByName("진도공공도서관");
//        crawlerService.crawl(library);
//    }

//    @Commit
//    @DisplayName("도서관 테이블의 url을 이용해서 api url을 생성해서 저장하는 기능 (DB에 적용하려면 @Commit을 달아야 함)")
//    @Test
//    void updateApiUrl() throws Exception {
//        List<Library> libraries = libraryRepository.findAll();
//        libraries.forEach(library -> {
//            String apiUrl = crawler.getApiUrl(library);
//            library.setApiUrl(apiUrl);
//        });
//    }

//    @Commit
//    @DisplayName("api url을 이용해서 content-type에 값을 저장 (DB에 적용하려면 @Commit을 달아야 함)")
//    @Test
//    void updateContentType() throws Exception {
//        List<Library> libraries = libraryRepository.findAll();
//        libraries.forEach(library -> {
//            if (library.getApiUrl().endsWith("001")) {
//                library.setContentType("application/json");
//            } else {
//                library.setContentType("text/xml");
//            }
//        });
//    }

//    @DisplayName("json API url에 정상 접속되는지 테스트")
//    @Test
//    void apiUrlRequestingTest() throws Exception {
//        List<Library> libraries = libraryRepository.findAll();
//        List<Library> jsons = libraries.stream().filter(library -> library.getContentType().equals(APPLICATION_JSON_VALUE)).collect(Collectors.toList());
//        List<Library> xmls = libraries.stream().filter(library -> library.getContentType().equals(TEXT_XML_VALUE)).collect(Collectors.toList());
//        System.out.println("jsons.size() = " + jsons.size());
//        System.out.println("xmls.size() = " + xmls.size());
//
//        jsons.forEach(library -> {
//            try {
//                crawler.requestUrlAndGetResponse(library.getApiUrl());
//            } catch (IOException e) {
//                System.out.println("크롤링 실패 - url을 확인하세요. : 도서관 = " + library.getName() + ", url = "+ library.getUrl() + ", apiUrl = " + library.getApiUrl());
//            }
//        });
//    }

}