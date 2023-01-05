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
public class Yes24XmlDto implements ResponseDto {

    @XmlElement(name = "Parameters")
    private Parameters parameters;

    private static class Parameters {
        @XmlElement(name = "Parameter")
        private List<Parameter> paramList;

        @Getter
        private static class Parameter {
            @XmlElement(name = "ParameterValue")
            private String content;
        }

        private Integer getTotalBooks() {
            return Integer.valueOf(paramList.get(0).content);
        }

        private List<BookDto> toBookDtos(Library library) {
            List<BookDto> dtos = new ArrayList<>();
            List<Parameter> parameters = paramList.subList(1, paramList.size());

            for (Parameter parameter : parameters) {
                String content = parameter.getContent();
                dtos.add(BookDto.builder()
                        .library(library)
                        .title(getResult("<pdName>(.+)</pdName>", content))
                        .author(getAuthor("<author>(.+)</author>", content))
                        .publisher(getResult("<publisher>(.+)</publisher>", content))
                        .publicDate(getLocalDate(content))
                        .coverUrl(getResult("<thumbnail>(.+)</thumbnail>", content))
                        .build());
            }

            return dtos;
        }

        private String getResult(String regex, String content) {
            Matcher m = Pattern.compile(regex).matcher(content);
            return m.find() ? m.group(1).strip() : "";
        }

        private LocalDate getLocalDate(String content) {
            String result = getResult("<ebookYMD>(.+)</ebookYMD>", content);
            if (result.matches("\\d{4}-\\d{2}-\\d{2}")) {
                return LocalDate.parse(result);
            }
            if (result.matches("\\d{8}")) {
                return LocalDate.parse(result, DateTimeFormatter.BASIC_ISO_DATE);
            }
            return null;
        }

        private String getAuthor(String regex, String content) {
            return getResult(regex, content)
                    .replaceAll("[<>]", "")
                    .replaceAll(" 글", "")
                    .replaceAll(" 외", "")
                    .replaceAll(" 글,?그림", "")
                    .replaceAll(" 편?공?등?저$", "")
                    .replaceAll(" 공?저$", "")
                    .strip();
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

}
