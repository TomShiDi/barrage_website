package barrage.demo.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Null;

/**
 * @Author TomShiDi
 * @Since 2020/7/31
 * @Version 1.0
 */
public class QuotesInfoDto {
    @NotEmpty(message = "内容不能为空")
    private String quotesContent;

    @Null(message = "上传者id必须为空")
    private Integer publisherId;

    @NotEmpty(message = "作者名不能为空")
    private String author;

    public String getQuotesContent() {
        return quotesContent;
    }

    public void setQuotesContent(String quotesContent) {
        this.quotesContent = quotesContent;
    }

    public Integer getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(Integer publisherId) {
        this.publisherId = publisherId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
