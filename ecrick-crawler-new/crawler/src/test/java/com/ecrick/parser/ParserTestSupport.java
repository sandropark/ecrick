package com.ecrick.parser;

import com.ecrick.entity.Library;
import com.ecrick.entity.RowBook;
import com.ecrick.model.CommonModel;
import com.ecrick.util.FileIOUtil;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ParserTestSupport {

    protected void parse(CrawlerParser parser, String fileName) {
        // Given
        String data = FileIOUtil.read(fileName);

        // When
        CommonModel model = parser.parse(data);

        // Then
        Integer totalBooks = model.getTotalBooks();
        assertThat(totalBooks).isNotNull();
        System.out.println("totalBooks = " + totalBooks);

        List<RowBook> rowBooks = model.getRowBooks();
        assertThat(rowBooks).isNotEmpty();

        rowBooks.forEach(rowBook -> {
            assertThat(rowBook.getTitle()).isNotNull();
            assertThat(rowBook.getAuthor()).isNotNull();
            assertThat(rowBook.getPublisher()).isNotNull();
            assertThat(rowBook.getPublicDate()).isNotNull();
            System.out.println("rowBook = " + rowBook);
        });
    }

}
