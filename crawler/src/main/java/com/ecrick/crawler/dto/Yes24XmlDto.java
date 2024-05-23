package com.ecrick.crawler.dto;

import com.ecrick.core.domain.Library;
import com.ecrick.core.dto.CoreDto;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;

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

        private List<CoreDto> toBookDtos(Library library) {
            List<CoreDto> dtos = new ArrayList<>();
            List<Parameter> parameters = paramList.subList(1, paramList.size());

            for (Parameter parameter : parameters) {
                String content = parameter.getContent();
                dtos.add(CoreDto.builder()
                        .library(library)
                        .title(getResult("<pdName>(.+)</pdName>", content))
                        .author(getResult("<author>(.+)</author>", content))
                        .publisher(getResult("<publisher>(.+)</publisher>", content))
                        .publicDate(getResult("<ebookYMD>(.+)</ebookYMD>", content))
                        .coverUrl(getResult("<thumbnail>(.+)</thumbnail>", content))
                        .build());
            }

            return dtos;
        }

        private String getResult(String regex, String content) {
            Matcher m = Pattern.compile(regex).matcher(content);
            return m.find() ? m.group(1).strip() : "";
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
