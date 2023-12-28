package com.ecrick.crawler.domain.webclient.response;

import com.ecrick.entity.Library;
import com.ecrick.entity.RowBook;
import lombok.Getter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDate;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.time.format.DateTimeFormatter.BASIC_ISO_DATE;

@Getter
@XmlRootElement(name = "RespMessage")
public class Yes24XmlResponse implements LibraryResponse {

    private static final Pattern TITLE_PATTERN = Pattern.compile("<pdName>(.+)</pdName>");
    private static final Pattern AUTHOR_PATTERN = Pattern.compile("<author>(.+)</author>");
    private static final Pattern PUBLISHER_PATTERN = Pattern.compile("<publisher>(.+)</publisher>");
    private static final Pattern PUBLIC_DATE_PATTERN = Pattern.compile("<ebookYMD>(.+)</ebookYMD>");
    private static final Pattern COVER_URL_PATTERN = Pattern.compile("<thumbnail>(.+)</thumbnail>");

    @XmlElement(name = "Parameters")
    private Parameters parameters;

    private static class Parameters {
        @XmlElement(name = "Parameter")
        private List<Parameter> paramList;

        @Getter
        private static class Parameter {
            @XmlElement(name = "ParameterValue")
            private String content;

            private String getTitle() {
                return getResult(TITLE_PATTERN, content);
            }

            private String getAuthor() {
                return getResult(AUTHOR_PATTERN, content);
            }

            private String getPublisher() {
                return getResult(PUBLISHER_PATTERN, content);
            }

            private LocalDate getPublicDate() {
                return LocalDate.parse(getResult(PUBLIC_DATE_PATTERN, content), BASIC_ISO_DATE);
            }

            private String getCoverUrl() {
                return getResult(COVER_URL_PATTERN, content);
            }

            private String getResult(Pattern compile, String content) {
                Matcher m = compile.matcher(content);
                return m.find() ? m.group(1).strip() : "";
            }
        }

        private Integer getTotalBooks() {
            return Integer.valueOf(paramList.get(0).content);
        }

        private List<RowBook> toRowBooks(Library library) {
            return paramList.subList(1, paramList.size()).stream()
                    .map(parameter -> RowBook.builder()
                            .library(library)
                            .title(parameter.getTitle())
                            .author(parameter.getAuthor())
                            .publisher(parameter.getPublisher())
                            .publicDate(parameter.getPublicDate())
                            .coverUrl(parameter.getCoverUrl())
                            .build())
                    .collect(Collectors.toList());
        }
    }

    @Override
    public Integer getTotalBooks() {
        return parameters.getTotalBooks();
    }

    @Override
    public List<RowBook> toRowBooks(Library library) {
        return parameters.toRowBooks(library);
    }

}
