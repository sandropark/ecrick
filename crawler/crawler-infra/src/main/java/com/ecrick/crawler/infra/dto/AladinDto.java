package com.ecrick.crawler.infra.dto;

import com.ecrick.crawler.domain.ResponseDto;
import com.ecrick.domain.dto.CoreDto;
import com.ecrick.domain.entity.Library;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;

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
                return getField("<author>(.+)</author>");
            }

            private String getPublisher() {
                return getField("<publisher>(.+)</publisher>");
            }

            private String getPublicDate() {
                return getField("<ebookYMD>(.+)</ebookYMD>");
            }

            private String getCoverUrl() {
                return getField("<thumbnail>(.+)</thumbnail>");
            }
        }

        private Integer getTotalBooks() {
            return Integer.valueOf(paramList.get(0).content);
        }

        private List<CoreDto> toBookDtos(Library library) {
            List<CoreDto> dtos = new ArrayList<>();
            List<Parameter> parameters = paramList.subList(1, paramList.size());

            for (Parameter content : parameters) {
                dtos.add(CoreDto.builder()
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
    public List<CoreDto> toCoreDtos(Library library) {
        return parameters.toBookDtos(library);
    }

}
