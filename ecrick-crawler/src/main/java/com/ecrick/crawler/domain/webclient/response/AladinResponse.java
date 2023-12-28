package com.ecrick.crawler.domain.webclient.response;

import com.ecrick.entity.Library;
import com.ecrick.entity.RowBook;
import lombok.Getter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Getter
@XmlRootElement(name = "RespMessage")
public class AladinResponse implements LibraryResponse {
    @XmlElement(name = "Parameters")
    private Parameters parameters;

    private static class Parameters {
        @XmlElement(name = "Parameter")
        private List<Parameter> paramList;

        @Getter
        private static class Parameter {
            @XmlElement(name = "ParameterValue")
            private String content;

            private String getField(String regex) {
                Matcher m = Pattern.compile(regex).matcher(content);
                return m.find() ? m.group(1).strip() : "";
            }

            private String getTitle() {
                return getField("<pdName>(.+)</pdName>");
            }

            private String getAuthor() {
                return getField("<author>(.+)</author>");
            }

            private String getPublisher() {
                return getField("<publisher>(.+)</publisher>");
            }

            private LocalDate getPublicDate() {
                return LocalDate.parse(
                        getField("<ebookYMD>(.+)</ebookYMD>").replaceAll("[^0-9]", ""),
                        DateTimeFormatter.BASIC_ISO_DATE
                );
            }

            private String getCoverUrl() {
                return getField("<thumbnail>(.+)</thumbnail>");
            }
        }

        private Integer getTotalBooks() {
            return Integer.valueOf(paramList.get(0).content);
        }

        private List<RowBook> toRowBooks(Library library) {
            return paramList.subList(1, paramList.size()).stream()
                    .map(content -> RowBook.builder()
                            .library(library)
                            .title(content.getTitle())
                            .author(content.getAuthor())
                            .publisher(content.getPublisher())
                            .publicDate(content.getPublicDate())
                            .coverUrl(content.getCoverUrl())
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
