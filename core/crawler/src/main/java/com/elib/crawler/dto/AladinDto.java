package com.elib.crawler.dto;

import com.elib.domain.Library;
import com.elib.dto.BookDto;
import lombok.Getter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@XmlRootElement(name = "RespMessage")
public class AladinDto implements ResponseDto {
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
                return getField("<author>(.+)</author>")
                        .replaceAll("[<>]", "")
                        .replaceAll(" 글", "")
                        .replaceAll(" 외", "")
                        .replaceAll(" 글,?그림", "")
                        .replaceAll(" 편?공?등?저$", "")
                        .replaceAll(" 공?저$", "")
                        .strip();
            }
            private String getPublisher() {
                return getField("<publisher>(.+)</publisher>");
            }
            private LocalDate getPublicDate() {
                String publicDate = getField("<ebookYMD>(.+)</ebookYMD>");

                if (publicDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
                    return LocalDate.parse(publicDate);
                }
                if (publicDate.matches("\\d{8}")) {
                    return LocalDate.parse(publicDate, DateTimeFormatter.BASIC_ISO_DATE);
                }
                return null;

            }
            private String getCoverUrl() {
                return getField("<thumbnail>(.+)</thumbnail>");
            }
        }

        private Integer getTotalBooks() {
            return Integer.valueOf(paramList.get(0).content);
        }

        private List<BookDto> toBookDtos(Library library) {
            List<BookDto> dtos = new ArrayList<>();
            List<Parameter> parameters = paramList.subList(1, paramList.size());

            for (Parameter content : parameters) {
                dtos.add(BookDto.builder()
                        .library(library)
                        .title(content.getTitle())
                        .author(content.getAuthor())
                        .publisher(content.getPublisher())
                        .publicDate(content.getPublicDate())
                        .coverUrl(content.getCoverUrl())
                        .build());
            }

            return dtos;
        }

    }

    @Override
    public Integer getTotalBooks() {
        return parameters.getTotalBooks();
    }

    @Override
    public List<BookDto> toBookDtos(Library library) {
        return parameters.toBookDtos(library);
    }

    public List<String> getDetailUrl(String url) {
        ArrayList<String> detailUrls = new ArrayList<>();
        int size = 20;
        for (int page = 1; page < getTotalBooks(); page+=size) {    // 알라딘은 로직이 다르다. 주의
            detailUrls.add(url + "currentPage=" + page);
        }
        detailUrls.add(url + "currentPage=" + getTotalBooks());     // 알라딘은 로직이 다르다. 주의
        return detailUrls;
    }

}
