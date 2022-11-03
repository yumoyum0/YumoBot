package top.yumoyumo.yumobot.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * The type Image bean.
 *
 * @Author: yumo
 * @Description: TODO
 * @DateTime: 2022 /9/20 18:10
 */
@NoArgsConstructor
@Data
public class ImageBean {

    @JsonProperty("error")
    private String error;
    @JsonProperty("data")
    private List<DataDTO> data;

    /**
     * The type Data dto.
     */
    @NoArgsConstructor
    @Data
    public static class DataDTO {
        @JsonProperty("pid")
        private Integer pid;
        @JsonProperty("p")
        private Integer p;
        @JsonProperty("uid")
        private Integer uid;
        @JsonProperty("title")
        private String title;
        @JsonProperty("author")
        private String author;
        @JsonProperty("r18")
        private Boolean r18;
        @JsonProperty("width")
        private Integer width;
        @JsonProperty("height")
        private Integer height;
        @JsonProperty("tags")
        private List<String> tags;
        @JsonProperty("ext")
        private String ext;
        @JsonProperty("uploadDate")
        private Long uploadDate;
        @JsonProperty("urls")
        private UrlsDTO urls;

        /**
         * The type Urls dto.
         */
        @NoArgsConstructor
        @Data
        public static class UrlsDTO {
            @JsonProperty("original")
            private String original;
        }
    }
}
